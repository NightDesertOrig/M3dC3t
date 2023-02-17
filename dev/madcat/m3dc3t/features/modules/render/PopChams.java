//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import com.mojang.authlib.GameProfile;
import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.TotemPopChams;
import java.awt.Color;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class PopChams extends Module {
  public static Setting<Boolean> self;
  
  public static Setting<Boolean> elevator;
  
  public static Setting<Integer> rL;
  
  public static Setting<Integer> gL;
  
  public static Setting<Integer> bL;
  
  public static Setting<Integer> aL;
  
  public static Setting<Integer> rF;
  
  public static Setting<Integer> gF;
  
  public static Setting<Integer> bF;
  
  public static Setting<Integer> aF;
  
  public static Setting<Integer> fadestart;
  
  public static Setting<Float> fadetime;
  
  public static Setting<Boolean> fillrainbow;
  
  public static Setting<Boolean> outlinerainbow;
  
  public static Setting<Boolean> onlyOneEsp;
  
  public static Setting<ElevatorMode> elevatorMode;
  
  EntityOtherPlayerMP player;
  
  ModelPlayer playerModel;
  
  Long startTime;
  
  double alphaFill;
  
  double alphaLine;
  
  public PopChams() {
    super("PopChams", "Pop rendering.", Module.Category.RENDER, true, false, false);
    self = register(new Setting("Render Own Pops", Boolean.valueOf(true)));
    elevator = register(new Setting("Travel", Boolean.valueOf(true)));
    elevatorMode = register(new Setting("Elevator", ElevatorMode.UP, v -> ((Boolean)elevator.getValue()).booleanValue()));
    rL = register(new Setting("Outline Red", Integer.valueOf(30), Integer.valueOf(0), Integer.valueOf(255)));
    bL = register(new Setting("Outline Green", Integer.valueOf(167), Integer.valueOf(0), Integer.valueOf(255)));
    gL = register(new Setting("Outline Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    aL = register(new Setting("Outline Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    outlinerainbow = register(new Setting("Outline Rainbow", Boolean.valueOf(true)));
    rF = register(new Setting("Fill Red", Integer.valueOf(30), Integer.valueOf(0), Integer.valueOf(255)));
    bF = register(new Setting("Fill Green", Integer.valueOf(167), Integer.valueOf(0), Integer.valueOf(255)));
    gF = register(new Setting("Fill Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    aF = register(new Setting("Fill Alpha", Integer.valueOf(140), Integer.valueOf(0), Integer.valueOf(255)));
    fillrainbow = register(new Setting("Fill Rainbow", Boolean.valueOf(true)));
    fadestart = register(new Setting("Fade Start", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255)));
    fadetime = register(new Setting("Fade Time", Float.valueOf(0.5F), Float.valueOf(0.0F), Float.valueOf(2.0F)));
    onlyOneEsp = register(new Setting("Only Render One", Boolean.valueOf(true)));
  }
  
  public static void renderEntity(EntityLivingBase entity, ModelBase modelBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, int scale) {
    if (mc.getRenderManager() == null)
      return; 
    float partialTicks = mc.getRenderPartialTicks();
    double x = entity.posX - (mc.getRenderManager()).viewerPosX;
    double y = entity.posY - (mc.getRenderManager()).viewerPosY;
    double z = entity.posZ - (mc.getRenderManager()).viewerPosZ;
    GlStateManager.pushMatrix();
    if (entity.isSneaking())
      y -= 0.125D; 
    float interpolateRotation = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
    float interpolateRotation2 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
    float rotationInterp = interpolateRotation2 - interpolateRotation;
    float renderPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
    renderLivingAt(x, y, z);
    float f8 = handleRotationFloat(entity, partialTicks);
    prepareRotations(entity);
    float f9 = prepareScale(entity, scale);
    GlStateManager.enableAlpha();
    modelBase.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
    modelBase.setRotationAngles(limbSwing, limbSwingAmount, f8, entity.rotationYaw, entity.rotationPitch, f9, (Entity)entity);
    modelBase.render((Entity)entity, limbSwing, limbSwingAmount, f8, entity.rotationYaw, entity.rotationPitch, f9);
    GlStateManager.popMatrix();
  }
  
  public static void prepareTranslate(EntityLivingBase entityIn, double x, double y, double z) {
    renderLivingAt(x - (mc.getRenderManager()).viewerPosX, y - (mc.getRenderManager()).viewerPosY, z - (mc.getRenderManager()).viewerPosZ);
  }
  
  public static void renderLivingAt(double x, double y, double z) {
    GlStateManager.translate((float)x, (float)y, (float)z);
  }
  
  public static float prepareScale(EntityLivingBase entity, float scale) {
    GlStateManager.enableRescaleNormal();
    GlStateManager.scale(-1.0F, -1.0F, 1.0F);
    double widthX = (entity.getRenderBoundingBox()).maxX - (entity.getRenderBoundingBox()).minX;
    double widthZ = (entity.getRenderBoundingBox()).maxZ - (entity.getRenderBoundingBox()).minZ;
    GlStateManager.scale(scale + widthX, (scale * entity.height), scale + widthZ);
    float f = 0.0625F;
    GlStateManager.translate(0.0F, -1.501F, 0.0F);
    return 0.0625F;
  }
  
  public static void prepareRotations(EntityLivingBase entityLivingBase) {
    GlStateManager.rotate(180.0F - entityLivingBase.rotationYaw, 0.0F, 1.0F, 0.0F);
  }
  
  public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
    float f;
    for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F);
    while (f >= 180.0F)
      f -= 360.0F; 
    return prevYawOffset + partialTicks * f;
  }
  
  public static Color newAlpha(Color color, int alpha) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
  }
  
  public static void glColor(Color color) {
    GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
  }
  
  public static float handleRotationFloat(EntityLivingBase livingBase, float partialTicks) {
    return 0.0F;
  }
  
  public static void prepareGL() {
    GL11.glBlendFunc(770, 771);
    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.glLineWidth(1.5F);
    GlStateManager.disableTexture2D();
    GlStateManager.depthMask(false);
    GlStateManager.enableBlend();
    GlStateManager.disableDepth();
    GlStateManager.disableLighting();
    GlStateManager.disableCull();
    GlStateManager.enableAlpha();
    GlStateManager.color(1.0F, 1.0F, 1.0F);
  }
  
  public static void releaseGL() {
    GlStateManager.enableCull();
    GlStateManager.depthMask(true);
    GlStateManager.enableTexture2D();
    GlStateManager.enableBlend();
    GlStateManager.enableDepth();
  }
  
  @SubscribeEvent
  public void onUpdate(PacketEvent.Receive event) {
    SPacketEntityStatus packet;
    if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).getOpCode() == 35 && packet.getEntity((World)mc.world) != null && (((Boolean)self.getValue()).booleanValue() || packet.getEntity((World)mc.world).getEntityId() != mc.player.getEntityId())) {
      GameProfile profile = new GameProfile(mc.player.getUniqueID(), "");
      this.player = new EntityOtherPlayerMP((World)mc.world, profile);
      this.player.copyLocationAndAnglesFrom(packet.getEntity((World)mc.world));
      this.playerModel = new ModelPlayer(0.0F, false);
      this.startTime = Long.valueOf(System.currentTimeMillis());
      this.playerModel.bipedHead.showModel = false;
      this.playerModel.bipedBody.showModel = false;
      this.playerModel.bipedLeftArmwear.showModel = false;
      this.playerModel.bipedLeftLegwear.showModel = false;
      this.playerModel.bipedRightArmwear.showModel = false;
      this.playerModel.bipedRightLegwear.showModel = false;
      this.alphaFill = ((Integer)aF.getValue()).intValue();
      this.alphaLine = ((Integer)aL.getValue()).intValue();
      if (!((Boolean)onlyOneEsp.getValue()).booleanValue())
        TotemPopChams totemPopChams = new TotemPopChams(this.player, this.playerModel, this.startTime, this.alphaFill, this.alphaLine); 
    } 
  }
  
  @SubscribeEvent
  public void onRenderWorld(RenderWorldLastEvent event) {
    if (((Boolean)onlyOneEsp.getValue()).booleanValue()) {
      if (this.player == null || mc.world == null || mc.player == null)
        return; 
      if (((Boolean)elevator.getValue()).booleanValue())
        if (elevatorMode.getValue() == ElevatorMode.UP) {
          this.player.posY += (0.05F * event.getPartialTicks());
        } else if (elevatorMode.getValue() == ElevatorMode.DOWN) {
          this.player.posY -= (0.05F * event.getPartialTicks());
        }  
      GL11.glLineWidth(1.0F);
      Color lineColorS = ((Boolean)outlinerainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()) : new Color(((Integer)rL.getValue()).intValue(), ((Integer)bL.getValue()).intValue(), ((Integer)gL.getValue()).intValue(), ((Integer)aL.getValue()).intValue());
      Color fillColorS = ((Boolean)fillrainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()) : new Color(((Integer)rF.getValue()).intValue(), ((Integer)bF.getValue()).intValue(), ((Integer)gF.getValue()).intValue(), ((Integer)aF.getValue()).intValue());
      int lineA = lineColorS.getAlpha();
      int fillA = fillColorS.getAlpha();
      long time = System.currentTimeMillis() - this.startTime.longValue() - ((Number)fadestart.getValue()).longValue();
      if (System.currentTimeMillis() - this.startTime.longValue() > ((Number)fadestart.getValue()).longValue()) {
        double normal = normalize(time, 0.0D, ((Number)fadetime.getValue()).doubleValue());
        normal = MathHelper.clamp(normal, 0.0D, 1.0D);
        normal = -normal + 1.0D;
        lineA *= (int)normal;
        fillA *= (int)normal;
      } 
      Color lineColor = newAlpha(lineColorS, lineA);
      Color fillColor = newAlpha(fillColorS, fillA);
      if (this.player != null && this.playerModel != null) {
        prepareGL();
        GL11.glPushAttrib(1048575);
        GL11.glEnable(2881);
        GL11.glEnable(2848);
        if (this.alphaFill > 1.0D)
          this.alphaFill -= ((Float)fadetime.getValue()).floatValue(); 
        Color fillFinal = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), (int)this.alphaFill);
        if (this.alphaLine > 1.0D)
          this.alphaLine -= ((Float)fadetime.getValue()).floatValue(); 
        Color outlineFinal = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int)this.alphaLine);
        glColor(fillFinal);
        GL11.glPolygonMode(1032, 6914);
        renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, this.player.ticksExisted, this.player.rotationYawHead, this.player.rotationPitch, 1);
        glColor(outlineFinal);
        GL11.glPolygonMode(1032, 6913);
        renderEntity((EntityLivingBase)this.player, (ModelBase)this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, this.player.ticksExisted, this.player.rotationYawHead, this.player.rotationPitch, 1);
        GL11.glPolygonMode(1032, 6914);
        GL11.glPopAttrib();
        releaseGL();
      } 
    } 
  }
  
  double normalize(double value, double min, double max) {
    return (value - min) / (max - min);
  }
  
  public enum ElevatorMode {
    UP, DOWN;
  }
}
