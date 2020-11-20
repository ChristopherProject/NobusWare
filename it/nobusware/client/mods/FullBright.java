package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventRenderer3D;
import it.nobusware.client.manager.Module;

public class FullBright extends Module {

	public FullBright(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public void onMerda(EventRenderer3D e) {
		if(this.isAbilitato())
		mc.gameSettings.gammaSetting = 50.0F;
	}
}