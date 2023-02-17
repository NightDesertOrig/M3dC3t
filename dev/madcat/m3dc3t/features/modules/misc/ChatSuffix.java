//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.manager.ModuleManager;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatSuffix extends Module {
  public ChatSuffix() {
    super("ChatSuffix", "Message with suffix.", Module.Category.MISC, true, false, false);
  }
  
  @SubscribeEvent
  public void onPacketSend(PacketEvent.Send event) {
    if (ModuleManager.getModuleByName("ChatQueue").isEnabled())
      return; 
    CPacketChatMessage packet;
    String message;
    if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage && !(message = (packet = (CPacketChatMessage)event.getPacket()).getMessage()).startsWith("/"))
      packet.message = message + " ♿ ᴄᴜᴇᴛ ᴄᴀᴛ ♿"; 
  }
}
