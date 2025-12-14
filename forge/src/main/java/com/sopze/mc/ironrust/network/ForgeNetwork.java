package com.sopze.mc.ironrust.network;

import com.sopze.mc.ironrust.Constants;
import com.sopze.mc.ironrust.IronRustClient;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class ForgeNetwork {

  public static final SimpleChannel INSTANCE = ChannelBuilder.named(
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "main"))
    .acceptedVersions((s,v)->true)
    .networkProtocolVersion(1)
    .simpleChannel();

  public static void initialize() {
    INSTANCE.messageBuilder(BiGreetPacket.class, NetworkDirection.PLAY_TO_CLIENT)
      .encoder(BiGreetPacket::encode).decoder(BiGreetPacket::new)
      .consumerMainThread((msg, ctx)->{
        if(ctx.isClientSide()) IronRustClient._onReceivedGreetPayload_Client(msg, ctx);
      })
      .add();
  }

  public static void SendC2SPacket(Object msg){
    INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
  }

  public static void SendS2CPacket(Object msg, ServerPlayer player){
    INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
  }
}
