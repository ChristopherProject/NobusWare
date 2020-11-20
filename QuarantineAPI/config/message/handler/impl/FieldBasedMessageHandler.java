package QuarantineAPI.config.message.handler.impl;

import java.util.function.Consumer;

import QuarantineAPI.config.filter.MessageFilter;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.wrapped.WrappedType;

public final class FieldBasedMessageHandler<T> implements MessageHandler<T> {
  private final Class<T> topic;
  
  private final MessageFilter<T>[] filters;
  
  private final Consumer listenerConsumer;
  
  private final boolean wrapped;
  
  public FieldBasedMessageHandler(Class<T> topic, MessageFilter<T>[] filters, Consumer listenerConsumer, boolean wrapped) {
    this.topic = topic;
    this.filters = filters;
    this.listenerConsumer = listenerConsumer;
    this.wrapped = wrapped;
  }
  
  public void handle(T topic) {
    this.listenerConsumer.accept(this.wrapped ? new WrappedType(topic) : topic);
  }
  
  public Class<T> getTopic() {
    return this.topic;
  }
  
  public MessageFilter<T>[] filters() {
    return this.filters;
  }
}
