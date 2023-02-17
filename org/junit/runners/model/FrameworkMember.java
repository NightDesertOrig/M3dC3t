package org.junit.runners.model;

import java.lang.annotation.Annotation;
import java.util.List;

abstract class FrameworkMember<T extends FrameworkMember<T>> {
  abstract Annotation[] getAnnotations();
  
  abstract boolean isShadowedBy(T paramT);
  
  boolean isShadowedBy(List<T> members) {
    for (FrameworkMember frameworkMember : members) {
      if (isShadowedBy((T)frameworkMember))
        return true; 
    } 
    return false;
  }
}
