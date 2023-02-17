//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BlockInteractionHelper {
  public static boolean hotbarSlotCheckEmpty(ItemStack stack) {
    return (stack != ItemStack.EMPTY);
  }
  
  public static boolean blockCheckNonBlock(ItemStack stack) {
    return stack.getItem() instanceof net.minecraft.item.ItemBlock;
  }
  
  public static void placeBlockScaffold(BlockPos pos) {
    Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    for (EnumFacing side : EnumFacing.values()) {
      BlockPos neighbor = pos.offset(side);
      EnumFacing side2 = side.getOpposite();
      if (canBeClicked(neighbor)) {
        Vec3d hitVec = (new Vec3d((Vec3i)neighbor)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(side2.getDirectionVec())).scale(0.5D));
        if (eyesPos.squareDistanceTo(hitVec) <= 18.0625D) {
          faceVectorPacketInstant(hitVec);
          processRightClickBlock(neighbor, side2, hitVec);
          mc.player.swingArm(EnumHand.MAIN_HAND);
          return;
        } 
      } 
    } 
  }
  
  public static void placeBlockScaffoldStrictRaytrace(BlockPos pos) {
    BlockPos neighbor = null;
    EnumFacing side2 = null;
    if (findBlockFacingLocationBlock(pos) == 1) {
      neighbor = pos.west();
      side2 = EnumFacing.EAST;
    } 
    if (findBlockFacingLocationBlock(pos) == 2) {
      neighbor = pos.east();
      side2 = EnumFacing.WEST;
    } 
    if (findBlockFacingLocationBlock(pos) == 3) {
      neighbor = pos.north();
      side2 = EnumFacing.SOUTH;
    } 
    if (findBlockFacingLocationBlock(pos) == 4) {
      neighbor = pos.south();
      side2 = EnumFacing.NORTH;
    } 
    double y = 0.0D;
    if (neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) < 1.0D)
      y = 0.95D; 
    if (neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) > 1.0D && neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) < 2.0D)
      y = 0.85D; 
    if (neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) > 2.0D && neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) < 3.0D)
      y = 0.75D; 
    if (neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) > 3.0D)
      y = 0.65D; 
    RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(neighbor.getX() + 0.5D, pos.north().getY() + y, neighbor.getZ() + 0.5D));
    Vec3d hitVec = (new Vec3d((Vec3i)neighbor)).add(0.5D, y, 0.5D).add((new Vec3d(result.sideHit.getDirectionVec())).scale(0.5D));
    faceVectorPacketInstant(hitVec);
    processRightClickBlock(neighbor, side2, hitVec);
    mc.player.swingArm(EnumHand.MAIN_HAND);
  }
  
  public static void placeBlockScaffoldStrict(BlockPos pos) {
    BlockPos neighbor = null;
    EnumFacing side2 = null;
    if (findBlockFacingLocationBlock(pos) == 1) {
      neighbor = pos.west();
      side2 = EnumFacing.EAST;
    } 
    if (findBlockFacingLocationBlock(pos) == 2) {
      neighbor = pos.east();
      side2 = EnumFacing.WEST;
    } 
    if (findBlockFacingLocationBlock(pos) == 3) {
      neighbor = pos.north();
      side2 = EnumFacing.SOUTH;
    } 
    if (findBlockFacingLocationBlock(pos) == 4) {
      neighbor = pos.south();
      side2 = EnumFacing.NORTH;
    } 
    Vec3d hitVec = (new Vec3d((Vec3i)neighbor)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(side2.getDirectionVec())).scale(0.5D));
    faceVectorPacketInstant(hitVec);
    processRightClickBlock(neighbor, side2, hitVec);
    mc.player.swingArm(EnumHand.MAIN_HAND);
  }
  
  public static int findBlockFacingLocationBlock(BlockPos pos) {
    double playerX = 0.0D;
    double posX = 0.0D;
    double distanceX = 0.0D;
    double playerZ = 0.0D;
    double posZ = 0.0D;
    double distanceZ = 0.0D;
    int direction = 0;
    playerX = mc.player.posX;
    posX = pos.getX();
    if (playerX > posX) {
      distanceX = playerX - posX;
    } else {
      distanceX = posX - playerX;
    } 
    playerZ = mc.player.posZ;
    posZ = pos.getZ();
    if (playerZ > posZ) {
      distanceZ = playerZ - posZ;
    } else {
      distanceZ = posZ - playerZ;
    } 
    if (distanceX > distanceZ) {
      if (playerX > posX) {
        direction = 1;
      } else {
        direction = 2;
      } 
    } else if (playerZ > posZ) {
      direction = 3;
    } else {
      direction = 4;
    } 
    return direction;
  }
  
  public static int findBlockFacingLocationPlayer(BlockPos pos) {
    double playerX = 0.0D;
    double enemyX = 0.0D;
    double distanceX = 0.0D;
    double playerZ = 0.0D;
    double enemyZ = 0.0D;
    double distanceZ = 0.0D;
    int direction = 0;
    playerX = mc.player.posX;
    enemyX = pos.getX();
    if (playerX > enemyX) {
      distanceX = playerX - enemyX;
    } else {
      distanceX = enemyX - playerX;
    } 
    playerZ = mc.player.posZ;
    enemyZ = pos.getZ();
    if (playerZ > enemyZ) {
      distanceZ = playerZ - enemyZ;
    } else {
      distanceZ = enemyZ - playerZ;
    } 
    if (distanceX > distanceZ) {
      if (playerX > enemyX) {
        direction = 1;
      } else {
        direction = 2;
      } 
    } else if (playerZ > enemyZ) {
      direction = 3;
    } else {
      direction = 4;
    } 
    return direction;
  }
  
  public static void placeBlockScaffoldPiston(BlockPos pos, BlockPos look) {
    Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    for (EnumFacing side : EnumFacing.values()) {
      BlockPos neighbor = pos.offset(side);
      BlockPos neighborLook = look.offset(side);
      EnumFacing side2 = side.getOpposite();
      EnumFacing side2Look = side.getOpposite();
      if (canBeClicked(neighbor)) {
        Vec3d hitVec = (new Vec3d((Vec3i)neighbor)).add(0.9D, 0.1D, 0.9D).add((new Vec3d(side2.getDirectionVec())).scale(0.5D));
        Vec3d hitVecLook = (new Vec3d((Vec3i)neighborLook)).add(0.9D, 0.1D, 0.9D).add((new Vec3d(side2Look.getDirectionVec())).scale(0.5D));
        if (eyesPos.squareDistanceTo(hitVec) <= 18.0625D) {
          faceVectorPacketInstant(hitVecLook);
          processRightClickBlock(neighbor, side2, hitVec);
          mc.player.swingArm(EnumHand.MAIN_HAND);
          return;
        } 
      } 
    } 
  }
  
  public static void placeBlockScaffoldNoRotate(BlockPos pos) {
    Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    for (EnumFacing side : EnumFacing.values()) {
      BlockPos neighbor = pos.offset(side);
      EnumFacing side2 = side.getOpposite();
      if (canBeClicked(neighbor)) {
        Vec3d hitVec = (new Vec3d((Vec3i)neighbor)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(side2.getDirectionVec())).scale(0.5D));
        processRightClickBlock(neighbor, side2, hitVec);
        mc.player.swingArm(EnumHand.MAIN_HAND);
        return;
      } 
    } 
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
  
  private static void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
    double[] v = calculateLookAt(px, py, pz, me);
    setYawAndPitch((float)v[0], (float)v[1]);
  }
  
  private static void setYawAndPitch(float yaw1, float pitch1) {
    yaw = yaw1;
    pitch = pitch1;
  }
  
  private static float[] getLegitRotations(Vec3d vec) {
    Vec3d eyesPos = getEyesPos();
    double diffX = vec.x - eyesPos.x;
    double diffY = vec.y - eyesPos.y;
    double diffZ = vec.z - eyesPos.z;
    double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
    float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
    float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
    return new float[] { mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch) };
  }
  
  private static Vec3d getEyesPos() {
    return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
  }
  
  public static void faceVectorPacketInstant(Vec3d vec) {
    float[] rotations = getLegitRotations(vec);
    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
  }
  
  private static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
    getPlayerController().processRightClickBlock(mc.player, mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
  }
  
  public static boolean canBeClicked(BlockPos pos) {
    return getBlock(pos).canCollideCheck(getState(pos), false);
  }
  
  public static Block getBlock(BlockPos pos) {
    return getState(pos).getBlock();
  }
  
  private static PlayerControllerMP getPlayerController() {
    return (Minecraft.getMinecraft()).playerController;
  }
  
  private static IBlockState getState(BlockPos pos) {
    return mc.world.getBlockState(pos);
  }
  
  public static boolean checkForNeighbours(BlockPos blockPos) {
    if (!hasNeighbour(blockPos)) {
      for (EnumFacing side : EnumFacing.values()) {
        BlockPos neighbour = blockPos.offset(side);
        if (hasNeighbour(neighbour))
          return true; 
      } 
      return false;
    } 
    return true;
  }
  
  private static boolean hasNeighbour(BlockPos blockPos) {
    for (EnumFacing side : EnumFacing.values()) {
      BlockPos neighbour = blockPos.offset(side);
      if (!mc.world.getBlockState(neighbour).getMaterial().isReplaceable())
        return true; 
    } 
    return false;
  }
  
  public static float[] calcAngle(Vec3d from, Vec3d to) {
    double difX = to.x - from.x;
    double difY = (to.y - from.y) * -1.0D;
    double difZ = to.z - from.z;
    double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
    return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
  }
  
  public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
    List<BlockPos> circleblocks = new ArrayList<>();
    int cx = loc.getX();
    int cy = loc.getY();
    int cz = loc.getZ();
    for (int x = cx - (int)r; x <= cx + r; x++) {
      for (int z = cz - (int)r; z <= cz + r; ) {
        int y = sphere ? (cy - (int)r) : cy;
        for (;; z++) {
          if (y < (sphere ? (cy + r) : (cy + h))) {
            double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
            if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
              BlockPos l = new BlockPos(x, y + plus_y, z);
              circleblocks.add(l);
            } 
            y++;
            continue;
          } 
        } 
      } 
    } 
    return circleblocks;
  }
  
  public static List<BlockPos> getCircle(BlockPos loc, int y, float r, boolean hollow) {
    List<BlockPos> circleblocks = new ArrayList<>();
    int cx = loc.getX();
    int cz = loc.getZ();
    for (int x = cx - (int)r; x <= cx + r; x++) {
      for (int z = cz - (int)r; z <= cz + r; z++) {
        double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z));
        if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
          BlockPos l = new BlockPos(x, y, z);
          circleblocks.add(l);
        } 
      } 
    } 
    return circleblocks;
  }
  
  public static EnumFacing getPlaceableSide(BlockPos pos) {
    for (EnumFacing side : EnumFacing.values()) {
      BlockPos neighbour = pos.offset(side);
      if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
        IBlockState blockState = mc.world.getBlockState(neighbour);
        if (!blockState.getMaterial().isReplaceable())
          return side; 
      } 
    } 
    return null;
  }
  
  public static final List<Block> blackList = Arrays.asList(new Block[] { 
        Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, 
        Blocks.ENCHANTING_TABLE });
  
  public static final List<Block> shulkerList = Arrays.asList(new Block[] { 
        Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, 
        Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX });
  
  private static final Minecraft mc = Minecraft.getMinecraft();
  
  public static double yaw;
  
  public static double pitch;
  
  public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck, float height) {
    return (!shouldCheck || mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX(), (pos.getY() + height), pos.getZ()), false, true, false) == null);
  }
  
  public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck) {
    return rayTracePlaceCheck(pos, shouldCheck, 1.0F);
  }
}
