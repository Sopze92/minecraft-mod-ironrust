package com.sopze.mc.ironrust.block;

import com.sopze.mc.ironrust.mixin.BlocksMixinInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

import static com.sopze.mc.ironrust.Constants.MOD_ID;

public class ModBlocks {

  public static final Block[] CUTOUT_BLOCKS;

  public static final Block
    ACTIVE_HONEYCOMB_BLOCK,
    EXPOSED_IRON,
    WEATHERED_IRON,
    OXIDIZED_IRON,
    COATED_IRON_BLOCK,
    COATED_WEATHERED_IRON,
    COATED_EXPOSED_IRON,
    COATED_OXIDIZED_IRON,
    EXPOSED_IRON_DOOR,
    OXIDIZED_IRON_DOOR,
    WEATHERED_IRON_DOOR,
    COATED_IRON_DOOR,
    COATED_EXPOSED_IRON_DOOR,
    COATED_OXIDIZED_IRON_DOOR,
    COATED_WEATHERED_IRON_DOOR,
    EXPOSED_IRON_TRAPDOOR,
    OXIDIZED_IRON_TRAPDOOR,
    WEATHERED_IRON_TRAPDOOR,
    COATED_IRON_TRAPDOOR,
    COATED_EXPOSED_IRON_TRAPDOOR,
    COATED_OXIDIZED_IRON_TRAPDOOR,
    COATED_WEATHERED_IRON_TRAPDOOR,
    IRON_GRATE,
    EXPOSED_IRON_GRATE,
    WEATHERED_IRON_GRATE,
    OXIDIZED_IRON_GRATE,
    COATED_IRON_GRATE,
    COATED_EXPOSED_IRON_GRATE,
    COATED_WEATHERED_IRON_GRATE,
    COATED_OXIDIZED_IRON_GRATE,
    EXPOSED_IRON_BARS,
    WEATHERED_IRON_BARS,
    OXIDIZED_IRON_BARS,
    COATED_IRON_BARS,
    COATED_EXPOSED_IRON_BARS,
    COATED_WEATHERED_IRON_BARS,
    COATED_OXIDIZED_IRON_BARS,
    EXPOSED_IRON_CHAIN,
    WEATHERED_IRON_CHAIN,
    OXIDIZED_IRON_CHAIN,
    COATED_IRON_CHAIN,
    COATED_EXPOSED_IRON_CHAIN,
    COATED_WEATHERED_IRON_CHAIN,
    COATED_OXIDIZED_IRON_CHAIN;

