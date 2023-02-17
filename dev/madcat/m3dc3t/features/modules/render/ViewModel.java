package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.event.events.RenderItemEvent;
import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ViewModel extends Module {
  private static ViewModel INSTANCE = new ViewModel();
  
  public Setting<Settings> settings = register(new Setting("Settings", Settings.TRANSLATE));
  
  public Setting<Boolean> noEatAnimation = register(new Setting("NoEatAnimation", Boolean.valueOf(false), v -> (this.settings.getValue() == Settings.TWEAKS)));
  
  public Setting<Double> eatX = register(new Setting("EatX", Double.valueOf(3.5D), Double.valueOf(-5.0D), Double.valueOf(15.0D), v -> (this.settings.getValue() == Settings.TWEAKS && !((Boolean)this.noEatAnimation.getValue()).booleanValue())));
  
  public Setting<Double> eatY = register(new Setting("EatY", Double.valueOf(2.1D), Double.valueOf(-5.0D), Double.valueOf(15.0D), v -> (this.settings.getValue() == Settings.TWEAKS && !((Boolean)this.noEatAnimation.getValue()).booleanValue())));
  
  public Setting<Boolean> doBob = register(new Setting("ItemBob", Boolean.valueOf(true), v -> (this.settings.getValue() == Settings.TWEAKS)));
  
  public Setting<Double> mainX = register(new Setting("MainX", Double.valueOf(1.2D), Double.valueOf(-2.0D), Double.valueOf(4.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
  
  public Setting<Double> mainY = register(new Setting("MainY", Double.valueOf(-0.95D), Double.valueOf(-3.0D), Double.valueOf(3.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
  
  public Setting<Double> mainZ = register(new Setting("MainZ", Double.valueOf(-1.45D), Double.valueOf(-5.0D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
  
  public Setting<Double> offX = register(new Setting("OffX", Double.valueOf(1.2D), Double.valueOf(-2.0D), Double.valueOf(4.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
  
  public Setting<Double> offY = register(new Setting("OffY", Double.valueOf(-0.95D), Double.valueOf(-3.0D), Double.valueOf(3.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
  
  public Setting<Double> offZ = register(new Setting("OffZ", Double.valueOf(-1.45D), Double.valueOf(-5.0D), Double.valueOf(5.0D), v -> (this.settings.getValue() == Settings.TRANSLATE)));
  
  public ViewModel() {
    super("ViewModel", "Change the position of the arm.", Module.Category.RENDER, true, false, false);
    setInstance();
  }
  
  public static ViewModel getInstance() {
    if (INSTANCE == null)
      INSTANCE = new ViewModel(); 
    return INSTANCE;
  }
  
  private void setInstance() {
    INSTANCE = this;
  }
  
  @SubscribeEvent
  public void onItemRender(RenderItemEvent event) {
    event.setMainX(((Double)this.mainX.getValue()).doubleValue());
    event.setMainY(((Double)this.mainY.getValue()).doubleValue());
    event.setMainZ(((Double)this.mainZ.getValue()).doubleValue());
    event.setOffX(-((Double)this.offX.getValue()).doubleValue());
    event.setOffY(((Double)this.offY.getValue()).doubleValue());
    event.setOffZ(((Double)this.offZ.getValue()).doubleValue());
  }
  
  private enum Settings {
    TRANSLATE, TWEAKS;
  }
}
