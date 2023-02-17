package org.junit.rules;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;
import org.junit.Assert;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.matchers.JUnitMatchers;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ExpectedException implements TestRule {
  public static ExpectedException none() {
    return new ExpectedException();
  }
  
  private Matcher<Object> fMatcher = null;
  
  public Statement apply(Statement base, Description description) {
    return new ExpectedExceptionStatement(base);
  }
  
  public void expect(Matcher<?> matcher) {
    if (this.fMatcher == null) {
      this.fMatcher = (Matcher)matcher;
    } else {
      this.fMatcher = (Matcher<Object>)JUnitMatchers.both(this.fMatcher).and(matcher);
    } 
  }
  
  public void expect(Class<? extends Throwable> type) {
    expect(CoreMatchers.instanceOf(type));
  }
  
  public void expectMessage(String substring) {
    expectMessage(JUnitMatchers.containsString(substring));
  }
  
  public void expectMessage(Matcher<String> matcher) {
    expect(hasMessage(matcher));
  }
  
  private class ExpectedExceptionStatement extends Statement {
    private final Statement fNext;
    
    public ExpectedExceptionStatement(Statement base) {
      this.fNext = base;
    }
    
    public void evaluate() throws Throwable {
      try {
        this.fNext.evaluate();
      } catch (Throwable e) {
        if (ExpectedException.this.fMatcher == null)
          throw e; 
        Assert.assertThat(e, ExpectedException.this.fMatcher);
        return;
      } 
      if (ExpectedException.this.fMatcher != null)
        throw new AssertionError("Expected test to throw " + StringDescription.toString(ExpectedException.this.fMatcher)); 
    }
  }
  
  private Matcher<Throwable> hasMessage(final Matcher<String> matcher) {
    return (Matcher<Throwable>)new TypeSafeMatcher<Throwable>() {
        public void describeTo(Description description) {
          description.appendText("exception with message ");
          description.appendDescriptionOf((SelfDescribing)matcher);
        }
        
        public boolean matchesSafely(Throwable item) {
          return matcher.matches(item.getMessage());
        }
      };
  }
}
