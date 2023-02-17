//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;

public class Colour extends Color {
  public Colour(int r, int g, int b) {
    super(r, g, b);
  }
  
  public Colour(int rgb) {
    super(rgb);
  }
  
  public Colour(int rgba, boolean hasalpha) {
    super(rgba, hasalpha);
  }
  
  public Colour(int r, int g, int b, int a) {
    super(r, g, b, a);
  }
  
  public Colour(Color color) {
    super(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
  }
  
  public Colour(Colour color, int a) {
    super(color.getRed(), color.getGreen(), color.getBlue(), a);
  }
  
  public static Colour fromHSB(float hue, float saturation, float brightness) {
    return new Colour(Color.getHSBColor(hue, saturation, brightness));
  }
  
  public float getHue() {
    return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[0];
  }
  
  public float getSaturation() {
    return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[1];
  }
  
  public float getBrightness() {
    return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[2];
  }
  
  public void glColor() {
    GlStateManager.color(getRed() / 255.0F, getGreen() / 255.0F, getBlue() / 255.0F, getAlpha() / 255.0F);
  }
}
