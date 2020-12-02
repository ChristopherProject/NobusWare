package it.nobusware.client.mods;

import java.beans.EventSetDescriptor;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.manager.Module;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotation extends Module{

	public NoRotation(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}
	
	@Handler
	public void PacketsOut(EventNettyPackets e) {
		if(this.isAbilitato()) {
			if (e.getPacket() instanceof S08PacketPlayerPosLook && mc.theWorld != null) {
				//e.cancel();
			}
		}
	}
}
