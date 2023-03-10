package dev.madcat.m3dc3t.mixin.mixins;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
public class MixinNetworkManager {
  @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
  private void onSendPacketPre(Packet<?> packet, CallbackInfo info) {
    PacketEvent.Send event = new PacketEvent.Send(packet);
    MinecraftForge.EVENT_BUS.post((Event)event);
    if (event.isCanceled())
      info.cancel(); 
  }
  
  @Inject(method = {"channelRead0"}, at = {@At("HEAD")}, cancellable = true)
  private void onChannelReadPre(ChannelHandlerContext context, Packet<?> packet, CallbackInfo info) {
    PacketEvent.Receive event = new PacketEvent.Receive(packet);
    MinecraftForge.EVENT_BUS.post((Event)event);
    if (event.isCanceled())
      info.cancel(); 
  }
}
