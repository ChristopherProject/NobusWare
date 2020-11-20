package QuarantineAPI.config.message.handler.impl;

import java.lang.reflect.Method;

import QuarantineAPI.config.configuration.config.impl.ExceptionHandlingConfiguration;
import QuarantineAPI.config.filter.MessageFilter;
import QuarantineAPI.config.listener.Listener;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.wrapped.WrappedType;

public final class MethodBasedMessageHandler<T> implements MessageHandler<T> {
  private final Object parent;
  
  private final Class<T> topic;
  
  private final Listener listener;
  
  private final MessageFilter<T>[] filters;
  
  private final ExceptionHandlingConfiguration exceptionHandlingConfiguration;
  
  private final boolean wrapped;
  
  public MethodBasedMessageHandler(Object parent, Class<T> topic, Method method, MessageFilter<T>[] filters, ExceptionHandlingConfiguration exceptionHandlingConfiguration, boolean wrapped) {
    this.parent = parent;
    this.topic = topic;
    this.listener = Listener.of(parent.getClass(), method, wrapped ? WrappedType.class : topic, exceptionHandlingConfiguration);
    this.filters = filters;
    this.exceptionHandlingConfiguration = exceptionHandlingConfiguration;
    this.wrapped = wrapped;
  }
  
  public void handle(T topic) {
    try {
      this.listener.invoke(this.parent, this.wrapped ? new WrappedType(topic) : topic);
    } catch (ReflectiveOperationException e) {
      this.exceptionHandlingConfiguration.getExceptionHandler().handleException(e);
    } 
  }
  
  public Class<T> getTopic() {
    return this.topic;
  }
  
  public MessageFilter<T>[] filters() {
    return this.filters;
  }
}
