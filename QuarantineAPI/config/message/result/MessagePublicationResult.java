package QuarantineAPI.config.message.result;

import java.util.List;
import java.util.concurrent.TimeUnit;

import QuarantineAPI.config.message.handler.MessageHandler;

public interface MessagePublicationResult<T> {
  void dispatch();
  
  void async();
  
  void async(long paramLong, TimeUnit paramTimeUnit);
  
  List<MessageHandler<T>> getHandlers();
}
