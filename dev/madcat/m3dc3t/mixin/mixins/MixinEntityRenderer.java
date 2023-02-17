//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.mixin.mixins;

import com.google.common.base.Predicate;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.player.PhaseTrace;
import dev.madcat.m3dc3t.features.modules.render.CameraClip;
import dev.madcat.m3dc3t.features.modules.render.NoRender;
import dev.madcat.m3dc3t.manager.ModuleManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public class MixinEntityRenderer {
  public ItemStack itemActivationItem;
  
  @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
  public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate predicate) {
    if (PhaseTrace.getINSTANCE().isOn())
      return new ArrayList<>(); 
    return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
  }
  
  @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
  public void hurtCameraEffect(float ticks, CallbackInfo info) {
    if (ModuleManager.getModuleByName("NoRender").isEnabled())
      info.cancel(); 
  }
  
  @ModifyVariable(method = {"orientCamera"}, ordinal = 3, at = @At(value = "STORE", ordinal = 0), require = 1)
  public double changeCameraDistanceHook(double range) {
    CameraClip CameraClip = (CameraClip)M3dC3t.moduleManager.getModuleByDisplayName("CameraClip");
    if (ModuleManager.getModuleByName("CameraClip").isEnabled())
      return ((Double)CameraClip.distance.getValue()).doubleValue(); 
    return range;
  }
  
  @ModifyVariable(method = {"orientCamera"}, ordinal = 7, at = @At(value = "STORE", ordinal = 0), require = 1)
  public double orientCameraHook(double range) {
    CameraClip CameraClip = (CameraClip)M3dC3t.moduleManager.getModuleByDisplayName("CameraClip");
    if (ModuleManager.getModuleByName("CameraClip").isEnabled())
      return ((Double)CameraClip.distance.getValue()).doubleValue(); 
    return range;
  }
  
  @Inject(method = {"renderItemActivation"}, at = {@At("HEAD")}, cancellable = true)
  public void renderItemActivationHook(CallbackInfo info) {
    if (this.itemActivationItem != null && NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).totemPops.getValue()).booleanValue() && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING)
      info.cancel(); 
  }
  
  @Redirect(method = {"setupCameraTransform"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
  public float prevTimeInPortalHook(EntityPlayerSP entityPlayerSP) {
    if (NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).nausea.getValue()).booleanValue())
      return -3.4028235E38F; 
    return entityPlayerSP.prevTimeInPortal;
  }
}
