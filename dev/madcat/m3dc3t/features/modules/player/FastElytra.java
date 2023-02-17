//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class FastElytra extends Module {
  private int numOfTotems;
  
  private int preferredTotemSlot;
  
  private static FastElytra INSTANCE = new FastElytra();
  
  public FastElytra() {
    super("FastElytra", "Tweaks for wear Elytra", Module.Category.PLAYER, true, false, false);
    setInstance();
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public static FastElytra getInstance() {
    if (INSTANCE == null)
      INSTANCE = new FastElytra(); 
    return INSTANCE;
  }
  
  public void onUpdate() {
    if (!mc.player.onGround) {
      if (findTotems() && 
        !mc.player.inventoryContainer.getSlot(6).getStack().getItem().equals(Items.ELYTRA)) {
        mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
        mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
        mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      } 
    } else if (findTotems2() && 
      mc.player.inventoryContainer.getSlot(6).getStack().getItem().equals(Items.ELYTRA)) {
      mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
      mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
    } 
  }
  
  private boolean findTotems() {
    this.numOfTotems = 0;
    AtomicInteger preferredTotemSlotStackSize = new AtomicInteger();
    preferredTotemSlotStackSize.set(-2147483648);
    getInventorySlots(9).forEach((slotKey, slotValue) -> {
          int numOfTotemsInStack = 0;
          if (slotValue.getItem().equals(Items.ELYTRA)) {
            numOfTotemsInStack = slotValue.getCount();
            if (preferredTotemSlotStackSize.get() < numOfTotemsInStack) {
              preferredTotemSlotStackSize.set(numOfTotemsInStack);
              this.preferredTotemSlot = slotKey.intValue();
            } 
          } 
          this.numOfTotems += numOfTotemsInStack;
        });
    if (mc.player.inventoryContainer.getSlot(6).getStack().getItem().equals(Items.ELYTRA))
      this.numOfTotems++; 
    return (this.numOfTotems != 0);
  }
  
  private boolean findTotems2() {
    this.numOfTotems = 0;
    AtomicInteger preferredTotemSlotStackSize = new AtomicInteger();
    preferredTotemSlotStackSize.set(-2147483648);
    getInventorySlots(9).forEach((slotKey, slotValue) -> {
          int numOfTotemsInStack = 0;
          if (slotValue.getItem().equals(Items.DIAMOND_CHESTPLATE)) {
            numOfTotemsInStack = slotValue.getCount();
            if (preferredTotemSlotStackSize.get() < numOfTotemsInStack) {
              preferredTotemSlotStackSize.set(numOfTotemsInStack);
              this.preferredTotemSlot = slotKey.intValue();
            } 
          } 
          this.numOfTotems += numOfTotemsInStack;
        });
    if (mc.player.inventoryContainer.getSlot(6).getStack().getItem().equals(Items.DIAMOND_CHESTPLATE))
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
