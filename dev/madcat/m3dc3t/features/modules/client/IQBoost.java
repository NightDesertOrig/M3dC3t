//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.client;

import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.features.Feature;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.Util;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class IQBoost extends Module {
  public IQBoost() {
    super("IQBoost", "Increase your IQ.", Module.Category.CLIENT, true, false, false);
    this.iq = register(new Setting("IQ", Float.valueOf(0.0F), Float.valueOf(-1000.0F), Float.valueOf(1000.0F)));
    this.iq2 = register(new Setting("IQ+++", Boolean.valueOf(false), "Cleans your chat"));
    this.imageX = register(new Setting("x", Integer.valueOf(18), Integer.valueOf(0), Integer.valueOf(300), v -> ((Boolean)this.iq2.getValue()).booleanValue()));
    this.imageY = register(new Setting("y", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(300), v -> ((Boolean)this.iq2.getValue()).booleanValue()));
  }
  
  public static final ResourceLocation mark = new ResourceLocation("textures/setting2.png");
  
  private int color;
  
  private final Setting<Float> iq;
  
  public Setting<Boolean> iq2;
  
  public Setting<Integer> imageX;
  
  public Setting<Integer> imageY;
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    return ((Float)this.iq.getValue()).floatValue() + "IQ";
  }
  
  public void renderLogo() {
    int x = ((Integer)this.imageX.getValue()).intValue();
    int y = ((Integer)this.imageY.getValue()).intValue();
    Util.mc.renderEngine.bindTexture(mark);
    GlStateManager.color(255.0F, 255.0F, 255.0F);
    Gui.drawScaledCustomSizeModalRect(x - 2, y - 36, 7.0F, 7.0F, 53, 53, 60, 60, 60.0F, 60.0F);
  }
  
  public void onRender2D(Render2DEvent event) {
    if (((Boolean)this.iq2.getValue()).booleanValue() && 
      !Feature.fullNullCheck()) {
      this.color = ColorUtil.toRGBA(((Integer)(ClickGui.getInstance()).red.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).green.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).blue.getValue()).intValue());
      if (((Boolean)this.enabled.getValue()).booleanValue())
        renderLogo(); 
    } 
  }
}
