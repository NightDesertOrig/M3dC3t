//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.UpdateWalkingPlayerEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.DamageUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import dev.madcat.m3dc3t.util.MathUtil;
import dev.madcat.m3dc3t.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KillAura extends Module {
  public static Entity target;
  
  private final Timer timer = new Timer();
  
  public Setting<Float> range = register(new Setting("Range", Float.valueOf(6.0F), Float.valueOf(0.1F), Float.valueOf(7.0F)));
  
  public Setting<Boolean> delay = register(new Setting("HitDelay", Boolean.valueOf(true)));
  
  public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
  
  public Setting<Boolean> onlySharp = register(new Setting("SwordOnly", Boolean.valueOf(true)));
  
  public Setting<Float> raytrace = register(new Setting("Raytrace", Float.valueOf(3.0F), Float.valueOf(0.1F), Float.valueOf(3.0F), "Wall Range."));
  
  public Setting<Boolean> players = register(new Setting("Players", Boolean.valueOf(true)));
  
  public Setting<Boolean> mobs = register(new Setting("Mobs", Boolean.valueOf(false)));
  
  public Setting<Boolean> animals = register(new Setting("Animals", Boolean.valueOf(false)));
  
  public Setting<Boolean> vehicles = register(new Setting("Entities", Boolean.valueOf(false)));
  
  public Setting<Boolean> projectiles = register(new Setting("Projectiles", Boolean.valueOf(false)));
  
  public Setting<Boolean> tps = register(new Setting("TpsSync", Boolean.valueOf(true)));
  
  public Setting<Boolean> silent = register(new Setting("Silent", Boolean.valueOf(false)));
  
  public Setting<Boolean> packet = register(new Setting("Packet", Boolean.valueOf(false)));
  
  public KillAura() {
    super("KillAura", "Attacks entities automatically.", Module.Category.COMBAT, true, false, false);
  }
  
  public void onTick() {
    if (!((Boolean)this.rotate.getValue()).booleanValue())
      doKillaura(); 
  }
  
  @SubscribeEvent
  public void onUpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent event) {
    if (event.getStage() == 0 && ((Boolean)this.rotate.getValue()).booleanValue())
      doKillaura(); 
  }
  
  private void doKillaura() {
    int wait = 0;
    if (((Boolean)this.onlySharp.getValue()).booleanValue() && !EntityUtil.holdingWeapon((EntityPlayer)mc.player) && !((Boolean)this.silent.getValue()).booleanValue()) {
      target = null;
      return;
    } 
    int n = !((Boolean)this.delay.getValue()).booleanValue() ? 0 : (wait = (int)(DamageUtil.getCooldownByWeapon((EntityPlayer)mc.player) * (((Boolean)this.tps.getValue()).booleanValue() ? M3dC3t.serverManager.getTpsFactor() : 1.0F)));
    if (((Boolean)this.silent.getValue()).booleanValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD) != -1)
      wait = 600; 
    if (!this.timer.passedMs(wait))
      return; 
    target = getTarget();
    if (target == null)
      return; 
    if (((Boolean)this.rotate.getValue()).booleanValue())
      M3dC3t.rotationManager.lookAtEntity(target); 
    if (((Boolean)this.silent.getValue()).booleanValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD) != -1) {
      int old = mc.player.inventory.currentItem;
      int sword = InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD);
      switchToSlot(sword);
      EntityUtil.attackEntity(target, ((Boolean)this.packet.getValue()).booleanValue(), true);
      switchToSlot(old);
    } else {
      EntityUtil.attackEntity(target, ((Boolean)this.packet.getValue()).booleanValue(), true);
    } 
    this.timer.reset();
  }
  
  private Entity getTarget() {
    Entity closestEntity = null;
    for (Entity entity : mc.world.loadedEntityList) {
      if (((!((Boolean)this.players.getValue()).booleanValue() || !(entity instanceof EntityPlayer)) && (!((Boolean)this.mobs.getValue()).booleanValue() || !EntityUtil.isMobAggressive(entity)) && (!((Boolean)this.animals.getValue()).booleanValue() || !EntityUtil.isPassive(entity)) && (!((Boolean)this.vehicles.getValue()).booleanValue() || !EntityUtil.isVehicle(entity)) && (!((Boolean)this.projectiles.getValue()).booleanValue() || !EntityUtil.isProjectile(entity))) || (entity instanceof net.minecraft.entity.EntityLivingBase && EntityUtil.isntValid(entity, ((Float)this.range.getValue()).floatValue())) || (!mc.player.canEntityBeSeen(entity) && mc.player.getDistanceSq(entity) > MathUtil.square(((Float)this.raytrace.getValue()).floatValue())))
        continue; 
      if (closestEntity == null) {
        closestEntity = entity;
        continue;
      } 
      if (closestEntity.getDistance((Entity)mc.player) <= entity.getDistance((Entity)mc.player))
        continue; 
      closestEntity = entity;
    } 
    return closestEntity;
  }
  
  private void switchToSlot(int slot) {
    mc.player.inventory.currentItem = slot;
    mc.playerController.updateController();
  }
  
  public String getDisplayInfo() {
    if (target instanceof EntityPlayer)
      return target.getName(); 
    return null;
  }
}
