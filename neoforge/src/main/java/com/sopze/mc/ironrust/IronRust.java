package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.block.ModBlocks;
import com.sopze.mc.ironrust.item.ModItems;
import com.sopze.mc.ironrust.wrapper.I_LoaderWrapper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.sopze.mc.ironrust.Constants.*;

@Mod(Constants.MOD_ID_CONST)
public class IronRust implements I_LoaderWrapper {

	public IronRust(IEventBus bus) {

    Main.initialize(this);

    bus.addListener(IronRust::register);
    bus.addListener(IronRust::onCommonSetup);
    NeoForge.EVENT_BUS.addListener(IronRust::onUserConnected);

    ModBlocks.initialize();
    ModItems.initialize(bus);
	}

  @SubscribeEvent
  public static void onCommonSetup(FMLCommonSetupEvent event) {
    event.enqueueWork(ModItems::initializeAfter);
  }

  @SubscribeEvent
  public static void register(RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar("1");
    registrar.playBidirectional(Network.GreetPayload.PAYLOAD_ID, Network.GreetPayload.CODEC, (a,b)->{});
  }

  @SubscribeEvent
  public static void onUserConnected(PlayerEvent.PlayerLoggedInEvent event) {
    if(!(event.getEntity() instanceof ServerPlayer player)) return;
    if(player.level().isClientSide()) return;

    Logger.slog(CHECKING_CLIENT_MOD, player.getName().getString());
    player.connection.send(new Network.GreetPayload(Main.getLocalVersion()));
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