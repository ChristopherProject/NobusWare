package QuarantineAPI.config.filter;

@FunctionalInterface
public interface MessageFilter<T> {
  boolean passes(T paramT);
}
