//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.modules.combat.BreakCheck;
import dev.madcat.m3dc3t.features.modules.combat.InstantMine;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class AntiPistonKick extends Module {
  private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
  
  private final Setting<Boolean> head = register(new Setting("TrapHead", Boolean.valueOf(false)));
  
  BlockPos pos;
  
  public AntiPistonKick() {
    super("AntiPistonKick", "Anti piston push.", Module.Category.PLAYER, true, false, false);
  }
  
  public void onUpdate() {
    this.pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    if (getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.PISTON || getBlock(this.pos.add(0, 2, 1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(0, 1, 2)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK) {
      if (getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.AIR)
        perform(this.pos.add(0, 1, -1)); 
      if (getBlock(this.pos.add(0, 2, -1)).getBlock() == Blocks.AIR)
        perform(this.pos.add(0, 2, -1)); 
      if (getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.PISTON)
        mc.playerController.onPlayerDamageBlock(this.pos.add(0, 1, 1), BlockUtil.getRayTraceFacing(this.pos.add(0, 1, 1))); 
      if (getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && (
        (Boolean)this.head.getValue()).booleanValue())
        perform(this.pos.add(0, 2, 0)); 
    } 
    if (getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.PISTON || getBlock(this.pos.add(0, 2, -1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(0, 1, -2)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
      if (getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.AIR)
        perform(this.pos.add(0, 1, 1)); 
      if (getBlock(this.pos.add(0, 2, 1)).getBlock() == Blocks.AIR)
        perform(this.pos.add(0, 2, 1)); 
      if (getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.PISTON)
        mc.playerController.onPlayerDamageBlock(this.pos.add(0, 1, -1), BlockUtil.getRayTraceFacing(this.pos.add(0, 1, -1))); 
      if (getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && (
        (Boolean)this.head.getValue()).booleanValue())
        perform(this.pos.add(0, 2, 0)); 
    } 
    if (getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.PISTON || getBlock(this.pos.add(1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
      if (getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.AIR)
        perform(this.pos.add(-1, 1, 0)); 
      if (getBlock(this.pos.add(-1, 2, 0)).getBlock() == Blocks.AIR)
        perform(this.pos.add(-1, 2, 0)); 
      if (getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.PISTON)
        mc.playerController.onPlayerDamageBlock(this.pos.add(1, 1, 0), BlockUtil.getRayTraceFacing(this.pos.add(1, 1, 0))); 
      if (getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && (
        (Boolean)this.head.getValue()).booleanValue())
        perform(this.pos.add(0, 2, 0)); 
    } 
    if (getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.PISTON || getBlock(this.pos.add(-1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
      if (getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.AIR)
        perform(this.pos.add(1, 1, 0)); 
      if (getBlock(this.pos.add(1, 2, 0)).getBlock() == Blocks.AIR)
        perform(this.pos.add(1, 2, 0)); 
      if (getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.PISTON)
        mc.playerController.onPlayerDamageBlock(this.pos.add(-1, 1, 0), BlockUtil.getRayTraceFacing(this.pos.add(-1, 1, 0))); 
      if (getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && (
        (Boolean)this.head.getValue()).booleanValue())
        perform(this.pos.add(0, 2, 0)); 
    } 
  }
  
  private IBlockState getBlock(BlockPos block) {
    return mc.world.getBlockState(block);
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (mc.player != null) {
      if (getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.PISTON || getBlock(this.pos.add(-1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK)
        return "Working"; 
      if (getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.PISTON || getBlock(this.pos.add(1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK)
        return "Working"; 
      if (getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.PISTON || getBlock(this.pos.add(0, 2, -1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(0, 1, -2)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK)
        return "Working"; 
      if (getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.PISTON || getBlock(this.pos.add(0, 2, 1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(0, 1, 2)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK)
        return "Working"; 
    } 
    return null;
  }
  
  private void perform(BlockPos pos2) {
    int old = mc.player.inventory.currentItem;
    if (mc.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
      if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos2)))
        return; 
      if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos2)))
        return; 
      if (InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1) {
        mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        mc.playerController.updateController();
        BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
        mc.player.inventory.currentItem = old;
        mc.playerController.updateController();
      } 
    } 
  }
}
