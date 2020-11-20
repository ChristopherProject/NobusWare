package it.nobusware.client.utils.value.clamper;

public class NumberClamper {
  public static <T extends Number> T clamp(T value, T min, T max) {
    return (((Comparable<T>)value).compareTo(min) < 0) ? min : (
      (((Comparable<T>)value).compareTo(max) > 0) ? max : value);
  }
}
