//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiBookBan extends Module {
  public AntiBookBan() {
    super("AntiBookBan", "AntiBookBan", Module.Category.MISC, true, false, false);
  }
  
  @SubscribeEvent
  public void onLeavingDeathEvent(PacketEvent.Receive event) {
    if (!(event.getPacket() instanceof CPacketClickWindow))
      return; 
    CPacketClickWindow packet = (CPacketClickWindow)event.getPacket();
    if (!(packet.getClickedItem().getItem() instanceof net.minecraft.item.ItemWrittenBook))
      return; 
    event.isCancelled();
    mc.player.openContainer.slotClick(packet.getSlotId(), packet.getUsedButton(), packet.getClickType(), (EntityPlayer)mc.player);
  }
}
