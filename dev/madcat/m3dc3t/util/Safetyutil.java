//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import dev.madcat.m3dc3t.features.Feature;
import dev.madcat.m3dc3t.features.modules.combat.AutoCrystal;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import net.minecraft.entity.Entity;

public class Safetyutil extends Feature implements Runnable {
  private final Timer syncTimer;
  
  private static boolean SAFE = false;
  
  private ScheduledExecutorService service;
  
  public Safetyutil() {
    this.syncTimer = new Timer();
    this;
    SAFE = false;
  }
  
  public void run() {
    if (AutoCrystal.getInstance().isOff() || (AutoCrystal.getInstance()).threadMode.getValue() == AutoCrystal.ThreadMode.NONE) {
      this;
      doSafetyCheck();
    } 
  }
  
  public static void doSafetyCheck() {
    if (!Feature.fullNullCheck()) {
      boolean safe = true;
      ArrayList<Entity> crystals = new ArrayList<>(mc.world.loadedEntityList);
      for (Entity crystal : crystals) {
        if (!(crystal instanceof net.minecraft.entity.item.EntityEnderCrystal) || 
          DamageUtil.calculateDamage(crystal, (Entity)mc.player) <= 4.0D)
          continue; 
        safe = false;
      } 
      SAFE = safe;
    } 
  }
  
  public void onUpdate() {
    run();
  }
  
  public String getSafetyString() {
    this;
    if (SAFE)
      return "搂aSecure"; 
    return "搂cUnsafe";
  }
  
  public boolean isSafe() {
    this;
    return SAFE;
  }
  
  public ScheduledExecutorService getService() {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    return service;
  }
}
