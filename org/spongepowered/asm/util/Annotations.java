package org.spongepowered.asm.util;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;

public final class Annotations {
  public static void setVisible(FieldNode field, Class<? extends Annotation> annotationClass, Object... value) {
    AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
    field.visibleAnnotations = add(field.visibleAnnotations, node);
  }
  
  public static void setInvisible(FieldNode field, Class<? extends Annotation> annotationClass, Object... value) {
    AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
    field.invisibleAnnotations = add(field.invisibleAnnotations, node);
  }
  
  public static void setVisible(MethodNode method, Class<? extends Annotation> annotationClass, Object... value) {
    AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
    method.visibleAnnotations = add(method.visibleAnnotations, node);
  }
  
  public static void setInvisible(MethodNode method, Class<? extends Annotation> annotationClass, Object... value) {
    AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
    method.invisibleAnnotations = add(method.invisibleAnnotations, node);
  }
  
  private static AnnotationNode createNode(String annotationType, Object... value) {
    AnnotationNode node = new AnnotationNode(annotationType);
    for (int pos = 0; pos < value.length - 1; pos += 2) {
      if (!(value[pos] instanceof String))
        throw new IllegalArgumentException("Annotation keys must be strings, found " + value[pos].getClass().getSimpleName() + " with " + value[pos]
            .toString() + " at index " + pos + " creating " + annotationType); 
      node.visit((String)value[pos], value[pos + 1]);
    } 
    return node;
  }
  
  private static List<AnnotationNode> add(List<AnnotationNode> annotations, AnnotationNode node) {
    if (annotations == null) {
      annotations = new ArrayList<AnnotationNode>(1);
    } else {
      annotations.remove(get(annotations, node.desc));
    } 
    annotations.add(node);
    return annotations;
  }
  
  public static AnnotationNode getVisible(FieldNode field, Class<? extends Annotation> annotationClass) {
    return get(field.visibleAnnotations, Type.getDescriptor(annotationClass));
  }
  
  public static AnnotationNode getInvisible(FieldNode field, Class<? extends Annotation> annotationClass) {
    return get(field.invisibleAnnotations, Type.getDescriptor(annotationClass));
  }
  
  public static AnnotationNode getVisible(MethodNode method, Class<? extends Annotation> annotationClass) {
    return get(method.visibleAnnotations, Type.getDescriptor(annotationClass));
  }
  
  public static AnnotationNode getInvisible(MethodNode method, Class<? extends Annotation> annotationClass) {
    return get(method.invisibleAnnotations, Type.getDescriptor(annotationClass));
  }
  
  public static AnnotationNode getSingleVisible(MethodNode method, Class<? extends Annotation>... annotationClasses) {
    return getSingle(method.visibleAnnotations, annotationClasses);
  }
  
  public static AnnotationNode getSingleInvisible(MethodNode method, Class<? extends Annotation>... annotationClasses) {
    return getSingle(method.invisibleAnnotations, annotationClasses);
  }
  
  public static AnnotationNode getVisible(ClassNode classNode, Class<? extends Annotation> annotationClass) {
    return get(classNode.visibleAnnotations, Type.getDescriptor(annotationClass));
  }
  
  public static AnnotationNode getInvisible(ClassNode classNode, Class<? extends Annotation> annotationClass) {
    return get(classNode.invisibleAnnotations, Type.getDescriptor(annotationClass));
  }
  
  public static AnnotationNode getVisibleParameter(MethodNode method, Class<? extends Annotation> annotationClass, int paramIndex) {
    return getParameter((List<AnnotationNode>[])method.visibleParameterAnnotations, Type.getDescriptor(annotationClass), paramIndex);
  }
  
  public static AnnotationNode getInvisibleParameter(MethodNode method, Class<? extends Annotation> annotationClass, int paramIndex) {
    return getParameter((List<AnnotationNode>[])method.invisibleParameterAnnotations, Type.getDescriptor(annotationClass), paramIndex);
  }
  
