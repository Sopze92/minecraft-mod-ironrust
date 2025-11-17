package com.sopze.mc.ironrust.item;

import com.sopze.mc.ironrust.Main;
import com.sopze.mc.ironrust.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Function;

import static com.sopze.mc.ironrust.Constants.MOD_ID;

public class ModItems {

  private static final Item
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

  static{
    ACTIVE_HONEYCOMB = _register("active_honeycomb", ActiveHoneycombItem::new);
    // ITEM BLOCKS
    ACTIVE_HONEYCOMB_BLOCK = _register(ModBlocks.ACTIVE_HONEYCOMB_BLOCK);
    EXPOSED_IRON = _register(ModBlocks.EXPOSED_IRON);
    WEATHERED_IRON = _register(ModBlocks.WEATHERED_IRON);
    OXIDIZED_IRON = _register(ModBlocks.OXIDIZED_IRON);
    COATED_IRON_BLOCK = _register(ModBlocks.COATED_IRON_BLOCK);
    COATED_WEATHERED_IRON = _register(ModBlocks.COATED_WEATHERED_IRON);
    COATED_EXPOSED_IRON = _register(ModBlocks.COATED_EXPOSED_IRON);
    COATED_OXIDIZED_IRON = _register(ModBlocks.COATED_OXIDIZED_IRON);
    EXPOSED_IRON_DOOR = _register(ModBlocks.EXPOSED_IRON_DOOR);
    OXIDIZED_IRON_DOOR = _register(ModBlocks.OXIDIZED_IRON_DOOR);
    WEATHERED_IRON_DOOR = _register(ModBlocks.WEATHERED_IRON_DOOR);
    COATED_IRON_DOOR = _register(ModBlocks.COATED_IRON_DOOR);
    COATED_EXPOSED_IRON_DOOR = _register(ModBlocks.COATED_EXPOSED_IRON_DOOR);
    COATED_OXIDIZED_IRON_DOOR = _register(ModBlocks.COATED_OXIDIZED_IRON_DOOR);
    COATED_WEATHERED_IRON_DOOR = _register(ModBlocks.COATED_WEATHERED_IRON_DOOR);
    EXPOSED_IRON_TRAPDOOR = _register(ModBlocks.EXPOSED_IRON_TRAPDOOR);
    OXIDIZED_IRON_TRAPDOOR = _register(ModBlocks.OXIDIZED_IRON_TRAPDOOR);
    WEATHERED_IRON_TRAPDOOR = _register(ModBlocks.WEATHERED_IRON_TRAPDOOR);
    COATED_IRON_TRAPDOOR = _register(ModBlocks.COATED_IRON_TRAPDOOR);
    COATED_EXPOSED_IRON_TRAPDOOR = _register(ModBlocks.COATED_EXPOSED_IRON_TRAPDOOR);
    COATED_OXIDIZED_IRON_TRAPDOOR = _register(ModBlocks.COATED_OXIDIZED_IRON_TRAPDOOR);
    COATED_WEATHERED_IRON_TRAPDOOR = _register(ModBlocks.COATED_WEATHERED_IRON_TRAPDOOR);
    IRON_GRATE = _register(ModBlocks.IRON_GRATE);
    EXPOSED_IRON_GRATE = _register(ModBlocks.EXPOSED_IRON_GRATE);
    WEATHERED_IRON_GRATE = _register(ModBlocks.WEATHERED_IRON_GRATE);
    OXIDIZED_IRON_GRATE = _register(ModBlocks.OXIDIZED_IRON_GRATE);
    COATED_IRON_GRATE = _register(ModBlocks.COATED_IRON_GRATE);
    COATED_EXPOSED_IRON_GRATE = _register(ModBlocks.COATED_EXPOSED_IRON_GRATE);
    COATED_WEATHERED_IRON_GRATE = _register(ModBlocks.COATED_WEATHERED_IRON_GRATE);
    COATED_OXIDIZED_IRON_GRATE = _register(ModBlocks.COATED_OXIDIZED_IRON_GRATE);
    EXPOSED_IRON_BARS = _register(ModBlocks.EXPOSED_IRON_BARS);
    WEATHERED_IRON_BARS = _register(ModBlocks.WEATHERED_IRON_BARS);
    OXIDIZED_IRON_BARS = _register(ModBlocks.OXIDIZED_IRON_BARS);
    COATED_IRON_BARS = _register(ModBlocks.COATED_IRON_BARS);
    COATED_EXPOSED_IRON_BARS = _register(ModBlocks.COATED_EXPOSED_IRON_BARS);
    COATED_WEATHERED_IRON_BARS = _register(ModBlocks.COATED_WEATHERED_IRON_BARS);
    COATED_OXIDIZED_IRON_BARS = _register(ModBlocks.COATED_OXIDIZED_IRON_BARS);
    EXPOSED_IRON_CHAIN = _register(ModBlocks.EXPOSED_IRON_CHAIN);
    WEATHERED_IRON_CHAIN = _register(ModBlocks.WEATHERED_IRON_CHAIN);
    OXIDIZED_IRON_CHAIN = _register(ModBlocks.OXIDIZED_IRON_CHAIN);
    COATED_IRON_CHAIN = _register(ModBlocks.COATED_IRON_CHAIN);
    COATED_EXPOSED_IRON_CHAIN = _register(ModBlocks.COATED_EXPOSED_IRON_CHAIN);
    COATED_WEATHERED_IRON_CHAIN = _register(ModBlocks.COATED_WEATHERED_IRON_CHAIN);
    COATED_OXIDIZED_IRON_CHAIN = _register(ModBlocks.COATED_OXIDIZED_IRON_CHAIN);
  }

