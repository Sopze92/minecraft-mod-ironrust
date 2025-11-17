package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.sopze.mc.ironrust.Constants.*;

@Mod(value= Constants.MOD_ID_CONST, dist= Dist.CLIENT)
public class IronRustClient {

  private static boolean _SERVER_HAS_MOD = false;
  private static byte[] _SERVER_VERSION;

  public IronRustClient(IEventBus bus) {
    bus.addListener(IronRustClient::onBuildCreativeTabContents);
    bus.addListener(IronRustClient::onRegisterPayloadHandlers);
    NeoForge.EVENT_BUS.addListener(IronRustClient::onUserConnected);
    NeoForge.EVENT_BUS.addListener(IronRustClient::onUserDisconnected);
	}

  @SubscribeEvent
  public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
    ModItems.onBuildCreativeTabContents(event);
  }

  @SubscribeEvent
  public static void onRegisterPayloadHandlers(RegisterClientPayloadHandlersEvent event) {
    event.register(Network.GreetPayload.PAYLOAD_ID, IronRustClient::_onReceivedGreetPayload_Client);
  }

  @SubscribeEvent
  public static void onUserConnected(PlayerEvent.PlayerLoggedInEvent event) {
    if(!(event.getEntity() instanceof ServerPlayer player)) return;
    boolean local= player.level().isClientSide();

    _SERVER_HAS_MOD = local;
    _SERVER_VERSION = local ? Main.getLocalVersion() : new byte[] {0,0,0};
    Main.setEnabledLocally(local);
  }

  @SubscribeEvent
  public static void onUserDisconnected(PlayerEvent.PlayerLoggedOutEvent event) {
    _SERVER_HAS_MOD = false;
    _SERVER_VERSION = new byte[] {0,0,0};
  }

  public static void _onReceivedGreetPayload_Client(Network.GreetPayload payload, IPayloadContext context) {
    _SERVER_HAS_MOD = true;
    _SERVER_VERSION = payload.read();
    final byte[] localVersion= Main.getLocalVersion();

    boolean valid= Util.isCompatibleVersion(_SERVER_VERSION);
    Main.setEnabledLocally(valid);

    Logger.slog(VERSION_CHECK_INFO,
      String.format("%d.%d.%d%s", _SERVER_VERSION[0], _SERVER_VERSION[1], _SERVER_VERSION[2], "FBAX".charAt(_SERVER_VERSION[3])),
      String.format("%d.%d.%d%s", localVersion[0], localVersion[1], localVersion[2], "FBAX".charAt(localVersion[3])));
    Logger.slog(VERSION_CHECK_MESSAGE, valid ? VERSION_CHECK_SUCCEED : VERSION_CHECK_FAIL);
  }

  public static boolean getServerHasMod() { return _SERVER_HAS_MOD; }
  public static byte[] getServerVersion() { return _SERVER_VERSION; }
}