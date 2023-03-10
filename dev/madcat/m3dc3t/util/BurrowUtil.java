//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BurrowUtil {
  public static final Minecraft mc = Minecraft.getMinecraft();
  
  public static boolean placeBlock(BlockPos pos, EnumHand hand, boolean rotate, boolean packet, boolean isSneaking) {
    boolean sneaking = false;
    EnumFacing side = getFirstFacing(pos);
    if (side == null)
      return isSneaking; 
    BlockPos neighbour = pos.offset(side);
    EnumFacing opposite = side.getOpposite();
    Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
    Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();
    if (!mc.player.isSneaking()) {
      mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
      mc.player.setSneaking(true);
      sneaking = true;
    } 
    if (rotate)
      faceVector(hitVec, true); 
    rightClickBlock(neighbour, hitVec, hand, opposite, packet);
    mc.player.swingArm(EnumHand.MAIN_HAND);
    mc.rightClickDelayTimer = 4;
    return (sneaking || isSneaking);
  }
  
  public static List<EnumFacing> getPossibleSides(BlockPos pos) {
    List<EnumFacing> facings = new ArrayList<>();
    for (EnumFacing side : EnumFacing.values()) {
      BlockPos neighbour = pos.offset(side);
      if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
        IBlockState blockState = mc.world.getBlockState(neighbour);
        if (!blockState.getMaterial().isReplaceable())
          facings.add(side); 
      } 
    } 
    return facings;
  }
  
  public static EnumFacing getFirstFacing(BlockPos pos) {
    Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
    if (iterator.hasNext()) {
      EnumFacing facing = iterator.next();
      return facing;
    } 
    return null;
  }
  
  public static Vec3d getEyesPos() {
    return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
  }
  
  public static float[] getLegitRotations(Vec3d vec) {
    Vec3d eyesPos = getEyesPos();
    double diffX = vec.x - eyesPos.x;
    double diffY = vec.y - eyesPos.y;
    double diffZ = vec.z - eyesPos.z;
    double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
    float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
    float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
    return new float[] { mc.player.rotationYaw + 
        MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + 
        MathHelper.wrapDegrees(pitch - mc.player.rotationPitch) };
  }
  
  public static void faceVector(Vec3d vec, boolean normalizeAngle) {
    float[] rotations = getLegitRotations(vec);
    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? MathHelper.normalizeAngle((int)rotations[1], 360) : rotations[1], mc.player.onGround));
  }
  
  public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
    if (packet) {
      float f = (float)(vec.x - pos.getX());
      float f1 = (float)(vec.y - pos.getY());
      float f2 = (float)(vec.z - pos.getZ());
      mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
    } else {
      mc.playerController.processRightClickBlock(mc.player, mc.world, pos, direction, vec, hand);
    } 
    mc.player.swingArm(EnumHand.MAIN_HAND);
    mc.rightClickDelayTimer = 4;
  }
  
  public static int findHotbarBlock(Class clazz) {
    for (int i = 0; i < 9; i++) {
      ItemStack stack = mc.player.inventory.getStackInSlot(i);
      if (stack != ItemStack.EMPTY) {
        if (clazz.isInstance(stack.getItem()))
          return i; 
        if (stack.getItem() instanceof ItemBlock) {
          Block block = ((ItemBlock)stack.getItem()).getBlock();
          if (clazz.isInstance(block))
            return i; 
        } 
      } 
    } 
    return -1;
  }
  
  public static void switchToSlot(int slot) {
    mc.player.inventory.currentItem = slot;
    mc.playerController.updateController();
  }
}
