package dev.madcat.m3dc3t.util;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class MomentumEvent extends Event {
  Stage stage;
  
  public MomentumEvent() {}
  
  public MomentumEvent(Stage stage) {
    this.stage = stage;
  }
  
  public Stage getStage() {
    return this.stage;
  }
  
  public void setStage(Stage stage) {
    this.stage = stage;
    setCanceled(false);
  }
  
  public enum Stage {
    PRE, POST;
  }
}
