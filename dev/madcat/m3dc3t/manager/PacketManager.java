//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.manager;

import dev.madcat.m3dc3t.features.Feature;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.Packet;

public class PacketManager extends Feature {
  private final List<Packet<?>> noEventPackets = new ArrayList<>();
  
  public void sendPacketNoEvent(Packet<?> packet) {
    if (packet != null && !nullCheck()) {
      this.noEventPackets.add(packet);
      mc.player.connection.sendPacket(packet);
    } 
  }
  
  public boolean shouldSendPacket(Packet<?> packet) {
    if (this.noEventPackets.contains(packet)) {
      this.noEventPackets.remove(packet);
      return false;
    } 
    return true;
  }
}
