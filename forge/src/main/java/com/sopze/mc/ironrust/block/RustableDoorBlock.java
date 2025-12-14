package com.sopze.mc.ironrust.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class RustableDoorBlock extends DoorBlock implements I_Rustable {
  public static final MapCodec<RustableDoorBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(BlockSetType.CODEC.fieldOf("block_set_type").forGetter(DoorBlock::type), RustLevel.CODEC.fieldOf("weathering_state").forGetter(RustableDoorBlock::getAge), propertiesCodec()).apply(instance, RustableDoorBlock::new));
  private final RustLevel rustLevel;

  public MapCodec<RustableDoorBlock> getCodec() {
    return CODEC;
  }

  public RustableDoorBlock(BlockSetType type, RustLevel rustLevel, Properties settings) {
    super(type, settings);
    this.rustLevel = rustLevel;
  }

  protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
    if (state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
      this.changeOverTime(state, world, pos, random);
    }
  }

  protected boolean isRandomlyTicking(BlockState state) { return I_Rustable.getNext(state.getBlock()).isPresent(); }

  public RustLevel getAge() { return this.rustLevel; }
}

