//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.mixin.mixins;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.KeyEvent;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import dev.madcat.m3dc3t.features.modules.player.MultiTask;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public abstract class MixinMinecraft {
  @Inject(method = {"shutdownMinecraftApplet"}, at = {@At("HEAD")})
  private void stopClient(CallbackInfo callbackInfo) {
    unload();
  }
  
  @Redirect(method = {"run"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
  public void displayCrashReport(Minecraft minecraft, CrashReport crashReport) {
    unload();
  }
  
  @Inject(method = {"runTickKeyboard"}, at = {@At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE)})
  private void onKeyboard(CallbackInfo callbackInfo) {
    int i = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey(), n = i;
    if (Keyboard.getEventKeyState()) {
      KeyEvent event = new KeyEvent(i);
      MinecraftForge.EVENT_BUS.post((Event)event);
    } 
  }
  
  @Shadow
  public abstract void displayGuiScreen(@Nullable GuiScreen paramGuiScreen);
  
  @Inject(method = {"displayGuiScreen"}, at = {@At("HEAD")})
  private void displayGuiScreen(GuiScreen screen, CallbackInfo ci) {
    ClickGui ClickGui = (ClickGui)M3dC3t.moduleManager.getModuleByDisplayName("ClickGui");
  }
  
  @Inject(method = {"runTick()V"}, at = {@At("RETURN")})
  private void runTick(CallbackInfo callbackInfo) {
    ClickGui ClickGui = (ClickGui)M3dC3t.moduleManager.getModuleByDisplayName("ClickGui");
  }
  
  private void unload() {
    M3dC3t.LOGGER.info("Initiated client shutdown.");
    M3dC3t.onUnload();
    M3dC3t.LOGGER.info("Finished client shutdown.");
  }
  
  @Redirect(method = {"sendClickBlockToController"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
  private boolean isHandActiveWrapper(EntityPlayerSP playerSP) {
    return (!MultiTask.getInstance().isOn() && playerSP.isHandActive());
  }
  
  @Redirect(method = {"rightClickMouse"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0))
  private boolean isHittingBlockHook(PlayerControllerMP playerControllerMP) {
    return (!MultiTask.getInstance().isOn() && playerControllerMP.getIsHittingBlock());
  }
}
