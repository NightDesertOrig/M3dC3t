//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AntiBurrow extends Module {
  private final Setting<Double> range = register(new Setting("Range", Double.valueOf(6.0D), Double.valueOf(1.0D), Double.valueOf(8.0D)));
  
  public static BlockPos pos;
  
  int ticked = 0;
  
  EntityPlayer player;
  
  public AntiBurrow() {
    super("AntiBurrow", "Automatically dig the enemy's burrow.", Module.Category.COMBAT, true, false, false);
  }
  
  private EntityPlayer getTarget(double range) {
    EntityPlayer target = null;
    double distance = Math.pow(range, 2.0D) + 1.0D;
    for (EntityPlayer player : mc.world.playerEntities) {
      if (EntityUtil.isntValid((Entity)player, range) || M3dC3t.speedManager.getPlayerSpeed(player) > 10.0D)
        continue; 
      if (target == null) {
        target = player;
        distance = mc.player.getDistanceSq((Entity)player);
        continue;
      } 
      if (mc.player.getDistanceSq((Entity)player) >= distance)
        continue; 
      target = player;
      distance = mc.player.getDistanceSq((Entity)player);
    } 
    return target;
  }
  
  public void onTick() {
    if (fullNullCheck())
      return; 
    if (mc.currentScreen instanceof net.minecraft.client.gui.GuiHopper)
      return; 
    this.player = getTarget(((Double)this.range.getValue()).doubleValue());
    if (this.player == null)
      return; 
    pos = new BlockPos(this.player.posX, this.player.posY + 0.5D, this.player.posZ);
    if (this.ticked >= 0)
      this.ticked++; 
    if (InstantMine.breakPos != null && InstantMine.breakPos.equals(pos) && this.ticked >= 60 && mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(pos).getBlock() != Blocks.AIR && mc.world.getBlockState(pos).getBlock() != Blocks.WEB && mc.world.getBlockState(pos).getBlock() != Blocks.REDSTONE_WIRE && !isOnLiquid() && !isInLiquid() && mc.world.getBlockState(pos).getBlock() != Blocks.WATER && mc.world.getBlockState(pos).getBlock() != Blocks.LAVA) {
      mc.player.swingArm(EnumHand.MAIN_HAND);
      mc.playerController.onPlayerDamageBlock(pos, BlockUtil.getRayTraceFacing(pos));
      this.ticked = 1;
    } 
    if (InstantMine.breakPos2 != null && 
      InstantMine.breakPos2.equals(pos))
      return; 
    if (InstantMine.breakPos != null) {
      if (InstantMine.breakPos.equals(pos))
        return; 
      if (InstantMine.breakPos.equals(new BlockPos(mc.player.posX, mc.player.posY + 2.0D, mc.player.posZ)))
        return; 
      if (InstantMine.breakPos.equals(new BlockPos(mc.player.posX, mc.player.posY - 1.0D, mc.player.posZ)))
        return; 
      if (mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.WEB)
        return; 
    } 
    if (mc.world.getBlockState(pos).getBlock() != Blocks.AIR && mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(pos).getBlock() != Blocks.WEB && mc.world.getBlockState(pos).getBlock() != Blocks.REDSTONE_WIRE && !isOnLiquid() && !isInLiquid() && mc.world.getBlockState(pos).getBlock() != Blocks.WATER && mc.world.getBlockState(pos).getBlock() != Blocks.LAVA) {
      mc.player.swingArm(EnumHand.MAIN_HAND);
      mc.playerController.onPlayerDamageBlock(pos, BlockUtil.getRayTraceFacing(pos));
      this.ticked = 1;
    } 
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (this.player != null)
      return this.player.getName(); 
    return null;
  }
  
  private boolean isOnLiquid() {
    double y = mc.player.posY - 0.03D;
    for (int x = MathHelper.floor(mc.player.posX); x < MathHelper.ceil(mc.player.posX); x++) {
      for (int z = MathHelper.floor(mc.player.posZ); z < MathHelper.ceil(mc.player.posZ); ) {
        BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
        if (!(mc.world.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockLiquid)) {
          z++;
          continue;
        } 
        return true;
      } 
    } 
    return false;
  }
  
  private boolean isInLiquid() {
    double y = mc.player.posY + 0.01D;
    for (int x = MathHelper.floor(mc.player.posX); x < MathHelper.ceil(mc.player.posX); x++) {
      for (int z = MathHelper.floor(mc.player.posZ); z < MathHelper.ceil(mc.player.posZ); ) {
        BlockPos pos = new BlockPos(x, (int)y, z);
        if (!(mc.world.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockLiquid)) {
          z++;
          continue;
        } 
        return true;
      } 
    } 
    return false;
  }
}
