package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventTick;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.BungeeChannelUtil;
import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.ServerOnline;

public class EverKick extends Module{

	public EverKick(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}
	
	@Override
	public void Abilitato() {
		ChatUtils.print("Sended Packet.");
	}
	
	@Handler
	public void onTicked(EventTick ev) {
		if(this.isAbilitato()) {
			for(int i = 0; i < ServerOnline.getAllPlayers().size(); i++) {
				BungeeChannelUtil.SkinRestorerGUI(ServerOnline.getAllPlayers().get(i).toString());
			}
		}
	}
}