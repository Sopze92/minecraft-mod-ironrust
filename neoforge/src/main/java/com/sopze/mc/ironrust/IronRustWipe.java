package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.wrapper.I_LoaderWrapper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID_CONST)
public class IronRustWipe implements I_LoaderWrapper {

	public IronRustWipe(IEventBus bus) {
    Main.initialize(this);
	}

  // wrapper

  public boolean isModLoaded(String modid) { return ModList.get().isLoaded(modid); }

  public String computeVersionString()
  {
    return ModList.get().getModContainerById(Constants.MOD_ID)
      .map(mod -> mod.getModInfo().getVersion().toString())
      .orElse("0.0.0X-MissingVersion");
  }
}