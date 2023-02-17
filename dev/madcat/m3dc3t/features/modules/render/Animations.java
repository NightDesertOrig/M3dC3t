//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;

public class Animations extends Module {
  private final Setting<Mode> mode = register(new Setting("Anim", Mode.OLD));
  
  private final Setting<Swing> swing = register(new Setting("Swing", Swing.Mainhand));
  
  private final Setting<Boolean> slow = register(new Setting("Slow", Boolean.valueOf(false)));
  
  public Animations() {
    super("Animations", "Change animations.", Module.Category.RENDER, true, false, false);
  }
  
  public void onUpdate() {
    if (this.swing.getValue() == Swing.Offhand)
      mc.player.swingingHand = EnumHand.OFF_HAND; 
    if (this.mode.getValue() == Mode.OLD) {
      mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0F;
      mc.entityRenderer.itemRenderer.itemStackMainHand = mc.player.getHeldItemMainhand();
    } 
    if (((Boolean)this.slow.getValue()).booleanValue())
      mc.player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 255000, 3)); 
    if (!((Boolean)this.slow.getValue()).booleanValue())
      mc.player.removePotionEffect(MobEffects.MINING_FATIGUE); 
  }
  
  public void onDisable() {
    mc.player.removePotionEffect(MobEffects.MINING_FATIGUE);
  }
  
  private enum Swing {
    Mainhand, Offhand;
  }
  
  private enum Mode {
    Normal, OLD;
  }
}
