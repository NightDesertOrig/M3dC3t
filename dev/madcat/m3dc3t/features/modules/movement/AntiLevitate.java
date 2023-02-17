//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;
import java.util.Objects;
import net.minecraft.potion.Potion;

public class AntiLevitate extends Module {
  public AntiLevitate() {
    super("AntiLevitate", "Removes shulker levitation effect.", Module.Category.MOVEMENT, false, false, false);
  }
  
  public void onUpdate() {
    if (mc.player.isPotionActive(Objects.<Potion>requireNonNull(Potion.getPotionFromResourceLocation("levitation"))))
      mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation("levitation")); 
  }
}
