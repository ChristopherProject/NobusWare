package QuarantineAPI.config.message.scan.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import QuarantineAPI.config.annotation.Filter;
import QuarantineAPI.config.annotation.Handler;
import QuarantineAPI.config.annotation.Wrapped;
import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.configuration.config.impl.ExceptionHandlingConfiguration;
import QuarantineAPI.config.filter.MessageFilter;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.handler.impl.FieldBasedMessageHandler;
import QuarantineAPI.config.message.scan.MessageScanner;

public final class FieldBasedMessageScanner<T> implements MessageScanner<T> {
  public List<MessageHandler<T>> scan(Object parent, SubscribeMessageBus messageBus) {
    List<MessageHandler<T>> messageHandlers = new ArrayList<>();
    ExceptionHandlingConfiguration exceptionHandlingConfiguration = (ExceptionHandlingConfiguration)messageBus.getConfigurations().get(ExceptionHandlingConfiguration.class);
    for (Field field : parent.getClass().getDeclaredFields()) {
      try {
        if (Consumer.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent((Class)Handler.class)) {
          Consumer listenerConsumer = (Consumer)field.get(parent);
          Class<?> type = (Class)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
          Filter handlerFilter = field.<Filter>getDeclaredAnnotation(Filter.class);
          Wrapped wrappedType = field.<Wrapped>getDeclaredAnnotation(Wrapped.class);
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
              messageHandlers.add(new FieldBasedMessageHandler(acceptedType, filter, listenerConsumer, true)); 
          } else {
            messageHandlers.add(new FieldBasedMessageHandler(type, filter, listenerConsumer, false));
          } 
        } 
      } catch (ReflectiveOperationException e) {
        exceptionHandlingConfiguration.getExceptionHandler().handleException(e);
      } 
    } 
    return messageHandlers;
  }
}
