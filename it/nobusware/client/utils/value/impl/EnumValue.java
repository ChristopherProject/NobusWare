package it.nobusware.client.utils.value.impl;

import it.nobusware.client.utils.value.Value;

public class EnumValue<T extends Enum<T>> extends Value<T> {
  private final T[] constants;
  
  public EnumValue(String name, T value) {
    super(name, value);
    this.constants = extractConstantsFromEnumValue(value);
  }
  
  public T[] extractConstantsFromEnumValue(T value) {
    return (T[])value.getDeclaringClass().getEnumConstants();
  }
  
  public String getFixedValue() {
    return ((Enum)getValue()).toString();
  }
  
  public T[] getConstants() {
    return this.constants;
  }
  
  public Object[] getValues(EnumValue<T> property) {
    return property.getConstants();
  }
  
  public void increment() {
    Enum enum_ = (Enum)getValue();
    T[] arrayOfT;
    int i;
    byte b;
    for (arrayOfT = this.constants, i = arrayOfT.length, b = 0; b < i; ) {
      T newValue, constant = arrayOfT[b];
      if (constant != enum_) {
        b++;
        continue;
      } 
      int ordinal = constant.ordinal();
      if (ordinal == this.constants.length - 1) {
        newValue = this.constants[0];
      } else {
        newValue = this.constants[ordinal + 1];
      } 
      setValue(newValue);
      return;
    } 
  }
  
  public void decrement() {
    Enum enum_ = (Enum)getValue();
    T[] arrayOfT;
    int i;
    byte b;
    for (arrayOfT = this.constants, i = arrayOfT.length, b = 0; b < i; ) {
      T newValue, constant = arrayOfT[b];
      if (constant != enum_) {
        b++;
        continue;
      } 
      int ordinal = constant.ordinal();
      if (ordinal == 0) {
        newValue = this.constants[this.constants.length - 1];
      } else {
        newValue = this.constants[ordinal - 1];
      } 
      setValue(newValue);
      return;
    } 
  }
  
  public void setValue(String string) {
    for (T constant : this.constants) {
      if (constant.name().equalsIgnoreCase(string))
        setValue(constant); 
    } 
  }
}
