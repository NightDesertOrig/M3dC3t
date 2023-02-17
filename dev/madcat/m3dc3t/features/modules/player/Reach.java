//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;

public class Reach extends Module {
  private final Setting<Integer> Reach = register(new Setting("Reach", Integer.valueOf(6), Integer.valueOf(5), Integer.valueOf(10)));
  
  public Reach() {
    super("Reach", "Increase reach range.", Module.Category.PLAYER, true, false, false);
  }
  
  public void onUpdate() {
    mc.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(((Integer)this.Reach.getValue()).intValue());
  }
  
  public void onDisable() {
    mc.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(5.0D);
  }
}
