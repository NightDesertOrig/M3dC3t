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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class AntiCity extends Module {
  private static AntiCity INSTANCE = new AntiCity();
  
  public EntityPlayer target;
  
  public Entity crystal;
  
  int Check = 0;
  
  BlockPos CrystalPos;
  
  BlockPos feet;
  
  BlockPos pos;
  
  public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
  
  public Setting<Boolean> packet = register(new Setting("Packet", Boolean.valueOf(true)));
  
  public AntiCity() {
    super("AntiCity", "Very mart surround extend.", Module.Category.COMBAT, true, false, false);
    setInstance();
  }
  
  public static AntiCity getInstance() {
    if (INSTANCE != null)
      return INSTANCE; 
    INSTANCE = new AntiCity();
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public void onDisable() {
    this.Check = 0;
  }
  
  public void onUpdate() {
    this.pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    if (M3dC3t.moduleManager.isModuleEnabled("Surround")) {
      if ((((getBlock(this.pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(this.pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
        if ((((getBlock(this.pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(this.pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
          if ((((getBlock(this.pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(this.pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
            if ((((getBlock(this.pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(this.pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
              this.Check = 1;    
    } else {
      this.Check = 0;
      return;
    } 
    if (this.Check == 0)
      return; 
    this.target = getTarget();
    if (this.target == null)
      return; 
    this.feet = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
    if (InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN) == -1)
      return; 
    perform(this.pos.add(0, 0, 1));
    perform(this.pos.add(0, 0, -1));
    perform(this.pos.add(1, 0, 0));
    perform(this.pos.add(-1, 0, 0));
    perform(this.pos.add(1, -1, 0));
    perform(this.pos.add(-1, -1, 0));
    perform(this.pos.add(0, -1, 1));
    perform(this.pos.add(0, -1, -1));
    perform(this.pos.add(0, 0, 2));
    perform(this.pos.add(0, 0, -2));
    perform(this.pos.add(2, 0, 0));
    perform(this.pos.add(-2, 0, 0));
    perform(this.pos.add(1, 0, 1));
    perform(this.pos.add(-1, 0, 1));
    perform(this.pos.add(1, 0, -1));
    perform(this.pos.add(-1, 0, -1));
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 0, 0))))
      perform(this.pos.add(1, 1, 0)); 
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 0, 0))))
      perform(this.pos.add(-1, 1, 0)); 
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 0, 1))))
      perform(this.pos.add(0, 1, 1)); 
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 0, -1))))
      perform(this.pos.add(0, 1, -1)); 
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 0, 1))))
      perform(this.pos.add(1, 1, 1)); 
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 0, -1))))
      perform(this.pos.add(1, 1, -1)); 
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 0, 1))))
      perform(this.pos.add(-1, 1, 1)); 
    if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 0, -1))))
      perform(this.pos.add(-1, 1, -1)); 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 0, 0))))
      perform(this.pos.add(1, 1, 0)); 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 0, 0))))
      perform(this.pos.add(-1, 1, 0)); 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 0, 1))))
      perform(this.pos.add(0, 1, 1)); 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 0, -1))))
      perform(this.pos.add(0, 1, -1)); 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 0, 1))))
      perform(this.pos.add(1, 1, 1)); 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 0, -1))))
      perform(this.pos.add(1, 1, -1)); 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 0, 1))))
      perform(this.pos.add(-1, 1, 1)); 
    if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 0, -1))))
      perform(this.pos.add(-1, 1, -1)); 
    if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(1, -1, 0))))
      perform(this.pos.add(2, 0, 0)); 
    perform(this.pos.add(2, 1, 0));
    perform(this.pos.add(3, 0, 0));
    if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(-1, -1, 0))))
      perform(this.pos.add(-2, 0, 0)); 
    perform(this.pos.add(-2, 1, 0));
    perform(this.pos.add(-3, 0, 0));
    if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(0, -1, 1))))
      perform(this.pos.add(0, 0, 2)); 
    perform(this.pos.add(0, 1, 2));
    perform(this.pos.add(0, 0, 3));
    if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(0, -1, -1))))
      perform(this.pos.add(0, 0, -2)); 
    perform(this.pos.add(0, 1, -2));
    perform(this.pos.add(0, 0, -3));
    if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(2, -1, 0))))
      perform(this.pos.add(1, 0, 0)); 
    perform(this.pos.add(2, 1, 0));
    perform(this.pos.add(3, 0, 0));
    if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(-2, -1, 0))))
      perform(this.pos.add(-1, 0, 0)); 
    perform(this.pos.add(-2, 1, 0));
    perform(this.pos.add(-3, 0, 0));
    if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(0, -1, 2))))
      perform(this.pos.add(0, 0, 1)); 
    perform(this.pos.add(0, 1, 2));
    perform(this.pos.add(0, 0, 3));
    if ((new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(0, -1, -2))))
      perform(this.pos.add(0, 0, -1)); 
    perform(this.pos.add(0, 1, -2));
    perform(this.pos.add(0, 0, -3));
    this.crystal = getEndCrystal();
    if (this.crystal == null)
      return; 
    this.CrystalPos = new BlockPos(this.crystal.posX, this.crystal.posY, this.crystal.posZ);
    if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, 1)))) {
      AttackCrystal(this.pos.add(1, 1, 1));
      if ((this.feet == null || !(new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, 1)))) && (this.feet == null || !(new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(1, 0, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, 1)))) && mc.world.getBlockState(this.pos.add(1, 1, 1)).getBlock() == Blocks.AIR) {
        perform(this.pos.add(1, 1, 1));
      } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 2, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 2, 1)))) && mc.world.getBlockState(this.pos.add(1, 1, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(this.pos.add(1, 2, 1)).getBlock() == Blocks.AIR) {
        perform(this.pos.add(1, 2, 1));
      } 
    } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, -1)))) {
      AttackCrystal(this.pos.add(1, 1, -1));
      if ((this.feet == null || !(new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, -1)))) && (this.feet == null || !(new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(1, 0, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, -1)))) && mc.world.getBlockState(this.pos.add(1, 1, -1)).getBlock() == Blocks.AIR) {
        perform(this.pos.add(1, 1, -1));
      } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 2, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 2, -1)))) && mc.world.getBlockState(this.pos.add(1, 1, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(this.pos.add(1, 2, -1)).getBlock() == Blocks.AIR) {
        perform(this.pos.add(1, 2, -1));
      } 
    } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, -1)))) {
      AttackCrystal(this.pos.add(-1, 1, -1));
      if ((this.feet == null || !(new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, -1)))) && (this.feet == null || !(new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(-1, 0, -1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, -1)))) && mc.world.getBlockState(this.pos.add(-1, 1, -1)).getBlock() == Blocks.AIR) {
        perform(this.pos.add(-1, 1, -1));
      } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 2, -1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 2, -1)))) && mc.world.getBlockState(this.pos.add(-1, 1, -1)).getBlock() == Blocks.AIR && mc.world.getBlockState(this.pos.add(-1, 2, -1)).getBlock() == Blocks.AIR) {
        perform(this.pos.add(-1, 2, -1));
      } 
    } else if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, 1)))) {
      AttackCrystal(this.pos.add(-1, 1, 1));
      if ((this.feet == null || !(new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, 1)))) && (this.feet == null || !(new BlockPos((Vec3i)this.feet)).equals(new BlockPos((Vec3i)this.pos.add(-1, 0, 1)))) && ((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, 1)))) && mc.world.getBlockState(this.pos.add(-1, 1, 1)).getBlock() == Blocks.AIR) {
        perform(this.pos.add(-1, 1, 1));
      } else if (((BreakCheck.Instance()).BrokenPos == null || !(new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 2, 1)))) && (InstantMine.breakPos == null || !(new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 2, 1)))) && mc.world.getBlockState(this.pos.add(-1, 1, 1)).getBlock() == Blocks.AIR && mc.world.getBlockState(this.pos.add(-1, 2, 1)).getBlock() == Blocks.AIR) {
        perform(this.pos.add(-1, 2, 1));
      } 
    } 
    if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 1, 1)))) {
      AttackCrystal(this.pos.add(0, 1, 1));
      perform(this.pos.add(0, 1, 1));
      if (getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR)
        if (getBlock(this.pos.add(1, 1, 1)).getBlock() != Blocks.AIR) {
          perform(this.pos.add(1, 2, 1));
        } else if (getBlock(this.pos.add(1, 0, 1)).getBlock() != Blocks.AIR) {
          perform(this.pos.add(1, 1, 1));
        }  
      if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 1, 1))))
        perform(this.pos.add(0, 2, 1)); 
      if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 1, 1))))
        perform(this.pos.add(0, 2, 1)); 
    } 
    if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 1, -1)))) {
      AttackCrystal(this.pos.add(0, 1, -1));
      perform(this.pos.add(0, 1, -1));
      if (getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR)
        if (getBlock(this.pos.add(-1, 1, -1)).getBlock() != Blocks.AIR) {
          perform(this.pos.add(-1, 2, -1));
        } else if (getBlock(this.pos.add(-1, 0, -1)).getBlock() != Blocks.AIR) {
          perform(this.pos.add(-1, 1, -1));
        }  
      if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 1, -1))))
        perform(this.pos.add(0, 2, -1)); 
      if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(0, 1, -1))))
        perform(this.pos.add(0, 2, -1)); 
    } 
    if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, 0)))) {
      AttackCrystal(this.pos.add(1, 1, 0));
      perform(this.pos.add(1, 1, 0));
      if (getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR)
        if (getBlock(this.pos.add(1, 1, 1)).getBlock() != Blocks.AIR) {
          perform(this.pos.add(1, 2, 1));
        } else if (getBlock(this.pos.add(1, 0, 1)).getBlock() != Blocks.AIR) {
          perform(this.pos.add(1, 1, 1));
        }  
      if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, 0))))
        perform(this.pos.add(1, 2, 0)); 
      if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(1, 1, 0))))
        perform(this.pos.add(1, 2, 0)); 
    } 
    if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, 0)))) {
      AttackCrystal(this.pos.add(-1, 1, 0));
      perform(this.pos.add(-1, 1, 0));
      if (getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR)
        if (getBlock(this.pos.add(-1, 1, -1)).getBlock() != Blocks.AIR) {
          perform(this.pos.add(-1, 2, -1));
        } else if (getBlock(this.pos.add(-1, 0, -1)).getBlock() != Blocks.AIR) {
          perform(this.pos.add(-1, 1, -1));
        }  
      if (InstantMine.breakPos != null && (new BlockPos((Vec3i)InstantMine.breakPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, 0))))
        perform(this.pos.add(-1, 2, 0)); 
      if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.pos.add(-1, 1, 0))))
        perform(this.pos.add(-1, 2, 0)); 
    } 
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
      mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
      mc.playerController.updateController();
      BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
      mc.player.inventory.currentItem = old;
      mc.playerController.updateController();
    } 
    this.crystal = getEndCrystal();
    if (this.crystal == null)
      return; 
    this.CrystalPos = new BlockPos(this.crystal.posX, this.crystal.posY, this.crystal.posZ);
    if ((new BlockPos((Vec3i)this.CrystalPos)).equals(new BlockPos((Vec3i)pos2)))
      AttackCrystal(pos2); 
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
  
  private IBlockState getBlock(BlockPos block) {
    return mc.world.getBlockState(block);
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
