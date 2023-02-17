//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.combat.InstantMine;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.Timer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AutoDupe extends Module {
  public Setting<Boolean> packet;
  
  public Setting<Boolean> rotate;
  
  private final Setting<Integer> delay;
  
  private final Timer timer;
  
  private BlockPos pos;
  
  int Im;
  
  public AutoDupe() {
    super("AutoDupe", "Automatically places Shulker.", Module.Category.MISC, true, false, false);
    this.packet = register(new Setting("Packet", Boolean.valueOf(false)));
    this.rotate = register(new Setting("Rotate", Boolean.valueOf(false)));
    this.delay = register(new Setting("Delay", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(2000)));
    this.timer = new Timer();
  }
  
  public void onEnable() {}
  
  public void onDisable() {}
  
  public void onUpdate() {
    if (InstantMine.breakPos == null)
      return; 
    this.pos = InstantMine.breakPos;
    IBlockState blockState = mc.world.getBlockState(this.pos);
    this.Im = getItemShulkerBox();
    if (blockState.getBlock() == Blocks.AIR && this.Im != -1) {
      mc.player.inventory.currentItem = this.Im;
      BlockUtil.placeBlock(this.pos, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), ((Boolean)this.packet.getValue()).booleanValue(), false);
      this.timer.passedDms(((Integer)this.delay.getValue()).intValue());
    } 
  }
  
  public int getItemShulkerBox() {
    int fus = -1;
    for (int x = 0; x <= 8; x++) {
      Item item = mc.player.inventory.getStackInSlot(x).getItem();
      if (item instanceof net.minecraft.item.ItemShulkerBox)
        fus = x; 
    } 
    return fus;
  }
}
