//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.mixin.mixins;

import dev.madcat.m3dc3t.event.events.RenderItemEvent;
import dev.madcat.m3dc3t.features.modules.render.NoRender;
import dev.madcat.m3dc3t.features.modules.render.ViewModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
public abstract class MixinItemRenderer {
  private final boolean injection = true;
  
  @Inject(method = {"renderSuffocationOverlay"}, at = {@At("HEAD")}, cancellable = true)
  public void renderSuffocationOverlay(CallbackInfo ci) {
    if (NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).blocks.getValue()).booleanValue())
      ci.cancel(); 
  }
  
  @Inject(method = {"transformSideFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
  public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo cancel) {
    RenderItemEvent event = new RenderItemEvent(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    MinecraftForge.EVENT_BUS.post((Event)event);
    if (ViewModel.getInstance().isEnabled()) {
      boolean bob = (ViewModel.getInstance().isDisabled() || ((Boolean)(ViewModel.getInstance()).doBob.getValue()).booleanValue());
      int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
      GlStateManager.translate(i * 0.56F, -0.52F + (bob ? p_187459_2_ : 0.0F) * -0.6F, -0.72F);
      if (hand == EnumHandSide.RIGHT) {
        GlStateManager.translate(event.getMainX(), event.getMainY(), event.getMainZ());
      } else {
        GlStateManager.translate(event.getOffX(), event.getOffY(), event.getOffZ());
      } 
      cancel.cancel();
    } 
  }
  
  @Inject(method = {"transformEatFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
  private void transformEatFirstPerson(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo cancel) {
    if (ViewModel.getInstance().isEnabled()) {
      if (!((Boolean)(ViewModel.getInstance()).noEatAnimation.getValue()).booleanValue()) {
        float f = (Minecraft.getMinecraft()).player.getItemInUseCount() - p_187454_1_ + 1.0F;
        float f2 = f / stack.getMaxItemUseDuration();
        if (f2 < 0.8F) {
          float f1 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
          GlStateManager.translate(0.0F, f1, 0.0F);
        } 
        float f3 = 1.0F - (float)Math.pow(f2, 27.0D);
        int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
        GlStateManager.translate((f3 * 0.6F * i) * ((Double)(ViewModel.getInstance()).eatX.getValue()).doubleValue(), (f3 * 0.5F) * -((Double)(ViewModel.getInstance()).eatY.getValue()).doubleValue(), 0.0D);
        GlStateManager.rotate(i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
      } 
      cancel.cancel();
    } 
  }
}
