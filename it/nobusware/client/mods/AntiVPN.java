package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.aura.modes.Single;
import net.minecraft.network.handshake.client.C00Handshake;

public class AntiVPN extends Module{

	public AntiVPN(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public void onPacket1(EventPackets e) {
		if(e.getPacket() instanceof C00Handshake)
		{
			if(mc.isSingleplayer())
				return;
			C00Handshake p1 = (C00Handshake) e.getPacket();
			p1.setIp("127.0.0.1");
			p1.setPort(25565);
			p1.setProtocolVersion(47);
			mc.thePlayer.sendQueue.noEventPacket(p1);
			e.cancel();
		}
	}
	
	@Handler
	public void onPacket2(EventNettyPackets e) {
		if(e.getPacket() instanceof C00Handshake)
		{
			if(mc.isSingleplayer())
				return;
			C00Handshake p2 = (C00Handshake) e.getPacket();
			p2.setIp("127.0.0.1");
			p2.setPort(25565);
			p2.setProtocolVersion(47);
			mc.thePlayer.sendQueue.noEventPacket(p2);
			e.cancel();
		}
	}
}
