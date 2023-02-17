//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.manager.ModuleManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Module {
  private int numOfTotems;
  
  private int preferredTotemSlot;
  
  private static AutoArmor INSTANCE = new AutoArmor();
  
  public AutoArmor() {
    super("AutoArmor", "Tweaks for wear armors", Module.Category.PLAYER, true, false, false);
    setInstance();
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public static AutoArmor getInstance() {
    if (INSTANCE == null)
      INSTANCE = new AutoArmor(); 
    return INSTANCE;
  }
  
  public void onUpdate() {
    if (mc.player.inventoryContainer.getSlot(5).getStack().getItem().equals(Items.AIR) && findTotems(Items.DIAMOND_HELMET, 5)) {
      mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      mc.playerController.windowClick(0, 5, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      return;
    } 
    if (mc.player.inventoryContainer.getSlot(6).getStack().getItem().equals(Items.AIR) && findTotems(Items.DIAMOND_CHESTPLATE, 6)) {
      mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      return;
    } 
    if (mc.player.inventoryContainer.getSlot(7).getStack().getItem().equals(Items.AIR) && findTotems(Items.DIAMOND_LEGGINGS, 7)) {
      mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      mc.playerController.windowClick(0, 7, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      return;
    } 
    if (mc.player.inventoryContainer.getSlot(8).getStack().getItem().equals(Items.AIR) && findTotems(Items.DIAMOND_BOOTS, 8)) {
      mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      mc.playerController.windowClick(0, 8, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      return;
    } 
    if (mc.player.inventoryContainer.getSlot(6).getStack().getItem().equals(Items.ELYTRA) && mc.player.onGround && !ModuleManager.getModuleByName("FastElytra").isEnabled() && findTotems(Items.DIAMOND_CHESTPLATE, 6)) {
      mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
    } 
  }
  
  private boolean findTotems(ItemArmor item, int slotId) {
    this.numOfTotems = 0;
    AtomicInteger preferredTotemSlotStackSize = new AtomicInteger();
    preferredTotemSlotStackSize.set(-2147483648);
    getInventorySlots(9).forEach((slotKey, slotValue) -> {
          int numOfTotemsInStack = 0;
          if (slotValue.getItem().equals(item)) {
            numOfTotemsInStack = slotValue.getCount();
            if (preferredTotemSlotStackSize.get() < numOfTotemsInStack) {
              preferredTotemSlotStackSize.set(numOfTotemsInStack);
              this.preferredTotemSlot = slotKey.intValue();
            } 
          } 
          this.numOfTotems += numOfTotemsInStack;
        });
    if (mc.player.inventoryContainer.getSlot(slotId).getStack().getItem().equals(Items.DIAMOND_HELMET))
      this.numOfTotems++; 
    return (this.numOfTotems != 0);
  }
  
  private static Map<Integer, ItemStack> getInventorySlots(int current) {
    HashMap<Object, Object> fullInventorySlots;
    for (fullInventorySlots = new HashMap<>(); current <= 44; current++)
      fullInventorySlots.put(Integer.valueOf(current), mc.player.inventoryContainer.getInventory().get(current)); 
    return (Map)fullInventorySlots;
  }
}
