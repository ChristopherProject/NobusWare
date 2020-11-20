package QuarantineAPI.config.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import QuarantineAPI.config.configuration.config.Configuration;
import QuarantineAPI.config.configuration.config.impl.AsynchronousPublicationConfiguration;
import QuarantineAPI.config.configuration.config.impl.BusConfiguration;
import QuarantineAPI.config.configuration.config.impl.BusPubSubConfiguration;
import QuarantineAPI.config.configuration.config.impl.ExceptionHandlingConfiguration;
import QuarantineAPI.config.configuration.exception.BusConfigurationException;

public final class BusConfigurations {
  private final Map<Class<? extends Configuration<?>>, Configuration<?>> configurationMap;
  
  private BusConfigurations(Builder builder) {
    this.configurationMap = (builder == null) ? new HashMap<>() : builder.configurationMap;
  }
  
  public static BusConfigurations getDefault() {
    BusConfigurations configurations = new BusConfigurations(null);
    configurations.configurationMap.put(AsynchronousPublicationConfiguration.class, AsynchronousPublicationConfiguration.getDefault());
    configurations.configurationMap.put(BusConfiguration.class, BusConfiguration.getDefault());
    configurations.configurationMap.put(ExceptionHandlingConfiguration.class, ExceptionHandlingConfiguration.getDefault());
    configurations.configurationMap.put(BusPubSubConfiguration.class, BusPubSubConfiguration.getDefault());
    return configurations;
  }
  
  public <T extends Configuration<?>> T get(Class<T> configuration) {
    Configuration configuration1 = (Configuration)configuration.cast(this.configurationMap.get(configuration));
    if (configuration1 == null)
      throw new BusConfigurationException("Could not find configuration for \"" + configuration.getName() + "\"."); 
    return (T)configuration1;
  }
  
  public static final class Builder {
    private final Map<Class<? extends Configuration<?>>, Configuration<?>> configurationMap = new HashMap<>();
    
	public Builder setConfiguration(Class<BusPubSubConfiguration> configurationClass,
			Supplier<Configuration<?>> configurationSupplier) {
      this.configurationMap.put(configurationClass, configurationSupplier.get());
      return this;
    }
    
    public BusConfigurations build() {
      BusConfigurations reference = BusConfigurations.getDefault();
      for (Class<? extends Configuration<?>> configuration : (Iterable<Class<? extends Configuration<?>>>)reference.configurationMap.keySet())
        this.configurationMap.computeIfAbsent(configuration, reference.configurationMap::get); 
      return new BusConfigurations(this);
    }

  }
}
