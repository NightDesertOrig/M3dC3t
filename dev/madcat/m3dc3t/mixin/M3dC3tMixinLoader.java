package dev.madcat.m3dc3t.mixin;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

@Name("M3dC3t")
public class M3dC3tMixinLoader implements IFMLLoadingPlugin {
  private static boolean isObfuscatedEnvironment = false;
  
  public M3dC3tMixinLoader() {
    System.out.println("Loading mixins by MadCat");
    MixinBootstrap.init();
    Mixins.addConfiguration("mixins.m3dc3t.json");
    MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    System.out.println(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
  }
  
  public String[] getASMTransformerClass() {
    return new String[0];
  }
  
  public String getModContainerClass() {
    return null;
  }
  
  public String getSetupClass() {
    return null;
  }
  
  public void injectData(Map<String, Object> data) {
    isObfuscatedEnvironment = ((Boolean)data.get("runtimeDeobfuscationEnabled")).booleanValue();
  }
  
  public String getAccessTransformerClass() {
    return null;
  }
}
