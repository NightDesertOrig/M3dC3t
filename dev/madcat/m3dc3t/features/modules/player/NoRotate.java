//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.util.Timer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRotate extends Module {
  private final Timer timer = new Timer();
  
  private boolean cancelPackets = true;
  
  private boolean timerReset = false;
  
  public NoRotate() {
    super("NoRotate", "Dangerous to use might desync you.", Module.Category.PLAYER, true, false, false);
  }
  
  public void onLogout() {
    this.cancelPackets = false;
  }
  
  public void onLogin() {
    this.timer.reset();
    this.timerReset = true;
  }
  
  public void onUpdate() {
    if (this.timerReset && !this.cancelPackets) {
      this.cancelPackets = true;
      this.timerReset = false;
    } 
  }
  
  @SubscribeEvent
  public void onPacketReceive(PacketEvent.Receive event) {
    if (event.getStage() == 0 && this.cancelPackets && event.getPacket() instanceof SPacketPlayerPosLook) {
      SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
      packet.yaw = mc.player.rotationYaw;
      packet.pitch = mc.player.rotationPitch;
    } 
  }
}
