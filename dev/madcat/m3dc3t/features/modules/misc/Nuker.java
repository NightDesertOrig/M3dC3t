//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.combat.InstantMine;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.MathUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

public class Nuker extends Module {
  private final Setting<Integer> range = register(new Setting("Range", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(6)));
  
  private Setting<Boolean> logout = register(new Setting("BreakAll", Boolean.valueOf(false)));
  
  private Setting<Boolean> hp = register(new Setting("32kMode", Boolean.valueOf(false)));
  
  private final Setting<Integer> saferange = register(new Setting("SafeRange", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(6), v -> !((Boolean)this.logout.getValue()).booleanValue()));
  
  public Nuker() {
    super("Nuker", "Crazy dig.", Module.Category.MISC, true, false, false);
  }
  
  public void onTick() {
    for (BlockPos blockPos : breakPos(((Integer)this.range.getValue()).intValue())) {
      if (mc.player.getDistanceSq(InstantMine.breakPos) > MathUtil.square(6.0D) && ((Boolean)this.hp.getValue()).booleanValue() && InstantMine.breakPos != null && mc.world.getBlockState(InstantMine.breakPos).getBlock() instanceof net.minecraft.block.BlockHopper)
        return; 
      if (mc.player.getDistanceSq(InstantMine.breakPos) > MathUtil.square(6.0D) && InstantMine.breakPos != null && mc.world.getBlockState(InstantMine.breakPos).getBlock() instanceof net.minecraft.block.BlockShulkerBox)
        return; 
      if (mc.player.getDistanceSq(InstantMine.breakPos) > MathUtil.square(6.0D) && ((Boolean)this.hp.getValue()).booleanValue() && InstantMine.breakPos != null && mc.world.getBlockState(InstantMine.breakPos).getBlock() instanceof net.minecraft.block.BlockHopper)
        return; 
      if (mc.player.getDistanceSq(InstantMine.breakPos) > MathUtil.square(6.0D) && ((Boolean)this.logout.getValue()).booleanValue() && mc.world.getBlockState(InstantMine.breakPos).getBlock() != Blocks.AIR)
        return; 
      if (!((Boolean)this.logout.getValue()).booleanValue()) {
        if (mc.player.getDistanceSq(blockPos) < MathUtil.square(((Integer)this.saferange.getValue()).intValue()) || blockPos == null)
          continue; 
        if (((Boolean)this.hp.getValue()).booleanValue()) {
          if (mc.world.getBlockState(blockPos).getBlock() instanceof net.minecraft.block.BlockHopper) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.playerController.onPlayerDamageBlock(blockPos, BlockUtil.getRayTraceFacing(blockPos));
            continue;
          } 
          if (mc.world.getBlockState(blockPos).getBlock() instanceof net.minecraft.block.BlockShulkerBox) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.playerController.onPlayerDamageBlock(blockPos, BlockUtil.getRayTraceFacing(blockPos));
          } 
        } 
        continue;
      } 
      if (mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) {
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.playerController.onPlayerDamageBlock(blockPos, BlockUtil.getRayTraceFacing(blockPos));
      } 
    } 
  }
  
  private NonNullList<BlockPos> breakPos(float placeRange) {
    NonNullList<BlockPos> positions = NonNullList.create();
    positions.addAll(BlockUtil.getSphere(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ)), placeRange, 0, false, true, 0));
    return positions;
  }
}
