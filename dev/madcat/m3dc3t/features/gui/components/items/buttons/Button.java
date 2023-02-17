//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui.components.items.buttons;

import dev.madcat.m3dc3t.features.gui.M3dC3tGui;
import dev.madcat.m3dc3t.features.gui.components.Component;
import dev.madcat.m3dc3t.features.gui.components.items.Item;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class Button extends Item {
  private boolean state;
  
  public Button(String name) {
    super(name);
    this.height = 15;
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
    //   18: aload_0
    //   19: getfield y : F
    //   22: aload_0
    //   23: getfield height : I
    //   26: i2f
    //   27: fadd
    //   28: ldc 0.5
    //   30: fsub
    //   31: aload_0
    //   32: invokevirtual getState : ()Z
    //   35: ifeq -> 113
    //   38: aload_0
    //   39: iload_1
    //   40: iload_2
    //   41: invokevirtual isHovering : (II)Z
    //   44: ifne -> 80
    //   47: getstatic dev/madcat/m3dc3t/M3dC3t.colorManager : Ldev/madcat/m3dc3t/manager/ColorManager;
    //   50: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   53: pop
    //   54: ldc dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   56: invokestatic getModuleByClass : (Ljava/lang/Class;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   59: checkcast dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   62: getfield hoverAlpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   65: invokevirtual getValue : ()Ljava/lang/Object;
    //   68: checkcast java/lang/Integer
    //   71: invokevirtual intValue : ()I
    //   74: invokevirtual getColorWithAlpha : (I)I
    //   77: goto -> 129
    //   80: getstatic dev/madcat/m3dc3t/M3dC3t.colorManager : Ldev/madcat/m3dc3t/manager/ColorManager;
    //   83: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   86: pop
    //   87: ldc dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   89: invokestatic getModuleByClass : (Ljava/lang/Class;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   92: checkcast dev/madcat/m3dc3t/features/modules/client/ClickGui
    //   95: getfield alpha : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   98: invokevirtual getValue : ()Ljava/lang/Object;
    //   101: checkcast java/lang/Integer
    //   104: invokevirtual intValue : ()I
    //   107: invokevirtual getColorWithAlpha : (I)I
    //   110: goto -> 129
    //   113: aload_0
    //   114: iload_1
    //   115: iload_2
    //   116: invokevirtual isHovering : (II)Z
    //   119: ifne -> 127
    //   122: ldc 290805077
    //   124: goto -> 129
    //   127: ldc -2007673515
    //   129: invokestatic drawRect : (FFFFI)V
    //   132: invokestatic getInstance : ()Ldev/madcat/m3dc3t/features/modules/client/FontMod;
    //   135: getfield cfont : Ldev/madcat/m3dc3t/features/setting/Setting;
    //   138: invokevirtual getValue : ()Ljava/lang/Object;
    //   141: checkcast java/lang/Boolean
    //   144: invokevirtual booleanValue : ()Z
    //   147: ifne -> 165
    //   150: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   153: pop
    //   154: ldc 'CustomFont'
    //   156: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   159: invokevirtual isEnabled : ()Z
    //   162: ifne -> 180
    //   165: getstatic dev/madcat/m3dc3t/M3dC3t.moduleManager : Ldev/madcat/m3dc3t/manager/ModuleManager;
    //   168: pop
    //   169: ldc 'CustomFont'
    //   171: invokestatic getModuleByName : (Ljava/lang/String;)Ldev/madcat/m3dc3t/features/modules/Module;
    //   174: invokevirtual isEnabled : ()Z
    //   177: ifne -> 227
    //   180: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   183: aload_0
    //   184: invokevirtual getName : ()Ljava/lang/String;
    //   187: aload_0
    //   188: getfield x : F
    //   191: ldc 2.3
    //   193: fadd
    //   194: aload_0
    //   195: getfield y : F
    //   198: fconst_2
    //   199: fsub
    //   200: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   203: invokevirtual getTextOffset : ()I
    //   206: i2f
    //   207: fsub
    //   208: aload_0
    //   209: invokevirtual getState : ()Z
    //   212: ifeq -> 219
    //   215: iconst_m1
    //   216: goto -> 221
    //   219: ldc -5592406
    //   221: invokevirtual drawStringWithShadow : (Ljava/lang/String;FFI)V
    //   224: goto -> 272
    //   227: getstatic dev/madcat/m3dc3t/M3dC3t.textManager : Ldev/madcat/m3dc3t/manager/TextManager;
    //   230: aload_0
    //   231: invokevirtual getName : ()Ljava/lang/String;
    //   234: aload_0
    //   235: getfield x : F
    //   238: ldc 2.3
    //   240: fadd
    //   241: aload_0
    //   242: getfield y : F
    //   245: fconst_1
    //   246: fsub
    //   247: invokestatic getClickGui : ()Ldev/madcat/m3dc3t/features/gui/M3dC3tGui;
    //   250: invokevirtual getTextOffset : ()I
    //   253: i2f
    //   254: fsub
    //   255: aload_0
    //   256: invokevirtual getState : ()Z
    //   259: ifeq -> 266
    //   262: iconst_m1
    //   263: goto -> 268
    //   266: ldc -5592406
    //   268: invokevirtual drawStringClickGui : (Ljava/lang/String;FFI)F
    //   271: pop
    //   272: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #24	-> 0
    //   #25	-> 132
    //   #26	-> 180
    //   #28	-> 227
    //   #30	-> 272
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	273	0	this	Ldev/madcat/m3dc3t/features/gui/components/items/buttons/Button;
    //   0	273	1	mouseX	I
    //   0	273	2	mouseY	I
    //   0	273	3	partialTicks	F
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    if (mouseButton == 0 && isHovering(mouseX, mouseY))
      onMouseClick(); 
  }
  
  public void onMouseClick() {
    this.state = !this.state;
    toggle();
    mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
  }
  
  public void toggle() {}
  
  public boolean getState() {
    return this.state;
  }
  
  public int getHeight() {
    return 14;
  }
  
  public boolean isHovering(int mouseX, int mouseY) {
    for (Component component : M3dC3tGui.getClickGui().getComponents()) {
      if (!component.drag)
        continue; 
      return false;
    } 
    return (mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + this.height);
  }
}
