package QuarantineAPI.config.bus;

import QuarantineAPI.config.configuration.BusConfigurations;

@FunctionalInterface
public interface MessageBus {
  BusConfigurations getConfigurations();
  
  default String getIdentifier() {
    return "LWJEB";
  }
}
