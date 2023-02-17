package dev.madcat.m3dc3t.util;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketSendEvent extends PacketEvent {
  public PacketSendEvent(Packet<?> packet, MomentumEvent.Stage stage) {
    super(packet, stage);
  }
}
