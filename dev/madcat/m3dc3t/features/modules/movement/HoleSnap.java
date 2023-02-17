//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.command.Command;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.MathUtil;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HoleSnap extends Module {
  private static HoleSnap INSTANCE = new HoleSnap();
  
  private final Setting<Float> range = register(new Setting("Range", Float.valueOf(8.0F), Float.valueOf(0.0F), Float.valueOf(100.0F)));
  
  private final Setting<Float> range2 = register(new Setting("Timer", Float.valueOf(2.0F), Float.valueOf(0.5F), Float.valueOf(8.0F)));
  
  public HoleSnap() {
    super("HoleSnap", "Teleports you in a hole.", Module.Category.MOVEMENT, true, false, false);
    setInstance();
  }
  
  public static HoleSnap getInstance() {
    if (INSTANCE == null)
      INSTANCE = new HoleSnap(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  private IBlockState getBlock(BlockPos block) {
    return mc.world.getBlockState(block);
  }
  
  @SubscribeEvent
  public void onUpdateInput(InputUpdateEvent event) {
    M3dC3t.holeManager.update();
    List<BlockPos> holes = M3dC3t.holeManager.getSortedHoles();
    BlockPos pos;
    if (!holes.isEmpty() && mc.player.getDistanceSq(pos = holes.get(0)) <= MathUtil.square(((Float)this.range.getValue()).floatValue())) {
      BlockPos playpos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
      if (playpos == null)
        return; 
      if ((((getBlock(playpos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(playpos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
        if ((((getBlock(playpos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(playpos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
          if ((((getBlock(playpos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(playpos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
            if ((((getBlock(playpos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(playpos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0) {
              disable();
              TickShift.mc.timer.tickLength = 50.0F;
              return;
            }    
      if (playpos.getZ() == pos.getY() && playpos.getZ() == pos.getY() && playpos.getX() == pos.getX()) {
        disable();
        TickShift.mc.timer.tickLength = 50.0F;
        return;
      } 
      M3dC3t.rotationManager.lookAtPos(pos);
      (event.getMovementInput()).moveForward = 1.0F;
      TickShift.mc.timer.tickLength = 50.0F / ((Float)this.range2.getValue()).floatValue();
    } else {
      disable();
      Command.sendMessage("<" + getDisplayName() + "> " + ChatFormatting.RED + "No Hole in range");
    } 
  }
  
  public void onDisable() {
    if (mc.timer.tickLength == 50.0F / ((Float)this.range2.getValue()).floatValue())
      TickShift.mc.timer.tickLength = 50.0F; 
  }
}
