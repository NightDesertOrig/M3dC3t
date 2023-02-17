//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Timers extends Module {
  public static CopyOnWriteArrayList<Packet<?>> packetList = new CopyOnWriteArrayList<>();
  
  private final Setting<Float> tickNormal = register(new Setting("Speed", Float.valueOf(1.2F), Float.valueOf(1.0F), Float.valueOf(10.0F)));
  
  private final Setting<Boolean> bypass = register(new Setting("Bypass", Boolean.valueOf(true)));
  
  public final Setting<Float> speedvalue = register(new Setting("SpeedValue", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(100.0F), v -> ((Boolean)this.bypass.getValue()).booleanValue()));
  
  private final Setting<Boolean> setrotate = register(new Setting("SetRotate", Boolean.valueOf(true), v -> ((Boolean)this.bypass.getValue()).booleanValue()));
  
  public int i = 0;
  
  public int x = 0;
  
  private float yaw = 0.0F;
  
  private float pitch = 0.0F;
  
  private final Timer timer = new Timer();
  
  private boolean cancelPackets = true;
  
  private boolean timerReset = false;
  
  int ticked = 79;
  
  public Timers() {
    super("Timer", "Change client running speed.", Module.Category.MOVEMENT, true, false, false);
  }
  
  public void onDisable() {
    mc.timer.tickLength = 50.0F;
    packetList.clear();
  }
  
  public void onEnable() {
    mc.timer.tickLength = 50.0F;
    packetList.clear();
  }
  
  public void onUpdate() {
    if (this.timerReset && !this.cancelPackets) {
      this.cancelPackets = true;
      this.timerReset = false;
    } 
    if (((Boolean)this.bypass.getValue()).booleanValue()) {
      if (this.i <= ((Float)this.speedvalue.getValue()).floatValue()) {
        this.i++;
        mc.timer.tickLength = 50.0F / ((Float)this.tickNormal.getValue()).floatValue();
        this.x = 0;
      } else if (this.x <= ((Float)this.speedvalue.getValue()).floatValue() - ((Float)this.speedvalue.getValue()).floatValue() / 2.0F / 2.0F) {
        this.x++;
        mc.timer.tickLength = 50.0F;
      } else {
        this.i = 0;
      } 
    } else {
      mc.timer.tickLength = 50.0F / ((Float)this.tickNormal.getValue()).floatValue();
    } 
  }
  
  public void onTick() {
    if (((Boolean)this.bypass.getValue()).booleanValue() && (
      (Boolean)this.setrotate.getValue()).booleanValue()) {
      this.ticked++;
      if (this.ticked >= 79) {
        this.yaw = mc.player.rotationYaw;
        this.pitch = mc.player.rotationPitch;
        this.ticked = 0;
      } 
    } 
  }
  
  @SubscribeEvent
  public void onPacketSend(PacketEvent.Send event) {
    if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer && !packetList.contains(event.getPacket()))
      packetList.add(event.getPacket()); 
    if (event.getStage() == 0 && ((Boolean)this.setrotate.getValue()).booleanValue() && this.cancelPackets && event.getPacket() instanceof SPacketPlayerPosLook) {
      SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
      packet.yaw = this.yaw;
      packet.pitch = this.pitch;
    } 
  }
  
  public void onLogout() {
    this.cancelPackets = false;
  }
  
  public void onLogin() {
    this.timer.reset();
    this.timerReset = true;
  }
}
