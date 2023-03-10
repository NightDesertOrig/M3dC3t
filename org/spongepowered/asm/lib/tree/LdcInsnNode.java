package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class LdcInsnNode extends AbstractInsnNode {
  public Object cst;
  
  public LdcInsnNode(Object cst) {
    super(18);
    this.cst = cst;
  }
  
  public int getType() {
    return 9;
  }
  
  public void accept(MethodVisitor mv) {
    mv.visitLdcInsn(this.cst);
    acceptAnnotations(mv);
  }
  
  public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
    return (new LdcInsnNode(this.cst)).cloneAnnotations(this);
  }
}
