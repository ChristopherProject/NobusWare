package QuarantineAPI.config.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import QuarantineAPI.config.bus.publish.AsynchronousPublishMessageBus;
import QuarantineAPI.config.bus.subscribe.SubscribeMessageBus;
import QuarantineAPI.config.configuration.BusConfigurations;
import QuarantineAPI.config.configuration.config.impl.AsynchronousPublicationConfiguration;
import QuarantineAPI.config.configuration.config.impl.BusConfiguration;
import QuarantineAPI.config.configuration.config.impl.ExceptionHandlingConfiguration;
import QuarantineAPI.config.message.result.MessagePublicationResult;

public abstract class AbstractAsynchronousPubSubMessageBus<T> implements AsynchronousPublishMessageBus<T>, SubscribeMessageBus<T> {
  private final List<Thread> dispatchers;
  
  private final BlockingQueue<MessagePublicationResult<T>> resultQueue;
  
  private final BusConfigurations busConfigurations;
  
  private final AsynchronousPublicationConfiguration asynchronousPublicationConfiguration;
  
  private final ExceptionHandlingConfiguration exceptionHandlingConfiguration;
  
  private final BusConfiguration busConfiguration;
  
  private volatile boolean shutdown;
  
  public AbstractAsynchronousPubSubMessageBus(BusConfigurations busConfigurations) {
    this.dispatchers = new ArrayList<>();
    this.resultQueue = new LinkedBlockingQueue<>();
    this.busConfigurations = busConfigurations;
    this.asynchronousPublicationConfiguration = (AsynchronousPublicationConfiguration)busConfigurations.get(AsynchronousPublicationConfiguration.class);
    this.exceptionHandlingConfiguration = (ExceptionHandlingConfiguration)busConfigurations.get(ExceptionHandlingConfiguration.class);
    this.busConfiguration = (BusConfiguration)busConfigurations.get(BusConfiguration.class);
  }
  
  public void setupDispatchers() {
    for (int i = 0; i < this.asynchronousPublicationConfiguration.getDispatcherCount(); i++) {
      Thread thread = new Thread(() -> {
            while (!this.shutdown) {
              try {
                if (!this.resultQueue.isEmpty()) {
                  MessagePublicationResult<T> result = this.resultQueue.take();
                  result.dispatch();
                } 
              } catch (Throwable t) {
                if (!this.asynchronousPublicationConfiguration.isSuppressDispatcherInterrupt() || !(t instanceof InterruptedException))
                  this.exceptionHandlingConfiguration.getExceptionHandler().handleException(t); 
              } 
            } 
          });
      thread.setName(String.format("(%s) Dispatch - %d", new Object[] { getIdentifier(), Integer.valueOf(i) }));
      thread.start();
      this.dispatchers.add(thread);
    } 
  }
  
  public void addMessage(MessagePublicationResult<T> result) {
    this.resultQueue.offer(result);
  }
  
  public void addMessage(MessagePublicationResult<T> result, long timeout, TimeUnit timeUnit) {
    try {
      this.resultQueue.offer(result, timeout, timeUnit);
    } catch (InterruptedException e) {
      this.exceptionHandlingConfiguration.getExceptionHandler().handleException(e);
    } 
  }
 
  public void shutdown(int delay, TimeUnit timeUnit) {
      ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
      scheduledExecutorService.schedule(() -> {
          shutdown = true;
          new Thread(() -> {
              while (!resultQueue.isEmpty());

              for (Thread dispatcher : dispatchers) {
                  dispatcher.interrupt();
              }
          }, String.format("(%s) Shutdown", getIdentifier())).start();
      }, delay, timeUnit);
      scheduledExecutorService.shutdown();
  }
  
  public void shutdown() {
    shutdown(250, TimeUnit.MILLISECONDS);
  }
  
  public void forceShutdown() {
    this.shutdown = true;
    for (Thread dispatcher : this.dispatchers)
      dispatcher.interrupt(); 
  }
  
  public BusConfigurations getConfigurations() {
    return this.busConfigurations;
  }
}
