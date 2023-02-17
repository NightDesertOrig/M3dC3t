//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.Feature;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;

public abstract class Command extends Feature {
  protected String name;
  
  protected String[] commands;
  
  public Command(String name) {
    super(name);
    this.name = name;
    this.commands = new String[] { "" };
  }
  
  public Command(String name, String[] commands) {
    super(name);
    this.name = name;
    this.commands = commands;
  }
  
  public static void sendMessage(String message) {
    sendSilentMessage(M3dC3t.commandManager.getClientMessage() + " " + ChatFormatting.GRAY + message);
  }
  
  public static void sendSilentMessage(String message) {
    if (nullCheck())
      return; 
    mc.player.sendMessage((ITextComponent)new ChatMessage(message));
  }
  
  public static String getCommandPrefix() {
    return M3dC3t.commandManager.getPrefix();
  }
  
  public abstract void execute(String[] paramArrayOfString);
  
  public String getName() {
    return this.name;
  }
  
  public String[] getCommands() {
    return this.commands;
  }
  
  public static class ChatMessage extends TextComponentBase {
    private final String text;
    
    public ChatMessage(String text) {
      Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
      Matcher matcher = pattern.matcher(text);
      StringBuffer stringBuffer = new StringBuffer();
      while (matcher.find()) {
        String replacement = matcher.group().substring(1);
        matcher.appendReplacement(stringBuffer, replacement);
      } 
      matcher.appendTail(stringBuffer);
      this.text = stringBuffer.toString();
    }
    
    public String getUnformattedComponentText() {
      return this.text;
    }
    
    public ITextComponent createCopy() {
      return null;
    }
    
    public ITextComponent shallowCopy() {
      return (ITextComponent)new ChatMessage(this.text);
    }
  }
  
  public static char coolLineThing() {
    return '§';
  }
}
