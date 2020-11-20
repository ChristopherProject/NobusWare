package QuarantineAPI.config.subscribe;

import java.util.List;

import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.scan.MessageScanner;

public interface ListenerSubscriber<T> {
  void subscribe(Object paramObject, MessageScanner<T> paramMessageScanner, SubscribeMessageBus<T> paramSubscribeMessageBus);
  
  void unsubscribe(Object paramObject, MessageScanner<T> paramMessageScanner, SubscribeMessageBus<T> paramSubscribeMessageBus);
  
  <S extends List<MessageHandler<T>>, U extends java.util.Map<Class<T>, S>> U subscriberMap();
  
  List<MessageHandler<T>> getCachedHandlers(Object paramObject, MessageScanner<T> paramMessageScanner, SubscribeMessageBus<T> paramSubscribeMessageBus);
}
