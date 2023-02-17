//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.event.events.MoveEvent;
import dev.madcat.m3dc3t.event.events.UpdateWalkingPlayerEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.manager.ModuleManager;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Strafe extends Module {
  public Setting<Mode> mode = register(new Setting("Mode", Mode.NORMAL));
  
  private static Strafe INSTANCE = new Strafe();
  
  private double lastDist;
  
  private double moveSpeed;
  
  int stage;
  
  public Strafe() {
    super("Strafe", "mobility more flexible.", Module.Category.MOVEMENT, true, false, false);
    setInstance();
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public static Strafe getInstance() {
    if (INSTANCE != null)
      return INSTANCE; 
    INSTANCE = new Strafe();
    return INSTANCE;
  }
  
  @SubscribeEvent
  public void onUpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent event) {
    if (event.getStage() == 1 && fullNullCheck())
      return; 
    this.lastDist = Math.sqrt((mc.player.posX - mc.player.prevPosX) * (mc.player.posX - mc.player.prevPosX) + (mc.player.posZ - mc.player.prevPosZ) * (mc.player.posZ - mc.player.prevPosZ));
  }
  
  @SubscribeEvent
  public void onStrafe(MoveEvent event) {
    double motionY;
    if (Anchor.Anchoring)
      return; 
    if (mc.player.onGround)
      this.stage = 2; 
    switch (this.stage) {
      case 0:
        this.stage++;
        this.lastDist = 0.0D;
        break;
      case 2:
        motionY = 0.40123128D;
        if (!mc.player.onGround || !ModuleManager.getModuleByName("AutoJump").isEnabled() || !mc.gameSettings.keyBindJump.isKeyDown())
          break; 
        if (mc.player.isPotionActive(MobEffects.JUMP_BOOST))
          motionY += ((mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F); 
        mc.player.motionY = motionY;
        event.setY(mc.player.motionY);
        this.moveSpeed *= (this.mode.getValue() == Mode.NORMAL) ? 1.67D : 2.149D;
        break;
      case 3:
        this.moveSpeed = this.lastDist - ((this.mode.getValue() == Mode.NORMAL) ? 0.6896D : 0.795D) * (this.lastDist - getBaseMoveSpeed());
        break;
      default:
        if ((mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(0.0D, mc.player.motionY, 0.0D)).size() > 0 || mc.player.collidedVertically) && this.stage > 0)
          this.stage = (mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F) ? 1 : 0; 
        this.moveSpeed = this.lastDist - this.lastDist / ((this.mode.getValue() == Mode.NORMAL) ? 730.0D : 159.0D);
        break;
    } 
    this.moveSpeed = ((!mc.gameSettings.keyBindJump.isKeyDown() || !ModuleManager.getModuleByName("AutoJump").isEnabled()) && mc.player.onGround) ? getBaseMoveSpeed() : Math.max(this.moveSpeed, getBaseMoveSpeed());
    double n = mc.player.movementInput.moveForward;
    double n2 = mc.player.movementInput.moveStrafe;
    double n3 = mc.player.rotationYaw;
    if (n == 0.0D && n2 == 0.0D) {
      event.setX(0.0D);
      event.setZ(0.0D);
    } else if (n != 0.0D && n2 != 0.0D) {
      n *= Math.sin(0.7853981633974483D);
      n2 *= Math.cos(0.7853981633974483D);
    } 
    double n4 = (this.mode.getValue() == Mode.NORMAL) ? 0.993D : 0.99D;
    event.setX((n * this.moveSpeed * -Math.sin(Math.toRadians(n3)) + n2 * this.moveSpeed * Math.cos(Math.toRadians(n3))) * n4);
    event.setZ((n * this.moveSpeed * Math.cos(Math.toRadians(n3)) - n2 * this.moveSpeed * -Math.sin(Math.toRadians(n3))) * n4);
    this.stage++;
    event.setCanceled(true);
  }
  
  public double getBaseMoveSpeed() {
    double n = 0.2873D;
    if (!mc.player.isPotionActive(MobEffects.SPEED))
      return n; 
    n *= 1.0D + 0.2D * (((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED))).getAmplifier() + 1);
    return n;
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    return this.mode.currentEnumName();
  }
  
  public enum Mode {
    NORMAL, Strict;
  }
}
