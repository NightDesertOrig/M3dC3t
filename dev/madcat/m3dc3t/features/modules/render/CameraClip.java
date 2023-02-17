package dev.madcat.m3dc3t.features.modules.render;

import dev.madcat.m3dc3t.features.modules.Module;
import dev.madcat.m3dc3t.features.setting.Setting;

public class CameraClip extends Module {
  public Setting<Double> distance;
  
  public CameraClip() {
    super("CameraClip", "Allow Camera pierce through.", Module.Category.RENDER, false, false, false);
    this.distance = register(new Setting("Distance", Double.valueOf(4.0D), Double.valueOf(-10.0D), Double.valueOf(20.0D)));
  }
}
