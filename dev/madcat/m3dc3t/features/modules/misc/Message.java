//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.apache.commons.lang3.RandomStringUtils;

public class Message extends Module {
  private final Timer timer = new Timer();
  
  private Setting<String> custom = register(new Setting("Custom", "M3dC3t very cute:3"));
  
  private Setting<Integer> random = register(new Setting("Random", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(20)));
  
  private Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(10000)));
  
  public Message() {
    super("Spammer", "Automatically send messages.", Module.Category.MISC, true, false, false);
  }
  
  public void onTick() {
    if (fullNullCheck())
      return; 
    if (!this.timer.passedMs(((Integer)this.delay.getValue()).intValue()))
      return; 
    mc.player.connection.sendPacket((Packet)new CPacketChatMessage((String)this.custom.getValue() + RandomStringUtils.randomAlphanumeric(((Integer)this.random.getValue()).intValue())));
    this.timer.reset();
  }
}
