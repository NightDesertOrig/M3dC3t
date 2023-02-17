//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.MathUtil;
import dev.madcat.m3dc3t.util.Timer;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoRejoin extends Module {
  private static ServerData serverData;
  
  private static AutoRejoin INSTANCE = new AutoRejoin();
  
  private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(5)));
  
  public AutoRejoin() {
    super("AutoRejoin", "Reconnects you if you disconnect.", Module.Category.MISC, true, false, false);
    setInstance();
  }
  
  public static AutoRejoin getInstance() {
    if (INSTANCE == null)
      INSTANCE = new AutoRejoin(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  @SubscribeEvent
  public void sendPacket(GuiOpenEvent event) {
    if (event.getGui() instanceof GuiDisconnected) {
      updateLastConnectedServer();
      GuiDisconnected disconnected = (GuiDisconnected)event.getGui();
      event.setGui((GuiScreen)new GuiDisconnectedHook(disconnected));
    } 
  }
  
  @SubscribeEvent
  public void onWorldUnload(WorldEvent.Unload event) {
    updateLastConnectedServer();
  }
  
  public void updateLastConnectedServer() {
    ServerData data = mc.getCurrentServerData();
    if (data != null)
      serverData = data; 
  }
  
  private class GuiDisconnectedHook extends GuiDisconnected {
    private final Timer timer;
    
    public GuiDisconnectedHook(GuiDisconnected disconnected) {
      super(disconnected.parentScreen, disconnected.reason, disconnected.message);
      this.timer = new Timer();
      this.timer.reset();
    }
    
    public void updateScreen() {
      if (this.timer.passedS(((Integer)AutoRejoin.this.delay.getValue()).intValue()))
        this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this.parentScreen, this.mc, (AutoRejoin.serverData == null) ? this.mc.currentServerData : AutoRejoin.serverData)); 
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
      String s = "Reconnecting in " + MathUtil.round(((((Integer)AutoRejoin.this.delay.getValue()).intValue() * 1000) - this.timer.getPassedTimeMs()) / 1000.0D, 1);
      AutoRejoin.this.renderer.drawString(s, (this.width / 2 - AutoRejoin.this.renderer.getStringWidth(s) / 2), (this.height - 16), 16777215, true);
    }
  }
}
