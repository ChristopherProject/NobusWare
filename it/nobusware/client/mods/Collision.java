package it.nobusware.client.mods;

import java.util.function.Consumer;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.CollisionEvent;
import it.nobusware.client.manager.Module;
import net.minecraft.util.AxisAlignedBB;

public class Collision extends Module{

	public Collision(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public Consumer<CollisionEvent> eventConsumer = (event) -> {
		if (this.isAbilitato() && this.mc.theWorld != null && !mc.thePlayer.isSneaking()) {
			event.setBoundingBox(new AxisAlignedBB(-2, -1, -2, 2, 1, 2).offset(event.getX(), event.getY(), event.getZ()));	
		}
	};
}
