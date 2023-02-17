//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow extends Module {
  public Setting<Boolean> shift;
  
  public NoSlow() {
    super("NoSlow", "No item use slow down.", Module.Category.MOVEMENT, true, false, false);
    this.shift = register(new Setting("Sneak", Boolean.valueOf(false)));
  }
  
  @SubscribeEvent
  public void Slow(InputUpdateEvent event) {
    if (!mc.player.isSneaking() && mc.player.isHandActive() && !mc.player.isRiding()) {
      (event.getMovementInput()).moveStrafe *= 5.0F;
      (event.getMovementInput()).moveForward *= 5.0F;
      return;
    } 
    if (((Boolean)this.shift.getValue()).booleanValue() && mc.player.isHandActive() && !mc.player.isRiding()) {
      (event.getMovementInput()).moveStrafe *= 5.0F;
      (event.getMovementInput()).moveForward *= 5.0F;
    } 
  }
}
