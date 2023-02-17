//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.misc;

import com.mojang.authlib.GameProfile;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.modules.client.HUD;
import dev.madcat.m3dc3t.features.setting.Setting;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.GameType;
import net.minecraft.world.World;

public class FakePlayer extends Module {
  private final Setting<Integer> setHealth = register(new Setting("SetHealth", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(20)));
  
  public Setting<String> fName = register(new Setting("Name", "HuShuYu", "Fake player's name"));
  
  private EntityOtherPlayerMP clonedPlayer;
  
  public FakePlayer() {
    super("FakePlayer", "Spawns a FakePlayer for testing.", Module.Category.MISC, true, false, false);
  }
  
  public void onEnable() {
    if (mc.player != null && !mc.player.isDead) {
      this.clonedPlayer = new EntityOtherPlayerMP((World)mc.world, new GameProfile(UUID.fromString("a3ca166d-c5f1-3d5a-baac-b18a5b38d4cd"), (String)this.fName.getValue()));
      this.clonedPlayer.copyLocationAndAnglesFrom((Entity)mc.player);
      this.clonedPlayer.rotationYawHead = mc.player.rotationYawHead;
      this.clonedPlayer.rotationYaw = mc.player.rotationYaw;
      this.clonedPlayer.rotationPitch = mc.player.rotationPitch;
      this.clonedPlayer.inventory.copyInventory(mc.player.inventory);
      this.clonedPlayer.setGameType(GameType.SURVIVAL);
      this.clonedPlayer.setHealth(((Integer)this.setHealth.getValue()).intValue());
      mc.world.addEntityToWorld(-404, (Entity)this.clonedPlayer);
      this.clonedPlayer.onLivingUpdate();
      return;
    } 
    disable();
  }
  
  public void onDisable() {
    if (mc.world == null)
      return; 
    mc.world.removeEntityFromWorld(-404);
  }
  
  public String getDisplayInfo() {
    if (!((Boolean)(HUD.getInstance()).moduleInfo.getValue()).booleanValue())
      return null; 
    return (String)this.fName.getValue();
  }
}
