package QuarantineAPI.config.configuration.config;

@FunctionalInterface
public interface Configuration<T extends Configuration> {
  T provideDefault();
}
