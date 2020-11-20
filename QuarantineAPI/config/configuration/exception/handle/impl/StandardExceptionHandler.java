package QuarantineAPI.config.configuration.exception.handle.impl;

import QuarantineAPI.config.configuration.exception.handle.ExceptionHandler;

public enum StandardExceptionHandler implements ExceptionHandler {
  INSTANCE;
  
  public void handleException(Throwable t) {
    t.printStackTrace();
  }
}
