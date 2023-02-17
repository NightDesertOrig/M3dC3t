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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class NewSurround extends Module {
  public Setting<Boolean> rotate;
  
  private final Setting<Boolean> center;
  
  BlockPos startPos;
  
  BlockPos FeetPos;
  
  BlockPos CrystalPos;
  
  public NewSurround() {
    super("NewSurround", "Surrounds you in 2b2t.xin with Obsidian.", Module.Category.COMBAT, true, false, false);
    this.rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
    this.center = register(new Setting("TPCenter", Boolean.valueOf(true)));
    this.CrystalPos = null;
  }
  
  public void onEnable() {
    this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
    if (((Boolean)this.center.getValue()).booleanValue())
      M3dC3t.positionManager.setPositionPacket(this.startPos.getX() + 0.5D, this.startPos.getY(), this.startPos.getZ() + 0.5D, true, true, true); 
  }
  
  public void onUpdate() {
    if (this.startPos == null) {
      disable();
      return;
    } 
    if (!this.startPos.equals(EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player))) {
      disable();
      return;
    } 
    BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    EntityPlayer target = getTarget();
    if (target != null) {
      this.FeetPos = new BlockPos(target.posX, target.posY, target.posZ);
    } else {
      this.FeetPos = null;
    } 
    Entity crystal = getEndCrystal();
    if (crystal != null) {
      this.CrystalPos = new BlockPos(crystal.posX, crystal.posY, crystal.posZ);
      if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))) {
        AttackCrystal(pos.add(0, 0, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))) {
        AttackCrystal(pos.add(0, 0, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))) {
        AttackCrystal(pos.add(1, 0, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))) {
        AttackCrystal(pos.add(-1, 0, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1)))) {
        AttackCrystal(pos.add(0, -1, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1)))) {
        AttackCrystal(pos.add(0, -1, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0)))) {
        AttackCrystal(pos.add(1, -1, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0)))) {
        AttackCrystal(pos.add(-1, -1, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2)))) {
        AttackCrystal(pos.add(0, 0, 2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2)))) {
        AttackCrystal(pos.add(0, 0, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0)))) {
        AttackCrystal(pos.add(2, 0, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0)))) {
        AttackCrystal(pos.add(-2, 0, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 2)))) {
        AttackCrystal(pos.add(0, 1, 2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -2)))) {
        AttackCrystal(pos.add(0, 1, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, 1, 0)))) {
        AttackCrystal(pos.add(2, 1, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, 1, 0)))) {
        AttackCrystal(pos.add(-2, 1, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 2)))) {
        AttackCrystal(pos.add(0, -1, 2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -2)))) {
        AttackCrystal(pos.add(0, -1, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, -1, 0)))) {
        AttackCrystal(pos.add(2, -1, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, -1, 0)))) {
        AttackCrystal(pos.add(-2, -1, 0));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) {
        AttackCrystal(pos.add(1, 0, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) {
        AttackCrystal(pos.add(1, 0, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) {
        AttackCrystal(pos.add(-1, 0, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) {
        AttackCrystal(pos.add(-1, 0, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1)))) {
        AttackCrystal(pos.add(1, -1, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1)))) {
        AttackCrystal(pos.add(1, -1, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1)))) {
        AttackCrystal(pos.add(-1, -1, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1)))) {
        AttackCrystal(pos.add(-1, -1, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 1)))) {
        AttackCrystal(pos.add(2, 0, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, -1)))) {
        AttackCrystal(pos.add(2, 0, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, -1)))) {
        AttackCrystal(pos.add(-2, 0, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 1)))) {
        AttackCrystal(pos.add(-2, 0, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 2)))) {
        AttackCrystal(pos.add(1, 0, 2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -2)))) {
        AttackCrystal(pos.add(1, 0, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -2)))) {
        AttackCrystal(pos.add(-1, 0, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 2)))) {
        AttackCrystal(pos.add(-1, 0, 2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, -1, 1)))) {
        AttackCrystal(pos.add(2, -1, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, -1, -1)))) {
        AttackCrystal(pos.add(2, -1, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, -1, -1)))) {
        AttackCrystal(pos.add(-2, -1, -1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, -1, 1)))) {
        AttackCrystal(pos.add(-2, -1, 1));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 2)))) {
        AttackCrystal(pos.add(1, -1, 2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, -2)))) {
        AttackCrystal(pos.add(1, -1, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -2)))) {
        AttackCrystal(pos.add(-1, -1, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 2)))) {
        AttackCrystal(pos.add(-1, -1, 2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 2)))) {
        AttackCrystal(pos.add(2, 0, 2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, -2)))) {
        AttackCrystal(pos.add(2, 0, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, -2)))) {
        AttackCrystal(pos.add(-2, 0, -2));
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 2)))) {
        AttackCrystal(pos.add(-2, 0, 2));
      } 
    } 
    if (mc.world.getBlockState(pos.add(0, -1, -1)).getBlock() != Blocks.AIR && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))) && mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, 0, -1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -2, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1)))) && mc.world.getBlockState(pos.add(0, -1, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, -1, -1));
    } 
    if (mc.world.getBlockState(pos.add(-1, -1, 0)).getBlock() != Blocks.AIR && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))) && mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(-1, 0, 0));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -2, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0)))) && mc.world.getBlockState(pos.add(-1, -1, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(-1, -1, 0));
    } 
    if (mc.world.getBlockState(pos.add(1, -1, 0)).getBlock() != Blocks.AIR && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))) && mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(1, 0, 0));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -2, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0)))) && mc.world.getBlockState(pos.add(1, -1, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(1, -1, 0));
    } 
    if (mc.world.getBlockState(pos.add(0, -1, 1)).getBlock() != Blocks.AIR && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))) && mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, 0, 1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -2, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1)))) && mc.world.getBlockState(pos.add(0, -1, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, -1, 1));
    } else if (checkCrystal(EntityUtil.getVarOffsets(0, 0, 2)) == null && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 2)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2)))) && mc.world.getBlockState(pos.add(0, 0, 2)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, 0, 2));
    } else if (checkCrystal(EntityUtil.getVarOffsets(0, 0, -2)) == null && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -2)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2)))) && mc.world.getBlockState(pos.add(0, 0, -2)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, 0, -2));
    } else if (checkCrystal(EntityUtil.getVarOffsets(2, 0, 0)) == null && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(2, -1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0)))) && mc.world.getBlockState(pos.add(2, 0, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(2, 0, 0));
    } else if (checkCrystal(EntityUtil.getVarOffsets(-3, 0, 0)) == null && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-2, -1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0)))) && mc.world.getBlockState(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(-2, 0, 0));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) && mc.world.getBlockState(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(1, 0, 1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) && mc.world.getBlockState(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(-1, 0, 1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) && mc.world.getBlockState(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(1, 0, -1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) && mc.world.getBlockState(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(-1, 0, -1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 2)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 2)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 2)))) && mc.world.getBlockState(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, 1, 2));
      if (checkCrystal(EntityUtil.getVarOffsets(1, 1, 2)) == null && mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
        perform(pos.add(1, 0, 2));
        perform(pos.add(1, 1, 2));
      } 
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -2)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -2)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -2)))) && mc.world.getBlockState(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, 1, -2));
      if (checkCrystal(EntityUtil.getVarOffsets(-1, 1, -2)) == null && mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
        perform(pos.add(-1, 0, -2));
        perform(pos.add(-1, 1, -2));
      } 
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(2, 1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(2, 1, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(2, 1, 0)))) && mc.world.getBlockState(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(2, 1, 0));
      if (checkCrystal(EntityUtil.getVarOffsets(2, 1, 1)) == null && mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
        perform(pos.add(2, 0, 1));
        perform(pos.add(2, 1, 1));
      } 
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-2, 1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-2, 1, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-2, 1, 0)))) && mc.world.getBlockState(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(-2, 1, 0));
      if (checkCrystal(EntityUtil.getVarOffsets(-2, 1, -1)) == null && mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
        perform(pos.add(-2, 0, -1));
        perform(pos.add(-2, 1, -1));
      } 
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0)))) && mc.world.getBlockState(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR && (mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR || (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))) || ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))))) {
      perform(pos.add(-1, 1, 0));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0)))) && mc.world.getBlockState(pos.add(1, 1, 0)).getBlock() == Blocks.AIR && (mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.AIR || (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))) || ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))))) {
      perform(pos.add(1, 1, 0));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1)))) && mc.world.getBlockState(pos.add(0, 1, 1)).getBlock() == Blocks.AIR && (mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.AIR || (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))) || ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))))) {
      perform(pos.add(0, 1, 1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1)))) && mc.world.getBlockState(pos.add(0, 1, -1)).getBlock() == Blocks.AIR && (mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.AIR || (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))) || ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))))) {
      perform(pos.add(0, 1, -1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, 0)))) && (BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, 0)))) && mc.world.getBlockState(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 2, 0)).getBlock() != Blocks.AIR) {
      perform(pos.add(-1, 2, 0));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, 0)))) && (BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, 0)))) && mc.world.getBlockState(pos.add(1, 2, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 1, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 2, 0)).getBlock() != Blocks.AIR) {
      perform(pos.add(1, 2, 0));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, 1)))) && (BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, 1)))) && mc.world.getBlockState(pos.add(0, 2, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 1, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 2, 0)).getBlock() != Blocks.AIR) {
      perform(pos.add(0, 2, 1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, -1)))) && (BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, -1)))) && mc.world.getBlockState(pos.add(0, 2, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 1, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 2, 0)).getBlock() != Blocks.AIR) {
      perform(pos.add(0, 2, -1));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-3, 0, 0)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-3, -1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-3, 0, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-3, -1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-3, 0, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-3, 0, 0)))) && mc.world.getBlockState(pos.add(-3, 0, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(-3, 0, 0));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(3, 0, 0)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(3, -1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(3, 0, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(3, -1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(3, 0, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(3, 0, 0)))) && mc.world.getBlockState(pos.add(3, 0, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(2, 0, 0)).getBlock() == Blocks.AIR) {
      perform(pos.add(3, 0, 0));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 3)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 3)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 3)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 3)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 3)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 3)))) && mc.world.getBlockState(pos.add(0, 0, 3)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 0, 2)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, 0, 3));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -3)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -3)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -3)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -3)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -3)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -3)))) && mc.world.getBlockState(pos.add(0, 0, -3)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 0, -2)).getBlock() == Blocks.AIR) {
      perform(pos.add(0, 0, -3));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1)))) && mc.world.getBlockState(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(-1, 1, 1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1)))) && mc.world.getBlockState(pos.add(1, 1, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(1, 1, 1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1)))) && mc.world.getBlockState(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(-1, 1, -1));
    } else if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1)))) && mc.world.getBlockState(pos.add(1, 1, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(1, 1, -1));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 1)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 1)))) && mc.world.getBlockState(pos.add(-2, 0, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(-2, 0, 1));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 2)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 2)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 2)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 2)))) && mc.world.getBlockState(pos.add(-1, 0, 2)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(-1, 0, 2));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 1)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 1)))) && mc.world.getBlockState(pos.add(2, 0, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(2, 0, 1));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 2)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 2)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 2)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 2)))) && mc.world.getBlockState(pos.add(1, 0, 2)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
      perform(pos.add(1, 0, 2));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -2)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -2)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -2)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -2)))) && mc.world.getBlockState(pos.add(-1, 0, -2)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(-1, 0, -2));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, -1)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, -1)))) && mc.world.getBlockState(pos.add(-2, 0, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(-2, 0, -1));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, -1)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, -1)))) && mc.world.getBlockState(pos.add(2, 0, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(2, 0, -1));
    } else if ((this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -2)))) && (this.CrystalPos == null || !(new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -2)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -2)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -2)))) && mc.world.getBlockState(pos.add(1, 0, -2)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
      perform(pos.add(1, 0, -2));
    } else if (checkCrystal(EntityUtil.getVarOffsets(-3, 0, 0)) == null && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-3, 0, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-3, -1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-3, 0, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-3, 0, 0)))) && mc.world.getBlockState(pos.add(-3, 0, 0)).getBlock() == Blocks.AIR && ((InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0)))) || ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0)))))) {
      perform(pos.add(-3, 0, 0));
    } else if (checkCrystal(EntityUtil.getVarOffsets(3, 0, 0)) == null && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(3, 0, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(3, -1, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(3, 0, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(3, 0, 0)))) && mc.world.getBlockState(pos.add(3, 0, 0)).getBlock() == Blocks.AIR && ((InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0)))) || ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0)))))) {
      perform(pos.add(3, 0, 0));
    } else if (checkCrystal(EntityUtil.getVarOffsets(0, 0, 3)) == null && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 3)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, 3)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 3)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 3)))) && mc.world.getBlockState(pos.add(0, 0, 3)).getBlock() == Blocks.AIR && ((InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2)))) || ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2)))))) {
      perform(pos.add(0, 0, 3));
    } else if (checkCrystal(EntityUtil.getVarOffsets(0, 0, -3)) == null && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -3)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, -1, -3)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -3)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -3)))) && mc.world.getBlockState(pos.add(0, 0, -3)).getBlock() == Blocks.AIR && ((InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2)))) || ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2)))))) {
      perform(pos.add(0, 0, -3));
    } 
    if (this.CrystalPos != null)
      if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1)))) {
        AttackCrystal(pos.add(1, 1, 1));
        if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1)))) && mc.world.getBlockState(pos.add(1, 1, 1)).getBlock() == Blocks.AIR) {
          perform(pos.add(1, 1, 1));
        } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, 1)))) && mc.world.getBlockState(pos.add(1, 1, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 2, 1)).getBlock() == Blocks.AIR) {
          perform(pos.add(1, 2, 1));
        } 
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1)))) {
        AttackCrystal(pos.add(1, 1, -1));
        if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1)))) && mc.world.getBlockState(pos.add(1, 1, -1)).getBlock() == Blocks.AIR) {
          perform(pos.add(1, 1, -1));
        } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, -1)))) && mc.world.getBlockState(pos.add(1, 1, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(1, 2, -1)).getBlock() == Blocks.AIR) {
          perform(pos.add(1, 2, -1));
        } 
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1)))) {
        AttackCrystal(pos.add(-1, 1, -1));
        if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1)))) && mc.world.getBlockState(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR) {
          perform(pos.add(-1, 1, -1));
        } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, -1)))) && mc.world.getBlockState(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 2, -1)).getBlock() == Blocks.AIR) {
          perform(pos.add(-1, 2, -1));
        } 
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1)))) {
        AttackCrystal(pos.add(-1, 1, 1));
        if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1)))) && mc.world.getBlockState(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR) {
          perform(pos.add(-1, 1, 1));
        } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, 1)))) && mc.world.getBlockState(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 2, 1)).getBlock() == Blocks.AIR) {
          perform(pos.add(-1, 2, 1));
        } 
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1)))) {
        AttackCrystal(pos.add(0, 1, 1));
        if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1)))) && mc.world.getBlockState(pos.add(0, 1, 1)).getBlock() == Blocks.AIR) {
          perform(pos.add(0, 1, 1));
        } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, 1)))) && mc.world.getBlockState(pos.add(0, 1, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 2, 1)).getBlock() == Blocks.AIR) {
          perform(pos.add(0, 2, 1));
        } 
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1)))) {
        AttackCrystal(pos.add(0, 1, -1));
        if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1)))) && mc.world.getBlockState(pos.add(0, 1, -1)).getBlock() == Blocks.AIR) {
          perform(pos.add(0, 1, -1));
        } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(0, 2, -1)))) && mc.world.getBlockState(pos.add(0, 1, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(0, 2, -1)).getBlock() == Blocks.AIR) {
          perform(pos.add(0, 2, -1));
        } 
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0)))) {
        AttackCrystal(pos.add(1, 1, 0));
        if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0)))) && mc.world.getBlockState(pos.add(1, 1, 0)).getBlock() == Blocks.AIR) {
          perform(pos.add(1, 1, 0));
        } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(1, 2, 0)))) && mc.world.getBlockState(pos.add(1, 1, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR) {
          perform(pos.add(1, 2, 0));
        } 
      } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0)))) {
        AttackCrystal(pos.add(-1, 1, 0));
        if ((this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0)))) && (this.FeetPos == null || !(new BlockPos((Vec3i)this.FeetPos)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0)))) && mc.world.getBlockState(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) {
          perform(pos.add(-1, 1, 0));
        } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, 0)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)pos.add(-1, 2, 0)))) && mc.world.getBlockState(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR && mc.world.getBlockState(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR) {
          perform(pos.add(-1, 2, 0));
        } 
      }  
  }
  
  public static void AttackCrystal(BlockPos pos) {
    for (Entity crystal : mc.world.loadedEntityList.stream().filter(e -> (e instanceof net.minecraft.entity.item.EntityEnderCrystal && !e.isDead)).sorted(Comparator.comparing(e -> Float.valueOf(mc.player.getDistance(e)))).collect(Collectors.toList())) {
      if (!(crystal instanceof net.minecraft.entity.item.EntityEnderCrystal) || crystal.getDistanceSq(pos) > 1.0D)
        continue; 
      mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
      mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
    } 
  }
  
  private Entity getEndCrystal() {
    Entity crystal = null;
    for (Entity player : mc.world.loadedEntityList) {
      if (!(player instanceof net.minecraft.entity.item.EntityEnderCrystal))
        continue; 
      crystal = player;
    } 
    return crystal;
  }
  
  private void perform(BlockPos pos) {
    int old = mc.player.inventory.currentItem;
    if (InventoryUtil.findHotbarBlock(BlockObsidian.class) == -1)
      return; 
    mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
    mc.playerController.updateController();
    BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
    mc.player.inventory.currentItem = old;
    mc.playerController.updateController();
  }
  
  Entity checkCrystal(Vec3d[] list) {
    Entity crystal = null;
    int var5 = list.length;
    for (int var6 = 0; var6 < var5; var6++) {
      Vec3d vec3d = list[var6];
      BlockPos position = (new BlockPos(mc.player.getPositionVector())).add(vec3d.x, vec3d.y, vec3d.z);
      for (Entity entity : AntiCev.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(position))) {
        if (!(entity instanceof net.minecraft.entity.item.EntityEnderCrystal) || crystal != null)
          continue; 
        crystal = entity;
      } 
    } 
    return crystal;
  }
  
  private EntityPlayer getTarget() {
    EntityPlayer target = null;
    double distance = 12.0D;
    for (EntityPlayer player : mc.world.playerEntities) {
      if (EntityUtil.isntValid((Entity)player, 12.0D) || M3dC3t.friendManager.isFriend(player.getName()) || mc.player.posY - player.posY >= 5.0D)
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
}
