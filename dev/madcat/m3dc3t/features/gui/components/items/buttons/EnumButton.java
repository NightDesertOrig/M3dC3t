//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui.components.items.buttons;

import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class EnumButton extends Button {
  public Setting setting;
  
  public EnumButton(Setting setting) {
    super(setting.getName());
    this.setting = setting;
    this.width = 15;
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    // Byte code:
    //   0: aload_0
    //   1: getfield x : F
    //   4: aload_0
    //   5: getfield y : F
    //   8: aload_0
    //   9: getfield x : F
    //   12: aload_0
    //   13: getfield width : I
    //   16: i2f
    //   17: fadd
    //   18: ldc 7.4
    //   20: fadd
    //   21: aload_0
    //   22: getfield y : F
    //   25: aload_0
    //   26: getfield height : I
    //   29: i2f
    //   30: fadd
    //   31: ldc 0.5
    //   33: fsub
    //   34: aload_0
    //   35: invokevirtual getState : ()Z
    //   38: ifeq -> 116
    //   41: aload_0
    //   42: iload_1
    //   43: iload_2
    //   44: invokevirtual isHovering : (II)Z
    //   47: ifne -> 83
    //   50: getstatic dev/madcat/m3dc3t/M3dC3t.colorManager : Ldev/madcat/m3dc3t/manager/ColorManager;
    //   53: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   56: pop
    //   57: ldc dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   59: invokestatic getModuleByClass : (Ljava/lang/Class;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   62: checkcast dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   65: getfield hoverAlpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   68: invokevirtual getValue : ()Ljava/lang/Object;
    //   71: checkcast java/lang/Integer
    //   74: invokevirtual intValue : ()I
    //   77: invokevirtual getColorWithAlpha : (I)I
    //   80: goto -> 132
    //   83: getstatic dev/madcat/m3dc3t/M3dC3t.colorManager : Ldev/madcat/m3dc3t/manager/ColorManager;
    //   86: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   89: pop
    //   90: ldc dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   92: invokestatic getModuleByClass : (Ljava/lang/Class;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   95: checkcast dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   98: getfield alpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   101: invokevirtual getValue : ()Ljava/lang/Object;
    //   104: checkcast java/lang/Integer
    //   107: invokevirtual intValue : ()I
    //   110: invokevirtual getColorWithAlpha : (I)I
    //   113: goto -> 132
    //   116: aload_0
    //   117: iload_1
    //   118: iload_2
    //   119: invokevirtual isHovering : (II)Z
    //   122: ifne -> 130
    //   125: ldc 290805077
    //   127: goto -> 132
    //   130: ldc -2007673515
    //   132: invokestatic drawRect : (FFFFI)V
    //   135: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   138: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   141: invokevirtual getValue : ()Ljava/lang/Object;
    //   144: checkcast java/lang/Boolean
    //   147: invokevirtual booleanValue : ()Z
    //   150: ifne -> 168
    //   153: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   156: pop
    //   157: ldc 'CustomFont'
    //   159: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   162: invokevirtual isEnabled : ()Z
    //   165: ifne -> 183
    //   168: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   171: pop
    //   172: ldc 'CustomFont'
    //   174: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   177: invokevirtual isEnabled : ()Z
    //   180: ifne -> 288
    //   183: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   186: new java/lang/StringBuilder
    //   189: dup
    //   190: invokespecial <init> : ()V
    //   193: aload_0
    //   194: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   197: invokevirtual getName : ()Ljava/lang/String;
    //   200: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: ldc ' '
    //   205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: getstatic com/mojang/realmsclient/gui/ChatFormatting.GRAY : Lcom/mojang/realmsclient/gui/ChatFormatting;
    //   211: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   214: aload_0
    //   215: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   218: invokevirtual currentEnumName : ()Ljava/lang/String;
    //   221: ldc 'ABC'
    //   223: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   226: ifeq -> 234
    //   229: ldc 'ABC'
    //   231: goto -> 241
    //   234: aload_0
    //   235: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   238: invokevirtual currentEnumName : ()Ljava/lang/String;
    //   241: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: invokevirtual toString : ()Ljava/lang/String;
    //   247: aload_0
    //   248: getfield x : F
    //   251: ldc 2.3
    //   253: fadd
    //   254: aload_0
    //   255: getfield y : F
    //   258: ldc 1.7
    //   260: fsub
    //   261: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   264: invokevirtual getTextOffset : ()I
    //   267: i2f
    //   268: fsub
    //   269: aload_0
    //   270: invokevirtual getState : ()Z
    //   273: ifeq -> 280
    //   276: iconst_m1
    //   277: goto -> 282
    //   280: ldc -5592406
    //   282: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   285: goto -> 391
    //   288: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   291: new java/lang/StringBuilder
    //   294: dup
    //   295: invokespecial <init> : ()V
    //   298: aload_0
    //   299: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   302: invokevirtual getName : ()Ljava/lang/String;
    //   305: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: ldc ' '
    //   310: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   313: getstatic com/mojang/realmsclient/gui/ChatFormatting.GRAY : Lcom/mojang/realmsclient/gui/ChatFormatting;
    //   316: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   319: aload_0
    //   320: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   323: invokevirtual currentEnumName : ()Ljava/lang/String;
    //   326: ldc 'ABC'
    //   328: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   331: ifeq -> 339
    //   334: ldc 'ABC'
    //   336: goto -> 346
    //   339: aload_0
    //   340: getfield setting : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   343: invokevirtual currentEnumName : ()Ljava/lang/String;
    //   346: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   349: invokevirtual toString : ()Ljava/lang/String;
    //   352: aload_0
    //   353: getfield x : F
    //   356: ldc 2.3
    //   358: fadd
    //   359: aload_0
    //   360: getfield y : F
    //   363: ldc 0.7
    //   365: fsub
    //   366: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   369: invokevirtual getTextOffset : ()I
    //   372: i2f
    //   373: fsub
    //   374: aload_0
    //   375: invokevirtual getState : ()Z
    //   378: ifeq -> 385
    //   381: iconst_m1
    //   382: goto -> 387
    //   385: ldc -5592406
    //   387: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   390: pop
    //   391: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #25	-> 0
    //   #26	-> 135
    //   #27	-> 183
    //   #29	-> 288
    //   #31	-> 391
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	392	0	this	Ldev/madcat/m3dc3t/features/gui/components/items/buttons/EnumButton;
    //   0	392	1	mouseX	I
    //   0	392	2	mouseY	I
    //   0	392	3	partialTicks	F
  }
  
  public void update() {
    setHidden(!this.setting.isVisible());
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (isHovering(mouseX, mouseY))
      mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F)); 
  }
  
  public int getHeight() {
    return 14;
  }
  
  public void toggle() {
    this.setting.increaseEnum();
  }
  
  public boolean getState() {
    return true;
  }
}
