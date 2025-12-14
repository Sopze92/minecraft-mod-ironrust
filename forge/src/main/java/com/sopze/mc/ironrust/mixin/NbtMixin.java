package com.sopze.mc.ironrust.mixin;

import com.sopze.mc.ironrust.Logger;
import com.sopze.mc.ironrust.NbtHelper;
import com.sopze.mc.ironrust.WipeUtil.ThreadTrackerUtil;
import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static com.sopze.mc.ironrust.Constants.*;

public class NbtMixin {

  @Mixin(CompoundTag.class)
  public static class CompoundTagMixin {

    // BLOCK PALETTES

    @Inject( method = "<init>(Ljava/util/Map;)V", at = @At( value = "TAIL"))
    private void h_ctor(Map<String, Tag> entries, CallbackInfo ci){
      if(entries == null || entries.isEmpty()) return;

      if(ThreadTrackerUtil.getThreadFileName() instanceof String file && file.equals("level.dat")){

        int[] changes= {0,0};
        if(entries.get("DataPacks") instanceof CompoundTag la) changes[0]= NbtHelper.processDataPackNbt(la);
        if(entries.get("WorldGenSettings") instanceof CompoundTag la) changes[1]= NbtHelper.processWorldGenSettingsNbt(la);

        if(changes[0] + changes[1] > 0) Logger.log(LOC_NBTMIXIN_CTOR, INF_NBTLEVEL, ThreadTrackerUtil.get().info(), changes[0] + changes[1], changes[0], changes[1]);
      }
      else if(entries.get("sections") instanceof ListTag la) { // chunk
/*
        String k= "SECTIONS";
        System.out.println(la);
        int x=0;
*/
        int changes= 0;
        changes= NbtHelper.processChunkSectionsNbt(la);

        if(changes > 0) Logger.log(LOC_NBTMIXIN_CTOR, INF_NBTSECTIONS, ThreadTrackerUtil.get().info(), changes);
      }
      else if(entries.containsKey("Pos")) { // entity
/*
        String k= "ENTITY";
        System.out.println(entries);
        int x=0;
*/
        StringTag ida= (entries.get("id") instanceof StringTag idb) ? idb : null;
        String id = ida != null ? ida.value() : "error:unknown_id";

        if(entries.get("Item") instanceof CompoundTag la) { // single item

          int changes= 0;
          changes= NbtHelper.processItemNbt(la, id);

          if(changes > 0) Logger.log(LOC_NBTMIXIN_CTOR, INF_NBTITEM, ThreadTrackerUtil.get().info(), id);
        }
        else {

          int[] changes= {0,0,0,0};
          if(entries.containsKey("Brain")){ // mobs | player

            boolean player= entries.containsKey("playerGameType");

            if(player) id= String.format("player:%s", entries.get("UUID") instanceof IntArrayTag la ? NbtHelper.PlayerIdString(la.getAsIntArray()) : "unknown_UUID");

            if(entries.get("equipment") instanceof CompoundTag la) changes[0]= NbtHelper.processEquipmentNbt(la, id);
            if(entries.get("Inventory") instanceof ListTag la) changes[1]= NbtHelper.processContainerNbt(la, "inventory", id);

            if(player) {
              if(entries.get("EnderItems") instanceof ListTag la) changes[2]= NbtHelper.processContainerNbt(la, "ender-items", id);
              if(entries.get("recipeBook") instanceof CompoundTag la) changes[2]= NbtHelper.processRecipeBookNbt(la, id);
              if(changes[0] + changes[1] + changes[2] > 0)  Logger.log(LOC_NBTMIXIN_CTOR, INF_NBTPLAYER, ThreadTrackerUtil.get().info(), changes[0] + changes[1] + changes[2], id, changes[0], changes[1], changes[2]);
            }
            else if(changes[0] + changes[1] > 0) Logger.log(LOC_NBTMIXIN_CTOR, INF_NBTMOB, ThreadTrackerUtil.get().info(), changes[0] + changes[1], id, changes[0], changes[1]);
          }
          else{ // entities with containers

            if(entries.get("Items") instanceof ListTag la) changes[3]= NbtHelper.processContainerNbt(la, "items", id); // entities with containers
            if(changes[3] > 0) Logger.log(LOC_NBTMIXIN_CTOR, INF_NBTENTITY, ThreadTrackerUtil.get().info(), changes[3], id);
          }
        }
      }
      else if(entries.get("Items") instanceof ListTag la) { // block entities
/*
        String k= "BLOCK-ENTITY";
        System.out.println(entries);
        int x=0;
*/
        if(entries.get("id") instanceof StringTag ida){

          String id = ida.value();
          int changes= 0;

          changes= NbtHelper.processContainerNbt(la, "items", id);
          if(NbtHelper.processRawID(ida) instanceof StringTag dst){
            entries.put("id", dst);
            changes++;
          }
          if(changes > 0) Logger.log(LOC_NBTMIXIN_CTOR, INF_NBTBLOCKENTITY, ThreadTrackerUtil.get().info(), changes, id);
        }
      }
/*
      String[] tester= new String[]{
        "Pos", "__block_states", "__block_entities"
      };

      List<String> set= new ArrayList<String>();

      for(String k : entries.keySet()) for(String t : tester) if(k.equals(t)) set.add(k);

      if(!set.isEmpty()){
        ThreadTrackerUtil ttu= ThreadTrackerUtil.get();

        String k= "";
        for(int i=0; i< set.size()-1; i++) k+= set.get(i) + ", ";
        k+= set.getLast();

        Logger.slog("[%d:%s:%s %s%s] NbtMixin.NbtCompound.h_ctor() :: keys: '%s'", ttu.id(), ttu.task(), ttu.zone(), ttu.storage() ? 'L' : '_', ttu.chunk() ? 'C' : '_', k);
        Logger.slog(" --> at '%s'", ttu.path());

        var stackTrace= Thread.currentThread().getStackTrace();

        boolean player= entries.containsKey("playerGameType");
        var id= entries.get("id");

        int i=0;
      }*/
    }
/*
    // BLOCK PALETTES
    @Inject( method = "getCompound", at = @At( value = "RETURN", ordinal=0 ))
    private void h_getCompound(String key, CallbackInfoReturnable<Optional<NbtCompound>> cir) {

      if(!TAGS_GETCOMPOUND.contains(key)) TAGS_GETCOMPOUND.add(key);

      if(!ThreadTrackerUtil.isThreadInChunk() || key == null || key.length()!=12 || key.charAt(6)!=115) return; // s
      try{
        if(!(cir.getReturnValue().orElse(null) instanceof NbtCompound result) || result.isEmpty()) return;
        if(!(result.getList("palette").orElse(null) instanceof ListTag palette) || palette.isEmpty()) return;
        NbtHelper.processPaletteNbt(palette);
      }
      catch(Exception e){ Logger.logErr(LOC_NBTMIXIN_GETCOMPOUND, ERR_PALETTE_UNKNWOWN); }
    }

    // OPTIONAL BRIDGE
    @Inject( method = "getOptional", at = @At( value = "RETURN", ordinal=0 ))
    private void h_getOptional(String key, CallbackInfoReturnable<Optional<NbtElement>> cir) {
      String keylower= key == null ? "": key.toLowerCase();
      if(!keylower.equals("entities") && !keylower.equals("inventory") && !keylower.equals("__items")) return;
      if(cir.getReturnValue().orElse(null) instanceof NbtElement e) h_get(key, new CallbackInfoReturnable<NbtElement>("_IRWB", false, e));
    }

    // TESTING -- GET
    @Inject( method = "get(Ljava/lang/String;)Lnet/minecraft/nbt/NbtElement;", at = @At( value = "RETURN", ordinal=0 ))
    private void h_get(String key, CallbackInfoReturnable<NbtElement> cir) {
      if(key == null) return;
      if(!TAGS_GET.contains(key)) TAGS_GET.add(key);
      //if(name.length() == 19 && name.charAt(5) == 121)

      String keylower= key.toLowerCase();
      if(keylower.equals("entities") || keylower.equals("passengers") || keylower.equals("inventory") || keylower.equals("item") || keylower.equals("items")){
        if(cir.getReturnValue() != null){
          String mode= "h_get";
          var stackTrace= Thread.currentThread().getStackTrace();
          //Thread.dumpStack();
          ThreadTrackerUtil ttu= ThreadTrackerUtil.get();
          Logger.slog("[%d:%s:%s %s] NbtMixin..h_get() :: key: '%s'", ttu.id(), ttu.task(), ttu.zone(), ttu.chunk() ? 'C' : '_', key);
          System.out.flush();
          System.out.print("");
        }
      }

      if(ThreadTrackerUtil.isThreadInZone("entity-deserializer")){
        if(key == null || key.length() != 8 || key.charAt(1)!=110) return; // n

        try {
          if (!(cir.getReturnValue() instanceof ListTag la) || la.isEmpty() || !(la.getFirst() instanceof NbtCompound nbt && nbt.get(NBT_ID) instanceof StringTag)) return;
          NbtHelper.processEntitiesNbt(la);
        }
        catch(Exception e){ Logger.logErr(LOC_NBTMIXIN_GET, ERR_ENTITYWORKER_UNKNNOWN); }
      }
    }

    // ENTITIES / BLOCKENTITIES
    @Inject( method = "getList", at = @At( value = "RETURN", ordinal=0 ))
    private void h_getList(String key, CallbackInfoReturnable<Optional<ListTag>> cir) {

      if(!TAGS_GETLIST.contains(key)) TAGS_GETLIST.add(key);

      if(!ThreadTrackerUtil.isThreadInChunk() || key == null || key.length() < 8) return;

      char c= key.charAt(1);
      if(c!=110 && c!=108) return; // n && l

      try{
        if (!(cir.getReturnValue().orElse(null) instanceof ListTag la) || la.isEmpty() || !(la.getFirst() instanceof NbtCompound nbt && nbt.get(NBT_ID) instanceof StringTag)) return;

        if(c == 110) NbtHelper.processEntitiesNbt(la);
        else NbtHelper.processBlockEntitiesNbt(la);
      }
      catch(Exception e){ Logger.logErr(LOC_NBTMIXIN_GETLIST, ERR_ListTag_UNKNWOWN, key); }
    }
    */
  }
}