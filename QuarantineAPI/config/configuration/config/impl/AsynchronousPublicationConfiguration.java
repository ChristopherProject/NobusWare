package QuarantineAPI.config.configuration.config.impl;

import QuarantineAPI.config.configuration.config.Configuration;

public final class AsynchronousPublicationConfiguration implements Configuration<AsynchronousPublicationConfiguration> {
  private int dispatcherCount;
  
  private boolean suppressDispatcherInterrupt;
  
  public static AsynchronousPublicationConfiguration getDefault() {
    return (new AsynchronousPublicationConfiguration()).provideDefault();
  }
  
  public AsynchronousPublicationConfiguration provideDefault() {
    AsynchronousPublicationConfiguration configuration = new AsynchronousPublicationConfiguration();
    configuration.setDispatcherCount(3);
    configuration.setSuppressDispatcherInterrupt(true);
    return configuration;
  }
  
  public int getDispatcherCount() {
    return this.dispatcherCount;
  }
  
  public void setDispatcherCount(int dispatcherCount) {
    this.dispatcherCount = dispatcherCount;
  }
  
  public boolean isSuppressDispatcherInterrupt() {
    return this.suppressDispatcherInterrupt;
  }
  
  public void setSuppressDispatcherInterrupt(boolean suppressDispatcherInterrupt) {
    this.suppressDispatcherInterrupt = suppressDispatcherInterrupt;
  }
}
