//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldModify extends Module {
  public Setting<Boolean> color;
  
  private Setting<Integer> red;
  
  private Setting<Integer> green;
  
  private Setting<Integer> blue;
  
  private Setting<Integer> alpha;
  
  public Setting<Boolean> timeChanger;
  
  private Setting<Integer> time;
  
  public WorldModify() {
    super("WorldModify", "Change the world something.", Module.Category.RENDER, true, false, false);
    this.color = register(new Setting("ColorChanger", Boolean.valueOf(false)));
    this.red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.color.getValue()).booleanValue()));
    this.green = register(new Setting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.color.getValue()).booleanValue()));
    this.blue = register(new Setting("Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.color.getValue()).booleanValue()));
    this.alpha = register(new Setting("Alpha", Integer.valueOf(35), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.color.getValue()).booleanValue()));
    this.timeChanger = register(new Setting("TimeChanger", Boolean.valueOf(false)));
    this.time = register(new Setting("Time", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(24000), v -> ((Boolean)this.timeChanger.getValue()).booleanValue()));
  }
  
  public void onRender2D(Render2DEvent event) {
    if (((Boolean)this.color.getValue()).booleanValue())
      RenderUtil.drawRectangleCorrectly(0, 0, 1920, 1080, ColorUtil.toRGBA(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue())); 
  }
  
  @SubscribeEvent
  public void init(WorldEvent event) {
    if (((Boolean)this.timeChanger.getValue()).booleanValue())
      event.getWorld().setWorldTime(((Integer)this.time.getValue()).intValue()); 
  }
  
  @SubscribeEvent
  public void onPacketReceive(PacketEvent.Receive event) {
    if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTimeUpdate && ((Boolean)this.timeChanger.getValue()).booleanValue())
      event.setCanceled(true); 
  }
}
