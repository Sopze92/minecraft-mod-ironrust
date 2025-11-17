package com.sopze.mc.ironrust.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class RustableTrapdoorBlock extends TrapDoorBlock implements I_Rustable {
  public static final MapCodec<RustableTrapdoorBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(BlockSetType.CODEC.fieldOf("block_set_type").forGetter(RustableTrapdoorBlock::getType), RustLevel.CODEC.fieldOf("weathering_state").forGetter(RustableTrapdoorBlock::getAge), propertiesCodec()).apply(instance, RustableTrapdoorBlock::new));
  private final RustLevel rustLevel;

  public MapCodec<RustableTrapdoorBlock> getCodec() {
    return CODEC;
  }

  public RustableTrapdoorBlock(BlockSetType type, RustLevel rustLevel, BlockBehaviour.Properties settings) {
    super(type, settings);
    this.rustLevel = rustLevel;
  }

  protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
    this.changeOverTime(state, world, pos, random);
  }

  protected boolean isRandomlyTicking(BlockState state) {
    return I_Rustable.getNext(state.getBlock()).isPresent();
  }

  public RustLevel getAge() {
    return this.rustLevel;
  }
}
