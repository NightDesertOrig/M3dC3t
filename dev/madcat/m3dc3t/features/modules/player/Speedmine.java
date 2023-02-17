//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;

public class Speedmine extends Module {
  private final Setting<Boolean> slow = register(new Setting("NoBreakDelay", Boolean.valueOf(true)));
  
  public Setting<Float> damage = register(new Setting("Damage", Float.valueOf(0.7F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
  
  public Speedmine() {
    super("Speedmine", "Speeds up mining.", Module.Category.PLAYER, true, false, false);
  }
  
  public void onUpdate() {
    if (((Boolean)this.slow.getValue()).booleanValue())
      mc.playerController.blockHitDelay = 0; 
    if (mc.playerController.curBlockDamageMP >= ((Float)this.damage.getValue()).floatValue())
      mc.playerController.curBlockDamageMP = 1.0F; 
  }
}
