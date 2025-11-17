package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.wrapper.I_LoaderWrapper;

import static com.sopze.mc.ironrust.Constants.*;

public class Main {

  private static I_LoaderWrapper _WRAPPER;

  public static String _LOCAL_VERSION_STRING;
  public static byte[] _LOCAL_VERSION;

  public static boolean
    _MOD_ENABLED_LOCALLY = false,
    _MOD_WIPER_STATE = false;

	public static void initialize(I_LoaderWrapper wrapper) {
    _WRAPPER= wrapper;

    _MOD_WIPER_STATE = _WRAPPER.isModLoaded(WIPER_ID);

    _LOCAL_VERSION_STRING= _WRAPPER.computeVersionString();
    _LOCAL_VERSION= Util._computeVersionBytes(_LOCAL_VERSION_STRING);

    if (_MOD_WIPER_STATE) Logger.logWrn(LOC_MAIN, WRN_IRONRUST_CONFLICT);

    setEnabledLocally(Util._isValidVersion());
	}

  public static boolean isEnabledLocally(){ return _MOD_ENABLED_LOCALLY; }
  public static void setEnabledLocally(boolean state){ _MOD_ENABLED_LOCALLY = state && !_MOD_WIPER_STATE; }

  public static boolean isWiperModPresent(){ return _MOD_WIPER_STATE; }

  public static String getLocalVersionString() { return _LOCAL_VERSION_STRING; }
  public static byte[] getLocalVersion() { return _LOCAL_VERSION; }

  // generic exception

  public static class ModRuntimeException extends RuntimeException{
    public ModRuntimeException(String message) { super(message); }
  }

}