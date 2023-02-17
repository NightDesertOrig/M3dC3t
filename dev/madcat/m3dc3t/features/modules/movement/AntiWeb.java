//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.event.events.UpdateWalkingPlayerEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.MathUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiWeb extends Module {
  private final Setting<Float> speed = register(new Setting("Factor", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(10.0F)));
  
  public AntiWeb() {
    super("AntiWeb", "Stops you being slowed down by webs.", Module.Category.MOVEMENT, true, false, false);
  }
  
  @SubscribeEvent
  public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
    if (event.getStage() == 1)
      return; 
    if (mc.player.isInWeb) {
      double[] calc = MathUtil.directionSpeed(((Float)this.speed.getValue()).floatValue() / 10.0D);
      mc.player.motionX = calc[0];
      mc.player.motionZ = calc[1];
      if (mc.gameSettings.keyBindSneak.isKeyDown())
        mc.player.motionY -= (((Float)this.speed.getValue()).floatValue() / 10.0F); 
    } 
  }
}
