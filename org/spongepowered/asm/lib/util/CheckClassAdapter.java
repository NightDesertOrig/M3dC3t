package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.Interpreter;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;

public class CheckClassAdapter extends ClassVisitor {
  private int version;
  
  private boolean start;
  
  private boolean source;
  
  private boolean outer;
  
  private boolean end;
  
  private Map<Label, Integer> labels;
  
  private boolean checkDataFlow;
  
  public static void main(String[] args) throws Exception {
    ClassReader cr;
    if (args.length != 1) {
      System.err.println("Verifies the given class.");
      System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
      return;
    } 
    if (args[0].endsWith(".class")) {
      cr = new ClassReader(new FileInputStream(args[0]));
    } else {
      cr = new ClassReader(args[0]);
    } 
    verify(cr, false, new PrintWriter(System.err));
  }
  
  public static void verify(ClassReader cr, ClassLoader loader, boolean dump, PrintWriter pw) {
    ClassNode cn = new ClassNode();
    cr.accept(new CheckClassAdapter((ClassVisitor)cn, false), 2);
    Type syperType = (cn.superName == null) ? null : Type.getObjectType(cn.superName);
    List<MethodNode> methods = cn.methods;
    List<Type> interfaces = new ArrayList<Type>();
    for (Iterator<String> iterator = cn.interfaces.iterator(); iterator.hasNext();)
      interfaces.add(Type.getObjectType(iterator.next())); 
    for (int i = 0; i < methods.size(); i++) {
      MethodNode method = methods.get(i);
      SimpleVerifier verifier = new SimpleVerifier(Type.getObjectType(cn.name), syperType, interfaces, ((cn.access & 0x200) != 0));
      Analyzer<BasicValue> a = new Analyzer((Interpreter)verifier);
      if (loader != null)
        verifier.setClassLoader(loader); 
      try {
        a.analyze(cn.name, method);
        if (!dump)
          continue; 
      } catch (Exception e) {
        e.printStackTrace(pw);
      } 
      printAnalyzerResult(method, a, pw);
      continue;
    } 
    pw.flush();
  }
  
  public static void verify(ClassReader cr, boolean dump, PrintWriter pw) {
    verify(cr, null, dump, pw);
  }
  
  static void printAnalyzerResult(MethodNode method, Analyzer<BasicValue> a, PrintWriter pw) {
    Frame[] arrayOfFrame = a.getFrames();
    Textifier t = new Textifier();
    TraceMethodVisitor mv = new TraceMethodVisitor(t);
    pw.println(method.name + method.desc);
    int j;
    for (j = 0; j < method.instructions.size(); j++) {
      method.instructions.get(j).accept(mv);
      StringBuilder sb = new StringBuilder();
      Frame<BasicValue> f = arrayOfFrame[j];
      if (f == null) {
        sb.append('?');
      } else {
        int k;
        for (k = 0; k < f.getLocals(); k++)
          sb.append(getShortName(((BasicValue)f.getLocal(k)).toString()))
            .append(' '); 
        sb.append(" : ");
        for (k = 0; k < f.getStackSize(); k++)
          sb.append(getShortName(((BasicValue)f.getStack(k)).toString()))
            .append(' '); 
      } 
      while (sb.length() < method.maxStack + method.maxLocals + 1)
        sb.append(' '); 
      pw.print(Integer.toString(j + 100000).substring(1));
      pw.print(" " + sb + " : " + t.text.get(t.text.size() - 1));
    } 
    for (j = 0; j < method.tryCatchBlocks.size(); j++) {
      ((TryCatchBlockNode)method.tryCatchBlocks.get(j)).accept(mv);
      pw.print(" " + t.text.get(t.text.size() - 1));
    } 
    pw.println();
  }
  
  private static String getShortName(String name) {
    int n = name.lastIndexOf('/');
    int k = name.length();
    if (name.charAt(k - 1) == ';')
      k--; 
    return (n == -1) ? name : name.substring(n + 1, k);
  }
  
  public CheckClassAdapter(ClassVisitor cv) {
    this(cv, true);
  }
  
