//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.event.events.Render3DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.ColorUtil;
import dev.madcat.m3dc3t.util.MathUtil;
import dev.madcat.m3dc3t.util.RenderUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class StorageESP extends Module {
  private final Setting<Float> range = register(new Setting("Range", Float.valueOf(50.0F), Float.valueOf(1.0F), Float.valueOf(300.0F)));
  
  private final Setting<Boolean> chest = register(new Setting("Chest", Boolean.valueOf(true)));
  
  private final Setting<Boolean> dispenser = register(new Setting("Dispenser", Boolean.valueOf(false)));
  
  private final Setting<Boolean> shulker = register(new Setting("Shulker", Boolean.valueOf(true)));
  
  private final Setting<Boolean> echest = register(new Setting("Ender Chest", Boolean.valueOf(true)));
  
  private final Setting<Boolean> furnace = register(new Setting("Furnace", Boolean.valueOf(false)));
  
  private final Setting<Boolean> hopper = register(new Setting("Hopper", Boolean.valueOf(false)));
  
  private final Setting<Boolean> cart = register(new Setting("Minecart", Boolean.valueOf(false)));
  
  private final Setting<Boolean> frame = register(new Setting("Item Frame", Boolean.valueOf(false)));
  
  private final Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(false)));
  
  private final Setting<Integer> boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(255), v -> ((Boolean)this.box.getValue()).booleanValue()));
  
  private final Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
  
  private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
  
  public StorageESP() {
    super("StorageESP", "Highlights Containers.", Module.Category.RENDER, false, false, false);
  }
  
  public void onRender3D(Render3DEvent event) {
    HashMap<BlockPos, Integer> positions = new HashMap<>();
    for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
      int color;
      BlockPos pos;
      if (((!(tileEntity instanceof net.minecraft.tileentity.TileEntityChest) || !((Boolean)this.chest.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityDispenser) || !((Boolean)this.dispenser.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox) || !((Boolean)this.shulker.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest) || !((Boolean)this.echest.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityFurnace) || !((Boolean)this.furnace.getValue()).booleanValue()) && (!(tileEntity instanceof net.minecraft.tileentity.TileEntityHopper) || !((Boolean)this.hopper.getValue()).booleanValue())) || mc.player.getDistanceSq(pos = tileEntity.getPos()) > MathUtil.square(((Float)this.range.getValue()).floatValue()) || (color = getTileEntityColor(tileEntity)) == -1)
        continue; 
      positions.put(pos, Integer.valueOf(color));
    } 
    for (Entity entity : mc.world.loadedEntityList) {
      int color;
      BlockPos pos;
      if (((!(entity instanceof EntityItemFrame) || !((Boolean)this.frame.getValue()).booleanValue()) && (!(entity instanceof net.minecraft.entity.item.EntityMinecartChest) || !((Boolean)this.cart.getValue()).booleanValue())) || mc.player.getDistanceSq(pos = entity.getPosition()) > MathUtil.square(((Float)this.range.getValue()).floatValue()) || (color = getEntityColor(entity)) == -1)
        continue; 
      positions.put(pos, Integer.valueOf(color));
    } 
    for (Map.Entry<BlockPos, Integer> entry : positions.entrySet()) {
      BlockPos blockPos = (BlockPos)entry.getKey();
      int color = ((Integer)entry.getValue()).intValue();
      RenderUtil.drawBoxESP(blockPos, new Color(color), false, new Color(color), ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), false);
    } 
  }
  
  private int getTileEntityColor(TileEntity tileEntity) {
    if (tileEntity instanceof net.minecraft.tileentity.TileEntityChest)
      return ColorUtil.Colors.BLUE; 
    if (tileEntity instanceof net.minecraft.tileentity.TileEntityShulkerBox)
      return ColorUtil.Colors.RED; 
    if (tileEntity instanceof net.minecraft.tileentity.TileEntityEnderChest)
      return ColorUtil.Colors.PURPLE; 
    if (tileEntity instanceof net.minecraft.tileentity.TileEntityFurnace)
      return ColorUtil.Colors.GRAY; 
    if (tileEntity instanceof net.minecraft.tileentity.TileEntityHopper)
      return ColorUtil.Colors.DARK_RED; 
    if (tileEntity instanceof net.minecraft.tileentity.TileEntityDispenser)
      return ColorUtil.Colors.ORANGE; 
    return -1;
  }
  
  private int getEntityColor(Entity entity) {
    if (entity instanceof net.minecraft.entity.item.EntityMinecartChest)
      return ColorUtil.Colors.ORANGE; 
    if (entity instanceof EntityItemFrame && ((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof net.minecraft.item.ItemShulkerBox)
      return ColorUtil.Colors.YELLOW; 
    if (entity instanceof EntityItemFrame && !(((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof net.minecraft.item.ItemShulkerBox))
      return ColorUtil.Colors.ORANGE; 
    return -1;
  }
}
