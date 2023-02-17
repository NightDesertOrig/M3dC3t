//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.PacketEvent;
import dev.madcat.m3dc3t.event.events.Render3DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HoleFiller extends Module {
  private static BlockPos PlayerPos;
  
  private static boolean togglePitch;
  
  private static boolean isSpoofingAngles;
  
  private static double yaw;
  
  private static double pitch;
  
  public HoleFiller() {
    super("HoleFiller", "Fills holes around enemy.", Module.Category.COMBAT, true, false, true);
    this.range = register(new Setting("Range", Double.valueOf(4.5D), Double.valueOf(0.1D), Double.valueOf(6.0D)));
    this.smartRange = register(new Setting("HoleRange", Double.valueOf(3.0D), Double.valueOf(0.1D), Double.valueOf(6.0D)));
    this.web = register(new Setting("WEB", Boolean.valueOf(false)));
    this.rainbow = register(new Setting("Rainbow", Boolean.valueOf(false)));
    this.red = register(new Setting("Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> !((Boolean)this.rainbow.getValue()).booleanValue()));
    this.green = register(new Setting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> !((Boolean)this.rainbow.getValue()).booleanValue()));
    this.blue = register(new Setting("Blue", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> !((Boolean)this.rainbow.getValue()).booleanValue()));
    this.alpha = register(new Setting("Alpha", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> !((Boolean)this.rainbow.getValue()).booleanValue()));
    this.outlineAlpha = register(new Setting("OL-Alpha", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> !((Boolean)this.rainbow.getValue()).booleanValue()));
    this.systemTime = -1L;
    this.switchCooldown = false;
    this.isAttacking = false;
    setInstance();
    Packet[] packet = { null };
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (this.closestTarget != null)
      return this.closestTarget.getName(); 
    return null;
  }
  
  public static HoleFiller getInstance() {
    if (INSTANCE == null)
      INSTANCE = new HoleFiller(); 
    return INSTANCE;
  }
  
  public static BlockPos getPlayerPos() {
    return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
  }
  
  private static void setYawAndPitch(float yaw1, float pitch1) {
    yaw = yaw1;
    pitch = pitch1;
    isSpoofingAngles = true;
  }
  
  private static void resetRotation() {
    if (isSpoofingAngles) {
      yaw = mc.player.rotationYaw;
      pitch = mc.player.rotationPitch;
      isSpoofingAngles = false;
    } 
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  @SubscribeEvent
  public void onPacketSend(PacketEvent.Send event) {
    Packet packet = event.getPacket();
    if (packet instanceof CPacketPlayer && isSpoofingAngles) {
      ((CPacketPlayer)packet).yaw = (float)yaw;
      ((CPacketPlayer)packet).pitch = (float)pitch;
    } 
  }
  
  public void onEnable() {
    super.onEnable();
  }
  
  public void onUpdate() {
    if (mc.world == null)
      return; 
    findClosestTarget();
    List<BlockPos> blocks = findCrystalBlocks();
    BlockPos q = null;
    double dist = 0.0D;
    double prevDist = 0.0D;
    int obsidianSlot = (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) ? mc.player.inventory.currentItem : -1, n = obsidianSlot;
    if (obsidianSlot == -1)
      for (int l = 0; l < 9; l++) {
        if (mc.player.inventory.getStackInSlot(l).getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
          obsidianSlot = l;
          break;
        } 
      }  
    if (((Boolean)this.web.getValue()).booleanValue()) {
      obsidianSlot = (mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.WEB)) ? mc.player.inventory.currentItem : -1;
      if (obsidianSlot == -1)
        for (int l = 0; l < 9; l++) {
          if (mc.player.inventory.getStackInSlot(l).getItem() == Item.getItemFromBlock(Blocks.WEB)) {
            obsidianSlot = l;
            break;
          } 
        }  
    } 
    if (obsidianSlot == -1)
      if (((Boolean)this.web.getValue()).booleanValue()) {
        obsidianSlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (obsidianSlot == -1)
          return; 
      } else {
        return;
      }  
    for (BlockPos blockPos : blocks) {
      if (!mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos)).isEmpty())
        continue; 
      if (isInRange(blockPos)) {
        q = blockPos;
        continue;
      } 
      q = blockPos;
    } 
    this.render = q;
    if (q != null && mc.player.onGround) {
      if ((BreakCheck.Instance()).BrokenPos != null && (new BlockPos((Vec3i)(BreakCheck.Instance()).BrokenPos)).equals(new BlockPos((Vec3i)this.render)))
        return; 
      int oldSlot = mc.player.inventory.currentItem;
      switchToSlot(obsidianSlot);
      BlockUtil.placeBlock(this.render, EnumHand.MAIN_HAND, false, true, false);
      switchToSlot(oldSlot);
    } 
  }
  
  public void onRender3D(Render3DEvent event) {
    if (this.render != null)
      RenderUtil.drawBoxESP(this.render, ((Boolean)this.rainbow.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()) : new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.outlineAlpha.getValue()).intValue()), 3.5F, true, true, ((Integer)this.alpha.getValue()).intValue()); 
  }
  
  private void switchToSlot(int slot) {
    SmartTrap.mc.player.inventory.currentItem = slot;
    SmartTrap.mc.playerController.updateController();
  }
  
  private double getDistanceToBlockPos(BlockPos pos1, BlockPos pos2) {
    double x = (pos1.getX() - pos2.getX());
    double y = (pos1.getY() - pos2.getY());
    double z = (pos1.getZ() - pos2.getZ());
    return Math.sqrt(x * x + y * y + z * z);
  }
  
  private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
    double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
    setYawAndPitch((float)v[0], (float)v[1]);
  }
  
  public boolean IsHole(BlockPos blockPos) {
    BlockPos boost = blockPos.add(0, 1, 0);
    BlockPos boost2 = blockPos.add(0, 0, 0);
    BlockPos boost3 = blockPos.add(0, 0, -1);
    BlockPos boost4 = blockPos.add(1, 0, 0);
    BlockPos boost5 = blockPos.add(-1, 0, 0);
    BlockPos boost6 = blockPos.add(0, 0, 1);
    BlockPos boost7 = blockPos.add(0, 2, 0);
    BlockPos boost8 = blockPos.add(0.5D, 0.5D, 0.5D);
    BlockPos boost9 = blockPos.add(0, -1, 0);
    return (mc.world.getBlockState(boost).getBlock() == Blocks.AIR && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && mc.world.getBlockState(boost7).getBlock() == Blocks.AIR && (mc.world.getBlockState(boost3).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK) && (mc.world.getBlockState(boost4).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK) && (mc.world.getBlockState(boost5).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK) && (mc.world.getBlockState(boost6).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK) && mc.world.getBlockState(boost8).getBlock() == Blocks.AIR && (mc.world.getBlockState(boost9).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK));
  }
  
  public BlockPos getClosestTargetPos() {
    if (this.closestTarget != null)
      return new BlockPos(Math.floor(this.closestTarget.posX), Math.floor(this.closestTarget.posY), Math.floor(this.closestTarget.posZ)); 
    return null;
  }
  
  private void findClosestTarget() {
    List<EntityPlayer> playerList = mc.world.playerEntities;
    this.closestTarget = null;
    for (EntityPlayer target : playerList) {
      if (target == mc.player || M3dC3t.friendManager.isFriend(target.getName()) || !EntityUtil.isLiving((Entity)target) || 
        target.getHealth() <= 0.0F)
        continue; 
      if (this.closestTarget == null) {
        this.closestTarget = target;
        continue;
      } 
      if (mc.player.getDistance((Entity)target) >= mc.player.getDistance((Entity)this.closestTarget))
        continue; 
      this.closestTarget = target;
    } 
  }
  
  private boolean isInRange(BlockPos blockPos) {
    NonNullList<BlockPos> positions = NonNullList.create();
    positions.addAll((Collection)getSphere(getPlayerPos(), ((Double)this.range.getValue()).floatValue(), ((Double)this.range.getValue()).intValue(), false, true, 0).stream().filter(this::IsHole).collect(Collectors.toList()));
    return positions.contains(blockPos);
  }
  
  private List<BlockPos> findCrystalBlocks() {
    NonNullList<BlockPos> positions = NonNullList.create();
    if (this.closestTarget != null)
      positions.addAll((Collection)getSphere(getClosestTargetPos(), ((Double)this.smartRange.getValue()).floatValue(), ((Double)this.range.getValue()).intValue(), false, true, 0).stream().filter(this::IsHole).filter(this::isInRange).collect(Collectors.toList())); 
    return (List<BlockPos>)positions;
  }
  
  public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
    ArrayList<BlockPos> circleblocks = new ArrayList<>();
    int cx = loc.getX();
    int cy = loc.getY();
    int cz = loc.getZ();
    for (int x = cx - (int)r; x <= cx + r; x++) {
      for (int z = cz - (int)r; z <= cz + r; ) {
        int y = sphere ? (cy - (int)r) : cy;
        while (true) {
          float f = y;
          float f2 = sphere ? (cy + r) : (cy + h);
          if (f >= f2) {
            z++;
            continue;
          } 
          double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
          if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
            BlockPos l = new BlockPos(x, y + plus_y, z);
            circleblocks.add(l);
          } 
          y++;
        } 
      } 
    } 
    return circleblocks;
  }
  
  public void onDisable() {
    this.closestTarget = null;
    this.render = null;
    resetRotation();
    super.onDisable();
  }
  
  private static HoleFiller INSTANCE = new HoleFiller();
  
  private final Setting<Double> range;
  
  private final Setting<Double> smartRange;
  
  private final Setting<Boolean> web;
  
  private final Setting<Boolean> rainbow;
  
  private final Setting<Integer> red;
  
  private final Setting<Integer> green;
  
  private final Setting<Integer> blue;
  
  private final Setting<Integer> alpha;
  
  private final Setting<Integer> outlineAlpha;
  
  private final long systemTime;
  
  private final boolean switchCooldown;
  
  private final boolean isAttacking;
  
  double d;
  
  private BlockPos render;
  
  private Entity renderEnt;
  
  private EntityPlayer closestTarget;
  
  private boolean caOn;
  
  private int newSlot;
  
  static {
    togglePitch = false;
  }
}
