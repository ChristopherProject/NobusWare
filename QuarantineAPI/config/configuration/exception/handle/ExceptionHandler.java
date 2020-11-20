package QuarantineAPI.config.configuration.exception.handle;

@FunctionalInterface
public interface ExceptionHandler {
  void handleException(Throwable paramThrowable);
}
