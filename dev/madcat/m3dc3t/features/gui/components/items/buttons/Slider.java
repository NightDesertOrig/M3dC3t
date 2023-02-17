package dev.madcat.m3dc3t.features.gui.components.items.buttons;

import dev.madcat.m3dc3t.features.gui.M3dC3tGui;
import dev.madcat.m3dc3t.features.gui.components.Component;
import dev.madcat.m3dc3t.features.setting.Setting;
import org.lwjgl.input.Mouse;

public class Slider extends Button {
  private final Number min;
  
  private final Number max;
  
  private final int difference;
  
  public Setting setting;
  
  public Slider(Setting setting) {
    super(setting.getName());
    this.setting = setting;
    this.min = (Number)setting.getMin();
    this.max = (Number)setting.getMax();
    this.difference = this.max.intValue() - this.min.intValue();
    this.width = 15;
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: iload_2
    //   3: invokespecial dragSetting : (II)V
    //   6: aload_0
    //   7: getfield x : F
    //   10: aload_0
    //   11: getfield y : F
    //   14: aload_0
    //   15: getfield x : F
    //   18: aload_0
    //   19: getfield width : I
    //   22: i2f
    //   23: fadd
    //   24: ldc 7.4
    //   26: fadd
    //   27: aload_0
    //   28: getfield y : F
    //   31: aload_0
    //   32: getfield height : I
    //   35: i2f
    //   36: fadd
    //   37: ldc 0.5
    //   39: fsub
    //   40: aload_0
    //   41: iload_1
    //   42: iload_2
    //   43: invokevirtual isHovering : (II)Z
    //   46: ifne -> 54
    //   49: ldc 290805077
    //   51: goto -> 56
    //   54: ldc -2007673515
    //   56: invokestatic drawRect : (FFFFI)V
    //   59: aload_0
    //   60: getfield x : F
    //   63: aload_0
    //   64: getfield y : F
    //   67: aload_0
    //   68: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   71: invokevirtual getValue : ()Ljava/lang/Object;
    //   74: checkcast java/lang/Number
    //   77: invokevirtual floatValue : ()F
    //   80: aload_0
    //   81: getfield min : Ljava/lang/Number;
    //   84: invokevirtual floatValue : ()F
    //   87: fcmpg
    //   88: ifgt -> 98
    //   91: aload_0
    //   92: getfield x : F
    //   95: goto -> 116
    //   98: aload_0
    //   99: getfield x : F
    //   102: aload_0
    //   103: getfield width : I
    //   106: i2f
    //   107: ldc 7.4
    //   109: fadd
    //   110: aload_0
    //   111: invokespecial partialMultiplier : ()F
    //   114: fmul
    //   115: fadd
    //   116: aload_0
    //   117: getfield y : F
    //   120: aload_0
    //   121: getfield height : I
    //   124: i2f
    //   125: fadd
    //   126: ldc 0.5
    //   128: fsub
    //   129: aload_0
    //   130: iload_1
    //   131: iload_2
    //   132: invokevirtual isHovering : (II)Z
    //   135: ifne -> 171
    //   138: getstatic dev/madcat/m3dc3t/M3dC3t.colorManager : Ldev/madcat/m3dc3t/manager/ColorManager;
    //   141: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   144: pop
    //   145: ldc dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   147: invokestatic getModuleByClass : (Ljava/lang/Class;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   150: checkcast dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   153: getfield hoverAlpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   156: invokevirtual getValue : ()Ljava/lang/Object;
    //   159: checkcast java/lang/Integer
    //   162: invokevirtual intValue : ()I
    //   165: invokevirtual getColorWithAlpha : (I)I
    //   168: goto -> 201
    //   171: getstatic dev/madcat/m3dc3t/M3dC3t.colorManager : Ldev/madcat/m3dc3t/manager/ColorManager;
    //   174: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   177: pop
    //   178: ldc dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   180: invokestatic getModuleByClass : (Ljava/lang/Class;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   183: checkcast dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   186: getfield alpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   189: invokevirtual getValue : ()Ljava/lang/Object;
    //   192: checkcast java/lang/Integer
    //   195: invokevirtual intValue : ()I
    //   198: invokevirtual getColorWithAlpha : (I)I
    //   201: invokestatic drawRect : (FFFFI)V
    //   204: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   207: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   210: invokevirtual getValue : ()Ljava/lang/Object;
    //   213: checkcast java/lang/Boolean
    //   216: invokevirtual booleanValue : ()Z
    //   219: ifne -> 237
    //   222: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   225: pop
    //   226: ldc 'CustomFont'
    //   228: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   231: invokevirtual isEnabled : ()Z
    //   234: ifne -> 252
    //   237: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   240: pop
    //   241: ldc 'CustomFont'
    //   243: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   246: invokevirtual isEnabled : ()Z
    //   249: ifne -> 354
    //   252: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   255: new java/lang/StringBuilder
    //   258: dup
    //   259: invokespecial <init> : ()V
    //   262: aload_0
    //   263: invokevirtual getName : ()Ljava/lang/String;
    //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: ldc ' '
    //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: getstatic com/mojang/realmsclient/gui/ChatFormatting.GRAY : Lcom/mojang/realmsclient/gui/ChatFormatting;
    //   277: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   280: aload_0
    //   281: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   284: invokevirtual getValue : ()Ljava/lang/Object;
    //   287: instanceof java/lang/Float
    //   290: ifeq -> 303
    //   293: aload_0
    //   294: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   297: invokevirtual getValue : ()Ljava/lang/Object;
    //   300: goto -> 319
    //   303: aload_0
    //   304: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   307: invokevirtual getValue : ()Ljava/lang/Object;
    //   310: checkcast java/lang/Number
    //   313: invokevirtual doubleValue : ()D
    //   316: invokestatic valueOf : (D)Ljava/lang/Double;
    //   319: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   322: invokevirtual toString : ()Ljava/lang/String;
    //   325: aload_0
    //   326: getfield x : F
    //   329: ldc 2.3
    //   331: fadd
    //   332: aload_0
    //   333: getfield y : F
    //   336: ldc 1.7
    //   338: fsub
    //   339: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   342: invokevirtual getTextOffset : ()I
    //   345: i2f
    //   346: fsub
    //   347: iconst_m1
    //   348: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   351: goto -> 454
    //   354: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   357: new java/lang/StringBuilder
    //   360: dup
    //   361: invokespecial <init> : ()V
    //   364: aload_0
    //   365: invokevirtual getName : ()Ljava/lang/String;
    //   368: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: ldc ' '
    //   373: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   376: getstatic com/mojang/realmsclient/gui/ChatFormatting.GRAY : Lcom/mojang/realmsclient/gui/ChatFormatting;
    //   379: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   382: aload_0
    //   383: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   386: invokevirtual getValue : ()Ljava/lang/Object;
    //   389: instanceof java/lang/Float
    //   392: ifeq -> 405
    //   395: aload_0
    //   396: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   399: invokevirtual getValue : ()Ljava/lang/Object;
    //   402: goto -> 421
    //   405: aload_0
    //   406: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   409: invokevirtual getValue : ()Ljava/lang/Object;
    //   412: checkcast java/lang/Number
    //   415: invokevirtual doubleValue : ()D
    //   418: invokestatic valueOf : (D)Ljava/lang/Double;
    //   421: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   424: invokevirtual toString : ()Ljava/lang/String;
    //   427: aload_0
    //   428: getfield x : F
    //   431: ldc 2.3
    //   433: fadd
    //   434: aload_0
    //   435: getfield y : F
    //   438: ldc 0.7
    //   440: fsub
    //   441: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   444: invokevirtual getTextOffset : ()I
    //   447: i2f
    //   448: fsub
    //   449: iconst_m1
    //   450: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   453: pop
    //   454: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #31	-> 0
    //   #32	-> 6
    //   #33	-> 59
    //   #34	-> 204
    //   #35	-> 252
    //   #37	-> 354
    //   #39	-> 454
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	455	0	this	Ldev/madcat/m3dc3t/features/gui/components/items/buttons/Slider;
    //   0	455	1	mouseX	I
    //   0	455	2	mouseY	I
    //   0	455	3	partialTicks	F
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (isHovering(mouseX, mouseY))
      setSettingFromX(mouseX); 
  }
  
