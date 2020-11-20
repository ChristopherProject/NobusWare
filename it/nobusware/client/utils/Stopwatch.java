package it.nobusware.client.utils;

public final class Stopwatch {
  private long ms = getCurrentMS();
  
  private long getCurrentMS() {
    return System.currentTimeMillis();
  }
  
  public final long getElapsedTime() {
    return getCurrentMS() - this.ms;
  }
  
  public final boolean elapsed(long milliseconds) {
    return (getCurrentMS() - this.ms > milliseconds);
  }
  
  public final void reset() {
    this.ms = getCurrentMS();
  }
}
