package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.test.Player;
import it.nobusware.client.utils.test.ass;

public class GodMode extends Module {


	public GodMode(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	private int delay;

	public void Abilitato() {
		//ass.diocane("♪ TheFatRat & JJD - Prelude VIP Edit (Minecraft Animation) [Music Video]");
		
	}
	
	public void Disabilitato() {
		//Player.stop();
	}
	
//	delay++;
//	if(delay > -1){
//		delay = 0;
//		event.cancel();
//	}
	
	@Handler
	public void onUpdate(EventUpdate event) {
		if (this.delay <= 159) {
			++this.delay;
		}
		if (this.delay > 159) {
			this.mc.thePlayer.sendChatMessage("/delhome home");
			this.mc.thePlayer.sendChatMessage("/sethome home");
			this.mc.thePlayer.sendChatMessage("/home home");
			this.delay = 0;
		}
	}
}
