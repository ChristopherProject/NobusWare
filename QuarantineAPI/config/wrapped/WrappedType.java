package QuarantineAPI.config.wrapped;

import java.util.Optional;

public class WrappedType {
  private final Object type;
  
  public WrappedType(Object type) {
    this.type = type;
  }
  
  public <T> Optional<T> as(Class<T> type) {
    try {
      return Optional.of(type.cast(this.type));
    } catch (ClassCastException e) {
      return Optional.empty();
    } 
  }
  
  public Object raw() {
    return this.type;
  }
  
  public String toString() {
    return this.type.toString();
  }
}
