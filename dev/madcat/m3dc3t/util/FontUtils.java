//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import dev.madcat.m3dc3t.M3dC3t;
import net.minecraft.client.Minecraft;

public class FontUtils {
  private static final Minecraft mc = Minecraft.getMinecraft();
  
  public static float drawStringWithShadow(boolean customFont, String text, int x, int y, int color) {
    if (customFont)
      return M3dC3t.fontRenderer.drawStringWithShadow(text, x, y, color); 
    return mc.fontRenderer.drawStringWithShadow(text, x, y, color);
  }
  
  public static int getStringWidth(boolean customFont, String str) {
    if (customFont)
      return M3dC3t.fontRenderer.getStringWidth(str); 
    return mc.fontRenderer.getStringWidth(str);
  }
  
  public static int getFontHeight(boolean customFont) {
    if (customFont)
      return M3dC3t.fontRenderer.getHeight(); 
    return mc.fontRenderer.FONT_HEIGHT;
  }
  
  public static float drawKeyStringWithShadow(boolean customFont, String text, int x, int y, int color) {
    if (customFont)
      return M3dC3t.fontRenderer.drawStringWithShadow(text, x, y, color); 
    return mc.fontRenderer.drawStringWithShadow(text, x, y, color);
  }
}
