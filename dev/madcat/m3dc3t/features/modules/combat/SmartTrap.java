//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class SmartTrap extends Module {
  public EntityPlayer target;
  
  private final Setting<Float> range = register(new Setting("Range", Float.valueOf(5.0F), Float.valueOf(1.0F), Float.valueOf(8.0F)));
  
  private final Setting<Boolean> web = register(new Setting("WebHead", Boolean.valueOf(false)));
  
  private final Setting<Boolean> feet = register(new Setting("Feet", Boolean.valueOf(false)));
  
  public SmartTrap() {
    super("AutoTrap", "Automatically trap the enemy.", Module.Category.COMBAT, true, false, false);
  }
  
  public void onTick() {
    this.target = getTarget(((Float)this.range.getValue()).floatValue());
    if (this.target == null)
      return; 
    BlockPos people = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
    if (InstantMine.breakPos != null && InstantMine.breakPos.equals(new BlockPos((Vec3i)people.add(0, 2, 0))))
      return; 
    int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
    if (obbySlot == -1)
      return; 
    int webSlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
    if (webSlot == -1)
      return; 
    if (((Boolean)this.web.getValue()).booleanValue()) {
      webSlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
      if (webSlot == -1) {
        webSlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (webSlot == -1)
          return; 
      } 
    } 
    int old = mc.player.inventory.currentItem;
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)people.add(0, 2, 0))))
      return; 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)people.add(0, 2, 0))))
      return; 
    if (getBlock(people.add(0, 2, 0)).getBlock() == Blocks.AIR)
      if (getBlock(people.add(1, 2, 0)).getBlock() != Blocks.AIR || getBlock(people.add(0, 2, 1)).getBlock() != Blocks.AIR || getBlock(people.add(-1, 2, 0)).getBlock() != Blocks.AIR || getBlock(people.add(0, 2, -1)).getBlock() != Blocks.AIR) {
        switchToSlot(webSlot);
        BlockUtil.placeBlock(people.add(0, 2, 0), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(1, 1, 0)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(1, 2, 0), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(-1, 1, 0)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(-1, 2, 0), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(0, 1, 1)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(0, 2, 1), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(0, 1, -1)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(0, 2, -1), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(1, 0, 0)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(1, 1, 0), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(-1, 1, 0), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(0, 0, 1)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(0, 1, 1), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(0, 0, -1)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(0, 1, -1), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      } else if (getBlock(people.add(0, 0, 0)).getBlock() != Blocks.AIR) {
        switchToSlot(obbySlot);
        BlockUtil.placeBlock(people.add(0, 0, -1), EnumHand.MAIN_HAND, false, true, false);
        BlockUtil.placeBlock(people.add(0, 0, 1), EnumHand.MAIN_HAND, false, true, false);
        switchToSlot(old);
      }  
  }
  
  private EntityPlayer getTarget(double range) {
    EntityPlayer target = null;
    double distance = range;
    for (EntityPlayer player : mc.world.playerEntities) {
      if (EntityUtil.isntValid((Entity)player, range) || M3dC3t.friendManager.isFriend(player.getName()) || mc.player.posY - player.posY >= 5.0D)
        continue; 
      if (target == null) {
        target = player;
        distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
        continue;
      } 
      if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance)
        continue; 
      target = player;
      distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
    } 
    return target;
  }
  
  private IBlockState getBlock(BlockPos block) {
    return mc.world.getBlockState(block);
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (this.target != null)
      return this.target.getName(); 
    return null;
  }
  
  private void switchToSlot(int slot) {
    mc.player.inventory.currentItem = slot;
    mc.playerController.updateController();
  }
}
