//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;

public class Flight extends Module {
  public Setting<Float> speed;
  
  public Flight() {
    super("Flight", "Allow you fly.", Module.Category.MOVEMENT, true, false, false);
    this.speed = register(new Setting("Speed", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(50.0F)));
  }
  
  public void onEnable() {
    if (mc.player == null)
      return; 
    mc.player.capabilities.isFlying = true;
    if (mc.player.capabilities.isCreativeMode)
      return; 
    mc.player.capabilities.allowFlying = true;
  }
  
  public void onTick() {
    mc.player.capabilities.setFlySpeed(((Float)this.speed.getValue()).floatValue() / 100.0F);
    mc.player.capabilities.isFlying = true;
    if (mc.player.capabilities.isCreativeMode)
      return; 
    mc.player.capabilities.allowFlying = true;
  }
  
  public void onDisable() {
    mc.player.capabilities.isFlying = false;
    mc.player.capabilities.setFlySpeed(0.05F);
    if (mc.player.capabilities.isCreativeMode)
      return; 
    mc.player.capabilities.allowFlying = false;
  }
}
