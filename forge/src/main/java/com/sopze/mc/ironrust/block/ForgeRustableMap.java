package com.sopze.mc.ironrust.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class ForgeRustableMap {

  public static Supplier<BiMap<Block, Block>> RUST_LEVEL_INCREASES= ()-> (BiMap) ImmutableBiMap.builder().build();
  public static Supplier<BiMap<Block, Block>> RUST_LEVEL_DECREASES= ()-> (BiMap) ImmutableBiMap.builder().build();

  public static void initialize(){
    RUST_LEVEL_INCREASES = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
      .put(Blocks.IRON_BLOCK, ModBlocks.EXPOSED_IRON)
      .put(ModBlocks.EXPOSED_IRON, ModBlocks.WEATHERED_IRON)
      .put(ModBlocks.WEATHERED_IRON, ModBlocks.OXIDIZED_IRON)
      .put(Blocks.IRON_DOOR, ModBlocks.EXPOSED_IRON_DOOR)
      .put(ModBlocks.EXPOSED_IRON_DOOR, ModBlocks.WEATHERED_IRON_DOOR)
      .put(ModBlocks.WEATHERED_IRON_DOOR, ModBlocks.OXIDIZED_IRON_DOOR)
      .put(Blocks.IRON_TRAPDOOR, ModBlocks.EXPOSED_IRON_TRAPDOOR)
      .put(ModBlocks.EXPOSED_IRON_TRAPDOOR, ModBlocks.WEATHERED_IRON_TRAPDOOR)
      .put(ModBlocks.WEATHERED_IRON_TRAPDOOR, ModBlocks.OXIDIZED_IRON_TRAPDOOR)
      .put(Blocks.IRON_BARS, ModBlocks.EXPOSED_IRON_BARS)
      .put(ModBlocks.EXPOSED_IRON_BARS, ModBlocks.WEATHERED_IRON_BARS)
      .put(ModBlocks.WEATHERED_IRON_BARS, ModBlocks.OXIDIZED_IRON_BARS)
      .put(ModBlocks.IRON_GRATE, ModBlocks.EXPOSED_IRON_GRATE)
      .put(ModBlocks.EXPOSED_IRON_GRATE, ModBlocks.WEATHERED_IRON_GRATE)
      .put(ModBlocks.WEATHERED_IRON_GRATE, ModBlocks.OXIDIZED_IRON_GRATE)
      .put(Blocks.IRON_CHAIN, ModBlocks.EXPOSED_IRON_CHAIN)
      .put(ModBlocks.EXPOSED_IRON_CHAIN, ModBlocks.WEATHERED_IRON_CHAIN)
      .put(ModBlocks.WEATHERED_IRON_CHAIN, ModBlocks.OXIDIZED_IRON_CHAIN)
      .build()
    );
    RUST_LEVEL_DECREASES = Suppliers.memoize(() -> ((BiMap)RUST_LEVEL_INCREASES.get()).inverse());
  }
}
