package com.sopze.mc.ironrust;

public class Constants {

  public static final String MOD_ID_CONST= "ironrustwipe";

  public static final String
    MOD_ID, LOG_ID, TARGET_ID, TARGET_NAME,
    SETTINGS_PATH,
    REPLACEMENT_TYPE,
    INF_REGISTERED,
    JSON_VERSION, JSON_DATA, JSON_META, JSON_DST, JSON_SRC,
    INF_NBTLEVEL, INF_NBTITEM, INF_NBTPLAYER, INF_NBTMOB, INF_NBTENTITY, INF_NBTBLOCKENTITY, INF_NBTSECTIONS,
    LOC_MAIN,
    LOC_REGISTRY_INITIALIZE,
    LOC_REGISTER_REPLACEMENT,
    _LOC_NBTMIXIN,
    LOC_NBTMIXIN_CTOR, LOC_NBTMIXIN_DATAPACK, LOC_NBTMIXIN_WORLDGEN, LOC_NBTMIXIN_SECTION,
    LOC_NBTMIXIN_EQUIPMENT, LOC_NBTMIXIN_RECIPEBOOK, LOC_NBTMIXIN_CONTAINER, LOC_NBTMIXIN_ITEM, LOC_NBTMIXIN_SUBCONTAINER,
    WRN_UNMARKED_BLOCK,
    WRN_EMPTY_SETTINGS,
    WRN_SINGLEPLAYER_ONLY,
    WRN_SERVERCLEANUP_HELP0, WRN_SERVERCLEANUP_HELP1, WRN_SERVERCLEANUP_HELP2, WRN_SERVERCLEANUP_HELP3,
    ERR_UNKNOWN,
    ERR_IRONRUST_CONFLICT,
    ERR_VERSION_MALFORMED,
    ERR_VERSION_PARSING,
    ERR_MALFORMED_JSON,
    ERR_EMPTY_JSON,
    ERR_UNREADABLE_FILE,
    ERR_INVALID_OBJECT,
    ERR_MISSING_KEY,
    ERR_INVALID_KEY,
    ERR_EMPTY_BLOCK_SRC,
    ERR_INVALID_SRC_ELEMENT,
    ERR_INVALID_SRC,
    ERR_REGISTER_KEYNOTFOUND,
    _ERR_UNEXPECTED_PARSE,
    ERR_DATAPACK_ENTRIES_INVALID, ERR_LAYER_ENTRY_INVALID, ERR_PALETTE_ENTRY_INVALID,
    ERR_RECIPES_ENTRIES_INVALID, ERR_EQUIPMENT_ENTRY_INVALID, ERR_CONTAINER_ENTRY_INVALID, ERR_ITEM_INVALID, ERR_SUBCONTAINER_ENTRY_INVALID;

