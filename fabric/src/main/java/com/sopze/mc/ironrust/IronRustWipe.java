package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.wrapper.I_LoaderWrapper;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;

import static com.sopze.mc.ironrust.Constants.*;

public class IronRustWipe implements ModInitializer, I_LoaderWrapper {

  @Override
	public void onInitialize() { Main.initialize(this); }

  @Override
  public boolean isModLoaded(String wiperId) { return FabricLoader.getInstance().isModLoaded(wiperId); }

  @Override
  public String computeVersionString() {
    return FabricLoader.getInstance().getModContainer(MOD_ID)
      .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
      .orElse("0.0.0X-MissingVersion");
  }
}