//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class TrapSelf extends Module {
  private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(false)));
  
  private final Setting<Boolean> cev = register(new Setting("HighCev", Boolean.valueOf(false)));
  
  private final Setting<Boolean> civ = register(new Setting("Civ", Boolean.valueOf(false)));
  
  private final Setting<Boolean> cev2 = register(new Setting("HighCev+", Boolean.valueOf(false)));
  
  private final Setting<Boolean> head = register(new Setting("Head", Boolean.valueOf(false)));
  
  private final Setting<Boolean> headB = register(new Setting("HeadButton", Boolean.valueOf(false), v -> ((Boolean)this.head.getValue()).booleanValue()));
  
  private final Setting<Boolean> center = register(new Setting("TPCenter", Boolean.valueOf(true)));
  
  private BlockPos startPos;
  
  public TrapSelf() {
    super("TrapSelf", "Trap self.", Module.Category.COMBAT, true, false, false);
  }
  
  public void onEnable() {
    this.startPos = EntityUtil.getRoundedBlockPos((Entity)mc.player);
    if (((Boolean)this.center.getValue()).booleanValue())
      M3dC3t.positionManager.setPositionPacket(this.startPos.getX() + 0.5D, this.startPos.getY(), this.startPos.getZ() + 0.5D, true, true, true); 
  }
  
  public void onTick() {
    if (this.startPos == null) {
      disable();
      return;
    } 
    if (!this.startPos.equals(EntityUtil.getRoundedBlockPos((Entity)mc.player))) {
      disable();
      return;
    } 
    BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    perform(pos.add(1, 0, 0), EntityUtil.getVarOffsets(1, 0, 0));
    perform(pos.add(1, 1, 0), EntityUtil.getVarOffsets(1, 1, 0));
    perform(pos.add(-1, 0, 0), EntityUtil.getVarOffsets(-1, 0, 0));
    perform(pos.add(-1, 1, 0), EntityUtil.getVarOffsets(-1, 1, 0));
    perform(pos.add(0, 0, 1), EntityUtil.getVarOffsets(0, 0, 1));
    perform(pos.add(0, 1, 1), EntityUtil.getVarOffsets(0, 1, 1));
    perform(pos.add(0, 0, -1), EntityUtil.getVarOffsets(0, 0, -1));
    perform(pos.add(0, 1, -1), EntityUtil.getVarOffsets(0, 1, -1));
    if (((Boolean)this.cev.getValue()).booleanValue()) {
      perform(pos.add(0, 2, -1), EntityUtil.getVarOffsets(0, 2, -1));
      perform(pos.add(0, 3, -1), EntityUtil.getVarOffsets(0, 3, -1));
      perform(pos.add(0, 3, 0), EntityUtil.getVarOffsets(0, 3, 0));
    } 
    if (((Boolean)this.cev2.getValue()).booleanValue()) {
      perform(pos.add(0, 3, -1), EntityUtil.getVarOffsets(0, 3, -1));
      perform(pos.add(0, 4, -1), EntityUtil.getVarOffsets(0, 4, -1));
      perform(pos.add(0, 4, 0), EntityUtil.getVarOffsets(0, 4, 0));
    } 
    if (((Boolean)this.head.getValue()).booleanValue()) {
      perform(pos.add(0, 2, -1), EntityUtil.getVarOffsets(0, 2, -1));
      if (((Boolean)this.headB.getValue()).booleanValue()) {
        BPerform(pos.add(0, 2, 0), EntityUtil.getVarOffsets(0, 2, 0));
      } else {
        perform(pos.add(0, 2, 0), EntityUtil.getVarOffsets(0, 2, 0));
      } 
    } 
    if (((Boolean)this.civ.getValue()).booleanValue()) {
      perform(pos.add(0, 2, -1), EntityUtil.getVarOffsets(0, 2, -1));
      perform(pos.add(0, 2, 1), EntityUtil.getVarOffsets(0, 2, 1));
      perform(pos.add(1, 2, 0), EntityUtil.getVarOffsets(1, 2, 0));
      perform(pos.add(-1, 2, 0), EntityUtil.getVarOffsets(-1, 2, 0));
    } 
  }
  
  private void perform(BlockPos pos2, Vec3d[] list) {
    int old = mc.player.inventory.currentItem;
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos2)))
      return; 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos2)))
      return; 
    if (mc.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
      if (InventoryUtil.findHotbarBlock(BlockObsidian.class) == -1)
        return; 
      if (checkCrystal(mc.player.getPositionVector(), list) != null)
        attackCrystal(pos2); 
      mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
      mc.playerController.updateController();
      BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
      mc.player.inventory.currentItem = old;
      mc.playerController.updateController();
    } 
  }
  
  private void BPerform(BlockPos pos2, Vec3d[] list) {
    int old = mc.player.inventory.currentItem;
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos2)))
      return; 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos2)))
      return; 
    if (mc.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
      if (InventoryUtil.findHotbarBlock(Blocks.WOODEN_BUTTON) == -1) {
        if (InventoryUtil.findHotbarBlock(BlockObsidian.class) == -1)
          return; 
        mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
      } else {
        mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.WOODEN_BUTTON);
      } 
      if (checkCrystal(mc.player.getPositionVector(), list) != null)
        attackCrystal(pos2); 
      mc.playerController.updateController();
      BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
      mc.player.inventory.currentItem = old;
      mc.playerController.updateController();
    } 
  }
  
  Entity checkCrystal(Vec3d pos, Vec3d[] list) {
    Entity crystal = null;
    int var5 = list.length;
    for (int var6 = 0; var6 < var5; var6++) {
      Vec3d vec3d = list[var6];
      BlockPos position = (new BlockPos(pos)).add(vec3d.x, vec3d.y, vec3d.z);
      for (Entity entity : AntiCev.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(position))) {
        if (!(entity instanceof net.minecraft.entity.item.EntityEnderCrystal) || crystal != null)
          continue; 
        crystal = entity;
      } 
    } 
    return crystal;
  }
  
  public static void attackCrystal(BlockPos pos) {
    for (Entity crystal : mc.world.loadedEntityList.stream().filter(e -> (e instanceof net.minecraft.entity.item.EntityEnderCrystal && !e.isDead)).sorted(Comparator.comparing(e -> Float.valueOf(mc.player.getDistance(e)))).collect(Collectors.toList())) {
      if (!(crystal instanceof net.minecraft.entity.item.EntityEnderCrystal) || crystal.getDistanceSq(pos) > 1.0D)
        continue; 
      mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
      mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
    } 
  }
}