  public static AnnotationNode getParameter(List<AnnotationNode>[] parameterAnnotations, String annotationType, int paramIndex) {
    if (parameterAnnotations == null || paramIndex < 0 || paramIndex >= parameterAnnotations.length)
      return null; 
    return get(parameterAnnotations[paramIndex], annotationType);
  }
  
  public static AnnotationNode get(List<AnnotationNode> annotations, String annotationType) {
    if (annotations == null)
      return null; 
    for (AnnotationNode annotation : annotations) {
      if (annotationType.equals(annotation.desc))
        return annotation; 
    } 
    return null;
  }
  
  private static AnnotationNode getSingle(List<AnnotationNode> annotations, Class<? extends Annotation>[] annotationClasses) {
    List<AnnotationNode> nodes = new ArrayList<AnnotationNode>();
    for (Class<? extends Annotation> annotationClass : annotationClasses) {
      AnnotationNode annotation = get(annotations, Type.getDescriptor(annotationClass));
      if (annotation != null)
        nodes.add(annotation); 
    } 
    int foundNodes = nodes.size();
    if (foundNodes > 1)
      throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(nodes, new Function<AnnotationNode, String>() {
              public String apply(AnnotationNode input) {
                return input.desc;
              }
            })); 
    return (foundNodes == 0) ? null : nodes.get(0);
  }
  
  public static <T> T getValue(AnnotationNode annotation) {
    return getValue(annotation, "value");
  }
  
  public static <T> T getValue(AnnotationNode annotation, String key, T defaultValue) {
    T returnValue = getValue(annotation, key);
    return (returnValue != null) ? returnValue : defaultValue;
  }
  
  public static <T> T getValue(AnnotationNode annotation, String key, Class<?> annotationClass) {
    Preconditions.checkNotNull(annotationClass, "annotationClass cannot be null");
    T value = getValue(annotation, key);
    if (value == null)
      try {
        value = (T)annotationClass.getDeclaredMethod(key, new Class[0]).getDefaultValue();
      } catch (NoSuchMethodException noSuchMethodException) {} 
    return value;
  }
  
  public static <T> T getValue(AnnotationNode annotation, String key) {
    boolean getNextValue = false;
    if (annotation == null || annotation.values == null)
      return null; 
    for (Object value : annotation.values) {
      if (getNextValue)
        return (T)value; 
      if (value.equals(key))
        getNextValue = true; 
    } 
    return null;
  }
  
  public static <T extends Enum<T>> T getValue(AnnotationNode annotation, String key, Class<T> enumClass, T defaultValue) {
    String[] value = getValue(annotation, key);
    if (value == null)
      return defaultValue; 
    return toEnumValue(enumClass, value);
  }
  
  public static <T> List<T> getValue(AnnotationNode annotation, String key, boolean notNull) {
    Object value = getValue(annotation, key);
    if (value instanceof List)
      return (List<T>)value; 
    if (value != null) {
      List<T> list = new ArrayList<T>();
      list.add((T)value);
      return list;
    } 
    return Collections.emptyList();
  }
  
  public static <T extends Enum<T>> List<T> getValue(AnnotationNode annotation, String key, boolean notNull, Class<T> enumClass) {
    Object value = getValue(annotation, key);
    if (value instanceof List) {
      for (ListIterator<Object> iter = ((List<Object>)value).listIterator(); iter.hasNext();)
        iter.set(toEnumValue(enumClass, (String[])iter.next())); 
      return (List<T>)value;
    } 
    if (value instanceof String[]) {
      List<T> list = new ArrayList<T>();
      list.add(toEnumValue(enumClass, (String[])value));
      return list;
    } 
    return Collections.emptyList();
  }
  
  private static <T extends Enum<T>> T toEnumValue(Class<T> enumClass, String[] value) {
    if (!enumClass.getName().equals(Type.getType(value[0]).getClassName()))
      throw new IllegalArgumentException("The supplied enum class does not match the stored enum value"); 
    return Enum.valueOf(enumClass, value[1]);
  }
}
