package QuarantineAPI.config.message.scan;

import java.util.List;

import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.message.handler.MessageHandler;

@FunctionalInterface
public interface MessageScanner<T> {
  List<MessageHandler<T>> scan(Object paramObject, SubscribeMessageBus paramSubscribeMessageBus);
}
