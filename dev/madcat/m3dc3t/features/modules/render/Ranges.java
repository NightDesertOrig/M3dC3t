//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.event.events.Render3DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Ranges extends Module {
  private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
  
  private final Setting<Double> radius = register(new Setting("Radius", Double.valueOf(4.5D), Double.valueOf(0.1D), Double.valueOf(8.0D)));
  
  public Ranges() {
    super("Ranges", "Draws a circle around the player.", Module.Category.RENDER, false, false, false);
  }
  
  public void onUpdate() {}
  
  public void onRender3D(Render3DEvent event) {
    GlStateManager.pushMatrix();
    GlStateManager.enableBlend();
    GlStateManager.disableTexture2D();
    GlStateManager.enableDepth();
    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    RenderManager renderManager = mc.getRenderManager();
    float hue = (float)(System.currentTimeMillis() % 7200L) / 7200.0F;
    Color color = new Color(Color.HSBtoRGB(hue, 1.0F, 1.0F));
    ArrayList<Vec3d> hVectors = new ArrayList<>();
    double x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks() - renderManager.renderPosX;
    double y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks() - renderManager.renderPosY;
    double z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks() - renderManager.renderPosZ;
    GL11.glLineWidth(((Float)this.lineWidth.getValue()).floatValue());
    GL11.glBegin(1);
    for (int i = 0; i <= 360; i++) {
      Vec3d vec = new Vec3d(x + Math.sin(i * Math.PI / 180.0D) * ((Double)this.radius.getValue()).doubleValue(), y + 0.1D, z + Math.cos(i * Math.PI / 180.0D) * ((Double)this.radius.getValue()).doubleValue());
      RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), vec, false, false, true);
      hVectors.add(vec);
    } 
    for (int j = 0; j < hVectors.size() - 1; j++) {
      GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
      GL11.glVertex3d(((Vec3d)hVectors.get(j)).x, ((Vec3d)hVectors.get(j)).y, ((Vec3d)hVectors.get(j)).z);
      GL11.glVertex3d(((Vec3d)hVectors.get(j + 1)).x, ((Vec3d)hVectors.get(j + 1)).y, ((Vec3d)hVectors.get(j + 1)).z);
      color = new Color(Color.HSBtoRGB(hue += 0.0027777778F, 1.0F, 1.0F));
    } 
    GL11.glEnd();
    GlStateManager.resetColor();
    GlStateManager.disableDepth();
    GlStateManager.enableTexture2D();
    GlStateManager.disableBlend();
    GlStateManager.popMatrix();
  }
}
