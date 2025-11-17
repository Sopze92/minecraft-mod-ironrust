package com.sopze.mc.ironrust.mixin;

import com.google.common.collect.BiMap;
import com.sopze.mc.ironrust.block.I_Rustable;
import com.sopze.mc.ironrust.item.ActiveHoneycombItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

  private static BlockState __STATE;

  @Inject(method = "evaluateNewBlockState", at= @At("HEAD"))
  private void i_tryStrip_0(Level world, BlockPos pos, Player player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir){
    __STATE = state;
  }

  @Inject(method = "evaluateNewBlockState", at= @At("RETURN"))
  private void i_tryStrip_1(Level world, BlockPos pos, Player player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir){
    __STATE = null;
  }

  @ModifyVariable( method = "evaluateNewBlockState", ordinal=1, at = @At( value = "STORE", ordinal=0 ))
  private Optional<BlockState> a_tryStrip_0(Optional<BlockState> var){
    return var.isEmpty() && __STATE != null ? I_Rustable.getPrevious(__STATE) : var;
  }

  @ModifyVariable( method = "evaluateNewBlockState", ordinal=2, at = @At( value = "STORE", ordinal=0 ))
  private Optional<BlockState> a_tryStrip_1(Optional<BlockState> var){
    return var.isEmpty() && __STATE != null ? Optional.ofNullable((Block)((BiMap) ActiveHoneycombItem.COATED_TO_UNCOATED_BLOCKS.get()).get(__STATE.getBlock())).map((block) -> block.withPropertiesOf(__STATE)) : var;
  }
}
