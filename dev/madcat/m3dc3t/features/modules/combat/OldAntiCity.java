//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.combat;

import dev.madcat.m3dc3t.M3dC3t;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import dev.madcat.m3dc3t.util.BlockUtil;
import dev.madcat.m3dc3t.util.EntityUtil;
import dev.madcat.m3dc3t.util.InventoryUtil;
import dev.madcat.m3dc3t.util.Timer;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class OldAntiCity extends Module {
  int checke = 0;
  
  public EntityPlayer target;
  
  public Setting<Boolean> ac2 = register(new Setting("SurroundExtend", Boolean.valueOf(true)));
  
  public Setting<Boolean> ac = register(new Setting("SurroundExtend+", Boolean.valueOf(true), v -> ((Boolean)this.ac2.getValue()).booleanValue()));
  
  private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
  
  public Setting<Boolean> packet = register(new Setting("Packet", Boolean.valueOf(true)));
  
  public Setting<Boolean> sm = register(new Setting("Smart", Boolean.valueOf(true)));
  
  private final Setting<Float> range = register(new Setting("Range", Float.valueOf(8.0F), Float.valueOf(1.0F), Float.valueOf(12.0F), v -> ((Boolean)this.sm.getValue()).booleanValue()));
  
  private int obsidian = -1;
  
  private final Timer timer = new Timer();
  
  private final Timer retryTimer = new Timer();
  
  private float yaw = 0.0F;
  
  private float pitch = 0.0F;
  
  private boolean rotating = false;
  
  private boolean isSneaking;
  
  private BlockPos startPos;
  
  public OldAntiCity() {
    super("OldAntiCity", "Old AntiCity.", Module.Category.COMBAT, true, false, false);
  }
  
  public void onTick() {
    if (this.startPos == null && this.checke == 1) {
      this.checke = 0;
      return;
    } 
    if (this.checke == 1 && !this.startPos.equals(EntityUtil.getRoundedBlockPos((Entity)mc.player)))
      this.checke = 0; 
    BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    if (!((Boolean)this.sm.getValue()).booleanValue())
      return; 
    if ((((getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
      if ((((getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
        if ((((getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0)
          if ((((getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) ? 1 : 0) | ((getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) ? 1 : 0)) != 0) {
            if (M3dC3t.moduleManager.isModuleEnabled("Surround"))
              M3dC3t.moduleManager.disableModule("Surround"); 
            if (this.checke == 1)
              return; 
            this.checke = 1;
            this.startPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
          }    
  }
  
  public void onDisable() {
    this.checke = 0;
  }
  
  public void onEnable() {
    this.checke = 0;
  }
  
  public void onUpdate() {
    Vec3d a = mc.player.getPositionVector();
    this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
    this.target = getTarget(((Float)this.range.getValue()).floatValue());
    if (this.target == null)
      return; 
    BlockPos feet = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
    BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    if (this.obsidian == -1)
      return; 
    if (((Boolean)this.ac2.getValue()).booleanValue() && 
      M3dC3t.moduleManager.isModuleEnabled("Surround")) {
      if (getBlock(pos.add(0, 0, -1)).getBlock() != Blocks.BEDROCK) {
        perform(pos.add(0, 0, -1));
        perform(pos.add(0, 0, -2));
        perform(pos.add(1, 0, -1));
        perform(pos.add(-1, 0, -1));
        if (((Boolean)this.ac.getValue()).booleanValue()) {
          perform(pos.add(0, 1, -2));
          perform(pos.add(0, 1, -1));
        } 
      } 
      if (getBlock(pos.add(0, 0, 1)).getBlock() != Blocks.BEDROCK) {
        perform(pos.add(0, 0, 1));
        perform(pos.add(0, 0, 2));
        perform(pos.add(1, 0, 1));
        perform(pos.add(-1, 0, 1));
        if (((Boolean)this.ac.getValue()).booleanValue()) {
          perform(pos.add(0, 1, 2));
          perform(pos.add(0, 1, 1));
        } 
      } 
      if (getBlock(pos.add(1, 0, 0)).getBlock() != Blocks.BEDROCK) {
        perform(pos.add(1, 0, 0));
        perform(pos.add(2, 0, 0));
        perform(pos.add(1, 0, 1));
        perform(pos.add(1, 0, -1));
        if (((Boolean)this.ac.getValue()).booleanValue()) {
          perform(pos.add(2, 1, 0));
          perform(pos.add(1, 1, 0));
        } 
      } 
      if (getBlock(pos.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK) {
        perform(pos.add(-1, 0, 0));
        perform(pos.add(-2, 0, 0));
        perform(pos.add(-1, 0, 1));
        perform(pos.add(-1, 0, -1));
        if (((Boolean)this.ac.getValue()).booleanValue()) {
          perform(pos.add(-2, 1, 0));
          perform(pos.add(-1, 1, 0));
        } 
      } 
    } 
    if (this.checke == 0)
      return; 
    if (!((Boolean)this.sm.getValue()).booleanValue())
      return; 
    if (checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 1)) != null && 
      checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 2)) != null) {
      EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 1)), true);
      place(a, EntityUtil.getVarOffsets(0, 1, 1));
      place(a, EntityUtil.getVarOffsets(0, 1, 2));
    } 
    if (checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -1)) != null && 
      checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -2)) != null) {
      EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -1)), true);
      place(a, EntityUtil.getVarOffsets(0, 1, -1));
      place(a, EntityUtil.getVarOffsets(0, 1, -2));
    } 
    if (checkCrystal(a, EntityUtil.getVarOffsets(1, 1, 0)) != null && 
      checkCrystal(a, EntityUtil.getVarOffsets(2, 1, 0)) != null) {
      EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(1, 1, 0)), true);
      place(a, EntityUtil.getVarOffsets(1, 1, 0));
      place(a, EntityUtil.getVarOffsets(2, 1, 0));
    } 
    if (checkCrystal(a, EntityUtil.getVarOffsets(-1, 1, 0)) != null && 
      checkCrystal(a, EntityUtil.getVarOffsets(-2, 1, 0)) != null) {
      EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-1, 1, 0)), true);
      place(a, EntityUtil.getVarOffsets(-1, 1, 0));
      place(a, EntityUtil.getVarOffsets(-2, 1, 0));
    } 
    if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)) != null && 
      checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)) != null) {
      EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)), true);
      place(a, EntityUtil.getVarOffsets(0, 0, 1));
    } 
    if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)) != null && 
      checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)) != null) {
      EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)), true);
      place(a, EntityUtil.getVarOffsets(0, 0, -1));
    } 
    if (checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)) != null && 
      checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)) != null) {
      EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)), true);
      place(a, EntityUtil.getVarOffsets(1, 0, 0));
    } 
    if (checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)) != null && 
      checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)) != null) {
      EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)), true);
      place(a, EntityUtil.getVarOffsets(-1, 0, 0));
    } 
    if (getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(2, 1, 0)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(2, 1, 0)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(2, 1, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0))))
        perform(pos.add(2, 1, 0)); 
    } 
    if (getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(-2, 1, 0)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-2, 1, 0)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-2, 1, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0))))
        perform(pos.add(-2, 1, 0)); 
    } 
    if (getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 2)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 2)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 1, 2))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2))))
        perform(pos.add(0, 1, 2)); 
    } 
    if (getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -2)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -2)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 1, -2))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2))))
        perform(pos.add(0, 1, -2)); 
    } 
    if (getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, -1, 1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -2, 1))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1)))) {
        perform(pos.add(0, -1, 1));
        perform(pos.add(0, 0, 1));
      } 
    } 
    if (getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, -1, -1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -2, -1))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1)))) {
        perform(pos.add(0, -1, -1));
        perform(pos.add(0, 0, -1));
      } 
    } 
    if (getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, -1, 0)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -2, 0))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0)))) {
        perform(pos.add(1, -1, 0));
        perform(pos.add(1, 0, 0));
      } 
    } 
    if (getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, -1, 0)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -2, 0))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0)))) {
        perform(pos.add(-1, -1, 0));
        perform(pos.add(-1, 0, 0));
      } 
    } 
    if (getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 2))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, 2))))
        if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)) == null) {
          perform(pos.add(0, 0, 1));
          perform(pos.add(0, 0, 2));
          perform(pos.add(0, -1, 2));
          perform(pos.add(0, 0, 3));
        } else if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)) != null && checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)) != null) {
          EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)), true);
          perform(pos.add(0, 0, 1));
          perform(pos.add(0, 0, 2));
          perform(pos.add(0, -1, 2));
          perform(pos.add(0, 0, 3));
        }  
    } 
    if (getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -2))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, -2))))
        if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)) == null) {
          perform(pos.add(0, 0, -1));
          perform(pos.add(0, 0, -2));
          perform(pos.add(0, -1, -2));
          perform(pos.add(0, 0, -3));
        } else if (checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)) != null && checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)) != null) {
          EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)), true);
          perform(pos.add(0, 0, -1));
          perform(pos.add(0, 0, -2));
          perform(pos.add(0, -1, -2));
          perform(pos.add(0, 0, -3));
        }  
    } 
    if (getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(2, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(2, -1, 0))))
        if (checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)) == null) {
          perform(pos.add(1, 0, 0));
          perform(pos.add(2, 0, 0));
          perform(pos.add(2, -1, 0));
          perform(pos.add(3, 0, 0));
        } else if (checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)) != null && checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)) != null) {
          EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)), true);
          perform(pos.add(1, 0, 0));
          perform(pos.add(2, 0, 0));
          perform(pos.add(2, -1, 0));
          perform(pos.add(3, 0, 0));
        }  
    } 
    if (getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-2, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-2, -1, 0))))
        if (checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)) == null) {
          perform(pos.add(-1, 0, 0));
          perform(pos.add(-2, 0, 0));
          perform(pos.add(-2, -1, 0));
          perform(pos.add(-3, 0, 0));
        } else if (checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)) != null && checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)) != null) {
          EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)), true);
          perform(pos.add(-1, 0, 0));
          perform(pos.add(-2, 0, 0));
          perform(pos.add(-2, -1, 0));
          perform(pos.add(-3, 0, 0));
        }  
    } 
    if (getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR)
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1)))) {
        if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1)))) {
          perform(pos.add(0, 0, 1));
          perform(pos.add(0, 1, 1));
          perform(pos.add(1, 1, 1));
          perform(pos.add(0, 1, 2));
        } 
      } else if (getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
        perform(pos.add(0, 0, 2));
        perform(pos.add(0, 1, 2));
        perform(pos.add(1, 0, 2));
        perform(pos.add(1, 1, 2));
      }  
    if (getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR)
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1)))) {
        if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1)))) {
          perform(pos.add(0, 0, -1));
          perform(pos.add(0, 1, -1));
          perform(pos.add(-1, 1, -1));
          perform(pos.add(0, 1, -2));
        } 
      } else if (getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR && getBlock(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
        perform(pos.add(0, 0, -2));
        perform(pos.add(0, 1, -2));
        perform(pos.add(1, 0, -2));
        perform(pos.add(1, 1, -2));
      }  
    if (getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR)
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0)))) {
        if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 1, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0)))) {
          perform(pos.add(1, 0, 0));
          perform(pos.add(1, 1, 0));
          perform(pos.add(1, 1, 1));
          perform(pos.add(2, 1, 0));
        } 
      } else if (getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
        perform(pos.add(2, 0, 0));
        perform(pos.add(2, 1, 0));
        perform(pos.add(2, 0, 1));
        perform(pos.add(2, 1, 1));
      }  
    if (getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR)
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0)))) {
        if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0)))) {
          perform(pos.add(-1, 0, 0));
          perform(pos.add(-1, 1, 0));
          perform(pos.add(-1, 1, -1));
          perform(pos.add(-2, 1, 0));
        } 
      } else if (getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
        perform(pos.add(-2, 0, 0));
        perform(pos.add(-2, 1, 0));
        perform(pos.add(-2, 0, 1));
        perform(pos.add(-2, 1, 1));
      }  
    if (getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) {
        perform(pos.add(1, 0, 0));
        perform(pos.add(1, 0, 1));
      } 
    } 
    if (getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) {
        perform(pos.add(0, 0, 1));
        perform(pos.add(1, 0, 1));
      } 
    } 
    if (getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) {
        perform(pos.add(-1, 0, 0));
        perform(pos.add(-1, 0, -1));
      } 
    } 
    if (getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) {
        perform(pos.add(0, 0, -1));
        perform(pos.add(-1, 0, -1));
      } 
    } 
    if (getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) {
        perform(pos.add(-1, 0, 0));
        perform(pos.add(-1, 0, 1));
      } 
    } 
    if (getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) {
        perform(pos.add(0, 0, 1));
        perform(pos.add(-1, 0, 1));
      } 
    } 
    if (getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) {
        perform(pos.add(1, 0, 0));
        perform(pos.add(1, 0, -1));
      } 
    } 
    if (getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
      if (checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)) != null)
        EntityUtil.attackEntity(checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)), true); 
      if (!(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1))) && 
        !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) {
        perform(pos.add(0, 0, -1));
        perform(pos.add(1, 0, -1));
      } 
    } 
    if (getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 1, 1)).getBlock() == Blocks.AIR && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) {
      perform(pos.add(1, 0, 0));
      perform(pos.add(1, 0, 1));
      perform(pos.add(1, 1, 1));
    } 
    if (getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 1, 1)).getBlock() == Blocks.AIR && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 1)))) {
      perform(pos.add(0, 0, 1));
      perform(pos.add(1, 0, 1));
      perform(pos.add(1, 1, 1));
    } 
    if (getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) {
      perform(pos.add(-1, 0, 0));
      perform(pos.add(-1, 0, -1));
      perform(pos.add(-1, 1, -1));
    } 
    if (getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, -1)))) {
      perform(pos.add(0, 0, -1));
      perform(pos.add(-1, 0, -1));
      perform(pos.add(-1, 1, -1));
    } 
    if (getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 0))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) {
      perform(pos.add(-1, 0, 0));
      perform(pos.add(-1, 0, 1));
      perform(pos.add(-1, 1, 1));
    } 
    if (getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && getBlock(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, 1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, -1, 1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(-1, 0, 1)))) {
      perform(pos.add(0, 0, 1));
      perform(pos.add(-1, 0, 1));
      perform(pos.add(-1, 1, 1));
    } 
    if (getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 1, -1)).getBlock() == Blocks.AIR && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, 0))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, 0))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) {
      perform(pos.add(1, 0, 0));
      perform(pos.add(1, 0, -1));
      perform(pos.add(1, 1, -1));
    } 
    if (getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && getBlock(pos.add(1, 1, -1)).getBlock() == Blocks.AIR && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, 0, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(0, -1, -1))) && 
      !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, -1, -1))) && !(new BlockPos((Vec3i)feet)).equals(new BlockPos((Vec3i)pos.add(1, 0, -1)))) {
      perform(pos.add(0, 0, -1));
      perform(pos.add(1, 0, -1));
      perform(pos.add(1, 1, -1));
    } 
  }
  
  private void place(Vec3d pos, Vec3d[] list) {
    Vec3d[] var3 = list;
    int var4 = list.length;
    for (int var5 = 0; var5 < var4; var5++) {
      Vec3d vec3d = var3[var5];
      BlockPos position = (new BlockPos(pos)).add(vec3d.x, vec3d.y, vec3d.z);
      int a = mc.player.inventory.currentItem;
      mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
      mc.playerController.updateController();
      this.isSneaking = BlockUtil.placeBlock(position, EnumHand.MAIN_HAND, false, ((Boolean)this.packet.getValue()).booleanValue(), true);
      mc.player.inventory.currentItem = a;
      mc.playerController.updateController();
    } 
  }
  
  Entity checkCrystal(Vec3d pos, Vec3d[] list) {
    Entity crystal = null;
    Vec3d[] var4 = list;
    int var5 = list.length;
    for (int var6 = 0; var6 < var5; var6++) {
      Vec3d vec3d = var4[var6];
      BlockPos position = (new BlockPos(pos)).add(vec3d.x, vec3d.y, vec3d.z);
      for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(position))) {
        if (!(entity instanceof net.minecraft.entity.item.EntityEnderCrystal) || crystal != null)
          continue; 
        crystal = entity;
      } 
    } 
    return crystal;
  }
  
  private IBlockState getBlock(BlockPos block) {
    return mc.world.getBlockState(block);
  }
  
  private EntityPlayer getTarget(double range) {
    EntityPlayer target = null;
    double distance = Math.pow(range, 2.0D) + 1.0D;
    for (EntityPlayer player : mc.world.playerEntities) {
      if (EntityUtil.isntValid((Entity)player, range) || M3dC3t.speedManager.getPlayerSpeed(player) > 10.0D)
        continue; 
      if (target == null) {
        target = player;
        distance = mc.player.getDistanceSq((Entity)player);
        continue;
      } 
      if (mc.player.getDistanceSq((Entity)player) >= distance)
        continue; 
      target = player;
      distance = mc.player.getDistanceSq((Entity)player);
    } 
    return target;
  }
  
  private void perform(BlockPos pos) {
    int old = mc.player.inventory.currentItem;
    if (mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
      mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
      mc.playerController.updateController();
      BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, ((Boolean)this.rotate.getValue()).booleanValue(), true, false);
      mc.player.inventory.currentItem = old;
      mc.playerController.updateController();
    } 
  }
}
