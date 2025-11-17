package com.sopze.mc.ironrust.item;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;

import com.sopze.mc.ironrust.block.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

@SuppressWarnings("unchecked")
public class ActiveHoneycombItem extends Item {
  public static final Supplier<BiMap<Block, Block>> UNCOATED_TO_COATED_BLOCKS = Suppliers.memoize(() -> (BiMap)ImmutableBiMap.builder()
    .put(Blocks.IRON_BLOCK, ModBlocks.COATED_IRON_BLOCK)
    .put(ModBlocks.EXPOSED_IRON, ModBlocks.COATED_EXPOSED_IRON)
    .put(ModBlocks.WEATHERED_IRON, ModBlocks.COATED_WEATHERED_IRON)
    .put(Blocks.IRON_DOOR, ModBlocks.COATED_IRON_DOOR)
    .put(ModBlocks.EXPOSED_IRON_DOOR, ModBlocks.COATED_EXPOSED_IRON_DOOR)
    .put(ModBlocks.WEATHERED_IRON_DOOR, ModBlocks.COATED_WEATHERED_IRON_DOOR)
    .put(Blocks.IRON_TRAPDOOR, ModBlocks.COATED_IRON_TRAPDOOR)
    .put(ModBlocks.EXPOSED_IRON_TRAPDOOR, ModBlocks.COATED_EXPOSED_IRON_TRAPDOOR)
    .put(ModBlocks.WEATHERED_IRON_TRAPDOOR, ModBlocks.COATED_WEATHERED_IRON_TRAPDOOR)
    .put(Blocks.IRON_BARS, ModBlocks.COATED_IRON_BARS)
    .put(ModBlocks.EXPOSED_IRON_BARS, ModBlocks.COATED_EXPOSED_IRON_BARS)
    .put(ModBlocks.WEATHERED_IRON_BARS, ModBlocks.COATED_WEATHERED_IRON_BARS)
    .put(ModBlocks.IRON_GRATE, ModBlocks.COATED_IRON_GRATE)
    .put(ModBlocks.EXPOSED_IRON_GRATE, ModBlocks.COATED_EXPOSED_IRON_GRATE)
    .put(ModBlocks.WEATHERED_IRON_GRATE, ModBlocks.COATED_WEATHERED_IRON_GRATE)
    .put(Blocks.IRON_CHAIN, ModBlocks.COATED_IRON_CHAIN)
    .put(ModBlocks.EXPOSED_IRON_CHAIN, ModBlocks.COATED_EXPOSED_IRON_CHAIN)
    .put(ModBlocks.WEATHERED_IRON_CHAIN, ModBlocks.COATED_WEATHERED_IRON_CHAIN)
    .build()
  );
  public static final Supplier<BiMap<Block, Block>> COATED_TO_UNCOATED_BLOCKS = Suppliers.memoize(() -> ((BiMap) UNCOATED_TO_COATED_BLOCKS.get()).inverse());

  public ActiveHoneycombItem(Item.Properties settings) {
    super(settings);
  }

  public InteractionResult useOn(UseOnContext context) {
    Level world = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    BlockState blockState = world.getBlockState(blockPos);
    return (getCoatedState(blockState).map((blockState2) -> {
      Player playerEntity = context.getPlayer();
      ItemStack itemStack = context.getItemInHand();
      if (playerEntity instanceof ServerPlayer serverPlayerEntity) {
        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayerEntity, blockPos, itemStack);
      }

      itemStack.shrink(1);
      world.setBlock(blockPos, blockState2, 11);
      world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(playerEntity, blockState2));
      world.levelEvent(playerEntity, 3003, blockPos, 0);

     // public static final SimpleParticleType WAX_ON = register("wax_on", true);
      //public static final SimpleParticleType WAX_OFF = register("wax_off", true);

      return (InteractionResult) InteractionResult.SUCCESS;
    }).orElse(InteractionResult.PASS));
  }

  public static Optional<BlockState> getCoatedState(BlockState state) {
    return Optional.ofNullable((Block)((BiMap) UNCOATED_TO_COATED_BLOCKS.get()).get(state.getBlock())).map((block) -> block.withPropertiesOf(state));
  }
}
