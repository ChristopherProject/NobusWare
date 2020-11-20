package it.nobusware.client.mods;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventRenderer3D;
import it.nobusware.client.manager.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class CazzoDuro extends Module {
	
	public CazzoDuro(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	private int amount;

	private float spin;

	private float cumSize;

	@Handler
	public void onRender(EventRenderer3D event) {
		for (Object o : mc.theWorld.loadedEntityList) {
			if (o instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) o;
				if (player != mc.thePlayer) {
					double n = player.lastTickPosX + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks;
					mc.getRenderManager();
					double x = n - RenderManager.renderPosX;
					double n2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks;
					mc.getRenderManager();
					double y = n2 - RenderManager.renderPosY;
					double n3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks;
					mc.getRenderManager();
					double z = n3 - RenderManager.renderPosZ;
					GL11.glPushMatrix();
					RenderHelper.disableStandardItemLighting();
					esp(player, x, y, z);
					RenderHelper.enableStandardItemLighting();
					GL11.glPopMatrix();
				}
			}
			this.amount++;
			if (this.amount > 25) {
				this.spin++;
				if (this.spin > 50.0F) {
					this.spin = -50.0F;
				} else if (this.spin < -50.0F) {
					this.spin = 50.0F;
				}
				this.amount = 0;
			}
			this.cumSize++;
			if (this.cumSize > 180.0F) {
				this.cumSize = -180.0F;
				continue;
			}
			if (this.cumSize < -180.0F)
				this.cumSize = 180.0F;
		}
	}

	public void esp(EntityPlayer player, double x, double y, double z) {
		GL11.glDisable(2896);
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glEnable(2848);
		GL11.glDepthMask(true);
		GL11.glLineWidth(1.0F);
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(-player.rotationYaw, 0.0F, player.height, 0.0F);
		GL11.glTranslated(-x, -y, -z);
		GL11.glTranslated(x, y + (player.height / 2.0F) - 0.22499999403953552D, z);
		GL11.glColor4f(1.38F, 0.55F, 2.38F, 1.0F);
		GL11.glRotatef((player.isSneaking() ? 35 : 0) + this.spin, 1.0F + this.spin, 0.0F, this.cumSize);
		int lines = 20;
		GL11.glTranslated(0.0D, 0.0D, 0.07500000298023224D);
		Cylinder shaft = new Cylinder();
		shaft.setDrawStyle(100013);
		shaft.draw(0.1F, 0.11F, 0.4F, 25, 20);
		GL11.glColor4f(1.38F, 0.85F, 1.38F, 1.0F);
		GL11.glTranslated(0.0D, 0.0D, -0.12500000298023223D);
		GL11.glTranslated(-0.09000000074505805D, 0.0D, 0.0D);
		Sphere right = new Sphere();
		right.setDrawStyle(100013);
		right.draw(0.14F, 10, 20);
		GL11.glTranslated(0.16000000149011612D, 0.0D, 0.0D);
		Sphere left = new Sphere();
		left.setDrawStyle(100013);
		left.draw(0.14F, 10, 20);
		GL11.glColor4f(1.35F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslated(-0.07000000074505806D, 0.0D, 0.589999952316284D);
		Sphere tip = new Sphere();
		tip.setDrawStyle(100013);
		tip.draw(0.13F, 15, 20);
		GL11.glDepthMask(true);
		GL11.glDisable(2848);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glEnable(3553);
	}
}
