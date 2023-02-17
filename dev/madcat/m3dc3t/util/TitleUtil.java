package dev.madcat.m3dc3t.util;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.client.Title;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;

public class TitleUtil {
  int ticks = 0;
  
  int bruh = 0;
  
  int breakTimer = 0;
  
  String bruh1;
  
  boolean qwerty = false;
  
  @SubscribeEvent
  public void onTick(TickEvent.ClientTickEvent event) {
    if (this.bruh1 != null && 
      this.bruh1 != (Title.getInstance()).titleName.getValue()) {
      this.ticks = 0;
      this.bruh = 0;
      this.breakTimer = 0;
      this.qwerty = false;
    } 
    this.bruh1 = (String)(Title.getInstance()).titleName.getValue();
    if (M3dC3t.moduleManager.isModuleEnabled("Title")) {
      if (this.bruh1 != null) {
        this.ticks++;
        if (this.ticks % 17 == 0) {
          Display.setTitle(this.bruh1.substring(0, this.bruh1.length() - this.bruh));
          if ((this.bruh == this.bruh1.length() && this.breakTimer != 2) || (this.bruh == 0 && this.breakTimer != 4)) {
            this.breakTimer++;
            return;
          } 
          this.breakTimer = 0;
          if (this.bruh == this.bruh1.length())
            this.qwerty = true; 
          if (this.qwerty) {
            this.bruh--;
          } else {
            this.bruh++;
          } 
          if (this.bruh == 0)
            this.qwerty = false; 
        } 
      } 
    } else {
      Display.setTitle("Minecraft 1.12.2");
    } 
  }
}
