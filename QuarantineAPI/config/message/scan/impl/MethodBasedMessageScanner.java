package QuarantineAPI.config.message.scan.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import QuarantineAPI.config.annotation.Filter;
import QuarantineAPI.config.annotation.Handler;
import QuarantineAPI.config.annotation.Wrapped;
import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.configuration.config.impl.ExceptionHandlingConfiguration;
import QuarantineAPI.config.filter.MessageFilter;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.handler.impl.MethodBasedMessageHandler;
import QuarantineAPI.config.message.scan.MessageScanner;

public final class MethodBasedMessageScanner<T> implements MessageScanner<T> {
  public List<MessageHandler<T>> scan(Object parent, SubscribeMessageBus messageBus) {
    List<MessageHandler<T>> handlers = new ArrayList<>();
    ExceptionHandlingConfiguration exceptionHandlingConfiguration = (ExceptionHandlingConfiguration)messageBus.getConfigurations().get(ExceptionHandlingConfiguration.class);
    for (Method method : parent.getClass().getDeclaredMethods()) {
      try {
        if (method.isAnnotationPresent((Class)Handler.class) && method.getParameterCount() == 1) {
          Class<?> type = method.getParameters()[0].getType();
          Filter handlerFilter = method.<Filter>getDeclaredAnnotation(Filter.class);
          Wrapped wrappedType = method.<Wrapped>getDeclaredAnnotation(Wrapped.class);
          MessageFilter[] filter = new MessageFilter[0];
          if (handlerFilter != null) {
            filter = new MessageFilter[(handlerFilter.value()).length];
            for (int i = 0; i < filter.length; i++) {
              Constructor<? extends MessageFilter> constructor = handlerFilter.value()[i].getDeclaredConstructor(new Class[0]);
              constructor.setAccessible(true);
              filter[i] = constructor.newInstance(new Object[0]);
            } 
          } 
          if (wrappedType != null) {
            for (Class<?> acceptedType : wrappedType.value())
              handlers.add(new MethodBasedMessageHandler(parent, acceptedType, method, filter, exceptionHandlingConfiguration, true)); 
          } else {
            handlers.add(new MethodBasedMessageHandler(parent, type, method, filter, exceptionHandlingConfiguration, false));
          } 
        } 
      } catch (ReflectiveOperationException e) {
        exceptionHandlingConfiguration.getExceptionHandler().handleException(e);
      } 
    } 
    return handlers;
  }
}
