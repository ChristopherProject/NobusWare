package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.NumberValue;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class PingSpoof extends Module{
	
	private NumberValue<Integer> spoof = new NumberValue("Value", Integer.valueOf(200), Integer.valueOf(1), Integer.valueOf(700), Integer.valueOf(1));
	
	public PingSpoof(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.spoof });
	}
	
	@Handler
	private void onUpdate(final EventPackets e) {
		if(this.isAbilitato() && !mc.isSingleplayer()) {
			if (e.getPacket() instanceof C00PacketKeepAlive) {
				C00PacketKeepAlive packet = (C00PacketKeepAlive) e.getPacket();
				packet.setKey(this.spoof.getValue().intValue());

			}
		}
	}
}