  private static Item _register(String id, Function<Item.Properties, Item> factory) { return Items.registerItem(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, id)), factory); }
  private static Item _register(Block block){
    return Items.registerBlock(block);
  }

  public static void initialize(){

    ItemGroupEvents.MODIFY_ENTRIES_ALL.register(ModItems::_onModifyItemGroupEntriesEvent);

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
  }

  private static void _onModifyItemGroupEntriesEvent(CreativeModeTab group, FabricItemGroupEntries entries){
    if(!Main.isEnabledLocally() || !(BuiltInRegistries.CREATIVE_MODE_TAB.wrapAsHolder(group).unwrapKey().orElse(null) instanceof ResourceKey<CreativeModeTab> groupKey)) return;

    if(groupKey == CreativeModeTabs.INGREDIENTS){
      entries.addAfter(Items.HONEYCOMB, ACTIVE_HONEYCOMB);
    }
    else if(groupKey == CreativeModeTabs.NATURAL_BLOCKS){
      entries.addAfter(Blocks.HONEYCOMB_BLOCK, ACTIVE_HONEYCOMB_BLOCK);
    }
    else if(groupKey == CreativeModeTabs.BUILDING_BLOCKS){
      entries.addAfter(Blocks.IRON_BLOCK, ModBlocks.IRON_GRATE);
      entries.addAfter(Blocks.IRON_CHAIN, new Item[] {
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
      });
    }
    else if(groupKey == CreativeModeTabs.REDSTONE_BLOCKS){
      entries.addAfter(Blocks.IRON_DOOR, new Item[] {
        EXPOSED_IRON_DOOR,
        WEATHERED_IRON_DOOR,
        OXIDIZED_IRON_DOOR,
        COATED_IRON_DOOR,
        COATED_EXPOSED_IRON_DOOR,
        COATED_WEATHERED_IRON_DOOR,
        COATED_OXIDIZED_IRON_DOOR
      });
      entries.addAfter(Blocks.IRON_TRAPDOOR, new Item[] {
        EXPOSED_IRON_TRAPDOOR,
        WEATHERED_IRON_TRAPDOOR,
        OXIDIZED_IRON_TRAPDOOR,
        COATED_IRON_TRAPDOOR,
        COATED_EXPOSED_IRON_TRAPDOOR,
        COATED_WEATHERED_IRON_TRAPDOOR,
        COATED_OXIDIZED_IRON_TRAPDOOR
      });
    }
    else if(groupKey == CreativeModeTabs.FUNCTIONAL_BLOCKS){
      entries.addAfter(Blocks.IRON_CHAIN, new Item[] {
        EXPOSED_IRON_CHAIN,
        WEATHERED_IRON_CHAIN,
        OXIDIZED_IRON_CHAIN,
        COATED_IRON_CHAIN,
        COATED_EXPOSED_IRON_CHAIN,
        COATED_WEATHERED_IRON_CHAIN,
        COATED_OXIDIZED_IRON_CHAIN
      });
    }
  }
}
