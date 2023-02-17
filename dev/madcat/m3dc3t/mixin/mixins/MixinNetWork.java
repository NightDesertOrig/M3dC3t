//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.mixin.mixins;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {NetworkManager.class}, priority = 312312)
public class MixinNetWork {
  @Inject(method = {"channelRead0"}, at = {@At("HEAD")}, cancellable = true)
  private void packetReceived(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
    if ((Minecraft.getMinecraft()).player != null && (Minecraft.getMinecraft()).world != null) {
      PacketEvent.Receive event = new PacketEvent.Receive(packet);
      MinecraftForge.EVENT_BUS.post((Event)event);
      if (event.isCanceled() && callbackInfo.isCancellable())
        callbackInfo.cancel(); 
    } 
  }
  
  @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
  private void sendPacket(Packet<?> packetIn, CallbackInfo callbackInfo) {
    if ((Minecraft.getMinecraft()).player != null && (Minecraft.getMinecraft()).world != null) {
      PacketEvent.Send event = new PacketEvent.Send(packetIn);
      MinecraftForge.EVENT_BUS.post((Event)event);
      if (event.isCanceled() && callbackInfo.isCancellable())
        callbackInfo.cancel(); 
    } 
  }
}
