//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.mixin.mixins;

import com.mojang.authlib.GameProfile;
import dev.madcat.m3dc3t.event.events.PlayerJumpEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPlayer.class})
public abstract class MixinEntityPlayer extends EntityLivingBase {
  EntityPlayer player;
  
  public MixinEntityPlayer(World worldIn, GameProfile gameProfileIn) {
    super(worldIn);
  }
  
  @Inject(method = {"getCooldownPeriod"}, at = {@At("HEAD")}, cancellable = true)
  private void getCooldownPeriodHook(CallbackInfoReturnable<Float> callbackInfoReturnable) {}
  
  @Inject(method = {"jump"}, at = {@At("HEAD")}, cancellable = true)
  public void onJump(CallbackInfo ci) {
    if ((Minecraft.getMinecraft()).player.getName() == getName())
      MinecraftForge.EVENT_BUS.post((Event)new PlayerJumpEvent(this.motionX, this.motionY)); 
  }
}
