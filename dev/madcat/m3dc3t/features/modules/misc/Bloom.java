//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.combat.BreakCheck;
import dev.madcat.m3dc3t.features.modules.combat.InstantMine;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class Bloom extends Module {
  private final Setting<Boolean> rotate;
  
  public Bloom() {
    super("Bloom", "Like youtiao233.", Module.Category.MISC, true, false, false);
    this.rotate = register(new Setting("Rotate", Boolean.valueOf(false)));
  }
  
  public void onEnable() {
    M3dC3t.positionManager.setPositionPacket(EntityUtil.getRoundedBlockPos((Entity)mc.player).getX() + 0.5D, EntityUtil.getRoundedBlockPos((Entity)mc.player).getY(), EntityUtil.getRoundedBlockPos((Entity)mc.player).getZ() + 0.5D, true, true, true);
  }
  
  public void onUpdate() {
    BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    perform(pos.add(0, 0, -1));
    perform(pos.add(0, 0, -2));
    perform(pos.add(1, 0, -1));
    perform(pos.add(-1, 0, -1));
    perform(pos.add(0, 1, -2));
    perform(pos.add(0, 1, -1));
    perform(pos.add(0, 0, 1));
    perform(pos.add(0, 0, 2));
    perform(pos.add(1, 0, 1));
    perform(pos.add(-1, 0, 1));
    perform(pos.add(0, 1, 2));
    perform(pos.add(0, 1, 1));
    perform(pos.add(1, 0, 0));
    perform(pos.add(2, 0, 0));
    perform(pos.add(1, 0, 1));
    perform(pos.add(1, 0, -1));
    perform(pos.add(2, 1, 0));
    perform(pos.add(1, 1, 0));
    perform(pos.add(-1, 0, 0));
    perform(pos.add(-2, 0, 0));
    perform(pos.add(-1, 0, 1));
    perform(pos.add(-1, 0, -1));
    perform(pos.add(-2, 1, 0));
    perform(pos.add(-1, 1, 0));
    perform(pos.add(-1, 2, 0));
    perform(pos.add(1, 2, 0));
    perform(pos.add(0, 2, 1));
    perform(pos.add(0, 2, -1));
    perform(pos.add(-1, 3, 0));
    perform(pos.add(1, 3, 0));
    perform(pos.add(0, 3, 1));
    perform(pos.add(0, 3, -1));
    perform(pos.add(0, 3, 0));
    perform(pos.add(0, 4, 0));
    perform(pos.add(-1, 1, -1));
    perform(pos.add(1, 1, -1));
    perform(pos.add(-1, 1, 1));
    perform(pos.add(1, 1, 1));
    perform(pos.add(3, 0, 0));
    perform(pos.add(2, 0, -1));
    perform(pos.add(2, 0, 1));
    perform(pos.add(-3, 0, 0));
    perform(pos.add(-2, 0, -1));
    perform(pos.add(-2, 0, 1));
    perform(pos.add(0, 0, 3));
    perform(pos.add(1, 0, 2));
    perform(pos.add(-1, 0, 2));
    perform(pos.add(0, 0, -3));
    perform(pos.add(1, 0, -2));
    perform(pos.add(-1, 0, -2));
    perform(pos.add(0, -1, -1));
    perform(pos.add(0, -1, 1));
    perform(pos.add(1, -1, 0));
    perform(pos.add(-1, -1, 0));
    perform(pos.add(0, 2, 0));
    perform(pos.add(1, 2, -1));
    perform(pos.add(1, 2, 1));
    perform(pos.add(-1, 2, -1));
    perform(pos.add(-1, 2, 1));
    perform(pos.add(1, 3, -1));
    perform(pos.add(1, 3, 1));
    perform(pos.add(-1, 3, -1));
    perform(pos.add(-1, 3, 1));
    perform(pos.add(1, 4, -1));
    perform(pos.add(1, 4, 1));
    perform(pos.add(-1, 4, -1));
    perform(pos.add(-1, 4, 1));
  }
  
  private void perform(BlockPos pos2) {
    int old = mc.player.inventory.currentItem;
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos2)))
      return; 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos2)))
      return; 
    if (InventoryUtil.findHotbarBlock(BlockObsidian.class) == -1)
      return; 
    if (mc.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
      mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
      mc.playerController.updateController();
      BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
      mc.player.inventory.currentItem = old;
      mc.playerController.updateController();
    } 
  }
}
