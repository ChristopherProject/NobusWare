package QuarantineAPI.config.message.scan.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.scan.MessageScanner;

public final class MethodAndFieldBasedMessageScanner<T> implements MessageScanner<T> {
  public List<MessageHandler<T>> scan(Object parent, SubscribeMessageBus messageBus) {
    List<MessageHandler<T>> messageHandlers = new ArrayList<>();
    messageHandlers.addAll((Collection)(new MethodBasedMessageScanner()).scan(parent, messageBus));
    messageHandlers.addAll((Collection)(new FieldBasedMessageScanner()).scan(parent, messageBus));
    return messageHandlers;
  }
}
