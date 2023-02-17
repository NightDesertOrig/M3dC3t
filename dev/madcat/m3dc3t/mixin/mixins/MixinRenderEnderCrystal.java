//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.mixin.mixins;

import dev.madcat.m3dc3t.event.events.RenderEntityModelEvent;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import dev.madcat.m3dc3t.features.modules.render.CrystalScale;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderEnderCrystal.class})
public abstract class MixinRenderEnderCrystal {
  @Final
  @Shadow
  private static ResourceLocation ENDER_CRYSTAL_TEXTURES;
  
  @Shadow
  public ModelBase modelEnderCrystal;
  
  @Shadow
  public ModelBase modelEnderCrystalNoBase;
  
  @Shadow
  public abstract void doRender(EntityEnderCrystal paramEntityEnderCrystal, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
  
  @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
  public void renderModelBaseHook(ModelBase model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (CrystalScale.INSTANCE.isEnabled())
      GlStateManager.scale(((Float)CrystalScale.INSTANCE.scale.getValue()).floatValue(), ((Float)CrystalScale.INSTANCE.scale.getValue()).floatValue(), ((Float)CrystalScale.INSTANCE.scale.getValue()).floatValue()); 
    if (CrystalScale.INSTANCE.isEnabled() && ((Boolean)CrystalScale.INSTANCE.wireframe.getValue()).booleanValue()) {
      RenderEntityModelEvent event = new RenderEntityModelEvent(0, model, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      CrystalScale.INSTANCE.onRenderModel(event);
    } 
    if (CrystalScale.INSTANCE.isEnabled() && ((Boolean)CrystalScale.INSTANCE.chams.getValue()).booleanValue()) {
      GL11.glPushAttrib(1048575);
      GL11.glDisable(3008);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(1.5F);
      GL11.glEnable(2960);
      if (((Boolean)CrystalScale.INSTANCE.rainbow.getValue()).booleanValue()) {
        Color rainbowColor1 = ((Boolean)CrystalScale.INSTANCE.rainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()) : new Color(RenderUtil.getRainbow(((Integer)CrystalScale.INSTANCE.speed.getValue()).intValue() * 100, 0, ((Integer)CrystalScale.INSTANCE.saturation.getValue()).intValue() / 100.0F, ((Integer)CrystalScale.INSTANCE.brightness.getValue()).intValue() / 100.0F));
        Color rainbowColor2 = EntityUtil.getColor(entity, rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue(), ((Integer)CrystalScale.INSTANCE.alpha.getValue()).intValue(), true);
        if (((Boolean)CrystalScale.INSTANCE.throughWalls.getValue()).booleanValue()) {
          GL11.glDisable(2929);
          GL11.glDepthMask(false);
        } 
        GL11.glEnable(10754);
        GL11.glColor4f(rainbowColor2.getRed() / 255.0F, rainbowColor2.getGreen() / 255.0F, rainbowColor2.getBlue() / 255.0F, ((Integer)CrystalScale.INSTANCE.alpha.getValue()).intValue() / 255.0F);
        model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (((Boolean)CrystalScale.INSTANCE.throughWalls.getValue()).booleanValue()) {
          GL11.glEnable(2929);
          GL11.glDepthMask(true);
        } 
      } else if (((Boolean)CrystalScale.INSTANCE.xqz.getValue()).booleanValue() && ((Boolean)CrystalScale.INSTANCE.throughWalls.getValue()).booleanValue()) {
        Color hiddenColor = EntityUtil.getColor(entity, ((Integer)CrystalScale.INSTANCE.hiddenRed.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.hiddenGreen.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.hiddenBlue.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.hiddenAlpha.getValue()).intValue(), true);
        Color color = EntityUtil.getColor(entity, ((Integer)CrystalScale.INSTANCE.red.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.green.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.blue.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.alpha.getValue()).intValue(), true), visibleColor = color;
        if (((Boolean)CrystalScale.INSTANCE.throughWalls.getValue()).booleanValue()) {
          GL11.glDisable(2929);
          GL11.glDepthMask(false);
        } 
        GL11.glEnable(10754);
        GL11.glColor4f(hiddenColor.getRed() / 255.0F, hiddenColor.getGreen() / 255.0F, hiddenColor.getBlue() / 255.0F, ((Integer)CrystalScale.INSTANCE.alpha.getValue()).intValue() / 255.0F);
        model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (((Boolean)CrystalScale.INSTANCE.throughWalls.getValue()).booleanValue()) {
          GL11.glEnable(2929);
          GL11.glDepthMask(true);
        } 
        GL11.glColor4f(visibleColor.getRed() / 255.0F, visibleColor.getGreen() / 255.0F, visibleColor.getBlue() / 255.0F, ((Integer)CrystalScale.INSTANCE.alpha.getValue()).intValue() / 255.0F);
        model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      } else {
        Color color2 = ((Boolean)CrystalScale.INSTANCE.rainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()) : EntityUtil.getColor(entity, ((Integer)CrystalScale.INSTANCE.red.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.green.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.blue.getValue()).intValue(), ((Integer)CrystalScale.INSTANCE.alpha.getValue()).intValue(), true), visibleColor = color2;
        if (((Boolean)CrystalScale.INSTANCE.throughWalls.getValue()).booleanValue()) {
          GL11.glDisable(2929);
          GL11.glDepthMask(false);
        } 
        GL11.glEnable(10754);
        GL11.glColor4f(visibleColor.getRed() / 255.0F, visibleColor.getGreen() / 255.0F, visibleColor.getBlue() / 255.0F, ((Integer)CrystalScale.INSTANCE.alpha.getValue()).intValue() / 255.0F);
        model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (((Boolean)CrystalScale.INSTANCE.throughWalls.getValue()).booleanValue()) {
          GL11.glEnable(2929);
          GL11.glDepthMask(true);
        } 
      } 
      GL11.glEnable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glEnable(3008);
      GL11.glPopAttrib();
    } else {
      model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    } 
    if (CrystalScale.INSTANCE.isEnabled())
      GlStateManager.scale(1.0F / ((Float)CrystalScale.INSTANCE.scale.getValue()).floatValue(), 1.0F / ((Float)CrystalScale.INSTANCE.scale.getValue()).floatValue(), 1.0F / ((Float)CrystalScale.INSTANCE.scale.getValue()).floatValue()); 
  }
  
  @Inject(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = {@At("RETURN")}, cancellable = true)
  public void IdoRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
    Minecraft mc = Minecraft.getMinecraft();
    mc.gameSettings.fancyGraphics = false;
  }
}
