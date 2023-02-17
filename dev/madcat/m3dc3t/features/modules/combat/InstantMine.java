//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.event.PlayerDamageBlockEvent;
import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.event.events.Render3DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Bind;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import dev.madcat.m3dc3t.util.Timer;
import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class InstantMine extends Module {
  private final Timer breakSuccess = new Timer();
  
  private static InstantMine INSTANCE = new InstantMine();
  
  private Setting<Boolean> creativeMode = register(new Setting("CreativeMode", Boolean.valueOf(true)));
  
  private Setting<Boolean> ghostHand = register(new Setting("GhostHand", Boolean.valueOf(true), v -> ((Boolean)this.creativeMode.getValue()).booleanValue()));
  
  private Setting<Boolean> render = register(new Setting("Fill", Boolean.valueOf(true)));
  
  private Setting<Integer> falpha = register(new Setting("FillAlpha", Integer.valueOf(30), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.render.getValue()).booleanValue()));
  
  private Setting<Boolean> render2 = register(new Setting("Box", Boolean.valueOf(true)));
  
  private Setting<Integer> balpha = register(new Setting("BoxAlpha", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.render2.getValue()).booleanValue()));
  
  private final Setting<Boolean> crystal = register(new Setting("Crystal", Boolean.TRUE));
  
  private final Setting<Boolean> crystalp = register(new Setting("Crystal on Break", Boolean.TRUE, v -> ((Boolean)this.crystal.getValue()).booleanValue()));
  
  public final Setting<Boolean> attackcrystal = register(new Setting("Attack Crystal", Boolean.TRUE, v -> ((Boolean)this.crystal.getValue()).booleanValue()));
  
  public final Setting<Bind> bind = register(new Setting("ObsidianBind", new Bind(-1), v -> ((Boolean)this.crystal.getValue()).booleanValue()));
  
  public Setting<Boolean> db = register(new Setting("Silent Double", Boolean.valueOf(true)));
  
  public final Setting<Float> health = register(new Setting("Health", Float.valueOf(18.0F), Float.valueOf(0.0F), Float.valueOf(35.9F), v -> ((Boolean)this.db.getValue()).booleanValue()));
  
  private Setting<Integer> red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
  
  private Setting<Integer> green = register(new Setting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
  
  private Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
  
  private Setting<Integer> alpha = register(new Setting("BoxAlpha", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(255)));
  
  private Setting<Integer> alpha2 = register(new Setting("FillAlpha", Integer.valueOf(70), Integer.valueOf(0), Integer.valueOf(255)));
  
  private final List<Block> godBlocks = Arrays.asList(new Block[] { Blocks.AIR, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.WATER, Blocks.BEDROCK });
  
  private boolean cancelStart = false;
  
  private boolean empty = false;
  
  private EnumFacing facing;
  
  public static BlockPos breakPos;
  
  int slotMain2;
  
  int swithc2;
  
  public static BlockPos breakPos2;
  
  double manxi;
  
  double manxi2;
  
  public final Timer imerS;
  
  public final Timer imerS2;
  
  long times;
  
  public InstantMine() {
    super("InstantMine", "Crazy packet miner.", Module.Category.COMBAT, true, false, false);
    this.manxi = 0.0D;
    this.manxi2 = 0.0D;
    this.imerS = new Timer();
    this.imerS2 = new Timer();
    this.times = 0L;
    setInstance();
  }
  
  public static InstantMine getInstance() {
    if (INSTANCE != null)
      return INSTANCE; 
    INSTANCE = new InstantMine();
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public void onTick() {
    if (mc.player.isCreative())
      return; 
    this.slotMain2 = mc.player.inventory.currentItem;
    if (ticked <= 86 && ticked >= 0)
      ticked++; 
    if (breakPos2 == null)
      this.manxi2 = 0.0D; 
    if (breakPos2 != null && (ticked >= 65 || (ticked >= 20 && mc.world.getBlockState(breakPos).getBlock() == Blocks.ENDER_CHEST)))
      if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.GOLDEN_APPLE || mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.CHORUS_FRUIT) {
        if (!Mouse.isButtonDown(1)) {
          if (mc.player.getHealth() + mc.player.getAbsorptionAmount() >= ((Float)this.health.getValue()).floatValue()) {
            if (InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) != -1 && ((Boolean)this.db.getValue()).booleanValue()) {
              mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getItemHotbars(Items.DIAMOND_PICKAXE)));
              this.swithc2 = 1;
              ticked++;
            } 
          } else if (this.swithc2 == 1) {
            mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.slotMain2));
            this.swithc2 = 0;
          } 
        } else if (this.swithc2 == 1) {
          mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.slotMain2));
          this.swithc2 = 0;
        } 
      } else if (mc.player.getHealth() + mc.player.getAbsorptionAmount() >= ((Float)this.health.getValue()).floatValue()) {
        if (InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) != -1 && ((Boolean)this.db.getValue()).booleanValue()) {
          mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getItemHotbars(Items.DIAMOND_PICKAXE)));
          this.swithc2 = 1;
          ticked++;
        } 
      } else if (this.swithc2 == 1) {
        mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.slotMain2));
        this.swithc2 = 0;
      }  
    if (breakPos2 != null && mc.world.getBlockState(breakPos2).getBlock() == Blocks.AIR) {
      if (this.swithc2 == 1) {
        mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.slotMain2));
        this.swithc2 = 0;
      } 
      breakPos2 = null;
      this.manxi2 = 0.0D;
      ticked = 0;
    } 
    if (ticked == 0) {
      this.manxi2 = 0.0D;
      breakPos2 = null;
    } 
    if (ticked >= 140) {
      if (this.swithc2 == 1) {
        mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.slotMain2));
        this.swithc2 = 0;
      } 
      this.manxi2 = 0.0D;
      breakPos2 = null;
      ticked = 0;
    } 
    if (breakPos != null && mc.world.getBlockState(breakPos).getBlock() == Blocks.AIR && breakPos2 == null)
      ticked = 0; 
    if (fullNullCheck())
      return; 
    if (!((Boolean)this.creativeMode.getValue()).booleanValue())
      return; 
    if (!this.cancelStart)
      return; 
    if (((Boolean)this.crystal.getValue()).booleanValue() && ((Boolean)this.attackcrystal.getValue()).booleanValue() && mc.world.getBlockState(breakPos).getBlock() == Blocks.AIR)
      attackcrystal(); 
    if (((Bind)this.bind.getValue()).isDown() && ((Boolean)this.crystal.getValue()).booleanValue() && InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1 && mc.world.getBlockState(breakPos).getBlock() == Blocks.AIR) {
      int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
      int old = mc.player.inventory.currentItem;
      switchToSlot(obbySlot);
      BlockUtil.placeBlock(breakPos, EnumHand.MAIN_HAND, false, true, false);
      switchToSlot(old);
    } 
    if (InventoryUtil.getItemHotbar(Items.END_CRYSTAL) != -1 && ((Boolean)this.crystal.getValue()).booleanValue() && mc.world.getBlockState(breakPos).getBlock() == Blocks.OBSIDIAN && !breakPos.equals(AntiBurrow.pos))
      if (this.empty) {
        BlockUtil.placeCrystalOnBlock(breakPos, EnumHand.MAIN_HAND, true, false, true);
      } else if (!((Boolean)this.crystalp.getValue()).booleanValue()) {
        BlockUtil.placeCrystalOnBlock(breakPos, EnumHand.MAIN_HAND, true, false, true);
      }  
    this;
    if (this.godBlocks.contains(mc.world.getBlockState(breakPos).getBlock()))
      return; 
    this;
    if (mc.world.getBlockState(breakPos).getBlock() != Blocks.WEB) {
      if (((Boolean)this.ghostHand.getValue()).booleanValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) != -1 && InventoryUtil.getItemHotbars(Items.DIAMOND_PICKAXE) != -1) {
        int slotMain = mc.player.inventory.currentItem;
        this;
        if (mc.world.getBlockState(breakPos).getBlock() == Blocks.OBSIDIAN) {
          if (!this.breakSuccess.passedMs(1234L))
            return; 
          mc.player.inventory.currentItem = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
          mc.playerController.updateController();
          this;
          mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
          mc.player.inventory.currentItem = slotMain;
          mc.playerController.updateController();
          return;
        } 
        mc.player.inventory.currentItem = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
        mc.playerController.updateController();
        this;
        mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
        mc.player.inventory.currentItem = slotMain;
        mc.playerController.updateController();
        return;
      } 
    } else if (((Boolean)this.ghostHand.getValue()).booleanValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD) != -1 && InventoryUtil.getItemHotbars(Items.DIAMOND_SWORD) != -1) {
      int slotMain = mc.player.inventory.currentItem;
      mc.player.inventory.currentItem = InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD);
      mc.playerController.updateController();
      this;
      mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
      mc.player.inventory.currentItem = slotMain;
      mc.playerController.updateController();
      return;
    } 
    this;
    mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
  }
  
  private void switchToSlot(int slot) {
    FeetPad.mc.player.inventory.currentItem = slot;
    FeetPad.mc.playerController.updateController();
  }
  
  public void onRender3D(Render3DEvent event) {
    if (!mc.player.isCreative()) {
      if (breakPos2 != null) {
        AxisAlignedBB axisAlignedBB = mc.world.getBlockState(breakPos2).getSelectedBoundingBox((World)mc.world, breakPos2);
        double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0D;
        double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0D;
        double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0D;
        double progressValX = (getInstance()).manxi2 * (axisAlignedBB.maxX - centerX) / 10.0D;
        double progressValY = (getInstance()).manxi2 * (axisAlignedBB.maxY - centerY) / 10.0D;
        double progressValZ = (getInstance()).manxi2 * (axisAlignedBB.maxZ - centerZ) / 10.0D;
        AxisAlignedBB axisAlignedBB1 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
        if (breakPos != null) {
          if (!breakPos2.equals(breakPos)) {
            RenderUtil.drawBBBox(axisAlignedBB1, new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue()), ((Integer)this.alpha.getValue()).intValue());
            RenderUtil.drawBBFill(axisAlignedBB1, new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha2.getValue()).intValue()), ((Integer)this.alpha2.getValue()).intValue());
          } 
        } else {
          RenderUtil.drawBBBox(axisAlignedBB1, new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue()), ((Integer)this.alpha.getValue()).intValue());
          RenderUtil.drawBBFill(axisAlignedBB1, new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha2.getValue()).intValue()), ((Integer)this.alpha2.getValue()).intValue());
        } 
      } 
      if (((Boolean)this.creativeMode.getValue()).booleanValue() && this.cancelStart) {
        this;
        if (this.godBlocks.contains(mc.world.getBlockState(breakPos).getBlock()))
          this.empty = true; 
        if (this.imerS.passedMs(15L)) {
          if (this.manxi <= 10.0D)
            this.manxi += 0.11D; 
          this.imerS.reset();
        } 
        if (this.imerS2.passedMs(22L)) {
          if (this.manxi2 <= 10.0D && this.manxi2 >= 0.0D)
            this.manxi2 += 0.11D; 
          this.imerS2.reset();
        } 
        AxisAlignedBB axisAlignedBB = mc.world.getBlockState(breakPos).getSelectedBoundingBox((World)mc.world, breakPos);
        double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0D;
        double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0D;
        double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0D;
        double progressValX = this.manxi * (axisAlignedBB.maxX - centerX) / 10.0D;
        double progressValY = this.manxi * (axisAlignedBB.maxY - centerY) / 10.0D;
        double progressValZ = this.manxi * (axisAlignedBB.maxZ - centerZ) / 10.0D;
        AxisAlignedBB axisAlignedBB1 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
        if (((Boolean)this.render.getValue()).booleanValue())
          RenderUtil.drawBBFill(axisAlignedBB1, new Color(this.empty ? 0 : 255, this.empty ? 255 : 0, 0, 255), ((Integer)this.falpha.getValue()).intValue()); 
        if (((Boolean)this.render2.getValue()).booleanValue())
          RenderUtil.drawBBBox(axisAlignedBB1, new Color(this.empty ? 0 : 255, this.empty ? 255 : 0, 0, 255), ((Integer)this.balpha.getValue()).intValue()); 
      } 
    } 
  }
  
  @SubscribeEvent
  public void onPacketSend(PacketEvent.Send event) {
    if (fullNullCheck())
      return; 
    if (mc.player.isCreative())
      return; 
    if (!(event.getPacket() instanceof CPacketPlayerDigging))
      return; 
    CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
    if (packet.getAction() != CPacketPlayerDigging.Action.START_DESTROY_BLOCK)
      return; 
    event.setCanceled(this.cancelStart);
  }
  
  public static void attackcrystal() {
    for (Entity crystal : mc.world.loadedEntityList.stream().filter(e -> (e instanceof net.minecraft.entity.item.EntityEnderCrystal && !e.isDead)).sorted(Comparator.comparing(e -> Float.valueOf(mc.player.getDistance(e)))).collect(Collectors.toList())) {
      if (!(crystal instanceof net.minecraft.entity.item.EntityEnderCrystal) || crystal.getDistanceSq(breakPos) > 2.0D)
        continue; 
      mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
      mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
    } 
  }
  
  static int ticked = 0;
  
  @SubscribeEvent
  public void onBlockEvent(PlayerDamageBlockEvent event) {
    if (fullNullCheck())
      return; 
    if (mc.player.isCreative())
      return; 
    if (!BlockUtil.canBreak(event.pos))
      return; 
    if (breakPos != null && 
      breakPos.getX() == event.pos.getX() && breakPos.getY() == event.pos.getY() && breakPos.getZ() == event.pos.getZ())
      return; 
    if (ticked == 0)
      ticked = 1; 
    if (this.manxi2 == 0.0D)
      this.manxi2 = 0.11D; 
    if (breakPos != null && 
      breakPos2 == null && 
      mc.world.getBlockState(breakPos).getBlock() != Blocks.AIR) {
      this;
      breakPos2 = breakPos;
    } 
    if (breakPos == null && 
      breakPos2 == null)
      breakPos2 = event.pos; 
    this.manxi = 0.0D;
    this.empty = false;
    this.cancelStart = false;
    this;
    breakPos = event.pos;
    this.breakSuccess.reset();
    this.facing = event.facing;
    this;
    if (breakPos == null)
      return; 
    mc.player.swingArm(EnumHand.MAIN_HAND);
    this;
    mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, breakPos, this.facing));
    this.cancelStart = true;
    this;
    mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, breakPos, this.facing));
    event.setCanceled(true);
  }
}