  static{
    MOD_ID = "ironrustwipe"; LOG_ID = "IronRust-Wiper"; TARGET_ID = "ironrust"; TARGET_NAME="IronRust";
    SETTINGS_PATH= "config/cleanup_settings.json";

    REPLACEMENT_TYPE= "replacement";
    INF_REGISTERED= "Registered %d replacements (%d block, %d item)";
    JSON_VERSION="version"; JSON_DATA= "data"; JSON_META= "meta"; JSON_DST= "dst"; JSON_SRC= "src";

    INF_NBTLEVEL= "%s %d changes in level settings (%d datapacks, %d worldgen)";
    INF_NBTITEM= "%s item '%s' changed";
    INF_NBTPLAYER= "%s %d changes in player '%s' (%d equipment, %d inventory, %d recipe book)";
    INF_NBTMOB= "%s %d changes in mob '%s' (%d equipment, %d inventory)";
    INF_NBTENTITY= "%s %d changes in entity '%s'";
    INF_NBTBLOCKENTITY= "%s %d changes in block-entity '%s'";
    INF_NBTSECTIONS= "%s %d changes in section palettes";

    LOC_MAIN= "onInitialize";
    LOC_REGISTRY_INITIALIZE = "Registry.initialize()";
    LOC_REGISTER_REPLACEMENT = "ModItems._registerReplacement()";
    _LOC_NBTMIXIN = "NbtMixin.h_ctor()";

    LOC_NBTMIXIN_CTOR = _LOC_NBTMIXIN + ".h_ctor()";
    LOC_NBTMIXIN_DATAPACK = _LOC_NBTMIXIN + "..processDataPackNbt()";
    LOC_NBTMIXIN_WORLDGEN = _LOC_NBTMIXIN + "..processWorldGenSettingsNbt()";
    LOC_NBTMIXIN_SECTION = _LOC_NBTMIXIN + "..processChunkSectionsNbt()";
    LOC_NBTMIXIN_EQUIPMENT = _LOC_NBTMIXIN + "..processEquipmentNbt()";
    LOC_NBTMIXIN_RECIPEBOOK = _LOC_NBTMIXIN + "..processRecipeBookNbt()";
    LOC_NBTMIXIN_CONTAINER = _LOC_NBTMIXIN + "..processContainerNbt()";
    LOC_NBTMIXIN_ITEM= _LOC_NBTMIXIN + "..processItemNbt()";
    LOC_NBTMIXIN_SUBCONTAINER = _LOC_NBTMIXIN + "..processSubContainerNbt()";

    WRN_UNMARKED_BLOCK= "JSON object %d:%s is not marked for Block nor Item and will be skipped%n";
    WRN_EMPTY_SETTINGS= "Settings file resulted in an empty replacement registry";
    WRN_SINGLEPLAYER_ONLY= "Detected non-local game. If you're trying to cleanup a World, that can only be done in single-player. If you're just connecting to a server to play, then ignore this message.";
    WRN_SERVERCLEANUP_HELP0= "If you want to perform a cleanup on your dedicated-server world:";
    WRN_SERVERCLEANUP_HELP1= "  - Drop the server's 'world' folder into your 'Minecraft/saves' folder (rename if needed) then open it in Singleplayer";
    WRN_SERVERCLEANUP_HELP2= "  - Perform the cleanup tasks as usual";
    WRN_SERVERCLEANUP_HELP3= "  - Move your 'world' folder back to its original place and name (if renamed)";

    ERR_UNKNOWN= "Unknown error";
    ERR_IRONRUST_CONFLICT= "Skipping initialization: Please delete the IronRust mod file first for the IronRust Uninstaller to work.";
    ERR_VERSION_MALFORMED= "Malformed mod version string: %s";
    ERR_VERSION_PARSING= "Error while parsing mod version digit[%d]: %s";
    ERR_MALFORMED_JSON= "Malformed cleanup settings";
    ERR_EMPTY_JSON= "Empty cleanup settings";
    ERR_UNREADABLE_FILE= "Unable to read settings file";
    ERR_INVALID_OBJECT= "Invalid JSON object[%d]%n";
    ERR_MISSING_KEY= "Missing required key '%s' in JSON object[%d] %n";
    ERR_INVALID_KEY= "Invalid value type for key '%s' in JSON object[%d], must be '%s'%n";
    ERR_EMPTY_BLOCK_SRC= "Empty 'src' list in JSON object[%d]%n";
    ERR_INVALID_SRC_ELEMENT= "Invalid value type in JSON object[%d].src[%d], must be '%s'%n";
    ERR_INVALID_SRC= "Error reading 'src' list element at JSON object[%d].src[%d]%n";
    ERR_REGISTER_KEYNOTFOUND= "Couldn't register BlockItem '%s': registry key not found, Item must be registered after the Block";

    _ERR_UNEXPECTED_PARSE= "%s Unexpected error parsing ";

    ERR_DATAPACK_ENTRIES_INVALID= _ERR_UNEXPECTED_PARSE + "world datapack entries";
    ERR_LAYER_ENTRY_INVALID= _ERR_UNEXPECTED_PARSE + "dimension '%s' layer[%d]: %s";
    ERR_PALETTE_ENTRY_INVALID= _ERR_UNEXPECTED_PARSE + "chunk (%s) section[%d] palette entry[%d], value: %s";
    ERR_EQUIPMENT_ENTRY_INVALID= _ERR_UNEXPECTED_PARSE + "entity '%s' equipment slot '%s', value: %s";
    ERR_RECIPES_ENTRIES_INVALID= _ERR_UNEXPECTED_PARSE + "entity '%s' recipe book entries";
    ERR_CONTAINER_ENTRY_INVALID= _ERR_UNEXPECTED_PARSE + "entity '%s' container '%s' entry[%d], value: %s";
    ERR_ITEM_INVALID= _ERR_UNEXPECTED_PARSE + "dropped item entity '%s', value: %s";
    ERR_SUBCONTAINER_ENTRY_INVALID= _ERR_UNEXPECTED_PARSE + "entity '%s' item sub-container entry[%d], value: %s";
  }

}
