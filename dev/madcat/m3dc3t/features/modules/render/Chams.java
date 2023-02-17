package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.features.modules.Module;

public class Chams extends Module {
  private static Chams INSTANCE = new Chams();
  
  public Chams() {
    super("Chams", "Player behind rendered wall.", Module.Category.RENDER, false, false, false);
    setInstance();
  }
  
  public static Chams getInstance() {
    if (INSTANCE == null)
      INSTANCE = new Chams(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
}
