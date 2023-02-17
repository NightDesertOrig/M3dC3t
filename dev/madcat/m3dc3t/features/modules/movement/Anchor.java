//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import com.google.common.eventbus.Subscribe;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Anchor extends Module {
  public static boolean Anchoring;
  
  private final Setting<Boolean> disable = register(new Setting("Toggle", Boolean.valueOf(true)));
  
  private final Setting<Boolean> pull = register(new Setting("Pull", Boolean.valueOf(true)));
  
  int holeblocks;
  
  public Anchor() {
    super("Anchor", "Automatically makes u go into holes.", Module.Category.MOVEMENT, false, false, false);
  }
  
  public boolean isBlockHole(BlockPos blockPos) {
    this.holeblocks = 0;
    if (mc.world.getBlockState(blockPos.add(0, 3, 0)).getBlock() == Blocks.AIR)
      this.holeblocks++; 
    if (mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock() == Blocks.AIR)
      this.holeblocks++; 
    if (mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() == Blocks.AIR)
      this.holeblocks++; 
    if (mc.world.getBlockState(blockPos.add(0, 0, 0)).getBlock() == Blocks.AIR)
      this.holeblocks++; 
    if (mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK)
      this.holeblocks++; 
    if (mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK)
      this.holeblocks++; 
    if (mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK)
      this.holeblocks++; 
    if (mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK)
      this.holeblocks++; 
    if (mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK)
      this.holeblocks++; 
    return (this.holeblocks >= 9);
  }
  
  public Vec3d GetCenter(double d, double d2, double d3) {
    double d4 = Math.floor(d) + 0.5D;
    double d5 = Math.floor(d2);
    double d6 = Math.floor(d3) + 0.5D;
    return new Vec3d(d4, d5, d6);
  }
  
  @Subscribe
  public void onUpdate() {
    if (mc.world == null)
      return; 
    if (isBlockHole(getPlayerPos().down(1)) || isBlockHole(getPlayerPos().down(2)) || isBlockHole(getPlayerPos().down(3)) || isBlockHole(getPlayerPos().down(4))) {
      Anchoring = true;
      if (!((Boolean)this.pull.getValue()).booleanValue()) {
        mc.player.motionX = 0.0D;
        mc.player.motionZ = 0.0D;
      } else {
        Vec3d center = GetCenter(mc.player.posX, mc.player.posY, mc.player.posZ);
        double d = Math.abs(center.x - mc.player.posX);
        double d2 = Math.abs(center.z - mc.player.posZ);
        if (d > 0.1D || d2 > 0.1D) {
          double d3 = center.x - mc.player.posX;
          double d4 = center.z - mc.player.posZ;
          mc.player.motionX = d3 / 2.0D;
          mc.player.motionZ = d4 / 2.0D;
        } 
      } 
    } else {
      Anchoring = false;
    } 
    if (((Boolean)this.disable.getValue()).booleanValue() && EntityUtil.isSafe((Entity)mc.player))
      disable(); 
  }
  
  public void onDisable() {
    Anchoring = false;
    this.holeblocks = 0;
  }
  
  public BlockPos getPlayerPos() {
    return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
  }
}
