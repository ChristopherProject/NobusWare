package QuarantineAPI.config.bus.subscribe;

import QuarantineAPI.config.bus.MessageBus;
import QuarantineAPI.config.subscribe.ListenerSubscriber;

public interface SubscribeMessageBus<T> extends MessageBus {
  ListenerSubscriber<T> getSubscriber();
}
