package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.MoveEvent;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.MoveUtils;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.NumberValue;

public class Speed extends Module {
	
	private NumberValue<Float> speed = new NumberValue("Speed", Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(19.0F), Float.valueOf(0.1F));

	public Speed(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.speed });
	}
	
	@Handler
	public void eventotick(MoveEvent ev) {
		if(this.isAbilitato()) {
			if (this.mc.thePlayer.isMovingOnGround()) {
				ev.setY(this.mc.thePlayer.motionY = 0.41999998688697815);
				MoveUtils.setMotion((Float)this.speed.getValue().floatValue());
			} else {
				MoveUtils.setMotion(0.772);
			}	
		}
	}
	
	@Override
	public void Disabilitato() {
		net.minecraft.util.Timer.timerSpeed = 1.0F;
	}
}
