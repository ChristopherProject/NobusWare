package it.nobusware.client.events;

import QuarantineAPI.Event;

public class EventUpdate extends Event {
	
	private double x;
	private double y;
	private double z;
	public float yaw;
	public float pitch;
	private boolean ground;

	public EventUpdate(double x, double y, double z, float yaw, float pitch, boolean ground) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.pre = true;
    }

	public EventUpdate() {
        this.pre = false;
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

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return this.z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public float getYaw() {
		return this.yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public boolean getGroundState() {
		return this.ground;
	}

	public void setGround(boolean ground) {
		this.ground = ground;
	}

	public boolean isPre() {
		return this.pre;
	}

	public boolean isPost() {
		return !this.pre;
	}
}