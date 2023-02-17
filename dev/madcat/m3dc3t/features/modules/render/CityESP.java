//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.Render3DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.combat.AntiCity;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import java.awt.Color;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CityESP extends Module {
  public EntityPlayer target;
  
  private final Setting<Float> range = register(new Setting("Range", Float.valueOf(7.0F), Float.valueOf(1.0F), Float.valueOf(12.0F)));
  
  public CityESP() {
    super("CityESP", "Show enemy's hole flaws.", Module.Category.RENDER, true, false, false);
  }
  
  public void onRender3D(Render3DEvent event) {
    if (fullNullCheck())
      return; 
    this.target = getTarget(((Float)this.range.getValue()).floatValue());
    surroundRender1();
  }
  
  private void surroundRender1() {
    if (this.target == null)
      return; 
    Vec3d a = this.target.getPositionVector();
    BlockPos people = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
    if (getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.AIR && getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(-2, 0, 0)).getBlock() == Blocks.AIR && getBlock(people.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
        surroundRender1(a, -1.0D, 0.0D, 0.0D, true);
      } else if (getBlock(people.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK && getBlock(people.add(-2, 1, 0)).getBlock() != Blocks.BEDROCK) {
        surroundRender1(a, -1.0D, 0.0D, 0.0D, false);
      }  
    if (getBlock(people.add(1, 0, 0)).getBlock() != Blocks.AIR && getBlock(people.add(1, 0, 0)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(2, 0, 0)).getBlock() == Blocks.AIR && getBlock(people.add(2, 1, 0)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 1.0D, 0.0D, 0.0D, true);
      } else if (getBlock(people.add(2, 0, 0)).getBlock() != Blocks.BEDROCK && getBlock(people.add(2, 1, 0)).getBlock() != Blocks.BEDROCK) {
        surroundRender1(a, 1.0D, 0.0D, 0.0D, false);
      }  
    if (getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(-2, 1, 0)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(-2, 0, 0)).getBlock() != Blocks.AIR && getBlock(people.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(people.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
        surroundRender1(a, -2.0D, 0.0D, 0.0D, true);
      } else {
        surroundRender1(a, -2.0D, 0.0D, 0.0D, false);
      }  
    if (getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(-2, 1, 0)).getBlock() != Blocks.AIR && getBlock(people.add(-2, 1, 0)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(people.add(-2, 0, 0)).getBlock() == Blocks.AIR) {
        surroundRender1(a, -2.0D, 1.0D, 0.0D, true);
      } else {
        surroundRender1(a, -2.0D, 1.0D, 0.0D, false);
      }  
    if (getBlock(people.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(2, 1, 0)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(2, 0, 0)).getBlock() != Blocks.AIR && getBlock(people.add(2, 0, 0)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(people.add(2, 1, 0)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 2.0D, 0.0D, 0.0D, true);
      } else {
        surroundRender1(a, 2.0D, 0.0D, 0.0D, false);
      }  
    if (getBlock(people.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(2, 0, 0)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(2, 1, 0)).getBlock() != Blocks.AIR && getBlock(people.add(2, 1, 0)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(people.add(2, 0, 0)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 2.0D, 1.0D, 0.0D, true);
      } else {
        surroundRender1(a, 2.0D, 1.0D, 0.0D, false);
      }  
    if (getBlock(people.add(0, 0, 1)).getBlock() != Blocks.AIR && getBlock(people.add(0, 0, 1)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(0, 0, 2)).getBlock() == Blocks.AIR && getBlock(people.add(0, 1, 2)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 0.0D, 0.0D, 1.0D, true);
      } else if (getBlock(people.add(0, 0, 2)).getBlock() != Blocks.BEDROCK && getBlock(people.add(0, 1, 2)).getBlock() != Blocks.BEDROCK) {
        surroundRender1(a, 0.0D, 0.0D, 1.0D, false);
      }  
    if (getBlock(people.add(0, 0, -1)).getBlock() != Blocks.AIR && getBlock(people.add(0, 0, -1)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(0, 0, -2)).getBlock() == Blocks.AIR && getBlock(people.add(0, 1, -2)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 0.0D, 0.0D, -1.0D, true);
      } else if (getBlock(people.add(0, 0, -2)).getBlock() != Blocks.BEDROCK && getBlock(people.add(0, 1, -2)).getBlock() != Blocks.BEDROCK) {
        surroundRender1(a, 0.0D, 0.0D, -1.0D, false);
      }  
    if (getBlock(people.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(0, 1, 2)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(0, 0, 2)).getBlock() != Blocks.AIR && getBlock(people.add(0, 0, 2)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(people.add(0, 1, 2)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 0.0D, 0.0D, 2.0D, true);
      } else {
        surroundRender1(a, 0.0D, 0.0D, 2.0D, false);
      }  
    if (getBlock(people.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(0, 0, 2)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(0, 1, 2)).getBlock() != Blocks.AIR && getBlock(people.add(0, 1, 2)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(people.add(0, 0, 2)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 0.0D, 1.0D, 2.0D, true);
      } else {
        surroundRender1(a, 0.0D, 1.0D, 2.0D, false);
      }  
    if (getBlock(people.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(0, 1, -2)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(0, 0, -2)).getBlock() != Blocks.AIR && getBlock(people.add(0, 0, -2)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(people.add(0, 1, -2)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 0.0D, 0.0D, -2.0D, true);
      } else {
        surroundRender1(a, 0.0D, 0.0D, -2.0D, false);
      }  
    if (getBlock(people.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(0, 0, -2)).getBlock() != Blocks.BEDROCK && 
      getBlock(people.add(0, 1, -2)).getBlock() != Blocks.AIR && getBlock(people.add(0, 1, -2)).getBlock() != Blocks.BEDROCK)
      if (getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(people.add(0, 0, -2)).getBlock() == Blocks.AIR) {
        surroundRender1(a, 0.0D, 1.0D, -2.0D, true);
      } else {
        surroundRender1(a, 0.0D, 1.0D, -2.0D, false);
      }  
    if (getBlock(people.add(0, 0, 0)).getBlock() != Blocks.AIR && getBlock(people.add(0, 0, 0)).getBlock() != Blocks.BEDROCK)
      RenderUtil.drawBoxESP(new BlockPos(a), new Color(255, 255, 0), false, new Color(255, 255, 0), 1.0F, false, true, 42, true); 
  }
  
  private void surroundRender1(Vec3d pos, double x, double y, double z, boolean red) {
    BlockPos position = (new BlockPos(pos)).add(x, y, z);
    if (mc.world.getBlockState(position).getBlock() == Blocks.AIR)
      return; 
    if (mc.world.getBlockState(position).getBlock() == Blocks.FIRE)
      return; 
    if (red) {
      RenderUtil.drawBoxESP(position, new Color(255, 147, 147), false, new Color(255, 147, 147), 1.0F, false, true, 80, true);
      return;
    } 
    RenderUtil.drawBoxESP(position, new Color(118, 118, 255), false, new Color(118, 118, 255), 1.0F, false, true, 40, true);
  }
  
  private IBlockState getBlock(BlockPos block) {
    return AntiCity.mc.world.getBlockState(block);
  }
  
  private EntityPlayer getTarget(double range) {
    EntityPlayer target = null;
    double distance = range;
    for (EntityPlayer player : mc.world.playerEntities) {
      if (EntityUtil.isntValid((Entity)player, range) || M3dC3t.friendManager.isFriend(player.getName()) || mc.player.posY - player.posY >= 5.0D)
        continue; 
      if (target == null) {
        target = player;
        distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
        continue;
      } 
      if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance)
        continue; 
      target = player;
      distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
    } 
    return target;
  }
}
