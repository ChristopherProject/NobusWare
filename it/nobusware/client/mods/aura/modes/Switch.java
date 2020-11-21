package it.nobusware.client.mods.aura.modes;

import java.util.List;

import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.utils.CombatUtil;
import it.nobusware.client.utils.RotationUtils;
import it.nobusware.client.utils.RotationUtils1;
import it.nobusware.client.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Switch {

	private static Timer timer = new Timer();
	public static Entity entity;

	public static void doUpdate(final killaura killaura, final EventUpdate e, final Minecraft mc) {
		EntityLivingBase entity = (EntityLivingBase) CombatUtil.getTarget(false, true, killaura.getRange().getValue(), false);
		if (killaura.isAbilitato() && Minecraft.getMinecraft().theWorld != null && entity != null) {
			if (!(entity instanceof EntityPlayerSP)) {
				float[] rots = RotationUtils.getRotations((EntityLivingBase) entity);
				if (mc.thePlayer.ticksExisted % 33 == 0) {
					e.setYaw(rots[0]);
					e.setPitch(rots[1]);
					mc.thePlayer.rotationYawHead = rots[0];
					mc.thePlayer.rotationPitchHead = rots[1];
					mc.thePlayer.renderYawOffset = rots[0];
				}
				if (timer.delay((float) (1000L / (killaura.getCps().getValue()))) && e.isPre()) {
					mc.thePlayer.swingItem();
					if ((getMouseOver(RotationUtils1.serveryaw, RotationUtils1.serverpitch, entity) != null)) {
						 mc.playerController.attackEntity(mc.thePlayer,getMouseOver( RotationUtils1.serveryaw ,  RotationUtils1.serverpitch, entity ));
					}else {
						mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
					}
					timer.reset();
				}
			}
		}
	}
	
	/*
	 * 					
	 */

	public static Entity getMouseOver(final float yaw, final float pitch, final Entity target) {
		Entity pointedEntity = null;
		final float p_78473_1_ = 1.0f;
		final Entity var2 = Minecraft.getMinecraft().func_175606_aa();
		if (var2 != null && Minecraft.getMinecraft().theWorld != null) {
			Minecraft.getMinecraft().mcProfiler.startSection("pick");
			Minecraft.getMinecraft().pointedEntity = null;
			double var3 = Minecraft.getMinecraft().playerController.getBlockReachDistance();
			Minecraft.getMinecraft().objectMouseOver = var2.func_174822_a(var3, p_78473_1_);
			double var4 = var3;
			final Vec3 var5 = var2.func_174824_e(var2.getEyeHeight());
			var3 = target.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
			var4 = target.getDistanceToEntity(Minecraft.getMinecraft().thePlayer);
			if (Minecraft.getMinecraft().objectMouseOver != null) {
				var4 = Minecraft.getMinecraft().objectMouseOver.hitVec.distanceTo(var5);
			}
			final Vec3 var6 = var2.getRotationVec(pitch, yaw);
			final Vec3 var7 = var5.addVector(var6.xCoord * var3, var6.yCoord * var3, var6.zCoord * var3);
			pointedEntity = null;
			Vec3 var8 = null;
			final float var9 = 0.3f;
			final List var10 = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABBExcludingEntity(var2,
					var2.getEntityBoundingBox().addCoord(var6.xCoord * var3, var6.yCoord * var3, var6.zCoord * var3)
							.expand(var9, var9, var9));
			double var11 = var4;
			for (int var12 = 0; var12 < var10.size(); ++var12) {
				final Entity var13 = (Entity) var10.get(var12);
				if (var13.canBeCollidedWith()) {
					final float var14 = var13.getCollisionBorderSize();
					final AxisAlignedBB var15 = var13.getEntityBoundingBox().expand(var14, var14, var14);
					final MovingObjectPosition var16 = var15.calculateIntercept(var5, var7);
					if (var15.isVecInside(var5)) {
						if (0.0 < var11 || var11 == 0.0) {
							pointedEntity = var13;
							var8 = ((var16 == null) ? var5 : var16.hitVec);
							var11 = 0.0;
						}
					} else {
						final double var17;
						if (var16 != null && ((var17 = var5.distanceTo(var16.hitVec)) < var11 || var11 == 0.0)) {
							if (var13 == var2.ridingEntity) {
								if (var11 == 0.0) {
									pointedEntity = var13;
									var8 = var16.hitVec;
								}
							} else {
								pointedEntity = var13;
								var8 = var16.hitVec;
								var11 = var17;
							}
						}
					}
				}
			}
			if (pointedEntity != null && (var11 < var4 || Minecraft.getMinecraft().objectMouseOver == null)) {
				Minecraft.getMinecraft().objectMouseOver = new MovingObjectPosition(pointedEntity, var8);
				if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
					Minecraft.getMinecraft().pointedEntity = pointedEntity;
				}
			}
			Minecraft.getMinecraft().mcProfiler.endSection();
		}
		return pointedEntity;
	}
}