//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.client;

import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class HitMarkers extends Module {
  int renderTicks = 80;
  
  int regi = 0;
  
  public Setting<Integer> red;
  
  public Setting<Integer> green;
  
  public Setting<Integer> blue;
  
  public Setting<Integer> thickness;
  
  public Setting<Integer> time;
  
  public Setting<Boolean> iq2;
  
  public HitMarkers() {
    super("HitMarkers", "hitmarker thingys.", Module.Category.CLIENT, false, false, false);
    this.red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    this.green = register(new Setting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    this.blue = register(new Setting("Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    this.thickness = register(new Setting("Thickness", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(6)));
    this.time = register(new Setting("Time", Integer.valueOf(40), Integer.valueOf(0), Integer.valueOf(80)));
    this.iq2 = register(new Setting("Debug", Boolean.valueOf(false), "debug"));
  }
  
  public void onEnable() {
    MinecraftForge.EVENT_BUS.register(this);
    this.regi = 1;
  }
  
  public void onDisable() {
    MinecraftForge.EVENT_BUS.unregister(this);
    this.regi = 0;
  }
  
  public void onUpdate() {
    if (this.regi == 0) {
      MinecraftForge.EVENT_BUS.register(this);
      this.regi = 1;
    } 
  }
  
  public void onRender2D(Render2DEvent event) {
    if (this.renderTicks < ((Integer)this.time.getValue()).intValue()) {
      ScaledResolution resolution = new ScaledResolution(mc);
      RenderUtil.drawLine(resolution.getScaledWidth() / 2.0F - 4.0F, resolution.getScaledHeight() / 2.0F - 4.0F, resolution.getScaledWidth() / 2.0F - 8.0F, resolution.getScaledHeight() / 2.0F - 8.0F, ((Integer)this.thickness.getValue()).intValue(), (new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue())).getRGB());
      RenderUtil.drawLine(resolution.getScaledWidth() / 2.0F + 4.0F, resolution.getScaledHeight() / 2.0F - 4.0F, resolution.getScaledWidth() / 2.0F + 8.0F, resolution.getScaledHeight() / 2.0F - 8.0F, ((Integer)this.thickness.getValue()).intValue(), (new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue())).getRGB());
      RenderUtil.drawLine(resolution.getScaledWidth() / 2.0F - 4.0F, resolution.getScaledHeight() / 2.0F + 4.0F, resolution.getScaledWidth() / 2.0F - 8.0F, resolution.getScaledHeight() / 2.0F + 8.0F, ((Integer)this.thickness.getValue()).intValue(), (new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue())).getRGB());
      RenderUtil.drawLine(resolution.getScaledWidth() / 2.0F + 4.0F, resolution.getScaledHeight() / 2.0F + 4.0F, resolution.getScaledWidth() / 2.0F + 8.0F, resolution.getScaledHeight() / 2.0F + 8.0F, ((Integer)this.thickness.getValue()).intValue(), (new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue())).getRGB());
    } 
  }
  
  @SubscribeEvent
  public void onAttackEntity(AttackEntityEvent event) {
    this.renderTicks = 0;
  }
  
  public void onTick() {
    this.renderTicks++;
  }
}
