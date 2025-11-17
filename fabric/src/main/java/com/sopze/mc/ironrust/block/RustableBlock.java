package com.sopze.mc.ironrust.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RustableBlock extends Block implements I_Rustable {
  public static final MapCodec<RustableBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(RustLevel.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec()).apply(instance, RustableBlock::new));
  private final RustLevel rustLevel;

  public MapCodec<RustableBlock> getCodec() {
    return CODEC;
  }

  public RustableBlock(RustLevel rustLevel, BlockBehaviour.Properties settings) {
    super(settings);
    this.rustLevel = rustLevel;
  }

  protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
    this.changeOverTime(state, world, pos, random);
  }

  protected boolean isRandomlyTicking(BlockState state) {
    return I_Rustable.getNext(state.getBlock()).isPresent();
  }

  public RustLevel getAge() { return this.rustLevel; }
}