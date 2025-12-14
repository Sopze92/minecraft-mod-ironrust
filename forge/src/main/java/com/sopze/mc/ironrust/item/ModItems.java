package com.sopze.mc.ironrust.item;

import com.sopze.mc.ironrust.Logger;
import com.sopze.mc.ironrust.Main;
import com.sopze.mc.ironrust.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.*;
import java.util.function.Function;

import static com.sopze.mc.ironrust.Constants.*;

public class ModItems {

  private static Item
    ACTIVE_HONEYCOMB,
  // ITEM BLOCKS
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

  private static Item _create(RegisterEvent.RegisterHelper h, String id, Function<Item.Properties, Item> factory) {
    Item i= factory.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, id))));
    h.register(id, i);
    return i;
  }

  private static Item _create(RegisterEvent.RegisterHelper h, Block block){
    Identifier resLoc= BuiltInRegistries.BLOCK.getKey(block);
    Item i= new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, resLoc)).useBlockDescriptionPrefix());
    h.register(resLoc.getPath(), i);
    return i;
  }

  public static void register(RegisterEvent event){
    event.register(ForgeRegistries.Keys.ITEMS, (h)->{
      ACTIVE_HONEYCOMB = _create(h, "active_honeycomb", ActiveHoneycombItem::new);
      // ITEM BLOCKS
      ACTIVE_HONEYCOMB_BLOCK = _create(h, ModBlocks.ACTIVE_HONEYCOMB_BLOCK);
      EXPOSED_IRON = _create(h, ModBlocks.EXPOSED_IRON);
      WEATHERED_IRON = _create(h, ModBlocks.WEATHERED_IRON);
      OXIDIZED_IRON = _create(h, ModBlocks.OXIDIZED_IRON);
      COATED_IRON_BLOCK = _create(h, ModBlocks.COATED_IRON_BLOCK);
      COATED_WEATHERED_IRON = _create(h, ModBlocks.COATED_WEATHERED_IRON);
      COATED_EXPOSED_IRON = _create(h, ModBlocks.COATED_EXPOSED_IRON);
      COATED_OXIDIZED_IRON = _create(h, ModBlocks.COATED_OXIDIZED_IRON);
      EXPOSED_IRON_DOOR = _create(h, ModBlocks.EXPOSED_IRON_DOOR);
      OXIDIZED_IRON_DOOR = _create(h, ModBlocks.OXIDIZED_IRON_DOOR);
      WEATHERED_IRON_DOOR = _create(h, ModBlocks.WEATHERED_IRON_DOOR);
      COATED_IRON_DOOR = _create(h, ModBlocks.COATED_IRON_DOOR);
      COATED_EXPOSED_IRON_DOOR = _create(h, ModBlocks.COATED_EXPOSED_IRON_DOOR);
      COATED_OXIDIZED_IRON_DOOR = _create(h, ModBlocks.COATED_OXIDIZED_IRON_DOOR);
      COATED_WEATHERED_IRON_DOOR = _create(h, ModBlocks.COATED_WEATHERED_IRON_DOOR);
      EXPOSED_IRON_TRAPDOOR = _create(h, ModBlocks.EXPOSED_IRON_TRAPDOOR);
      OXIDIZED_IRON_TRAPDOOR = _create(h, ModBlocks.OXIDIZED_IRON_TRAPDOOR);
      WEATHERED_IRON_TRAPDOOR = _create(h, ModBlocks.WEATHERED_IRON_TRAPDOOR);
      COATED_IRON_TRAPDOOR = _create(h, ModBlocks.COATED_IRON_TRAPDOOR);
      COATED_EXPOSED_IRON_TRAPDOOR = _create(h, ModBlocks.COATED_EXPOSED_IRON_TRAPDOOR);
      COATED_OXIDIZED_IRON_TRAPDOOR = _create(h, ModBlocks.COATED_OXIDIZED_IRON_TRAPDOOR);
      COATED_WEATHERED_IRON_TRAPDOOR = _create(h, ModBlocks.COATED_WEATHERED_IRON_TRAPDOOR);
      IRON_GRATE = _create(h, ModBlocks.IRON_GRATE);
      EXPOSED_IRON_GRATE = _create(h, ModBlocks.EXPOSED_IRON_GRATE);
      WEATHERED_IRON_GRATE = _create(h, ModBlocks.WEATHERED_IRON_GRATE);
      OXIDIZED_IRON_GRATE = _create(h, ModBlocks.OXIDIZED_IRON_GRATE);
      COATED_IRON_GRATE = _create(h, ModBlocks.COATED_IRON_GRATE);
      COATED_EXPOSED_IRON_GRATE = _create(h, ModBlocks.COATED_EXPOSED_IRON_GRATE);
      COATED_WEATHERED_IRON_GRATE = _create(h, ModBlocks.COATED_WEATHERED_IRON_GRATE);
      COATED_OXIDIZED_IRON_GRATE = _create(h, ModBlocks.COATED_OXIDIZED_IRON_GRATE);
      EXPOSED_IRON_BARS = _create(h, ModBlocks.EXPOSED_IRON_BARS);
      WEATHERED_IRON_BARS = _create(h, ModBlocks.WEATHERED_IRON_BARS);
      OXIDIZED_IRON_BARS = _create(h, ModBlocks.OXIDIZED_IRON_BARS);
      COATED_IRON_BARS = _create(h, ModBlocks.COATED_IRON_BARS);
      COATED_EXPOSED_IRON_BARS = _create(h, ModBlocks.COATED_EXPOSED_IRON_BARS);
      COATED_WEATHERED_IRON_BARS = _create(h, ModBlocks.COATED_WEATHERED_IRON_BARS);
      COATED_OXIDIZED_IRON_BARS = _create(h, ModBlocks.COATED_OXIDIZED_IRON_BARS);
      EXPOSED_IRON_CHAIN = _create(h, ModBlocks.EXPOSED_IRON_CHAIN);
      WEATHERED_IRON_CHAIN = _create(h, ModBlocks.WEATHERED_IRON_CHAIN);
      OXIDIZED_IRON_CHAIN = _create(h, ModBlocks.OXIDIZED_IRON_CHAIN);
      COATED_IRON_CHAIN = _create(h, ModBlocks.COATED_IRON_CHAIN);
      COATED_EXPOSED_IRON_CHAIN = _create(h, ModBlocks.COATED_EXPOSED_IRON_CHAIN);
      COATED_WEATHERED_IRON_CHAIN = _create(h, ModBlocks.COATED_WEATHERED_IRON_CHAIN);
      COATED_OXIDIZED_IRON_CHAIN = _create(h, ModBlocks.COATED_OXIDIZED_IRON_CHAIN);

      DispenserBlock.registerBehavior(ACTIVE_HONEYCOMB, new OptionalDispenseItemBehavior() {
        @Override
        public ItemStack execute(BlockSource b, ItemStack i) {
          BlockPos blockpos = b.pos().relative(b.state().getValue(DispenserBlock.FACING));
          Level level = b.level();
          BlockState blockstate = level.getBlockState(blockpos);
          Optional<BlockState> optional = ActiveHoneycombItem.getCoatedState(blockstate);
          if (optional.isPresent()) {
            level.setBlockAndUpdate(blockpos, optional.get());
            level.levelEvent(3003, blockpos, 0);
            i.shrink(1);
            this.setSuccess(true);
            return i;
          } else {
            return super.execute(b, i);
          }
        }
      });
    });
  }


  private static void _insertAfter(BuildCreativeModeTabContentsEvent event, ItemLike target, Item item, CreativeModeTab.TabVisibility visibility){
    Logger.slog("insert item: %s", item);
    if(item == null) return;
    event.getEntries().putAfter(target.asItem().getDefaultInstance(), item.getDefaultInstance(), visibility);
  }

  private static void _insertAllAfter(BuildCreativeModeTabContentsEvent event, ItemLike target, Collection<Item> items, CreativeModeTab.TabVisibility visibility){
    for(Item i : items){
      Logger.slog("insert all item: %s", i);
      if(i == null) continue;
      event.getEntries().putAfter(target.asItem().getDefaultInstance(), i.getDefaultInstance(), visibility);
    }
  }
  public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event){
    if(!Main.isEnabledLocally()) return;

    ResourceKey<CreativeModeTab> groupKey= event.getTabKey();

    if(groupKey == CreativeModeTabs.INGREDIENTS){
      _insertAfter(event, Items.HONEYCOMB, ACTIVE_HONEYCOMB, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
    else if(groupKey == CreativeModeTabs.NATURAL_BLOCKS){
      _insertAfter(event, Blocks.HONEYCOMB_BLOCK, ACTIVE_HONEYCOMB_BLOCK, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
    else if(groupKey == CreativeModeTabs.BUILDING_BLOCKS){
      _insertAfter(event, Blocks.IRON_BLOCK, IRON_GRATE, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
      _insertAllAfter(event, Blocks.IRON_CHAIN, Arrays.stream(new Item[] {
        EXPOSED_IRON,
        EXPOSED_IRON_GRATE,
        EXPOSED_IRON_BARS,
        EXPOSED_IRON_DOOR,
        EXPOSED_IRON_TRAPDOOR,
        EXPOSED_IRON_CHAIN,
        WEATHERED_IRON,
        WEATHERED_IRON_GRATE,
        WEATHERED_IRON_BARS,
        WEATHERED_IRON_DOOR,
        WEATHERED_IRON_TRAPDOOR,
        WEATHERED_IRON_CHAIN,
        OXIDIZED_IRON,
        OXIDIZED_IRON_GRATE,
        OXIDIZED_IRON_BARS,
        OXIDIZED_IRON_DOOR,
        OXIDIZED_IRON_TRAPDOOR,
        OXIDIZED_IRON_CHAIN,
        COATED_IRON_BLOCK,
        COATED_IRON_GRATE,
        COATED_IRON_BARS,
        COATED_IRON_DOOR,
        COATED_IRON_TRAPDOOR,
        COATED_IRON_CHAIN,
        COATED_EXPOSED_IRON,
        COATED_EXPOSED_IRON_GRATE,
        COATED_EXPOSED_IRON_BARS,
        COATED_EXPOSED_IRON_DOOR,
        COATED_EXPOSED_IRON_TRAPDOOR,
        COATED_EXPOSED_IRON_CHAIN,
        COATED_WEATHERED_IRON,
        COATED_WEATHERED_IRON_GRATE,
        COATED_WEATHERED_IRON_BARS,
        COATED_WEATHERED_IRON_DOOR,
        COATED_WEATHERED_IRON_TRAPDOOR,
        COATED_WEATHERED_IRON_CHAIN,
        COATED_OXIDIZED_IRON,
        COATED_OXIDIZED_IRON_GRATE,
        COATED_OXIDIZED_IRON_BARS,
        COATED_OXIDIZED_IRON_DOOR,
        COATED_OXIDIZED_IRON_TRAPDOOR,
        COATED_OXIDIZED_IRON_CHAIN
      }).toList().reversed(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
    else if(groupKey == CreativeModeTabs.REDSTONE_BLOCKS){
      _insertAllAfter(event, Blocks.IRON_DOOR, Arrays.stream(new Item[] {
        EXPOSED_IRON_DOOR,
        WEATHERED_IRON_DOOR,
        OXIDIZED_IRON_DOOR,
        COATED_IRON_DOOR,
        COATED_EXPOSED_IRON_DOOR,
        COATED_WEATHERED_IRON_DOOR,
        COATED_OXIDIZED_IRON_DOOR,
      }).toList().reversed(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
      _insertAllAfter(event, Blocks.IRON_TRAPDOOR, Arrays.stream(new Item[] {
        EXPOSED_IRON_TRAPDOOR,
        WEATHERED_IRON_TRAPDOOR,
        OXIDIZED_IRON_TRAPDOOR,
        COATED_IRON_TRAPDOOR,
        COATED_EXPOSED_IRON_TRAPDOOR,
        COATED_WEATHERED_IRON_TRAPDOOR,
        COATED_OXIDIZED_IRON_TRAPDOOR
      }).toList().reversed(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
    else if(groupKey == CreativeModeTabs.FUNCTIONAL_BLOCKS){
      _insertAllAfter(event, Blocks.IRON_CHAIN, Arrays.stream(new Item[] {
        EXPOSED_IRON_CHAIN,
        WEATHERED_IRON_CHAIN,
        OXIDIZED_IRON_CHAIN,
        COATED_IRON_CHAIN,
        COATED_EXPOSED_IRON_CHAIN,
        COATED_WEATHERED_IRON_CHAIN,
        COATED_OXIDIZED_IRON_CHAIN
      }).toList().reversed(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
  }
}
