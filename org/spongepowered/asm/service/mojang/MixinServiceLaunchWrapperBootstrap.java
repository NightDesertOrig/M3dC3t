package org.spongepowered.asm.service.mojang;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.service.IMixinServiceBootstrap;

public class MixinServiceLaunchWrapperBootstrap implements IMixinServiceBootstrap {
  private static final String SERVICE_PACKAGE = "org.spongepowered.asm.service.";
  
  private static final String MIXIN_UTIL_PACKAGE = "org.spongepowered.asm.util.";
  
  private static final String ASM_PACKAGE = "org.spongepowered.asm.lib.";
  
  private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
  
  public String getName() {
    return "LaunchWrapper";
  }
  
  public String getServiceClassName() {
    return "org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper";
  }
  
  public void bootstrap() {
    Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.service.");
    Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.lib.");
    Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.mixin.");
    Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.util.");
  }
}
