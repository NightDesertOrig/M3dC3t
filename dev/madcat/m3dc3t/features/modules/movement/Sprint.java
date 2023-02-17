//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;

public class Sprint extends Module {
  public Sprint() {
    super("Sprint", "Force sprint.", Module.Category.MOVEMENT, true, false, false);
  }
  
  public void onTick() {
    if ((mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F) && 
      !mc.player.isSprinting())
      mc.player.setSprinting(true); 
  }
}
