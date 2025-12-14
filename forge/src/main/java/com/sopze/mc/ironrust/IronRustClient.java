package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.block.ModBlocks;
import com.sopze.mc.ironrust.item.ModItems;
import com.sopze.mc.ironrust.network.BiGreetPacket;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.sopze.mc.ironrust.Constants.*;

public class IronRustClient {

  private static boolean _SERVER_HAS_MOD = false;
  private static byte[] _SERVER_VERSION;

  public static void initialize() {
    for(Block b : ModBlocks.CUTOUT_BLOCKS) ItemBlockRenderTypes.setRenderLayer(b, ChunkSectionLayer.CUTOUT);

    BuildCreativeModeTabContentsEvent.BUS.addListener(IronRustClient::onBuildCreativeTabContents);
    ClientPlayerNetworkEvent.LoggingIn.BUS.addListener(IronRustClient::onUserConnected);
    ClientPlayerNetworkEvent.LoggingOut.BUS.addListener(IronRustClient::onUserDisconnected);
  }

  public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) { ModItems.onBuildCreativeTabContents(event); }

  public static void onUserConnected(ClientPlayerNetworkEvent.LoggingIn event) {
    boolean local= event.getPlayer().connection.getConnection().isMemoryConnection();

    _SERVER_HAS_MOD = local;
    _SERVER_VERSION = local ? Main.getLocalVersion() : new byte[] {0,0,0};
    Main.setEnabledLocally(local);
  }

  public static void onUserDisconnected(ClientPlayerNetworkEvent.LoggingOut event) {
    _SERVER_HAS_MOD = false;
    _SERVER_VERSION = new byte[] {0,0,0};
  }

  public static void _onReceivedGreetPayload_Client(BiGreetPacket packet, CustomPayloadEvent.Context context) {
    _SERVER_HAS_MOD = true;
    _SERVER_VERSION = packet.version();
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