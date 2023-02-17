//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.mixin.mixins.IEntityLivingBase;
import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityUtil implements Util {
  public static final Vec3d[] antiDropOffsetList = new Vec3d[] { new Vec3d(0.0D, -2.0D, 0.0D) };
  
  public static final Vec3d[] platformOffsetList = new Vec3d[] { new Vec3d(0.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(1.0D, -1.0D, 0.0D) };
  
  public static final Vec3d[] legOffsetList = new Vec3d[] { new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 1.0D) };
  
  public static final Vec3d[] OffsetList = new Vec3d[] { new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(0.0D, 2.0D, 0.0D) };
  
  public static final Vec3d[] antiStepOffsetList = new Vec3d[] { new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(0.0D, 2.0D, -1.0D) };
  
  public static final Vec3d[] antiScaffoldOffsetList = new Vec3d[] { new Vec3d(0.0D, 3.0D, 0.0D) };
  
  public static final Vec3d[] doubleLegOffsetList = new Vec3d[] { new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -2.0D), new Vec3d(0.0D, 0.0D, 2.0D) };
  
  public static Block isColliding(double posX, double posY, double posZ) {
    Block block = null;
    if (mc.player != null) {
      AxisAlignedBB bb = (mc.player.getRidingEntity() != null) ? mc.player.getRidingEntity().getEntityBoundingBox().contract(0.0D, 0.0D, 0.0D).offset(posX, posY, posZ) : mc.player.getEntityBoundingBox().contract(0.0D, 0.0D, 0.0D).offset(posX, posY, posZ);
      int y = (int)bb.minY;
      for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; x++) {
        for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; z++)
          block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock(); 
      } 
    } 
    return block;
  }
  
  public static void LocalPlayerfakeJump() {
    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, mc.player.onGround));
    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, mc.player.onGround));
    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, mc.player.onGround));
    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.15610926093821D, mc.player.posZ, mc.player.onGround));
  }
  
  public static BlockPos getLocalPlayerPosFloored() {
    return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
  }
  
  public static boolean isPlayerInHole() {
    BlockPos blockPos = getLocalPlayerPosFloored();
    IBlockState blockState = mc.world.getBlockState(blockPos);
    if (blockState.getBlock() != Blocks.AIR)
      return false; 
    if (mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR)
      return false; 
    if (mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR)
      return false; 
    BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
    int validHorizontalBlocks = 0;
    for (BlockPos touching : touchingBlocks) {
      IBlockState touchingState = mc.world.getBlockState(touching);
      if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock())
        validHorizontalBlocks++; 
    } 
    return (validHorizontalBlocks >= 4);
  }
  
  public static boolean getSurroundWeakness(Vec3d pos, int feetMine, int render) {
    Block block1;
    BlockPos raytrace;
    Block blockb;
    Block block;
    Block block3;
    Block block2;
    Block blocka;
    BlockPos blockPos1;
    Block block4;
    Block block5;
    switch (feetMine) {
      case 1:
        blockPos1 = new BlockPos(pos);
        if (!BlockUtil.canBlockBeSeen((blockPos1.getX() - 2), blockPos1.getY(), blockPos1.getZ()) && Math.sqrt(mc.player.getDistanceSq((blockPos1.getX() - 2), blockPos1.getY(), blockPos1.getZ())) > 3.0D)
          return false; 
        block5 = mc.world.getBlockState((new BlockPos(pos)).add(-2, 1, 0)).getBlock();
        if ((block5 != Blocks.AIR && block5 != Blocks.FIRE) || ((block3 = mc.world.getBlockState((new BlockPos(pos)).add(-2, 0, 0)).getBlock()) != Blocks.AIR && block3 != Blocks.FIRE) || ((block1 = mc.world.getBlockState((new BlockPos(pos)).add(-2, -1, 0)).getBlock()) != Blocks.OBSIDIAN && block1 != Blocks.BEDROCK) || mc.world.getBlockState((new BlockPos(pos)).add(-1, 0, 0)).getBlock() == Blocks.BEDROCK)
          break; 
        return true;
      case 2:
        blockPos1 = new BlockPos(pos);
        if (!BlockUtil.canBlockBeSeen((blockPos1.getX() + 2), blockPos1.getY(), blockPos1.getZ()) && Math.sqrt(mc.player.getDistanceSq((blockPos1.getX() + 2), blockPos1.getY(), blockPos1.getZ())) > 3.0D)
          return false; 
        block5 = mc.world.getBlockState((new BlockPos(pos)).add(2, 1, 0)).getBlock();
        if ((block5 != Blocks.AIR && block5 != Blocks.FIRE) || ((block3 = mc.world.getBlockState((new BlockPos(pos)).add(2, 0, 0)).getBlock()) != Blocks.AIR && block3 != Blocks.FIRE) || ((block1 = mc.world.getBlockState((new BlockPos(pos)).add(2, -1, 0)).getBlock()) != Blocks.OBSIDIAN && block1 != Blocks.BEDROCK) || mc.world.getBlockState((new BlockPos(pos)).add(1, 0, 0)).getBlock() == Blocks.BEDROCK)
          break; 
        return true;
      case 3:
        blockPos1 = new BlockPos(pos);
        if (!BlockUtil.canBlockBeSeen(blockPos1.getX(), blockPos1.getY(), (blockPos1.getZ() - 2)) && Math.sqrt(mc.player.getDistanceSq(blockPos1.getX(), blockPos1.getY(), (blockPos1.getZ() - 2))) > 3.0D)
          return false; 
        block5 = mc.world.getBlockState((new BlockPos(pos)).add(0, 1, -2)).getBlock();
        if ((block5 != Blocks.AIR && block5 != Blocks.FIRE) || ((block3 = mc.world.getBlockState((new BlockPos(pos)).add(0, 0, -2)).getBlock()) != Blocks.AIR && block3 != Blocks.FIRE) || ((block1 = mc.world.getBlockState((new BlockPos(pos)).add(0, -1, -2)).getBlock()) != Blocks.OBSIDIAN && block1 != Blocks.BEDROCK) || mc.world.getBlockState((new BlockPos(pos)).add(0, 0, -1)).getBlock() == Blocks.BEDROCK)
          break; 
        return true;
      case 4:
        blockPos1 = new BlockPos(pos);
        if (!BlockUtil.canBlockBeSeen(blockPos1.getX(), blockPos1.getY(), (blockPos1.getZ() + 2)) && Math.sqrt(mc.player.getDistanceSq(blockPos1.getX(), blockPos1.getY(), (blockPos1.getZ() + 2))) > 3.0D)
          return false; 
        block5 = mc.world.getBlockState((new BlockPos(pos)).add(0, 1, 2)).getBlock();
        if ((block5 != Blocks.AIR && block5 != Blocks.FIRE) || ((block3 = mc.world.getBlockState((new BlockPos(pos)).add(0, 0, 2)).getBlock()) != Blocks.AIR && block3 != Blocks.FIRE) || ((block1 = mc.world.getBlockState((new BlockPos(pos)).add(0, -1, 2)).getBlock()) != Blocks.OBSIDIAN && block1 != Blocks.BEDROCK) || mc.world.getBlockState((new BlockPos(pos)).add(0, 0, 1)).getBlock() == Blocks.BEDROCK)
          break; 
        return true;
      case 5:
        raytrace = new BlockPos(pos);
        if (!BlockUtil.canBlockBeSeen((raytrace.getX() - 1), raytrace.getY(), raytrace.getZ()) && Math.sqrt(mc.player.getDistanceSq((raytrace.getX() - 1), raytrace.getY(), raytrace.getZ())) > 3.0D)
          return false; 
        block2 = mc.world.getBlockState((new BlockPos(pos)).add(-1, 1, 0)).getBlock();
        if ((block2 != Blocks.AIR && block2 != Blocks.FIRE) || mc.world.getBlockState((new BlockPos(pos)).add(-1, 0, 0)).getBlock() == Blocks.BEDROCK)
          break; 
        return true;
      case 6:
        raytrace = new BlockPos(pos);
        if (!BlockUtil.canBlockBeSeen((raytrace.getX() + 1), raytrace.getY(), raytrace.getZ()) && Math.sqrt(mc.player.getDistanceSq((raytrace.getX() + 1), raytrace.getY(), raytrace.getZ())) > 3.0D)
          return false; 
        block2 = mc.world.getBlockState((new BlockPos(pos)).add(1, 1, 0)).getBlock();
        if ((block2 != Blocks.AIR && block2 != Blocks.FIRE) || mc.world.getBlockState((new BlockPos(pos)).add(1, 0, 0)).getBlock() == Blocks.BEDROCK)
          break; 
        return true;
      case 7:
        raytrace = new BlockPos(pos);
        if (!BlockUtil.canBlockBeSeen(raytrace.getX(), raytrace.getY(), (raytrace.getZ() - 1)) && Math.sqrt(mc.player.getDistanceSq(raytrace.getX(), raytrace.getY(), (raytrace.getZ() - 1))) > 3.0D)
          return false; 
        block2 = mc.world.getBlockState((new BlockPos(pos)).add(0, 1, -1)).getBlock();
        if ((block2 != Blocks.AIR && block2 != Blocks.FIRE) || mc.world.getBlockState((new BlockPos(pos)).add(0, 0, -1)).getBlock() == Blocks.BEDROCK)
          break; 
        return true;
      case 8:
        raytrace = new BlockPos(pos);
        if (!BlockUtil.canBlockBeSeen(raytrace.getX(), raytrace.getY(), (raytrace.getZ() + 1)) && Math.sqrt(mc.player.getDistanceSq(raytrace.getX(), raytrace.getY(), (raytrace.getZ() + 1))) > 3.0D)
          return false; 
        block2 = mc.world.getBlockState((new BlockPos(pos)).add(0, 1, 1)).getBlock();
        if ((block2 != Blocks.AIR && block2 != Blocks.FIRE) || mc.world.getBlockState((new BlockPos(pos)).add(0, 0, 1)).getBlock() == Blocks.BEDROCK)
          break; 
        return true;
    } 
    switch (render) {
      case 1:
        block4 = mc.world.getBlockState((new BlockPos(pos)).add(-2, 1, 0)).getBlock();
        if (block4 != Blocks.AIR && 
          block4 != Blocks.FIRE)
          return false; 
        if ((blocka = mc.world.getBlockState((new BlockPos(pos)).add(-2, 0, 0)).getBlock()) != Blocks.AIR && 
          blocka != Blocks.FIRE)
          return false; 
        if ((blockb = mc.world.getBlockState((new BlockPos(pos)).add(-2, -1, 0)).getBlock()) != Blocks.OBSIDIAN && 
          blockb != Blocks.BEDROCK)
          return false; 
        if (mc.world.getBlockState((new BlockPos(pos)).add(-1, 0, 0)).getBlock() == Blocks.BEDROCK)
          return false; 
        return true;
      case 2:
        block4 = mc.world.getBlockState((new BlockPos(pos)).add(2, 1, 0)).getBlock();
        if (block4 != Blocks.AIR && 
          block4 != Blocks.FIRE)
          return false; 
        if ((blocka = mc.world.getBlockState((new BlockPos(pos)).add(2, 0, 0)).getBlock()) != Blocks.AIR && 
          blocka != Blocks.FIRE)
          return false; 
        if ((blockb = mc.world.getBlockState((new BlockPos(pos)).add(2, -1, 0)).getBlock()) != Blocks.OBSIDIAN && 
          blockb != Blocks.BEDROCK)
          return false; 
        if (mc.world.getBlockState((new BlockPos(pos)).add(1, 0, 0)).getBlock() == Blocks.BEDROCK)
          return false; 
        return true;
      case 3:
        block4 = mc.world.getBlockState((new BlockPos(pos)).add(0, 1, -2)).getBlock();
        if (block4 != Blocks.AIR && 
          block4 != Blocks.FIRE)
          return false; 
        if ((blocka = mc.world.getBlockState((new BlockPos(pos)).add(0, 0, -2)).getBlock()) != Blocks.AIR && 
          blocka != Blocks.FIRE)
          return false; 
        if ((blockb = mc.world.getBlockState((new BlockPos(pos)).add(0, -1, -2)).getBlock()) != Blocks.OBSIDIAN && 
          blockb != Blocks.BEDROCK)
          return false; 
        if (mc.world.getBlockState((new BlockPos(pos)).add(0, 0, -1)).getBlock() == Blocks.BEDROCK)
          return false; 
        return true;
      case 4:
        block4 = mc.world.getBlockState((new BlockPos(pos)).add(0, 1, 2)).getBlock();
        if (block4 != Blocks.AIR && 
          block4 != Blocks.FIRE)
          return false; 
        if ((blocka = mc.world.getBlockState((new BlockPos(pos)).add(0, 0, 2)).getBlock()) != Blocks.AIR && 
          blocka != Blocks.FIRE)
          return false; 
        if ((blockb = mc.world.getBlockState((new BlockPos(pos)).add(0, -1, 2)).getBlock()) != Blocks.OBSIDIAN && 
          blockb != Blocks.BEDROCK)
          return false; 
        if (mc.world.getBlockState((new BlockPos(pos)).add(0, 0, 1)).getBlock() == Blocks.BEDROCK)
          return false; 
        return true;
      case 5:
        block = mc.world.getBlockState((new BlockPos(pos)).add(-1, 1, 0)).getBlock();
        if (block != Blocks.AIR && 
          block != Blocks.FIRE)
          return false; 
        if (mc.world.getBlockState((new BlockPos(pos)).add(-1, 0, 0)).getBlock() == Blocks.BEDROCK)
          return false; 
        return true;
      case 6:
        block = mc.world.getBlockState((new BlockPos(pos)).add(1, 1, 0)).getBlock();
        if (block != Blocks.AIR && 
          block != Blocks.FIRE)
          return false; 
        if (mc.world.getBlockState((new BlockPos(pos)).add(1, 0, 0)).getBlock() == Blocks.BEDROCK)
          return false; 
        return true;
      case 7:
        block = mc.world.getBlockState((new BlockPos(pos)).add(0, 1, -1)).getBlock();
        if (block != Blocks.AIR && 
          block != Blocks.FIRE)
          return false; 
        if (mc.world.getBlockState((new BlockPos(pos)).add(0, 0, -1)).getBlock() == Blocks.BEDROCK)
          return false; 
        return true;
      case 8:
        block = mc.world.getBlockState((new BlockPos(pos)).add(0, 1, 1)).getBlock();
        if (block != Blocks.AIR && 
          block != Blocks.FIRE)
          return false; 
        if (mc.world.getBlockState((new BlockPos(pos)).add(0, 0, 1)).getBlock() == Blocks.BEDROCK)
          return false; 
        return true;
    } 
    return false;
  }
  
  public static boolean isOnLiquid() {
    double y = mc.player.posY - 0.03D;
    for (int x = MathHelper.floor(mc.player.posX); x < MathHelper.ceil(mc.player.posX); x++) {
      for (int z = MathHelper.floor(mc.player.posZ); z < MathHelper.ceil(mc.player.posZ); z++) {
        BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
        if (mc.world.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockLiquid)
          return true; 
      } 
    } 
    return false;
  }
  
  public static void resetTimer() {
    mc.timer.tickLength = 50.0F;
  }
  
  public static boolean isInLiquid() {
    if (mc.player != null) {
      if (mc.player.fallDistance >= 3.0F)
        return false; 
      boolean inLiquid = false;
      AxisAlignedBB bb = (mc.player.getRidingEntity() != null) ? mc.player.getRidingEntity().getEntityBoundingBox() : mc.player.getEntityBoundingBox();
      int y = (int)bb.minY;
      for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; x++) {
        for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; z++) {
          Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
          if (!(block instanceof net.minecraft.block.BlockAir)) {
            if (!(block instanceof net.minecraft.block.BlockLiquid))
              return false; 
            inLiquid = true;
          } 
        } 
      } 
      return inLiquid;
    } 
    return false;
  }
  
  public static void attackEntity(Entity entity, boolean packet, EnumHand hand) {
    if (packet) {
      mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
    } else {
      mc.playerController.attackEntity((EntityPlayer)mc.player, entity);
    } 
    mc.player.swingArm(hand);
  }
  
  public static void attackEntity(Entity entity, boolean packet, boolean swingArm) {
    if (packet) {
      mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
    } else {
      mc.playerController.attackEntity((EntityPlayer)mc.player, entity);
    } 
    if (swingArm)
      mc.player.swingArm(EnumHand.MAIN_HAND); 
  }
  
  public static void setTimer(float speed) {
    mc.timer.tickLength = 50.0F / speed;
  }
  
  public static Vec3d interpolateEntity(Entity entity, float time) {
    return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
  }
  
  public static Vec3d getInterpolatedPos(Entity entity, float partialTicks) {
    return (new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)).add(getInterpolatedAmount(entity, partialTicks));
  }
  
  public static Vec3d getInterpolatedRenderPos(Entity entity, float partialTicks) {
    return getInterpolatedPos(entity, partialTicks).subtract((mc.getRenderManager()).renderPosX, (mc.getRenderManager()).renderPosY, (mc.getRenderManager()).renderPosZ);
  }
  
  public static Vec3d getInterpolatedRenderPos(Vec3d vec) {
    return (new Vec3d(vec.x, vec.y, vec.z)).subtract((mc.getRenderManager()).renderPosX, (mc.getRenderManager()).renderPosY, (mc.getRenderManager()).renderPosZ);
  }
  
  public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
    return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
  }
  
  public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
    return getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
  }
  
  public static Vec3d getInterpolatedAmount(Entity entity, float partialTicks) {
    return getInterpolatedAmount(entity, partialTicks, partialTicks, partialTicks);
  }
  
  public static double getBaseMoveSpeed() {
    double baseSpeed = 0.2873D;
    if (mc.player != null && mc.player.isPotionActive(Potion.getPotionById(1))) {
      int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
      baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
    } 
    return baseSpeed;
  }
  
  public static boolean isPassive(Entity entity) {
    if (entity instanceof EntityWolf && ((EntityWolf)entity).isAngry())
      return false; 
    if (entity instanceof net.minecraft.entity.EntityAgeable || entity instanceof net.minecraft.entity.passive.EntityAmbientCreature || entity instanceof net.minecraft.entity.passive.EntitySquid)
      return true; 
    return (entity instanceof EntityIronGolem && ((EntityIronGolem)entity).getRevengeTarget() == null);
  }
  
  public static boolean isSafe(Entity entity, int height, boolean floor, boolean face) {
    return (getUnsafeBlocks(entity, height, floor).size() == 0);
  }
  
  public static boolean isSafe(Entity entity, int height, boolean floor) {
    if (getUnsafeBlocks(entity, height, floor).size() != 0)
      return false; 
    return true;
  }
  
  public static boolean stopSneaking(boolean isSneaking) {
    if (isSneaking && mc.player != null)
      mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING)); 
    return false;
  }
  
  public static boolean isSafe(Entity entity) {
    return isSafe(entity, 0, false, true);
  }
  
  public static BlockPos getPlayerPos(EntityPlayer player) {
    return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
  }
  
  public static List<Vec3d> getUnsafeBlocks(Entity entity, int height, boolean floor) {
    return getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
  }
  
  public static boolean isMobAggressive(Entity entity) {
    if (entity instanceof EntityPigZombie) {
      if (((EntityPigZombie)entity).isArmsRaised() || ((EntityPigZombie)entity).isAngry())
        return true; 
    } else {
      if (entity instanceof EntityWolf)
        return (((EntityWolf)entity).isAngry() && !mc.player.equals(((EntityWolf)entity).getOwner())); 
      if (entity instanceof EntityEnderman)
        return ((EntityEnderman)entity).isScreaming(); 
    } 
    return isHostileMob(entity);
  }
  
  public static boolean isNeutralMob(Entity entity) {
    return (entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman);
  }
  
  public static boolean isProjectile(Entity entity) {
    return (entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball);
  }
  
  public static boolean isVehicle(Entity entity) {
    return (entity instanceof net.minecraft.entity.item.EntityBoat || entity instanceof net.minecraft.entity.item.EntityMinecart);
  }
  
  public static boolean isFriendlyMob(Entity entity) {
    return ((entity.isCreatureType(EnumCreatureType.CREATURE, false) && !isNeutralMob(entity)) || entity.isCreatureType(EnumCreatureType.AMBIENT, false) || entity instanceof net.minecraft.entity.passive.EntityVillager || entity instanceof EntityIronGolem || (isNeutralMob(entity) && !isMobAggressive(entity)));
  }
  
  public static boolean isHostileMob(Entity entity) {
    return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity));
  }
  
  public static List<Vec3d> getUnsafeBlocksFromVec3d(Vec3d pos, int height, boolean floor) {
    ArrayList<Vec3d> vec3ds = new ArrayList<>();
    for (Vec3d vector : getOffsets(height, floor)) {
      BlockPos targetPos = (new BlockPos(pos)).add(vector.x, vector.y, vector.z);
      Block block = mc.world.getBlockState(targetPos).getBlock();
      if (block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockFire || block instanceof net.minecraft.block.BlockDeadBush || block instanceof net.minecraft.block.BlockSnow)
        vec3ds.add(vector); 
    } 
    return vec3ds;
  }
  
  public static boolean isInHole(Entity entity) {
    return isBlockValid(new BlockPos(entity.posX, entity.posY, entity.posZ));
  }
  
  public static boolean isBlockValid(BlockPos blockPos) {
    return (isBedrockHole(blockPos) || isObbyHole(blockPos) || isBothHole(blockPos));
  }
  
  public static boolean isObbyHole(BlockPos blockPos) {
    BlockPos[] touchingBlocks;
    BlockPos[] arrayOfBlockPos1;
    int i;
    byte b;
    for (arrayOfBlockPos1 = touchingBlocks = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() }, i = arrayOfBlockPos1.length, b = 0; b < i; ) {
      BlockPos pos = arrayOfBlockPos1[b];
      IBlockState touchingState = mc.world.getBlockState(pos);
      if (touchingState.getBlock() != Blocks.AIR && touchingState.getBlock() == Blocks.OBSIDIAN) {
        b++;
        continue;
      } 
      return false;
    } 
    return true;
  }
  
  public static boolean isBedrockHole(BlockPos blockPos) {
    BlockPos[] touchingBlocks;
    BlockPos[] arrayOfBlockPos1;
    int i;
    byte b;
    for (arrayOfBlockPos1 = touchingBlocks = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() }, i = arrayOfBlockPos1.length, b = 0; b < i; ) {
      BlockPos pos = arrayOfBlockPos1[b];
      IBlockState touchingState = mc.world.getBlockState(pos);
      if (touchingState.getBlock() != Blocks.AIR && touchingState.getBlock() == Blocks.BEDROCK) {
        b++;
        continue;
      } 
      return false;
    } 
    return true;
  }
  
  public static boolean isBothHole(BlockPos blockPos) {
    BlockPos[] touchingBlocks;
    BlockPos[] arrayOfBlockPos1;
    int i;
    byte b;
    for (arrayOfBlockPos1 = touchingBlocks = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() }, i = arrayOfBlockPos1.length, b = 0; b < i; ) {
      BlockPos pos = arrayOfBlockPos1[b];
      IBlockState touchingState = mc.world.getBlockState(pos);
      if (touchingState.getBlock() != Blocks.AIR && (touchingState.getBlock() == Blocks.BEDROCK || touchingState.getBlock() == Blocks.OBSIDIAN)) {
        b++;
        continue;
      } 
      return false;
    } 
    return true;
  }
  
  public static Vec3d[] getUnsafeBlockArray(Entity entity, int height, boolean floor) {
    List<Vec3d> list = getUnsafeBlocks(entity, height, floor);
    Vec3d[] array = new Vec3d[list.size()];
    return list.<Vec3d>toArray(array);
  }
  
  public static Vec3d[] getUnsafeBlockArrayFromVec3d(Vec3d pos, int height, boolean floor) {
    List<Vec3d> list = getUnsafeBlocksFromVec3d(pos, height, floor);
    Vec3d[] array = new Vec3d[list.size()];
    return list.<Vec3d>toArray(array);
  }
  
  public static double getDst(Vec3d vec) {
    return mc.player.getPositionVector().distanceTo(vec);
  }
  
  public static boolean isTrapped(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
    return (getUntrappedBlocks(player, antiScaffold, antiStep, legs, platform, antiDrop).size() == 0);
  }
  
  public static boolean isTrappedExtended(int extension, EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
    return (getUntrappedBlocksExtended(extension, player, antiScaffold, antiStep, legs, platform, antiDrop, raytrace).size() == 0);
  }
  
  public static List<Vec3d> getUntrappedBlocks(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
    ArrayList<Vec3d> vec3ds = new ArrayList<>();
    if (!antiStep && getUnsafeBlocks((Entity)player, 2, false).size() == 4)
      vec3ds.addAll(getUnsafeBlocks((Entity)player, 2, false)); 
    for (int i = 0; i < (getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)).length; i++) {
      Vec3d vector = getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)[i];
      BlockPos targetPos = (new BlockPos(player.getPositionVector())).add(vector.x, vector.y, vector.z);
      Block block = mc.world.getBlockState(targetPos).getBlock();
      if (block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockFire || block instanceof net.minecraft.block.BlockDeadBush || block instanceof net.minecraft.block.BlockSnow)
        vec3ds.add(vector); 
    } 
    return vec3ds;
  }
  
  public static boolean isInWater(Entity entity) {
    if (entity == null)
      return false; 
    double y = entity.posY + 0.01D;
    for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++) {
      for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ) {
        BlockPos pos = new BlockPos(x, (int)y, z);
        if (!(mc.world.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockLiquid)) {
          z++;
          continue;
        } 
        return true;
      } 
    } 
    return false;
  }
  
  public static boolean isDrivenByPlayer(Entity entityIn) {
    return (mc.player != null && entityIn != null && entityIn.equals(mc.player.getRidingEntity()));
  }
  
  public static boolean isPlayer(Entity entity) {
    return entity instanceof EntityPlayer;
  }
  
  public static boolean isAboveWater(Entity entity) {
    return isAboveWater(entity, false);
  }
  
  public static boolean isAboveWater(Entity entity, boolean packet) {
    if (entity == null)
      return false; 
    double y = entity.posY - (packet ? 0.03D : (isPlayer(entity) ? 0.2D : 0.5D));
    for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++) {
      for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ) {
        BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
        if (!(mc.world.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockLiquid)) {
          z++;
          continue;
        } 
        return true;
      } 
    } 
    return false;
  }
  
  public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
    double dirx = me.posX - px;
    double diry = me.posY - py;
    double dirz = me.posZ - pz;
    double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
    dirx /= len;
    diry /= len;
    dirz /= len;
    double pitch = Math.asin(diry);
    double yaw = Math.atan2(dirz, dirx);
    pitch = pitch * 180.0D / Math.PI;
    yaw = yaw * 180.0D / Math.PI;
    yaw += 90.0D;
    return new double[] { yaw, pitch };
  }
  
  public static List<Vec3d> getUntrappedBlocksExtended(int extension, EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
    ArrayList<Vec3d> placeTargets = new ArrayList<>();
    if (extension == 1) {
      placeTargets.addAll(targets(player.getPositionVector(), antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
    } else {
      int extend = 1;
      for (Vec3d vec3d : MathUtil.getBlockBlocks((Entity)player)) {
        if (extend > extension)
          break; 
        placeTargets.addAll(targets(vec3d, antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
        extend++;
      } 
    } 
    ArrayList<Vec3d> removeList = new ArrayList<>();
    for (Vec3d vec3d : placeTargets) {
      BlockPos pos = new BlockPos(vec3d);
      if (BlockUtil.isPositionPlaceable(pos, raytrace) != -1)
        continue; 
      removeList.add(vec3d);
    } 
    for (Vec3d vec3d : removeList)
      placeTargets.remove(vec3d); 
    return placeTargets;
  }
  
  public static List<Vec3d> targets(Vec3d vec3d, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
    ArrayList<Vec3d> placeTargets = new ArrayList<>();
    if (antiDrop)
      Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiDropOffsetList)); 
    if (platform)
      Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, platformOffsetList)); 
    if (legs)
      Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, legOffsetList)); 
    Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, OffsetList));
    if (antiStep) {
      Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiStepOffsetList));
    } else {
      List<Vec3d> vec3ds = getUnsafeBlocksFromVec3d(vec3d, 2, false);
      if (vec3ds.size() == 4)
        for (Vec3d vector : vec3ds) {
          BlockPos position = (new BlockPos(vec3d)).add(vector.x, vector.y, vector.z);
          switch (BlockUtil.isPositionPlaceable(position, raytrace)) {
            case 0:
              break;
            case -1:
            case 1:
            case 2:
              continue;
            case 3:
              placeTargets.add(vec3d.add(vector));
              break;
            default:
              // Byte code: goto -> 234
          } 
          if (antiScaffold)
            Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList)); 
          return placeTargets;
        }  
    } 
    if (antiScaffold)
      Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList)); 
    return placeTargets;
  }
  
  public static void OffhandAttack(Entity entity, boolean packet, boolean swingArm) {
    if (packet) {
      mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
    } else {
      mc.playerController.attackEntity((EntityPlayer)mc.player, entity);
    } 
    if (swingArm)
      mc.player.swingArm(EnumHand.OFF_HAND); 
  }
  
  public static List<Vec3d> getOffsetList(int y, boolean floor) {
    ArrayList<Vec3d> offsets = new ArrayList<>();
    offsets.add(new Vec3d(-1.0D, y, 0.0D));
    offsets.add(new Vec3d(1.0D, y, 0.0D));
    offsets.add(new Vec3d(0.0D, y, -1.0D));
    offsets.add(new Vec3d(0.0D, y, 1.0D));
    if (floor)
      offsets.add(new Vec3d(0.0D, (y - 1), 0.0D)); 
    return offsets;
  }
  
  public static Vec3d[] getOffsets(int y, boolean floor) {
    List<Vec3d> offsets = getOffsetList(y, floor);
    Vec3d[] array = new Vec3d[offsets.size()];
    return offsets.<Vec3d>toArray(array);
  }
  
  public static Vec3d[] getTrapOffsets(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
    List<Vec3d> offsets = getTrapOffsetsList(antiScaffold, antiStep, legs, platform, antiDrop);
    Vec3d[] array = new Vec3d[offsets.size()];
    return offsets.<Vec3d>toArray(array);
  }
  
  public static List<Vec3d> getTrapOffsetsList(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
    ArrayList<Vec3d> offsets = new ArrayList<>(getOffsetList(1, false));
    offsets.add(new Vec3d(0.0D, 2.0D, 0.0D));
    if (antiScaffold)
      offsets.add(new Vec3d(0.0D, 3.0D, 0.0D)); 
    if (antiStep)
      offsets.addAll(getOffsetList(2, false)); 
    if (legs)
      offsets.addAll(getOffsetList(0, false)); 
    if (platform) {
      offsets.addAll(getOffsetList(-1, false));
      offsets.add(new Vec3d(0.0D, -1.0D, 0.0D));
    } 
    if (antiDrop)
      offsets.add(new Vec3d(0.0D, -2.0D, 0.0D)); 
    return offsets;
  }
  
  public static Vec3d[] getHeightOffsets(int min, int max) {
    ArrayList<Vec3d> offsets = new ArrayList<>();
    for (int i = min; i <= max; i++)
      offsets.add(new Vec3d(0.0D, i, 0.0D)); 
    Vec3d[] array = new Vec3d[offsets.size()];
    return offsets.<Vec3d>toArray(array);
  }
  
  public static BlockPos getRoundedBlockPos(Entity entity) {
    return new BlockPos(MathUtil.roundVec(entity.getPositionVector(), 0));
  }
  
  public static boolean isLiving(Entity entity) {
    return entity instanceof EntityLivingBase;
  }
  
  public static boolean isAlive(Entity entity) {
    return (isLiving(entity) && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0.0F);
  }
  
  public static boolean isDead(Entity entity) {
    return !isAlive(entity);
  }
  
  public static float getHealth(Entity entity) {
    if (isLiving(entity)) {
      EntityLivingBase livingBase = (EntityLivingBase)entity;
      return livingBase.getHealth() + livingBase.getAbsorptionAmount();
    } 
    return 0.0F;
  }
  
  public static float getHealth(Entity entity, boolean absorption) {
    if (isLiving(entity)) {
      EntityLivingBase livingBase = (EntityLivingBase)entity;
      return livingBase.getHealth() + (absorption ? livingBase.getAbsorptionAmount() : 0.0F);
    } 
    return 0.0F;
  }
  
  public static boolean canEntityFeetBeSeen(Entity entityIn) {
    return (mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posX + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ), false, true, false) == null);
  }
  
  public static boolean isntValid(Entity entity, double range) {
    return (entity == null || isDead(entity) || entity.equals(mc.player) || (entity instanceof EntityPlayer && M3dC3t.friendManager.isFriend(entity.getName())) || mc.player.getDistanceSq(entity) > MathUtil.square(range));
  }
  
  public static boolean isValid(Entity entity, double range) {
    return !isntValid(entity, range);
  }
  
  public static boolean holdingWeapon(EntityPlayer player) {
    return (player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemSword || player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemAxe);
  }
  
  public static double getMaxSpeed() {
    double maxModifier = 0.2873D;
    if (mc.player.isPotionActive(Objects.<Potion>requireNonNull(Potion.getPotionById(1))))
      maxModifier *= 1.0D + 0.2D * (((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.player.getActivePotionEffect(Objects.<Potion>requireNonNull(Potion.getPotionById(1))))).getAmplifier() + 1); 
    return maxModifier;
  }
  
  public static void mutliplyEntitySpeed(Entity entity, double multiplier) {
    if (entity != null) {
      entity.motionX *= multiplier;
      entity.motionZ *= multiplier;
    } 
  }
  
  public static boolean isEntityMoving(Entity entity) {
    if (entity == null)
      return false; 
    if (entity instanceof EntityPlayer)
      return (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()); 
    return (entity.motionX != 0.0D || entity.motionY != 0.0D || entity.motionZ != 0.0D);
  }
  
  public static double getEntitySpeed(Entity entity) {
    if (entity != null) {
      double distTraveledLastTickX = entity.posX - entity.prevPosX;
      double distTraveledLastTickZ = entity.posZ - entity.prevPosZ;
      double speed = MathHelper.sqrt(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ);
      return speed * 20.0D;
    } 
    return 0.0D;
  }
  
  public static boolean is32k(ItemStack stack) {
    return (EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack) >= 1000);
  }
  
  public static void moveEntityStrafe(double speed, Entity entity) {
    if (entity != null) {
      MovementInput movementInput = mc.player.movementInput;
      double forward = movementInput.moveForward;
      double strafe = movementInput.moveStrafe;
      float yaw = mc.player.rotationYaw;
      if (forward == 0.0D && strafe == 0.0D) {
        entity.motionX = 0.0D;
        entity.motionZ = 0.0D;
      } else {
        if (forward != 0.0D) {
          if (strafe > 0.0D) {
            yaw += ((forward > 0.0D) ? -45 : 45);
          } else if (strafe < 0.0D) {
            yaw += ((forward > 0.0D) ? 45 : -45);
          } 
          strafe = 0.0D;
          if (forward > 0.0D) {
            forward = 1.0D;
          } else if (forward < 0.0D) {
            forward = -1.0D;
          } 
        } 
        entity.motionX = forward * speed * Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((yaw + 90.0F)));
        entity.motionZ = forward * speed * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((yaw + 90.0F)));
      } 
    } 
  }
  
  public static boolean rayTraceHitCheck(Entity entity, boolean shouldCheck) {
    return (!shouldCheck || mc.player.canEntityBeSeen(entity));
  }
  
  public static Color getColor(Entity entity, int red, int green, int blue, int alpha, boolean colorFriends) {
    Color color = new Color(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
    if (entity instanceof EntityPlayer && colorFriends && M3dC3t.friendManager.isFriend((EntityPlayer)entity))
      color = new Color(0.33333334F, 1.0F, 1.0F, alpha / 255.0F); 
    return color;
  }
  
  public static boolean isMoving() {
    return (mc.player.moveForward != 0.0D || mc.player.moveStrafing != 0.0D);
  }
  
  public static boolean isMoving(EntityLivingBase entity) {
    return (entity.moveForward != 0.0F || entity.moveStrafing != 0.0F);
  }
  
  public static EntityPlayer getClosestEnemy(double distance) {
    EntityPlayer closest = null;
    for (EntityPlayer player : mc.world.playerEntities) {
      if (isntValid((Entity)player, distance))
        continue; 
      if (closest == null) {
        closest = player;
        continue;
      } 
      if (mc.player.getDistanceSq((Entity)player) >= mc.player.getDistanceSq((Entity)closest))
        continue; 
      closest = player;
    } 
    return closest;
  }
  
  public static boolean checkCollide() {
    if (mc.player.isSneaking())
      return false; 
    if (mc.player.getRidingEntity() != null && (mc.player.getRidingEntity()).fallDistance >= 3.0F)
      return false; 
    return (mc.player.fallDistance < 3.0F);
  }
  
  public static BlockPos getPlayerPosWithEntity() {
    return new BlockPos((mc.player.getRidingEntity() != null) ? (mc.player.getRidingEntity()).posX : mc.player.posX, (mc.player.getRidingEntity() != null) ? (mc.player.getRidingEntity()).posY : mc.player.posY, (mc.player.getRidingEntity() != null) ? (mc.player.getRidingEntity()).posZ : mc.player.posZ);
  }
  
  public static boolean isCrystalAtFeet(EntityEnderCrystal crystal, double range) {
    for (EntityPlayer player : mc.world.playerEntities) {
      if (mc.player.getDistanceSq((Entity)player) > range * range)
        continue; 
      if (M3dC3t.friendManager.isFriend(player))
        continue; 
      for (Vec3d vec : doubleLegOffsetList) {
        if ((new BlockPos(player.getPositionVector())).add(vec.x, vec.y, vec.z) == crystal.getPosition())
          return true; 
      } 
    } 
    return false;
  }
  
  public static double[] forward(double speed) {
    float forward = mc.player.movementInput.moveForward;
    float side = mc.player.movementInput.moveStrafe;
    float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
    if (forward != 0.0F) {
      if (side > 0.0F) {
        yaw += ((forward > 0.0F) ? -45 : 45);
      } else if (side < 0.0F) {
        yaw += ((forward > 0.0F) ? 45 : -45);
      } 
      side = 0.0F;
      if (forward > 0.0F) {
        forward = 1.0F;
      } else if (forward < 0.0F) {
        forward = -1.0F;
      } 
    } 
    double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
    double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
    double posX = forward * speed * cos + side * speed * sin;
    double posZ = forward * speed * sin - side * speed * cos;
    return new double[] { posX, posZ };
  }
  
  public static void swingArmNoPacket(EnumHand hand, EntityLivingBase entity) {
    ItemStack stack = entity.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem().onEntitySwing(entity, stack))
      return; 
    if (!entity.isSwingInProgress || entity.swingProgressInt >= ((IEntityLivingBase)entity).getArmSwingAnimationEnd() / 2 || entity.swingProgressInt < 0) {
      entity.swingProgressInt = -1;
      entity.isSwingInProgress = true;
      entity.swingingHand = hand;
    } 
  }
  
  public static Map<String, Integer> getTextRadarPlayers() {
    Map<String, Integer> output = new HashMap<>();
    DecimalFormat dfHealth = new DecimalFormat("#.#");
    dfHealth.setRoundingMode(RoundingMode.CEILING);
    DecimalFormat dfDistance = new DecimalFormat("#.#");
    dfDistance.setRoundingMode(RoundingMode.CEILING);
    StringBuilder healthSB = new StringBuilder();
    StringBuilder distanceSB = new StringBuilder();
    for (EntityPlayer player : mc.world.playerEntities) {
      if (player.isInvisible() || player.getName().equals(mc.player.getName()))
        continue; 
      int hpRaw = (int)getHealth((Entity)player);
      String hp = dfHealth.format(hpRaw);
      healthSB.append("Â§");
      if (hpRaw >= 20) {
        healthSB.append("a");
      } else if (hpRaw >= 10) {
        healthSB.append("e");
      } else if (hpRaw >= 5) {
        healthSB.append("6");
      } else {
        healthSB.append("c");
      } 
      healthSB.append(hp);
      int distanceInt = (int)mc.player.getDistance((Entity)player);
      String distance = dfDistance.format(distanceInt);
      distanceSB.append("Â§");
      if (distanceInt >= 25) {
        distanceSB.append("a");
      } else if (distanceInt > 10) {
        distanceSB.append("6");
      } else {
        distanceSB.append("c");
      } 
      distanceSB.append(distance);
      output.put(healthSB.toString() + " " + (M3dC3t.friendManager.isFriend(player) ? (String)ChatFormatting.AQUA : (String)ChatFormatting.RED) + player.getName() + " " + distanceSB.toString() + " Â§f0", Integer.valueOf((int)mc.player.getDistance((Entity)player)));
      healthSB.setLength(0);
      distanceSB.setLength(0);
    } 
    if (!output.isEmpty())
      output = MathUtil.sortByValue(output, false); 
    return output;
  }
  
  public static boolean isAboveBlock(Entity entity, BlockPos blockPos) {
    return (entity.posY >= blockPos.getY());
  }
  
  public static Vec3d[] getVarOffsets(int x, int y, int z) {
    List<Vec3d> offsets = getVarOffsetList(x, y, z);
    Vec3d[] array = new Vec3d[offsets.size()];
    return offsets.<Vec3d>toArray(array);
  }
  
  public static List<Vec3d> getVarOffsetList(int x, int y, int z) {
    ArrayList<Vec3d> offsets = new ArrayList<>();
    offsets.add(new Vec3d(x, y, z));
    return offsets;
  }
  
  public static void attackEntity(Entity entity, boolean packet) {
    if (packet) {
      mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
    } else {
      mc.playerController.attackEntity((EntityPlayer)mc.player, entity);
    } 
  }
  
  public static Boolean posEqualsBlock(BlockPos pos, Block block) {
    return Boolean.valueOf(getBlockOnPos(pos).equals(block));
  }
  
  public static Block getBlockOnPos(BlockPos pos) {
    return mc.world.getBlockState(pos).getBlock();
  }
}
