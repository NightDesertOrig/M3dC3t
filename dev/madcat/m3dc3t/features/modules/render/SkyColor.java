package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import java.awt.Color;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyColor extends Module {
  public Setting<Integer> red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
  
  public Setting<Integer> green = register(new Setting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
  
  public Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
  
  public Setting<Boolean> rainbow = register(new Setting("Rainbow", Boolean.valueOf(true)));
  
  int registered;
  
  public SkyColor() {
    super("FogColor", "Changes the color of the fog and sky.", Module.Category.RENDER, false, false, false);
    this.registered = 0;
  }
  
  @SubscribeEvent
  public void fogColors(EntityViewRenderEvent.FogColors event) {
    event.setRed(((Integer)this.red.getValue()).intValue() / 255.0F);
    event.setGreen(((Integer)this.green.getValue()).intValue() / 255.0F);
    event.setBlue(((Integer)this.blue.getValue()).intValue() / 255.0F);
  }
  
  @SubscribeEvent
  public void fog_density(EntityViewRenderEvent.FogDensity event) {
    event.setDensity(0.0F);
    event.setCanceled(true);
  }
  
  public void onDisable() {
    MinecraftForge.EVENT_BUS.unregister(this);
    this.registered = 0;
  }
  
  public void onUpdate() {
    if (this.registered == 0) {
      MinecraftForge.EVENT_BUS.register(this);
      this.registered = 1;
    } 
    if (((Boolean)this.rainbow.getValue()).booleanValue())
      doRainbow(); 
  }
  
  public void doRainbow() {
    float[] tick_color = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F };
    int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8F, 0.8F);
    this.red.setValue(Integer.valueOf(color_rgb_o >> 16 & 0xFF));
    this.green.setValue(Integer.valueOf(color_rgb_o >> 8 & 0xFF));
    this.blue.setValue(Integer.valueOf(color_rgb_o & 0xFF));
  }
}
