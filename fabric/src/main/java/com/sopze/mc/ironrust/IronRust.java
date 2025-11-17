package com.sopze.mc.ironrust;

import com.sopze.mc.ironrust.block.ModBlocks;
import com.sopze.mc.ironrust.item.ModItems;
import com.sopze.mc.ironrust.wrapper.I_LoaderWrapper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import static com.sopze.mc.ironrust.Constants.*;

public class IronRust implements ModInitializer, I_LoaderWrapper {

  @Override
	public void onInitialize() {
    Main.initialize(this);

    PayloadTypeRegistry.playS2C().register(Network.GreetPayload.PAYLOAD_ID, Network.GreetPayload.CODEC);
    PayloadTypeRegistry.playC2S().register(Network.GreetPayload.PAYLOAD_ID, Network.GreetPayload.CODEC);

    ServerPlayConnectionEvents.JOIN.register(IronRust::_onUserConnected_Server);

    ModBlocks.initialize();
    ModItems.initialize();
	}

  private static void _onUserConnected_Server(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server){
    if(!server.isDedicatedServer()){ return; }
    ServerPlayer player= handler.player;
    Logger.slog(CHECKING_CLIENT_MOD, player.getName().getString());
    player.connection.send(ServerPlayNetworking.createS2CPacket(new Network.GreetPayload(Main.getLocalVersion())));
  }

  // wrapper

  public boolean isModLoaded(String modid) { return FabricLoader.getInstance().isModLoaded(modid); }

  public String computeVersionString(){
    return FabricLoader.getInstance().getModContainer(MOD_ID)
      .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
      .orElse("0.0.0X-MissingVersion");
  }
}