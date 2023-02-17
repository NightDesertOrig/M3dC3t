//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.event.events.Render3DEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.combat.InstantMine;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.RenderUtil;
import dev.madcat.m3dc3t.util.Timer;
import java.awt.Color;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BreakESP extends Module {
  private static BreakESP INSTANCE;
  
  BlockPos pos;
  
  private Setting<Integer> red;
  
  private Setting<Integer> green;
  
  private Setting<Integer> blue;
  
  private Setting<Integer> alpha2;
  
  private Setting<Integer> alpha;
  
  public final Timer imerS;
  
  double manxi;
  
  public BreakESP() {
    super("BreakESP", "Show enemy's break packet.", Module.Category.RENDER, true, false, false);
    this.red = register(new Setting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    this.green = register(new Setting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    this.blue = register(new Setting("Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255)));
    this.alpha2 = register(new Setting("Alpha", Integer.valueOf(100), Integer.valueOf(20), Integer.valueOf(255)));
    this.alpha = register(new Setting("OutlineAlpha", Integer.valueOf(100), Integer.valueOf(20), Integer.valueOf(255)));
    this.imerS = new Timer();
    this.manxi = 0.0D;
    setInstance();
  }
  
  public static BreakESP getInstance() {
    if (INSTANCE == null)
      INSTANCE = new BreakESP(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  @SubscribeEvent
  public void BrokenBlock(PlaySoundEvent event) {
    if (InstantMine.breakPos != null && InstantMine.breakPos.equals(new BlockPos(event.getSound().getXPosF(), event.getSound().getYPosF(), event.getSound().getZPosF())))
      return; 
    if (!event.getName().endsWith("hit"))
      return; 
    if (event.getName().endsWith("arrow.hit"))
      return; 
    if (event.getName().endsWith("stand.hit"))
      return; 
    if (this.pos != null && this.pos.equals(new BlockPos(event.getSound().getXPosF(), event.getSound().getYPosF(), event.getSound().getZPosF())))
      return; 
    if (mc.world.getBlockState(new BlockPos(event.getSound().getXPosF(), event.getSound().getYPosF(), event.getSound().getZPosF())).getBlock() == Blocks.BEDROCK)
      return; 
    this.pos = new BlockPos(event.getSound().getXPosF(), event.getSound().getYPosF(), event.getSound().getZPosF());
    this.manxi = 0.0D;
  }
  
  @SubscribeEvent
  public void BrokenBlock2(PlaySoundEvent event) {
    if (InstantMine.breakPos != null && InstantMine.breakPos.equals(new BlockPos(event.getSound().getXPosF(), event.getSound().getYPosF(), event.getSound().getZPosF())))
      return; 
    if (!event.getName().endsWith("break"))
      return; 
    if (event.getName().endsWith("potion.break"))
      return; 
    if (this.pos != null && this.pos.equals(new BlockPos(event.getSound().getXPosF(), event.getSound().getYPosF(), event.getSound().getZPosF())))
      return; 
    if (mc.world.getBlockState(new BlockPos(event.getSound().getXPosF(), event.getSound().getYPosF(), event.getSound().getZPosF())).getBlock() == Blocks.BEDROCK)
      return; 
    this.pos = new BlockPos(event.getSound().getXPosF(), event.getSound().getYPosF(), event.getSound().getZPosF());
    this.manxi = 0.0D;
  }
  
  public void onRender3D(Render3DEvent event) {
    if (this.pos != null) {
      if (this.imerS.passedMs(10L)) {
        if (this.manxi <= 10.0D)
          this.manxi += 0.11D; 
        this.imerS.reset();
      } 
      AxisAlignedBB axisAlignedBB = mc.world.getBlockState(this.pos).getSelectedBoundingBox((World)mc.world, this.pos);
      double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0D;
      double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0D;
      double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0D;
      double progressValX = this.manxi * (axisAlignedBB.maxX - centerX) / 10.0D;
      double progressValY = this.manxi * (axisAlignedBB.maxY - centerY) / 10.0D;
      double progressValZ = this.manxi * (axisAlignedBB.maxZ - centerZ) / 10.0D;
      AxisAlignedBB axisAlignedBB1 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
      RenderUtil.drawBBBox(axisAlignedBB1, new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue()), ((Integer)this.alpha.getValue()).intValue());
      RenderUtil.drawBBFill(axisAlignedBB1, new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha2.getValue()).intValue()), ((Integer)this.alpha2.getValue()).intValue());
    } 
  }
}
