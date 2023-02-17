//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class Shaders extends Module {
  private static final Shaders INSTANCE = new Shaders();
  
  public Setting<Mode> shader = register(new Setting("Mode", Mode.green));
  
  public Shaders() {
    super("Shaders", "Very cool frame.", Module.Category.RENDER, false, false, false);
  }
  
  public void onUpdate() {
    if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof net.minecraft.entity.player.EntityPlayer) {
      if (mc.entityRenderer.getShaderGroup() != null)
        mc.entityRenderer.getShaderGroup().deleteShaderGroup(); 
      try {
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + this.shader.getValue() + ".json"));
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else if (mc.entityRenderer.getShaderGroup() != null && mc.currentScreen == null) {
      mc.entityRenderer.getShaderGroup().deleteShaderGroup();
    } 
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    return this.shader.currentEnumName();
  }
  
  public void onDisable() {
    if (mc.entityRenderer.getShaderGroup() != null)
      mc.entityRenderer.getShaderGroup().deleteShaderGroup(); 
  }
  
  public enum Mode {
    notch, antialias, art, bits, blobs, blobs2, blur, bumpy, color_convolve, creeper, deconverge, desaturate, flip, fxaa, green, invert, ntsc, pencil, phosphor, sobel, spider, wobble;
  }
}
