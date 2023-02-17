package dev.madcat.m3dc3t.features.modules.movement;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;

public class InventoryMove extends Module {
  private static InventoryMove INSTANCE = new InventoryMove();
  
  public Setting<Boolean> shift;
  
  public InventoryMove() {
    super("InvMove", "Allow walking on the interface.", Module.Category.MOVEMENT, true, false, false);
    this.shift = register(new Setting("Sneak", Boolean.valueOf(false)));
    setInstance();
  }
  
  public static InventoryMove getInstance() {
    if (INSTANCE == null)
      INSTANCE = new InventoryMove(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
}
