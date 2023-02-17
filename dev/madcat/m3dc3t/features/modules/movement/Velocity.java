//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.event.events.PushEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {
  public Setting<Boolean> antiKnockBack = register(new Setting("KnockBack", Boolean.valueOf(true)));
  
  public Setting<Boolean> noEntityPush = register(new Setting("No PlayerPush", Boolean.valueOf(true)));
  
  public Setting<Boolean> noBlockPush = register(new Setting("No BlockPush", Boolean.valueOf(true)));
  
  public Setting<Boolean> noWaterPush = register(new Setting("No LiquidPush", Boolean.valueOf(true)));
  
  public Velocity() {
    super("Velocity", "Anti push and knock back.", Module.Category.MOVEMENT, true, false, false);
  }
  
  @SubscribeEvent
  public void onPacketReceived(PacketEvent.Receive event) {
    if (fullNullCheck())
      return; 
    if (((Boolean)this.antiKnockBack.getValue()).booleanValue()) {
      if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == mc.player.getEntityId())
        event.setCanceled(true); 
      if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketExplosion)
        event.setCanceled(true); 
    } 
  }
  
  @SubscribeEvent
  public void onPush(PushEvent event) {
    if (fullNullCheck())
      return; 
    if (event.getStage() == 0 && ((Boolean)this.noEntityPush.getValue()).booleanValue() && event.entity.equals(mc.player)) {
      event.x = -event.x * 0.0D;
      event.y = -event.y * 0.0D;
      event.z = -event.z * 0.0D;
    } else if (event.getStage() == 1 && ((Boolean)this.noBlockPush.getValue()).booleanValue()) {
      event.setCanceled(true);
    } else if (event.getStage() == 2 && ((Boolean)this.noWaterPush.getValue()).booleanValue() && mc.player != null && mc.player.equals(event.entity)) {
      event.setCanceled(true);
    } 
  }
}
