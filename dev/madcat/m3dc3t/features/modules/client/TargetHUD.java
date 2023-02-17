//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.client;

import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import java.awt.Color;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TargetHUD extends Module {
  private final Setting<Integer> x = register(new Setting("X", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(2000)));
  
  private final Setting<Integer> y = register(new Setting("Y", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(2000)));
  
  private final Setting<Integer> backgroundAlpha = register(new Setting("Alpha", Integer.valueOf(80), Integer.valueOf(0), Integer.valueOf(255)));
  
  EntityLivingBase target;
  
  private static double applyAsDouble(EntityLivingBase entityLivingBase) {
    return entityLivingBase.getDistance((Entity)mc.player);
  }
  
  private static boolean checkIsNotPlayer(Entity entity) {
    return !entity.equals(mc.player);
  }
  
  public TargetHUD() {
    super("TargetHUD", "Target indicator.", Module.Category.CLIENT, true, false, false);
    this.target = (EntityLivingBase)mc.player;
  }
  
  public synchronized void onTick() {
    if (!fullNullCheck()) {
      List<EntityLivingBase> entities = new LinkedList<>();
      mc.world.loadedEntityList
        .stream()
        .filter(EntityPlayer.class::isInstance)
        .filter(TargetHUD::checkIsNotPlayer)
        .map(EntityLivingBase.class::cast)
        .sorted(Comparator.comparingDouble(TargetHUD::applyAsDouble))
        .forEach(entities::add);
      if (!entities.isEmpty()) {
        this.target = entities.get(0);
      } else {
        this.target = (EntityLivingBase)mc.player;
      } 
      if (mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)
        this.target = (EntityLivingBase)mc.player; 
    } 
  }
  
  public synchronized void onRender2D(Render2DEvent event) {
    if (this.target != null && !this.target.isDead && 
      !fullNullCheck() && this.target != null) {
      int backgroundStopX;
      FontRenderer fr = mc.fontRenderer;
      int color = (this.target.getHealth() / this.target.getMaxHealth() > 0.66F) ? -16711936 : ((this.target.getHealth() / this.target.getMaxHealth() > 0.33F) ? -26368 : -65536);
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      GuiInventory.drawEntityOnScreen(((Integer)this.x.getValue()).intValue() + 15, ((Integer)this.y.getValue()).intValue() + 32, 15, 1.0F, 1.0F, this.target);
      List<ItemStack> armorList = new LinkedList<>();
      List<ItemStack> _armorList = new LinkedList<>();
      this.target.getArmorInventoryList().forEach(itemStack -> {
            if (!itemStack.isEmpty())
              _armorList.add(itemStack); 
          });
      for (int i = _armorList.size() - 1; i >= 0; i--)
        armorList.add(_armorList.get(i)); 
      int armorSize = 0;
      switch (armorList.size()) {
        case 0:
          if (!this.target.getHeldItemMainhand().isEmpty() && !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemMainhand(), ((Integer)this.x.getValue()).intValue() + 28, ((Integer)this.y.getValue()).intValue() + 18);
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemOffhand(), ((Integer)this.x.getValue()).intValue() + 43, ((Integer)this.y.getValue()).intValue() + 18);
            armorSize += 45;
            break;
          } 
          if (!this.target.getHeldItemMainhand().isEmpty() || !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(
                this.target.getHeldItemMainhand().isEmpty() ? this.target.getHeldItemOffhand() : this.target.getHeldItemMainhand(), ((Integer)this.x
                .getValue()).intValue() + 28, ((Integer)this.y
                .getValue()).intValue() + 18);
            armorSize += 30;
          } 
          break;
        case 1:
          armorSize = 15;
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(0), ((Integer)this.x.getValue()).intValue() + 28, ((Integer)this.y.getValue()).intValue() + 18);
          if (!this.target.getHeldItemMainhand().isEmpty() && !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemMainhand(), ((Integer)this.x.getValue()).intValue() + 43, ((Integer)this.y.getValue()).intValue() + 18);
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemOffhand(), ((Integer)this.x.getValue()).intValue() + 58, ((Integer)this.y.getValue()).intValue() + 18);
            armorSize += 45;
            break;
          } 
          if (!this.target.getHeldItemMainhand().isEmpty() || !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(
                this.target.getHeldItemMainhand().isEmpty() ? this.target.getHeldItemOffhand() : this.target.getHeldItemMainhand(), ((Integer)this.x
                .getValue()).intValue() + 43, ((Integer)this.y
                .getValue()).intValue() + 18);
            armorSize += 30;
          } 
          break;
        case 2:
          armorSize = 30;
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(0), ((Integer)this.x.getValue()).intValue() + 28, ((Integer)this.y.getValue()).intValue() + 18);
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(1), ((Integer)this.x.getValue()).intValue() + 43, ((Integer)this.y.getValue()).intValue() + 18);
          if (!this.target.getHeldItemMainhand().isEmpty() && !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemMainhand(), ((Integer)this.x.getValue()).intValue() + 58, ((Integer)this.y.getValue()).intValue() + 18);
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemOffhand(), ((Integer)this.x.getValue()).intValue() + 73, ((Integer)this.y.getValue()).intValue() + 18);
            armorSize += 45;
            break;
          } 
          if (!this.target.getHeldItemMainhand().isEmpty() || !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(
                this.target.getHeldItemMainhand().isEmpty() ? this.target.getHeldItemOffhand() : this.target.getHeldItemMainhand(), ((Integer)this.x
                .getValue()).intValue() + 58, ((Integer)this.y
                .getValue()).intValue() + 18);
            armorSize += 30;
          } 
          break;
        case 3:
          armorSize = 45;
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(0), ((Integer)this.x.getValue()).intValue() + 28, ((Integer)this.y.getValue()).intValue() + 18);
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(1), ((Integer)this.x.getValue()).intValue() + 43, ((Integer)this.y.getValue()).intValue() + 18);
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(2), ((Integer)this.x.getValue()).intValue() + 58, ((Integer)this.y.getValue()).intValue() + 18);
          if (!this.target.getHeldItemMainhand().isEmpty() && !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemMainhand(), ((Integer)this.x.getValue()).intValue() + 73, ((Integer)this.y.getValue()).intValue() + 18);
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemOffhand(), ((Integer)this.x.getValue()).intValue() + 98, ((Integer)this.y.getValue()).intValue() + 18);
            armorSize += 45;
            break;
          } 
          if (!this.target.getHeldItemMainhand().isEmpty() || !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(
                this.target.getHeldItemMainhand().isEmpty() ? this.target.getHeldItemOffhand() : this.target.getHeldItemMainhand(), ((Integer)this.x
                .getValue()).intValue() + 73, ((Integer)this.y
                .getValue()).intValue() + 18);
            armorSize += 30;
          } 
          break;
        case 4:
          armorSize = 60;
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(0), ((Integer)this.x.getValue()).intValue() + 28, ((Integer)this.y.getValue()).intValue() + 18);
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(1), ((Integer)this.x.getValue()).intValue() + 43, ((Integer)this.y.getValue()).intValue() + 18);
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(2), ((Integer)this.x.getValue()).intValue() + 58, ((Integer)this.y.getValue()).intValue() + 18);
          mc.getRenderItem().renderItemAndEffectIntoGUI(armorList.get(3), ((Integer)this.x.getValue()).intValue() + 73, ((Integer)this.y.getValue()).intValue() + 18);
          if (!this.target.getHeldItemMainhand().isEmpty() && !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemMainhand(), ((Integer)this.x.getValue()).intValue() + 98, ((Integer)this.y.getValue()).intValue() + 18);
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.target.getHeldItemOffhand(), ((Integer)this.x.getValue()).intValue() + 113, ((Integer)this.y.getValue()).intValue() + 18);
            armorSize += 45;
            break;
          } 
          if (!this.target.getHeldItemMainhand().isEmpty() || !this.target.getHeldItemOffhand().isEmpty()) {
            mc.getRenderItem().renderItemAndEffectIntoGUI(
                this.target.getHeldItemMainhand().isEmpty() ? this.target.getHeldItemOffhand() : this.target.getHeldItemMainhand(), ((Integer)this.x
                .getValue()).intValue() + 98, ((Integer)this.y
                .getValue()).intValue() + 18);
            armorSize += 30;
          } 
          break;
      } 
      int backgroundStopY = ((Integer)this.y.getValue()).intValue() + 35;
      int stringWidth = fr.getStringWidth(this.target.getName()) + 30;
      if (fr.getStringWidth(this.target.getName()) > armorSize) {
        backgroundStopX = ((Integer)this.x.getValue()).intValue() + stringWidth;
      } else {
        backgroundStopX = ((Integer)this.x.getValue()).intValue() + armorSize + 30;
      } 
      backgroundStopX += 5;
      backgroundStopY += 5;
      Gui.drawRect(((Integer)this.x.getValue()).intValue() - 2, ((Integer)this.y.getValue()).intValue(), backgroundStopX, backgroundStopY, (new Color(0, 0, 0, ((Integer)this.backgroundAlpha.getValue()).intValue())).getRGB());
      int healthBarLength = (int)(this.target.getHealth() / this.target.getMaxHealth() * (backgroundStopX - ((Integer)this.x.getValue()).intValue()));
      Gui.drawRect(((Integer)this.x.getValue()).intValue() - 2, backgroundStopY - 2, ((Integer)this.x.getValue()).intValue() + healthBarLength, backgroundStopY, color);
      fr.drawString(this.target.getName(), (((Integer)this.x.getValue()).intValue() + 30), (((Integer)this.y.getValue()).intValue() + 8), (new Color(255, 255, 255)).getRGB(), true);
    } 
  }
}
