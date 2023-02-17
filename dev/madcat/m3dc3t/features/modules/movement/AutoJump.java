//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;

public class AutoJump extends Module {
  public AutoJump() {
    super("AutoJump", "Jump like bunny.", Module.Category.MOVEMENT, false, false, false);
  }
  
  public void onUpdate() {
    if (!mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()))
      mc.player.jump(); 
  }
}
