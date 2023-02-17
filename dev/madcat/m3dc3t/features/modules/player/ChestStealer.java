//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerShulkerBox;

public class ChestStealer extends Module {
  public ChestStealer() {
    super("ChestStealer", "steal items in chests.", Module.Category.PLAYER, true, false, false);
  }
  
  public void onUpdate() {
    if (mc.player.openContainer instanceof ContainerShulkerBox) {
      ContainerShulkerBox chest = (ContainerShulkerBox)mc.player.openContainer;
      for (int items = 0; items < 27; items++)
        mc.playerController.windowClick(chest.windowId, items, 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.player); 
      mc.player.closeScreen();
    } 
  }
}
