//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.client;

import dev.madcat.m3dc3t.event.events.Render2DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStats extends Module {
  public Setting<Boolean> combatCount;
  
  public Setting<Integer> combatCountX;
  
  public Setting<Integer> combatCountY;
  
  public Setting<Boolean> armor;
  
  public ItemStats() {
    super("CombatStats", "Show your items count.", Module.Category.CLIENT, true, false, false);
    this.combatCount = register(new Setting("ItemsCount", Boolean.valueOf(true)));
    this.combatCountX = register(new Setting("X", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(300), v -> ((Boolean)this.combatCount.getValue()).booleanValue()));
    this.combatCountY = register(new Setting("Y", Integer.valueOf(18), Integer.valueOf(0), Integer.valueOf(500), v -> ((Boolean)this.combatCount.getValue()).booleanValue()));
    this.armor = register(new Setting("Armor", Boolean.valueOf(true)));
  }
  
  private static final ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING);
  
  private static final ItemStack Crystal = new ItemStack(Items.END_CRYSTAL);
  
  private static final ItemStack xp = new ItemStack(Items.EXPERIENCE_BOTTLE);
  
  private static final ItemStack ap = new ItemStack(Items.GOLDEN_APPLE);
  
  private static final ItemStack obs = new ItemStack(Blocks.OBSIDIAN);
  
  public void onRender2D(Render2DEvent event) {
    if (((Boolean)this.combatCount.getValue()).booleanValue()) {
      int width = this.renderer.scaledWidth;
      int height = this.renderer.scaledHeight;
      int totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.TOTEM_OF_UNDYING)).mapToInt(ItemStack::getCount).sum();
      if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)
        totems += mc.player.getHeldItemOffhand().getCount(); 
      GlStateManager.enableTexture2D();
      int i = width / 2;
      int y = height - ((Integer)this.combatCountY.getValue()).intValue();
      int x = i + ((Integer)this.combatCountX.getValue()).intValue();
      GlStateManager.enableDepth();
      RenderUtil.itemRender.zLevel = 200.0F;
      RenderUtil.itemRender.renderItemAndEffectIntoGUI(totem, x, y);
      RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, totem, x, y, "");
      RenderUtil.itemRender.zLevel = 0.0F;
      GlStateManager.enableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      this.renderer.drawStringWithShadow(totems + "", (x + 19 - 2 - this.renderer.getStringWidth(totems + "")), (y + 9), 16777215);
      GlStateManager.enableDepth();
      GlStateManager.disableLighting();
      int Crystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.END_CRYSTAL)).mapToInt(ItemStack::getCount).sum();
      if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)
        Crystals += mc.player.getHeldItemOffhand().getCount(); 
      x += 20;
      GlStateManager.enableDepth();
      RenderUtil.itemRender.zLevel = 200.0F;
      RenderUtil.itemRender.renderItemAndEffectIntoGUI(Crystal, x, y);
      RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, Crystal, x, y, "");
      RenderUtil.itemRender.zLevel = 0.0F;
      GlStateManager.enableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      this.renderer.drawStringWithShadow(Crystals + "", (x + 19 - 2 - this.renderer.getStringWidth(Crystals + "")), (y + 9), 16777215);
      GlStateManager.enableDepth();
      GlStateManager.disableLighting();
      int EXP = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.EXPERIENCE_BOTTLE)).mapToInt(ItemStack::getCount).sum();
      if (mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE)
        EXP += mc.player.getHeldItemOffhand().getCount(); 
      x += 20;
      GlStateManager.enableDepth();
      RenderUtil.itemRender.zLevel = 200.0F;
      RenderUtil.itemRender.renderItemAndEffectIntoGUI(xp, x, y);
      RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, xp, x, y, "");
      RenderUtil.itemRender.zLevel = 0.0F;
      GlStateManager.enableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      this.renderer.drawStringWithShadow(EXP + "", (x + 19 - 2 - this.renderer.getStringWidth(EXP + "")), (y + 9), 16777215);
      GlStateManager.enableDepth();
      GlStateManager.disableLighting();
      int GP = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Items.GOLDEN_APPLE)).mapToInt(ItemStack::getCount).sum();
      if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE)
        GP += mc.player.getHeldItemOffhand().getCount(); 
      x += 20;
      GlStateManager.enableDepth();
      RenderUtil.itemRender.zLevel = 200.0F;
      RenderUtil.itemRender.renderItemAndEffectIntoGUI(ap, x, y);
      RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, ap, x, y, "");
      RenderUtil.itemRender.zLevel = 0.0F;
      GlStateManager.enableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      this.renderer.drawStringWithShadow(GP + "", (x + 19 - 2 - this.renderer.getStringWidth(GP + "")), (y + 9), 16777215);
      GlStateManager.enableDepth();
      GlStateManager.disableLighting();
      int OBS = mc.player.inventory.mainInventory.stream().filter(itemStack -> (itemStack.getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN))).mapToInt(ItemStack::getCount).sum();
      x += 20;
      GlStateManager.enableDepth();
      RenderUtil.itemRender.zLevel = 200.0F;
      RenderUtil.itemRender.renderItemAndEffectIntoGUI(obs, x, y);
      RenderUtil.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, obs, x, y, "");
      RenderUtil.itemRender.zLevel = 0.0F;
      GlStateManager.enableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      this.renderer.drawStringWithShadow(OBS + "", (x + 19 - 2 - this.renderer.getStringWidth(OBS + "")), (y + 9), 16777215);
      GlStateManager.enableDepth();
      GlStateManager.disableLighting();
    } 
    if (((Boolean)this.armor.getValue()).booleanValue()) {
      int width = this.renderer.scaledWidth;
      int height = this.renderer.scaledHeight;
      GlStateManager.enableTexture2D();
      int i = width / 2;
      int iteration = 0;
      int y = height - 55 - ((HUD.mc.player.isInWater() && HUD.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
      for (ItemStack is : HUD.mc.player.inventory.armorInventory) {
        iteration++;
        if (is.isEmpty())
          continue; 
        int x = i - 90 + (9 - iteration) * 20 + 2;
        GlStateManager.enableDepth();
        RenderUtil.itemRender.zLevel = 200.0F;
        RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
        RenderUtil.itemRender.renderItemOverlayIntoGUI(HUD.mc.fontRenderer, is, x, y, "");
        RenderUtil.itemRender.zLevel = 0.0F;
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
        this.renderer.drawStringWithShadow(s, (x + 19 - 2 - this.renderer.getStringWidth(s)), (y + 9), 16777215);
        float green = (is.getMaxDamage() - is.getItemDamage()) / is.getMaxDamage();
        float red = 1.0F - green;
        int dmg = 100 - (int)(red * 100.0F);
        if (dmg == -2147483547)
          dmg = 100; 
        this.renderer.drawStringWithShadow(dmg + "", (x + 8 - this.renderer.getStringWidth(dmg + "") / 2), (y - 11), ColorUtil.toRGBA((int)(red * 255.0F), (int)(green * 255.0F), 0));
      } 
      GlStateManager.enableDepth();
      GlStateManager.disableLighting();
    } 
  }
}
