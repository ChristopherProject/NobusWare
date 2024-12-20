package it.nobusware.client.mods;

import java.awt.Color;
import java.util.Random;
import java.util.function.Consumer;

import it.nobusware.client.events.*;
import it.nobusware.client.utils.MoveUtils;
import net.minecraft.util.AxisAlignedBB;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ColorUtilsArray;
import it.nobusware.client.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Teleport extends Module {

	private boolean tp;

	public Teleport(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		this.tp = false;
	}


	@Override
	public void Disabilitato() {
		this.mc.thePlayer.isCollided = false;
		this.mc.thePlayer.isCollidedVertically = false;
		this.mc.thePlayer.isCollidedHorizontally = false;
	}

	@Handler
	public void onUpdate(EventUpdate event) {
		if (this.isAbilitato()) {
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
							+ (x_new - this.mc.thePlayer.func_174811_aO().getFrontOffsetX() - this.mc.thePlayer.posX) * d
									/ distance,
							this.mc.thePlayer.posY + (y_new - this.mc.thePlayer.posY) * d / distance,
							this.mc.thePlayer.posZ + (z_new - this.mc.thePlayer.func_174811_aO().getFrontOffsetZ()
									- this.mc.thePlayer.posZ) * d / distance);
				setPos(x_new, y_new, z_new);
				this.mc.renderGlobal.loadRenderers();
			}
		}
		
	}

	@Handler
	public void EventRender(EventRenderer3D e) {
		if (this.isAbilitato()) {
			MovingObjectPosition ray = rayTrace(500.0D);
			if (ray == null)
				return;
			double x = ray.getBlockPos().getX() + 0.5D;
			double y = (ray.getBlockPos().getY() + 1);
			double z = ray.getBlockPos().getZ() + 0.5D;
			drawBlock(new Vec3(x, y, z));	
		}

	}

	private void drawBlock(Vec3 vec) {
		double x = vec.getX() - RenderManager.renderPosX;
		double y = vec.getY() - RenderManager.renderPosY;
		double z = vec.getZ() - RenderManager.renderPosZ;
		double width = 0.3;
		double height = mc.thePlayer.getEyeHeight();
		RenderUtils.pre3D();
		GL11.glLoadIdentity();
		mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
		int[] colors = { ColorUtilsArray.getColor(Color.black), ColorUtilsArray.getColor(Color.white) };
		for (int i = 0; i < 2; i++) {
			RenderUtils.glColor(colors[i]);
			GL11.glLineWidth(3 - i * 2);
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y + height, z - width);
			GL11.glVertex3d(x + width, y + height, z - width);
			GL11.glVertex3d(x + width, y, z - width);
			GL11.glVertex3d(x - width, y, z - width);
			GL11.glVertex3d(x - width, y, z + width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x + width, y, z + width);
			GL11.glVertex3d(x + width, y + height, z + width);
			GL11.glVertex3d(x - width, y + height, z + width);
			GL11.glVertex3d(x - width, y, z + width);
			GL11.glVertex3d(x + width, y, z + width);
			GL11.glVertex3d(x + width, y, z - width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x + width, y + height, z + width);
			GL11.glVertex3d(x + width, y + height, z - width);
			GL11.glEnd();
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3d(x - width, y + height, z + width);
			GL11.glVertex3d(x - width, y + height, z - width);
			GL11.glEnd();
		}

		RenderUtils.post3D();
	}

	public MovingObjectPosition rayTrace(double blockReachDistance) {
		Vec3 vec3 = this.mc.thePlayer.func_174824_e(1.0F);
		Vec3 vec4 = this.mc.thePlayer.getLookVec();
		Vec3 vec5 = vec3.addVector(vec4.xCoord * blockReachDistance, vec4.yCoord * blockReachDistance,
				vec4.zCoord * blockReachDistance);
		return this.mc.theWorld.rayTraceBlocks(vec3, vec5, !this.mc.thePlayer.isInWater(), false, false);
	}

	@Handler
	public Consumer<CollisionEvent> eventConsumer = (event) -> {
		if (this.isAbilitato() && this.mc.theWorld != null
				&& !mc.thePlayer.isSneaking()) {
			//event.setBoundingBox(new AxisAlignedBB(-2, -1, -2, 2, 0, 2).offset(event.getX(), event.getY(), event.getZ()));
		}
	};

	public void setPos(double x, double y, double z) {
		// mette il player in un veicolo
		mc.thePlayer.sendQueue.noEventPacket(new C0CPacketInput(1.0F, 2.05F, true, true));
		// tippa il player
		this.mc.thePlayer.sendQueue
				.addToSendQueue((Packet) new C03PacketPlayer.C04PacketPlayerPosition(x, y * (double)0.98f, z, false));
		this.mc.thePlayer.setPosition(x, y, z);
		this.mc.thePlayer.isCollided = false;
		this.mc.thePlayer.isCollidedVertically = false;
		this.mc.thePlayer.isCollidedHorizontally = false;
	}

	public void damagePlayer(int damage) {
		if (damage < 1)
			damage = 1;
		if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
			damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

		double offset = 0.0625;
		if (mc.thePlayer != null && mc.getNetHandler() != null && mc.thePlayer.onGround) {
			for (int i = 0; i <= ((3 + damage) / offset); i++) {
				mc.getNetHandler()
						.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
								mc.thePlayer.posY + offset + (double) (new Random()).nextFloat() * 1.0E-6D,
								mc.thePlayer.posZ, false));
				mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
			}
		}
	}

	public static Block getBlock(final int x, final int y, final int z) {
		return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
	}

	public static Block getBlock(final BlockPos pos) {
		return mc.theWorld.getBlockState(pos).getBlock();
	}
}