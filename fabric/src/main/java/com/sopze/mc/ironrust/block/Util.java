package com.sopze.mc.ironrust.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class Util {

  public static Block registerVanilla(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
    final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, Identifier.withDefaultNamespace(id));
    return Blocks.register(registryKey, factory, settings);
  }

}
