package QuarantineAPI.config.message.publish.impl;

import java.util.List;

import QuarantineAPI.config.bus.AbstractAsynchronousPubSubMessageBus;
import QuarantineAPI.config.bus.publish.AsynchronousPublishMessageBus;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.publish.MessagePublisher;
import QuarantineAPI.config.message.result.MessagePublicationResult;
import QuarantineAPI.config.message.result.impl.DeadMessagePublicationResult;
import QuarantineAPI.config.message.result.impl.ExperimentalMessagePublicationResult;

public final class ExperimentalMessagePublisher<T> implements MessagePublisher<T> {
  public MessagePublicationResult<T> publish(T topic, AbstractAsynchronousPubSubMessageBus<T> messageBus) {
    List<MessageHandler<T>> messageHandlers = (List<MessageHandler<T>>)messageBus.getSubscriber().subscriberMap().get(topic.getClass());
    return (messageHandlers == null) ? (MessagePublicationResult<T>)new DeadMessagePublicationResult() : (MessagePublicationResult<T>)new ExperimentalMessagePublicationResult((AsynchronousPublishMessageBus)messageBus, messageHandlers, topic);
  }
}
