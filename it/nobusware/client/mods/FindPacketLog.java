package it.nobusware.client.mods;

import java.util.ArrayList;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventChatReceive;
import it.nobusware.client.events.EventChatSend;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.Timer;

public class FindPacketLog extends Module {

	private Timer tr = new Timer();
	private boolean isPacketlogged = false;
	public static final ArrayList<String> packetlog = new ArrayList<String>();
	
	public FindPacketLog(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		setDangerous(true);
	}
	
	public void Abilitato() {
		if(!this.isAbilitato() || mc.isSingleplayer()) {
			return;
		}
		ChatUtils.print("Scanning Server...");
		if (tr.delay(360L)) {
			mc.thePlayer.sendChatMessage("/packet");
		} 
		if (tr.delay(940L)) {
			mc.thePlayer.sendChatMessage("/lpx");
		}
	}
	
	@Handler
	public void onUpdate(EventUpdate e) {
		if(this.isAbilitato() && !mc.isSingleplayer()) {
			if(isPacketlogged) {
				packetlog.add(mc.getCurrentServerData().serverIP);
			}
		}
	}
	
	@Handler
	public void onChatting(EventChatReceive e) {
		if(!this.isAbilitato() || mc.isSingleplayer()) {
			return;
		}else {
			if(e.getMessage().contains("Link")) {
				e.cancel();
			}else if(e.getMessage().contains("Running")) {
				ChatUtils.print("this server use §6LPX");
				isPacketlogged = true;
				e.cancel();
			}
			else if(e.getMessage().contains("protocol.admin")) {
				ChatUtils.print("this server use §aPacketLogOG");
				isPacketlogged = true;
				e.cancel();
			}	
		}
	}
	
	public void Disabilitato() {
		this.isPacketlogged = false;
		tr.reset();
	}
}