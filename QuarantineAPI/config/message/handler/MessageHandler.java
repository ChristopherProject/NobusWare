package QuarantineAPI.config.message.handler;

import QuarantineAPI.config.filter.MessageFilter;

public interface MessageHandler<T> {
  void handle(T paramT);
  
  Class<T> getTopic();
  
  MessageFilter<T>[] filters();
  
  default boolean passesFilters(T topic) {
    for (MessageFilter<T> filter : filters()) {
      if (!filter.passes(topic))
        return false; 
    } 
    return true;
  }
}
