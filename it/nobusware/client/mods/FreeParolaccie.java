package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventChatSend;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class FreeParolaccie extends Module {

	public FreeParolaccie(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}
	
	@Handler
	public void OnChatBypassDiocane(EventChatSend e) {
		String b = ChatUtils.sendBypassedString(e.getMessage());
		e.setMessage(b);
	}
}