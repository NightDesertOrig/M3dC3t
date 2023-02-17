package org.junit.internal.runners.rules;

import java.lang.annotation.Annotation;
import java.util.List;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.TestClass;

public enum RuleFieldValidator {
  CLASS_RULE_VALIDATOR((Class)ClassRule.class, true),
  RULE_VALIDATOR((Class)Rule.class, false);
  
  private final Class<? extends Annotation> fAnnotation;
  
  private final boolean fOnlyStaticFields;
  
  RuleFieldValidator(Class<? extends Annotation> annotation, boolean onlyStaticFields) {
    this.fAnnotation = annotation;
    this.fOnlyStaticFields = onlyStaticFields;
  }
  
  public void validate(TestClass target, List<Throwable> errors) {
    List<FrameworkField> fields = target.getAnnotatedFields(this.fAnnotation);
    for (FrameworkField each : fields)
      validateField(each, errors); 
  }
  
  private void validateField(FrameworkField field, List<Throwable> errors) {
    optionallyValidateStatic(field, errors);
    validatePublic(field, errors);
    validateTestRuleOrMethodRule(field, errors);
  }
  
  private void optionallyValidateStatic(FrameworkField field, List<Throwable> errors) {
    if (this.fOnlyStaticFields && !field.isStatic())
      addError(errors, field, "must be static."); 
  }
  
  private void validatePublic(FrameworkField field, List<Throwable> errors) {
    if (!field.isPublic())
      addError(errors, field, "must be public."); 
  }
  
  private void validateTestRuleOrMethodRule(FrameworkField field, List<Throwable> errors) {
    if (!isMethodRule(field) && !isTestRule(field))
      addError(errors, field, "must implement MethodRule or TestRule."); 
  }
  
  private boolean isTestRule(FrameworkField target) {
    return TestRule.class.isAssignableFrom(target.getType());
  }
  
  private boolean isMethodRule(FrameworkField target) {
    return MethodRule.class.isAssignableFrom(target.getType());
  }
  
  private void addError(List<Throwable> errors, FrameworkField field, String suffix) {
    String message = "The @" + this.fAnnotation.getSimpleName() + " '" + field.getName() + "' " + suffix;
    errors.add(new Exception(message));
  }
}
