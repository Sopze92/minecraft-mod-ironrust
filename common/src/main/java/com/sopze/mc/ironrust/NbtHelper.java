package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.WipeUtil.ThreadTrackerUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

import static com.sopze.mc.ironrust.Constants.*;

public class NbtHelper {

  private static final String
    TAG_ID, TAG_NAME, TAG_BLOCK,
    WDP_TAG_DISABLED, WDP_TAG_ENABLED,
    WGS_TAG_GENERATOR, WGS_TAG_SETTINGS, WGS_TAG_LAYERS, WGS_TAG_DIMENSIONS,
    RFS_TAG_BLOCKSTATES, RFS_TAG_DATA, RFS_TAG_PALETTE,
    ERB_TAG_RECIPES, ERB_TAG_TOBEDISPLAYED,
    IEC_TAG_COMPONENTS, IEC_TAG_CONTAINER_COMPONENT, IEC_TAG_ITEM;

  static{
    TAG_ID= "id"; TAG_NAME= "Name"; TAG_BLOCK= "block";
    WDP_TAG_DISABLED = "Disabled"; WDP_TAG_ENABLED = "Enabled";
    WGS_TAG_GENERATOR= "generator"; WGS_TAG_SETTINGS= "settings"; WGS_TAG_LAYERS= "layers"; WGS_TAG_DIMENSIONS= "dimensions";
    RFS_TAG_BLOCKSTATES = "block_states"; RFS_TAG_DATA = "data"; RFS_TAG_PALETTE = "palette";
    ERB_TAG_RECIPES= "recipes"; ERB_TAG_TOBEDISPLAYED= "toBeDisplayed";
    IEC_TAG_COMPONENTS= "components"; IEC_TAG_CONTAINER_COMPONENT= "minecraft:container"; IEC_TAG_ITEM= "item";
  }

  public static int processDataPackNbt(CompoundTag nbt) {
    int changes= 0;
    try {
      if(nbt.get(WDP_TAG_DISABLED) instanceof ListTag lb && !lb.isEmpty()) if(_processDataPackListNbt(lb)) changes++;;
      if(nbt.get(WDP_TAG_ENABLED) instanceof ListTag lb && !lb.isEmpty()) if(_processDataPackListNbt(lb)) changes++;;
    }
    catch (Exception e) {
      Logger.logErr(LOC_NBTMIXIN_WORLDGEN, ERR_DATAPACK_ENTRIES_INVALID, ThreadTrackerUtil.get().info());
    }
    return changes;
  }

  public static int processWorldGenSettingsNbt(CompoundTag nbt){
    int changes= 0;
    if(nbt.get(WGS_TAG_DIMENSIONS) instanceof CompoundTag lz){
      for(String ka : lz.keySet()){
        if(!(nbt.get(ka) instanceof CompoundTag la) || !(la.get(WGS_TAG_GENERATOR) instanceof CompoundTag lb) || !(lb.get(WGS_TAG_SETTINGS) instanceof CompoundTag lc) || !(lc.get(WGS_TAG_LAYERS) instanceof ListTag ld && !ld.isEmpty())) continue;
        for (int i=0 ; i< ld.size(); i++) {
          try {
            if (ld.get(i) instanceof CompoundTag le && le.get(TAG_BLOCK) instanceof StringTag(String src) && Registry.getDstBlock(src) instanceof String dst) {
              le.put(TAG_BLOCK, StringTag.valueOf(dst));
              changes++;
            }
          }
          catch (Exception e) {
            Logger.logErr(LOC_NBTMIXIN_WORLDGEN, ERR_LAYER_ENTRY_INVALID, ThreadTrackerUtil.get().info(), ka, i, ld.get(i));
          }
        }
      }
    }
    return changes;
  }

  public static int processChunkSectionsNbt(ListTag nbt){
    int changes= 0;
    CompoundTag replacement;
    for(int i=0; i< nbt.size(); i++){
      if(nbt.get(i) instanceof CompoundTag la && la.get(RFS_TAG_BLOCKSTATES) instanceof CompoundTag lb && lb.get(RFS_TAG_DATA) != null && lb.get(RFS_TAG_PALETTE) instanceof ListTag lc && !lc.isEmpty()){
        for (short j = 0, k = 0; j < lc.size(); j++, k++) {
          try {
            if (lc.get(j) instanceof CompoundTag le && le.get(TAG_NAME) instanceof StringTag(String src) && Registry.getDstBlock(src) instanceof String dst) {
              le.put(TAG_NAME, StringTag.valueOf(dst));
              changes++;
            }
          }
          catch (Exception e) {
            ThreadTrackerUtil ttu= ThreadTrackerUtil.get();
            Logger.logErr(LOC_NBTMIXIN_SECTION, ERR_PALETTE_ENTRY_INVALID, ttu.info(), i, j, ttu.chunkString(), lc.get(j));
          }
        }
      }
    }

    return changes;
  }

