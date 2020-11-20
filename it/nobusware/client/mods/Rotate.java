package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;

public class Rotate extends Module {

	public Rotate(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	private float yaw;
	private boolean doSlow;
	private double speed;

	public void Abilitato() {
		this.speed = 0.3D;
		this.doSlow = false;
		super.Abilitato();
	}

	@Handler
	public void onUpdate(EventUpdate event) {
		if (this.mc.thePlayer.swingProgress > 0.0F)
			return;
		if (event.isPre()) {
			this.yaw -= 22.0F;
			if (this.yaw <= -180.0F)
				this.yaw = 180.0F;
			this.mc.thePlayer.renderYawOffset = this.yaw;
			this.mc.thePlayer.rotationYawHead = this.yaw;
			event.setYaw(this.yaw);
		}
	}
}