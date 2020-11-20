package QuarantineAPI.config.bus;

import java.util.HashMap;
import java.util.Map;

import QuarantineAPI.config.configuration.BusConfigurations;
import QuarantineAPI.config.configuration.config.impl.BusPubSubConfiguration;
import QuarantineAPI.config.message.publish.MessagePublisher;
import QuarantineAPI.config.message.result.MessagePublicationResult;
import QuarantineAPI.config.subscribe.ListenerSubscriber;

public final class PubSub<T> extends AbstractAsynchronousPubSubMessageBus<T> {
	
  private final ListenerSubscriber<T> listenerSubscriber;
  
  private final MessagePublisher<T> messagePublisher;
  
  private final BusPubSubConfiguration busPubSubConfiguration;
  
  private final Map<Object, MessagePublicationResult<T>> resultCache;
  
  public PubSub() {
    this(BusConfigurations.getDefault());
  }
  
  public PubSub(BusConfigurations busConfigurations) {
    super(busConfigurations);
    this.busPubSubConfiguration = (BusPubSubConfiguration)busConfigurations.get(BusPubSubConfiguration.class);
    this.listenerSubscriber = this.busPubSubConfiguration.getSubscriber();
    this.messagePublisher = this.busPubSubConfiguration.getPublisher();
    this.resultCache = new HashMap<>();
  }
  
  public MessagePublicationResult<T> post(T topic) {
    MessagePublicationResult<T> result = this.resultCache.get(topic);
    if (result == null) {
      MessagePublicationResult<T> publish = this.messagePublisher.publish(topic, this);
      this.resultCache.put(topic, publish);
      return publish;
    } 
    return result;
  }
  
  public void subscribe(Object parent) {
    invalidateCaches();
    this.listenerSubscriber.subscribe(parent, this.busPubSubConfiguration.getScanner(), this);
  }
  
  public void unsubscribe(Object parent) {
    invalidateCaches();
    this.listenerSubscriber.unsubscribe(parent, this.busPubSubConfiguration.getScanner(), this);
  }
  
  public void invalidateCaches() {
    this.resultCache.clear();
  }
  
  public MessagePublisher<T> getPublisher() {
    return this.messagePublisher;
  }
  
  public ListenerSubscriber<T> getSubscriber() {
    return this.listenerSubscriber;
  }
}
