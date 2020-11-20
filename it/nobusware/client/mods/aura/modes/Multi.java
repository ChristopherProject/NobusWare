package it.nobusware.client.mods.aura.modes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.utils.CombatUtil;
import it.nobusware.client.utils.RotationUtils;
import it.nobusware.client.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;

public class Multi {
	
	private static Timer timer = new Timer();
	public static List<EntityLivingBase> targets = new ArrayList<>();
	private static int index = 0;
	private static Timer t = new Timer();
	private static final Random RANDOM_NUMBER_GENERATOR = new Random();
	private static int targetIndex;
	public static boolean buttpee;

	public static boolean chance(int percent) {
		return RANDOM_NUMBER_GENERATOR.nextInt(100) <= percent;
	}

	public static void doUpdate(final killaura killaura, final EventUpdate e, final Minecraft mc) {
		double range = 4.969D;
		final int blockPercentage = 1;
		buttpee = false;
		final List<EntityLivingBase> targets = CombatUtil.getTargets(100, false, true, range, true);
		final boolean autoBlock = killaura.isBlocking;
		final long clickSpeed = (long) 13.0D;
		buttpee = chance(blockPercentage) && mc.thePlayer.getHeldItem() != null
				&& mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && autoBlock
				&& CombatUtil.canBlock(false, true, range + 4.0, true);
		if (buttpee) {
			if (e.isPost()) {
				mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 140);
				mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
			} else {
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
						C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
			}
		}
		if (!targets.isEmpty()) {
			if (targetIndex >= targets.size()) {
				targetIndex = 0;
			}
			killaura.currentEntity = targets.get(0);
			float[] rots = RotationUtils.getRotations(targets.get(targetIndex));
			boolean gay = (mc.thePlayer.ticksExisted % 1 == 0);
			if (gay) {
				e.setYaw(rots[0]);
				e.setPitch(rots[1]);
				mc.thePlayer.rotationYawHead = rots[0];
				mc.thePlayer.rotationPitchHead = rots[1];
				mc.thePlayer.renderYawOffset = rots[0];
			}
		} else {
			targetIndex = 0;
			killaura.currentEntity = null;
		}
		if (e.isPost()) {
			return;
		}
		if (!timer.delay(1000L / clickSpeed)) {
			return;
		}
		for (final Entity entity : targets) {
			killaura
					.sendPacketSilent(new C18PacketSpectate(UUID.fromString("9b450781-162f-4c1d-8d1f-af2aab7e526e")));
			mc.thePlayer.setBoundingBox(mc.thePlayer.boundingBox.offset(0, 0.08, 0));
			mc.thePlayer.swingItem();
			mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
			mc.effectRenderer.func_178926_a(entity, EnumParticleTypes.CRIT);
			mc.effectRenderer.func_178926_a(entity, EnumParticleTypes.CRIT_MAGIC);
		}
		++targetIndex;
		timer.reset();
	}
}
