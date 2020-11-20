package QuarantineAPI.config.subscribe.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.scan.MessageScanner;
import QuarantineAPI.config.subscribe.AbstractListenerSubscriber;

public final class WeakReferencedListenerSubscriber<T> extends AbstractListenerSubscriber<T> {
  private final WeakHashMap<Class<T>, ArrayList<MessageHandler<T>>> subscriberMap = new WeakHashMap<>();
  
  public void subscribe(Object parent, MessageScanner<T> scanner, SubscribeMessageBus<T> subscribeBus) {
    for (MessageHandler<T> cachedHandler : (Iterable<MessageHandler<T>>)getCachedHandlers(parent, scanner, subscribeBus))
      ((ArrayList<MessageHandler<T>>)this.subscriberMap.computeIfAbsent(cachedHandler.getTopic(), ignored -> new ArrayList())).add(cachedHandler); 
  }
  
  public WeakHashMap<Class<T>, ArrayList<MessageHandler<T>>> subscriberMap() {
    return this.subscriberMap;
  }
}
