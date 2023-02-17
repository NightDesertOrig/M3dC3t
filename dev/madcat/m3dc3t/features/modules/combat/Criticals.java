//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.Timer;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Criticals extends Module {
  private final Setting<Integer> packets = register(new Setting("Packets", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(4), "Amount of packets you want to send."));
  
  private final Timer timer = new Timer();
  
  private final boolean resetTimer = false;
  
  private static Criticals INSTANCE = new Criticals();
  
  public Criticals() {
    super("Criticals", "Scores criticals for you", Module.Category.COMBAT, true, false, false);
  }
  
  @SubscribeEvent
  public void onPacketSend(PacketEvent.Send event) {
    CPacketUseEntity packet;
    if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
      if (!this.timer.passedMs(0L))
        return; 
      if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() && packet.getEntityFromWorld((World)mc.world) instanceof net.minecraft.entity.EntityLivingBase && !mc.player.isInWater() && !mc.player.isInLava()) {
        switch (((Integer)this.packets.getValue()).intValue()) {
          case 1:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            break;
          case 2:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625101D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.1E-5D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            break;
          case 3:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625101D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0125D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            break;
          case 4:
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1625D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 4.0E-6D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-6D, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer());
            mc.player.onCriticalHit(Objects.<Entity>requireNonNull(packet.getEntityFromWorld((World)mc.world)));
            break;
        } 
        this.timer.reset();
      } 
    } 
  }
  
  public static Criticals getInstance() {
    if (INSTANCE == null)
      INSTANCE = new Criticals(); 
    return INSTANCE;
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    return "Packet";
  }
}
