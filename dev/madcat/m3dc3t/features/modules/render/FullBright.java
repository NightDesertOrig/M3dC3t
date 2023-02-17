//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Module {
  public Setting<SwingMode> mode;
  
  float oldBright;
  
  public FullBright() {
    super("FullBright", "All bright.", Module.Category.RENDER, false, false, false);
    this.mode = register(new Setting("Swing", SwingMode.Gamma));
  }
  
  public enum SwingMode {
    Gamma, Potion;
  }
  
  public void onUpdate() {
    if (nullCheck())
      return; 
    if (this.mode.getValue() == SwingMode.Potion)
      mc.player.addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 80950, 1, false, false))); 
  }
  
  public void onEnable() {
    if (nullCheck())
      return; 
    this.oldBright = mc.gameSettings.gammaSetting;
    if (this.mode.getValue() == SwingMode.Gamma)
      mc.gameSettings.gammaSetting = 100.0F; 
  }
  
  public void onDisable() {
    mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
    if (this.mode.getValue() == SwingMode.Gamma)
      mc.gameSettings.gammaSetting = this.oldBright; 
  }
}
