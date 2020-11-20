package QuarantineAPI.config.configuration.config.impl;

import QuarantineAPI.config.configuration.config.Configuration;
import QuarantineAPI.config.configuration.exception.handle.ExceptionHandler;
import QuarantineAPI.config.configuration.exception.handle.impl.StandardExceptionHandler;

public final class ExceptionHandlingConfiguration implements Configuration<ExceptionHandlingConfiguration> {
  private ExceptionHandler exceptionHandler;
  
  public static ExceptionHandlingConfiguration getDefault() {
    return (new ExceptionHandlingConfiguration()).provideDefault();
  }
  
  public ExceptionHandlingConfiguration provideDefault() {
    ExceptionHandlingConfiguration configuration = new ExceptionHandlingConfiguration();
    configuration.setExceptionHandler((ExceptionHandler)StandardExceptionHandler.INSTANCE);
    return configuration;
  }
  
  public ExceptionHandler getExceptionHandler() {
    return this.exceptionHandler;
  }
  
  public void setExceptionHandler(ExceptionHandler exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
  }
}
