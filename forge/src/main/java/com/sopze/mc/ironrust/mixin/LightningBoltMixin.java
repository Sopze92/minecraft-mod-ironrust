package com.sopze.mc.ironrust.mixin;

import com.sopze.mc.ironrust.block.I_Rustable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {

  @Redirect( method="tick", at= @At( value="INVOKE", target="Lnet/minecraft/world/entity/LightningBolt;clearCopperOnLightningStrike(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V" ))
  private static void clearOxideOnLightningStrike(Level level, BlockPos pos) {
    BlockState blockstate = level.getBlockState(pos);
    Block b= blockstate.getBlock();
    if (b instanceof WeatheringCopper || b instanceof I_Rustable) {
      level.setBlockAndUpdate(pos, b instanceof WeatheringCopper ? WeatheringCopper.getFirst(level.getBlockState(pos)) : I_Rustable.getFirst(level.getBlockState(pos)));
      BlockPos.MutableBlockPos blockpos$mutableblockpos = pos.mutable();
      int i = level.random.nextInt(3) + 3;
      for(int j = 0; j < i; ++j) {
        int k = level.random.nextInt(8) + 1;
        randomWalkCleaningOxide(level, pos, blockpos$mutableblockpos, k);
      }
    }
  }

  @Unique
  private static void randomWalkCleaningOxide(Level level, BlockPos pos, BlockPos.MutableBlockPos mutable, int steps) {
    mutable.set(pos);
    for(int i = 0; i < steps; ++i) {
      Optional<BlockPos> optional = randomStepCleaningOxide(level, mutable);
      if (optional.isEmpty()) {
        break;
      }
      mutable.set((Vec3i)optional.get());
    }
  }

  @Unique
  private static Optional<BlockPos> randomStepCleaningOxide(Level level, BlockPos pos) {
    for(BlockPos blockpos : BlockPos.randomInCube(level.random, 10, pos, 1)) {
      BlockState blockstate = level.getBlockState(blockpos);
      Block b= blockstate.getBlock();
      if (b instanceof WeatheringCopper || b instanceof I_Rustable) {
        if(b instanceof WeatheringCopper) WeatheringCopper.getPrevious(blockstate).ifPresent((p_147144_) -> level.setBlockAndUpdate(blockpos, p_147144_));
        else if(b instanceof I_Rustable) I_Rustable.getPrevious(blockstate).ifPresent((p_147144_) -> level.setBlockAndUpdate(blockpos, p_147144_));
        level.levelEvent(3002, blockpos, -1);
        return Optional.of(blockpos);
      }
    }
    return Optional.empty();
  }
}
