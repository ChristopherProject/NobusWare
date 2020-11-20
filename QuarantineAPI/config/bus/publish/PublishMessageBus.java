package QuarantineAPI.config.bus.publish;

import QuarantineAPI.config.bus.MessageBus;
import QuarantineAPI.config.message.publish.MessagePublisher;

public interface PublishMessageBus<T> extends MessageBus {
  MessagePublisher<T> getPublisher();
}
