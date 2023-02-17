//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.manager;

import dev.madcat.m3dc3t.features.Feature;

public class TimerManager extends Feature {
  private float timer = 1.0F;
  
  public void unload() {
    this.timer = 1.0F;
    mc.timer.tickLength = 50.0F;
  }
  
  public void update() {
    mc.timer.tickLength = 50.0F / ((this.timer <= 0.0F) ? 0.1F : this.timer);
  }
  
  public void setTimer(float timer) {
    if (timer > 0.0F)
      this.timer = timer; 
  }
  
  public float getTimer() {
    return this.timer;
  }
  
  public void reset() {
    this.timer = 1.0F;
  }
}
