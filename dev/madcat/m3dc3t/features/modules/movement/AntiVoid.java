//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.features.command.Command;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;

public class AntiVoid extends Module {
  public Setting<Mode> mode = register(new Setting("Mode", Mode.BOUNCE));
  
  public Setting<Boolean> display = register(new Setting("Display", Boolean.valueOf(true)));
  
  public AntiVoid() {
    super("AntiVoid", "Glitches you up from void.", Module.Category.MOVEMENT, false, false, false);
  }
  
  public void onUpdate() {
    double yLevel = mc.player.posY;
    if (yLevel <= 0.5D) {
      Command.sendMessage(ChatFormatting.RED + "Player " + ChatFormatting.GREEN + mc.player.getName() + ChatFormatting.RED + " is in the void!");
      if (((Mode)this.mode.getValue()).equals(Mode.BOUNCE)) {
        mc.player.moveVertical = 10.0F;
        mc.player.jump();
      } 
      if (((Mode)this.mode.getValue()).equals(Mode.LAUNCH)) {
        mc.player.moveVertical = 100.0F;
        mc.player.jump();
      } 
    } else {
      mc.player.moveVertical = 0.0F;
    } 
  }
  
  public void onDisable() {
    mc.player.moveVertical = 0.0F;
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (((Boolean)this.display.getValue()).booleanValue()) {
      if (((Mode)this.mode.getValue()).equals(Mode.BOUNCE))
        return "Bounce"; 
      if (((Mode)this.mode.getValue()).equals(Mode.LAUNCH))
        return "Launch"; 
    } 
    return null;
  }
  
  public enum Mode {
    BOUNCE, LAUNCH;
  }
}
