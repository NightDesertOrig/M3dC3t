package dev.madcat.m3dc3t.features.command.commands;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.command.Command;

public class UnloadCommand extends Command {
  public UnloadCommand() {
    super("unload", new String[0]);
  }
  
  public void execute(String[] commands) {
    M3dC3t.unload(true);
  }
}
