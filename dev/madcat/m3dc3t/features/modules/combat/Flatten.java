//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class Flatten extends Module {
  public EntityPlayer target;
  
  private final Setting<Float> range = register(new Setting("Range", Float.valueOf(5.0F), Float.valueOf(1.0F), Float.valueOf(6.0F)));
  
  private final Setting<Boolean> negative = register(new Setting("Chest Place", Boolean.valueOf(false)));
  
  private final Setting<Boolean> air = register(new Setting("WhenSelfInAir", Boolean.valueOf(true)));
  
  public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
  
  BlockPos feet;
  
  public Flatten() {
    super("Flatten", "Automatically pave the road for the enemy.", Module.Category.COMBAT, true, false, false);
  }
  
  public void onUpdate() {
    if (fullNullCheck())
      return; 
    this.target = getTarget(((Float)this.range.getValue()).floatValue());
    if (this.target == null)
      return; 
    if (!((Boolean)this.air.getValue()).booleanValue() && !mc.player.onGround)
      return; 
    this.feet = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
    perform(this.feet.add(0, -1, 0));
    perform(this.feet.add(1, -1, 0));
    perform(this.feet.add(-1, -1, 0));
    perform(this.feet.add(0, -1, 1));
    perform(this.feet.add(0, -1, -1));
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
  
  private void perform(BlockPos pos2) {
    int old = mc.player.inventory.currentItem;
    if (mc.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
      if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos2)))
        return; 
      if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)pos2)))
        return; 
      if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)pos2.add(0, -1, 0))))
        return; 
      if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos2)))
        return; 
      if (((Boolean)this.negative.getValue()).booleanValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
        mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        mc.playerController.updateController();
        BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
        mc.player.inventory.currentItem = old;
        mc.playerController.updateController();
      } else if (InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1) {
        mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        mc.playerController.updateController();
        BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
        mc.player.inventory.currentItem = old;
        mc.playerController.updateController();
      } 
    } 
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (this.target != null)
      return this.target.getName(); 
    return null;
  }
}
