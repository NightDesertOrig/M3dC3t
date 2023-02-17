package dev.madcat.m3dc3t.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.features.command.Command;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;

public class NickHider extends Module {
  public final Setting<String> NameString = register(new Setting("Name", "New Name Here..."));
  
  private static NickHider instance;
  
  public NickHider() {
    super("NameChanger", "Change your name.", Module.Category.CLIENT, false, false, false);
    instance = this;
  }
  
  public void onEnable() {
    Command.sendMessage(ChatFormatting.GRAY + "Success! Name succesfully changed to " + ChatFormatting.GREEN + (String)this.NameString.getValue());
  }
  
  public static NickHider getInstance() {
    if (instance == null)
      instance = new NickHider(); 
    return instance;
  }
}
