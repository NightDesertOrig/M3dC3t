package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.util.Bytecode;

public class InjectionNodes extends ArrayList<InjectionNodes.InjectionNode> {
  private static final long serialVersionUID = 1L;
  
  public static class InjectionNode implements Comparable<InjectionNode> {
    private static int nextId = 0;
    
    private final int id;
    
    private final AbstractInsnNode originalTarget;
    
    private AbstractInsnNode currentTarget;
    
    private Map<String, Object> decorations;
    
    public InjectionNode(AbstractInsnNode node) {
      this.currentTarget = this.originalTarget = node;
      this.id = nextId++;
    }
    
    public int getId() {
      return this.id;
    }
    
    public AbstractInsnNode getOriginalTarget() {
      return this.originalTarget;
    }
    
    public AbstractInsnNode getCurrentTarget() {
      return this.currentTarget;
    }
    
    public InjectionNode replace(AbstractInsnNode target) {
      this.currentTarget = target;
      return this;
    }
    
    public InjectionNode remove() {
      this.currentTarget = null;
      return this;
    }
    
    public boolean matches(AbstractInsnNode node) {
      return (this.originalTarget == node || this.currentTarget == node);
    }
    
    public boolean isReplaced() {
      return (this.originalTarget != this.currentTarget);
    }
    
    public boolean isRemoved() {
      return (this.currentTarget == null);
    }
    
    public <V> InjectionNode decorate(String key, V value) {
      if (this.decorations == null)
        this.decorations = new HashMap<String, Object>(); 
      this.decorations.put(key, value);
      return this;
    }
    
    public boolean hasDecoration(String key) {
      return (this.decorations != null && this.decorations.get(key) != null);
    }
    
    public <V> V getDecoration(String key) {
      return (this.decorations == null) ? null : (V)this.decorations.get(key);
    }
    
    public int compareTo(InjectionNode other) {
      return (other == null) ? Integer.MAX_VALUE : (hashCode() - other.hashCode());
    }
    
    public String toString() {
      return String.format("InjectionNode[%s]", new Object[] { Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " ") });
    }
  }
  
  public InjectionNode add(AbstractInsnNode node) {
    InjectionNode injectionNode = get(node);
    if (injectionNode == null) {
      injectionNode = new InjectionNode(node);
      add(injectionNode);
    } 
    return injectionNode;
  }
  
  public InjectionNode get(AbstractInsnNode node) {
    for (InjectionNode injectionNode : this) {
      if (injectionNode.matches(node))
        return injectionNode; 
    } 
    return null;
  }
  
  public boolean contains(AbstractInsnNode node) {
    return (get(node) != null);
  }
  
  public void replace(AbstractInsnNode oldNode, AbstractInsnNode newNode) {
    InjectionNode injectionNode = get(oldNode);
    if (injectionNode != null)
      injectionNode.replace(newNode); 
  }
  
  public void remove(AbstractInsnNode node) {
    InjectionNode injectionNode = get(node);
    if (injectionNode != null)
      injectionNode.remove(); 
  }
}
