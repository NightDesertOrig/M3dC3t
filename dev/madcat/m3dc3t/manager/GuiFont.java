package dev.madcat.m3dc3t.manager;

import dev.madcat.m3dc3t.features.gui.font.CustomFont;
import dev.madcat.m3dc3t.util.Globals;
import dev.madcat.m3dc3t.util.Rainbow;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Locale;

public class GuiFont implements Globals {
  private final String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(Locale.ENGLISH);
  
  public String fontName = "Tahoma";
  
  public int fontSize = 16;
  
  private CustomFont font = new CustomFont(new Font(this.fontName, 0, this.fontSize), true, false);
  
  public void setFont() {
    this.font = new CustomFont(new Font(this.fontName, 0, this.fontSize), true, false);
  }
  
  public void reset() {
    setFont("Tahoma");
    setFontSize(16);
    setFont();
  }
  
  public boolean setFont(String fontName) {
    for (String font : this.fonts) {
      if (fontName.equalsIgnoreCase(font)) {
        this.fontName = font;
        setFont();
        return true;
      } 
    } 
    return false;
  }
  
  public void setFontSize(int size) {
    this.fontSize = size;
    setFont();
  }
  
  public void drawStringWithShadow(String string, float x, float y, int colour) {
    drawString(string, x, y, colour, true);
  }
  
  public float drawString(String string, float x, float y, int colour, boolean shadow) {
    if (shadow)
      return this.font.drawStringWithShadow(string, x, y, colour); 
    return this.font.drawString(string, x, y, colour);
  }
  
  public void drawStringRainbow(String string, float x, float y, boolean shadow) {
    if (shadow) {
      this.font.drawStringWithShadow(string, x, y, Rainbow.getColour().getRGB());
    } else {
      this.font.drawString(string, x, y, Rainbow.getColour().getRGB());
    } 
  }
  
  public int getTextWidth(String string) {
    return this.font.getStringWidth(string);
  }
}
