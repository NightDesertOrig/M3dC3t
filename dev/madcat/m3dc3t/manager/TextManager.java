//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.manager;

import dev.madcat.m3dc3t.features.Feature;
import dev.madcat.m3dc3t.features.gui.font.CFont;
import dev.madcat.m3dc3t.features.gui.font.CFontRenderer;
import dev.madcat.m3dc3t.features.gui.font.CustomFont;
import dev.madcat.m3dc3t.features.modules.client.FontMod;
import dev.madcat.m3dc3t.util.MathUtil;
import dev.madcat.m3dc3t.util.Timer;
import dev.madcat.m3dc3t.util.Util;
import java.awt.Color;
import java.awt.Font;
import net.minecraft.util.math.MathHelper;

public class TextManager extends Feature {
  private final Timer idleTimer = new Timer();
  
  public int scaledWidth;
  
  public int scaledHeight;
  
  public int scaleFactor;
  
  private CustomFont customFont = new CustomFont(new Font("Verdana", 0, 17), true, false);
  
  public CFontRenderer iconFont = new CFontRenderer(new CFont.CustomFont("/assets/minecraft/textures/fonts/IconFont.ttf", 20.0F, 0), true, false);
  
  private CustomFont ClickGuiFont = new CustomFont(new Font("Arial", 1, 17), true, false);
  
  private boolean idling;
  
  public TextManager() {
    updateResolution();
  }
  
  public void init(boolean startup) {
    FontMod cFont = ModuleManager.<FontMod>getModuleByClass(FontMod.class);
    try {
      setFontRenderer(new Font((String)cFont.fontName.getValue(), ((Integer)cFont.fontStyle.getValue()).intValue(), ((Integer)cFont.fontSize.getValue()).intValue()), ((Boolean)cFont.antiAlias.getValue()).booleanValue(), ((Boolean)cFont.fractionalMetrics.getValue()).booleanValue());
    } catch (Exception exception) {}
  }
  
  public void drawStringWithShadow(String text, float x, float y, int color) {
    drawString(text, x, y, color, true);
  }
  
  public float drawString2(String text, float x, float y, int color) {
    mc.fontRenderer.drawString(text, x, y, color, true);
    return x;
  }
  
  public float drawString(String text, float x, float y, int color, boolean shadow) {
    if (ModuleManager.getModuleByName("CustomFont").isEnabled() && ((Boolean)(FontMod.getInstance()).clientFont.getValue()).booleanValue()) {
      if (shadow) {
        this.customFont.drawStringWithShadow(text, x, y, color);
      } else {
        this.customFont.drawString(text, x, y, color);
      } 
      return x;
    } 
    mc.fontRenderer.drawString(text, x, y, color, shadow);
    return x;
  }
  
  public float drawStringlogo(String text, float x, float y, int color) {
    this.iconFont.drawStringWithShadow(text, x, y, color);
    return x;
  }
  
  public float drawStringClickGui(String text, float x, float y, int color) {
    this.ClickGuiFont.drawStringWithShadow(text, x, y, color);
    return x;
  }
  
  public void drawRainbowString(String text, float x, float y, int startColor, float factor, boolean shadow) {
    Color currentColor = new Color(startColor);
    float hueIncrement = 1.0F / factor;
    String[] rainbowStrings = text.split("§.");
    float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
    float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
    float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
    int currentWidth = 0;
    boolean shouldRainbow = true;
    boolean shouldContinue = false;
    for (int i = 0; i < text.length(); i++) {
      char currentChar = text.charAt(i);
      char nextChar = text.charAt(MathUtil.clamp(i + 1, 0, text.length() - 1));
      if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
        shouldRainbow = false;
      } else if ((String.valueOf(currentChar) + nextChar).equals("§+")) {
        shouldRainbow = true;
      } 
      if (shouldContinue) {
        shouldContinue = false;
      } else {
        if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
          String escapeString = text.substring(i);
          drawString(escapeString, x + currentWidth, y, Color.WHITE.getRGB(), shadow);
          break;
        } 
        drawString(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB(), shadow);
        if (String.valueOf(currentChar).equals("§"))
          shouldContinue = true; 
        currentWidth += getStringWidth(String.valueOf(currentChar));
        if (!String.valueOf(currentChar).equals(" ")) {
          currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
          currentHue += hueIncrement;
        } 
      } 
    } 
  }
  
  public int getStringWidth(String text) {
    if (ModuleManager.getModuleByName("CustomFont").isEnabled() && ((Boolean)(FontMod.getInstance()).clientFont.getValue()).booleanValue())
      return this.customFont.getStringWidth(text); 
    return mc.fontRenderer.getStringWidth(text);
  }
  
  public int getStringCWidth(String text) {
    return this.ClickGuiFont.getStringWidth(text);
  }
  
  public int getFontHeight() {
    return mc.fontRenderer.FONT_HEIGHT;
  }
  
  public void setFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
    this.customFont = new CustomFont(font, antiAlias, fractionalMetrics);
  }
  
  public Font getCurrentFont() {
    return this.customFont.getFont();
  }
  
  public void updateResolution() {
    this.scaledWidth = mc.displayWidth;
    this.scaledHeight = mc.displayHeight;
    this.scaleFactor = 1;
    boolean flag = Util.mc.isUnicode();
    int i = mc.gameSettings.guiScale;
    if (i == 0)
      i = 1000; 
    while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240)
      this.scaleFactor++; 
    if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1)
      this.scaleFactor--; 
    double scaledWidthD = (this.scaledWidth / this.scaleFactor);
    double scaledHeightD = (this.scaledHeight / this.scaleFactor);
    this.scaledWidth = MathHelper.ceil(scaledWidthD);
    this.scaledHeight = MathHelper.ceil(scaledHeightD);
  }
  
  public String getIdleSign() {
    if (this.idleTimer.passedMs(500L)) {
      this.idling = !this.idling;
      this.idleTimer.reset();
    } 
    if (this.idling)
      return "_"; 
    return "";
  }
}
