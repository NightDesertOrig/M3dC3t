//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.mixin.mixins;

import dev.madcat.m3dc3t.features.modules.client.NickHider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({FontRenderer.class})
public abstract class MixinFontRenderer {
  @Shadow
  protected abstract int renderString(String paramString, float paramFloat1, float paramFloat2, int paramInt, boolean paramBoolean);
  
  @Shadow
  protected abstract void renderStringAtPos(String paramString, boolean paramBoolean);
  
  @Redirect(method = {"renderString(Ljava/lang/String;FFIZ)I"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V"))
  public void renderStringAtPosHook(FontRenderer renderer, String text, boolean shadow) {
    if (NickHider.getInstance().isOn()) {
      renderStringAtPos(text.replace(Minecraft.getMinecraft().getSession().getUsername(), (NickHider.getInstance()).NameString.getValueAsString()), shadow);
    } else {
      renderStringAtPos(text, shadow);
    } 
  }
}
