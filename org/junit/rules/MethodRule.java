package org.junit.rules;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

@Deprecated
public interface MethodRule {
  Statement apply(Statement paramStatement, FrameworkMethod paramFrameworkMethod, Object paramObject);
}
