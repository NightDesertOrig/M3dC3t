//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.features.modules.Module;

public class Respawn extends Module {
  public Respawn() {
    super("Respawn", "Resurrection at the time of death.", Module.Category.MISC, true, false, false);
  }
  
  public void onUpdate() {
    if (mc.currentScreen instanceof net.minecraft.client.gui.GuiGameOver) {
      mc.player.respawnPlayer();
      mc.displayGuiScreen(null);
    } 
  }
}
