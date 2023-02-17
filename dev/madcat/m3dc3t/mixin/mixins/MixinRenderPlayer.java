package dev.madcat.m3dc3t.mixin.mixins;

import dev.madcat.m3dc3t.M3dC3t;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderPlayer.class})
public class MixinRenderPlayer {
  @Inject(method = {"renderEntityName"}, at = {@At("HEAD")}, cancellable = true)
  public void renderEntityNameHook(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
    if (M3dC3t.moduleManager.isModuleEnabled("NameTags"))
      info.cancel(); 
  }
}
