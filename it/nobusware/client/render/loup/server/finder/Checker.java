package it.nobusware.client.render.loup.server.finder;

import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Checker {
  private static final AtomicInteger threadNumber = new AtomicInteger(0);
  
  public static final Logger logger = LogManager.getLogger();
  
  public ServerData server;
  
  private boolean done = false;
  
  private boolean failed = false;
  
  public void ping(final String ip, final int port) {
    this.server = new ServerData("", String.valueOf(ip) + ":" + port);
    (new Thread("Server Connector #" + threadNumber.incrementAndGet()) {
        public void run() {
          OldServerPinger pinger = new OldServerPinger();
          try {
            Checker.logger.info("Pinging " + ip + ":" + port + "...");
            pinger.ping(Checker.this.server);
            Thread.sleep(250L);
            Checker.logger.info("Ping successful: " + ip + ":" + port);
          } catch (UnknownHostException e) {
            Checker.logger.info("Unknown host: " + ip + ":" + port);
            Checker.this.failed = true;
          } catch (Exception e2) {
            Checker.logger.info("Ping failed: " + ip + ":" + port);
            Checker.this.failed = true;
          } 
          pinger.clearPendingNetworks();
          Checker.this.done = true;
        }
      }).start();
  }
  
  public boolean isStillPinging() {
    return !this.done;
  }
  
  public boolean isWorking() {
    return !this.failed;
  }
  
  public boolean isOtherVersion() {
    return (this.server.version != 47 && this.server.version != 5 && this.server.version != -1 && this.server.version != 107);
  }
}
