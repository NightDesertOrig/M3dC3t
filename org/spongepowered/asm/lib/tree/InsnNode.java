package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.MethodVisitor;

public class InsnNode extends AbstractInsnNode {
  public InsnNode(int opcode) {
    super(opcode);
  }
  
  public int getType() {
    return 0;
  }
  
  public void accept(MethodVisitor mv) {
    mv.visitInsn(this.opcode);
    acceptAnnotations(mv);
  }
  
  public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
    return (new InsnNode(this.opcode)).cloneAnnotations(this);
  }
}
