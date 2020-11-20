package QuarantineAPI.config.configuration.config.impl;

import QuarantineAPI.config.configuration.config.Configuration;
import QuarantineAPI.config.message.publish.MessagePublisher;
import QuarantineAPI.config.message.publish.impl.StandardMessagePublisher;
import QuarantineAPI.config.message.scan.MessageScanner;
import QuarantineAPI.config.message.scan.impl.MethodBasedMessageScanner;
import QuarantineAPI.config.subscribe.ListenerSubscriber;
import QuarantineAPI.config.subscribe.impl.WeakReferencedListenerSubscriber;

public final class BusPubSubConfiguration implements Configuration<BusPubSubConfiguration> {
  private ListenerSubscriber subscriber;
  
  private MessagePublisher publisher;
  
  private MessageScanner scanner;
  
  public static BusPubSubConfiguration getDefault() {
    return (new BusPubSubConfiguration()).provideDefault();
  }
  
  public BusPubSubConfiguration provideDefault() {
    BusPubSubConfiguration configuration = new BusPubSubConfiguration();
    configuration.setSubscriber((ListenerSubscriber<?>)new WeakReferencedListenerSubscriber());
    configuration.setPublisher((MessagePublisher<?>)new StandardMessagePublisher());
    configuration.setScanner((MessageScanner<?>)new MethodBasedMessageScanner());
    return configuration;
  }
  
  public <T> ListenerSubscriber<T> getSubscriber() {
    return this.subscriber;
  }
  
  public <T> void setSubscriber(ListenerSubscriber<T> subscriber) {
    this.subscriber = subscriber;
  }
  
  public <T> MessagePublisher<T> getPublisher() {
    return this.publisher;
  }
  
  public <T> void setPublisher(MessagePublisher<T> publisher) {
    this.publisher = publisher;
  }
  
  public <T> MessageScanner<T> getScanner() {
    return this.scanner;
  }
  
  public <T> void setScanner(MessageScanner<T> scanner) {
    this.scanner = scanner;
  }
}
