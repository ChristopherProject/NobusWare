package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.CollisionEvent;
import it.nobusware.client.events.EventPushOutOfBlocks;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.manager.Module.Category;
import it.nobusware.client.utils.MoveUtils;
import it.nobusware.client.utils.PlayerUtils;

public class Phase extends Module {

	private int reset;

	private double dist;

	public Phase(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		this.dist = 1.0D;
	}

	@Handler
	public void onUpdate(EventUpdate event) {
		this.reset--;
		double xOff = 0.0D;
		double zOff = 0.0D;
		double multi = 2.4D;
		double mx = Math.cos(Math.toRadians((this.mc.thePlayer.rotationYaw + 90.0F)));
		double mz = Math.sin(Math.toRadians((this.mc.thePlayer.rotationYaw + 90.0F)));
		xOff = this.mc.thePlayer.moveForward * 2.6D * mx + this.mc.thePlayer.moveStrafing * 2.6D * mz;
		zOff = this.mc.thePlayer.moveForward * 2.6D * mz + this.mc.thePlayer.moveStrafing * 2.6D * mx;
		if (PlayerUtils.isInsideBlock() && this.mc.thePlayer.isSneaking())
			this.reset = 1;
		if (this.reset > 0)
			this.mc.thePlayer.boundingBox.offsetAndUpdate(xOff, 0.0D, zOff);
	}
	
	@Handler
	public void pushOutOfBlocks(EventPushOutOfBlocks event) {
		event.cancel();
	}

	@Handler
	public boolean onCollision(CollisionEvent event) {
		if ((PlayerUtils.isInsideBlock() && this.mc.gameSettings.keyBindJump.isPressed())
				|| (!PlayerUtils.isInsideBlock() && event.getBoundingBox() != null
						&& (event.getBoundingBox()).maxY > this.mc.thePlayer.boundingBox.minY
						&& this.mc.thePlayer.isSneaking())) {
			event.setBoundingBox(null);
			MoveUtils.setMotion(-0.02f);
		}

		return true;
	}
}
