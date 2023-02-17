//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui.components.items.buttons;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.gui.M3dC3tGui;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.manager.ModuleManager;
import dev.madcat.m3dc3t.util.RenderUtil;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ChatAllowedCharacters;

public class StringButton extends Button {
  private final Setting setting;
  
  public boolean isListening;
  
  private CurrentString currentString = new CurrentString("");
  
  public StringButton(Setting setting) {
    super(setting.getName());
    this.setting = setting;
    this.width = 15;
  }
  
  public static String removeLastChar(String str) {
    String output = "";
    if (str != null && str.length() > 0)
      output = str.substring(0, str.length() - 1); 
    return output;
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4F, this.y + this.height - 0.5F, getState() ? (!isHovering(mouseX, mouseY) ? M3dC3t.colorManager.getColorWithAlpha(((Integer)((ClickGui)ModuleManager.getModuleByClass(ClickGui.class)).hoverAlpha.getValue()).intValue()) : M3dC3t.colorManager.getColorWithAlpha(((Integer)((ClickGui)ModuleManager.getModuleByClass(ClickGui.class)).alpha.getValue()).intValue())) : (!isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
    if (this.isListening) {
      M3dC3t.textManager.drawStringWithShadow(this.currentString.getString() + M3dC3t.textManager.getIdleSign(), this.x + 2.3F, this.y - 1.7F - M3dC3tGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
    } else {
      M3dC3t.textManager.drawStringWithShadow((this.setting.getName().equals("Buttons") ? "Buttons " : (this.setting.getName().equals("Prefix") ? ("Prefix  " + ChatFormatting.GRAY) : "")) + this.setting.getValue(), this.x + 2.3F, this.y - 1.7F - M3dC3tGui.getClickGui().getTextOffset(), getState() ? -1 : -5592406);
    } 
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (isHovering(mouseX, mouseY))
      mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F)); 
  }
  
  public void onKeyTyped(char typedChar, int keyCode) {
    if (this.isListening) {
      switch (keyCode) {
        case 1:
          return;
        case 28:
          enterString();
        case 14:
          setString(removeLastChar(this.currentString.getString()));
          break;
      } 
      if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
        setString(this.currentString.getString() + typedChar); 
    } 
  }
  
  public void update() {
    setHidden(!this.setting.isVisible());
  }
  
  private void enterString() {
    if (this.currentString.getString().isEmpty()) {
      this.setting.setValue(this.setting.getDefaultValue());
    } else {
      this.setting.setValue(this.currentString.getString());
    } 
    setString("");
    onMouseClick();
  }
  
  public int getHeight() {
    return 14;
  }
  
  public void toggle() {
    this.isListening = !this.isListening;
  }
  
  public boolean getState() {
    return !this.isListening;
  }
  
  public void setString(String newString) {
    this.currentString = new CurrentString(newString);
  }
  
  public static class CurrentString {
    private final String string;
    
    public CurrentString(String string) {
      this.string = string;
    }
    
    public String getString() {
      return this.string;
    }
  }
}
