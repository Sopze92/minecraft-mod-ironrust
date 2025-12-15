package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.wrapper.I_LoaderWrapper;

import static com.sopze.mc.ironrust.Constants.*;

public class Main {

  private static I_LoaderWrapper _WRAPPER;

  public static String _LOCAL_VERSION_STRING;
  public static byte[] _LOCAL_VERSION;

  private static boolean
    _MOD_ENABLED_LOCALLY = false,
    MOD_TARGET_STATE= false;

	public static void initialize(I_LoaderWrapper wrapper) {
    _WRAPPER= wrapper;

    MOD_TARGET_STATE= _WRAPPER.isModLoaded(TARGET_ID);

    setEnabledLocally(!MOD_TARGET_STATE);

    if (MOD_TARGET_STATE) { Logger.logErr(LOC_MAIN, ERR_IRONRUST_CONFLICT); }
    else{ Registry.initialize(); }
	}


  public static boolean isEnabledLocally(){ return _MOD_ENABLED_LOCALLY; }
  public static void setEnabledLocally(boolean state){ _MOD_ENABLED_LOCALLY = state && !MOD_TARGET_STATE; }

  public static boolean isTargetModPresent(){ return MOD_TARGET_STATE; }

  public static String getLocalVersionString() { return _LOCAL_VERSION_STRING; }
  public static byte[] getLocalVersion() { return _LOCAL_VERSION; }

  // generic exception

  public static class ModRuntimeException extends RuntimeException{
    public ModRuntimeException(String message) { super(message); }
  }

}