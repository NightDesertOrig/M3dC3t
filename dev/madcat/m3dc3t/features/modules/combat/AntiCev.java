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
import net.minecraft.block.state.IBlockState;
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

public class AntiCev extends Module {
  public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
  
  public Setting<Boolean> packet = register(new Setting("Packet", Boolean.valueOf(true)));
  
  public Setting<Boolean> oldCev = register(new Setting("OldCev", Boolean.valueOf(true)));
  
  int CevHigh = 0;
  
  BlockPos startPos;
  
  BlockPos crystal;
  
  public AntiCev() {
    super("AntiCev", "Anti straight line explosion and oblique angle explosion.", Module.Category.COMBAT, true, false, false);
  }
  
  public void onTick() {
    if (!fullNullCheck() && InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1) {
      Vec3d a = mc.player.getPositionVector();
      BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
      Entity target = getTarget();
      if (target != null) {
        this.crystal = new BlockPos(target.posX, target.posY, target.posZ);
      } else {
        this.crystal = null;
      } 
      if (((Boolean)this.oldCev.getValue()).booleanValue()) {
        if (this.crystal == null)
          return; 
        if (getBlock(pos.add(0, 2, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 3, 1)))) {
          perform(pos.add(0, 1, 1));
          perform(pos.add(0, 2, 1));
        } 
        if (getBlock(pos.add(0, 2, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 3, -1)))) {
          perform(pos.add(0, 1, -1));
          perform(pos.add(0, 2, -1));
        } 
        if (getBlock(pos.add(1, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(1, 3, 0)))) {
          perform(pos.add(1, 1, 0));
          perform(pos.add(1, 2, 0));
        } 
        if (getBlock(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(-1, 3, 0)))) {
          perform(pos.add(-1, 1, 0));
          perform(pos.add(-1, 2, 0));
        } 
        if (getBlock(pos.add(0, 2, 0)).getBlock() != Blocks.AIR && getBlock(pos.add(0, 2, 0)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 3, 0)))) {
          mc.player.jump();
          if (!mc.player.onGround) {
            attackCrystal(pos.add(0, 3, 0));
            perform(pos.add(0, 3, 0));
            if (getBlock(pos.add(1, 3, 0)).getBlock() != Blocks.AIR) {
              perform(pos.add(1, 4, 0));
            } else if (getBlock(pos.add(1, 2, 0)).getBlock() != Blocks.AIR) {
              perform(pos.add(1, 3, 0));
            } else if (getBlock(pos.add(1, 1, 0)).getBlock() != Blocks.AIR) {
              perform(pos.add(1, 2, 0));
            } else if (getBlock(pos.add(1, 0, 0)).getBlock() != Blocks.AIR) {
              perform(pos.add(1, 1, 0));
            } 
            perform(pos.add(0, 4, 0));
          } 
        } 
        if (getBlock(pos.add(0, 3, 0)).getBlock() != Blocks.BEDROCK && getBlock(pos.add(0, 2, 0)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 4, 0)))) {
          perform(pos.add(0, 2, 0));
          perform(pos.add(0, 3, 0));
        } 
        if (getBlock(pos.add(0, 3, 0)).getBlock() != Blocks.BEDROCK && getBlock(pos.add(0, 4, 0)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 5, 0)))) {
          perform(pos.add(0, 4, 0));
          perform(pos.add(0, 3, 0));
        } 
      } else {
        if (this.crystal != null && getBlock(pos.add(0, 2, 0)).getBlock() != Blocks.AIR && getBlock(pos.add(0, 2, 0)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 3, 0))))
          this.CevHigh = 1; 
        if (this.CevHigh == 1 && this.crystal != null && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 3, 0)))) {
          mc.player.jump();
          if (!mc.player.onGround)
            attackCrystal(pos.add(0, 3, 0)); 
        } 
        if (this.CevHigh == 1 && checkCrystal(a, EntityUtil.getVarOffsets(0, 3, 0)) == null) {
          perform(pos.add(0, 3, 0));
          if (getBlock(pos.add(0, 3, 0)).getBlock() != Blocks.AIR)
            this.CevHigh = 0; 
        } 
        if (this.crystal != null && getBlock(pos.add(0, 3, 0)).getBlock() != Blocks.BEDROCK && getBlock(pos.add(0, 2, 0)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 4, 0))))
          this.CevHigh = 2; 
        if (this.CevHigh == 2 && checkCrystal(a, EntityUtil.getVarOffsets(0, 4, 0)) == null) {
          perform(pos.add(0, 4, 0));
          if (getBlock(pos.add(0, 4, 0)).getBlock() != Blocks.AIR)
            this.CevHigh = 0; 
        } 
        if (this.crystal != null && getBlock(pos.add(0, 3, 0)).getBlock() != Blocks.BEDROCK && getBlock(pos.add(0, 4, 0)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 5, 0))))
          this.CevHigh = 3; 
        if (this.CevHigh == 3 && checkCrystal(a, EntityUtil.getVarOffsets(0, 5, 0)) == null) {
          perform(pos.add(0, 5, 0));
          if (getBlock(pos.add(0, 5, 0)).getBlock() != Blocks.AIR)
            this.CevHigh = 0; 
        } 
        if (this.crystal == null)
          return; 
        if (M3dC3t.moduleManager.isModuleEnabled("Surround") || M3dC3t.moduleManager.isModuleEnabled("NewSurround")) {
          if (getBlock(pos.add(0, 2, 1)).getBlock() != Blocks.BEDROCK && getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 3, 1)))) {
            this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
            M3dC3t.positionManager.setPositionPacket(this.startPos.getX() + 0.5D, this.startPos.getY(), this.startPos.getZ() + 0.5D, true, true, true);
            attackCrystal(pos.add(0, 3, 1));
            perform(pos.add(0, 3, 1));
          } 
          if (getBlock(pos.add(0, 2, -1)).getBlock() != Blocks.BEDROCK && getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 3, -1)))) {
            this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
            M3dC3t.positionManager.setPositionPacket(this.startPos.getX() + 0.5D, this.startPos.getY(), this.startPos.getZ() + 0.5D, true, true, true);
            attackCrystal(pos.add(0, 3, -1));
            perform(pos.add(0, 3, -1));
          } 
          if (getBlock(pos.add(1, 2, 0)).getBlock() != Blocks.BEDROCK && getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(1, 3, 0)))) {
            this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
            M3dC3t.positionManager.setPositionPacket(this.startPos.getX() + 0.5D, this.startPos.getY(), this.startPos.getZ() + 0.5D, true, true, true);
            attackCrystal(pos.add(1, 3, 0));
            perform(pos.add(1, 3, 0));
          } 
          if (getBlock(pos.add(-1, 2, 0)).getBlock() != Blocks.BEDROCK && getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(-1, 3, 0)))) {
            this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
            M3dC3t.positionManager.setPositionPacket(this.startPos.getX() + 0.5D, this.startPos.getY(), this.startPos.getZ() + 0.5D, true, true, true);
            attackCrystal(pos.add(-1, 3, 0));
            perform(pos.add(-1, 3, 0));
          } 
        } 
      } 
      if (this.crystal == null)
        return; 
      if (getBlock(pos.add(0, 1, 1)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 2, 1)))) {
        attackCrystal(pos.add(0, 2, 1));
        perform(pos.add(0, 2, 1));
        perform(pos.add(0, 3, 1));
        if (getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR)
          if (getBlock(pos.add(1, 1, 1)).getBlock() != Blocks.AIR) {
            perform(pos.add(1, 2, 1));
          } else if (getBlock(pos.add(1, 0, 1)).getBlock() != Blocks.AIR) {
            perform(pos.add(1, 1, 1));
          }  
      } 
      if (getBlock(pos.add(0, 1, -1)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(0, 2, -1)))) {
        attackCrystal(pos.add(0, 2, -1));
        perform(pos.add(0, 2, -1));
        perform(pos.add(0, 3, -1));
        if (getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR)
          if (getBlock(pos.add(-1, 1, -1)).getBlock() != Blocks.AIR) {
            perform(pos.add(-1, 2, -1));
          } else if (getBlock(pos.add(-1, 0, -1)).getBlock() != Blocks.AIR) {
            perform(pos.add(-1, 1, -1));
          }  
      } 
      if (getBlock(pos.add(1, 1, 0)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(1, 2, 0)))) {
        attackCrystal(pos.add(1, 2, 0));
        perform(pos.add(1, 2, 0));
        perform(pos.add(1, 3, 0));
        if (getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR)
          if (getBlock(pos.add(1, 1, 1)).getBlock() != Blocks.AIR) {
            perform(pos.add(1, 2, 1));
          } else if (getBlock(pos.add(1, 0, 1)).getBlock() != Blocks.AIR) {
            perform(pos.add(1, 1, 1));
          }  
      } 
      if (getBlock(pos.add(-1, 1, 0)).getBlock() != Blocks.BEDROCK && (new BlockPos((Vec3i)this.crystal)).equals(new BlockPos((Vec3i)pos.add(-1, 2, 0)))) {
        attackCrystal(pos.add(-1, 2, 0));
        perform(pos.add(-1, 2, 0));
        perform(pos.add(-1, 3, 0));
        if (getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR)
          if (getBlock(pos.add(-1, 1, -1)).getBlock() != Blocks.AIR) {
            perform(pos.add(-1, 2, -1));
          } else if (getBlock(pos.add(-1, 0, -1)).getBlock() != Blocks.AIR) {
            perform(pos.add(-1, 1, -1));
          }  
      } 
    } 
  }
  
  public static void attackCrystal(BlockPos pos) {
    for (Entity crystal : mc.world.loadedEntityList.stream().filter(e -> (e instanceof net.minecraft.entity.item.EntityEnderCrystal && !e.isDead)).sorted(Comparator.comparing(e -> Float.valueOf(mc.player.getDistance(e)))).collect(Collectors.toList())) {
      if (!(crystal instanceof net.minecraft.entity.item.EntityEnderCrystal) || crystal.getDistanceSq(pos) > 1.0D)
        continue; 
      mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
      mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
    } 
  }
  
  private Entity getTarget() {
    Entity target = null;
    for (Entity player : mc.world.loadedEntityList) {
      if (!(player instanceof net.minecraft.entity.item.EntityEnderCrystal))
        continue; 
      target = player;
    } 
    return target;
  }
  
  Entity checkCrystal(Vec3d pos, Vec3d[] list) {
    Entity crystal = null;
    int var5 = list.length;
    for (int var6 = 0; var6 < var5; var6++) {
      Vec3d vec3d = list[var6];
      BlockPos position = (new BlockPos(pos)).add(vec3d.x, vec3d.y, vec3d.z);
      for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(position))) {
        if (!(entity instanceof net.minecraft.entity.item.EntityEnderCrystal) || crystal != null)
          continue; 
        crystal = entity;
      } 
    } 
    return crystal;
  }
  
  private IBlockState getBlock(BlockPos block) {
    return mc.world.getBlockState(block);
  }
  
  private void perform(BlockPos pos2) {
    if (mc.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
      int old = mc.player.inventory.currentItem;
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
