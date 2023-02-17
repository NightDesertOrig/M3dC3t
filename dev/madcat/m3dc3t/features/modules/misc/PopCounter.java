//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.command.Command;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

public class PopCounter extends Module {
  public static HashMap<String, Integer> TotemPopContainer = new HashMap<>();
  
  private final Setting<Boolean> atez = register(new Setting("AutoEZ", Boolean.valueOf(false)));
  
  private final Setting<Boolean> atez2 = register(new Setting("SelfAutoEZ", Boolean.valueOf(false)));
  
  private static PopCounter INSTANCE = new PopCounter();
  
  public PopCounter() {
    super("PopCounter", "Counts other players totem pops.", Module.Category.MISC, true, false, false);
    setInstance();
  }
  
  public static PopCounter getInstance() {
    if (INSTANCE == null)
      INSTANCE = new PopCounter(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public void onDeath(EntityPlayer player) {
    if (TotemPopContainer.containsKey(player.getName())) {
      int l_Count = ((Integer)TotemPopContainer.get(player.getName())).intValue();
      TotemPopContainer.remove(player.getName());
      if (l_Count == 1) {
        if (mc.player.equals(player)) {
          if (M3dC3t.moduleManager.isModuleEnabled("PopCounter")) {
            Command.sendMessage(ChatFormatting.BLUE + "You died after popping " + ChatFormatting.RED + l_Count + ChatFormatting.RED + " Totem!");
            if (((Boolean)this.atez2.getValue()).booleanValue())
              mc.player.connection.sendPacket((Packet)new CPacketChatMessage("Im so EZ died after popping " + l_Count + " Totem!")); 
          } 
        } else if (M3dC3t.moduleManager.isModuleEnabled("PopCounter")) {
          Command.sendMessage(ChatFormatting.RED + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " Totem!");
          if (((Boolean)this.atez.getValue()).booleanValue())
            mc.player.connection.sendPacket((Packet)new CPacketChatMessage("EZ " + player.getName() + " died after popping " + l_Count + " Totem!")); 
        } 
      } else if (mc.player.equals(player)) {
        if (M3dC3t.moduleManager.isModuleEnabled("PopCounter")) {
          Command.sendMessage(ChatFormatting.BLUE + "You died after popping " + ChatFormatting.RED + l_Count + ChatFormatting.RED + " Totems!");
          if (((Boolean)this.atez2.getValue()).booleanValue())
            mc.player.connection.sendPacket((Packet)new CPacketChatMessage("Im so EZ died after popping " + l_Count + " Totem!")); 
        } 
      } else if (M3dC3t.moduleManager.isModuleEnabled("PopCounter")) {
        Command.sendMessage(ChatFormatting.RED + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " Totems!");
        if (((Boolean)this.atez.getValue()).booleanValue())
          mc.player.connection.sendPacket((Packet)new CPacketChatMessage("EZ " + player.getName() + " died after popping " + l_Count + " Totem!")); 
      } 
    } 
  }
  
  public void onTotemPop(EntityPlayer player) {
    if (fullNullCheck())
      return; 
    int l_Count = 1;
    if (TotemPopContainer.containsKey(player.getName())) {
      l_Count = ((Integer)TotemPopContainer.get(player.getName())).intValue();
      TotemPopContainer.put(player.getName(), Integer.valueOf(++l_Count));
    } else {
      TotemPopContainer.put(player.getName(), Integer.valueOf(l_Count));
    } 
    if (l_Count == 1) {
      if (mc.player.equals(player)) {
        if (M3dC3t.moduleManager.isModuleEnabled("PopCounter"))
          Command.sendMessage(ChatFormatting.BLUE + "You popped " + ChatFormatting.RED + l_Count + ChatFormatting.RED + " Totem."); 
      } else if (M3dC3t.moduleManager.isModuleEnabled("PopCounter")) {
        Command.sendMessage(ChatFormatting.RED + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " Totem.");
      } 
    } else if (mc.player.equals(player)) {
      if (M3dC3t.moduleManager.isModuleEnabled("PopCounter"))
        Command.sendMessage(ChatFormatting.BLUE + "You popped " + ChatFormatting.RED + l_Count + ChatFormatting.RED + " Totems."); 
    } else if (M3dC3t.moduleManager.isModuleEnabled("PopCounter")) {
      Command.sendMessage(ChatFormatting.RED + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " Totems.");
    } 
  }
}
