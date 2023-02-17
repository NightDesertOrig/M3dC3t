//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;

public class StairSpeed extends Module {
  public StairSpeed() {
    super("StairSpeed", "Great module", Module.Category.MOVEMENT, true, false, false);
  }
  
  public void onUpdate() {
    if (mc.player.onGround && mc.player.posY - Math.floor(mc.player.posY) > 0.0D && mc.player.moveForward != 0.0F)
      mc.player.jump(); 
  }
}
