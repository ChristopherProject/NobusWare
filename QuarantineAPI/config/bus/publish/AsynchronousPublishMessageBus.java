package QuarantineAPI.config.bus.publish;

import java.util.concurrent.TimeUnit;

import QuarantineAPI.config.message.result.MessagePublicationResult;

public interface AsynchronousPublishMessageBus<T> extends PublishMessageBus<T> {
  void setupDispatchers();
  
  void addMessage(MessagePublicationResult<T> paramMessagePublicationResult);
  
  void addMessage(MessagePublicationResult<T> paramMessagePublicationResult, long paramLong, TimeUnit paramTimeUnit);
  
  void shutdown();
  
  void shutdown(int paramInt, TimeUnit paramTimeUnit);
  
  void forceShutdown();
}