  public boolean isHovering(int mouseX, int mouseY) {
    for (Component component : M3dC3tGui.getClickGui().getComponents()) {
      if (!component.drag)
        continue; 
      return false;
    } 
    return (mouseX >= getX() && mouseX <= getX() + getWidth() + 8.0F && mouseY >= getY() && mouseY <= getY() + this.height);
  }
  
  public void update() {
    setHidden(!this.setting.isVisible());
  }
  
  private void dragSetting(int mouseX, int mouseY) {
    if (isHovering(mouseX, mouseY) && Mouse.isButtonDown(0))
      setSettingFromX(mouseX); 
  }
  
  public int getHeight() {
    return 14;
  }
  
  private void setSettingFromX(int mouseX) {
    float percent = (mouseX - this.x) / (this.width + 7.4F);
    if (this.setting.getValue() instanceof Double) {
      double result = ((Double)this.setting.getMin()).doubleValue() + (this.difference * percent);
      this.setting.setValue(Double.valueOf(Math.round(10.0D * result) / 10.0D));
    } else if (this.setting.getValue() instanceof Float) {
      float result = ((Float)this.setting.getMin()).floatValue() + this.difference * percent;
      this.setting.setValue(Float.valueOf(Math.round(10.0F * result) / 10.0F));
    } else if (this.setting.getValue() instanceof Integer) {
      this.setting.setValue(Integer.valueOf(((Integer)this.setting.getMin()).intValue() + (int)(this.difference * percent)));
    } 
  }
  
  private float middle() {
    return this.max.floatValue() - this.min.floatValue();
  }
  
  private float part() {
    return ((Number)this.setting.getValue()).floatValue() - this.min.floatValue();
  }
  
  private float partialMultiplier() {
    return part() / middle();
  }
}
