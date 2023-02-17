//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class MCP extends Module {
  private boolean clicked = false;
  
  public MCP() {
    super("MCP", "Click the middle mouse button Throws a pearl.", Module.Category.MISC, false, false, false);
  }
  
  public void onEnable() {
    if (fullNullCheck())
      disable(); 
  }
  
  public void onTick() {
    if (Mouse.isButtonDown(2)) {
      if (!this.clicked)
        throwPearl(); 
      this.clicked = true;
    } else {
      this.clicked = false;
    } 
  }
  
  private void throwPearl() {
    int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
    boolean offhand = (mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL), bl = offhand;
    if (pearlSlot != -1 || offhand) {
      int oldslot = mc.player.inventory.currentItem;
      if (!offhand)
        InventoryUtil.switchToHotbarSlot(pearlSlot, false); 
      mc.playerController.processRightClick((EntityPlayer)mc.player, (World)mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
      if (!offhand)
        InventoryUtil.switchToHotbarSlot(oldslot, false); 
    } 
  }
}
