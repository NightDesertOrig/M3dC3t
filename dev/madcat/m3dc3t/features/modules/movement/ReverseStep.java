//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.entity.Entity;

public class ReverseStep extends Module {
  private static ReverseStep INSTANCE = new ReverseStep();
  
  private final Setting<Integer> speed = register(new Setting("Speed", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(20)));
  
  private final Setting<Boolean> inliquid = register(new Setting("Liquid", Boolean.valueOf(false)));
  
  private final Setting<Cancel> canceller = register(new Setting("CancelType", Cancel.None));
  
  public ReverseStep() {
    super("ReverseStep", "Rapid decline.", Module.Category.MOVEMENT, true, false, false);
    setInstance();
  }
  
  public static ReverseStep getInstance() {
    if (INSTANCE == null)
      INSTANCE = new ReverseStep(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public void onUpdate() {
    if (nullCheck())
      return; 
    if (mc.player.isSneaking() || mc.player.isDead || mc.player.collidedHorizontally || !mc.player.onGround || (mc.player.isInWater() && !((Boolean)this.inliquid.getValue()).booleanValue()) || (mc.player.isInLava() && !((Boolean)this.inliquid.getValue()).booleanValue()) || mc.player.isOnLadder() || mc.gameSettings.keyBindJump.isKeyDown() || M3dC3t.moduleManager.isModuleEnabled("Burrow") || mc.player.noClip || M3dC3t.moduleManager.isModuleEnabled("Packetfly") || M3dC3t.moduleManager.isModuleEnabled("Phase") || (mc.gameSettings.keyBindSneak.isKeyDown() && this.canceller.getValue() == Cancel.Shift) || (mc.gameSettings.keyBindSneak.isKeyDown() && this.canceller.getValue() == Cancel.Both) || (mc.gameSettings.keyBindJump.isKeyDown() && this.canceller.getValue() == Cancel.Space) || (mc.gameSettings.keyBindJump.isKeyDown() && this.canceller.getValue() == Cancel.Both) || M3dC3t.moduleManager.isModuleEnabled("Strafe"))
      return; 
    for (double y = 0.0D; y < 90.5D; ) {
      if (mc.world.getCollisionBoxes((Entity)mc.player, mc.player.getEntityBoundingBox().offset(0.0D, -y, 0.0D)).isEmpty()) {
        y += 0.01D;
        continue;
      } 
      mc.player.motionY = (-((Integer)this.speed.getValue()).intValue() / 10.0F);
    } 
  }
  
  public enum Cancel {
    None, Space, Shift, Both;
  }
}
