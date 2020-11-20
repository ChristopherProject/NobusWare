package QuarantineAPI.config.listener;

final class ListenerClassLoader extends ClassLoader {
  private static final ListenerClassLoader INSTANCE = new ListenerClassLoader();
  
  Class<?> createClass(String className, byte[] bytecode) {
    try {
      return defineClass(className, bytecode, 0, bytecode.length);
    } catch (LinkageError e) {
      return findLoadedClass(className);
    } 
  }
  
  static ListenerClassLoader getInstance() {
    return INSTANCE;
  }
}
