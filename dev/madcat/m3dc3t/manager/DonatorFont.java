package dev.madcat.m3dc3t.manager;

import dev.madcat.m3dc3t.features.gui.font.CustomFont;
import dev.madcat.m3dc3t.util.Globals;
import java.awt.Font;

public class DonatorFont implements Globals {
  private final String fontName = "Tahoma";
  
  private final int smallSize = 15;
  
  private final int mediumSize = 19;
  
  private final int largeSize = 24;
  
  private final CustomFont smallFont = new CustomFont(new Font("Tahoma", 0, 15), true, false);
  
  private final CustomFont mediumFont = new CustomFont(new Font("Tahoma", 0, 19), true, false);
  
  private final CustomFont largeFont = new CustomFont(new Font("Tahoma", 0, 24), true, false);
  
  public void drawSmallStringRainbow(String string, float x, float y, int colour) {
    this.smallFont.drawStringWithShadow(string, x, y, colour);
  }
  
  public void drawMediumStringRainbow(String string, float x, float y, int colour) {
    this.mediumFont.drawStringWithShadow(string, x, y, colour);
  }
  
  public void drawLargeStringRainbow(String string, float x, float y, int colour) {
    this.largeFont.drawStringWithShadow(string, x, y, colour);
  }
}
