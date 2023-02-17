//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.gui;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.Feature;
import dev.madcat.m3dc3t.features.gui.components.Component;
import dev.madcat.m3dc3t.features.gui.components.items.DescriptionDisplay;
import dev.madcat.m3dc3t.features.gui.components.items.Item;
import dev.madcat.m3dc3t.features.gui.components.items.buttons.Button;
import dev.madcat.m3dc3t.features.gui.components.items.buttons.ModuleButton;
import dev.madcat.m3dc3t.features.gui.particle.Snow;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.ClickGui;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class M3dC3tGui extends GuiScreen {
  private final ArrayList<Snow> _snowList = new ArrayList<>();
  
  private final ArrayList<Component> components = new ArrayList<>();
  
  public M3dC3tGui() {
    setInstance();
    load();
  }
  
  public static M3dC3tGui getInstance() {
    if (INSTANCE == null)
      INSTANCE = new M3dC3tGui(); 
    return INSTANCE;
  }
  
  public static M3dC3tGui getClickGui() {
    return getInstance();
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  private void load() {
    int x = -84;
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      for (int y = 0; y < 3; y++) {
        Snow snow = new Snow(25 * i, y * -50, random.nextInt(3) + 1, random.nextInt(2) + 1);
        this._snowList.add(snow);
      } 
    } 
    for (Module.Category category : M3dC3t.moduleManager.getCategories()) {
      x += 90;
      this.components.add(new Component(category.getName(), x, 34, true) {
            public void setupItems() {
              counter1 = new int[] { 1 };
              M3dC3t.moduleManager.getModulesByCategory(category).forEach(module -> {
                    if (!module.hidden)
                      addButton((Button)new ModuleButton(module)); 
                  });
            }
          });
    } 
    this.components.forEach(components -> components.getItems().sort(Comparator.comparing(Feature::getName)));
  }
  
  public void updateModule(Module module) {
    for (Component component : this.components) {
      for (Item item : component.getItems()) {
        if (!(item instanceof ModuleButton))
          continue; 
        ModuleButton button = (ModuleButton)item;
        Module mod = button.getModule();
        if (module == null || !module.equals(mod))
          continue; 
        button.initSettings();
      } 
    } 
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    ClickGui clickGui = ClickGui.getInstance();
    descriptionDisplay.setDraw(false);
    checkMouseWheel();
    drawDefaultBackground();
    this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
    if (descriptionDisplay.shouldDraw() && ((Boolean)clickGui.moduleDescription.getValue()).booleanValue())
      descriptionDisplay.drawScreen(mouseX, mouseY, partialTicks); 
    ScaledResolution res = new ScaledResolution(this.mc);
    if (!this._snowList.isEmpty() && ((Boolean)(ClickGui.getInstance()).snowing.getValue()).booleanValue())
      this._snowList.forEach(snow -> snow.Update(res)); 
  }
  
  public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
    this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
  }
  
  public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
    this.components.forEach(components -> components.mouseReleased(mouseX, mouseY, releaseButton));
  }
  
  public boolean doesGuiPauseGame() {
    return false;
  }
  
  public final ArrayList<Component> getComponents() {
    return this.components;
  }
  
  public DescriptionDisplay getDescriptionDisplay() {
    return descriptionDisplay;
  }
  
  public void checkMouseWheel() {
    int dWheel = Mouse.getDWheel();
    if (dWheel < 0) {
      this.components.forEach(component -> component.setY(component.getY() - 10));
    } else if (dWheel > 0) {
      this.components.forEach(component -> component.setY(component.getY() + 10));
    } 
  }
  
  public int getTextOffset() {
    return -6;
  }
  
  public Component getComponentByName(String name) {
    for (Component component : this.components) {
      if (!component.getName().equalsIgnoreCase(name))
        continue; 
      return component;
    } 
    return null;
  }
  
  public void keyTyped(char typedChar, int keyCode) throws IOException {
    super.keyTyped(typedChar, keyCode);
    this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
  }
  
  private static M3dC3tGui INSTANCE = new M3dC3tGui();
  
  private static final DescriptionDisplay descriptionDisplay;
  
  private static M3dC3tGui M3dC3tGui;
  
  static {
    descriptionDisplay = new DescriptionDisplay("", 0.0F, 0.0F);
  }
}
