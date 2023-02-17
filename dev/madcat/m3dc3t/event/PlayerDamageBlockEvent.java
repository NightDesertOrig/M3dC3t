package dev.madcat.m3dc3t.event;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PlayerDamageBlockEvent extends EventStage {
  public BlockPos pos;
  
  public EnumFacing facing;
  
  public PlayerDamageBlockEvent(int stage, BlockPos pos, EnumFacing facing) {
    super(stage);
    this.pos = pos;
    this.facing = facing;
  }
}
