package it.nobusware.client.utils.value.parse;

import com.google.common.primitives.Primitives;

public final class NumberCaster {
  public static <T extends Number, V extends Number> T cast(Class<T> numberClass, V value) {
    Object casted;
    numberClass = Primitives.wrap(numberClass);
    if (numberClass == Byte.class) {
      casted = Byte.valueOf(value.byteValue());
    } else if (numberClass == Short.class) {
      casted = Short.valueOf(value.shortValue());
    } else if (numberClass == Integer.class) {
      casted = Integer.valueOf(value.intValue());
    } else if (numberClass == Long.class) {
      casted = Long.valueOf(value.longValue());
    } else if (numberClass == Float.class) {
      casted = Float.valueOf(value.floatValue());
    } else {
      if (numberClass != Double.class)
        throw new ClassCastException(String.format("%s cannot be casted to %s", new Object[] { value.getClass(), numberClass })); 
      casted = Double.valueOf(value.doubleValue());
    } 
    return (T)casted;
  }
}