  static {
    ACTIVE_HONEYCOMB_BLOCK = _register("active_honeycomb_block", BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.9F).sound(SoundType.CORAL_BLOCK));
    // IRON_BLOCK
    COATED_IRON_BLOCK = _register("coated_iron_block", BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0f).sound(SoundType.IRON));
    EXPOSED_IRON = _register("exposed_iron", (settings) -> new RustableBlock(I_Rustable.RustLevel.EXPOSED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_BLOCK).mapColor(MapColor.TERRACOTTA_WHITE));
    WEATHERED_IRON = _register("weathered_iron", (settings) -> new RustableBlock(I_Rustable.RustLevel.WEATHERED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_BLOCK).mapColor(MapColor.COLOR_BROWN));
    OXIDIZED_IRON = _register("oxidized_iron", (settings) -> new RustableBlock(I_Rustable.RustLevel.OXIDIZED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_BLOCK).mapColor(MapColor.COLOR_ORANGE));
    COATED_WEATHERED_IRON = _register("coated_weathered_iron", BlockBehaviour.Properties.ofFullCopy(WEATHERED_IRON));
    COATED_EXPOSED_IRON = _register("coated_exposed_iron", BlockBehaviour.Properties.ofFullCopy(EXPOSED_IRON));
    COATED_OXIDIZED_IRON = _register("coated_oxidized_iron", BlockBehaviour.Properties.ofFullCopy(OXIDIZED_IRON));
    // IRON_GRATE
    IRON_GRATE = _register("iron_grate", (settings) -> new RustableGrateBlock(I_Rustable.RustLevel.UNAFFECTED, settings), BlockBehaviour.Properties.of().strength(3.0F, 6.0F).sound(SoundType.COPPER_GRATE).mapColor(MapColor.COLOR_ORANGE).noOcclusion().requiresCorrectToolForDrops().isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never2).isSuffocating(ModBlocks::never2).isViewBlocking(ModBlocks::never2));
    EXPOSED_IRON_GRATE = _register("exposed_iron_grate", (settings) -> new RustableGrateBlock(I_Rustable.RustLevel.EXPOSED, settings), BlockBehaviour.Properties.ofFullCopy(IRON_GRATE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY));
    WEATHERED_IRON_GRATE = _register("weathered_iron_grate", (settings) -> new RustableGrateBlock(I_Rustable.RustLevel.WEATHERED, settings), BlockBehaviour.Properties.ofFullCopy(IRON_GRATE).mapColor(MapColor.WARPED_STEM));
    OXIDIZED_IRON_GRATE = _register("oxidized_iron_grate", (settings) -> new RustableGrateBlock(I_Rustable.RustLevel.OXIDIZED, settings), BlockBehaviour.Properties.ofFullCopy(IRON_GRATE).mapColor(MapColor.WARPED_NYLIUM));
    COATED_IRON_GRATE = _register("coated_iron_grate", WaterloggedTransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(IRON_GRATE));
    COATED_EXPOSED_IRON_GRATE = _register("coated_exposed_iron_grate", WaterloggedTransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(EXPOSED_IRON_GRATE));
    COATED_WEATHERED_IRON_GRATE = _register("coated_weathered_iron_grate", WaterloggedTransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(WEATHERED_IRON_GRATE));
    COATED_OXIDIZED_IRON_GRATE = _register("coated_oxidized_iron_grate", WaterloggedTransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(OXIDIZED_IRON_GRATE));
    // IRON_BARS
    COATED_IRON_BARS = _register("coated_iron_bars", IronBarsBlock::new, BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.IRON).noOcclusion());
    EXPOSED_IRON_BARS = _register("exposed_iron_bars", (settings) -> new RustablePaneBlock(I_Rustable.RustLevel.EXPOSED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_BARS).mapColor(EXPOSED_IRON.defaultMapColor()));
    WEATHERED_IRON_BARS = _register("weathered_iron_bars", (settings) -> new RustablePaneBlock(I_Rustable.RustLevel.WEATHERED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_BARS).mapColor(WEATHERED_IRON.defaultMapColor()));
    OXIDIZED_IRON_BARS = _register("oxidized_iron_bars", (settings) -> new RustablePaneBlock(I_Rustable.RustLevel.OXIDIZED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_BARS).mapColor(OXIDIZED_IRON.defaultMapColor()));
    COATED_EXPOSED_IRON_BARS = _register("coated_exposed_iron_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.EXPOSED_IRON_BARS));
    COATED_WEATHERED_IRON_BARS = _register("coated_weathered_iron_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.WEATHERED_IRON_BARS));
    COATED_OXIDIZED_IRON_BARS = _register("coated_oxidized_iron_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.OXIDIZED_IRON_BARS));
    // IRON_DOOR
    COATED_IRON_DOOR = _register("coated_iron_door", (settings) -> new DoorBlock(BlockSetType.IRON, settings), BlockBehaviour.Properties.of().mapColor(COATED_IRON_BLOCK.defaultMapColor()).strength(5.0F).noOcclusion().pushReaction(PushReaction.DESTROY));
    EXPOSED_IRON_DOOR = _register("exposed_iron_door", (settings) -> new RustableDoorBlock(BlockSetType.IRON, I_Rustable.RustLevel.EXPOSED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_DOOR).mapColor(EXPOSED_IRON.defaultMapColor()));
    WEATHERED_IRON_DOOR = _register("weathered_iron_door", (settings) -> new RustableDoorBlock(BlockSetType.IRON, I_Rustable.RustLevel.WEATHERED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_DOOR).mapColor(WEATHERED_IRON.defaultMapColor()));
    OXIDIZED_IRON_DOOR = _register("oxidized_iron_door", (settings) -> new RustableDoorBlock(BlockSetType.IRON, I_Rustable.RustLevel.OXIDIZED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_DOOR).mapColor(OXIDIZED_IRON.defaultMapColor()));
    COATED_EXPOSED_IRON_DOOR = _register("coated_exposed_iron_door", (settings) -> new DoorBlock(BlockSetType.IRON, settings), BlockBehaviour.Properties.ofFullCopy(EXPOSED_IRON_DOOR));
    COATED_WEATHERED_IRON_DOOR = _register("coated_weathered_iron_door", (settings) -> new DoorBlock(BlockSetType.IRON, settings), BlockBehaviour.Properties.ofFullCopy(WEATHERED_IRON_DOOR));
    COATED_OXIDIZED_IRON_DOOR = _register("coated_oxidized_iron_door", (settings) -> new DoorBlock(BlockSetType.IRON, settings), BlockBehaviour.Properties.ofFullCopy(OXIDIZED_IRON_DOOR));
    // IRON_TRAPDOOR
    COATED_IRON_TRAPDOOR = _register("coated_iron_trapdoor", (settings) -> new TrapDoorBlock(BlockSetType.IRON, settings), BlockBehaviour.Properties.of().mapColor(COATED_IRON_BLOCK.defaultMapColor()).strength(5.0F).requiresCorrectToolForDrops().noOcclusion().isValidSpawn(ModBlocks::never));
    EXPOSED_IRON_TRAPDOOR = _register("exposed_iron_trapdoor", (settings) -> new RustableTrapdoorBlock(BlockSetType.IRON, I_Rustable.RustLevel.EXPOSED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_TRAPDOOR).mapColor(EXPOSED_IRON.defaultMapColor()));
    WEATHERED_IRON_TRAPDOOR = _register("weathered_iron_trapdoor", (settings) -> new RustableTrapdoorBlock(BlockSetType.IRON, I_Rustable.RustLevel.WEATHERED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_TRAPDOOR).mapColor(WEATHERED_IRON.defaultMapColor()));
    OXIDIZED_IRON_TRAPDOOR = _register("oxidized_iron_trapdoor", (settings) -> new RustableTrapdoorBlock(BlockSetType.IRON, I_Rustable.RustLevel.OXIDIZED, settings), BlockBehaviour.Properties.ofFullCopy(COATED_IRON_TRAPDOOR).mapColor(OXIDIZED_IRON.defaultMapColor()));
    COATED_EXPOSED_IRON_TRAPDOOR = _register("coated_exposed_iron_trapdoor", (settings) -> new TrapDoorBlock(BlockSetType.IRON, settings), BlockBehaviour.Properties.ofFullCopy(EXPOSED_IRON_TRAPDOOR));
    COATED_WEATHERED_IRON_TRAPDOOR = _register("coated_weathered_iron_trapdoor", (settings) -> new TrapDoorBlock(BlockSetType.IRON, settings), BlockBehaviour.Properties.ofFullCopy(WEATHERED_IRON_TRAPDOOR));
    COATED_OXIDIZED_IRON_TRAPDOOR = _register("coated_oxidized_iron_trapdoor", (settings) -> new TrapDoorBlock(BlockSetType.IRON, settings), BlockBehaviour.Properties.ofFullCopy(OXIDIZED_IRON_TRAPDOOR));
    // IRON_CHAIN
    COATED_IRON_CHAIN = _register("coated_iron_chain", ChainBlock::new, BlockBehaviour.Properties.of().forceSolidOn().requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.CHAIN).noOcclusion());
    EXPOSED_IRON_CHAIN = _register("exposed_iron_chain", (settings) -> new RustableChainBlock(I_Rustable.RustLevel.EXPOSED, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_CHAIN).mapColor(EXPOSED_IRON.defaultMapColor()));
    WEATHERED_IRON_CHAIN = _register("weathered_iron_chain", (settings) -> new RustableChainBlock(I_Rustable.RustLevel.WEATHERED, settings),BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_CHAIN).mapColor(WEATHERED_IRON.defaultMapColor()));
    OXIDIZED_IRON_CHAIN = _register("oxidized_iron_chain", (settings) -> new RustableChainBlock(I_Rustable.RustLevel.OXIDIZED, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_CHAIN).mapColor(OXIDIZED_IRON.defaultMapColor()));
    COATED_EXPOSED_IRON_CHAIN = _register("coated_exposed_iron_chain", ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.EXPOSED_IRON_CHAIN));
    COATED_WEATHERED_IRON_CHAIN = _register("coated_weathered_iron_chain", ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.WEATHERED_IRON_CHAIN));
    COATED_OXIDIZED_IRON_CHAIN = _register("coated_oxidized_iron_chain", ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.OXIDIZED_IRON_CHAIN));

    CUTOUT_BLOCKS= new Block[]{
      EXPOSED_IRON_DOOR, OXIDIZED_IRON_DOOR, WEATHERED_IRON_DOOR, COATED_IRON_DOOR, COATED_EXPOSED_IRON_DOOR, COATED_OXIDIZED_IRON_DOOR, COATED_WEATHERED_IRON_DOOR,
      EXPOSED_IRON_TRAPDOOR, OXIDIZED_IRON_TRAPDOOR, WEATHERED_IRON_TRAPDOOR, COATED_IRON_TRAPDOOR, COATED_EXPOSED_IRON_TRAPDOOR, COATED_OXIDIZED_IRON_TRAPDOOR, COATED_WEATHERED_IRON_TRAPDOOR,
      IRON_GRATE, EXPOSED_IRON_GRATE, WEATHERED_IRON_GRATE, OXIDIZED_IRON_GRATE, COATED_IRON_GRATE, COATED_EXPOSED_IRON_GRATE, COATED_WEATHERED_IRON_GRATE, COATED_OXIDIZED_IRON_GRATE,
      EXPOSED_IRON_BARS, WEATHERED_IRON_BARS, OXIDIZED_IRON_BARS, COATED_IRON_BARS, COATED_EXPOSED_IRON_BARS, COATED_WEATHERED_IRON_BARS, COATED_OXIDIZED_IRON_BARS,
      EXPOSED_IRON_CHAIN, WEATHERED_IRON_CHAIN, OXIDIZED_IRON_CHAIN, COATED_IRON_CHAIN, COATED_EXPOSED_IRON_CHAIN, COATED_WEATHERED_IRON_CHAIN, COATED_OXIDIZED_IRON_CHAIN
    };
  }

  private static Block _register(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) { return BlocksMixinInvoker.invoke_register(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, id)), factory, settings); }
  private static Block _register(String id, BlockBehaviour.Properties settings) { return _register(id, Block::new, settings); }

  public static Boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entity) { return false; }
  public static Boolean never2(BlockState state, BlockGetter level, BlockPos pos) { return false; }

  public static void initialize(){}
}
