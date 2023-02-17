//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.command.Command;
import dev.madcat.m3dc3t.features.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class MCF extends Module {
  private boolean clicked = false;
  
  public MCF() {
    super("MCF", "Click the middle mouse button to add a friends.", Module.Category.MISC, true, false, false);
  }
  
  public void onUpdate() {
    if (Mouse.isButtonDown(2)) {
      if (!this.clicked && mc.currentScreen == null)
        onClick(); 
      this.clicked = true;
    } else {
      this.clicked = false;
    } 
  }
  
  private void onClick() {
    RayTraceResult result = mc.objectMouseOver;
    Entity entity;
    if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && entity = result.entityHit instanceof net.minecraft.entity.player.EntityPlayer)
      if (M3dC3t.friendManager.isFriend(entity.getName())) {
        M3dC3t.friendManager.removeFriend(entity.getName());
        Command.sendMessage(ChatFormatting.RED + entity.getName() + ChatFormatting.RED + " has been unfriended.");
      } else {
        M3dC3t.friendManager.addFriend(entity.getName());
        Command.sendMessage(ChatFormatting.AQUA + entity.getName() + ChatFormatting.AQUA + " has been friended.");
      }  
    this.clicked = true;
  }
}
