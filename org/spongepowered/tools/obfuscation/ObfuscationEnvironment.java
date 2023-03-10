package org.spongepowered.tools.obfuscation;

import java.io.File;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ObfuscationUtil;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public abstract class ObfuscationEnvironment implements IObfuscationEnvironment {
  protected final ObfuscationType type;
  
  protected final IMappingProvider mappingProvider;
  
  protected final IMappingWriter mappingWriter;
  
  final class RemapperProxy implements ObfuscationUtil.IClassRemapper {
    public String map(String typeName) {
      if (ObfuscationEnvironment.this.mappingProvider == null)
        return null; 
      return ObfuscationEnvironment.this.mappingProvider.getClassMapping(typeName);
    }
    
    public String unmap(String typeName) {
      if (ObfuscationEnvironment.this.mappingProvider == null)
        return null; 
      return ObfuscationEnvironment.this.mappingProvider.getClassMapping(typeName);
    }
  }
  
  protected final RemapperProxy remapper = new RemapperProxy();
  
  protected final IMixinAnnotationProcessor ap;
  
  protected final String outFileName;
  
  protected final List<String> inFileNames;
  
  private boolean initDone;
  
  protected ObfuscationEnvironment(ObfuscationType type) {
    this.type = type;
    this.ap = type.getAnnotationProcessor();
    this.inFileNames = type.getInputFileNames();
    this.outFileName = type.getOutputFileName();
    this.mappingProvider = getMappingProvider((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
    this.mappingWriter = getMappingWriter((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
  }
  
  public String toString() {
    return this.type.toString();
  }
  
  protected abstract IMappingProvider getMappingProvider(Messager paramMessager, Filer paramFiler);
  
  protected abstract IMappingWriter getMappingWriter(Messager paramMessager, Filer paramFiler);
  
  private boolean initMappings() {
    if (!this.initDone) {
      this.initDone = true;
      if (this.inFileNames == null) {
        this.ap.printMessage(Diagnostic.Kind.ERROR, "The " + this.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur");
        return false;
      } 
      int successCount = 0;
      for (String inputFileName : this.inFileNames) {
        File inputFile = new File(inputFileName);
        try {
          if (inputFile.isFile()) {
            this.ap.printMessage(Diagnostic.Kind.NOTE, "Loading " + this.type + " mappings from " + inputFile.getAbsolutePath());
            this.mappingProvider.read(inputFile);
            successCount++;
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } 
      if (successCount < 1) {
        this.ap.printMessage(Diagnostic.Kind.ERROR, "No valid input files for " + this.type + " could be read, processing may not be sucessful.");
        this.mappingProvider.clear();
      } 
    } 
    return !this.mappingProvider.isEmpty();
  }
  
  public ObfuscationType getType() {
    return this.type;
  }
  
  public MappingMethod getObfMethod(MemberInfo method) {
    MappingMethod obfd = getObfMethod(method.asMethodMapping());
    if (obfd != null || !method.isFullyQualified())
      return obfd; 
    TypeHandle type = this.ap.getTypeProvider().getTypeHandle(method.owner);
    if (type == null || type.isImaginary())
      return null; 
    TypeMirror superClass = type.getElement().getSuperclass();
    if (superClass.getKind() != TypeKind.DECLARED)
      return null; 
    String superClassName = ((TypeElement)((DeclaredType)superClass).asElement()).getQualifiedName().toString();
    return getObfMethod(new MemberInfo(method.name, superClassName.replace('.', '/'), method.desc, method.matchAll));
  }
  
  public MappingMethod getObfMethod(MappingMethod method) {
    return getObfMethod(method, true);
  }
  
  public MappingMethod getObfMethod(MappingMethod method, boolean lazyRemap) {
    if (initMappings()) {
      boolean remapped = true;
      MappingMethod mapping = null;
      for (MappingMethod md = method; md != null && mapping == null; md = md.getSuper())
        mapping = this.mappingProvider.getMethodMapping(md); 
      if (mapping == null) {
        if (lazyRemap)
          return null; 
        mapping = method.copy();
        remapped = false;
      } 
      String remappedOwner = getObfClass(mapping.getOwner());
      if (remappedOwner == null || remappedOwner.equals(method.getOwner()) || remappedOwner.equals(mapping.getOwner()))
        return remapped ? mapping : null; 
      if (remapped)
        return mapping.move(remappedOwner); 
      String desc = ObfuscationUtil.mapDescriptor(mapping.getDesc(), this.remapper);
      return new MappingMethod(remappedOwner, mapping.getSimpleName(), desc);
    } 
    return null;
  }
  
  public MemberInfo remapDescriptor(MemberInfo method) {
    boolean transformed = false;
    String owner = method.owner;
    if (owner != null) {
      String newOwner = this.remapper.map(owner);
      if (newOwner != null) {
        owner = newOwner;
        transformed = true;
      } 
    } 
    String desc = method.desc;
    if (desc != null) {
      String newDesc = ObfuscationUtil.mapDescriptor(method.desc, this.remapper);
      if (!newDesc.equals(method.desc)) {
        desc = newDesc;
        transformed = true;
      } 
    } 
    return transformed ? new MemberInfo(method.name, owner, desc, method.matchAll) : null;
  }
  
  public String remapDescriptor(String desc) {
    return ObfuscationUtil.mapDescriptor(desc, this.remapper);
  }
  
  public MappingField getObfField(MemberInfo field) {
    return getObfField(field.asFieldMapping(), true);
  }
  
  public MappingField getObfField(MappingField field) {
    return getObfField(field, true);
  }
  
  public MappingField getObfField(MappingField field, boolean lazyRemap) {
    if (!initMappings())
      return null; 
    MappingField mapping = this.mappingProvider.getFieldMapping(field);
    if (mapping == null) {
      if (lazyRemap)
        return null; 
      mapping = field;
    } 
    String remappedOwner = getObfClass(mapping.getOwner());
    if (remappedOwner == null || remappedOwner.equals(field.getOwner()) || remappedOwner.equals(mapping.getOwner()))
      return (mapping != field) ? mapping : null; 
    return mapping.move(remappedOwner);
  }
  
  public String getObfClass(String className) {
    if (!initMappings())
      return null; 
    return this.mappingProvider.getClassMapping(className);
  }
  
  public void writeMappings(Collection<IMappingConsumer> consumers) {
    IMappingConsumer.MappingSet<MappingField> fields = new IMappingConsumer.MappingSet();
    IMappingConsumer.MappingSet<MappingMethod> methods = new IMappingConsumer.MappingSet();
    for (IMappingConsumer mappings : consumers) {
      fields.addAll((Collection)mappings.getFieldMappings(this.type));
      methods.addAll((Collection)mappings.getMethodMappings(this.type));
    } 
    this.mappingWriter.write(this.outFileName, this.type, fields, methods);
  }
}
