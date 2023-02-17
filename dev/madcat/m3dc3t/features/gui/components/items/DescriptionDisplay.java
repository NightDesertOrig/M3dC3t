package dev.madcat.m3dc3t.features.gui.components.items;

public class DescriptionDisplay extends Item {
  private String description;
  
  private boolean draw;
  
  public DescriptionDisplay(String description, float x, float y) {
    // Byte code:
    //   0: aload_0
    //   1: ldc 'DescriptionDisplay'
    //   3: invokespecial <init> : (Ljava/lang/String;)V
    //   6: aload_0
    //   7: aload_1
    //   8: putfield description : Ljava/lang/String;
    //   11: aload_0
    //   12: fload_2
    //   13: fload_3
    //   14: invokevirtual setLocation : (FF)V
    //   17: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   20: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   23: invokevirtual getValue : ()Ljava/lang/Object;
    //   26: checkcast java/lang/Boolean
    //   29: invokevirtual booleanValue : ()Z
    //   32: ifne -> 50
    //   35: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   38: pop
    //   39: ldc 'CustomFont'
    //   41: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   44: invokevirtual isEnabled : ()Z
    //   47: ifne -> 65
    //   50: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   53: pop
    //   54: ldc 'CustomFont'
    //   56: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   59: invokevirtual isEnabled : ()Z
    //   62: ifne -> 84
    //   65: aload_0
    //   66: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   69: aload_0
    //   70: getfield description : Ljava/lang/String;
    //   73: invokevirtual getStringWidth : (Ljava/lang/String;)I
    //   76: iconst_4
    //   77: iadd
    //   78: putfield width : I
    //   81: goto -> 100
    //   84: aload_0
    //   85: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   88: aload_0
    //   89: getfield description : Ljava/lang/String;
    //   92: invokevirtual getStringCWidth : (Ljava/lang/String;)I
    //   95: iconst_4
    //   96: iadd
    //   97: putfield width : I
    //   100: aload_0
    //   101: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   104: invokevirtual getFontHeight : ()I
    //   107: iconst_4
    //   108: iadd
    //   109: putfield height : I
    //   112: aload_0
    //   113: iconst_0
    //   114: putfield draw : Z
    //   117: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #16	-> 0
    //   #17	-> 6
    //   #18	-> 11
    //   #19	-> 17
    //   #20	-> 65
    //   #22	-> 84
    //   #24	-> 100
    //   #25	-> 112
    //   #26	-> 117
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	118	0	this	Ldev/madcat/m3dc3t/features/gui/components/items/DescriptionDisplay;
    //   0	118	1	description	Ljava/lang/String;
    //   0	118	2	x	F
    //   0	118	3	y	F
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    // Byte code:
    //   0: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   3: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   6: invokevirtual getValue : ()Ljava/lang/Object;
    //   9: checkcast java/lang/Boolean
    //   12: invokevirtual booleanValue : ()Z
    //   15: ifne -> 33
    //   18: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   21: pop
    //   22: ldc 'CustomFont'
    //   24: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   27: invokevirtual isEnabled : ()Z
    //   30: ifne -> 48
    //   33: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   36: pop
    //   37: ldc 'CustomFont'
    //   39: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   42: invokevirtual isEnabled : ()Z
    //   45: ifne -> 67
    //   48: aload_0
    //   49: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   52: aload_0
    //   53: getfield description : Ljava/lang/String;
    //   56: invokevirtual getStringWidth : (Ljava/lang/String;)I
    //   59: iconst_4
    //   60: iadd
    //   61: putfield width : I
    //   64: goto -> 83
    //   67: aload_0
    //   68: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   71: aload_0
    //   72: getfield description : Ljava/lang/String;
    //   75: invokevirtual getStringCWidth : (Ljava/lang/String;)I
    //   78: iconst_5
    //   79: iadd
    //   80: putfield width : I
    //   83: aload_0
    //   84: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   87: invokevirtual getFontHeight : ()I
    //   90: iconst_4
    //   91: iadd
    //   92: putfield height : I
    //   95: aload_0
    //   96: getfield x : F
    //   99: aload_0
    //   100: getfield y : F
    //   103: aload_0
    //   104: getfield x : F
    //   107: aload_0
    //   108: getfield width : I
    //   111: i2f
    //   112: fadd
    //   113: aload_0
    //   114: getfield y : F
    //   117: aload_0
    //   118: getfield height : I
    //   121: i2f
    //   122: fadd
    //   123: ldc -704643072
    //   125: invokestatic drawRect : (FFFFI)V
    //   128: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   131: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   134: invokevirtual getValue : ()Ljava/lang/Object;
    //   137: checkcast java/lang/Boolean
    //   140: invokevirtual booleanValue : ()Z
    //   143: ifne -> 161
    //   146: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   149: pop
    //   150: ldc 'CustomFont'
    //   152: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   155: invokevirtual isEnabled : ()Z
    //   158: ifne -> 176
    //   161: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   164: pop
    //   165: ldc 'CustomFont'
    //   167: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   170: invokevirtual isEnabled : ()Z
    //   173: ifne -> 205
    //   176: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   179: aload_0
    //   180: getfield description : Ljava/lang/String;
    //   183: aload_0
    //   184: getfield x : F
    //   187: fconst_2
    //   188: fadd
    //   189: aload_0
    //   190: getfield y : F
    //   193: fconst_2
    //   194: fadd
    //   195: ldc 16777215
    //   197: iconst_1
    //   198: invokevirtual drawString : (Ljava/lang/String;FFIZ)F
    //   201: pop
    //   202: goto -> 231
    //   205: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   208: aload_0
    //   209: getfield description : Ljava/lang/String;
    //   212: aload_0
    //   213: getfield x : F
    //   216: fconst_2
    //   217: fadd
    //   218: aload_0
    //   219: getfield y : F
    //   222: ldc 3.0
    //   224: fadd
    //   225: ldc 16777215
    //   227: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   230: pop
    //   231: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #30	-> 0
    //   #31	-> 48
    //   #33	-> 67
    //   #35	-> 83
    //   #36	-> 95
    //   #37	-> 128
    //   #38	-> 176
    //   #40	-> 205
    //   #42	-> 231
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	232	0	this	Ldev/madcat/m3dc3t/features/gui/components/items/DescriptionDisplay;
    //   0	232	1	mouseX	I
    //   0	232	2	mouseY	I
    //   0	232	3	partialTicks	F
  }
  
  public boolean shouldDraw() {
    return this.draw;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public void setDraw(boolean draw) {
    this.draw = draw;
  }
}
