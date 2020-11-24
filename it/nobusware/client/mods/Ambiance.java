package it.nobusware.client.mods;

import java.util.function.Consumer;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.EnumValue;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class Ambiance extends Module {
	
	private EnumValue<Mode> mode = new EnumValue("Mode", Mode.DAY);

	public Ambiance(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.mode });
	}

	@Handler
	public Consumer<EventNettyPackets> eventConsumer0 = (event) -> {
		if(this.isAbilitato()) {
			if (event.getPacket() instanceof S03PacketTimeUpdate) {
				event.cancel();
			}
		}
	};

	@Handler
	public Consumer<EventUpdate> eventConsumer1 = (event) -> {
		if(this.isAbilitato()) {
			mc.theWorld.getWorldInfo().setWorldTime(this.mode.getValue() == Mode.DAY ? 0 : 20000);
		}
	};

	public enum Mode {
		DAY,NIGHT
	}
}