package com.sopze.mc.ironrust.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RustablePaneBlock extends IronBarsBlock implements I_Rustable {
  public static final MapCodec<RustablePaneBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(RustLevel.CODEC.fieldOf("weathering_state").forGetter(RustablePaneBlock::getAge), propertiesCodec()).apply(instance, RustablePaneBlock::new));
  private final RustLevel rustLevel;

  public MapCodec<RustablePaneBlock> getCodec() {
    return CODEC;
  }

  public RustablePaneBlock(RustLevel rustLevel, Properties settings) {
    super(settings);
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
