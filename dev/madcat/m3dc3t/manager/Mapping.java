//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.manager;

import net.minecraft.client.Minecraft;

public class Mapping {
  public static boolean isObfuscated() {
    try {
      return (Minecraft.class.getDeclaredField("instance") == null);
    } catch (Exception e) {
      return true;
    } 
  }
  
  public static final String tickLength = isObfuscated() ? "tickLength" : "tickLength";
  
  public static final String timer = isObfuscated() ? "timer" : "timer";
  
  public static final String placedBlockDirection = isObfuscated() ? "placedBlockDirection" : "placedBlockDirection";
  
  public static final String playerPosLookYaw = isObfuscated() ? "yaw" : "yaw";
  
  public static final String playerPosLookPitch = isObfuscated() ? "pitch" : "pitch";
  
  public static final String isInWeb = isObfuscated() ? "isInWeb" : "isInWeb";
  
  public static final String cPacketPlayerYaw = isObfuscated() ? "yaw" : "yaw";
  
  public static final String cPacketPlayerPitch = isObfuscated() ? "pitch" : "pitch";
  
  public static final String renderManagerRenderPosX = isObfuscated() ? "renderPosX" : "renderPosX";
  
  public static final String renderManagerRenderPosY = isObfuscated() ? "renderPosY" : "renderPosY";
  
  public static final String renderManagerRenderPosZ = isObfuscated() ? "renderPosZ" : "renderPosZ";
  
  public static final String rightClickDelayTimer = isObfuscated() ? "rightClickDelayTimer" : "rightClickDelayTimer";
  
  public static final String sPacketEntityVelocityMotionX = isObfuscated() ? "motionX" : "motionX";
  
  public static final String sPacketEntityVelocityMotionY = isObfuscated() ? "motionY" : "motionY";
  
  public static final String sPacketEntityVelocityMotionZ = isObfuscated() ? "motionZ" : "motionZ";
}
