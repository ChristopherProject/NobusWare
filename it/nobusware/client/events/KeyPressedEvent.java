package it.nobusware.client.events;

import QuarantineAPI.Event;

public class KeyPressedEvent extends Event{
	
	  private final int eventKey;
	  
	  public KeyPressedEvent(int eventKey) {
	    this.eventKey = eventKey;
	  }
	  
	  public int getEventKey() {
	    return this.eventKey;
	  }

}
