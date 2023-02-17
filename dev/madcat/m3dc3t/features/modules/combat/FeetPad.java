//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class FeetPad extends Module {
  public EntityPlayer target;
  
  public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
  
  private final Setting<Boolean> air = register(new Setting("WhenSelfInAir", Boolean.valueOf(true)));
  
  private final Setting<Float> range = register(new Setting("Range", Float.valueOf(5.0F), Float.valueOf(1.0F), Float.valueOf(6.0F)));
  
  private final Setting<Boolean> WEB = register(new Setting("Web", Boolean.valueOf(false)));
  
  private final Setting<Boolean> feet2 = register(new Setting("PlaceRange+", Boolean.valueOf(false)));
  
  public FeetPad() {
    super("FeetPad", "Automatically put red stones on the enemy's feet.", Module.Category.COMBAT, true, false, false);
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (this.target != null)
      return this.target.getName(); 
    return null;
  }
  
  public void onUpdate() {
    this.target = getTarget(((Float)this.range.getValue()).floatValue());
    if (this.target == null)
      return; 
    if (!((Boolean)this.air.getValue()).booleanValue() && !mc.player.onGround)
      return; 
    BlockPos pos = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
    perform(pos.add(0, 0, 0));
    if (!((Boolean)this.feet2.getValue()).booleanValue())
      return; 
    perform(pos.add(1, 0, 0));
    perform(pos.add(-1, 0, 0));
    perform(pos.add(0, 0, 1));
    perform(pos.add(0, 0, -1));
  }
  
  private EntityPlayer getTarget(double range) {
    EntityPlayer target = null;
    double distance = range;
    for (EntityPlayer player : mc.world.playerEntities) {
      if (EntityUtil.isntValid((Entity)player, range) || M3dC3t.friendManager.isFriend(player.getName()) || mc.player.posY - player.posY >= 5.0D)
        continue; 
      if (target == null) {
        target = player;
        distance = mc.player.getDistanceSq((Entity)player);
        continue;
      } 
      if (mc.player.getDistanceSq((Entity)player) >= distance)
        continue; 
      target = player;
      distance = mc.player.getDistanceSq((Entity)player);
    } 
    return target;
  }
  
  private void perform(BlockPos pos2) {
    int old = mc.player.inventory.currentItem;
    if (mc.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
      if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos2)))
        return; 
      if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos2)))
        return; 
      if (((Boolean)this.WEB.getValue()).booleanValue() && InventoryUtil.findHotbarBlock(BlockWeb.class) != -1) {
        mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockWeb.class);
        mc.playerController.updateController();
        BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
        mc.player.inventory.currentItem = old;
        mc.playerController.updateController();
      } else if (InventoryUtil.findItemInHotbar(Items.REDSTONE) != -1) {
        mc.player.inventory.currentItem = InventoryUtil.findItemInHotbar(Items.REDSTONE);
        mc.playerController.updateController();
        BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
        mc.player.inventory.currentItem = old;
        mc.playerController.updateController();
      } 
    } 
  }
}
