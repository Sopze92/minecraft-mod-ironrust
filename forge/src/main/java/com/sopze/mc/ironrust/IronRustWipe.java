package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.wrapper.I_LoaderWrapper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.sopze.mc.ironrust.Constants.*;

@Mod(value= MOD_ID_CONST)
public class IronRustWipe implements I_LoaderWrapper {

	public IronRustWipe(FMLJavaModLoadingContext context) {
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