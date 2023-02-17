//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui.components;

import dev.madcat.m3dc3t.features.Feature;
import dev.madcat.m3dc3t.features.gui.M3dC3tGui;
import dev.madcat.m3dc3t.features.gui.components.items.Item;
import dev.madcat.m3dc3t.features.gui.components.items.buttons.Button;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;
import org.lwjgl.opengl.GL11;

public class Component extends Feature {
  public static int[] counter1 = new int[] { 1 };
  
  private final Minecraft minecraft = Minecraft.getMinecraft();
  
  private final ArrayList<Item> items = new ArrayList<>();
  
  private final int barHeight;
  
  public boolean drag;
  
  private int old;
  
  private int x;
  
  private int y;
  
  private int x2;
  
  private int y2;
  
  private int width;
  
  private int height;
  
  private boolean open;
  
  private int angle;
  
  private boolean hidden = false;
  
  private int startcolor;
  
  public Component(String name, int x, int y, boolean open) {
    super(name);
    this.x = x;
    this.y = y;
    this.width = 88 + ((Integer)(ClickGui.getInstance()).moduleWidth.getValue()).intValue();
    this.height = 18;
    this.barHeight = 15;
    this.angle = 180;
    this.open = open;
    setupItems();
  }
  
  public static void drawModalRect(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
    Gui.drawScaledCustomSizeModalRect(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
  }
  
  public static void glColor(Color color) {
    GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
  }
  
  public static float calculateRotation(float var0) {
    float f = 0.0F;
    var0 %= 360.0F;
    if (f >= 180.0F)
      var0 -= 360.0F; 
    if (var0 < -180.0F)
      var0 += 360.0F; 
    return var0;
  }
  
  public void setupItems() {}
  
  private void drag(int mouseX, int mouseY) {
    if (!this.drag)
      return; 
    this.x = this.x2 + mouseX;
    this.y = this.y2 + mouseY;
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    // Byte code:
    //   0: aload_0
    //   1: aload_0
    //   2: getfield x : I
    //   5: putfield old : I
    //   8: aload_0
    //   9: invokevirtual getName : ()Ljava/lang/String;
    //   12: ldc 'Combat'
    //   14: if_acmpne -> 43
    //   17: aload_0
    //   18: aload_0
    //   19: getfield old : I
    //   22: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   25: getfield moduleDistance : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   28: invokevirtual getValue : ()Ljava/lang/Object;
    //   31: checkcast java/lang/Integer
    //   34: invokevirtual intValue : ()I
    //   37: iconst_1
    //   38: imul
    //   39: iadd
    //   40: putfield old : I
    //   43: aload_0
    //   44: invokevirtual getName : ()Ljava/lang/String;
    //   47: ldc 'Misc'
    //   49: if_acmpne -> 78
    //   52: aload_0
    //   53: aload_0
    //   54: getfield old : I
    //   57: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   60: getfield moduleDistance : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   63: invokevirtual getValue : ()Ljava/lang/Object;
    //   66: checkcast java/lang/Integer
    //   69: invokevirtual intValue : ()I
    //   72: iconst_2
    //   73: imul
    //   74: iadd
    //   75: putfield old : I
    //   78: aload_0
    //   79: invokevirtual getName : ()Ljava/lang/String;
    //   82: ldc 'Render'
    //   84: if_acmpne -> 113
    //   87: aload_0
    //   88: aload_0
    //   89: getfield old : I
    //   92: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   95: getfield moduleDistance : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   98: invokevirtual getValue : ()Ljava/lang/Object;
    //   101: checkcast java/lang/Integer
    //   104: invokevirtual intValue : ()I
    //   107: iconst_3
    //   108: imul
    //   109: iadd
    //   110: putfield old : I
    //   113: aload_0
    //   114: invokevirtual getName : ()Ljava/lang/String;
    //   117: ldc 'Movement'
    //   119: if_acmpne -> 148
    //   122: aload_0
    //   123: aload_0
    //   124: getfield old : I
    //   127: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   130: getfield moduleDistance : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   133: invokevirtual getValue : ()Ljava/lang/Object;
    //   136: checkcast java/lang/Integer
    //   139: invokevirtual intValue : ()I
    //   142: iconst_4
    //   143: imul
    //   144: iadd
    //   145: putfield old : I
    //   148: aload_0
    //   149: invokevirtual getName : ()Ljava/lang/String;
    //   152: ldc 'Player'
    //   154: if_acmpne -> 183
    //   157: aload_0
    //   158: aload_0
    //   159: getfield old : I
    //   162: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   165: getfield moduleDistance : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   168: invokevirtual getValue : ()Ljava/lang/Object;
    //   171: checkcast java/lang/Integer
    //   174: invokevirtual intValue : ()I
    //   177: iconst_5
    //   178: imul
    //   179: iadd
    //   180: putfield old : I
    //   183: aload_0
    //   184: invokevirtual getName : ()Ljava/lang/String;
    //   187: ldc 'Client'
    //   189: if_acmpne -> 219
    //   192: aload_0
    //   193: aload_0
    //   194: getfield old : I
    //   197: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   200: getfield moduleDistance : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   203: invokevirtual getValue : ()Ljava/lang/Object;
    //   206: checkcast java/lang/Integer
    //   209: invokevirtual intValue : ()I
    //   212: bipush #6
    //   214: imul
    //   215: iadd
    //   216: putfield old : I
    //   219: aload_0
    //   220: bipush #88
    //   222: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   225: getfield moduleWidth : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   228: invokevirtual getValue : ()Ljava/lang/Object;
    //   231: checkcast java/lang/Integer
    //   234: invokevirtual intValue : ()I
    //   237: iadd
    //   238: putfield width : I
    //   241: aload_0
    //   242: iload_1
    //   243: iload_2
    //   244: invokespecial drag : (II)V
    //   247: iconst_1
    //   248: newarray int
    //   250: dup
    //   251: iconst_0
    //   252: iconst_1
    //   253: iastore
    //   254: putstatic dev/madcat/m3dc3t/features/gui/components/Component.counter1 : [I
    //   257: aload_0
    //   258: getfield open : Z
    //   261: ifeq -> 273
    //   264: aload_0
    //   265: invokespecial getTotalItemHeight : ()F
    //   268: fconst_2
    //   269: fsub
    //   270: goto -> 274
    //   273: fconst_0
    //   274: fstore #5
    //   276: fload #5
    //   278: fstore #6
    //   280: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   283: getfield rainbowg : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   286: invokevirtual getValue : ()Ljava/lang/Object;
    //   289: checkcast java/lang/Boolean
    //   292: invokevirtual booleanValue : ()Z
    //   295: ifeq -> 364
    //   298: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   301: getfield rainbowModeHud : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   304: invokevirtual getValue : ()Ljava/lang/Object;
    //   307: getstatic dev/madcat/m3dc3t/features/modules/client/ClickGui$rainbowMode.Static : Ldev/madcat/m3dc3t/features/modules/client/ClickGui$rainbowMode;
    //   310: if_acmpne -> 431
    //   313: aload_0
    //   314: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   317: getfield rainbowHue : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   320: invokevirtual getValue : ()Ljava/lang/Object;
    //   323: checkcast java/lang/Integer
    //   326: invokevirtual intValue : ()I
    //   329: invokestatic rainbow : (I)Ljava/awt/Color;
    //   332: invokevirtual getRGB : ()I
    //   335: putfield startcolor : I
    //   338: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   341: getfield rainbowHue : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   344: invokevirtual getValue : ()Ljava/lang/Object;
    //   347: checkcast java/lang/Integer
    //   350: invokevirtual intValue : ()I
    //   353: invokestatic rainbow : (I)Ljava/awt/Color;
    //   356: invokevirtual getRGB : ()I
    //   359: istore #4
    //   361: goto -> 431
    //   364: aload_0
    //   365: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   368: getfield g_red : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   371: invokevirtual getValue : ()Ljava/lang/Object;
    //   374: checkcast java/lang/Integer
    //   377: invokevirtual intValue : ()I
    //   380: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   383: getfield g_green : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   386: invokevirtual getValue : ()Ljava/lang/Object;
    //   389: checkcast java/lang/Integer
    //   392: invokevirtual intValue : ()I
    //   395: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   398: getfield g_blue : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   401: invokevirtual getValue : ()Ljava/lang/Object;
    //   404: checkcast java/lang/Integer
    //   407: invokevirtual intValue : ()I
    //   410: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   413: getfield g_alpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   416: invokevirtual getValue : ()Ljava/lang/Object;
    //   419: checkcast java/lang/Integer
    //   422: invokevirtual intValue : ()I
    //   425: invokestatic toRGBA : (IIII)I
    //   428: putfield startcolor : I
    //   431: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   434: getfield g_red1 : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   437: invokevirtual getValue : ()Ljava/lang/Object;
    //   440: checkcast java/lang/Integer
    //   443: invokevirtual intValue : ()I
    //   446: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   449: getfield g_green1 : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   452: invokevirtual getValue : ()Ljava/lang/Object;
    //   455: checkcast java/lang/Integer
    //   458: invokevirtual intValue : ()I
    //   461: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   464: getfield g_blue1 : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   467: invokevirtual getValue : ()Ljava/lang/Object;
    //   470: checkcast java/lang/Integer
    //   473: invokevirtual intValue : ()I
    //   476: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   479: getfield g_alpha1 : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   482: invokevirtual getValue : ()Ljava/lang/Object;
    //   485: checkcast java/lang/Integer
    //   488: invokevirtual intValue : ()I
    //   491: invokestatic toRGBA : (IIII)I
    //   494: istore #4
    //   496: aload_0
    //   497: getfield old : I
    //   500: i2f
    //   501: aload_0
    //   502: getfield y : I
    //   505: i2f
    //   506: aload_0
    //   507: getfield old : I
    //   510: aload_0
    //   511: getfield width : I
    //   514: iadd
    //   515: i2f
    //   516: aload_0
    //   517: getfield y : I
    //   520: aload_0
    //   521: getfield height : I
    //   524: iadd
    //   525: iconst_5
    //   526: isub
    //   527: i2f
    //   528: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   531: getfield red : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   534: invokevirtual getValue : ()Ljava/lang/Object;
    //   537: checkcast java/lang/Integer
    //   540: invokevirtual intValue : ()I
    //   543: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   546: getfield green : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   549: invokevirtual getValue : ()Ljava/lang/Object;
    //   552: checkcast java/lang/Integer
    //   555: invokevirtual intValue : ()I
    //   558: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   561: getfield blue : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   564: invokevirtual getValue : ()Ljava/lang/Object;
    //   567: checkcast java/lang/Integer
    //   570: invokevirtual intValue : ()I
    //   573: sipush #255
    //   576: invokestatic toRGBA : (IIII)I
    //   579: invokestatic drawRect : (FFFFI)V
    //   582: aload_0
    //   583: getfield old : I
    //   586: iconst_1
    //   587: isub
    //   588: i2d
    //   589: aload_0
    //   590: getfield y : I
    //   593: i2d
    //   594: aload_0
    //   595: getfield old : I
    //   598: aload_0
    //   599: getfield width : I
    //   602: iadd
    //   603: iconst_1
    //   604: iadd
    //   605: i2d
    //   606: aload_0
    //   607: getfield y : I
    //   610: aload_0
    //   611: getfield barHeight : I
    //   614: iadd
    //   615: i2f
    //   616: fconst_2
    //   617: fsub
    //   618: f2d
    //   619: aload_0
    //   620: getfield startcolor : I
    //   623: iload #4
    //   625: invokestatic drawGradientSideways : (DDDDII)V
    //   628: aload_0
    //   629: getfield open : Z
    //   632: ifeq -> 747
    //   635: aload_0
    //   636: getfield old : I
    //   639: iconst_1
    //   640: isub
    //   641: i2d
    //   642: aload_0
    //   643: getfield y : I
    //   646: i2f
    //   647: ldc_w 13.2
    //   650: fadd
    //   651: f2d
    //   652: aload_0
    //   653: getfield old : I
    //   656: aload_0
    //   657: getfield width : I
    //   660: iadd
    //   661: iconst_1
    //   662: iadd
    //   663: i2d
    //   664: aload_0
    //   665: getfield y : I
    //   668: i2f
    //   669: fload #5
    //   671: fadd
    //   672: ldc_w 19.0
    //   675: fadd
    //   676: f2d
    //   677: aload_0
    //   678: getfield startcolor : I
    //   681: iload #4
    //   683: invokestatic drawGradientSideways : (DDDDII)V
    //   686: aload_0
    //   687: getfield old : I
    //   690: i2f
    //   691: aload_0
    //   692: getfield y : I
    //   695: i2f
    //   696: ldc_w 13.2
    //   699: fadd
    //   700: aload_0
    //   701: getfield old : I
    //   704: aload_0
    //   705: getfield width : I
    //   708: iadd
    //   709: i2f
    //   710: aload_0
    //   711: getfield y : I
    //   714: aload_0
    //   715: getfield height : I
    //   718: iadd
    //   719: i2f
    //   720: fload #5
    //   722: fadd
    //   723: iconst_0
    //   724: iconst_0
    //   725: iconst_0
    //   726: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   729: getfield alphaBox : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   732: invokevirtual getValue : ()Ljava/lang/Object;
    //   735: checkcast java/lang/Integer
    //   738: invokevirtual intValue : ()I
    //   741: invokestatic toRGBA : (IIII)I
    //   744: invokestatic drawRect : (FFFFI)V
    //   747: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   750: getfield moduleIcon : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   753: invokevirtual getValue : ()Ljava/lang/Object;
    //   756: checkcast java/lang/Boolean
    //   759: invokevirtual booleanValue : ()Z
    //   762: ifeq -> 896
    //   765: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   768: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   771: invokevirtual getValue : ()Ljava/lang/Object;
    //   774: checkcast java/lang/Boolean
    //   777: invokevirtual booleanValue : ()Z
    //   780: ifne -> 799
    //   783: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   786: pop
    //   787: ldc_w 'CustomFont'
    //   790: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   793: invokevirtual isEnabled : ()Z
    //   796: ifne -> 815
    //   799: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   802: pop
    //   803: ldc_w 'CustomFont'
    //   806: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   809: invokevirtual isEnabled : ()Z
    //   812: ifne -> 855
    //   815: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   818: aload_0
    //   819: invokevirtual getName : ()Ljava/lang/String;
    //   822: aload_0
    //   823: getfield old : I
    //   826: i2f
    //   827: ldc_w 17.0
    //   830: fadd
    //   831: aload_0
    //   832: getfield y : I
    //   835: i2f
    //   836: ldc_w 4.0
    //   839: fsub
    //   840: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   843: invokevirtual getTextOffset : ()I
    //   846: i2f
    //   847: fsub
    //   848: iconst_m1
    //   849: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   852: goto -> 1024
    //   855: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   858: aload_0
    //   859: invokevirtual getName : ()Ljava/lang/String;
    //   862: aload_0
    //   863: getfield old : I
    //   866: i2f
    //   867: ldc_w 17.0
    //   870: fadd
    //   871: aload_0
    //   872: getfield y : I
    //   875: i2f
    //   876: ldc_w 3.0
    //   879: fsub
    //   880: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   883: invokevirtual getTextOffset : ()I
    //   886: i2f
    //   887: fsub
    //   888: iconst_m1
    //   889: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   892: pop
    //   893: goto -> 1024
    //   896: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   899: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   902: invokevirtual getValue : ()Ljava/lang/Object;
    //   905: checkcast java/lang/Boolean
    //   908: invokevirtual booleanValue : ()Z
    //   911: ifne -> 930
    //   914: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   917: pop
    //   918: ldc_w 'CustomFont'
    //   921: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   924: invokevirtual isEnabled : ()Z
    //   927: ifne -> 946
    //   930: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   933: pop
    //   934: ldc_w 'CustomFont'
    //   937: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   940: invokevirtual isEnabled : ()Z
    //   943: ifne -> 986
    //   946: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   949: aload_0
    //   950: invokevirtual getName : ()Ljava/lang/String;
    //   953: aload_0
    //   954: getfield old : I
    //   957: i2f
    //   958: ldc_w 3.0
    //   961: fadd
    //   962: aload_0
    //   963: getfield y : I
    //   966: i2f
    //   967: ldc_w 4.0
    //   970: fsub
    //   971: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   974: invokevirtual getTextOffset : ()I
    //   977: i2f
    //   978: fsub
    //   979: iconst_m1
    //   980: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   983: goto -> 1024
    //   986: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   989: aload_0
    //   990: invokevirtual getName : ()Ljava/lang/String;
    //   993: aload_0
    //   994: getfield old : I
    //   997: i2f
    //   998: ldc_w 3.0
    //   1001: fadd
    //   1002: aload_0
    //   1003: getfield y : I
    //   1006: i2f
    //   1007: ldc_w 3.0
    //   1010: fsub
    //   1011: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1014: invokevirtual getTextOffset : ()I
    //   1017: i2f
    //   1018: fsub
    //   1019: iconst_m1
    //   1020: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   1023: pop
    //   1024: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1027: getfield moduleIcon : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1030: invokevirtual getValue : ()Ljava/lang/Object;
    //   1033: checkcast java/lang/Boolean
    //   1036: invokevirtual booleanValue : ()Z
    //   1039: ifeq -> 1318
    //   1042: aload_0
    //   1043: invokevirtual getName : ()Ljava/lang/String;
    //   1046: ldc 'Combat'
    //   1048: if_acmpne -> 1088
    //   1051: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1054: ldc_w 'b'
    //   1057: aload_0
    //   1058: getfield old : I
    //   1061: i2f
    //   1062: ldc_w 3.0
    //   1065: fadd
    //   1066: aload_0
    //   1067: getfield y : I
    //   1070: i2f
    //   1071: ldc_w 4.0
    //   1074: fsub
    //   1075: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1078: invokevirtual getTextOffset : ()I
    //   1081: i2f
    //   1082: fsub
    //   1083: iconst_m1
    //   1084: invokevirtual drawStringlogo : (Ljava/lang/String;FFI)F
    //   1087: pop
    //   1088: aload_0
    //   1089: invokevirtual getName : ()Ljava/lang/String;
    //   1092: ldc 'Misc'
    //   1094: if_acmpne -> 1134
    //   1097: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1100: ldc_w '['
    //   1103: aload_0
    //   1104: getfield old : I
    //   1107: i2f
    //   1108: ldc_w 3.0
    //   1111: fadd
    //   1112: aload_0
    //   1113: getfield y : I
    //   1116: i2f
    //   1117: ldc_w 4.0
    //   1120: fsub
    //   1121: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1124: invokevirtual getTextOffset : ()I
    //   1127: i2f
    //   1128: fsub
    //   1129: iconst_m1
    //   1130: invokevirtual drawStringlogo : (Ljava/lang/String;FFI)F
    //   1133: pop
    //   1134: aload_0
    //   1135: invokevirtual getName : ()Ljava/lang/String;
    //   1138: ldc 'Render'
    //   1140: if_acmpne -> 1180
    //   1143: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1146: ldc_w 'a'
    //   1149: aload_0
    //   1150: getfield old : I
    //   1153: i2f
    //   1154: ldc_w 3.0
    //   1157: fadd
    //   1158: aload_0
    //   1159: getfield y : I
    //   1162: i2f
    //   1163: ldc_w 4.0
    //   1166: fsub
    //   1167: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1170: invokevirtual getTextOffset : ()I
    //   1173: i2f
    //   1174: fsub
    //   1175: iconst_m1
    //   1176: invokevirtual drawStringlogo : (Ljava/lang/String;FFI)F
    //   1179: pop
    //   1180: aload_0
    //   1181: invokevirtual getName : ()Ljava/lang/String;
    //   1184: ldc 'Movement'
    //   1186: if_acmpne -> 1226
    //   1189: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1192: ldc_w '8'
    //   1195: aload_0
    //   1196: getfield old : I
    //   1199: i2f
    //   1200: ldc_w 3.0
    //   1203: fadd
    //   1204: aload_0
    //   1205: getfield y : I
    //   1208: i2f
    //   1209: ldc_w 4.0
    //   1212: fsub
    //   1213: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1216: invokevirtual getTextOffset : ()I
    //   1219: i2f
    //   1220: fsub
    //   1221: iconst_m1
    //   1222: invokevirtual drawStringlogo : (Ljava/lang/String;FFI)F
    //   1225: pop
    //   1226: aload_0
    //   1227: invokevirtual getName : ()Ljava/lang/String;
    //   1230: ldc 'Player'
    //   1232: if_acmpne -> 1272
    //   1235: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1238: ldc_w '5'
    //   1241: aload_0
    //   1242: getfield old : I
    //   1245: i2f
    //   1246: ldc_w 3.0
    //   1249: fadd
    //   1250: aload_0
    //   1251: getfield y : I
    //   1254: i2f
    //   1255: ldc_w 4.0
    //   1258: fsub
    //   1259: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1262: invokevirtual getTextOffset : ()I
    //   1265: i2f
    //   1266: fsub
    //   1267: iconst_m1
    //   1268: invokevirtual drawStringlogo : (Ljava/lang/String;FFI)F
    //   1271: pop
    //   1272: aload_0
    //   1273: invokevirtual getName : ()Ljava/lang/String;
    //   1276: ldc 'Client'
    //   1278: if_acmpne -> 1318
    //   1281: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1284: ldc_w '7'
    //   1287: aload_0
    //   1288: getfield old : I
    //   1291: i2f
    //   1292: ldc_w 3.0
    //   1295: fadd
    //   1296: aload_0
    //   1297: getfield y : I
    //   1300: i2f
    //   1301: ldc_w 4.0
    //   1304: fsub
    //   1305: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1308: invokevirtual getTextOffset : ()I
    //   1311: i2f
    //   1312: fsub
    //   1313: iconst_m1
    //   1314: invokevirtual drawStringlogo : (Ljava/lang/String;FFI)F
    //   1317: pop
    //   1318: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1321: getfield moduleiconmode : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1324: invokevirtual getValue : ()Ljava/lang/Object;
    //   1327: checkcast java/lang/Integer
    //   1330: invokevirtual intValue : ()I
    //   1333: ifne -> 1708
    //   1336: aload_0
    //   1337: invokevirtual getName : ()Ljava/lang/String;
    //   1340: ldc 'Combat'
    //   1342: if_acmpne -> 1398
    //   1345: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1348: ldc_w '[23]'
    //   1351: aload_0
    //   1352: getfield old : I
    //   1355: i2f
    //   1356: ldc_w 66.0
    //   1359: fadd
    //   1360: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1363: getfield moduleWidth : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1366: invokevirtual getValue : ()Ljava/lang/Object;
    //   1369: checkcast java/lang/Integer
    //   1372: invokevirtual intValue : ()I
    //   1375: i2f
    //   1376: fadd
    //   1377: aload_0
    //   1378: getfield y : I
    //   1381: i2f
    //   1382: ldc_w 4.0
    //   1385: fsub
    //   1386: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1389: invokevirtual getTextOffset : ()I
    //   1392: i2f
    //   1393: fsub
    //   1394: iconst_m1
    //   1395: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   1398: aload_0
    //   1399: invokevirtual getName : ()Ljava/lang/String;
    //   1402: ldc 'Misc'
    //   1404: if_acmpne -> 1460
    //   1407: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1410: ldc_w '[18]'
    //   1413: aload_0
    //   1414: getfield old : I
    //   1417: i2f
    //   1418: ldc_w 66.0
    //   1421: fadd
    //   1422: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1425: getfield moduleWidth : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1428: invokevirtual getValue : ()Ljava/lang/Object;
    //   1431: checkcast java/lang/Integer
    //   1434: invokevirtual intValue : ()I
    //   1437: i2f
    //   1438: fadd
    //   1439: aload_0
    //   1440: getfield y : I
    //   1443: i2f
    //   1444: ldc_w 4.0
    //   1447: fsub
    //   1448: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1451: invokevirtual getTextOffset : ()I
    //   1454: i2f
    //   1455: fsub
    //   1456: iconst_m1
    //   1457: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   1460: aload_0
    //   1461: invokevirtual getName : ()Ljava/lang/String;
    //   1464: ldc 'Render'
    //   1466: if_acmpne -> 1522
    //   1469: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1472: ldc_w '[21]'
    //   1475: aload_0
    //   1476: getfield old : I
    //   1479: i2f
    //   1480: ldc_w 66.0
    //   1483: fadd
    //   1484: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1487: getfield moduleWidth : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1490: invokevirtual getValue : ()Ljava/lang/Object;
    //   1493: checkcast java/lang/Integer
    //   1496: invokevirtual intValue : ()I
    //   1499: i2f
    //   1500: fadd
    //   1501: aload_0
    //   1502: getfield y : I
    //   1505: i2f
    //   1506: ldc_w 4.0
    //   1509: fsub
    //   1510: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1513: invokevirtual getTextOffset : ()I
    //   1516: i2f
    //   1517: fsub
    //   1518: iconst_m1
    //   1519: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   1522: aload_0
    //   1523: invokevirtual getName : ()Ljava/lang/String;
    //   1526: ldc 'Movement'
    //   1528: if_acmpne -> 1584
    //   1531: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1534: ldc_w '[20]'
    //   1537: aload_0
    //   1538: getfield old : I
    //   1541: i2f
    //   1542: ldc_w 66.0
    //   1545: fadd
    //   1546: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1549: getfield moduleWidth : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1552: invokevirtual getValue : ()Ljava/lang/Object;
    //   1555: checkcast java/lang/Integer
    //   1558: invokevirtual intValue : ()I
    //   1561: i2f
    //   1562: fadd
    //   1563: aload_0
    //   1564: getfield y : I
    //   1567: i2f
    //   1568: ldc_w 4.0
    //   1571: fsub
    //   1572: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1575: invokevirtual getTextOffset : ()I
    //   1578: i2f
    //   1579: fsub
    //   1580: iconst_m1
    //   1581: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   1584: aload_0
    //   1585: invokevirtual getName : ()Ljava/lang/String;
    //   1588: ldc 'Player'
    //   1590: if_acmpne -> 1646
    //   1593: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1596: ldc_w '[18]'
    //   1599: aload_0
    //   1600: getfield old : I
    //   1603: i2f
    //   1604: ldc_w 66.0
    //   1607: fadd
    //   1608: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1611: getfield moduleWidth : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1614: invokevirtual getValue : ()Ljava/lang/Object;
    //   1617: checkcast java/lang/Integer
    //   1620: invokevirtual intValue : ()I
    //   1623: i2f
    //   1624: fadd
    //   1625: aload_0
    //   1626: getfield y : I
    //   1629: i2f
    //   1630: ldc_w 4.0
    //   1633: fsub
    //   1634: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1637: invokevirtual getTextOffset : ()I
    //   1640: i2f
    //   1641: fsub
    //   1642: iconst_m1
    //   1643: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   1646: aload_0
    //   1647: invokevirtual getName : ()Ljava/lang/String;
    //   1650: ldc 'Client'
    //   1652: if_acmpne -> 1708
    //   1655: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   1658: ldc_w '[13]'
    //   1661: aload_0
    //   1662: getfield old : I
    //   1665: i2f
    //   1666: ldc_w 66.0
    //   1669: fadd
    //   1670: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1673: getfield moduleWidth : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1676: invokevirtual getValue : ()Ljava/lang/Object;
    //   1679: checkcast java/lang/Integer
    //   1682: invokevirtual intValue : ()I
    //   1685: i2f
    //   1686: fadd
    //   1687: aload_0
    //   1688: getfield y : I
    //   1691: i2f
    //   1692: ldc_w 4.0
    //   1695: fsub
    //   1696: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   1699: invokevirtual getTextOffset : ()I
    //   1702: i2f
    //   1703: fsub
    //   1704: iconst_m1
    //   1705: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   1708: aload_0
    //   1709: getfield open : Z
    //   1712: ifne -> 1736
    //   1715: aload_0
    //   1716: getfield angle : I
    //   1719: ifle -> 1757
    //   1722: aload_0
    //   1723: dup
    //   1724: getfield angle : I
    //   1727: bipush #6
    //   1729: isub
    //   1730: putfield angle : I
    //   1733: goto -> 1757
    //   1736: aload_0
    //   1737: getfield angle : I
    //   1740: sipush #180
    //   1743: if_icmpge -> 1757
    //   1746: aload_0
    //   1747: dup
    //   1748: getfield angle : I
    //   1751: bipush #6
    //   1753: iadd
    //   1754: putfield angle : I
    //   1757: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1760: getfield moduleiconmode : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1763: invokevirtual getValue : ()Ljava/lang/Object;
    //   1766: checkcast java/lang/Integer
    //   1769: invokevirtual intValue : ()I
    //   1772: iconst_1
    //   1773: if_icmpne -> 1896
    //   1776: invokestatic pushMatrix : ()V
    //   1779: invokestatic enableBlend : ()V
    //   1782: new java/awt/Color
    //   1785: dup
    //   1786: sipush #255
    //   1789: sipush #255
    //   1792: sipush #255
    //   1795: sipush #255
    //   1798: invokespecial <init> : (IIII)V
    //   1801: invokestatic glColor : (Ljava/awt/Color;)V
    //   1804: aload_0
    //   1805: getfield minecraft : Lnet/minecraft/client/Minecraft;
    //   1808: invokevirtual getTextureManager : ()Lnet/minecraft/client/renderer/texture/TextureManager;
    //   1811: new net/minecraft/util/ResourceLocation
    //   1814: dup
    //   1815: ldc_w 'textures/arrow.png'
    //   1818: invokespecial <init> : (Ljava/lang/String;)V
    //   1821: invokevirtual bindTexture : (Lnet/minecraft/util/ResourceLocation;)V
    //   1824: aload_0
    //   1825: invokevirtual getX : ()I
    //   1828: aload_0
    //   1829: invokevirtual getWidth : ()I
    //   1832: iadd
    //   1833: bipush #7
    //   1835: isub
    //   1836: i2f
    //   1837: aload_0
    //   1838: invokevirtual getY : ()I
    //   1841: bipush #6
    //   1843: iadd
    //   1844: i2f
    //   1845: ldc_w 0.3
    //   1848: fsub
    //   1849: fconst_0
    //   1850: invokestatic translate : (FFF)V
    //   1853: aload_0
    //   1854: getfield angle : I
    //   1857: i2f
    //   1858: invokestatic calculateRotation : (F)F
    //   1861: fconst_0
    //   1862: fconst_0
    //   1863: fconst_1
    //   1864: invokestatic rotate : (FFFF)V
    //   1867: bipush #-5
    //   1869: bipush #-5
    //   1871: fconst_0
    //   1872: fconst_0
    //   1873: bipush #10
    //   1875: bipush #10
    //   1877: bipush #10
    //   1879: bipush #10
    //   1881: ldc_w 10.0
    //   1884: ldc_w 10.0
    //   1887: invokestatic drawModalRect : (IIFFIIIIFF)V
    //   1890: invokestatic disableBlend : ()V
    //   1893: invokestatic popMatrix : ()V
    //   1896: aload_0
    //   1897: getfield open : Z
    //   1900: ifeq -> 2168
    //   1903: aload_0
    //   1904: getfield old : I
    //   1907: i2f
    //   1908: aload_0
    //   1909: getfield y : I
    //   1912: i2f
    //   1913: ldc_w 12.5
    //   1916: fadd
    //   1917: aload_0
    //   1918: getfield old : I
    //   1921: aload_0
    //   1922: getfield width : I
    //   1925: iadd
    //   1926: i2f
    //   1927: aload_0
    //   1928: getfield y : I
    //   1931: aload_0
    //   1932: getfield height : I
    //   1935: iadd
    //   1936: i2f
    //   1937: fload #5
    //   1939: fadd
    //   1940: ldc_w 1996488704
    //   1943: invokestatic drawRect : (FFFFI)V
    //   1946: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   1949: getfield outline : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   1952: invokevirtual getValue : ()Ljava/lang/Object;
    //   1955: checkcast java/lang/Boolean
    //   1958: invokevirtual booleanValue : ()Z
    //   1961: ifeq -> 2168
    //   1964: invokestatic disableTexture2D : ()V
    //   1967: invokestatic enableBlend : ()V
    //   1970: invokestatic disableAlpha : ()V
    //   1973: getstatic net/minecraft/client/renderer/GlStateManager$SourceFactor.SRC_ALPHA : Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;
    //   1976: getstatic net/minecraft/client/renderer/GlStateManager$DestFactor.ONE_MINUS_SRC_ALPHA : Lnet/minecraft/client/renderer/GlStateManager$DestFactor;
    //   1979: getstatic net/minecraft/client/renderer/GlStateManager$SourceFactor.ONE : Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;
    //   1982: getstatic net/minecraft/client/renderer/GlStateManager$DestFactor.ZERO : Lnet/minecraft/client/renderer/GlStateManager$DestFactor;
    //   1985: invokestatic tryBlendFuncSeparate : (Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;Lnet/minecraft/client/renderer/GlStateManager$DestFactor;Lnet/minecraft/client/renderer/GlStateManager$SourceFactor;Lnet/minecraft/client/renderer/GlStateManager$DestFactor;)V
    //   1988: sipush #7425
    //   1991: invokestatic shadeModel : (I)V
    //   1994: iconst_2
    //   1995: invokestatic glBegin : (I)V
    //   1998: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   2001: getfield red : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   2004: invokevirtual getValue : ()Ljava/lang/Object;
    //   2007: checkcast java/lang/Integer
    //   2010: invokevirtual intValue : ()I
    //   2013: i2f
    //   2014: ldc 255.0
    //   2016: fdiv
    //   2017: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   2020: getfield green : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   2023: invokevirtual getValue : ()Ljava/lang/Object;
    //   2026: checkcast java/lang/Integer
    //   2029: invokevirtual intValue : ()I
    //   2032: i2f
    //   2033: ldc 255.0
    //   2035: fdiv
    //   2036: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   2039: getfield blue : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   2042: invokevirtual getValue : ()Ljava/lang/Object;
    //   2045: checkcast java/lang/Integer
    //   2048: invokevirtual intValue : ()I
    //   2051: i2f
    //   2052: ldc 255.0
    //   2054: fdiv
    //   2055: ldc 255.0
    //   2057: invokestatic glColor4f : (FFFF)V
    //   2060: aload_0
    //   2061: getfield old : I
    //   2064: i2f
    //   2065: aload_0
    //   2066: getfield y : I
    //   2069: i2f
    //   2070: ldc_w 0.5
    //   2073: fsub
    //   2074: fconst_0
    //   2075: invokestatic glVertex3f : (FFF)V
    //   2078: aload_0
    //   2079: getfield old : I
    //   2082: aload_0
    //   2083: getfield width : I
    //   2086: iadd
    //   2087: i2f
    //   2088: aload_0
    //   2089: getfield y : I
    //   2092: i2f
    //   2093: ldc_w 0.5
    //   2096: fsub
    //   2097: fconst_0
    //   2098: invokestatic glVertex3f : (FFF)V
    //   2101: aload_0
    //   2102: getfield old : I
    //   2105: aload_0
    //   2106: getfield width : I
    //   2109: iadd
    //   2110: i2f
    //   2111: aload_0
    //   2112: getfield y : I
    //   2115: aload_0
    //   2116: getfield height : I
    //   2119: iadd
    //   2120: i2f
    //   2121: fload #5
    //   2123: fadd
    //   2124: fconst_0
    //   2125: invokestatic glVertex3f : (FFF)V
    //   2128: aload_0
    //   2129: getfield old : I
    //   2132: i2f
    //   2133: aload_0
    //   2134: getfield y : I
    //   2137: aload_0
    //   2138: getfield height : I
    //   2141: iadd
    //   2142: i2f
    //   2143: fload #5
    //   2145: fadd
    //   2146: fconst_0
    //   2147: invokestatic glVertex3f : (FFF)V
    //   2150: invokestatic glEnd : ()V
    //   2153: sipush #7424
    //   2156: invokestatic shadeModel : (I)V
    //   2159: invokestatic disableBlend : ()V
    //   2162: invokestatic enableAlpha : ()V
    //   2165: invokestatic enableTexture2D : ()V
    //   2168: aload_0
    //   2169: getfield open : Z
    //   2172: ifeq -> 2296
    //   2175: aload_0
    //   2176: invokevirtual getY : ()I
    //   2179: aload_0
    //   2180: invokevirtual getHeight : ()I
    //   2183: iadd
    //   2184: i2f
    //   2185: ldc_w 3.0
    //   2188: fsub
    //   2189: fstore #7
    //   2191: aload_0
    //   2192: invokevirtual getItems : ()Ljava/util/ArrayList;
    //   2195: invokevirtual iterator : ()Ljava/util/Iterator;
    //   2198: astore #8
    //   2200: aload #8
    //   2202: invokeinterface hasNext : ()Z
    //   2207: ifeq -> 2296
    //   2210: aload #8
    //   2212: invokeinterface next : ()Ljava/lang/Object;
    //   2217: checkcast dev/madcat/m3dc3t/features/gui/components/items/Item
    //   2220: astore #9
    //   2222: getstatic dev/madcat/m3dc3t/features/gui/components/Component.counter1 : [I
    //   2225: iconst_0
    //   2226: getstatic dev/madcat/m3dc3t/features/gui/components/Component.counter1 : [I
    //   2229: iconst_0
    //   2230: iaload
    //   2231: iconst_1
    //   2232: iadd
    //   2233: iastore
    //   2234: aload #9
    //   2236: invokevirtual isHidden : ()Z
    //   2239: ifeq -> 2245
    //   2242: goto -> 2200
    //   2245: aload #9
    //   2247: aload_0
    //   2248: getfield old : I
    //   2251: i2f
    //   2252: fconst_2
    //   2253: fadd
    //   2254: fload #7
    //   2256: invokevirtual setLocation : (FF)V
    //   2259: aload #9
    //   2261: aload_0
    //   2262: invokevirtual getWidth : ()I
    //   2265: iconst_4
    //   2266: isub
    //   2267: invokevirtual setWidth : (I)V
    //   2270: aload #9
    //   2272: iload_1
    //   2273: iload_2
    //   2274: fload_3
    //   2275: invokevirtual drawScreen : (IIF)V
    //   2278: fload #7
    //   2280: aload #9
    //   2282: invokevirtual getHeight : ()I
    //   2285: i2f
    //   2286: ldc_w 1.5
    //   2289: fadd
    //   2290: fadd
    //   2291: fstore #7
    //   2293: goto -> 2200
    //   2296: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #88	-> 0
    //   #89	-> 8
    //   #90	-> 17
    //   #91	-> 43
    //   #92	-> 52
    //   #93	-> 78
    //   #94	-> 87
    //   #95	-> 113
    //   #96	-> 122
    //   #97	-> 148
    //   #98	-> 157
    //   #99	-> 183
    //   #100	-> 192
    //   #101	-> 219
    //   #103	-> 241
    //   #104	-> 247
    //   #105	-> 257
    //   #106	-> 276
    //   #107	-> 280
    //   #108	-> 298
    //   #109	-> 313
    //   #110	-> 338
    //   #113	-> 364
    //   #115	-> 431
    //   #116	-> 496
    //   #117	-> 582
    //   #118	-> 628
    //   #119	-> 635
    //   #120	-> 686
    //   #122	-> 747
    //   #123	-> 765
    //   #124	-> 815
    //   #126	-> 855
    //   #129	-> 896
    //   #130	-> 946
    //   #132	-> 986
    //   #135	-> 1024
    //   #136	-> 1042
    //   #137	-> 1051
    //   #138	-> 1088
    //   #139	-> 1097
    //   #140	-> 1134
    //   #141	-> 1143
    //   #142	-> 1180
    //   #143	-> 1189
    //   #144	-> 1226
    //   #145	-> 1235
    //   #146	-> 1272
    //   #147	-> 1281
    //   #149	-> 1318
    //   #150	-> 1336
    //   #151	-> 1345
    //   #152	-> 1398
    //   #153	-> 1407
    //   #154	-> 1460
    //   #155	-> 1469
    //   #156	-> 1522
    //   #157	-> 1531
    //   #158	-> 1584
    //   #159	-> 1593
    //   #160	-> 1646
    //   #161	-> 1655
    //   #163	-> 1708
    //   #164	-> 1715
    //   #165	-> 1722
    //   #167	-> 1736
    //   #168	-> 1746
    //   #170	-> 1757
    //   #171	-> 1776
    //   #172	-> 1779
    //   #173	-> 1782
    //   #174	-> 1804
    //   #175	-> 1824
    //   #176	-> 1853
    //   #177	-> 1867
    //   #178	-> 1890
    //   #179	-> 1893
    //   #181	-> 1896
    //   #182	-> 1903
    //   #183	-> 1946
    //   #184	-> 1964
    //   #185	-> 1967
    //   #186	-> 1970
    //   #187	-> 1973
    //   #188	-> 1988
    //   #189	-> 1994
    //   #190	-> 1998
    //   #191	-> 2060
    //   #192	-> 2078
    //   #193	-> 2101
    //   #194	-> 2128
    //   #195	-> 2150
    //   #196	-> 2153
    //   #197	-> 2159
    //   #198	-> 2162
    //   #199	-> 2165
    //   #202	-> 2168
    //   #203	-> 2175
    //   #204	-> 2191
    //   #205	-> 2222
    //   #206	-> 2234
    //   #207	-> 2245
    //   #208	-> 2259
    //   #209	-> 2270
    //   #210	-> 2278
    //   #211	-> 2293
    //   #213	-> 2296
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   361	3	4	endcolor	I
    //   2222	71	9	item	Ldev/madcat/m3dc3t/features/gui/components/items/Item;
    //   2191	105	7	y	F
    //   0	2297	0	this	Ldev/madcat/m3dc3t/features/gui/components/Component;
    //   0	2297	1	mouseX	I
    //   0	2297	2	mouseY	I
    //   0	2297	3	partialTicks	F
    //   496	1801	4	endcolor	I
    //   276	2021	5	totalItemHeight	F
    //   280	2017	6	f	F
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    if (mouseButton == 0 && isHovering(mouseX, mouseY)) {
      this.x2 = this.old - mouseX;
      this.y2 = this.y - mouseY;
      M3dC3tGui.getClickGui().getComponents().forEach(component -> {
            if (component.drag)
              component.drag = false; 
          });
      this.drag = true;
      return;
    } 
    if (mouseButton == 1 && isHovering(mouseX, mouseY)) {
      this.open = !this.open;
      mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      return;
    } 
    if (!this.open)
      return; 
    getItems().forEach(item -> item.mouseClicked(mouseX, mouseY, mouseButton));
  }
  
  public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
    if (releaseButton == 0)
      this.drag = false; 
    if (!this.open)
      return; 
    getItems().forEach(item -> item.mouseReleased(mouseX, mouseY, releaseButton));
  }
  
  public void onKeyTyped(char typedChar, int keyCode) {
    if (!this.open)
      return; 
    getItems().forEach(item -> item.onKeyTyped(typedChar, keyCode));
  }
  
  public void addButton(Button button) {
    this.items.add(button);
  }
  
  public int getX() {
    return this.old;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public void setWidth(int width) {
    this.width = width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public void setHeight(int height) {
    this.height = height;
  }
  
  public boolean isHidden() {
    return this.hidden;
  }
  
  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }
  
  public boolean isOpen() {
    return this.open;
  }
  
  public final ArrayList<Item> getItems() {
    return this.items;
  }
  
  private boolean isHovering(int mouseX, int mouseY) {
    return (mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight() - (this.open ? 2 : 0));
  }
  
  private float getTotalItemHeight() {
    float height = 0.0F;
    for (Item item : getItems())
      height += item.getHeight() + 1.5F; 
    return height;
  }
}
