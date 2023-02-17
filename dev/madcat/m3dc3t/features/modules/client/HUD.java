//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.MathUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import dev.madcat.m3dc3t.util.TextUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class HUD extends Module {
  public HUD() {
    super("HUD", "HUD Elements rendered on your screen.", Module.Category.CLIENT, true, false, false);
    this.moduleInfo = register(new Setting("ModuleInfo", Boolean.valueOf(true)));
    this.waterMark = register(new Setting("Watermark", Boolean.valueOf(false), "displays watermark"));
    this.watermark2 = register(new Setting("SkeetWatermark", Boolean.valueOf(false), v -> ((Boolean)this.waterMark.getValue()).booleanValue()));
    this.compactX = register(new Setting("SkeetMarkX", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(1080), v -> (((Boolean)this.waterMark.getValue()).booleanValue() && ((Boolean)this.watermark2.getValue()).booleanValue())));
    this.compactY = register(new Setting("SkeetMarkY", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(530), v -> (((Boolean)this.waterMark.getValue()).booleanValue() && ((Boolean)this.watermark2.getValue()).booleanValue())));
    this.alpha = register(new Setting("Alpha", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(255), v -> (((Boolean)this.waterMark.getValue()).booleanValue() && ((Boolean)this.watermark2.getValue()).booleanValue())));
    this.grayNess = register(new Setting("Gray", Boolean.valueOf(true)));
    this.arrayList = register(new Setting("ActiveModules", Boolean.valueOf(true), "Lists the active modules."));
    this.renderingUp = register(new Setting("RenderingUp", Boolean.TRUE, v -> ((Boolean)this.arrayList.getValue()).booleanValue()));
    this.arraylisty = register(new Setting("OffsetY", Integer.valueOf(40), Integer.valueOf(0), Integer.valueOf(200), v -> (((Boolean)this.arrayList.getValue()).booleanValue() && ((Boolean)this.renderingUp.getValue()).booleanValue())));
    this.coords = register(new Setting("Coords", Boolean.valueOf(true), "Your current coordinates"));
    this.speed = register(new Setting("Speed", Boolean.valueOf(false), "Your Speed"));
    this.server = register(new Setting("IP", Boolean.valueOf(false), "Shows the server"));
    this.ping = register(new Setting("Ping", Boolean.valueOf(false), "Your response time to the server."));
    this.tps = register(new Setting("TPS", Boolean.valueOf(false), "Ticks per second of the server."));
    this.fps = register(new Setting("FPS", Boolean.valueOf(false), "Your frames per second."));
    this.players = new HashMap<>();
    this.lag = register(new Setting("LagNotifier", Boolean.valueOf(true), "The time"));
    this.FriendList = register(new Setting("FriendList", Boolean.FALSE));
    this.FriendListY = register(new Setting("FriendListY", Integer.valueOf(173), Integer.valueOf(0), Integer.valueOf(530), v -> ((Boolean)this.FriendList.getValue()).booleanValue()));
    this.bracketColor = register(new Setting("BracketColor", TextUtil.Color.WHITE));
    this.commandColor = register(new Setting("NameColor", TextUtil.Color.WHITE));
    this.notifyToggles = register(new Setting("ChatNotify", Boolean.valueOf(true), "notifys in chat"));
    this.renderingMode = register(new Setting("Ordering", RenderingMode.Length));
    this.lagTime = register(new Setting("LagTime", Integer.valueOf(1000), Integer.valueOf(0), Integer.valueOf(2000)));
    this.burrow = register(new Setting("BurrowWarner", Boolean.valueOf(false), "Your Speed"));
    setInstance();
  }
  
  public static HUD getInstance() {
    if (INSTANCE == null)
      INSTANCE = new HUD(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public void onRender2D(Render2DEvent event) {
    if (fullNullCheck())
      return; 
    if (((Boolean)this.burrow.getValue()).booleanValue()) {
      BlockPos burrowpos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
      if (burrowpos != null)
        if ((((mc.world.getBlockState(burrowpos).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((mc.world.getBlockState(burrowpos).getBlock() == Blocks.ENDER_CHEST) ? 1 : 0)) != 0)
          M3dC3t.textManager.drawString(ChatFormatting.DARK_RED + "You are in Burrow!", (this.renderer.scaledWidth / 2 - 45), (this.renderer.scaledHeight / 2 - 20), 200, true);  
    } 
    int width = this.renderer.scaledWidth;
    int height = this.renderer.scaledHeight;
    this.color = ColorUtil.toRGBA(((Integer)(ClickGui.getInstance()).red.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).green.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).blue.getValue()).intValue());
    if (((Boolean)this.waterMark.getValue()).booleanValue() && !((Boolean)this.watermark2.getValue()).booleanValue()) {
      String string = (ClickGui.getInstance()).clientName.getValueAsString() + " " + "2.2.1.4";
      if (((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue()) {
        if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
          this.renderer.drawString(string, 2.0F, 2.0F, ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
        } else {
          int[] arrayOfInt = { 1 };
          char[] stringToCharArray = string.toCharArray();
          float f = 0.0F;
          for (char c : stringToCharArray) {
            this.renderer.drawString(String.valueOf(c), 2.0F + f, 2.0F, ColorUtil.rainbow(arrayOfInt[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
            f += this.renderer.getStringWidth(String.valueOf(c));
            arrayOfInt[0] = arrayOfInt[0] + 1;
          } 
        } 
      } else {
        this.renderer.drawString(string, 2.0F, 2.0F, this.color, true);
      } 
    } 
    if (((Boolean)this.waterMark.getValue()).booleanValue() && ((Boolean)this.watermark2.getValue()).booleanValue()) {
      if (((Integer)this.alpha.getValue()).intValue() >= 0)
        RenderUtil.drawRectangleCorrectly(((Integer)this.compactX.getValue()).intValue(), ((Integer)this.compactY.getValue()).intValue(), 10 + this.renderer.getStringWidth((ClickGui.getInstance()).clientName.getValueAsString() + "Sense | FPS:" + Minecraft.debugFPS + " | TPS:" + M3dC3t.serverManager.getTPS() + " | Ping:" + M3dC3t.serverManager.getPing()), 15, ColorUtil.toRGBA(20, 20, 20, ((Integer)this.alpha.getValue()).intValue())); 
      String string = (ClickGui.getInstance()).clientName.getValueAsString() + "Sense | FPS:" + Minecraft.debugFPS + " | TPS:" + M3dC3t.serverManager.getTPS() + " | Ping:" + M3dC3t.serverManager.getPing();
      this.color = ColorUtil.toRGBA(((Integer)(ClickGui.getInstance()).red.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).green.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).blue.getValue()).intValue());
      if (((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue()) {
        RenderUtil.drawRectangleCorrectly(((Integer)this.compactX.getValue()).intValue(), 0 + ((Integer)this.compactY.getValue()).intValue(), 10 + this.renderer.getStringWidth((ClickGui.getInstance()).clientName.getValueAsString() + "Sense | FPS:" + Minecraft.debugFPS + " | TPS:" + M3dC3t.serverManager.getTPS() + " | Ping:" + M3dC3t.serverManager.getPing()), 1, ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB());
        if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
          this.renderer.drawString(string, (((Integer)this.compactX.getValue()).intValue() + 5), (((Integer)this.compactY.getValue()).intValue() + 4), ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
        } else {
          int[] arrayOfInt = { 1 };
          char[] stringToCharArray = string.toCharArray();
          float f = 0.0F;
          for (char c : stringToCharArray) {
            this.renderer.drawString(String.valueOf(c), (((Integer)this.compactX.getValue()).intValue() + 5) + f, (((Integer)this.compactY.getValue()).intValue() + 4), ColorUtil.rainbow(arrayOfInt[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
            f += this.renderer.getStringWidth(String.valueOf(c));
            arrayOfInt[0] = arrayOfInt[0] + 1;
          } 
        } 
      } else {
        this.renderer.drawString(string, (((Integer)this.compactX.getValue()).intValue() + 5), (((Integer)this.compactY.getValue()).intValue() + 4), this.color, true);
        RenderUtil.drawRectangleCorrectly(((Integer)this.compactX.getValue()).intValue(), ((Integer)this.compactY.getValue()).intValue(), 10 + this.renderer.getStringWidth((ClickGui.getInstance()).clientName.getValueAsString() + "Sense | FPS:" + Minecraft.debugFPS + " | TPS:" + M3dC3t.serverManager.getTPS() + " | Ping:" + M3dC3t.serverManager.getPing()), 1, this.color);
      } 
    } 
    this.color = ColorUtil.toRGBA(((Integer)(ClickGui.getInstance()).red.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).green.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).blue.getValue()).intValue());
    int y = 10;
    this.color = ColorUtil.toRGBA(((Integer)(ClickGui.getInstance()).red.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).green.getValue()).intValue(), ((Integer)(ClickGui.getInstance()).blue.getValue()).intValue());
    if (((Boolean)this.FriendList.getValue()).booleanValue())
      renderFriends(); 
    int[] counter1 = { 1 };
    int j = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat && !((Boolean)this.renderingUp.getValue()).booleanValue()) ? 14 : 0;
    if (((Boolean)this.arrayList.getValue()).booleanValue())
      if (((Boolean)this.renderingUp.getValue()).booleanValue()) {
        if (this.renderingMode.getValue() == RenderingMode.ABC) {
          for (int k = 0; k < M3dC3t.moduleManager.sortedModulesABC.size(); k++) {
            String str = M3dC3t.moduleManager.sortedModulesABC.get(k);
            this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str)), (2 + j * 4 + ((Integer)this.arraylisty.getValue()).intValue()), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
            j += 3;
            counter1[0] = counter1[0] + 1;
          } 
        } else {
          for (int k = 0; k < M3dC3t.moduleManager.sortedModules.size(); k++) {
            Module module = M3dC3t.moduleManager.sortedModules.get(k);
            String str2 = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
            this.renderer.drawString(str2, (width - 2 - this.renderer.getStringWidth(str2)), (2 + j * 4 + ((Integer)this.arraylisty.getValue()).intValue()), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
            j += 3;
            counter1[0] = counter1[0] + 1;
          } 
        } 
      } else if (this.renderingMode.getValue() == RenderingMode.ABC) {
        for (int k = 0; k < M3dC3t.moduleManager.sortedModulesABC.size(); k++) {
          String str = M3dC3t.moduleManager.sortedModulesABC.get(k);
          j += 12;
          this.renderer.drawString(str, (width - 2 - this.renderer.getStringWidth(str)), (height - j), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
      } else {
        for (int k = 0; k < M3dC3t.moduleManager.sortedModules.size(); k++) {
          Module module = M3dC3t.moduleManager.sortedModules.get(k);
          String str2 = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
          j += 12;
          this.renderer.drawString(str2, (width - 2 - this.renderer.getStringWidth(str2)), (height - j), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
      }  
    String grayString = ((Boolean)this.grayNess.getValue()).booleanValue() ? String.valueOf(ChatFormatting.GRAY) : "";
    int i = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat && ((Boolean)this.renderingUp.getValue()).booleanValue()) ? 13 : (((Boolean)this.renderingUp.getValue()).booleanValue() ? -2 : 0);
    if (((Boolean)this.renderingUp.getValue()).booleanValue()) {
      if (((Boolean)this.server.getValue()).booleanValue()) {
        String sText = grayString + "IP " + ChatFormatting.WHITE + (mc.isSingleplayer() ? "SinglePlayer" : ((ServerData)Objects.requireNonNull((T)mc.getCurrentServerData())).serverIP);
        i += 10;
        this.renderer.drawString(sText, (width - this.renderer.getStringWidth(sText) - 2), (height - 2 - i), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
        counter1[0] = counter1[0] + 1;
      } 
      if (((Boolean)this.speed.getValue()).booleanValue()) {
        String str2 = grayString + "Speed " + ChatFormatting.WHITE + M3dC3t.speedManager.getSpeedKpH() + " km/h";
        i += 10;
        this.renderer.drawString(str2, (width - this.renderer.getStringWidth(str2) - 2), (height - 2 - i), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
        counter1[0] = counter1[0] + 1;
      } 
      if (((Boolean)this.tps.getValue()).booleanValue()) {
        String str2 = grayString + "TPS " + ChatFormatting.WHITE + M3dC3t.serverManager.getTPS();
        i += 10;
        this.renderer.drawString(str2, (width - this.renderer.getStringWidth(str2) - 2), (height - 2 - i), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
        counter1[0] = counter1[0] + 1;
      } 
      String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
      String sText2 = grayString + "Server " + ChatFormatting.WHITE + (mc.isSingleplayer() ? "SinglePlayer" : ((ServerData)Objects.requireNonNull((T)mc.getCurrentServerData())).serverIP);
      String str4 = grayString + "Ping " + ChatFormatting.WHITE + M3dC3t.serverManager.getPing();
      if (this.renderer.getStringWidth(str4) > this.renderer.getStringWidth(fpsText)) {
        if (((Boolean)this.ping.getValue()).booleanValue()) {
          i += 10;
          this.renderer.drawString(str4, (width - this.renderer.getStringWidth(str4) - 2), (height - 2 - i), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
        if (((Boolean)this.fps.getValue()).booleanValue()) {
          i += 10;
          this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (height - 2 - i), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
      } else {
        if (((Boolean)this.fps.getValue()).booleanValue()) {
          i += 10;
          this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (height - 2 - i), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
        if (((Boolean)this.ping.getValue()).booleanValue()) {
          i += 10;
          this.renderer.drawString(str4, (width - this.renderer.getStringWidth(str4) - 2), (height - 2 - i), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
      } 
    } else {
      if (((Boolean)this.server.getValue()).booleanValue()) {
        String sText = grayString + "IP " + ChatFormatting.WHITE + (mc.isSingleplayer() ? "SinglePlayer" : ((ServerData)Objects.requireNonNull((T)mc.getCurrentServerData())).serverIP);
        this.renderer.drawString(sText, (width - this.renderer.getStringWidth(sText) - 2), (2 + i++ * 10), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
        counter1[0] = counter1[0] + 1;
      } 
      if (((Boolean)this.speed.getValue()).booleanValue()) {
        String str2 = grayString + "Speed " + ChatFormatting.WHITE + M3dC3t.speedManager.getSpeedKpH() + " km/h";
        this.renderer.drawString(str2, (width - this.renderer.getStringWidth(str2) - 2), (2 + i++ * 10), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
        counter1[0] = counter1[0] + 1;
      } 
      if (((Boolean)this.tps.getValue()).booleanValue()) {
        String str2 = grayString + "TPS " + ChatFormatting.WHITE + M3dC3t.serverManager.getTPS();
        this.renderer.drawString(str2, (width - this.renderer.getStringWidth(str2) - 2), (2 + i++ * 10), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
        counter1[0] = counter1[0] + 1;
      } 
      String fpsText = grayString + "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
      String str5 = grayString + "Ping " + ChatFormatting.WHITE + M3dC3t.serverManager.getPing();
      if (this.renderer.getStringWidth(str5) > this.renderer.getStringWidth(fpsText)) {
        if (((Boolean)this.ping.getValue()).booleanValue()) {
          this.renderer.drawString(str5, (width - this.renderer.getStringWidth(str5) - 2), (2 + i++ * 10), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
        if (((Boolean)this.fps.getValue()).booleanValue()) {
          this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (2 + i++ * 10), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
      } else {
        if (((Boolean)this.fps.getValue()).booleanValue()) {
          this.renderer.drawString(fpsText, (width - this.renderer.getStringWidth(fpsText) - 2), (2 + i++ * 10), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
        if (((Boolean)this.ping.getValue()).booleanValue()) {
          this.renderer.drawString(str5, (width - this.renderer.getStringWidth(str5) - 2), (2 + i++ * 10), ((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue() ? (((ClickGui.getInstance()).rainbowModeA.getValue() == ClickGui.rainbowModeArray.Up) ? ColorUtil.rainbow(counter1[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB() : ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB()) : this.color, true);
          counter1[0] = counter1[0] + 1;
        } 
      } 
    } 
    boolean inHell = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell");
    int posX = (int)mc.player.posX;
    int posY = (int)mc.player.posY;
    int posZ = (int)mc.player.posZ;
    float nether = inHell ? 8.0F : 0.125F;
    int hposX = (int)(mc.player.posX * nether);
    int hposZ = (int)(mc.player.posZ * nether);
    i = (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat) ? 14 : 0;
    String coordinates = ChatFormatting.WHITE + "XYZ " + ChatFormatting.RESET + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]" + ChatFormatting.RESET) : (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]"));
    String direction = ((Boolean)this.coords.getValue()).booleanValue() ? M3dC3t.rotationManager.getDirection4D(false) : "";
    String coords = ((Boolean)this.coords.getValue()).booleanValue() ? coordinates : "";
    i += 10;
    if (((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue()) {
      String rainbowCoords = ((Boolean)this.coords.getValue()).booleanValue() ? ("XYZ " + posX + ", " + posY + ", " + posZ + " [" + hposX + ", " + hposZ + "]") : "";
      if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
        this.renderer.drawString(direction, 2.0F, (height - i - 11), ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
        this.renderer.drawString(rainbowCoords, 2.0F, (height - i), ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
      } else {
        int[] counter2 = { 1 };
        char[] stringToCharArray2 = direction.toCharArray();
        float s = 0.0F;
        for (char c2 : stringToCharArray2) {
          this.renderer.drawString(String.valueOf(c2), 2.0F + s, (height - i - 11), ColorUtil.rainbow(counter2[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
          s += this.renderer.getStringWidth(String.valueOf(c2));
          counter2[0] = counter2[0] + 1;
        } 
        int[] counter3 = { 1 };
        char[] stringToCharArray3 = rainbowCoords.toCharArray();
        float u = 0.0F;
        for (char c3 : stringToCharArray3) {
          this.renderer.drawString(String.valueOf(c3), 2.0F + u, (height - i), ColorUtil.rainbow(counter3[0] * ((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
          u += this.renderer.getStringWidth(String.valueOf(c3));
          counter3[0] = counter3[0] + 1;
        } 
      } 
    } else {
      this.renderer.drawString(direction, 2.0F, (height - i - 11), this.color, true);
      this.renderer.drawString(coords, 2.0F, (height - i), this.color, true);
    } 
    if (((Boolean)this.lag.getValue()).booleanValue())
      renderLag(); 
  }
  
  public Map<String, Integer> getTextRadarPlayers() {
    return EntityUtil.getTextRadarPlayers();
  }
  
  public void renderLag() {
    int width = this.renderer.scaledWidth;
    if (M3dC3t.serverManager.isServerNotResponding()) {
      String text = ChatFormatting.RED + "Server not responding " + MathUtil.round((float)M3dC3t.serverManager.serverRespondingTime() / 1000.0F, 1) + "s.";
      this.renderer.drawString(text, width / 2.0F - this.renderer.getStringWidth(text) / 2.0F + 2.0F, 20.0F, this.color, true);
    } 
  }
  
  private void renderFriends() {
    List<String> friends = new ArrayList<>();
    for (EntityPlayer player : mc.world.playerEntities) {
      if (M3dC3t.friendManager.isFriend(player.getName()))
        friends.add(player.getName()); 
    } 
    if (((Boolean)(ClickGui.getInstance()).rainbow.getValue()).booleanValue()) {
      if ((ClickGui.getInstance()).rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
        int y = ((Integer)this.FriendListY.getValue()).intValue();
        if (friends.isEmpty()) {
          this.renderer.drawString("No friends", 0.0F, y, ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
        } else {
          this.renderer.drawString("Friends:", 0.0F, y, ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
          y += 12;
          for (String friend : friends) {
            this.renderer.drawString(friend, 0.0F, y, ColorUtil.rainbow(((Integer)(ClickGui.getInstance()).rainbowHue.getValue()).intValue()).getRGB(), true);
            y += 12;
          } 
        } 
      } 
    } else {
      int y = ((Integer)this.FriendListY.getValue()).intValue();
      if (friends.isEmpty()) {
        this.renderer.drawString("No Webstas online", 0.0F, y, this.color, true);
      } else {
        this.renderer.drawString("Webstas near you:", 0.0F, y, this.color, true);
        y += 12;
        for (String friend : friends) {
          this.renderer.drawString(friend, 0.0F, y, this.color, true);
          y += 12;
        } 
      } 
    } 
  }
  
  private static HUD INSTANCE = new HUD();
  
  private final Setting<Boolean> grayNess;
  
  private final Setting<Boolean> renderingUp;
  
  private final Setting<Boolean> arrayList;
  
  private final Setting<Boolean> coords;
  
  private final Setting<Boolean> speed;
  
  private final Setting<Boolean> server;
  
  private final Setting<Boolean> burrow;
  
  private final Setting<Boolean> ping;
  
  private final Setting<Boolean> tps;
  
  private final Setting<Boolean> fps;
  
  private final Map<String, Integer> players;
  
  private final Setting<Boolean> lag;
  
  public Setting<Boolean> FriendList;
  
  public Setting<Integer> arraylisty;
  
  public Setting<Integer> FriendListY;
  
  public Setting<Integer> sklisty;
  
  public Setting<Integer> sklistY;
  
  public Setting<TextUtil.Color> bracketColor;
  
  public Setting<TextUtil.Color> commandColor;
  
  public Setting<Boolean> notifyToggles;
  
  public Setting<RenderingMode> renderingMode;
  
  public Setting<Integer> lagTime;
  
  private int color;
  
  public final Setting<Boolean> moduleInfo;
  
  final Setting<Boolean> waterMark;
  
  public final Setting<Boolean> watermark2;
  
  public final Setting<Integer> compactX;
  
  public final Setting<Integer> compactY;
  
  public final Setting<Integer> alpha;
  
  public enum RenderingMode {
    Length, ABC;
  }
}
