//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.MathUtil;
import dev.madcat.m3dc3t.util.Timer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Blink extends Module {
  private static Blink INSTANCE = new Blink();
  
  private final Setting<Boolean> cPacketPlayer = register(new Setting("CPacketPlayer", Boolean.valueOf(true)));
  
  private final Setting<Mode> autoOff = register(new Setting("AutoOff", Mode.MANUAL));
  
  private final Setting<Integer> timeLimit = register(new Setting("Time", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(500), v -> (this.autoOff.getValue() == Mode.TIME)));
  
  private final Setting<Integer> packetLimit = register(new Setting("Packets", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(500), v -> (this.autoOff.getValue() == Mode.PACKETS)));
  
  private final Setting<Float> distance = register(new Setting("Distance", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(100.0F), v -> (this.autoOff.getValue() == Mode.DISTANCE)));
  
  private final Timer timer = new Timer();
  
  private final Queue<Packet<?>> packets = new ConcurrentLinkedQueue<>();
  
  private EntityOtherPlayerMP entity;
  
  private int packetsCanceled = 0;
  
  private BlockPos startPos = null;
  
  public Blink() {
    super("Blink", "Fake lag.", Module.Category.PLAYER, true, false, false);
    setInstance();
  }
  
  public static Blink getInstance() {
    if (INSTANCE == null)
      INSTANCE = new Blink(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public void onEnable() {
    if (!fullNullCheck()) {
      this.entity = new EntityOtherPlayerMP((World)mc.world, mc.session.getProfile());
      this.entity.copyLocationAndAnglesFrom((Entity)mc.player);
      this.entity.rotationYaw = mc.player.rotationYaw;
      this.entity.rotationYawHead = mc.player.rotationYawHead;
      this.entity.inventory.copyInventory(mc.player.inventory);
      mc.world.addEntityToWorld(6942069, (Entity)this.entity);
      this.startPos = mc.player.getPosition();
    } else {
      disable();
    } 
    this.packetsCanceled = 0;
    this.timer.reset();
  }
  
  public void onUpdate() {
    if (nullCheck() || (this.autoOff.getValue() == Mode.TIME && this.timer.passedS(((Integer)this.timeLimit.getValue()).intValue())) || (this.autoOff.getValue() == Mode.DISTANCE && this.startPos != null && mc.player.getDistanceSq(this.startPos) >= MathUtil.square(((Float)this.distance.getValue()).floatValue())) || (this.autoOff.getValue() == Mode.PACKETS && this.packetsCanceled >= ((Integer)this.packetLimit.getValue()).intValue()))
      disable(); 
  }
  
  public void onLogout() {
    if (isOn())
      disable(); 
  }
  
  @SubscribeEvent
  public void onSendPacket(PacketEvent.Send event) {
    if (mc.world != null && !mc.isSingleplayer()) {
      Object packet = event.getPacket();
      if (((Boolean)this.cPacketPlayer.getValue()).booleanValue() && packet instanceof net.minecraft.network.play.client.CPacketPlayer) {
        event.setCanceled(true);
        this.packets.add((Packet)packet);
        this.packetsCanceled++;
      } 
      if (!((Boolean)this.cPacketPlayer.getValue()).booleanValue()) {
        if (packet instanceof net.minecraft.network.play.client.CPacketChatMessage || packet instanceof net.minecraft.network.play.client.CPacketConfirmTeleport || packet instanceof net.minecraft.network.play.client.CPacketKeepAlive || packet instanceof net.minecraft.network.play.client.CPacketTabComplete || packet instanceof net.minecraft.network.play.client.CPacketClientStatus)
          return; 
        this.packets.add((Packet)packet);
        event.setCanceled(true);
        this.packetsCanceled++;
      } 
    } 
  }
  
  public void onDisable() {
    if (!fullNullCheck()) {
      mc.world.removeEntity((Entity)this.entity);
      while (!this.packets.isEmpty())
        mc.player.connection.sendPacket(this.packets.poll()); 
    } 
    this.startPos = null;
  }
  
  public String getDisplayInfo() {
    if (this.packets != null)
      return this.packets.size() + ""; 
    return null;
  }
  
  public enum Mode {
    MANUAL, TIME, DISTANCE, PACKETS;
  }
}
