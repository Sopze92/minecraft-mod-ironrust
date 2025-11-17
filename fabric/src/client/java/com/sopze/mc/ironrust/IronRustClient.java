package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

import static com.sopze.mc.ironrust.Constants.*;

public class IronRustClient implements ClientModInitializer {

  private static boolean _SERVER_HAS_MOD = false;
  private static byte[] _SERVER_VERSION;

  @Override
	public void onInitializeClient() {
    BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT, ModBlocks.CUTOUT_BLOCKS);
    ClientPlayConnectionEvents.DISCONNECT.register(IronRustClient::_onUserDisconnected_Client);
    ClientPlayConnectionEvents.JOIN.register(IronRustClient::_onUserConnected_Client);
    ClientPlayNetworking.registerGlobalReceiver(Network.GreetPayload.PAYLOAD_ID, IronRustClient::_onReceivedGreetPayload_Client);
	}

  private static void _onUserDisconnected_Client(ClientPacketListener handler, Minecraft client) {
    _SERVER_HAS_MOD = false;
    _SERVER_VERSION = new byte[] {0,0,0};
  }

  private static void _onUserConnected_Client(ClientPacketListener handler, PacketSender sender, Minecraft client) {
    boolean local= handler.getConnection().isMemoryConnection();
    _SERVER_HAS_MOD = local;
    _SERVER_VERSION = local ? Main.getLocalVersion() : new byte[] {0,0,0};
    Main.setEnabledLocally(local);
  }

  private static void _onReceivedGreetPayload_Client(Network.GreetPayload payload, ClientPlayNetworking.Context context) {
    _SERVER_HAS_MOD = true;
    _SERVER_VERSION = payload.read();
    final byte[] localVersion= Main.getLocalVersion();

    boolean valid= Util.isCompatibleVersion(_SERVER_VERSION);
    Main.setEnabledLocally(valid);

    Logger.slog(VERSION_CHECK_INFO, _SERVER_VERSION[0], _SERVER_VERSION[1], _SERVER_VERSION[2], localVersion[0], localVersion[1], localVersion[2]);
    Logger.slog(VERSION_CHECK_MESSAGE, valid ? VERSION_CHECK_SUCCEED : VERSION_CHECK_FAIL);
  }

  public static boolean getServerHasMod() { return _SERVER_HAS_MOD; }
  public static byte[] getServerVersion() { return _SERVER_VERSION; }
}