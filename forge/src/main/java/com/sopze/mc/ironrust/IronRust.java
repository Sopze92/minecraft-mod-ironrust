package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.block.ModBlocks;
import com.sopze.mc.ironrust.item.ModItems;
import com.sopze.mc.ironrust.network.BiGreetPacket;
import com.sopze.mc.ironrust.network.ForgeNetwork;
import com.sopze.mc.ironrust.wrapper.I_LoaderWrapper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import static com.sopze.mc.ironrust.Constants.*;

@Mod(value= MOD_ID_CONST)
public class IronRust implements I_LoaderWrapper {

	public IronRust(FMLJavaModLoadingContext context) {
    Main.initialize(this);

    BusGroup busgrp= context.getModBusGroup();

    FMLCommonSetupEvent.getBus(busgrp).addListener(IronRust::onCommonSetup);
    FMLClientSetupEvent.getBus(busgrp).addListener(IronRust::onClientSetup);

    RegisterEvent.getBus(busgrp).addListener(IronRust::onRegistry);

    PlayerEvent.PlayerLoggedInEvent.BUS.addListener(IronRust::onUserConnected);
  }

  public static void onCommonSetup(FMLCommonSetupEvent event) { event.enqueueWork(ForgeNetwork::initialize); }
  public static void onClientSetup(FMLClientSetupEvent event) { event.enqueueWork(IronRustClient::initialize); }

  public static void onRegistry(RegisterEvent event){
    ModBlocks.register(event);
    ModItems.register(event);
  }

  public static void onUserConnected(PlayerEvent.PlayerLoggedInEvent event) {
    if(!(event.getEntity() instanceof ServerPlayer player) || player.level().isClientSide()) return;

    Logger.slog(CHECKING_CLIENT_MOD, player.getName().getString());
    ForgeNetwork.SendS2CPacket(new BiGreetPacket(Main.getLocalVersion()), player);
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