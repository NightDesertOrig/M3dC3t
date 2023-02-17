//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class AutoLog extends Module {
  private final Setting<Float> health = register(new Setting("Health", Float.valueOf(16.0F), Float.valueOf(0.1F), Float.valueOf(36.0F)));
  
  public Setting<Integer> totems = register(new Setting("Totems", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10)));
  
  private final Setting<Boolean> logout = register(new Setting("LogoutOff", Boolean.valueOf(true)));
  
  public AutoLog() {
    super("AutoLog", "Logout when in danger.", Module.Category.MISC, false, false, false);
  }
  
  public void onTick() {
    int totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum();
    if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
      totems += mc.player.getHeldItemOffhand().getCount(); 
    if (mc.player.getHealth() <= ((Float)this.health.getValue()).floatValue() && (
      totems <= ((Integer)this.totems.getValue()).floatValue() || totems == ((Integer)this.totems.getValue()).floatValue())) {
      Minecraft.getMinecraft().getConnection().handleDisconnect(new SPacketDisconnect((ITextComponent)new TextComponentString("Internal Exception: java.lang.NullPointerException")));
      if (((Boolean)this.logout.getValue()).booleanValue())
        disable(); 
    } 
  }
}