  public static int processEquipmentNbt(CompoundTag nbt, String id) {
    int changes= 0;
    for(String ka : nbt.keySet()) {
      try {
        if (nbt.get(ka) instanceof CompoundTag le) {
          if(le.get(TAG_ID) instanceof StringTag(String src) && Registry.getDstItem(src) instanceof String dst) {
            le.put(TAG_ID, StringTag.valueOf(dst));
            changes++;
          }
          if(le.get(IEC_TAG_COMPONENTS) instanceof CompoundTag la && la.get(IEC_TAG_CONTAINER_COMPONENT) instanceof ListTag lb && !lb.isEmpty()) {
            changes+= _processSubContainerNbt(lb, id);
          }
        }
      }
      catch (Exception e) {
        ThreadTrackerUtil ttu= ThreadTrackerUtil.get();
        Logger.logErr(LOC_NBTMIXIN_EQUIPMENT, ERR_EQUIPMENT_ENTRY_INVALID, ttu.info(), id, ka, nbt.get(ka));
      }
    }
    return changes;
  }

  public static int processRecipeBookNbt(CompoundTag nbt, String id) {
    int changes= 0;
    try{
      String namespace= TARGET_ID + ":";
      if(nbt.get(ERB_TAG_RECIPES) instanceof ListTag la && !la.isEmpty()) changes+= _processSimpleNamespaceWipeNbt(la, namespace);
      if(nbt.get(ERB_TAG_TOBEDISPLAYED) instanceof ListTag la && !la.isEmpty()) changes+= _processSimpleNamespaceWipeNbt(la, namespace);
    }
    catch (Exception e) {
      Logger.logErr(LOC_NBTMIXIN_RECIPEBOOK, ERR_RECIPES_ENTRIES_INVALID, ThreadTrackerUtil.get().info(), id);
    }

    return changes;
  }

  public static int processContainerNbt(ListTag nbt, String id, String container){
    int changes= 0;
    for(int i=0; i< nbt.size(); i++) {
      try {
        if (nbt.get(i) instanceof CompoundTag le) {
          if (le.get(TAG_ID) instanceof StringTag(String src) && Registry.getDstItem(src) instanceof String dst) {
            le.put(TAG_ID, StringTag.valueOf(dst));
            changes++;
          }
          if(le.get(IEC_TAG_COMPONENTS) instanceof CompoundTag la && la.get(IEC_TAG_CONTAINER_COMPONENT) instanceof ListTag lb && !lb.isEmpty()) {
            changes+= _processSubContainerNbt(lb, id);
          }
        }
      }
      catch (Exception e) {
        Logger.logErr(LOC_NBTMIXIN_CONTAINER, ERR_CONTAINER_ENTRY_INVALID, ThreadTrackerUtil.get().info(), id, container, i, nbt.get(i));
      }
    }
    return changes;
  }

  public static int processItemNbt(CompoundTag nbt, String id) {
    int changes= 0;
    try {
      if (nbt.get(TAG_ID) instanceof StringTag(String src) && Registry.getDstItem(src) instanceof String dst) {
        nbt.put(TAG_ID, StringTag.valueOf(dst));
        changes++;
      }
      if(nbt.get(IEC_TAG_COMPONENTS) instanceof CompoundTag la && la.get(IEC_TAG_CONTAINER_COMPONENT) instanceof ListTag lb && !lb.isEmpty()) {
        changes+= _processSubContainerNbt(lb, id);
      }
    }
    catch (Exception e) {
      Logger.logErr(LOC_NBTMIXIN_ITEM, ERR_ITEM_INVALID, ThreadTrackerUtil.get().info(), id, nbt);
    }
    return changes;
  }

  private static int _processSubContainerNbt(ListTag nbt, String id){
    int changes= 0;
    for(int i=0; i< nbt.size(); i++) {
      try {
        if (nbt.get(i) instanceof CompoundTag le && le.get(IEC_TAG_ITEM) instanceof CompoundTag lf) {
          if(lf.get(TAG_ID) instanceof StringTag(String src) && Registry.getDstItem(src) instanceof String dst) {
            lf.put(TAG_ID, StringTag.valueOf(dst));
            changes++;
          }
          if(lf.get(IEC_TAG_COMPONENTS) instanceof CompoundTag la && la.get(IEC_TAG_CONTAINER_COMPONENT) instanceof ListTag lb && !lb.isEmpty()) {
            changes+= _processSubContainerNbt(lb, id);
          }
        }
      }
      catch (Exception e) {
        Logger.logErr(LOC_NBTMIXIN_SUBCONTAINER, ERR_SUBCONTAINER_ENTRY_INVALID, ThreadTrackerUtil.get().info(), id, i, nbt.get(i));
      }
    }
    return changes;
  }

  private static boolean _processDataPackListNbt(ListTag nbt){
    boolean changed= false;
    for(int i=0; i< nbt.size(); i++){
      if(nbt.get(i) instanceof StringTag(String lc) && lc.equals(TARGET_ID)) {
        nbt.remove(i);
        changed= true;
        break;
      }
    }
    return changed;
  }

  private static int _processSimpleNamespaceWipeNbt(ListTag nbt, String namespace){
    int changes= 0;
    for(int i=nbt.size()-1; i> 0; i--){
      if(nbt.get(i) instanceof StringTag(String lc) && lc.startsWith(namespace)) {
        nbt.remove(i);
        changes++;
      }
    }
    return changes;
  }

  public static StringTag processRawID(StringTag nbt) {
    return Registry.getDstItem(nbt.value()) instanceof String dst ? StringTag.valueOf(dst) : null;
  }

  public static String PlayerIdString(int[] a) {
    return String.format("%s%s%s%s", Integer.toHexString(a[0]), Integer.toHexString(a[1]), Integer.toHexString(a[2]), Integer.toHexString(a[3]));
  }
}