  public CheckClassAdapter(ClassVisitor cv, boolean checkDataFlow) {
    this(327680, cv, checkDataFlow);
    if (getClass() != CheckClassAdapter.class)
      throw new IllegalStateException(); 
  }
  
  protected CheckClassAdapter(int api, ClassVisitor cv, boolean checkDataFlow) {
    super(api, cv);
    this.labels = new HashMap<Label, Integer>();
    this.checkDataFlow = checkDataFlow;
  }
  
  public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    if (this.start)
      throw new IllegalStateException("visit must be called only once"); 
    this.start = true;
    checkState();
    checkAccess(access, 423473);
    if (name == null || !name.endsWith("package-info"))
      CheckMethodAdapter.checkInternalName(name, "class name"); 
    if ("java/lang/Object".equals(name)) {
      if (superName != null)
        throw new IllegalArgumentException("The super class name of the Object class must be 'null'"); 
    } else {
      CheckMethodAdapter.checkInternalName(superName, "super class name");
    } 
    if (signature != null)
      checkClassSignature(signature); 
    if ((access & 0x200) != 0 && 
      !"java/lang/Object".equals(superName))
      throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'"); 
    if (interfaces != null)
      for (int i = 0; i < interfaces.length; i++)
        CheckMethodAdapter.checkInternalName(interfaces[i], "interface name at index " + i);  
    this.version = version;
    super.visit(version, access, name, signature, superName, interfaces);
  }
  
  public void visitSource(String file, String debug) {
    checkState();
    if (this.source)
      throw new IllegalStateException("visitSource can be called only once."); 
    this.source = true;
    super.visitSource(file, debug);
  }
  
  public void visitOuterClass(String owner, String name, String desc) {
    checkState();
    if (this.outer)
      throw new IllegalStateException("visitOuterClass can be called only once."); 
    this.outer = true;
    if (owner == null)
      throw new IllegalArgumentException("Illegal outer class owner"); 
    if (desc != null)
      CheckMethodAdapter.checkMethodDesc(desc); 
    super.visitOuterClass(owner, name, desc);
  }
  
  public void visitInnerClass(String name, String outerName, String innerName, int access) {
    checkState();
    CheckMethodAdapter.checkInternalName(name, "class name");
    if (outerName != null)
      CheckMethodAdapter.checkInternalName(outerName, "outer class name"); 
    if (innerName != null) {
      int start = 0;
      while (start < innerName.length() && 
        Character.isDigit(innerName.charAt(start)))
        start++; 
      if (start == 0 || start < innerName.length())
        CheckMethodAdapter.checkIdentifier(innerName, start, -1, "inner class name"); 
    } 
    checkAccess(access, 30239);
    super.visitInnerClass(name, outerName, innerName, access);
  }
  
  public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
    checkState();
    checkAccess(access, 413919);
    CheckMethodAdapter.checkUnqualifiedName(this.version, name, "field name");
    CheckMethodAdapter.checkDesc(desc, false);
    if (signature != null)
      checkFieldSignature(signature); 
    if (value != null)
      CheckMethodAdapter.checkConstant(value); 
    FieldVisitor av = super.visitField(access, name, desc, signature, value);
    return new CheckFieldAdapter(av);
  }
  
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    CheckMethodAdapter cma;
    checkState();
    checkAccess(access, 400895);
    if (!"<init>".equals(name) && !"<clinit>".equals(name))
      CheckMethodAdapter.checkMethodIdentifier(this.version, name, "method name"); 
    CheckMethodAdapter.checkMethodDesc(desc);
    if (signature != null)
      checkMethodSignature(signature); 
    if (exceptions != null)
      for (int i = 0; i < exceptions.length; i++)
        CheckMethodAdapter.checkInternalName(exceptions[i], "exception name at index " + i);  
    if (this.checkDataFlow) {
      cma = new CheckMethodAdapter(access, name, desc, super.visitMethod(access, name, desc, signature, exceptions), this.labels);
    } else {
      cma = new CheckMethodAdapter(super.visitMethod(access, name, desc, signature, exceptions), this.labels);
    } 
    cma.version = this.version;
    return cma;
  }
  
  public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    checkState();
    CheckMethodAdapter.checkDesc(desc, false);
    return new CheckAnnotationAdapter(super.visitAnnotation(desc, visible));
  }
  
  public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
    checkState();
    int sort = typeRef >>> 24;
    if (sort != 0 && sort != 17 && sort != 16)
      throw new IllegalArgumentException("Invalid type reference sort 0x" + 
          Integer.toHexString(sort)); 
    checkTypeRefAndPath(typeRef, typePath);
    CheckMethodAdapter.checkDesc(desc, false);
    return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, desc, visible));
  }
  
  public void visitAttribute(Attribute attr) {
    checkState();
    if (attr == null)
      throw new IllegalArgumentException("Invalid attribute (must not be null)"); 
    super.visitAttribute(attr);
  }
  
  public void visitEnd() {
    checkState();
    this.end = true;
    super.visitEnd();
  }
  
  private void checkState() {
    if (!this.start)
      throw new IllegalStateException("Cannot visit member before visit has been called."); 
    if (this.end)
      throw new IllegalStateException("Cannot visit member after visitEnd has been called."); 
  }
  
  static void checkAccess(int access, int possibleAccess) {
    if ((access & (possibleAccess ^ 0xFFFFFFFF)) != 0)
      throw new IllegalArgumentException("Invalid access flags: " + access); 
    int pub = ((access & 0x1) == 0) ? 0 : 1;
    int pri = ((access & 0x2) == 0) ? 0 : 1;
    int pro = ((access & 0x4) == 0) ? 0 : 1;
    if (pub + pri + pro > 1)
      throw new IllegalArgumentException("public private and protected are mutually exclusive: " + access); 
    int fin = ((access & 0x10) == 0) ? 0 : 1;
    int abs = ((access & 0x400) == 0) ? 0 : 1;
    if (fin + abs > 1)
      throw new IllegalArgumentException("final and abstract are mutually exclusive: " + access); 
  }
  
  public static void checkClassSignature(String signature) {
    int pos = 0;
    if (getChar(signature, 0) == '<')
      pos = checkFormalTypeParameters(signature, pos); 
    pos = checkClassTypeSignature(signature, pos);
    while (getChar(signature, pos) == 'L')
      pos = checkClassTypeSignature(signature, pos); 
    if (pos != signature.length())
      throw new IllegalArgumentException(signature + ": error at index " + pos); 
  }
  
  public static void checkMethodSignature(String signature) {
    int pos = 0;
    if (getChar(signature, 0) == '<')
      pos = checkFormalTypeParameters(signature, pos); 
    pos = checkChar('(', signature, pos);
    while ("ZCBSIFJDL[T".indexOf(getChar(signature, pos)) != -1)
      pos = checkTypeSignature(signature, pos); 
    pos = checkChar(')', signature, pos);
    if (getChar(signature, pos) == 'V') {
      pos++;
    } else {
      pos = checkTypeSignature(signature, pos);
    } 
    while (getChar(signature, pos) == '^') {
      pos++;
      if (getChar(signature, pos) == 'L') {
        pos = checkClassTypeSignature(signature, pos);
        continue;
      } 
      pos = checkTypeVariableSignature(signature, pos);
    } 
    if (pos != signature.length())
      throw new IllegalArgumentException(signature + ": error at index " + pos); 
  }
  
  public static void checkFieldSignature(String signature) {
    int pos = checkFieldTypeSignature(signature, 0);
    if (pos != signature.length())
      throw new IllegalArgumentException(signature + ": error at index " + pos); 
  }
  
  static void checkTypeRefAndPath(int typeRef, TypePath typePath) {
    int mask = 0;
    switch (typeRef >>> 24) {
      case 0:
      case 1:
      case 22:
        mask = -65536;
        break;
      case 19:
      case 20:
      case 21:
      case 64:
      case 65:
      case 67:
      case 68:
      case 69:
      case 70:
        mask = -16777216;
        break;
      case 16:
      case 17:
      case 18:
      case 23:
      case 66:
        mask = -256;
        break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
        mask = -16776961;
        break;
      default:
        throw new IllegalArgumentException("Invalid type reference sort 0x" + 
            Integer.toHexString(typeRef >>> 24));
    } 
    if ((typeRef & (mask ^ 0xFFFFFFFF)) != 0)
      throw new IllegalArgumentException("Invalid type reference 0x" + 
          Integer.toHexString(typeRef)); 
    if (typePath != null)
      for (int i = 0; i < typePath.getLength(); i++) {
        int step = typePath.getStep(i);
        if (step != 0 && step != 1 && step != 3 && step != 2)
          throw new IllegalArgumentException("Invalid type path step " + i + " in " + typePath); 
        if (step != 3 && typePath
          .getStepArgument(i) != 0)
          throw new IllegalArgumentException("Invalid type path step argument for step " + i + " in " + typePath); 
      }  
  }
  
  private static int checkFormalTypeParameters(String signature, int pos) {
    pos = checkChar('<', signature, pos);
    pos = checkFormalTypeParameter(signature, pos);
    while (getChar(signature, pos) != '>')
      pos = checkFormalTypeParameter(signature, pos); 
    return pos + 1;
  }
  
  private static int checkFormalTypeParameter(String signature, int pos) {
    pos = checkIdentifier(signature, pos);
    pos = checkChar(':', signature, pos);
    if ("L[T".indexOf(getChar(signature, pos)) != -1)
      pos = checkFieldTypeSignature(signature, pos); 
    while (getChar(signature, pos) == ':')
      pos = checkFieldTypeSignature(signature, pos + 1); 
    return pos;
  }
  
  private static int checkFieldTypeSignature(String signature, int pos) {
    switch (getChar(signature, pos)) {
      case 'L':
        return checkClassTypeSignature(signature, pos);
      case '[':
        return checkTypeSignature(signature, pos + 1);
    } 
    return checkTypeVariableSignature(signature, pos);
  }
  
  private static int checkClassTypeSignature(String signature, int pos) {
    pos = checkChar('L', signature, pos);
    pos = checkIdentifier(signature, pos);
    while (getChar(signature, pos) == '/')
      pos = checkIdentifier(signature, pos + 1); 
    if (getChar(signature, pos) == '<')
      pos = checkTypeArguments(signature, pos); 
    while (getChar(signature, pos) == '.') {
      pos = checkIdentifier(signature, pos + 1);
      if (getChar(signature, pos) == '<')
        pos = checkTypeArguments(signature, pos); 
    } 
    return checkChar(';', signature, pos);
  }
  
  private static int checkTypeArguments(String signature, int pos) {
    pos = checkChar('<', signature, pos);
    pos = checkTypeArgument(signature, pos);
    while (getChar(signature, pos) != '>')
      pos = checkTypeArgument(signature, pos); 
    return pos + 1;
  }
  
  private static int checkTypeArgument(String signature, int pos) {
    char c = getChar(signature, pos);
    if (c == '*')
      return pos + 1; 
    if (c == '+' || c == '-')
      pos++; 
    return checkFieldTypeSignature(signature, pos);
  }
  
  private static int checkTypeVariableSignature(String signature, int pos) {
    pos = checkChar('T', signature, pos);
    pos = checkIdentifier(signature, pos);
    return checkChar(';', signature, pos);
  }
  
  private static int checkTypeSignature(String signature, int pos) {
    switch (getChar(signature, pos)) {
      case 'B':
      case 'C':
      case 'D':
      case 'F':
      case 'I':
      case 'J':
      case 'S':
      case 'Z':
        return pos + 1;
    } 
    return checkFieldTypeSignature(signature, pos);
  }
  
  private static int checkIdentifier(String signature, int pos) {
    if (!Character.isJavaIdentifierStart(getChar(signature, pos)))
      throw new IllegalArgumentException(signature + ": identifier expected at index " + pos); 
    pos++;
    while (Character.isJavaIdentifierPart(getChar(signature, pos)))
      pos++; 
    return pos;
  }
  
  private static int checkChar(char c, String signature, int pos) {
    if (getChar(signature, pos) == c)
      return pos + 1; 
    throw new IllegalArgumentException(signature + ": '" + c + "' expected at index " + pos);
  }
  
  private static char getChar(String signature, int pos) {
    return (pos < signature.length()) ? signature.charAt(pos) : Character.MIN_VALUE;
  }
}
