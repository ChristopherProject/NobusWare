package it.nobusware.client.mods;

import java.util.function.Consumer;

import QuarantineAPI.Event;
import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.mods.aura.modes.Single;

public class Criticals extends Module {

    public Criticals(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	private double groundSpoofDist = 0.001;
	
	@Handler
	public Consumer<EventUpdate> eventConsumer = event -> {
		if(mc.getNobita().getModManager().Prendi(killaura.class).isAbilitato()) {
		if (groundSpoofDist < 0.0001) {
			groundSpoofDist = 0.001;
		}
		if (mc.thePlayer.isSwingInProgress && mc.thePlayer.isCollidedVertically) {
			event.setY(event.getY() + groundSpoofDist);
			event.setGround(false);
			groundSpoofDist -= 1.0E-11;
		}
	}
 };

	public void Abilitato() {
		groundSpoofDist = 0.001;
	}

	public void Disablitato() {
		groundSpoofDist = 0.001;
	}
}
