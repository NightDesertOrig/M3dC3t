//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.gui.M3dC3tGui;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.manager.TextManager;
import dev.madcat.m3dc3t.util.Util;
import java.util.ArrayList;
import java.util.List;

public class Feature implements Util {
  public List<Setting> settings = new ArrayList<>();
  
  public TextManager renderer = M3dC3t.textManager;
  
  private String name;
  
  public Feature() {}
  
  public Feature(String name) {
    this.name = name;
  }
  
  public static boolean nullCheck() {
    return (mc.player == null);
  }
  
  public static boolean fullNullCheck() {
    return (mc.player == null || mc.world == null);
  }
  
  public String getName() {
    return this.name;
  }
  
  public List<Setting> getSettings() {
    return this.settings;
  }
  
  public boolean hasSettings() {
    return !this.settings.isEmpty();
  }
  
  public boolean isEnabled() {
    if (this instanceof Module)
      return ((Module)this).isOn(); 
    return false;
  }
  
  public boolean isDisabled() {
    return !isEnabled();
  }
  
  public Setting register(Setting setting) {
    setting.setFeature(this);
    this.settings.add(setting);
    if (this instanceof Module && mc.currentScreen instanceof M3dC3tGui)
      M3dC3tGui.getInstance().updateModule((Module)this); 
    return setting;
  }
  
  public void unregister(Setting settingIn) {
    ArrayList<Setting> removeList = new ArrayList<>();
    for (Setting setting : this.settings) {
      if (!setting.equals(settingIn))
        continue; 
      removeList.add(setting);
    } 
    if (!removeList.isEmpty())
      this.settings.removeAll(removeList); 
    if (this instanceof Module && mc.currentScreen instanceof M3dC3tGui)
      M3dC3tGui.getInstance().updateModule((Module)this); 
  }
  
  public Setting getSettingByName(String name) {
    for (Setting setting : this.settings) {
      if (!setting.getName().equalsIgnoreCase(name))
        continue; 
      return setting;
    } 
    return null;
  }
  
  public void reset() {
    for (Setting setting : this.settings)
      setting.setValue(setting.getDefaultValue()); 
  }
  
  public void clearSettings() {
    this.settings = new ArrayList<>();
  }
}
