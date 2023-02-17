//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.mixin.mixins;

import dev.madcat.m3dc3t.event.events.NoRenderEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {LayerBipedArmor.class}, priority = 1898)
public class MixinLayerBipedArmor {
  @Inject(method = {"setModelSlotVisible"}, at = {@At("HEAD")}, cancellable = true)
  protected void setModelSlotVisible(ModelBiped model, EntityEquipmentSlot slotIn, CallbackInfo ci) {
    NoRenderEvent event = new NoRenderEvent(0);
    MinecraftForge.EVENT_BUS.post((Event)event);
    if (!event.isCanceled())
      return; 
    ci.cancel();
    model.bipedHead.showModel = false;
    model.bipedHeadwear.showModel = false;
    model.bipedBody.showModel = false;
    model.bipedRightArm.showModel = false;
    model.bipedLeftArm.showModel = false;
    model.bipedRightLeg.showModel = false;
    model.bipedLeftLeg.showModel = false;
  }
}
