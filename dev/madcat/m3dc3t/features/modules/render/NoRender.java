//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.event.events.NoRenderEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender extends Module {
  public static NoRender INSTANCE = new NoRender();
  
  public Setting<Boolean> armor = register(new Setting("Armor", Boolean.valueOf(true)));
  
  public Setting<Boolean> fire = register(new Setting("Fire", Boolean.valueOf(true)));
  
  public Setting<Boolean> blind = register(new Setting("Blind", Boolean.valueOf(true)));
  
  public Setting<Boolean> nausea = register(new Setting("Nausea", Boolean.valueOf(true)));
  
  public Setting<Boolean> fog = register(new Setting("Fog", Boolean.valueOf(true)));
  
  public Setting<Boolean> noWeather = register(new Setting("Weather", Boolean.valueOf(true), "AntiWeather"));
  
  public Setting<Boolean> hurtCam = register(new Setting("HurtCam", Boolean.valueOf(true)));
  
  public Setting<Boolean> totemPops = register(new Setting("TotemPop", Boolean.valueOf(true), "Removes the Totem overlay."));
  
  public Setting<Boolean> blocks = register(new Setting("Block", Boolean.valueOf(true)));
  
  public NoRender() {
    super("NoRender", "Prevent some animation.", Module.Category.RENDER, true, false, false);
    setInstance();
  }
  
  public static NoRender getInstance() {
    if (INSTANCE != null)
      return INSTANCE; 
    INSTANCE = new NoRender();
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  public void onUpdate() {
    if (((Boolean)this.blind.getValue()).booleanValue() && mc.player.isPotionActive(MobEffects.BLINDNESS))
      mc.player.removePotionEffect(MobEffects.BLINDNESS); 
    if (!((Boolean)this.nausea.getValue()).booleanValue())
      return; 
    if (((Boolean)this.noWeather.getValue()).booleanValue())
      mc.world.getWorldInfo().setRaining(false); 
    if (!mc.player.isPotionActive(MobEffects.NAUSEA))
      return; 
    mc.player.removePotionEffect(MobEffects.NAUSEA);
  }
  
  @SubscribeEvent
  public void NoRenderEventListener(NoRenderEvent event) {
    if (event.getStage() == 0 && ((Boolean)this.armor.getValue()).booleanValue()) {
      event.setCanceled(true);
      return;
    } 
    if (event.getStage() != 1)
      return; 
    if (!((Boolean)this.hurtCam.getValue()).booleanValue())
      return; 
    event.setCanceled(true);
  }
  
  @SubscribeEvent
  public void fog_density(EntityViewRenderEvent.FogDensity event) {
    if (!((Boolean)this.fog.getValue()).booleanValue()) {
      event.setDensity(0.0F);
      event.setCanceled(true);
    } 
  }
  
  @SubscribeEvent
  public void blockOverlayEventListener(RenderBlockOverlayEvent event) {
    if (!((Boolean)this.fire.getValue()).booleanValue())
      return; 
    if (event.getOverlayType() != RenderBlockOverlayEvent.OverlayType.FIRE)
      return; 
    event.setCanceled(true);
  }
}
