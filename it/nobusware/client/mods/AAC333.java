package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import net.minecraft.util.MathHelper;

public class AAC333 extends Module {

	private boolean doJump;

	public AAC333(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	public void Abilitato() {
		doJump = true;
	}

	public void Disabilitato() {
		mc.timer.timerSpeed = 1F;
	}

	@Handler
	public void onUpdate(EventUpdate event) {
		if (event.isPre()) {
			if (mc.thePlayer.isMoving()) {
				if (doJump) {
					if (mc.thePlayer.onGround) {
						mc.thePlayer.jump();
						doJump = false;
					}
					return;
				}

				if (mc.thePlayer.onGround) {
					//doStrafe(0.375F);
					mc.thePlayer.jump();
					mc.thePlayer.motionY = 0.405;
					//mc.thePlayer.motionX -= (MathHelper.sin(mc.thePlayer.rotationYawHead) * 0.2F);
					mc.thePlayer.motionZ += (MathHelper.cos(mc.thePlayer.rotationYawHead) * 0.2F);
				} else
					mc.thePlayer.speedInAir = 0.021F;
			} else {
				if (mc.thePlayer.onGround) {
					doJump = true;
				}
				mc.thePlayer.motionX = 0D;
				mc.thePlayer.motionZ = 0D;
			}
		}

//		if (!mc.thePlayer.isMoving() || mc.thePlayer.isInWater() || mc.thePlayer.isOnLadder() || mc.thePlayer.isRiding()
//				|| mc.thePlayer.hurtTime > 0)
//			return;
//
//		if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
//			final float yaw = (float) Math.toRadians(mc.thePlayer.rotationYaw);
//			mc.thePlayer.motionX -= MathHelper.sin(yaw) * 0.232F;
//			mc.thePlayer.motionZ += MathHelper.cos(yaw) * 0.232F;
//			mc.thePlayer.motionY = 0.405F;
//			mc.timer.timerSpeed = 2F;
//		} else {
//			mc.timer.timerSpeed = 1F;
//		}
	}

	public final void doStrafe(double speed) {
		if (!mc.thePlayer.isMoving())
			return;

		final double yaw = mc.thePlayer.rotationYawHead;
		mc.thePlayer.motionX = -Math.sin(yaw) * speed;
		mc.thePlayer.motionZ = Math.cos(yaw) * speed;
	}

	public final double getSpeed() {
		return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
	}


	public final void doStrafe(double speed, double yaw) {
		mc.thePlayer.motionX = -Math.sin(yaw) * speed;
		mc.thePlayer.motionZ = Math.cos(yaw) * speed;
	}

}