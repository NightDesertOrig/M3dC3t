package org.junit.runners;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class Parameterized extends Suite {
  private class TestClassRunnerForParameters extends BlockJUnit4ClassRunner {
    private final int fParameterSetNumber;
    
    private final List<Object[]> fParameterList;
    
    TestClassRunnerForParameters(Class<?> type, List<Object[]> parameterList, int i) throws InitializationError {
      super(type);
      this.fParameterList = parameterList;
      this.fParameterSetNumber = i;
    }
    
    public Object createTest() throws Exception {
      return getTestClass().getOnlyConstructor().newInstance(computeParams());
    }
    
    private Object[] computeParams() throws Exception {
      try {
        return this.fParameterList.get(this.fParameterSetNumber);
      } catch (ClassCastException e) {
        throw new Exception(String.format("%s.%s() must return a Collection of arrays.", new Object[] { getTestClass().getName(), Parameterized.access$000(this.this$0, getTestClass()).getName() }));
      } 
    }
    
    protected String getName() {
      return String.format("[%s]", new Object[] { Integer.valueOf(this.fParameterSetNumber) });
    }
    
    protected String testName(FrameworkMethod method) {
      return String.format("%s[%s]", new Object[] { method.getName(), Integer.valueOf(this.fParameterSetNumber) });
    }
    
    protected void validateConstructor(List<Throwable> errors) {
      validateOnlyOneConstructor(errors);
    }
    
    protected Statement classBlock(RunNotifier notifier) {
      return childrenInvoker(notifier);
    }
    
    protected Annotation[] getRunnerAnnotations() {
      return new Annotation[0];
    }
  }
  
  private final ArrayList<Runner> runners = new ArrayList<Runner>();
  
  public Parameterized(Class<?> klass) throws Throwable {
    super(klass, Collections.emptyList());
    List<Object[]> parametersList = getParametersList(getTestClass());
    for (int i = 0; i < parametersList.size(); i++)
      this.runners.add(new TestClassRunnerForParameters(getTestClass().getJavaClass(), parametersList, i)); 
  }
  
  protected List<Runner> getChildren() {
    return this.runners;
  }
  
  private List<Object[]> getParametersList(TestClass klass) throws Throwable {
    return (List<Object[]>)getParametersMethod(klass).invokeExplosively(null, new Object[0]);
  }
  
  private FrameworkMethod getParametersMethod(TestClass testClass) throws Exception {
    List<FrameworkMethod> methods = testClass.getAnnotatedMethods(Parameters.class);
    for (FrameworkMethod each : methods) {
      int modifiers = each.getMethod().getModifiers();
      if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers))
        return each; 
    } 
    throw new Exception("No public static parameters method on class " + testClass.getName());
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD})
  public static @interface Parameters {}
}
