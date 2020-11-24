package it.nobusware.client.mods.aura.modes;

import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

import it.nobusware.client.events.EventPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.mods.Disabler;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.utils.CombatUtil;
import it.nobusware.client.utils.RotationUtils;
import it.nobusware.client.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Verus {

	static boolean invisibles = true;

	// public static double range = 4.00092D;
	private static final Random RANDOM_NUMBER_GENERATOR = new Random();
	static boolean players = true;
	static boolean monsters = false;
	static boolean autoBlock = true;

	public static EntityLivingBase entity;

	public static boolean buttpee;
	public static int randomCPS;

	public static Entity currentEntity;

	private static Timer timer = new Timer();

	public static EntityLivingBase getEntity() {
		return entity = (EntityLivingBase) CombatUtil.getTarget(monsters, players,
				killaura.getRange().getValue().doubleValue(), invisibles);
	}

	public static void setEntity(EntityLivingBase entity) {
		Verus.entity = entity;
	}
	
	public static void doUpdate(final killaura killaura, final EventUpdate e, final Minecraft mc) {
		long clickSpeed = (long) killaura.getCps().getValue().doubleValue();
		if (killaura.isAbilitato() && mc.theWorld != null && getEntity() != null) {
			if (e.isPre() && autoBlock
					&& CombatUtil.canBlock(false, true, killaura.getRange().getValue().doubleValue() + 0.2D, false)) {
				if (e.isPost() && !mc.getNobita().getModManager().Prendi(Disabler.class).isAbilitato()) {
					mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 140);
					autoBlock = true;
				}
			}else {
				autoBlock = false;
			}
			float[] rots = RotationUtils.getRotations((EntityLivingBase) getEntity());
			if (e.isPre()) {
				mc.thePlayer.rotationYawHead = rots[0];
				mc.thePlayer.rotationPitchHead = rots[1];
				mc.thePlayer.renderYawOffset = rots[0];
			}
			if (timer.delay((float) (1000L / (clickSpeed + randomCPS)))) {
				randomCPS = RandomUtils.nextInt(1, 2);
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue((Packet) new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
				timer.reset();
			}

		}
	};

	public static boolean chance(int percent) {
		return RANDOM_NUMBER_GENERATOR.nextInt(100) <= percent;
	}
//StillMe_
	public static void doPackets(final killaura killaura, final EventPackets e, final Minecraft mc) {
		if(killaura.isAbilitato()) {
			if (e.getPacket() instanceof S2DPacketOpenWindow) {
				e.cancel();
			}
			if (e.getPacket() instanceof C05PacketPlayerLook || e.getPacket() instanceof S08PacketPlayerPosLook) {
				e.cancel();
			}
			if (e.getPacket() instanceof C07PacketPlayerDigging) {
				e.cancel();
			}
			if (e.getPacket() instanceof S00PacketKeepAlive) {
				S00PacketKeepAlive packet = new S00PacketKeepAlive();
				packet.get(RandomUtils.nextInt(1, 128));
				e.cancel();
			}
			if (e.getPacket() instanceof C0EPacketClickWindow) {
				e.cancel();
			}	
		}
		
	}
}