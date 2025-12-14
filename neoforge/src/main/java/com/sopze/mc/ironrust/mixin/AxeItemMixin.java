package com.sopze.mc.ironrust.mixin;

import com.sopze.mc.ironrust.block.I_Rustable;
import com.sopze.mc.ironrust.item.ActiveHoneycombItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

  @Shadow private static void spawnSoundAndParticle(Level l, BlockPos bp, Player p, BlockState bs, SoundEvent se, int i) {}

  @ModifyVariable(method= "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", at = @At(value="STORE", ordinal= 0))
  private Optional<BlockState> a_tryStrip_0(Optional<BlockState> var, UseOnContext context){
    if(var.isEmpty()){

      Optional<BlockState> state;

      Level level = context.getLevel();
      BlockPos blockPos = context.getClickedPos();
      Player player = context.getPlayer();
      BlockState blockState= level.getBlockState(blockPos);

      state = I_Rustable.getPrevious(blockState);
      if (state.isPresent()) {
        AxeItemMixin.spawnSoundAndParticle(level, blockPos, player, blockState, SoundEvents.AXE_SCRAPE, 3005);
        return state;
      }
      else {
        state= Optional.ofNullable(ActiveHoneycombItem.COATED_TO_UNCOATED_BLOCKS.get().get(blockState.getBlock()))
          .map(b -> b.withPropertiesOf(blockState));
        if (state.isPresent()) {
          AxeItemMixin.spawnSoundAndParticle(level, blockPos, player, blockState, SoundEvents.AXE_WAX_OFF, 3004);
          return state;
        }
      }
    }
    return var;
  }
}
