package it.nobusware.client.events;

import QuarantineAPI.Event;

public class MoveEvent extends Event {

	private double x;

	private double y;

	private double z;

	public MoveEvent(double x, double y2, double z) {
		this.x = x;
		this.y = y2;
		this.z = z;
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y2) {
		this.y = y2;
	}

	public double getZ() {
		return this.z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setXYZ(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
