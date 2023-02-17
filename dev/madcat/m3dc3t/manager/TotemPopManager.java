//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.manager;

import dev.madcat.m3dc3t.features.Feature;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.EntityPlayer;

public class TotemPopManager extends Feature {
  private Map<EntityPlayer, Integer> poplist = new ConcurrentHashMap<>();
  
  private Set<EntityPlayer> toAnnounce = new HashSet<>();
  
  public void onUpdate() {
    for (EntityPlayer player : this.toAnnounce) {
      if (player == null)
        continue; 
      int playerNumber = 0;
      for (char character : player.getName().toCharArray()) {
        playerNumber += character;
        playerNumber *= 10;
      } 
      this.toAnnounce.remove(player);
    } 
  }
  
  public void onLogout() {}
  
  public void init() {}
  
  public void onTotemPop(EntityPlayer player) {
    popTotem(player);
    if (!player.equals(mc.player))
      this.toAnnounce.add(player); 
  }
  
  public void onDeath(EntityPlayer player) {
    int playerNumber = 0;
    for (char character : player.getName().toCharArray()) {
      playerNumber += character;
      playerNumber *= 10;
    } 
    this.toAnnounce.remove(player);
  }
  
  public void onLogout(EntityPlayer player, boolean clearOnLogout) {
    if (clearOnLogout)
      resetPops(player); 
  }
  
  public void onOwnLogout(boolean clearOnLogout) {
    if (clearOnLogout)
      clearList(); 
  }
  
  public void clearList() {
    this.poplist = new ConcurrentHashMap<>();
  }
  
  public void resetPops(EntityPlayer player) {
    setTotemPops(player, 0);
  }
  
  public void popTotem(EntityPlayer player) {
    this.poplist.merge(player, Integer.valueOf(1), Integer::sum);
  }
  
  public void setTotemPops(EntityPlayer player, int amount) {
    this.poplist.put(player, Integer.valueOf(amount));
  }
  
  public int getTotemPops(EntityPlayer player) {
    Integer pops = this.poplist.get(player);
    if (pops == null)
      return 0; 
    return pops.intValue();
  }
  
  public String getTotemPopString(EntityPlayer player) {
    return "§f" + ((getTotemPops(player) <= 0) ? "" : ("-" + getTotemPops(player) + " "));
  }
}
