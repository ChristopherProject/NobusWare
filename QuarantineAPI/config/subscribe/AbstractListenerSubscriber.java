package QuarantineAPI.config.subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.scan.MessageScanner;

public abstract class AbstractListenerSubscriber<T> implements ListenerSubscriber<T> {
  private final Map<Object, List<MessageHandler<T>>> cacheMap = new HashMap<>();
  
  public void unsubscribe(Object parent, MessageScanner<T> scanner, SubscribeMessageBus<T> subscribeBus) {
    List<MessageHandler<T>> cached = getCachedHandlers(parent, scanner, subscribeBus);
    for (MessageHandler<T> messageHandler : cached) {
      List<MessageHandler<T>> messageHandlers = (List<MessageHandler<T>>)subscriberMap().get(messageHandler.getTopic());
      if (messageHandlers != null)
        messageHandlers.remove(messageHandler); 
    } 
  }
  
  public List<MessageHandler<T>> getCachedHandlers(Object parent, MessageScanner<T> scanner, SubscribeMessageBus<T> subscribeBus) {
    return this.cacheMap.computeIfAbsent(parent, ignored -> scanner.scan(parent, subscribeBus));
  }
}
