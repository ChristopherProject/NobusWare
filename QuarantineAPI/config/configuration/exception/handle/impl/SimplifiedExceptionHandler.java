package QuarantineAPI.config.configuration.exception.handle.impl;

import QuarantineAPI.config.configuration.exception.handle.ExceptionHandler;

public enum SimplifiedExceptionHandler implements ExceptionHandler {
  INSTANCE;
  
  public void handleException(Throwable t) {
    System.err.println(t.toString() + " -> " + t.getStackTrace()[0] + ((t.getMessage() == null) ? "" : (" -> " + t.getMessage())));
  }
}
