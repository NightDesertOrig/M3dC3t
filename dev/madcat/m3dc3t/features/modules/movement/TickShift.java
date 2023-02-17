//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;

public class TickShift extends Module {
  public final Setting<Float> multiplier = register(new Setting("Multiplier", Float.valueOf(1.8F), Float.valueOf(0.1F), Float.valueOf(20.0F)));
  
  public final Setting<Integer> disableTicks = register(new Setting("Disable Ticks", Integer.valueOf(30), Integer.valueOf(1), Integer.valueOf(100)));
  
  public final Setting<Integer> noPauseTicks = register(new Setting("UnPause Ticks", Integer.valueOf(30), Integer.valueOf(1), Integer.valueOf(100)));
  
  public final Setting<Mode> disableMode = register(new Setting("Disable Mode", Mode.Pause));
  
  public int ticksPassed = 0;
  
  public int unPauseTicks = 0;
  
  public boolean pause = false;
  
  public TickShift() {
    super("TickShift", "Changes tick speed at certain intervals to bypass anticheat and go faster.", Module.Category.MOVEMENT, true, false, false);
  }
  
  public void onUpdate() {
    this.ticksPassed++;
    if (!this.pause)
      mc.timer.tickLength = 50.0F / getMultiplier(); 
    if (this.pause) {
      this.unPauseTicks++;
      mc.timer.tickLength = 50.0F;
    } 
    if (this.disableMode.getValue() != Mode.Pause)
      this.pause = false; 
    if (this.ticksPassed >= ((Integer)this.disableTicks.getValue()).intValue()) {
      this.ticksPassed = 0;
      if (this.disableMode.getValue() == Mode.Disable)
        disable(); 
      if (this.disableMode.getValue() == Mode.Pause)
        if (this.unPauseTicks <= ((Integer)this.noPauseTicks.getValue()).intValue()) {
          this.pause = true;
        } else if (this.unPauseTicks >= ((Integer)this.noPauseTicks.getValue()).intValue()) {
          this.pause = false;
          this.unPauseTicks = 0;
        }  
    } 
  }
  
  public void onDisable() {
    mc.timer.tickLength = 50.0F;
  }
  
  public float getMultiplier() {
    if (isEnabled())
      return ((Float)this.multiplier.getValue()).floatValue(); 
    return 1.0F;
  }
  
  public enum Mode {
    Pause, Disable;
  }
}
