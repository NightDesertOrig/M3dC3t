//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathsUtil implements Globals {
  public static int clamp(int num, int min, int max) {
    return (num < min) ? min : Math.min(num, max);
  }
  
  public static float clamp(float num, float min, float max) {
    return (num < min) ? min : Math.min(num, max);
  }
  
  public static double clamp(double num, double min, double max) {
    return (num < min) ? min : Math.min(num, max);
  }
  
  public static float[] calcAngle(Vec3d from, Vec3d to) {
    double difX = to.x - from.x;
    double difY = (to.y - from.y) * -1.0D;
    double difZ = to.z - from.z;
    double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
    return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
  }
  
  public static Vec3d roundVec(Vec3d vec3d, int places) {
    return new Vec3d(round(vec3d.x, places), round(vec3d.y, places), round(vec3d.z, places));
  }
  
  public static double round(double value, int places) {
    if (places < 0)
      throw new IllegalArgumentException(); 
    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.FLOOR);
    return bd.doubleValue();
  }
  
  public static int random(int min, int max) {
    return random.nextInt(max - min) + min;
  }
  
  public static int floor(double value) {
    int i = (int)value;
    return (value < i) ? (i - 1) : i;
  }
  
  public static Vec3d extrapolatePlayerPosition(EntityPlayer player, int ticks) {
    Vec3d lastPos = new Vec3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
    Vec3d currentPos = new Vec3d(player.posX, player.posY, player.posZ);
    double distance = square((float)player.motionX) + square((float)player.motionY) + square((float)player.motionZ);
    Vec3d tempVec = calculateLine(lastPos, currentPos, distance * ticks);
    return new Vec3d(tempVec.x, player.posY, tempVec.z);
  }
  
  public static Vec3d calculateLine(Vec3d x1, Vec3d x2, double distance) {
    double length = Math.sqrt(square((float)(x2.x - x1.x)) + square((float)(x2.y - x1.y)) + square((float)(x2.z - x1.z)));
    double unitSlopeX = (x2.x - x1.x) / length;
    double unitSlopeY = (x2.y - x1.y) / length;
    double unitSlopeZ = (x2.z - x1.z) / length;
    double x = x1.x + unitSlopeX * distance;
    double y = x1.y + unitSlopeY * distance;
    double z = x1.z + unitSlopeZ * distance;
    return new Vec3d(x, y, z);
  }
  
  public static double square(float input) {
    return (input * input);
  }
  
  public static double[] directionSpeed(double speed) {
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
  
  public static double degToRad(double deg) {
    return deg * 0.01745329238474369D;
  }
}
