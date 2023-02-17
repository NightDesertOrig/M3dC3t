//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;

public class FastPlace extends Module {
  public FastPlace() {
    super("FastPlace", "Fast place and use.", Module.Category.PLAYER, true, false, false);
  }
  
  public void onUpdate() {
    mc.rightClickDelayTimer = 0;
  }
}
