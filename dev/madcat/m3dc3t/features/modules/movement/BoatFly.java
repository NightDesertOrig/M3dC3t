//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;

public class BoatFly extends Module {
  private final Setting<Double> speed = register(new Setting("Speed", Double.valueOf(5.0D), Double.valueOf(0.1D), Double.valueOf(20.0D)));
  
  private final Setting<Double> yspeed = register(new Setting("YSpeed", Double.valueOf(3.0D), Double.valueOf(0.1D), Double.valueOf(20.0D)));
  
  private final Setting<Boolean> glide = register(new Setting("Glide", Boolean.valueOf(true)));
  
  public BoatFly() {
    super("BoatFly", "BoatFly.", Module.Category.MOVEMENT, false, false, false);
  }
  
  public void onDisable() {
    if (mc.player.getRidingEntity() != null)
      mc.player.getRidingEntity().setNoGravity(false); 
  }
  
  public void onUpdate() {
    if (mc.player == null || mc.player.getRidingEntity() == null || mc.world == null)
      return; 
    if (mc.player.getRidingEntity() != null) {
      mc.player.getRidingEntity().setNoGravity(true);
      (mc.player.getRidingEntity()).motionY = 0.0D;
      if (mc.gameSettings.keyBindJump.isKeyDown()) {
        (mc.player.getRidingEntity()).onGround = false;
        (mc.player.getRidingEntity()).motionY = ((Double)this.yspeed.getValue()).doubleValue();
      } 
      if (mc.gameSettings.keyBindSprint.isKeyDown()) {
        (mc.player.getRidingEntity()).onGround = false;
        (mc.player.getRidingEntity()).motionY = -((Double)this.yspeed.getValue()).doubleValue();
      } 
      double[] normalDir = directionSpeed(((Double)this.speed.getValue()).doubleValue() / 2.0D);
      if (mc.player.movementInput.moveStrafe != 0.0F || mc.player.movementInput.moveForward != 0.0F) {
        (mc.player.getRidingEntity()).motionX = normalDir[0];
        (mc.player.getRidingEntity()).motionZ = normalDir[1];
      } else {
        (mc.player.getRidingEntity()).motionX = 0.0D;
        (mc.player.getRidingEntity()).motionZ = 0.0D;
      } 
      if (((Boolean)this.glide.getValue()).booleanValue())
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
          if (mc.player.ticksExisted % 8 < 2)
            (mc.player.getRidingEntity()).motionY = -0.03999999910593033D; 
        } else if (mc.player.ticksExisted % 8 < 4) {
          (mc.player.getRidingEntity()).motionY = -0.03999999910593033D;
        }  
    } 
  }
  
  private double[] directionSpeed(double speed) {
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
}
