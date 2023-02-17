//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.event.events.ProcessRightClickBlockEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import dev.madcat.m3dc3t.util.Timer;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class Offhand extends Module {
  private static Offhand instance;
  
  private final Queue<InventoryUtil.Task> taskList = new ConcurrentLinkedQueue<>();
  
  private final Timer timer = new Timer();
  
  private final Timer secondTimer = new Timer();
  
  public Setting<Boolean> OnlyTotem = register(new Setting("OnlyTotem", Boolean.valueOf(true)));
  
  public Setting<Boolean> soft = register(new Setting("MainHand", Boolean.valueOf(false), v -> ((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public Setting<Boolean> soft2 = register(new Setting("NoAir", Boolean.valueOf(false), v -> ((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public final Setting<Float> health = register(new Setting("Health", Float.valueOf(16.0F), Float.valueOf(0.0F), Float.valueOf(35.9F), v -> ((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public Setting<Boolean> crystal = register(new Setting("Crystal", Boolean.valueOf(true), v -> !((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public Setting<Float> crystalHealth = register(new Setting("CrystalHP", Float.valueOf(13.0F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> !((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public Setting<Float> crystalHoleHealth = register(new Setting("CrystalHoleHP", Float.valueOf(3.5F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> !((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public Setting<Boolean> gapple = register(new Setting("Gapple", Boolean.valueOf(true), v -> !((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public Setting<Boolean> armorCheck = register(new Setting("ArmorCheck", Boolean.valueOf(true), v -> !((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public Setting<Integer> actions = register(new Setting("Packets", Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(4), v -> !((Boolean)this.OnlyTotem.getValue()).booleanValue()));
  
  public Mode2 currentMode = Mode2.TOTEMS;
  
  public int totems = 0;
  
  public int crystals = 0;
  
  public int gapples = 0;
  
  public int lastTotemSlot = -1;
  
  public int lastGappleSlot = -1;
  
  public int lastCrystalSlot = -1;
  
  public int lastObbySlot = -1;
  
  private int numOfTotems;
  
  private int preferredTotemSlot;
  
  public int lastWebSlot = -1;
  
  public boolean holdingCrystal = false;
  
  public boolean holdingTotem = false;
  
  public boolean holdingGapple = false;
  
  public boolean didSwitchThisTick = false;
  
  private boolean second = false;
  
  private boolean switchedForHealthReason = false;
  
  public Offhand() {
    super("AutoOffHand", "Allows you to switch up your Offhand.", Module.Category.COMBAT, true, false, false);
    instance = this;
  }
  
  public static Offhand getInstance() {
    if (instance == null)
      instance = new Offhand(); 
    return instance;
  }
  
  @SubscribeEvent
  public void onUpdateWalkingPlayer(ProcessRightClickBlockEvent event) {
    if (!((Boolean)this.OnlyTotem.getValue()).booleanValue() && event.hand == EnumHand.MAIN_HAND && event.stack.getItem() == Items.END_CRYSTAL && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.objectMouseOver != null && event.pos == mc.objectMouseOver.getBlockPos()) {
      event.setCanceled(true);
      mc.player.setActiveHand(EnumHand.OFF_HAND);
      mc.playerController.processRightClick((EntityPlayer)mc.player, (World)mc.world, EnumHand.OFF_HAND);
    } 
  }
  
  private boolean findTotems() {
    this.numOfTotems = 0;
    AtomicInteger preferredTotemSlotStackSize = new AtomicInteger();
    preferredTotemSlotStackSize.set(-2147483648);
    getInventoryAndHotbarSlots().forEach((slotKey, slotValue) -> {
          int numOfTotemsInStack = 0;
          if (slotValue.getItem().equals(Items.TOTEM_OF_UNDYING)) {
            numOfTotemsInStack = slotValue.getCount();
            if (preferredTotemSlotStackSize.get() < numOfTotemsInStack) {
              preferredTotemSlotStackSize.set(numOfTotemsInStack);
              this.preferredTotemSlot = slotKey.intValue();
            } 
          } 
          this.numOfTotems += numOfTotemsInStack;
        });
    if (mc.player.getHeldItemOffhand().getItem().equals(Items.TOTEM_OF_UNDYING))
      this.numOfTotems += mc.player.getHeldItemOffhand().getCount(); 
    return (this.numOfTotems != 0);
  }
  
  private static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
    return getInventorySlots(9);
  }
  
  private static Map<Integer, ItemStack> getInventorySlots(int current) {
    HashMap<Object, Object> fullInventorySlots;
    for (fullInventorySlots = new HashMap<>(); current <= 44; current++)
      fullInventorySlots.put(Integer.valueOf(current), mc.player.inventoryContainer.getInventory().get(current)); 
    return (Map)fullInventorySlots;
  }
  
  public void onUpdate() {
    if (!((Boolean)this.OnlyTotem.getValue()).booleanValue()) {
      if (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer)
        return; 
      if (this.timer.passedMs(50L)) {
        if (mc.player != null && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && Mouse.isButtonDown(1)) {
          mc.player.setActiveHand(EnumHand.OFF_HAND);
          mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(1);
        } 
      } else if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
        mc.gameSettings.keyBindUseItem.pressed = false;
      } 
      if (nullCheck())
        return; 
      doOffhand();
      if (this.secondTimer.passedMs(50L) && this.second) {
        this.second = false;
        this.timer.reset();
      } 
    } else {
      doTotem();
    } 
  }
  
  @SubscribeEvent
  public void onPacketSend(PacketEvent.Send event) {
    if (!((Boolean)this.OnlyTotem.getValue()).booleanValue() && fullNullCheck() && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && mc.gameSettings.keyBindUseItem.isKeyDown())
      if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
        CPacketPlayerTryUseItemOnBlock packet2 = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
        if (packet2.getHand() == EnumHand.MAIN_HAND) {
          if (this.timer.passedMs(50L)) {
            mc.player.setActiveHand(EnumHand.OFF_HAND);
            mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
          } 
          event.setCanceled(true);
        } 
      } else {
        CPacketPlayerTryUseItem packet;
        if (event.getPacket() instanceof CPacketPlayerTryUseItem && (packet = (CPacketPlayerTryUseItem)event.getPacket()).getHand() == EnumHand.OFF_HAND && !this.timer.passedMs(50L))
          event.setCanceled(true); 
      }  
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)
      return "Crystal"; 
    if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
      return "Totem"; 
    if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE)
      return "Gapple"; 
    return null;
  }
  
  public void doTotem() {
    if (findTotems())
      if (!((Boolean)this.soft.getValue()).booleanValue()) {
        if (!(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer) && (
          (Boolean)this.soft2.getValue()).booleanValue() && 
          mc.player.getHeldItemOffhand().getItem().equals(Items.AIR)) {
          boolean offhandEmptyPreSwitch = mc.player.getHeldItemOffhand().getItem().equals(Items.AIR);
          mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
          mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
          if (offhandEmptyPreSwitch)
            mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player); 
        } 
        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= ((Float)this.health.getValue()).floatValue() && 
          !mc.player.getHeldItemOffhand().getItem().equals(Items.TOTEM_OF_UNDYING)) {
          boolean offhandEmptyPreSwitch = mc.player.getHeldItemOffhand().getItem().equals(Items.AIR);
          mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
          mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
          if (offhandEmptyPreSwitch)
            mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player); 
        } 
      } else {
        if (!(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer) && (
          (Boolean)this.soft2.getValue()).booleanValue() && 
          !mc.player.getHeldItemMainhand().getItem().equals(Items.AIR)) {
          mc.player.inventory.currentItem = 0;
          boolean mainhandEmptyPreSwitch = mc.player.getHeldItemMainhand().getItem().equals(Items.AIR);
          mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
          mc.playerController.windowClick(0, 36, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
          if (mainhandEmptyPreSwitch)
            mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player); 
        } 
        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= ((Float)this.health.getValue()).floatValue() && 
          !mc.player.getHeldItemMainhand().getItem().equals(Items.TOTEM_OF_UNDYING)) {
          mc.player.inventory.currentItem = 0;
          boolean mainhandEmptyPreSwitch = mc.player.getHeldItemMainhand().getItem().equals(Items.AIR);
          mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
          mc.playerController.windowClick(0, 36, 0, ClickType.PICKUP, (EntityPlayer)mc.player);
          if (mainhandEmptyPreSwitch)
            mc.playerController.windowClick(0, this.preferredTotemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.player); 
        } 
      }  
  }
  
  public void doOffhand() {
    this.didSwitchThisTick = false;
    this.holdingCrystal = (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
    this.holdingTotem = (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING);
    this.holdingGapple = (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE);
    this.totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum();
    if (this.holdingTotem)
      this.totems += mc.player.inventory.offHandInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum(); 
    this.crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.END_CRYSTAL)).mapToInt(ItemStack::getCount).sum();
    if (this.holdingCrystal)
      this.crystals += mc.player.inventory.offHandInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.END_CRYSTAL)).mapToInt(ItemStack::getCount).sum(); 
    this.gapples = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.GOLDEN_APPLE)).mapToInt(ItemStack::getCount).sum();
    if (this.holdingGapple)
      this.gapples += mc.player.inventory.offHandInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.GOLDEN_APPLE)).mapToInt(ItemStack::getCount).sum(); 
    doSwitch();
  }
  
  public void doSwitch() {
    int lastSlot;
    this.currentMode = Mode2.TOTEMS;
    if (((Boolean)this.gapple.getValue()).booleanValue() && mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemSword && mc.gameSettings.keyBindUseItem.isKeyDown()) {
      this.currentMode = Mode2.GAPPLES;
    } else if (this.currentMode != Mode2.CRYSTALS && ((Boolean)this.crystal.getValue()).booleanValue() && ((EntityUtil.isSafe((Entity)mc.player) && EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.crystalHoleHealth.getValue()).floatValue()) || EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.crystalHealth.getValue()).floatValue())) {
      this.currentMode = Mode2.CRYSTALS;
    } 
    if (this.currentMode == Mode2.CRYSTALS && this.crystals == 0)
      setMode(Mode2.TOTEMS); 
    if (this.currentMode == Mode2.CRYSTALS && ((!EntityUtil.isSafe((Entity)mc.player) && EntityUtil.getHealth((Entity)mc.player, true) <= ((Float)this.crystalHealth.getValue()).floatValue()) || EntityUtil.getHealth((Entity)mc.player, true) <= ((Float)this.crystalHoleHealth.getValue()).floatValue())) {
      if (this.currentMode == Mode2.CRYSTALS)
        this.switchedForHealthReason = true; 
      setMode(Mode2.TOTEMS);
    } 
    if (this.switchedForHealthReason && ((EntityUtil.isSafe((Entity)mc.player) && EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.crystalHoleHealth.getValue()).floatValue()) || EntityUtil.getHealth((Entity)mc.player, true) > ((Float)this.crystalHealth.getValue()).floatValue())) {
      setMode(Mode2.CRYSTALS);
      this.switchedForHealthReason = false;
    } 
    if (this.currentMode == Mode2.CRYSTALS && ((Boolean)this.armorCheck.getValue()).booleanValue() && (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == Items.AIR || mc.player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == Items.AIR))
      setMode(Mode2.TOTEMS); 
    if (mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiContainer && !(mc.currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory))
      return; 
    Item currentOffhandItem = mc.player.getHeldItemOffhand().getItem();
    switch (this.currentMode) {
      case TOTEMS:
        if (this.totems <= 0 || this.holdingTotem)
          break; 
        this.lastTotemSlot = InventoryUtil.findItemInventorySlot(Items.TOTEM_OF_UNDYING, false);
        lastSlot = getLastSlot(currentOffhandItem, this.lastTotemSlot);
        putItemInOffhand(this.lastTotemSlot, lastSlot);
        break;
      case GAPPLES:
        if (this.gapples <= 0 || this.holdingGapple)
          break; 
        this.lastGappleSlot = InventoryUtil.findItemInventorySlot(Items.GOLDEN_APPLE, false);
        lastSlot = getLastSlot(currentOffhandItem, this.lastGappleSlot);
        putItemInOffhand(this.lastGappleSlot, lastSlot);
        break;
      default:
        if (this.crystals <= 0 || this.holdingCrystal)
          break; 
        this.lastCrystalSlot = InventoryUtil.findItemInventorySlot(Items.END_CRYSTAL, false);
        lastSlot = getLastSlot(currentOffhandItem, this.lastCrystalSlot);
        putItemInOffhand(this.lastCrystalSlot, lastSlot);
        break;
    } 
    for (int i = 0; i < ((Integer)this.actions.getValue()).intValue(); i++) {
      InventoryUtil.Task task = this.taskList.poll();
      if (task != null) {
        task.run();
        if (task.isSwitching())
          this.didSwitchThisTick = true; 
      } 
    } 
  }
  
  private int getLastSlot(Item item, int slotIn) {
    if (item == Items.END_CRYSTAL)
      return this.lastCrystalSlot; 
    if (item == Items.GOLDEN_APPLE)
      return this.lastGappleSlot; 
    if (item == Items.TOTEM_OF_UNDYING)
      return this.lastTotemSlot; 
    if (InventoryUtil.isBlock(item, BlockObsidian.class))
      return this.lastObbySlot; 
    if (InventoryUtil.isBlock(item, BlockWeb.class))
      return this.lastWebSlot; 
    if (item == Items.AIR)
      return -1; 
    return slotIn;
  }
  
  private void putItemInOffhand(int slotIn, int slotOut) {
    if (slotIn != -1 && this.taskList.isEmpty()) {
      this.taskList.add(new InventoryUtil.Task(slotIn));
      this.taskList.add(new InventoryUtil.Task(45));
      this.taskList.add(new InventoryUtil.Task(slotOut));
      this.taskList.add(new InventoryUtil.Task());
    } 
  }
  
  public void setMode(Mode2 mode) {
    this.currentMode = (this.currentMode == mode) ? Mode2.TOTEMS : mode;
  }
  
  public enum Mode2 {
    TOTEMS, GAPPLES, CRYSTALS;
  }
}
