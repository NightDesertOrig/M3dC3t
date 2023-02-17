//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui.components.items.buttons;

import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class BooleanButton extends Button {
  private final Setting setting;
  
  public BooleanButton(Setting setting) {
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
    //   180: ifne -> 231
    //   183: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   186: aload_0
    //   187: invokevirtual getName : ()Ljava/lang/String;
    //   190: aload_0
    //   191: getfield x : F
    //   194: ldc 2.3
    //   196: fadd
    //   197: aload_0
    //   198: getfield y : F
    //   201: ldc 1.7
    //   203: fsub
    //   204: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   207: invokevirtual getTextOffset : ()I
    //   210: i2f
    //   211: fsub
    //   212: aload_0
    //   213: invokevirtual getState : ()Z
    //   216: ifeq -> 223
    //   219: iconst_m1
    //   220: goto -> 225
    //   223: ldc -5592406
    //   225: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   228: goto -> 277
    //   231: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   234: aload_0
    //   235: invokevirtual getName : ()Ljava/lang/String;
    //   238: aload_0
    //   239: getfield x : F
    //   242: ldc 2.3
    //   244: fadd
    //   245: aload_0
    //   246: getfield y : F
    //   249: ldc 0.7
    //   251: fsub
    //   252: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   255: invokevirtual getTextOffset : ()I
    //   258: i2f
    //   259: fsub
    //   260: aload_0
    //   261: invokevirtual getState : ()Z
    //   264: ifeq -> 271
    //   267: iconst_m1
    //   268: goto -> 273
    //   271: ldc -5592406
    //   273: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   276: pop
    //   277: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #24	-> 0
    //   #25	-> 135
    //   #26	-> 183
    //   #28	-> 231
    //   #30	-> 277
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	278	0	this	Ldev/madcat/m3dc3t/features/gui/components/items/buttons/BooleanButton;
    //   0	278	1	mouseX	I
    //   0	278	2	mouseY	I
    //   0	278	3	partialTicks	F
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
    this.setting.setValue(Boolean.valueOf(!((Boolean)this.setting.getValue()).booleanValue()));
  }
  
  public boolean getState() {
    return ((Boolean)this.setting.getValue()).booleanValue();
  }
}
