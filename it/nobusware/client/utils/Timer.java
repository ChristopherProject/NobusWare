package it.nobusware.client.utils;

public class Timer {
	
	  private long prevMS = 0L;
	  
	  public Timer() {
	    reset();
	  }
	  
	  public boolean delay(float milliSec) {
	    return ((float)(getTime() - this.prevMS) >= milliSec);
	  }
	  
	  public void reset() {
	    this.prevMS = getTime();
	  }
	  
	  public long getTime() {
	    return System.nanoTime() / 1000000L;
	  }
	  
	  public long getDifference() {
	    return getTime() - this.prevMS;
	  }
	  
	  public void setDifference(long difference) {
	    this.prevMS = getTime() - difference;
	  }
	  public boolean hasTimeElapsed(long time, boolean reset) {
	        if (this.getTime() >= time) {
	            if (reset) {
	                this.reset();
	            }
	            return true;
	        }
	        return false;
	    }
	  
	  public boolean aSecondi(float second) {
		    return delay(second * 1000);
		  }

	  public boolean hasPassed(double milli) {
		    return ((getTime() - this.prevMS) >= milli);
		  }
	}
