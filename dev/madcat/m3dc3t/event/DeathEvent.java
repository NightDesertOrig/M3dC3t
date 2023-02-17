package dev.madcat.m3dc3t.event;

import net.minecraft.entity.player.EntityPlayer;

public class DeathEvent extends EventStage {
  public EntityPlayer player;
  
  public DeathEvent(EntityPlayer player) {
    this.player = player;
  }
}
