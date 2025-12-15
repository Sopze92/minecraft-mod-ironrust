package com.sopze.mc.ironrust;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

import static com.sopze.mc.ironrust.Constants.*;
import static com.sopze.mc.ironrust.Main.ModRuntimeException;
import static com.sopze.mc.ironrust.WipeUtil.loadInternalJson;

public class Registry {

  private static final Map<String, String> _BLOCK_MAP = new HashMap<>();
  private static final Map<String, String> _ITEM_MAP = new HashMap<>();

  public static void initialize() {
    try {
      JsonObject settings = loadInternalJson(SETTINGS_PATH);
      int version = settings.getAsJsonPrimitive(JSON_VERSION).getAsInt();
      JsonArray data = settings.getAsJsonArray(JSON_DATA);
      if (data == null) throw new ModRuntimeException(ERR_MALFORMED_JSON);
      if (data.isEmpty()) throw new ModRuntimeException(ERR_EMPTY_JSON);

      _BLOCK_MAP.clear();
      _ITEM_MAP.clear();

      if (version == 1) _parse_version1(data);

    }catch (ModRuntimeException e) {Logger.logErr(LOC_REGISTRY_INITIALIZE, e.getMessage());}catch (Exception e) {
      Logger.slogErr(ERR_UNREADABLE_FILE);
      e.printStackTrace();
    }
  }

  private static void _parse_version1(JsonArray data){
    JsonElement temp;
    JsonObject block;
    JsonArray sources;
    String meta, dst, src;
    boolean isBlock, isItem;

    for(int i=0; i< data.size(); i++){
      try{

        temp = data.get(i);
        if(!temp.isJsonObject()){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_INVALID_OBJECT, i); continue; }

        block = temp.getAsJsonObject();
        if(!block.has(JSON_META)){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_MISSING_KEY, JSON_META, i); continue; }
        if(!block.has(JSON_DST)){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_MISSING_KEY, JSON_DST, i); continue; }
        if(!block.has(JSON_SRC)){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_MISSING_KEY, JSON_SRC, i); continue; }

        temp= block.get(JSON_DST);
        if(!temp.isJsonPrimitive()){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_INVALID_KEY, JSON_DST, i, "String"); continue; }
        dst= temp.getAsString();

        temp= block.get(JSON_META);
        if(!temp.isJsonPrimitive()){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_INVALID_KEY, JSON_DST, i, "String"); continue; }
        meta= temp.getAsString();
        isBlock= meta.contains("b");
        isItem= meta.contains("i");

        temp= block.get(JSON_SRC);
        if(!temp.isJsonArray()){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_INVALID_KEY, JSON_SRC, i, "String Array"); continue; }
        sources= block.get(JSON_SRC).getAsJsonArray();
        if(sources.isEmpty()) { Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_EMPTY_BLOCK_SRC, i); continue; }
        if(!sources.get(0).isJsonPrimitive()){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_INVALID_KEY, JSON_SRC, i, "String Array"); continue; }

        if(!isBlock && !isItem){ Logger.logWrn(LOC_REGISTRY_INITIALIZE, WRN_UNMARKED_BLOCK, i); continue; }

        for(int j=0; j < sources.size(); j++){
          try{
            temp= sources.get(j);
            if(!temp.isJsonPrimitive()){ Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_INVALID_SRC_ELEMENT, i, j, "String"); continue; }
            src= temp.getAsString();
            if(isBlock) _BLOCK_MAP.put(src, dst);
            if(isItem) _ITEM_MAP.put(src, dst);
            //IronRustWipe.slog("Registered replacement for dst: %s, src: %s (%s)", dst, src, String.format("%s%s", isBlock ? "B" : "_", isItem ? "I" : "_"));
          }
          catch (Exception e) { Logger.logErr(LOC_REGISTRY_INITIALIZE, ERR_INVALID_SRC, i, j); continue; }
        }
      }
      catch (Exception e) {
        Logger.slogErr(ERR_UNKNOWN);
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }

    /*
    for(String key : BLOCK_MAP.keySet()){
      System.out.printf("Registered block replacement: %s -> %s%n", key, REGISTRY.get(BLOCK_MAP.get(key)));
    }
    for(String key : ITEM_MAP.keySet()){
      System.out.printf("Registered item replacement: %s -> %s%n", key, REGISTRY.get(ITEM_MAP.get(key)));
    }*/

    if(_BLOCK_MAP.isEmpty() && _ITEM_MAP.isEmpty()) Logger.slogWrn(WRN_EMPTY_SETTINGS);
    else Logger.slog(INF_REGISTERED, _BLOCK_MAP.size()+ _ITEM_MAP.size(), _BLOCK_MAP.size(), _ITEM_MAP.size());
  }

  public static boolean anySrcBlock(String identifier){ return _BLOCK_MAP.containsKey(identifier); };
  public static boolean anySrcItem(String identifier){ return _ITEM_MAP.containsKey(identifier); };

  public static String getDstBlock(String src) { return _BLOCK_MAP.get(src); }
  public static String getDstItem(String src) { return _ITEM_MAP.get(src); }
}
