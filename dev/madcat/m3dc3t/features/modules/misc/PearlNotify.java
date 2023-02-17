//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.features.command.Command;
import dev.madcat.m3dc3t.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PearlNotify extends Module {
  public PearlNotify() {
    super("PearlNotify", "Pearl notify.", Module.Category.MISC, true, false, false);
  }
  
  @SubscribeEvent
  public void onReceivePacket(PacketEvent.Receive event) {
    if (event.getPacket() instanceof SPacketSpawnObject) {
      SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
      EntityPlayer player = mc.world.getClosestPlayer(packet.getX(), packet.getY(), packet.getZ(), 1.0D, false);
      if (player == null)
        return; 
      if (packet.getEntityID() == 85)
        Command.sendMessage("§cPearl thrown by " + player.getName() + " at X:" + (int)packet.getX() + " Y:" + (int)packet.getY() + " Z:" + (int)packet.getZ()); 
    } 
  }
}
