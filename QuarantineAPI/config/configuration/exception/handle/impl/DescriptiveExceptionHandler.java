package QuarantineAPI.config.configuration.exception.handle.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.util.Date;

import QuarantineAPI.config.configuration.exception.handle.ExceptionHandler;

public enum DescriptiveExceptionHandler implements ExceptionHandler {
  INSTANCE;
  
  public void handleException(Throwable t) {
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    StringBuilder jvmArguments = new StringBuilder();
    for (String jvmArgument : runtimeMXBean.getInputArguments())
      jvmArguments.append(jvmArgument).append(' '); 
    System.err.println(DateFormat.getInstance().format(new Date()));
    System.err.println("\nAn exception was thrown, " + t.toString());
    System.err.println("Stacktrace:");
    t.printStackTrace();
    System.err.println("\nCurrent java version: " + System.getProperty("java.version"));
    System.err.println("JVM Arguments: " + jvmArguments.toString());
    System.err.println("JVM Information: " + runtimeMXBean.getVmName() + " | " + runtimeMXBean.getVmVendor() + " | " + runtimeMXBean.getVmVersion());
    System.err.println("Memory: " + Runtime.getRuntime().totalMemory() + "/" + Runtime.getRuntime().maxMemory());
  }
}
