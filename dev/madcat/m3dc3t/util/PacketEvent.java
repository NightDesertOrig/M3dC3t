package dev.madcat.m3dc3t.util;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketEvent extends MomentumEvent {
  Packet<?> packet;
  
  public PacketEvent(Packet<?> packet, MomentumEvent.Stage stage) {
    super(stage);
    this.packet = packet;
  }
  
  public Packet<?> getPacket() {
    return this.packet;
  }
}
