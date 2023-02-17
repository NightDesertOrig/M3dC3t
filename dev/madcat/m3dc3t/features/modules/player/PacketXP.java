//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Bind;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class PacketXP extends Module {
  private final Setting<Bind> bind;
  
  private int delay_count;
  
  int prvSlot;
  
  public PacketXP() {
    super("PacketXP", "Silent use experience.", Module.Category.PLAYER, false, false, false);
    this.bind = register(new Setting("PacketBind", new Bind(-1)));
  }
  
  public static Boolean inft = Boolean.valueOf(false);
  
  public static Bind binds;
  
  public void onEnable() {
    this.delay_count = 0;
  }
  
  public void onUpdate() {
    binds = (Bind)this.bind.getValue();
    if (findExpInHotbar() == -1)
      return; 
    if (((Bind)this.bind.getValue()).getKey() > -1) {
      if (Keyboard.isKeyDown(((Bind)this.bind.getValue()).getKey()) && mc.currentScreen == null) {
        if (findExpInHotbar() == -1)
          return; 
        usedXp();
      } 
    } else if (((Bind)this.bind.getValue()).getKey() < -1 && 
      Mouse.isButtonDown(convertToMouse(((Bind)this.bind.getValue()).getKey())) && mc.currentScreen == null) {
      if (findExpInHotbar() == -1)
        return; 
      usedXp();
    } 
  }
  
  public static int convertToMouse(int key) {
    switch (key) {
      case -2:
        return 0;
      case -3:
        return 1;
      case -4:
        return 2;
      case -5:
        return 3;
      case -6:
        return 4;
    } 
    return -1;
  }
  
  private int findExpInHotbar() {
    int slot = 0;
    for (int i = 0; i < 9; i++) {
      if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
        slot = i;
        break;
      } 
    } 
    return slot;
  }
  
  private void usedXp() {
    int oldPitch = (int)mc.player.rotationPitch;
    this.prvSlot = mc.player.inventory.currentItem;
    mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(findExpInHotbar()));
    mc.player.rotationPitch = -90.0F;
    mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
    mc.player.rotationPitch = oldPitch;
    mc.player.inventory.currentItem = this.prvSlot;
    mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.prvSlot));
  }
  
  public Boolean notInInv(Item itemOfChoice) {
    int n = 0;
    if (itemOfChoice == mc.player.getHeldItemOffhand().getItem())
      return Boolean.valueOf(true); 
    for (int i = 35; i >= 0; i--) {
      Item item = mc.player.inventory.getStackInSlot(i).getItem();
      if (item == itemOfChoice)
        return Boolean.valueOf(true); 
      if (item != itemOfChoice)
        n++; 
    } 
    if (n >= 35)
      return Boolean.valueOf(false); 
    return Boolean.valueOf(true);
  }
}
