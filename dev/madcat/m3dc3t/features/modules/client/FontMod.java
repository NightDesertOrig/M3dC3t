package dev.madcat.m3dc3t.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.ClientEvent;
import dev.madcat.m3dc3t.features.command.Command;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import java.awt.GraphicsEnvironment;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FontMod extends Module {
  private static FontMod INSTANCE = new FontMod();
  
  public Setting<Boolean> cfont = register(new Setting("ClickGuiFont", Boolean.valueOf(true)));
  
  public Setting<Boolean> clientFont = register(new Setting("ClientFont", Boolean.valueOf(true), "test."));
  
  public Setting<String> fontName = register(new Setting("FontName", "Arial", v -> ((Boolean)this.clientFont.getValue()).booleanValue()));
  
  public Setting<Boolean> antiAlias = register(new Setting("AntiAlias", Boolean.valueOf(true), v -> ((Boolean)this.clientFont.getValue()).booleanValue()));
  
  public Setting<Boolean> fractionalMetrics = register(new Setting("Metrics", Boolean.valueOf(true), v -> ((Boolean)this.clientFont.getValue()).booleanValue()));
  
  public Setting<Integer> fontSize = register(new Setting("Size", Integer.valueOf(18), Integer.valueOf(12), Integer.valueOf(30), v -> ((Boolean)this.clientFont.getValue()).booleanValue()));
  
  public Setting<Integer> fontStyle = register(new Setting("Style", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(3), v -> ((Boolean)this.clientFont.getValue()).booleanValue()));
  
  private boolean reloadFont = false;
  
  public FontMod() {
    super("CustomFont", "Modify the font of client text.", Module.Category.CLIENT, true, false, false);
    setInstance();
  }
  
  public static FontMod getInstance() {
    if (INSTANCE == null)
      INSTANCE = new FontMod(); 
    return INSTANCE;
  }
  
  public static boolean checkFont(String font, boolean message) {
    String[] fonts;
    for (String s : fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
      if (!message && s.equals(font))
        return true; 
      if (message)
        Command.sendMessage(s); 
    } 
    return false;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  @SubscribeEvent
  public void onSettingChange(ClientEvent event) {
    if (!((Boolean)this.clientFont.getValue()).booleanValue())
      return; 
    Setting setting;
    if (event.getStage() == 2 && (setting = event.getSetting()) != null && setting.getFeature().equals(this)) {
      if (setting.getName().equals("FontName") && !checkFont(setting.getPlannedValue().toString(), false)) {
        Command.sendMessage(ChatFormatting.RED + "That font doesnt exist.");
        event.setCanceled(true);
        return;
      } 
      this.reloadFont = true;
    } 
  }
  
  public void onTick() {
    if (!((Boolean)this.clientFont.getValue()).booleanValue())
      return; 
    if (this.reloadFont) {
      M3dC3t.textManager.init(false);
      this.reloadFont = false;
    } 
  }
}
