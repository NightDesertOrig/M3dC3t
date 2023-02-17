//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui.components.items.buttons;

import dev.madcat.m3dc3t.features.setting.Bind;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class BindButton extends Button {
  private final Setting setting;
  
  public boolean isListening;
  
  public BindButton(Setting setting) {
    super(setting.getName());
    this.setting = setting;
    this.width = 15;
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    // Byte code:
    //   0: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   3: getfield red : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   6: invokevirtual getValue : ()Ljava/lang/Object;
    //   9: checkcast java/lang/Integer
    //   12: invokevirtual intValue : ()I
    //   15: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   18: getfield green : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   21: invokevirtual getValue : ()Ljava/lang/Object;
    //   24: checkcast java/lang/Integer
    //   27: invokevirtual intValue : ()I
    //   30: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/ClickGui;
    //   33: getfield blue : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   36: invokevirtual getValue : ()Ljava/lang/Object;
    //   39: checkcast java/lang/Integer
    //   42: invokevirtual intValue : ()I
    //   45: sipush #255
    //   48: invokestatic toARGB : (IIII)I
    //   51: istore #4
    //   53: aload_0
    //   54: getfield x : F
    //   57: aload_0
    //   58: getfield y : F
    //   61: aload_0
    //   62: getfield x : F
    //   65: aload_0
    //   66: getfield width : I
    //   69: i2f
    //   70: fadd
    //   71: ldc 7.4
    //   73: fadd
    //   74: aload_0
    //   75: getfield y : F
    //   78: aload_0
    //   79: getfield height : I
    //   82: i2f
    //   83: fadd
    //   84: ldc 0.5
    //   86: fsub
    //   87: aload_0
    //   88: invokevirtual getState : ()Z
    //   91: ifeq -> 113
    //   94: aload_0
    //   95: iload_1
    //   96: iload_2
    //   97: invokevirtual isHovering : (II)Z
    //   100: ifne -> 108
    //   103: ldc 290805077
    //   105: goto -> 185
    //   108: ldc -2007673515
    //   110: goto -> 185
    //   113: aload_0
    //   114: iload_1
    //   115: iload_2
    //   116: invokevirtual isHovering : (II)Z
    //   119: ifne -> 155
    //   122: getstatic dev/madcat/m3dc3t/M3dC3t.colorManager : Ldev/madcat/m3dc3t/manager/ColorManager;
    //   125: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   128: pop
    //   129: ldc dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   131: invokestatic getModuleByClass : (Ljava/lang/Class;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   134: checkcast dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   137: getfield hoverAlpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   140: invokevirtual getValue : ()Ljava/lang/Object;
    //   143: checkcast java/lang/Integer
    //   146: invokevirtual intValue : ()I
    //   149: invokevirtual getColorWithAlpha : (I)I
    //   152: goto -> 185
    //   155: getstatic dev/madcat/m3dc3t/M3dC3t.colorManager : Ldev/madcat/m3dc3t/manager/ColorManager;
    //   158: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   161: pop
    //   162: ldc dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   164: invokestatic getModuleByClass : (Ljava/lang/Class;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   167: checkcast dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   170: getfield alpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   173: invokevirtual getValue : ()Ljava/lang/Object;
    //   176: checkcast java/lang/Integer
    //   179: invokevirtual intValue : ()I
    //   182: invokevirtual getColorWithAlpha : (I)I
    //   185: invokestatic drawRect : (FFFFI)V
    //   188: aload_0
    //   189: getfield isListening : Z
    //   192: ifeq -> 312
    //   195: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   198: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   201: invokevirtual getValue : ()Ljava/lang/Object;
    //   204: checkcast java/lang/Boolean
    //   207: invokevirtual booleanValue : ()Z
    //   210: ifne -> 228
    //   213: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   216: pop
    //   217: ldc 'CustomFont'
    //   219: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   222: invokevirtual isEnabled : ()Z
    //   225: ifne -> 243
    //   228: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   231: pop
    //   232: ldc 'CustomFont'
    //   234: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   237: invokevirtual isEnabled : ()Z
    //   240: ifne -> 277
    //   243: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   246: ldc 'Press a Key...'
    //   248: aload_0
    //   249: getfield x : F
    //   252: ldc 2.3
    //   254: fadd
    //   255: aload_0
    //   256: getfield y : F
    //   259: ldc 1.7
    //   261: fsub
    //   262: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   265: invokevirtual getTextOffset : ()I
    //   268: i2f
    //   269: fsub
    //   270: iconst_m1
    //   271: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   274: goto -> 540
    //   277: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   280: ldc 'Press a Key...'
    //   282: aload_0
    //   283: getfield x : F
    //   286: ldc 2.3
    //   288: fadd
    //   289: aload_0
    //   290: getfield y : F
    //   293: ldc 0.7
    //   295: fsub
    //   296: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   299: invokevirtual getTextOffset : ()I
    //   302: i2f
    //   303: fsub
    //   304: iconst_m1
    //   305: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   308: pop
    //   309: goto -> 540
    //   312: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   315: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   318: invokevirtual getValue : ()Ljava/lang/Object;
    //   321: checkcast java/lang/Boolean
    //   324: invokevirtual booleanValue : ()Z
    //   327: ifne -> 345
    //   330: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   333: pop
    //   334: ldc 'CustomFont'
    //   336: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   339: invokevirtual isEnabled : ()Z
    //   342: ifne -> 360
    //   345: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   348: pop
    //   349: ldc 'CustomFont'
    //   351: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   354: invokevirtual isEnabled : ()Z
    //   357: ifne -> 451
    //   360: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   363: new java/lang/StringBuilder
    //   366: dup
    //   367: invokespecial <init> : ()V
    //   370: aload_0
    //   371: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   374: invokevirtual getName : ()Ljava/lang/String;
    //   377: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   380: ldc ' '
    //   382: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   385: getstatic com/mojang/realmsclient/gui/ChatFormatting.GRAY : Lcom/mojang/realmsclient/gui/ChatFormatting;
    //   388: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   391: aload_0
    //   392: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   395: invokevirtual getValue : ()Ljava/lang/Object;
    //   398: invokevirtual toString : ()Ljava/lang/String;
    //   401: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   404: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   407: invokevirtual toString : ()Ljava/lang/String;
    //   410: aload_0
    //   411: getfield x : F
    //   414: ldc 2.3
    //   416: fadd
    //   417: aload_0
    //   418: getfield y : F
    //   421: ldc 1.7
    //   423: fsub
    //   424: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   427: invokevirtual getTextOffset : ()I
    //   430: i2f
    //   431: fsub
    //   432: aload_0
    //   433: invokevirtual getState : ()Z
    //   436: ifeq -> 443
    //   439: iconst_m1
    //   440: goto -> 445
    //   443: ldc -5592406
    //   445: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   448: goto -> 540
    //   451: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   454: new java/lang/StringBuilder
    //   457: dup
    //   458: invokespecial <init> : ()V
    //   461: aload_0
    //   462: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   465: invokevirtual getName : ()Ljava/lang/String;
    //   468: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   471: ldc ' '
    //   473: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   476: getstatic com/mojang/realmsclient/gui/ChatFormatting.GRAY : Lcom/mojang/realmsclient/gui/ChatFormatting;
    //   479: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   482: aload_0
    //   483: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   486: invokevirtual getValue : ()Ljava/lang/Object;
    //   489: invokevirtual toString : ()Ljava/lang/String;
    //   492: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   495: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: invokevirtual toString : ()Ljava/lang/String;
    //   501: aload_0
    //   502: getfield x : F
    //   505: ldc 2.3
    //   507: fadd
    //   508: aload_0
    //   509: getfield y : F
    //   512: ldc 0.7
    //   514: fsub
    //   515: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   518: invokevirtual getTextOffset : ()I
    //   521: i2f
    //   522: fsub
    //   523: aload_0
    //   524: invokevirtual getState : ()Z
    //   527: ifeq -> 534
    //   530: iconst_m1
    //   531: goto -> 536
    //   534: ldc -5592406
    //   536: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   539: pop
    //   540: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #28	-> 0
    //   #29	-> 53
    //   #30	-> 188
    //   #31	-> 195
    //   #32	-> 243
    //   #34	-> 277
    //   #37	-> 312
    //   #38	-> 360
    //   #40	-> 451
    //   #43	-> 540
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	541	0	this	Ldev/madcat/m3dc3t/features/gui/components/items/buttons/BindButton;
    //   0	541	1	mouseX	I
    //   0	541	2	mouseY	I
    //   0	541	3	partialTicks	F
    //   53	488	4	color	I
  }
  
  public void update() {
    setHidden(!this.setting.isVisible());
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (isHovering(mouseX, mouseY))
      mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F)); 
  }
  
  public void onKeyTyped(char typedChar, int keyCode) {
    if (this.isListening) {
      Bind bind = new Bind(keyCode);
      if (bind.toString().equalsIgnoreCase("Escape"))
        return; 
      if (bind.toString().equalsIgnoreCase("Delete"))
        bind = new Bind(-1); 
      this.setting.setValue(bind);
      onMouseClick();
    } 
  }
  
  public int getHeight() {
    return 14;
  }
  
  public void toggle() {
    this.isListening = !this.isListening;
  }
  
  public boolean getState() {
    return !this.isListening;
  }
}
