//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

public class Step extends Module {
  private final Setting<Double> height;
  
  public Setting<Boolean> useTimer;
  
  private final Setting<Double> timerTicks;
  
  public Setting<Boolean> sneakPause;
  
  public Setting<Boolean> waterPause;
  
  public Setting<Boolean> burrowPause;
  
  double[] forwardStep;
  
  double originalHeight;
  
  public Step() {
    super("Step", "Step up quickly.", Module.Category.MOVEMENT, false, false, false);
    this.height = register(new Setting("Height", Double.valueOf(2.0D), Double.valueOf(0.0D), Double.valueOf(10.0D)));
    this.useTimer = register(new Setting("use Timer", Boolean.valueOf(false)));
    this.timerTicks = register(new Setting("Timer Speed", Double.valueOf(0.5D), Double.valueOf(0.1D), Double.valueOf(2.0D), v -> ((Boolean)this.useTimer.getValue()).booleanValue()));
    this.sneakPause = register(new Setting("When Sneaking", Boolean.valueOf(false)));
    this.waterPause = register(new Setting("When in Liquid", Boolean.valueOf(true)));
    this.burrowPause = register(new Setting("When in Burrow", Boolean.valueOf(true)));
  }
  
  public void onDisable() {
    mc.timer.tickLength = 50.0F;
  }
  
  public void onEnable() {
    if (nullCheck())
      return; 
    this.originalHeight = mc.player.posY;
  }
  
  public void onUpdate() {
    if (nullCheck())
      return; 
    if (mc.timer.tickLength == (float)(50.0D / ((Double)this.timerTicks.getValue()).doubleValue()) && !M3dC3t.moduleManager.isModuleEnabled("Timer") && !M3dC3t.moduleManager.isModuleEnabled("TickShift"))
      mc.timer.tickLength = 50.0F; 
    if (!mc.player.collidedHorizontally)
      return; 
    if (mc.player.isOnLadder() || mc.player.movementInput.jump)
      return; 
    if ((mc.player.isInWater() | mc.player.isInLava()) != 0 && !((Boolean)this.waterPause.getValue()).booleanValue())
      return; 
    BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    if (pos != null && 
      mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN && !((Boolean)this.burrowPause.getValue()).booleanValue())
      return; 
    if (!mc.player.onGround)
      return; 
    if (((Boolean)this.useTimer.getValue()).booleanValue())
      mc.timer.tickLength = (float)(50.0D / ((Double)this.timerTicks.getValue()).doubleValue()); 
    if (mc.player.isSneaking() && !((Boolean)this.sneakPause.getValue()).booleanValue())
      return; 
    this.forwardStep = getMoveSpeed(0.1D);
    if ((getStepHeight()).height >= ((Double)this.height.getValue()).doubleValue() + 0.1D)
      return; 
    stepVanilla();
  }
  
  public static double[] getMoveSpeed(double speed) {
    float forward = mc.player.movementInput.moveForward;
    float strafe = mc.player.movementInput.moveStrafe;
    float yaw = mc.player.rotationYaw;
    if (forward != 0.0F) {
      if (strafe >= 1.0F) {
        yaw += ((forward > 0.0F) ? -45 : 45);
        strafe = 0.0F;
      } else if (strafe <= -1.0F) {
        yaw += ((forward > 0.0F) ? 45 : -45);
        strafe = 0.0F;
      } 
      if (forward > 0.0F) {
        forward = 1.0F;
      } else if (forward < 0.0F) {
        forward = -1.0F;
      } 
    } 
    double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
    double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
    double motionX = forward * speed * cos + strafe * speed * sin;
    double motionZ = forward * speed * sin - strafe * speed * cos;
    if (!isMoving()) {
      motionX = 0.0D;
      motionZ = 0.0D;
    } 
    return new double[] { motionX, motionZ };
  }
  
  public static boolean isMoving() {
    return (mc.player.moveForward != 0.0D || mc.player.moveStrafing != 0.0D);
  }
  
  public void stepVanilla() {
    mc.player.setPosition(mc.player.posX, mc.player.posY + (getStepHeight()).height, mc.player.posZ);
  }
  
  public void updateStepPackets(double[] stepArray) {
    for (double v : stepArray)
      mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + v, mc.player.posZ, mc.player.onGround)); 
  }
  
  public StepHeight getStepHeight() {
    if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 1.0D, this.forwardStep[1])).isEmpty() && !mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 0.6D, this.forwardStep[1])).isEmpty())
      return StepHeight.One; 
    if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 1.6D, this.forwardStep[1])).isEmpty() && !mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 1.4D, this.forwardStep[1])).isEmpty())
      return StepHeight.OneHalf; 
    if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 2.1D, this.forwardStep[1])).isEmpty() && !mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 1.9D, this.forwardStep[1])).isEmpty())
      return StepHeight.Two; 
    if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 2.6D, this.forwardStep[1])).isEmpty() && !mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 2.4D, this.forwardStep[1])).isEmpty())
      return StepHeight.TwoHalf; 
    return StepHeight.Unsafe;
  }
  
  public enum StepHeight {
    One(1.0D, new double[] { 0.42D, 0.753D }),
    OneHalf(1.5D, new double[] { 0.42D, 0.75D, 1.0D, 1.16D, 1.23D, 1.2D }),
    Two(2.0D, new double[] { 0.42D, 0.78D, 0.63D, 0.51D, 0.9D, 1.21D, 1.45D, 1.43D }),
    TwoHalf(2.5D, new double[] { 0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D }),
    Unsafe(3.0D, new double[] { 0.0D });
    
    double[] stepArray;
    
    double height;
    
    StepHeight(double height, double[] stepArray) {
      this.height = height;
      this.stepArray = stepArray;
    }
  }
}
