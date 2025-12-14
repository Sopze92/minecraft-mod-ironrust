package com.sopze.mc.ironrust.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.WaterFluid;

import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public interface I_Rustable extends ChangeOverTimeBlock<I_Rustable.RustLevel> {

  float[]
    CHANCE_MULTIPLIER_BY_DEGRADATION= { .85f, 1.0f, 1.02f, 1.06f },
    CHANCE_ADDER_BY_WATER_DISTANCE= { .17f, .09f, .04f, .00f },
    CHANCE_ADDER_BY_WATER_AMOUNT= { .00f, .015f, .040f, .075f, .13f, .19f, .265f, .325f, .365f, .385f, .40f };

  default void changeOverTime(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

    float[] baseChances= getChanceModifierWorld(state, world, pos);
    float passChance=  0.04988889F + baseChances[0] * .013774f;

    if (random.nextFloat() < passChance) {
      //System.out.printf("tryDegrade -> p:%.4f w:%d c:%.4f @ %d, %d, %d%n", passChance, (int)baseChances[0], baseChances[1], pos.getX(), pos.getY(), pos.getZ());
      this.getNextState(state, world, pos, random, baseChances[1]).ifPresent((degraded) -> world.setBlockAndUpdate(pos, degraded));
    }

  }

  default Optional<BlockState> getNextState(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, float waterFactor) {
    int
      levelCurrent = this.getAge().ordinal(),
      surroundingEquals = 0,
      surroundingHighers = 0;

    // neighbor test

    for(BlockPos blockPos : BlockPos.withinManhattan(pos, 4, 4, 4)) {
      if(blockPos.equals(pos)) continue;

      if (blockPos.distManhattan(pos) > 4 ) continue;

      Block blockTest = world.getBlockState(blockPos).getBlock();

      if (blockTest instanceof ChangeOverTimeBlock<?> degradable) {
        Enum<?> degradationLevelTest = degradable.getAge();

        if (this.getAge().getClass() == degradationLevelTest.getClass()) {
          int levelCurrentTest = degradationLevelTest.ordinal();

          if (levelCurrentTest == levelCurrent) ++surroundingEquals;
          else if (levelCurrentTest > levelCurrent) ++surroundingHighers;
        }
      }
    }

    // the maths

    float ratio = (float)(surroundingHighers + 1) / (float)(surroundingHighers + surroundingEquals + 1) + waterFactor;
    float finalFactor = (ratio * ratio) * (this.getChanceModifier() + waterFactor);

    //System.out.printf("  data -> c:%s se:%d, sh:%d r:%.4f f:%.4f%n", this.getDegradationLevel().toString(), surroundingEquals, surroundingHighers, ratio, finalFactor);

    return random.nextFloat() < finalFactor ? this.getNext(state) : Optional.empty();
  }

  static Optional<Block> getDecreasedRustBlock(Block block) {
    return Optional.ofNullable((Block)((BiMap)ForgeRustableMap.RUST_LEVEL_DECREASES.get()).get(block));
  }

  static Block getUnaffectedRustBlock(Block block) {
    Block block2 = block;

    for(Block block3 = (Block)((BiMap)ForgeRustableMap.RUST_LEVEL_DECREASES.get()).get(block); block3 != null; block3 = (Block)((BiMap)ForgeRustableMap.RUST_LEVEL_DECREASES.get()).get(block3)) {
      block2 = block3;
    }

    return block2;
  }

  static Optional<BlockState> getPrevious(BlockState state) {
    return getDecreasedRustBlock(state.getBlock()).map((block) -> block.withPropertiesOf(state));
  }

  static Optional<Block> getNext(Block block) {
    return Optional.ofNullable((Block)((BiMap)ForgeRustableMap.RUST_LEVEL_INCREASES.get()).get(block));
  }

  static BlockState getFirst(BlockState state) {
    return getUnaffectedRustBlock(state.getBlock()).withPropertiesOf(state);
  }

  default Optional<BlockState> getNext(BlockState state) {
    return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
  }

  default float getChanceModifier() {
    return CHANCE_MULTIPLIER_BY_DEGRADATION[this.getAge().ordinal()];
  }

  default float[] getChanceModifierWorld(BlockState state, ServerLevel world, BlockPos pos)
  {
    final int
      areaX= 6,
      areaY= 3,
      waterNeighborCountMax= CHANCE_ADDER_BY_WATER_AMOUNT.length-1;

    float waterNeighborFactor= .0f;
    int
      waterNeighborsCount = 0,
      waterClosestDistance = 8;

    BlockPos lookupPos= pos.below();
    BlockState checkBlockState;
    FluidState checkFluidState;
    int distance;

    for(BlockPos blockPos : BlockPos.withinManhattan(lookupPos, areaX, areaY, areaX)) {
      if(blockPos.equals(pos)) continue;

      distance= blockPos.distManhattan(lookupPos);
      if(distance > areaX ) continue;

      checkBlockState = world.getBlockState(blockPos);
      if (checkBlockState.getBlock() instanceof LiquidBlock) {

        checkFluidState = checkBlockState.getFluidState();
        if(checkFluidState.getType() instanceof WaterFluid) {

          if (distance < waterClosestDistance) waterClosestDistance = distance;

          waterNeighborsCount++;
          waterNeighborFactor += .125f * checkFluidState.getOwnHeight();

          if (waterNeighborsCount == waterNeighborCountMax) break;
        }
      }
    }

    int waterFactor = Math.clamp(waterClosestDistance/2 -1, 0, 3);
    float amountFactor= 1.0F + CHANCE_ADDER_BY_WATER_AMOUNT[Math.clamp((int)Math.floor(waterNeighborFactor), 0, waterNeighborCountMax)];

    return new float[] {waterFactor * amountFactor, CHANCE_ADDER_BY_WATER_DISTANCE[waterFactor] * amountFactor};
  }

  public static enum RustLevel implements StringRepresentable {
    UNAFFECTED("unaffected"),
    EXPOSED("exposed"),
    WEATHERED("weathered"),
    OXIDIZED("oxidized");

    public static final IntFunction<RustLevel> indexMapper = ByIdMap.continuous(Enum::ordinal, values(), OutOfBoundsStrategy.CLAMP);
    public static final Codec<RustLevel> CODEC = StringRepresentable.fromEnum(RustLevel::values);
    public static final StreamCodec<ByteBuf, RustLevel> PACKET_CODEC = ByteBufCodecs.idMapper(indexMapper, Enum::ordinal);
    private final String id;

    private RustLevel(final String id) {
      this.id = id;
    }

    public String getSerializedName() {
      return this.id;
    }

    public RustLevel next() {
      return (RustLevel)indexMapper.apply(this.ordinal() + 1);
    }

    public RustLevel previous() {
      return (RustLevel)indexMapper.apply(this.ordinal() - 1);
    }
  }
}

