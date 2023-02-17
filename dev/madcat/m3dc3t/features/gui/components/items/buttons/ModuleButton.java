//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui.components.items.buttons;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.gui.M3dC3tGui;
import dev.madcat.m3dc3t.features.gui.components.Component;
import dev.madcat.m3dc3t.features.gui.components.items.DescriptionDisplay;
import dev.madcat.m3dc3t.features.gui.components.items.Item;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import dev.madcat.m3dc3t.features.setting.Setting;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModuleButton extends Button {
  private final Module module;
  
  private int logs;
  
  private List<Item> items = new ArrayList<>();
  
  private boolean subOpen;
  
  public ModuleButton(Module module) {
    super(module.getName());
    this.module = module;
    initSettings();
  }
  
  public static void drawCompleteImage(float posX, float posY, int width, int height) {
    GL11.glPushMatrix();
    GL11.glTranslatef(posX, posY, 0.0F);
    GL11.glBegin(7);
    GL11.glTexCoord2f(0.0F, 0.0F);
    GL11.glVertex3f(0.0F, 0.0F, 0.0F);
    GL11.glTexCoord2f(0.0F, 1.0F);
    GL11.glVertex3f(0.0F, height, 0.0F);
    GL11.glTexCoord2f(1.0F, 1.0F);
    GL11.glVertex3f(width, height, 0.0F);
    GL11.glTexCoord2f(1.0F, 0.0F);
    GL11.glVertex3f(width, 0.0F, 0.0F);
    GL11.glEnd();
    GL11.glPopMatrix();
  }
  
  public static float fa(float var0) {
    float f = 0.0F;
    var0 %= 360.0F;
    if (f >= 180.0F)
      var0 -= 360.0F; 
    if (var0 < -180.0F)
      var0 += 360.0F; 
    return var0;
  }
  
  public static void drawModalRect(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
    Gui.drawScaledCustomSizeModalRect(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
  }
  
  public void initSettings() {
    ArrayList<Item> newItems = new ArrayList<>();
    if (!this.module.getSettings().isEmpty())
      for (Setting setting : this.module.getSettings()) {
        if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled"))
          newItems.add(new BooleanButton(setting)); 
        if (setting.getValue() instanceof dev.madcat.m3dc3t.features.setting.Bind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud"))
          newItems.add(new BindButton(setting)); 
        if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName"))
          newItems.add(new StringButton(setting)); 
        if (setting.isNumberSetting() && setting.hasRestriction()) {
          newItems.add(new Slider(setting));
          continue;
        } 
        if (!setting.isEnumSetting())
          continue; 
        newItems.add(new EnumButton(setting));
      }  
    newItems.add(new BindButton(this.module.getSettingByName("Keybind")));
    this.items = newItems;
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    if (isHovering(mouseX, mouseY)) {
      DescriptionDisplay descriptionDisplay = M3dC3tGui.getInstance().getDescriptionDisplay();
      descriptionDisplay.setDescription(this.module.getDescription());
      descriptionDisplay.setLocation((mouseX + 2), (mouseY + 1));
      descriptionDisplay.setDraw(true);
    } 
    super.drawScreen(mouseX, mouseY, partialTicks);
    if (!this.items.isEmpty())
      if (((Integer)(ClickGui.getInstance()).iconmode.getValue()).intValue() == 2 || ((Integer)(ClickGui.getInstance()).iconmode.getValue()).intValue() == 3) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        if (((Integer)(ClickGui.getInstance()).iconmode.getValue()).intValue() == 2)
          Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/setting.png")); 
        if (((Integer)(ClickGui.getInstance()).iconmode.getValue()).intValue() == 3)
          Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/setting2.png")); 
        GlStateManager.translate(getX() + getWidth() - 6.7F, getY() + 7.7F - 0.3F, 0.0F);
        GlStateManager.rotate(fa(this.logs), 0.0F, 0.0F, 1.0F);
        drawModalRect(-5, -5, 0.0F, 0.0F, 10, 10, 10, 10, 10.0F, 10.0F);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        if (this.subOpen) {
          float height = 1.0F;
          this.logs += 5;
          for (Item item : this.items) {
            Component.counter1[0] = Component.counter1[0] + 1;
            if (!item.isHidden()) {
              item.setLocation(this.x + 1.0F, this.y + (height += 15.0F));
              item.setHeight(15);
              item.setWidth(this.width - 9);
              item.drawScreen(mouseX, mouseY, partialTicks);
            } 
            item.update();
          } 
        } else {
          this.logs = 0;
        } 
      } else if (((Integer)(ClickGui.getInstance()).iconmode.getValue()).intValue() == 0) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        if (this.subOpen) {
          M3dC3t.textManager.drawStringlogo("_", this.x - 4.5F + this.width - 7.4F, this.y - 2.2F - M3dC3tGui.getClickGui().getTextOffset(), -1);
        } else {
          M3dC3t.textManager.drawStringlogo("A", this.x - 4.5F + this.width - 7.4F, this.y - 2.2F - M3dC3tGui.getClickGui().getTextOffset(), -1);
        } 
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        if (this.subOpen) {
          float height = 1.0F;
          for (Item item : this.items) {
            Component.counter1[0] = Component.counter1[0] + 1;
            if (!item.isHidden()) {
              item.setLocation(this.x + 1.0F, this.y + (height += 15.0F));
              item.setHeight(15);
              item.setWidth(this.width - 9);
              item.drawScreen(mouseX, mouseY, partialTicks);
            } 
            item.update();
          } 
        } 
      } else if (((Integer)(ClickGui.getInstance()).iconmode.getValue()).intValue() == 1) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/setting3.png"));
        drawCompleteImage(this.x - 1.5F + this.width - 7.4F, this.y - 2.2F - M3dC3tGui.getClickGui().getTextOffset(), 8, 8);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        if (this.subOpen) {
          float height = 1.0F;
          for (Item item : this.items) {
            Component.counter1[0] = Component.counter1[0] + 1;
            if (!item.isHidden()) {
              item.setLocation(this.x + 1.0F, this.y + (height += 15.0F));
              item.setHeight(15);
              item.setWidth(this.width - 9);
              item.drawScreen(mouseX, mouseY, partialTicks);
            } 
            item.update();
          } 
        } 
      } else if (((Integer)(ClickGui.getInstance()).iconmode.getValue()).intValue() == 4) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        if (this.subOpen) {
          M3dC3t.textManager.drawString("-", this.x + this.width - 7.4F, this.y - 2.2F - M3dC3tGui.getClickGui().getTextOffset(), -1, true);
        } else {
          M3dC3t.textManager.drawString("+", this.x + this.width - 7.4F, this.y - 2.2F - M3dC3tGui.getClickGui().getTextOffset(), -1, true);
        } 
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        if (this.subOpen) {
          float height = 1.0F;
          for (Item item : this.items) {
            Component.counter1[0] = Component.counter1[0] + 1;
            if (!item.isHidden()) {
              item.setLocation(this.x + 1.0F, this.y + (height += 15.0F));
              item.setHeight(15);
              item.setWidth(this.width - 9);
              item.drawScreen(mouseX, mouseY, partialTicks);
            } 
            item.update();
          } 
        } 
      } else if (((Integer)(ClickGui.getInstance()).iconmode.getValue()).intValue() == 5) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        if (this.subOpen) {
          float height = 1.0F;
          for (Item item : this.items) {
            Component.counter1[0] = Component.counter1[0] + 1;
            if (!item.isHidden()) {
              item.setLocation(this.x + 1.0F, this.y + (height += 15.0F));
              item.setHeight(15);
              item.setWidth(this.width - 9);
              item.drawScreen(mouseX, mouseY, partialTicks);
            } 
            item.update();
          } 
        } 
      }  
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (!this.items.isEmpty()) {
      if (mouseButton == 1 && isHovering(mouseX, mouseY)) {
        this.subOpen = !this.subOpen;
        mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      } 
      if (this.subOpen)
        for (Item item : this.items) {
          if (item.isHidden())
            continue; 
          item.mouseClicked(mouseX, mouseY, mouseButton);
        }  
    } 
  }
  
  public void onKeyTyped(char typedChar, int keyCode) {
    super.onKeyTyped(typedChar, keyCode);
    if (!this.items.isEmpty() && this.subOpen)
      for (Item item : this.items) {
        if (item.isHidden())
          continue; 
        item.onKeyTyped(typedChar, keyCode);
      }  
  }
  
  public int getHeight() {
    if (this.subOpen) {
      int height = 14;
      for (Item item : this.items) {
        if (item.isHidden())
          continue; 
        height += item.getHeight() + 1;
      } 
      return height + 2;
    } 
    return 14;
  }
  
  public Module getModule() {
    return this.module;
  }
  
  public void toggle() {
    this.module.toggle();
  }
  
  public boolean getState() {
    return this.module.isEnabled();
  }
}
