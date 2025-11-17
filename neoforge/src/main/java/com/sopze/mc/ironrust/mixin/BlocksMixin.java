package com.sopze.mc.ironrust.mixin;

import com.sopze.mc.ironrust.Logger;
import com.sopze.mc.ironrust.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Function;

import static com.sopze.mc.ironrust.Constants.LOC_REGISTRY_OVERRIDE;
import static com.sopze.mc.ironrust.Constants.OVERRIDE_BLOCK;

@Mixin(Blocks.class)
public class BlocksMixin {

  // IRON_BLOCK
  @Redirect( method = "<clinit>",
    slice = @Slice( from = @At( value = "CONSTANT", args= { "stringValue=iron_block" }, ordinal = 0) ),
    at = @At( value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0 )
  )
  private static Block r_register0(String id, BlockBehaviour.Properties settings) {
    Logger.log(LOC_REGISTRY_OVERRIDE, OVERRIDE_BLOCK, id);
    return Util.registerVanilla(id, (s) -> new RustableBlock(I_Rustable.RustLevel.UNAFFECTED, s), BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0F, 6.0f).sound(SoundType.IRON));
  }

  // IRON_DOOR
  @Redirect( method = "<clinit>",
    slice = @Slice( from = @At( value = "CONSTANT", args= { "stringValue=iron_door" }, ordinal = 0) ),
    at = @At( value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0 )
  )
  private static Block r_register1(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
    Logger.log(LOC_REGISTRY_OVERRIDE, OVERRIDE_BLOCK, id);
    return Util.registerVanilla(id, (s) -> new RustableDoorBlock(BlockSetType.IRON, I_Rustable.RustLevel.UNAFFECTED, s), BlockBehaviour.Properties.of().mapColor(Blocks.IRON_BLOCK.defaultMapColor()).strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY));
  }

  // IRON_TRAPDOOR
  @Redirect( method = "<clinit>",
    slice = @Slice( from = @At( value = "CONSTANT", args= { "stringValue=iron_trapdoor" }, ordinal = 0) ),
    at = @At( value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0 )
  )
  private static Block r_register2(String id, Function<BlockBehaviour.Properties, Block> factory,BlockBehaviour.Properties settings) {
    Logger.log(LOC_REGISTRY_OVERRIDE, OVERRIDE_BLOCK, id);
    return Util.registerVanilla(id, (s) -> new RustableTrapdoorBlock(BlockSetType.IRON, I_Rustable.RustLevel.UNAFFECTED, s), BlockBehaviour.Properties.of().mapColor(Blocks.IRON_BLOCK.defaultMapColor()).strength(5.0F).requiresCorrectToolForDrops().noOcclusion().isValidSpawn(Blocks::never));
  }

  // IRON_BARS
  @Redirect( method = "<clinit>",
    slice = @Slice( from = @At( value = "CONSTANT", args= { "stringValue=iron_bars" }, ordinal = 0) ),
    at = @At( value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0 )
  )
  private static Block r_register3(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
    Logger.log(LOC_REGISTRY_OVERRIDE, OVERRIDE_BLOCK, id);
    return Util.registerVanilla(id, (s) -> new RustablePaneBlock(I_Rustable.RustLevel.UNAFFECTED, s), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.IRON).noOcclusion());
  }

  // IRON_CHAIN
  @Redirect( method = "<clinit>",
    slice = @Slice( from = @At( value = "CONSTANT", args= { "stringValue=iron_chain" }, ordinal = 0) ),
    at = @At( value = "INVOKE", target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;", ordinal = 0 )
  )
  private static Block r_register4(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
    Logger.log(LOC_REGISTRY_OVERRIDE, OVERRIDE_BLOCK, id);
    return Util.registerVanilla(id, (s) -> new RustableChainBlock(I_Rustable.RustLevel.UNAFFECTED, s), BlockBehaviour.Properties.of().forceSolidOn().requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion());
  }

}