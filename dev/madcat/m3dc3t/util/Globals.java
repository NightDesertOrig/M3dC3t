//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import java.util.Random;
import net.minecraft.client.Minecraft;

public interface Globals {
  public static final Minecraft mc = Minecraft.getMinecraft();
  
  public static final Random random = new Random();
  
  public static final char SECTIONSIGN = '§';
  
  default boolean nullCheck() {
    return (mc.player == null || mc.world == null);
  }
}
