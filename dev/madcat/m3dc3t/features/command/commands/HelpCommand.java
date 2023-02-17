package dev.madcat.m3dc3t.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.command.Command;

public class HelpCommand extends Command {
  public HelpCommand() {
    super("help");
  }
  
  public void execute(String[] commands) {
    if (commands.length == 1) {
      sendMessage("Commands: ");
      for (Command command : M3dC3t.commandManager.getCommands())
        sendMessage(ChatFormatting.GRAY + M3dC3t.commandManager.getPrefix() + command.getName()); 
    } else {
      sendMessage("Module: module, <module>, <set/reset>, <setting>, <value>");
    } 
  }
}
