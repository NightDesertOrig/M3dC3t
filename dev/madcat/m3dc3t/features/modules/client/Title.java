package dev.madcat.m3dc3t.features.modules.client;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;

public class Title extends Module {
  private static Title INSTANCE = new Title();
  
  public Setting<String> titleName;
  
  public Title() {
    super("Title", "Change client title.", Module.Category.CLIENT, true, false, false);
    this.titleName = register(new Setting("TitleName", "CuteCat :3", "client title."));
    setInstance();
  }
  
  public static Title getInstance() {
    if (INSTANCE == null)
      INSTANCE = new Title(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
}
