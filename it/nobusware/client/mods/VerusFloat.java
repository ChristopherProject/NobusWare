package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.MoveEvent;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.MoveUtils;
import net.minecraft.util.Timer;

public class VerusFloat extends Module {

	public VerusFloat(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public void onMotion(MoveEvent e) {
		if (this.isAbilitato()) {
			mc.thePlayer.setBoundingBox(mc.thePlayer.boundingBox.offset(0, +0.000999998688697815, 0));
			MoveUtils.setMotion(3.232);
			if(!mc.thePlayer.onGround)
			MoveUtils.setMotion(0.032);
		}
	}
}