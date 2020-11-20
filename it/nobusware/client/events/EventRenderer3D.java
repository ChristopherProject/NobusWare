package it.nobusware.client.events;

import QuarantineAPI.Event;

public class EventRenderer3D extends Event {
	
	private final float tick;

	public EventRenderer3D(float tick) {
		this.tick = tick;
	}

	public float getRenderTick() {
		return this.tick;
	}
}