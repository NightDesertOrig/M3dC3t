//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.player;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class PacketEat extends Module {
  private static PacketEat INSTANCE = new PacketEat();
  
  private Setting<Boolean> autoEat;
  
  public final Setting<Float> health;
  
  public final Setting<Float> hunger;
  
  public PacketEat() {
    super("PacketEat", "Eat without action.", Module.Category.PLAYER, true, false, false);
    this.autoEat = register(new Setting("OnlyGappleAutoEAT", Boolean.valueOf(true)));
    this.health = register(new Setting("Health", Float.valueOf(32.0F), Float.valueOf(0.0F), Float.valueOf(35.9F), v -> ((Boolean)this.autoEat.getValue()).booleanValue()));
    this.hunger = register(new Setting("Hunger", Float.valueOf(19.0F), Float.valueOf(0.0F), Float.valueOf(19.9F), v -> ((Boolean)this.autoEat.getValue()).booleanValue()));
    setInstance();
  }
  
  public static PacketEat getInstance() {
    if (INSTANCE != null)
      return INSTANCE; 
    INSTANCE = new PacketEat();
    return INSTANCE;
  }
  
  public void onUpdate() {
    if (((Boolean)this.autoEat.getValue()).booleanValue()) {
      if (mc.player.isCreative())
        return; 
      if (mc.player.getHealth() + mc.player.getAbsorptionAmount() <= ((Float)this.health.getValue()).floatValue() || mc.player.getFoodStats().getFoodLevel() <= ((Float)this.hunger.getValue()).floatValue()) {
        if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.GOLDEN_APPLE) {
          mc.playerController.processRightClick((EntityPlayer)mc.player, (World)mc.world, EnumHand.MAIN_HAND);
          return;
        } 
        if (mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.GOLDEN_APPLE) {
          mc.playerController.processRightClick((EntityPlayer)mc.player, (World)mc.world, EnumHand.OFF_HAND);
          return;
        } 
      } 
    } 
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
}
