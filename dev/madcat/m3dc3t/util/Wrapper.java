//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class Wrapper {
  public static final Minecraft mc = Minecraft.getMinecraft();
  
  public static Minecraft getMinecraft() {
    return Minecraft.getMinecraft();
  }
  
  public static EntityPlayerSP getPlayer() {
    return (getMinecraft()).player;
  }
  
  public static World getWorld() {
    return (World)(getMinecraft()).world;
  }
  
  public static int getKey(String keyname) {
    return Keyboard.getKeyIndex(keyname.toUpperCase());
  }
}
