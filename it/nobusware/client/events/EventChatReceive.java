package it.nobusware.client.events;

import QuarantineAPI.Event;

public final class EventChatReceive extends Event {
	  private String mess;
	  
	  public EventChatReceive(String message) {
	    this.mess = message;
	  }
	  
	  public final String getMessage() {
	    return this.mess;
	  }
	  
	  public final void setMessage(String message) {
	    this.mess = message;
	  }
	}

