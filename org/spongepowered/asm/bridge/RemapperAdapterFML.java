package org.spongepowered.asm.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public final class RemapperAdapterFML extends RemapperAdapter {
  private static final String DEOBFUSCATING_REMAPPER_CLASS = "fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
  
  private static final String DEOBFUSCATING_REMAPPER_CLASS_FORGE = "net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
  
  private static final String DEOBFUSCATING_REMAPPER_CLASS_LEGACY = "cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
  
  private static final String INSTANCE_FIELD = "INSTANCE";
  
  private static final String UNMAP_METHOD = "unmap";
  
  private final Method mdUnmap;
  
  private RemapperAdapterFML(Remapper remapper, Method mdUnmap) {
    super(remapper);
    this.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[] { remapper });
    this.mdUnmap = mdUnmap;
  }
  
  public String unmap(String typeName) {
    try {
      return this.mdUnmap.invoke(this.remapper, new Object[] { typeName }).toString();
    } catch (Exception ex) {
      return typeName;
    } 
  }
  
  public static IRemapper create() {
    try {
      Class<?> clDeobfRemapper = getFMLDeobfuscatingRemapper();
      Field singletonField = clDeobfRemapper.getDeclaredField("INSTANCE");
      Method mdUnmap = clDeobfRemapper.getDeclaredMethod("unmap", new Class[] { String.class });
      Remapper remapper = (Remapper)singletonField.get(null);
      return new RemapperAdapterFML(remapper, mdUnmap);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  private static Class<?> getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
    try {
      return Class.forName("net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
    } catch (ClassNotFoundException ex) {
      return Class.forName("cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
    } 
  }
}
