package QuarantineAPI.config.configuration.config.impl;

import QuarantineAPI.config.configuration.config.Configuration;

public final class BusConfiguration implements Configuration<BusConfiguration> {
  private String identifier;
  
  public static BusConfiguration getDefault() {
    return (new BusConfiguration()).provideDefault();
  }
  
  public BusConfiguration provideDefault() {
    BusConfiguration configuration = new BusConfiguration();
    configuration.setIdentifier("LWJEB");
    return configuration;
  }
  
  public String getIdentifier() {
    return this.identifier;
  }
  
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }
}
