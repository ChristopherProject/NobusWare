package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;

public class NoFall extends Module{

	public NoFall(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}
	
	@Handler
	public void onMotion(EventUpdate e) {
	 	if (mc.thePlayer.fallDistance > 2.5F) {
    		e.setGround(true);
    	}
	}

}
