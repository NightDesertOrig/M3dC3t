//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import dev.madcat.m3dc3t.M3dC3t;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.NonNullList;

public class InventoryUtil implements Util {
  public static List<Integer> findAllItemSlots(Class<? extends Item> itemToFind) {
    List<Integer> slots = new ArrayList<>();
    NonNullList<ItemStack> nonNullList = mc.player.inventory.mainInventory;
    for (int i = 0; i < 36; i++) {
      ItemStack stack = nonNullList.get(i);
      if (stack != ItemStack.EMPTY && itemToFind.isInstance(stack.getItem()))
        slots.add(Integer.valueOf(i)); 
    } 
    return slots;
  }
  
  public static List<Integer> findAllBlockSlots(Class<? extends Block> blockToFind) {
    List<Integer> slots = new ArrayList<>();
    NonNullList<ItemStack> nonNullList = mc.player.inventory.mainInventory;
    for (int i = 0; i < 36; i++) {
      ItemStack stack = nonNullList.get(i);
      if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock)
        if (blockToFind.isInstance(((ItemBlock)stack.getItem()).getBlock()))
          slots.add(Integer.valueOf(i));  
    } 
    return slots;
  }
  
  public static void moveItemToOffhand(int slot) {
    int returnSlot = -1;
    if (slot == -1)
      return; 
    mc.playerController.windowClick(0, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
    for (int i = 0; i < 45; i++) {
      if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
        returnSlot = i;
        break;
      } 
    } 
    if (returnSlot != -1)
      mc.playerController.windowClick(0, (returnSlot < 9) ? (returnSlot + 36) : returnSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player); 
  }
  
  public static void switchToHotbarSlot(int slot, boolean silent) {
    if (mc.player.inventory.currentItem == slot || slot < 0)
      return; 
    if (silent) {
      mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
      mc.playerController.updateController();
    } else {
      mc.player.inventory.currentItem = slot;
      mc.playerController.updateController();
    } 
  }
  
  public static int getHotbarItemSlot(Item item) {
    for (int i = 0; i < 9; i++) {
      if (mc.player.inventory.getStackInSlot(i).getItem() == item)
        return i; 
    } 
    return -1;
  }
  
  public static void switchToSlot(Item item) {
    if (getHotbarItemSlot(item) != -1 && mc.player.inventory.currentItem != getHotbarItemSlot(item))
      mc.player.inventory.currentItem = getHotbarItemSlot(item); 
  }
  
  public static void switchToHotbarSlot(Class clazz, boolean silent) {
    int slot = findHotbarBlock(clazz);
    if (slot > -1)
      switchToHotbarSlot(slot, silent); 
  }
  
  public static boolean isNull(ItemStack stack) {
    return (stack == null || stack.getItem() instanceof net.minecraft.item.ItemAir);
  }
  
  public static int findHotbarBlock(Class clazz) {
    for (int i = 0; i < 9; i++) {
      ItemStack stack = mc.player.inventory.getStackInSlot(i);
      if (stack != ItemStack.EMPTY) {
        if (clazz.isInstance(stack.getItem()))
          return i; 
        Block block;
        if (stack.getItem() instanceof ItemBlock && clazz.isInstance(block = ((ItemBlock)stack.getItem()).getBlock()))
          return i; 
      } 
    } 
    return -1;
  }
  
  public static int findHotbarBlock(Block blockIn) {
    for (int i = 0; i < 9; ) {
      ItemStack stack = mc.player.inventory.getStackInSlot(i);
      Block block;
      if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || (block = ((ItemBlock)stack.getItem()).getBlock()) != blockIn) {
        i++;
        continue;
      } 
      return i;
    } 
    return -1;
  }
  
  public static int getItemHotbar(Item input) {
    for (int i = 0; i < 9; ) {
      Item item = mc.player.inventory.getStackInSlot(i).getItem();
      if (Item.getIdFromItem(item) != Item.getIdFromItem(input)) {
        i++;
        continue;
      } 
      return i;
    } 
    return -1;
  }
  
  public static int getItemHotbars(Item input) {
    for (int i = 0; i < 36; ) {
      Item item = mc.player.inventory.getStackInSlot(i).getItem();
      if (Item.getIdFromItem(item) != Item.getIdFromItem(input)) {
        i++;
        continue;
      } 
      return i;
    } 
    return -1;
  }
  
  public static int findStackInventory(Item input) {
    return findStackInventory(input, false);
  }
  
  public static int findStackInventory(Item input, boolean withHotbar) {
    int i = withHotbar ? 0 : 9, n = i;
    while (i < 36) {
      Item item = mc.player.inventory.getStackInSlot(i).getItem();
      if (Item.getIdFromItem(input) == Item.getIdFromItem(item))
        return i + ((i < 9) ? 36 : 0); 
      i++;
    } 
    return -1;
  }
  
  public static int findItemInventorySlot(Item item, boolean offHand) {
    AtomicInteger slot = new AtomicInteger();
    slot.set(-1);
    for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
      if (((ItemStack)entry.getValue()).getItem() != item || (((Integer)entry.getKey()).intValue() == 45 && !offHand))
        continue; 
      slot.set(((Integer)entry.getKey()).intValue());
      return slot.get();
    } 
    return slot.get();
  }
  
  public static int findItemInHotbar(Item item) {
    int index = -1;
    for (int i = 0; i < 9; i++) {
      if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
        index = i;
        break;
      } 
    } 
    return index;
  }
  
  public static List<Integer> getItemInventory(Item item) {
    List<Integer> ints = new ArrayList<>();
    for (int i = 9; i < 36; i++) {
      Item target = mc.player.inventory.getStackInSlot(i).getItem();
      if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().equals(item))
        ints.add(Integer.valueOf(i)); 
    } 
    if (ints.size() == 0)
      ints.add(Integer.valueOf(-1)); 
    return ints;
  }
  
  public static List<Integer> findEmptySlots(boolean withXCarry) {
    ArrayList<Integer> outPut = new ArrayList<>();
    for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
      if (!((ItemStack)entry.getValue()).isEmpty && ((ItemStack)entry.getValue()).getItem() != Items.AIR)
        continue; 
      outPut.add(entry.getKey());
    } 
    if (withXCarry)
      for (int i = 1; i < 5; i++) {
        Slot craftingSlot = mc.player.inventoryContainer.inventorySlots.get(i);
        ItemStack craftingStack = craftingSlot.getStack();
        if (craftingStack.isEmpty() || craftingStack.getItem() == Items.AIR)
          outPut.add(Integer.valueOf(i)); 
      }  
    return outPut;
  }
  
  public static int findInventoryBlock(Class clazz, boolean offHand) {
    AtomicInteger slot = new AtomicInteger();
    slot.set(-1);
    for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
      if (!isBlock(((ItemStack)entry.getValue()).getItem(), clazz) || (((Integer)entry.getKey()).intValue() == 45 && !offHand))
        continue; 
      slot.set(((Integer)entry.getKey()).intValue());
      return slot.get();
    } 
    return slot.get();
  }
  
  public static boolean isBlock(Item item, Class clazz) {
    if (item instanceof ItemBlock) {
      Block block = ((ItemBlock)item).getBlock();
      return clazz.isInstance(block);
    } 
    return false;
  }
  
  public static void confirmSlot(int slot) {
    mc.player.inventory.currentItem = slot;
    mc.playerController.updateController();
  }
  
  public static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
    return getInventorySlots(9, 44);
  }
  
  private static Map<Integer, ItemStack> getInventorySlots(int currentI, int last) {
    HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<>();
    for (int current = currentI; current <= last; current++)
      fullInventorySlots.put(Integer.valueOf(current), mc.player.inventoryContainer.getInventory().get(current)); 
    return fullInventorySlots;
  }
  
  public static boolean[] switchItem(boolean back, int lastHotbarSlot, boolean switchedItem, Switch mode, Class clazz) {
    boolean[] switchedItemSwitched = { switchedItem, false };
    switch (mode) {
      case NORMAL:
        if (!back && !switchedItem) {
          switchToHotbarSlot(findHotbarBlock(clazz), false);
          switchedItemSwitched[0] = true;
        } else if (back && switchedItem) {
          switchToHotbarSlot(lastHotbarSlot, false);
          switchedItemSwitched[0] = false;
        } 
        switchedItemSwitched[1] = true;
        break;
      case SILENT:
        if (!back && !switchedItem) {
          switchToHotbarSlot(findHotbarBlock(clazz), true);
          switchedItemSwitched[0] = true;
        } else if (back && switchedItem) {
          switchedItemSwitched[0] = false;
          M3dC3t.inventoryManager.recoverSilent(lastHotbarSlot);
        } 
        switchedItemSwitched[1] = true;
        break;
      case NONE:
        switchedItemSwitched[1] = (back || mc.player.inventory.currentItem == findHotbarBlock(clazz));
        break;
    } 
    return switchedItemSwitched;
  }
  
  public static boolean[] switchItemToItem(boolean back, int lastHotbarSlot, boolean switchedItem, Switch mode, Item item) {
    boolean[] switchedItemSwitched = { switchedItem, false };
    switch (mode) {
      case NORMAL:
        if (!back && !switchedItem) {
          switchToHotbarSlot(getItemHotbar(item), false);
          switchedItemSwitched[0] = true;
        } else if (back && switchedItem) {
          switchToHotbarSlot(lastHotbarSlot, false);
          switchedItemSwitched[0] = false;
        } 
        switchedItemSwitched[1] = true;
        break;
      case SILENT:
        if (!back && !switchedItem) {
          switchToHotbarSlot(getItemHotbar(item), true);
          switchedItemSwitched[0] = true;
        } else if (back && switchedItem) {
          switchedItemSwitched[0] = false;
          M3dC3t.inventoryManager.recoverSilent(lastHotbarSlot);
        } 
        switchedItemSwitched[1] = true;
        break;
      case NONE:
        switchedItemSwitched[1] = (back || mc.player.inventory.currentItem == getItemHotbar(item));
        break;
    } 
    return switchedItemSwitched;
  }
  
  public static boolean holdingItem(Class clazz) {
    boolean result = false;
    ItemStack stack = mc.player.getHeldItemMainhand();
    result = isInstanceOf(stack, clazz);
    if (!result) {
      ItemStack offhand = mc.player.getHeldItemOffhand();
      result = isInstanceOf(stack, clazz);
    } 
    return result;
  }
  
  public static boolean isInstanceOf(ItemStack stack, Class clazz) {
    if (stack == null)
      return false; 
    Item item = stack.getItem();
    if (clazz.isInstance(item))
      return true; 
    if (item instanceof ItemBlock) {
      Block block = Block.getBlockFromItem(item);
      return clazz.isInstance(block);
    } 
    return false;
  }
  
  public static int getEmptyXCarry() {
    for (int i = 1; i < 5; ) {
      Slot craftingSlot = mc.player.inventoryContainer.inventorySlots.get(i);
      ItemStack craftingStack = craftingSlot.getStack();
      if (!craftingStack.isEmpty() && craftingStack.getItem() != Items.AIR) {
        i++;
        continue;
      } 
      return i;
    } 
    return -1;
  }
  
  public static boolean isSlotEmpty(int i) {
    Slot slot = mc.player.inventoryContainer.inventorySlots.get(i);
    ItemStack stack = slot.getStack();
    return stack.isEmpty();
  }
  
  public static int convertHotbarToInv(int input) {
    return 36 + input;
  }
  
  public static boolean areStacksCompatible(ItemStack stack1, ItemStack stack2) {
    if (!stack1.getItem().equals(stack2.getItem()))
      return false; 
    if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
      Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
      Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
      if (!block1.material.equals(block2.material))
        return false; 
    } 
    if (!stack1.getDisplayName().equals(stack2.getDisplayName()))
      return false; 
    return (stack1.getItemDamage() == stack2.getItemDamage());
  }
  
  public static EntityEquipmentSlot getEquipmentFromSlot(int slot) {
    if (slot == 5)
      return EntityEquipmentSlot.HEAD; 
    if (slot == 6)
      return EntityEquipmentSlot.CHEST; 
    if (slot == 7)
      return EntityEquipmentSlot.LEGS; 
    return EntityEquipmentSlot.FEET;
  }
  
  public static int findArmorSlot(EntityEquipmentSlot type, boolean binding) {
    int slot = -1;
    float damage = 0.0F;
    for (int i = 9; i < 45; i++) {
      ItemStack s = (Minecraft.getMinecraft()).player.inventoryContainer.getSlot(i).getStack();
      ItemArmor armor;
      if (s.getItem() != Items.AIR && s.getItem() instanceof ItemArmor && (armor = (ItemArmor)s.getItem()).getEquipmentSlot() == type) {
        float currentDamage = (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, s));
        boolean cursed = (binding && EnchantmentHelper.hasBindingCurse(s)), bl = cursed;
        if (currentDamage > damage && !cursed) {
          damage = currentDamage;
          slot = i;
        } 
      } 
    } 
    return slot;
  }
  
  public static int findArmorSlot(EntityEquipmentSlot type, boolean binding, boolean withXCarry) {
    int slot = findArmorSlot(type, binding);
    if (slot == -1 && withXCarry) {
      float damage = 0.0F;
      for (int i = 1; i < 5; i++) {
        Slot craftingSlot = mc.player.inventoryContainer.inventorySlots.get(i);
        ItemStack craftingStack = craftingSlot.getStack();
        ItemArmor armor;
        if (craftingStack.getItem() != Items.AIR && craftingStack.getItem() instanceof ItemArmor && (armor = (ItemArmor)craftingStack.getItem()).getEquipmentSlot() == type) {
          float currentDamage = (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, craftingStack));
          boolean cursed = (binding && EnchantmentHelper.hasBindingCurse(craftingStack)), bl = cursed;
          if (currentDamage > damage && !cursed) {
            damage = currentDamage;
            slot = i;
          } 
        } 
      } 
    } 
    return slot;
  }
  
  public static int findItemInventorySlot(Item item, boolean offHand, boolean withXCarry) {
    int slot = findItemInventorySlot(item, offHand);
    if (slot == -1 && withXCarry)
      for (int i = 1; i < 5; i++) {
        Slot craftingSlot = mc.player.inventoryContainer.inventorySlots.get(i);
        ItemStack craftingStack = craftingSlot.getStack();
        Item craftingStackItem;
        if (craftingStack.getItem() != Items.AIR && (craftingStackItem = craftingStack.getItem()) == item)
          slot = i; 
      }  
    return slot;
  }
  
  public static int findBlockSlotInventory(Class clazz, boolean offHand, boolean withXCarry) {
    int slot = findInventoryBlock(clazz, offHand);
    if (slot == -1 && withXCarry)
      for (int i = 1; i < 5; i++) {
        Slot craftingSlot = mc.player.inventoryContainer.inventorySlots.get(i);
        ItemStack craftingStack = craftingSlot.getStack();
        if (craftingStack.getItem() != Items.AIR) {
          Item craftingStackItem = craftingStack.getItem();
          if (clazz.isInstance(craftingStackItem)) {
            slot = i;
          } else {
            Block block;
            if (craftingStackItem instanceof ItemBlock && clazz.isInstance(block = ((ItemBlock)craftingStackItem).getBlock()))
              slot = i; 
          } 
        } 
      }  
    return slot;
  }
  
  public enum Switch {
    NORMAL, SILENT, NONE;
  }
  
  public static class Task {
    private final int slot;
    
    private final boolean update;
    
    private final boolean quickClick;
    
    public Task() {
      this.update = true;
      this.slot = -1;
      this.quickClick = false;
    }
    
    public Task(int slot) {
      this.slot = slot;
      this.quickClick = false;
      this.update = false;
    }
    
    public Task(int slot, boolean quickClick) {
      this.slot = slot;
      this.quickClick = quickClick;
      this.update = false;
    }
    
    public void run() {
      if (this.update)
        Util.mc.playerController.updateController(); 
      if (this.slot != -1)
        Util.mc.playerController.windowClick(0, this.slot, 0, this.quickClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, (EntityPlayer)Util.mc.player); 
    }
    
    public boolean isSwitching() {
      return !this.update;
    }
  }
}
