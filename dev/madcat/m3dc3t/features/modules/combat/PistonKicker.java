//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import dev.madcat.m3dc3t.util.MathUtil;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PistonKicker extends Module {
  public static EntityPlayer target;
  
  private final Setting<Float> range;
  
  private final Setting<Boolean> rotate;
  
  private final Setting<Boolean> disable;
  
  private final Setting<Boolean> debug;
  
  public PistonKicker() {
    super("PistonKicker", "Automatically kick out the enemy's hole.", Module.Category.COMBAT, true, false, false);
    this.range = register(new Setting("Range", Float.valueOf(8.0F), Float.valueOf(1.0F), Float.valueOf(12.0F)));
    this.rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
    this.disable = register(new Setting("Disable", Boolean.valueOf(true)));
    this.debug = register(new Setting("Debug", Boolean.valueOf(true)));
  }
  
  public void onUpdate() {
    if (InventoryUtil.findHotbarBlock(BlockPistonBase.class) == -1) {
      if (((Boolean)this.disable.getValue()).booleanValue())
        toggle(); 
      return;
    } 
    if (InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK) == -1) {
      if (((Boolean)this.disable.getValue()).booleanValue())
        toggle(); 
      return;
    } 
    target = getTarget(((Float)this.range.getValue()).floatValue());
    if (target == null) {
      if (((Boolean)this.disable.getValue()).booleanValue())
        toggle(); 
      return;
    } 
    BlockPos pos = new BlockPos(target.posX, target.posY, target.posZ);
    float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F)));
    if (angle[1] >= -71.0F && angle[1] <= 71.0F && angle[0] >= -51.0F && angle[0] <= 51.0F && getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 2, -1)).getBlock() == Blocks.AIR)
      if ((((getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR) ? 1 : 0) | ((getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.PISTON) ? 1 : 0)) != 0) {
        perform(pos.add(0, 1, 1));
        if (getBlock(pos.add(0, 2, 1)).getBlock() == Blocks.AIR) {
          perform1(pos.add(0, 2, 1));
        } else if (getBlock(pos.add(0, 2, 1)).getBlock() != Blocks.REDSTONE_BLOCK) {
          if (getBlock(pos.add(1, 1, 1)).getBlock() == Blocks.AIR) {
            perform1(pos.add(1, 1, 1));
          } else if (getBlock(pos.add(1, 1, 1)).getBlock() != Blocks.REDSTONE_BLOCK) {
            if (getBlock(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
              perform1(pos.add(0, 1, 2));
            } else if (getBlock(pos.add(0, 1, 2)).getBlock() != Blocks.REDSTONE_BLOCK && 
              getBlock(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR) {
              perform1(pos.add(-1, 1, 1));
            } 
          } 
        } 
        return;
      }  
    if (angle[1] >= -71.0F && angle[1] <= 71.0F)
      if ((((angle[0] >= 129.0F) ? 1 : 0) | ((angle[0] <= -129.0F) ? 1 : 0)) != 0 && getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 2, 1)).getBlock() == Blocks.AIR)
        if ((((getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR) ? 1 : 0) | ((getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.PISTON) ? 1 : 0)) != 0) {
          perform(pos.add(0, 1, -1));
          if (getBlock(pos.add(0, 2, -1)).getBlock() == Blocks.AIR) {
            perform1(pos.add(0, 2, -1));
          } else if (getBlock(pos.add(0, 2, -1)).getBlock() != Blocks.REDSTONE_BLOCK) {
            if (getBlock(pos.add(1, 1, -1)).getBlock() == Blocks.AIR) {
              perform1(pos.add(1, 1, -1));
            } else if (getBlock(pos.add(1, 1, -1)).getBlock() != Blocks.REDSTONE_BLOCK) {
              if (getBlock(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
                perform1(pos.add(0, 1, -2));
              } else if (getBlock(pos.add(0, 1, -2)).getBlock() != Blocks.REDSTONE_BLOCK && 
                getBlock(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR) {
                perform1(pos.add(-1, 1, -1));
              } 
            } 
          } 
          return;
        }   
    if (angle[1] >= -71.0F && angle[1] <= 71.0F && angle[0] <= -51.0F && angle[0] >= -129.0F && getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR)
      if ((((getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR) ? 1 : 0) | ((getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.PISTON) ? 1 : 0)) != 0) {
        perform(pos.add(1, 1, 0));
        if (getBlock(pos.add(1, 2, 0)).getBlock() == Blocks.AIR) {
          perform1(pos.add(1, 2, 0));
        } else if (getBlock(pos.add(1, 2, 0)).getBlock() != Blocks.REDSTONE_BLOCK) {
          if (getBlock(pos.add(1, 1, 1)).getBlock() == Blocks.AIR) {
            perform1(pos.add(1, 1, 1));
          } else if (getBlock(pos.add(1, 1, 1)).getBlock() != Blocks.REDSTONE_BLOCK) {
            if (getBlock(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
              perform1(pos.add(2, 1, 0));
            } else if (getBlock(pos.add(2, 1, 0)).getBlock() != Blocks.REDSTONE_BLOCK && 
              getBlock(pos.add(1, 1, -1)).getBlock() == Blocks.AIR) {
              perform1(pos.add(1, 1, -1));
            } 
          } 
        } 
        return;
      }  
    if (angle[1] >= -71.0F && angle[1] <= 71.0F && angle[0] >= 51.0F && angle[0] <= 129.0F && getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 2, 0)).getBlock() == Blocks.AIR)
      if ((((getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) ? 1 : 0) | ((getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.PISTON) ? 1 : 0)) != 0) {
        perform(pos.add(-1, 1, 0));
        if (getBlock(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR) {
          perform1(pos.add(-1, 2, 0));
        } else if (getBlock(pos.add(-1, 2, 0)).getBlock() != Blocks.REDSTONE_BLOCK) {
          if (getBlock(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR) {
            perform1(pos.add(-1, 1, 1));
          } else if (getBlock(pos.add(-1, 1, 1)).getBlock() != Blocks.REDSTONE_BLOCK) {
            if (getBlock(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
              perform1(pos.add(-2, 1, 0));
            } else if (getBlock(pos.add(-2, 1, 0)).getBlock() != Blocks.REDSTONE_BLOCK && 
              getBlock(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR) {
              perform1(pos.add(-1, 1, -1));
            } 
          } 
        } 
        return;
      }  
    if (((Boolean)this.disable.getValue()).booleanValue())
      toggle(); 
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    if (target != null)
      return target.getName(); 
    return null;
  }
  
  public void onRender2D(Render2DEvent event) {
    if (!((Boolean)this.debug.getValue()).booleanValue())
      return; 
    if (InventoryUtil.findHotbarBlock(BlockPistonBase.class) == -1)
      return; 
    if (InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK) == -1)
      return; 
    target = getTarget(((Float)this.range.getValue()).floatValue());
    if (target == null)
      return; 
    BlockPos pos = new BlockPos(target.posX, target.posY, target.posZ);
    float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F)));
    mc.fontRenderer.drawString(angle[0] + "   " + angle[1], 200.0F, 200.0F, 255, true);
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
  
  private IBlockState getBlock(BlockPos block) {
    return mc.world.getBlockState(block);
  }
  
  private void perform(BlockPos pos) {
    int old = AntiCity.mc.player.inventory.currentItem;
    if (mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
      mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockPistonBase.class);
      mc.playerController.updateController();
      BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
      mc.player.inventory.currentItem = old;
      mc.playerController.updateController();
    } 
  }
  
  private void perform1(BlockPos pos) {
    int old = AntiCity.mc.player.inventory.currentItem;
    if (mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
      mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK);
      mc.playerController.updateController();
      BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
      mc.player.inventory.currentItem = old;
      mc.playerController.updateController();
    } 
  }
}
