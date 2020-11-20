package QuarantineAPI.config.message.publish;

import QuarantineAPI.config.bus.AbstractAsynchronousPubSubMessageBus;
import QuarantineAPI.config.message.result.MessagePublicationResult;

@FunctionalInterface
public interface MessagePublisher<T> {
  MessagePublicationResult<T> publish(T paramT, AbstractAsynchronousPubSubMessageBus<T> paramAbstractAsynchronousPubSubMessageBus);
}
