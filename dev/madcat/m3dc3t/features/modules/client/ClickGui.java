//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.event.events.ClientEvent;
import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.features.command.Command;
import dev.madcat.m3dc3t.features.gui.M3dC3tGui;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import dev.madcat.m3dc3t.util.TextUtil;
import dev.madcat.m3dc3t.util.Util;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClickGui extends Module {
  private static ClickGui INSTANCE = new ClickGui();
  
  private final Setting<Settings> setting = register(new Setting("Settings", Settings.Gui));
  
  public Setting<String> prefix = register(new Setting("Prefix", ".", v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<String> clientName = register(new Setting("ClientName", "CuteCat", v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Integer> red = register(new Setting("Red", Integer.valueOf(140), Integer.valueOf(0), Integer.valueOf(255), v -> (!((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Integer> green = register(new Setting("Green", Integer.valueOf(140), Integer.valueOf(0), Integer.valueOf(255), v -> (!((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Integer> blue = register(new Setting("Blue", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(255), v -> (!((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Integer> hoverAlpha = register(new Setting("Alpha", Integer.valueOf(225), Integer.valueOf(0), Integer.valueOf(255), v -> (!((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Integer> alpha = register(new Setting("HoverAlpha", Integer.valueOf(240), Integer.valueOf(0), Integer.valueOf(255), v -> (!((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Integer> alphaBox = register(new Setting("AlphaBox", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Color)));
  
  public Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Boolean> moduleDescription = register(new Setting("Description", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Boolean> moduleIcon = register(new Setting("Icon", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Boolean> snowing = register(new Setting("Snowing", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Integer> iconmode = register(new Setting("SettingIcon", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(5), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Integer> moduleiconmode = register(new Setting("ModuleIcon", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(2), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Integer> moduleWidth = register(new Setting("ModuleWidth", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(40), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Integer> moduleDistance = register(new Setting("ModuleDistance", Integer.valueOf(30), Integer.valueOf(0), Integer.valueOf(50), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Boolean> rainbowg = register(new Setting("Rainbow", Boolean.FALSE, v -> (this.setting.getValue() == Settings.Gradient)));
  
  public Setting<Boolean> rainbow = register(new Setting("Rainbow", Boolean.FALSE, v -> (this.setting.getValue() == Settings.Color)));
  
  public Setting<rainbowMode> rainbowModeHud = register(new Setting("HUD", rainbowMode.Static, v -> (((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<rainbowModeArray> rainbowModeA = register(new Setting("ArrayList", rainbowModeArray.Up, v -> (((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Integer> rainbowHue = register(new Setting("Delay", Integer.valueOf(600), Integer.valueOf(0), Integer.valueOf(600), v -> (((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Float> rainbowBrightness = register(new Setting("Brightness ", Float.valueOf(255.0F), Float.valueOf(1.0F), Float.valueOf(255.0F), v -> (((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Float> rainbowSaturation = register(new Setting("Saturation", Float.valueOf(255.0F), Float.valueOf(1.0F), Float.valueOf(255.0F), v -> (((Boolean)this.rainbow.getValue()).booleanValue() && this.setting.getValue() == Settings.Color)));
  
  public Setting<Boolean> background = register(new Setting("BackGround", Boolean.valueOf(false), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Boolean> blur = register(new Setting("Blur", Boolean.valueOf(true), v -> (this.setting.getValue() == Settings.Gui)));
  
  public Setting<Integer> g_red = register(new Setting("RedL", Integer.valueOf(105), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Gradient)));
  
  public Setting<Integer> g_green = register(new Setting("GreenL", Integer.valueOf(162), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Gradient)));
  
  public Setting<Integer> g_blue = register(new Setting("BlueL", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Gradient)));
  
  public Setting<Integer> g_red1 = register(new Setting("RedR", Integer.valueOf(143), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Gradient)));
  
  public Setting<Integer> g_green1 = register(new Setting("GreenR", Integer.valueOf(140), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Gradient)));
  
  public Setting<Integer> g_blue1 = register(new Setting("BlueR", Integer.valueOf(213), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Gradient)));
  
  public Setting<Integer> g_alpha = register(new Setting("AlphaL", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Gradient)));
  
  public Setting<Integer> g_alpha1 = register(new Setting("AlphaR", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> (this.setting.getValue() == Settings.Gradient)));
  
  public ClickGui() {
    super("ClickGui", "Module interface.", Module.Category.CLIENT, true, false, false);
    setInstance();
  }
  
  public static ClickGui getInstance() {
    if (INSTANCE == null)
      INSTANCE = new ClickGui(); 
    return INSTANCE;
  }
  
  public void onRender2D(Render2DEvent event) {
    if (((Boolean)this.background.getValue()).booleanValue()) {
      RenderUtil.drawRect(0.0F, 0.0F, 1000.0F, 550.0F, ColorUtil.toRGBA(20, 20, 20, 150));
      RenderUtil.drawGradientRect(0, 200, 1000, 350, ColorUtil.toRGBA(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), 0), ColorUtil.toRGBA(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), 255));
    } 
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  @SubscribeEvent
  public void onSettingChange(ClientEvent event) {
    if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
      if (event.getSetting().equals(this.prefix)) {
        M3dC3t.commandManager.setPrefix((String)this.prefix.getPlannedValue());
        Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + M3dC3t.commandManager.getPrefix());
      } 
      M3dC3t.colorManager.setColor(((Integer)this.red.getPlannedValue()).intValue(), ((Integer)this.green.getPlannedValue()).intValue(), ((Integer)this.blue.getPlannedValue()).intValue(), ((Integer)this.hoverAlpha.getPlannedValue()).intValue());
    } 
  }
  
  public String getCommandMessage() {
    return TextUtil.coloredString("[", (TextUtil.Color)(HUD.getInstance()).bracketColor.getPlannedValue()) + TextUtil.coloredString((getInstance()).clientName.getValueAsString(), (TextUtil.Color)(HUD.getInstance()).commandColor.getPlannedValue()) + TextUtil.coloredString("]", (TextUtil.Color)(HUD.getInstance()).bracketColor.getPlannedValue());
  }
  
  public void onEnable() {
    mc.displayGuiScreen((GuiScreen)M3dC3tGui.getClickGui());
  }
  
  public void onLoad() {
    M3dC3t.colorManager.setColor(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.hoverAlpha.getValue()).intValue());
    M3dC3t.commandManager.setPrefix((String)this.prefix.getValue());
  }
  
  public void onUpdate() {
    if (((Boolean)this.blur.getValue()).booleanValue()) {
      if (OpenGlHelper.shadersSupported && Util.mc.getRenderViewEntity() instanceof net.minecraft.entity.player.EntityPlayer) {
        if (Util.mc.entityRenderer.getShaderGroup() != null)
          Util.mc.entityRenderer.getShaderGroup().deleteShaderGroup(); 
        try {
          Util.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if (Util.mc.entityRenderer.getShaderGroup() != null && Util.mc.currentScreen == null) {
        Util.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
      } 
    } else if (Util.mc.entityRenderer.getShaderGroup() != null) {
      Util.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
    } 
  }
  
  public void onTick() {
    M3dC3t.commandManager.setClientMessage(getCommandMessage());
    if (!(mc.currentScreen instanceof M3dC3tGui))
      disable(); 
  }
  
  public void onDisable() {
    if (mc.currentScreen instanceof M3dC3tGui)
      Util.mc.displayGuiScreen(null); 
    if (Util.mc.entityRenderer.getShaderGroup() != null)
      Util.mc.entityRenderer.getShaderGroup().deleteShaderGroup(); 
  }
  
  public enum rainbowModeArray {
    Static, Up;
  }
  
  public enum rainbowMode {
    Static, Sideway;
  }
  
  public enum Settings {
    Gui, Color, Gradient;
  }
}
