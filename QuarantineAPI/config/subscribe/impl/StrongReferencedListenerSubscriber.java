package QuarantineAPI.config.subscribe.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.scan.MessageScanner;
import QuarantineAPI.config.subscribe.AbstractListenerSubscriber;

public final class StrongReferencedListenerSubscriber<T> extends AbstractListenerSubscriber<T> {
  private final ConcurrentHashMap<Class<T>, CopyOnWriteArrayList<MessageHandler<T>>> subscriberMap = new ConcurrentHashMap<>();
  
  public void subscribe(Object parent, MessageScanner<T> scanner, SubscribeMessageBus<T> subscribeBus) {
    for (MessageHandler<T> cachedHandler : (Iterable<MessageHandler<T>>)getCachedHandlers(parent, scanner, subscribeBus))
      ((CopyOnWriteArrayList<MessageHandler<T>>)this.subscriberMap.computeIfAbsent(cachedHandler.getTopic(), ignored -> new CopyOnWriteArrayList())).add(cachedHandler); 
  }
  
  public ConcurrentHashMap<Class<T>, CopyOnWriteArrayList<MessageHandler<T>>> subscriberMap() {
    return this.subscriberMap;
  }
}
