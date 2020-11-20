package it.nobusware.client.utils.factory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import it.nobusware.client.utils.factory.exception.FactoryException;

public final class ClassFactory {
  public static <K> K create(Class<K> objectClass, Object... arguments) {
    Constructor<K> constructor;
    try {
      List<Class<?>> list = new ArrayList<>();
      for (Object argument : arguments) {
        Class<?> aClass = argument.getClass();
        list.add(aClass);
      } 
      constructor = objectClass.getDeclaredConstructor();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      throw new FactoryException("Constructor not found! - Cannot create class.");
    } 
    if (!constructor.isAccessible())
      constructor.setAccessible(true); 
    try {
      return constructor.newInstance(arguments);
    } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
      e.printStackTrace();
      throw new FactoryException("An error occurred while instantiating the class!");
    } 
  }
}
