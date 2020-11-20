package QuarantineAPI.config.message.result.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import QuarantineAPI.config.bus.publish.AsynchronousPublishMessageBus;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.result.MessagePublicationResult;

public final class StandardMessagePublicationResult<T> implements MessagePublicationResult<T> {
  private final AsynchronousPublishMessageBus<T> publishBus;
  
  private final List<MessageHandler<T>> handlers;
  
  private final T topic;
  
  public StandardMessagePublicationResult(AsynchronousPublishMessageBus<T> publishBus, List<MessageHandler<T>> handlers, T topic) {
    this.publishBus = publishBus;
    this.handlers = handlers;
    this.topic = topic;
  }
  
  public void async() {
    this.publishBus.addMessage(this);
  }
  
  public void async(long timeout, TimeUnit timeUnit) {
    this.publishBus.addMessage(this, timeout, timeUnit);
  }
  
  public void dispatch() {
    for (MessageHandler<T> handler : this.handlers) {
      if (handler.passesFilters(this.topic))
        handler.handle(this.topic); 
    } 
  }
  
  public List<MessageHandler<T>> getHandlers() {
    return this.handlers;
  }
}
