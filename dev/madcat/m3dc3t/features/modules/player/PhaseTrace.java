package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;

public class PhaseTrace extends Module {
  private static PhaseTrace INSTANCE = new PhaseTrace();
  
  public boolean noTrace;
  
  public PhaseTrace() {
    super("PhaseTrace", "No entities hit box.", Module.Category.PLAYER, false, false, false);
    setInstance();
  }
  
  public static PhaseTrace getINSTANCE() {
    if (INSTANCE == null)
      INSTANCE = new PhaseTrace(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public void onUpdate() {
    this.noTrace = true;
  }
}
