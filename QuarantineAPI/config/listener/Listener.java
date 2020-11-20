package QuarantineAPI.config.listener;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import QuarantineAPI.config.configuration.config.impl.ExceptionHandlingConfiguration;

@FunctionalInterface
public interface Listener {
  static Listener of(Class<?> parent, Method method, Class<?> topic, ExceptionHandlingConfiguration exceptionHandlingConfiguration) {
    ClassNode classNode = new ClassNode();
    classNode.visit(52, 49, "lwjeb/generated/" + parent.getName().replace('.', '/') + "/" + getUniqueMethodName(method), null, "java/lang/Object", new String[] { Listener.class.getName().replace('.', '/') });
    classNode.methods = new ArrayList();
    MethodNode constructorMethodNode = new MethodNode(1, "<init>", "()V", null, null);
    constructorMethodNode.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
    constructorMethodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(183, "java/lang/Object", "<init>", "()V"));
    constructorMethodNode.instructions.add((AbstractInsnNode)new InsnNode(177));
    MethodNode invokeMethodNode = new MethodNode(1, "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)V", null, null);
    invokeMethodNode.instructions.add((AbstractInsnNode)new VarInsnNode(25, 1));
    invokeMethodNode.instructions.add((AbstractInsnNode)new TypeInsnNode(192, parent.getName().replace('.', '/')));
    invokeMethodNode.instructions.add((AbstractInsnNode)new VarInsnNode(25, 2));
    invokeMethodNode.instructions.add((AbstractInsnNode)new TypeInsnNode(192, topic.getName().replace('.', '/')));
    invokeMethodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(182, parent.getName().replace('.', '/'), method.getName(), Type.getMethodDescriptor(method)));
    invokeMethodNode.instructions.add((AbstractInsnNode)new InsnNode(177));
    classNode.methods.add(constructorMethodNode);
    classNode.methods.add(invokeMethodNode);
    ClassWriter classWriter = new ClassWriter(2);
    classNode.accept(classWriter);
    try {
      Class<?> compiledClass = ListenerClassLoader.getInstance().createClass(classNode.name.replace('/', '.'), classWriter.toByteArray());
      return (Listener)compiledClass.newInstance();
    } catch (ReflectiveOperationException e) {
    	   exceptionHandlingConfiguration.getExceptionHandler().handleException(e);
           return method::invoke;
    } 
  }
  
  static String getUniqueMethodName(Method method) {
    StringBuilder parameters = new StringBuilder();
    for (Parameter parameter : method.getParameters())
      parameters.append(parameter.getType().getName().replace('.', '_')); 
    return method.getName() + parameters.toString();
  }
  
  void invoke(Object paramObject1, Object paramObject2) throws ReflectiveOperationException;
}
