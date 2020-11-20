package QuarantineAPI.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import QuarantineAPI.config.filter.MessageFilter;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Filter {
  Class<? extends MessageFilter>[] value();
}
