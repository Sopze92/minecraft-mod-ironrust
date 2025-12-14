package com.sopze.mc.ironrust.network;

import net.minecraft.network.FriendlyByteBuf;

public record BiGreetPacket(byte[] version) {

  public BiGreetPacket(FriendlyByteBuf buf) {
    this(buf.readByteArray());
  }

  public void encode(FriendlyByteBuf buf) {
    buf.writeByteArray(version);
  }
}
