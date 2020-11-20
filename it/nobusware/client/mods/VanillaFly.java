package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.MoveUtils;
import it.nobusware.client.utils.Timer;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.NumberValue;
import net.minecraft.util.MovementInput;

public class VanillaFly extends Module {
	
	public static Timer timer = new Timer();
	private NumberValue<Float> speed = new NumberValue("Speed", Float.valueOf(1.4F), Float.valueOf(1.0F), Float.valueOf(7.0F), Float.valueOf(0.1F));
	 
	public VanillaFly(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.speed });
	}

	@Handler
	public void eventobellobello(EventUpdate ev) {
		if(this.isAbilitato()) {
			mc.timer.timerSpeed = 1f;
			if (ev.isPre()) {
				float speed = this.speed.getValue().floatValue();
				MovementInput movementInput = mc.thePlayer.movementInput;
				mc.thePlayer.motionY = movementInput.jump ? speed * 0.5F : movementInput.sneak ? -speed * 0.5F : 0.0F;
				MoveUtils.setMotion(speed);
				if (this.timer.delay(700F)) {
					MoveUtils.fallPacket();
					MoveUtils.ascendPacket();
					this.timer.reset();
				}
			}
		}
	}
	
	@Override
	public void Disabilitato() {
		if(!mc.thePlayer.capabilities.isCreativeMode) {
			mc.thePlayer.capabilities.allowFlying = false;
			mc.thePlayer.capabilities.isFlying = false;
			net.minecraft.util.Timer.timerSpeed = 1.0F;
			MoveUtils.setMotion(0.15F);
		    mc.timer.timerSpeed = 1.0F;
		}
	}
}