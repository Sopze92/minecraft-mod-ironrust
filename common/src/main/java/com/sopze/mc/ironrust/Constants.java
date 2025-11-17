package com.sopze.mc.ironrust;

public class Constants {

  public static final String MOD_ID_CONST= "ironrust";

  public static final String
    MOD_ID, LOG_ID, WIPER_ID,
    LOC_MAIN,
    LOC_REGISTRY_OVERRIDE,
    ERR_VERSION_MALFORMED,
    ERR_VERSION_PARSING,
    WRN_IRONRUST_CONFLICT,
    OVERRIDE_BLOCK,
    CHECKING_CLIENT_MOD,
    VERSION_CHECK_INFO, VERSION_CHECK_MESSAGE, VERSION_CHECK_SUCCEED, VERSION_CHECK_FAIL;

  static{
    MOD_ID = "ironrust"; LOG_ID = "IronRust"; WIPER_ID = "ironrustwipe";

    LOC_MAIN= "onInitialize()";
    LOC_REGISTRY_OVERRIDE= "r_register";

    ERR_VERSION_MALFORMED= "Malformed mod version string: %s";
    ERR_VERSION_PARSING= "Error while parsing mod version digit[%d]: %s";
    WRN_IRONRUST_CONFLICT= "IronRust mod file should be removed for the IronRust Uninstaller to work. From now, iron will NOT display on creative nor oxidize further, but in order to restore your vanilla blocks and items, keep only the Uninstaller while playing.";

    OVERRIDE_BLOCK= "Overriding vanilla block: %s";
    CHECKING_CLIENT_MOD= "Checking mod version parity with %s";
    VERSION_CHECK_INFO= "Server version= v%s | Local version= v%s";
    VERSION_CHECK_MESSAGE= "IronRust client-server versions %s"; VERSION_CHECK_SUCCEED= "compatible. Happy rusting!"; VERSION_CHECK_FAIL= "incompatible, mod disabled on client";
  }
}
