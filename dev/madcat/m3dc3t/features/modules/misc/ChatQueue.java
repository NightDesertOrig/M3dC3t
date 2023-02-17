//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.features.modules.Module;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatQueue extends Module {
  Map<String, String> QA;
  
  public ChatQueue() {
    super("ChatQueue", "Automatically queue in 2b2t.xin.", Module.Category.MISC, true, false, false);
    this.QA = new HashMap<String, String>() {
      
      };
  }
  
  @SubscribeEvent
  public void onClientChatReceived(ClientChatReceivedEvent event) {
    if (event.getMessage().getUnformattedText().contains("SquirmyNugget")) {
      int sec;
      String s = event.getMessage().getUnformattedText().substring(15, 17);
      if (s.contains(" ")) {
        sec = Integer.parseInt(s.substring(0, 1));
      } else {
        sec = Integer.parseInt(s);
      } 
      if (sec <= 2)
        return; 
    } 
    String msg = event.getMessage().getUnformattedText();
    Map.Entry<String, String> Answer = this.QA.entrySet().stream().filter(p -> msg.contains((CharSequence)p.getKey())).findFirst().orElse(null);
    if (Answer != null)
      mc.player.connection.sendPacket((Packet)new CPacketChatMessage(Answer.getValue())); 
  }
}
