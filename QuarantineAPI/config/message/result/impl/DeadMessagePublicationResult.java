package QuarantineAPI.config.message.result.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import QuarantineAPI.config.message.handler.MessageHandler;
import QuarantineAPI.config.message.result.MessagePublicationResult;

public final class DeadMessagePublicationResult<T> implements MessagePublicationResult<T> {
  public void async() {}
  
  public void async(long timeout, TimeUnit timeUnit) {}
  
  public void dispatch() {}
  
  public List<MessageHandler<T>> getHandlers() {
    return Collections.emptyList();
  }
}
