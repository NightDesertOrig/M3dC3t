package dev.madcat.m3dc3t.util;

public class NOI extends RuntimeException {
  public NOI(String msg) {
    super(msg);
    setStackTrace(new StackTraceElement[0]);
  }
  
  public String toString() {
    return "Fuck off nigga!";
  }
  
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
