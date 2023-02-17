//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.client;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.util.Util;

public class GUIBlur extends Module {
  public GUIBlur() {
    super("GUIBlur", "Blur in interface", Module.Category.CLIENT, true, false, false);
  }
  
  public void onDisable() {
    this;
    if (mc.world != null)
      Util.mc.entityRenderer.getShaderGroup().deleteShaderGroup(); 
  }
  
  public void onUpdate() {
    // Byte code:
    //   0: aload_0
    //   1: pop
    //   2: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   5: getfield world : Lnet/minecraft/client/multiplayer/WorldClient;
    //   8: ifnull -> 319
    //   11: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   14: invokevirtual isEnabled : ()Z
    //   17: ifne -> 188
    //   20: aload_0
    //   21: pop
    //   22: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   25: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   28: instanceof net/minecraft/client/gui/inventory/GuiContainer
    //   31: ifne -> 188
    //   34: aload_0
    //   35: pop
    //   36: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   39: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   42: instanceof net/minecraft/client/gui/GuiChat
    //   45: ifne -> 188
    //   48: aload_0
    //   49: pop
    //   50: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   53: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   56: instanceof net/minecraft/client/gui/GuiConfirmOpenLink
    //   59: ifne -> 188
    //   62: aload_0
    //   63: pop
    //   64: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   67: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   70: instanceof net/minecraft/client/gui/inventory/GuiEditSign
    //   73: ifne -> 188
    //   76: aload_0
    //   77: pop
    //   78: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   81: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   84: instanceof net/minecraft/client/gui/GuiGameOver
    //   87: ifne -> 188
    //   90: aload_0
    //   91: pop
    //   92: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   95: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   98: instanceof net/minecraft/client/gui/GuiOptions
    //   101: ifne -> 188
    //   104: aload_0
    //   105: pop
    //   106: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   109: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   112: instanceof net/minecraft/client/gui/GuiIngameMenu
    //   115: ifne -> 188
    //   118: aload_0
    //   119: pop
    //   120: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   123: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   126: instanceof net/minecraft/client/gui/GuiVideoSettings
    //   129: ifne -> 188
    //   132: aload_0
    //   133: pop
    //   134: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   137: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   140: instanceof net/minecraft/client/gui/GuiScreenOptionsSounds
    //   143: ifne -> 188
    //   146: aload_0
    //   147: pop
    //   148: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   151: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   154: instanceof net/minecraft/client/gui/GuiControls
    //   157: ifne -> 188
    //   160: aload_0
    //   161: pop
    //   162: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   165: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   168: instanceof net/minecraft/client/gui/GuiCustomizeSkin
    //   171: ifne -> 188
    //   174: aload_0
    //   175: pop
    //   176: getstatic dev/madcat/m3dc3t/features/modules/client/GUIBlur.mc : Lnet/minecraft/client/Minecraft;
    //   179: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   182: instanceof net/minecraftforge/fml/client/GuiModList
    //   185: ifeq -> 295
    //   188: getstatic net/minecraft/client/renderer/OpenGlHelper.shadersSupported : Z
    //   191: ifeq -> 259
    //   194: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   197: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
    //   200: instanceof net/minecraft/entity/player/EntityPlayer
    //   203: ifeq -> 259
    //   206: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   209: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
    //   212: invokevirtual getShaderGroup : ()Lnet/minecraft/client/shader/ShaderGroup;
    //   215: ifnull -> 230
    //   218: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   221: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
    //   224: invokevirtual getShaderGroup : ()Lnet/minecraft/client/shader/ShaderGroup;
    //   227: invokevirtual deleteShaderGroup : ()V
    //   230: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   233: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
    //   236: new net/minecraft/util/ResourceLocation
    //   239: dup
    //   240: ldc 'shaders/post/blur.json'
    //   242: invokespecial <init> : (Ljava/lang/String;)V
    //   245: invokevirtual loadShader : (Lnet/minecraft/util/ResourceLocation;)V
    //   248: goto -> 319
    //   251: astore_1
    //   252: aload_1
    //   253: invokevirtual printStackTrace : ()V
    //   256: goto -> 319
    //   259: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   262: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
    //   265: invokevirtual getShaderGroup : ()Lnet/minecraft/client/shader/ShaderGroup;
    //   268: ifnull -> 319
    //   271: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   274: getfield currentScreen : Lnet/minecraft/client/gui/GuiScreen;
    //   277: ifnonnull -> 319
    //   280: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   283: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
    //   286: invokevirtual getShaderGroup : ()Lnet/minecraft/client/shader/ShaderGroup;
    //   289: invokevirtual deleteShaderGroup : ()V
    //   292: goto -> 319
    //   295: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   298: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
    //   301: invokevirtual getShaderGroup : ()Lnet/minecraft/client/shader/ShaderGroup;
    //   304: ifnull -> 319
    //   307: getstatic dev/madcat/m3dc3t/util/Util.mc : Lnet/minecraft/client/Minecraft;
    //   310: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
    //   313: invokevirtual getShaderGroup : ()Lnet/minecraft/client/shader/ShaderGroup;
    //   316: invokevirtual deleteShaderGroup : ()V
    //   319: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #26	-> 0
    //   #27	-> 11
    //   #28	-> 188
    //   #29	-> 206
    //   #30	-> 218
    //   #33	-> 230
    //   #37	-> 248
    //   #35	-> 251
    //   #36	-> 252
    //   #37	-> 256
    //   #39	-> 259
    //   #40	-> 280
    //   #43	-> 295
    //   #44	-> 307
    //   #47	-> 319
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   252	4	1	e	Ljava/lang/Exception;
    //   0	320	0	this	Ldev/madcat/m3dc3t/features/modules/client/GUIBlur;
    // Exception table:
    //   from	to	target	type
    //   230	248	251	java/lang/Exception
  }
}
