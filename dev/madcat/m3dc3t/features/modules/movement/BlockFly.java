//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.event.events.UpdateWalkingPlayerEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import dev.madcat.m3dc3t.util.MathUtil;
import dev.madcat.m3dc3t.util.Timer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockFly extends Module {
  private final Timer timer = new Timer();
  
  public Setting<Boolean> rotation = register(new Setting("Rotate", Boolean.FALSE));
  
  private BlockPos pos;
  
  public BlockFly() {
    super("BlockFly", "Places Blocks underneath you.", Module.Category.MOVEMENT, true, false, false);
  }
  
  public void onEnable() {
    this.timer.reset();
  }
  
  @SubscribeEvent
  public void onUpdateWalkingPlayerPost(UpdateWalkingPlayerEvent event) {
    if (isOff() || fullNullCheck() || event.getStage() == 0)
      return; 
    if (!mc.gameSettings.keyBindJump.isKeyDown())
      this.timer.reset(); 
    BlockPos playerBlock;
    if (BlockUtil.isScaffoldPos((playerBlock = EntityUtil.getPlayerPosWithEntity()).add(0, -1, 0)))
      if (BlockUtil.isValidBlock(playerBlock.add(0, -2, 0))) {
        place(playerBlock.add(0, -1, 0), EnumFacing.UP);
      } else if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 0))) {
        place(playerBlock.add(0, -1, 0), EnumFacing.EAST);
      } else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 0))) {
        place(playerBlock.add(0, -1, 0), EnumFacing.WEST);
      } else if (BlockUtil.isValidBlock(playerBlock.add(0, -1, -1))) {
        place(playerBlock.add(0, -1, 0), EnumFacing.SOUTH);
      } else if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
        place(playerBlock.add(0, -1, 0), EnumFacing.NORTH);
      } else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
        if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1)))
          place(playerBlock.add(0, -1, 1), EnumFacing.NORTH); 
        place(playerBlock.add(1, -1, 1), EnumFacing.EAST);
      } else if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 1))) {
        if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 0)))
          place(playerBlock.add(0, -1, 1), EnumFacing.WEST); 
        place(playerBlock.add(-1, -1, 1), EnumFacing.SOUTH);
      } else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
        if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1)))
          place(playerBlock.add(0, -1, 1), EnumFacing.SOUTH); 
        place(playerBlock.add(1, -1, 1), EnumFacing.WEST);
      } else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
        if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1)))
          place(playerBlock.add(0, -1, 1), EnumFacing.EAST); 
        place(playerBlock.add(1, -1, 1), EnumFacing.NORTH);
      }  
  }
  
  public void place(BlockPos posI, EnumFacing face) {
    BlockPos pos = posI;
    if (face == EnumFacing.UP) {
      pos = pos.add(0, -1, 0);
    } else if (face == EnumFacing.NORTH) {
      pos = pos.add(0, 0, 1);
    } else if (face == EnumFacing.SOUTH) {
      pos = pos.add(0, 0, -1);
    } else if (face == EnumFacing.EAST) {
      pos = pos.add(-1, 0, 0);
    } else if (face == EnumFacing.WEST) {
      pos = pos.add(1, 0, 0);
    } 
    int oldSlot = mc.player.inventory.currentItem;
    int newSlot = -1;
    for (int i = 0; i < 9; ) {
      ItemStack stack = mc.player.inventory.getStackInSlot(i);
      if (InventoryUtil.isNull(stack) || !(stack.getItem() instanceof net.minecraft.item.ItemBlock) || !Block.getBlockFromItem(stack.getItem()).getDefaultState().isFullBlock()) {
        i++;
        continue;
      } 
      newSlot = i;
    } 
    if (newSlot == -1)
      return; 
    boolean crouched = false;
    Block block;
    if (!mc.player.isSneaking() && BlockUtil.blackList.contains(block = mc.world.getBlockState(pos).getBlock())) {
      mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
      crouched = true;
    } 
    if (!(mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBlock)) {
      mc.player.inventory.currentItem = newSlot;
      mc.playerController.updateController();
    } 
    if (mc.gameSettings.keyBindJump.isKeyDown()) {
      mc.player.motionX *= 0.3D;
      mc.player.motionZ *= 0.3D;
      mc.player.jump();
      if (this.timer.passedMs(1500L)) {
        mc.player.motionY = -0.28D;
        this.timer.reset();
      } 
    } 
    if (((Boolean)this.rotation.getValue()).booleanValue()) {
      float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((pos.getX() + 0.5F), (pos.getY() - 0.5F), (pos.getZ() + 0.5F)));
      mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], MathHelper.normalizeAngle((int)angle[1], 360), mc.player.onGround));
    } 
    mc.playerController.processRightClickBlock(mc.player, mc.world, pos, face, new Vec3d(0.5D, 0.5D, 0.5D), EnumHand.MAIN_HAND);
    mc.player.swingArm(EnumHand.MAIN_HAND);
    mc.player.inventory.currentItem = oldSlot;
    mc.playerController.updateController();
    if (crouched)
      mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING)); 
  }
}
