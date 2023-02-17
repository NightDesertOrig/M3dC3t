package dev.madcat.m3dc3t.util;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketReceiveEvent extends PacketEvent {
  public PacketReceiveEvent(Packet<?> packet, MomentumEvent.Stage stage) {
    super(packet, stage);
  }
}
