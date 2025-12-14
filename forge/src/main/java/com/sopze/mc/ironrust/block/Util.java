package com.sopze.mc.ironrust.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.sopze.mc.ironrust.mixin.BlocksMixinInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;
import java.util.function.Supplier;

public class Util {

  public static Block registerVanilla(String id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
    final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace(id));
    return BlocksMixinInvoker.invoke_register(registryKey, factory, settings);
  }
}
