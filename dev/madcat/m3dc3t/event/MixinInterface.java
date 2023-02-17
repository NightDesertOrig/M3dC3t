//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.event;

import net.minecraft.client.Minecraft;

public interface MixinInterface {
  public static final Minecraft mc = Minecraft.getMinecraft();
  
  public static final boolean nullCheck = (mc.player == null || mc.world == null);
}
