package org.junit.runner;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Description implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public static Description createSuiteDescription(String name, Annotation... annotations) {
    if (name.length() == 0)
      throw new IllegalArgumentException("name must have non-zero length"); 
    return new Description(name, annotations);
  }
  
  public static Description createTestDescription(Class<?> clazz, String name, Annotation... annotations) {
    return new Description(String.format("%s(%s)", new Object[] { name, clazz.getName() }), annotations);
  }
  
  public static Description createTestDescription(Class<?> clazz, String name) {
    return createTestDescription(clazz, name, new Annotation[0]);
  }
  
  public static Description createSuiteDescription(Class<?> testClass) {
    return new Description(testClass.getName(), testClass.getAnnotations());
  }
  
  public static final Description EMPTY = new Description("No Tests", new Annotation[0]);
  
  public static final Description TEST_MECHANISM = new Description("Test mechanism", new Annotation[0]);
  
  private final ArrayList<Description> fChildren = new ArrayList<Description>();
  
  private final String fDisplayName;
  
  private final Annotation[] fAnnotations;
  
  private Description(String displayName, Annotation... annotations) {
    this.fDisplayName = displayName;
    this.fAnnotations = annotations;
  }
  
  public String getDisplayName() {
    return this.fDisplayName;
  }
  
  public void addChild(Description description) {
    getChildren().add(description);
  }
  
  public ArrayList<Description> getChildren() {
    return this.fChildren;
  }
  
  public boolean isSuite() {
    return !isTest();
  }
  
  public boolean isTest() {
    return getChildren().isEmpty();
  }
  
  public int testCount() {
    if (isTest())
      return 1; 
    int result = 0;
    for (Description child : getChildren())
      result += child.testCount(); 
    return result;
  }
  
  public int hashCode() {
    return getDisplayName().hashCode();
  }
  
  public boolean equals(Object obj) {
    if (!(obj instanceof Description))
      return false; 
    Description d = (Description)obj;
    return getDisplayName().equals(d.getDisplayName());
  }
  
  public String toString() {
    return getDisplayName();
  }
  
  public boolean isEmpty() {
    return equals(EMPTY);
  }
  
  public Description childlessCopy() {
    return new Description(this.fDisplayName, this.fAnnotations);
  }
  
  public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
    for (Annotation each : this.fAnnotations) {
      if (each.annotationType().equals(annotationType))
        return annotationType.cast(each); 
    } 
    return null;
  }
  
  public Collection<Annotation> getAnnotations() {
    return Arrays.asList(this.fAnnotations);
  }
  
  public Class<?> getTestClass() {
    String name = getClassName();
    if (name == null)
      return null; 
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException e) {
      return null;
    } 
  }
  
  public String getClassName() {
    Matcher matcher = methodStringMatcher();
    return matcher.matches() ? matcher.group(2) : toString();
  }
  
  public String getMethodName() {
    return parseMethod();
  }
  
  private String parseMethod() {
    Matcher matcher = methodStringMatcher();
    if (matcher.matches())
      return matcher.group(1); 
    return null;
  }
  
  private Matcher methodStringMatcher() {
    return Pattern.compile("(.*)\\((.*)\\)").matcher(toString());
  }
}
