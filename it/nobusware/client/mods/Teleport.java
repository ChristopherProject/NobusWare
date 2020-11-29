package it.nobusware.client.mods;

import org.lwjgl.input.Mouse;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Teleport extends Module {

	private boolean tp;
	
	public Teleport(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		this.tp = false;
	}

	@Handler
	public void onUpdate(EventUpdate event) {
		MovingObjectPosition ray = rayTrace(500.0D);
		if (ray == null)
			return;
		if (Mouse.isButtonDown(1)) {
			double x_new = ray.getBlockPos().getX() + 0.5D;
			double y_new = (ray.getBlockPos().getY() + 1);
			double z_new = ray.getBlockPos().getZ() + 0.5D;
			for (double distance = this.mc.thePlayer.getDistance(x_new, y_new,
					z_new), d = 0.0D; d < distance; d += 2.0D)
				setPos(this.mc.thePlayer.posX
						+ (x_new - this.mc.thePlayer.func_174811_aO().getFrontOffsetX() - this.mc.thePlayer.posX)
								* d / distance,
						this.mc.thePlayer.posY + (y_new - this.mc.thePlayer.posY) * d / distance,
						this.mc.thePlayer.posZ + (z_new - this.mc.thePlayer.func_174811_aO().getFrontOffsetZ()
								- this.mc.thePlayer.posZ) * d / distance);
			setPos(x_new, y_new, z_new);
			this.mc.renderGlobal.loadRenderers();
		}
	}

	public MovingObjectPosition rayTrace(double blockReachDistance) {
		Vec3 vec3 = this.mc.thePlayer.func_174824_e(1.0F);
		Vec3 vec4 = this.mc.thePlayer.getLookVec();
		Vec3 vec5 = vec3.addVector(vec4.xCoord * blockReachDistance, vec4.yCoord * blockReachDistance, vec4.zCoord * blockReachDistance);
		return this.mc.theWorld.rayTraceBlocks(vec3, vec5, !this.mc.thePlayer.isInWater(), false, false);
	}

	public void setPos(double x, double y, double z) {
		this.mc.thePlayer.sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
		this.mc.thePlayer.setPosition(x, y, z);
	}
}