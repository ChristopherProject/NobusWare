package it.nobusware.client.mods;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.apache.commons.lang3.RandomUtils;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.CombatUtil;
import it.nobusware.client.utils.RotationUtils;
import it.nobusware.client.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S00PacketKeepAlive;

public class TrollandiaKillaura extends Module {

	public TrollandiaKillaura(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	static boolean invisibles = true;
	// 6 3 3.00092D
	public static double range = 4.00092D;
	static boolean players = true;
	static boolean monsters = false;
	static boolean autoBlock = true;

	public static EntityLivingBase entity;

	public static boolean buttpee;
	public static int randomCPS;

	public static Entity currentEntity;

	private static Timer timer = new Timer();

	public void Abilitato() {
		System.out.println("Sto Pensando A Malachiel Nudo Mentre Faccio La KillauraS");
		super.Abilitato();
	}

	public static EntityLivingBase getEntity() {
		return entity = (EntityLivingBase) CombatUtil.getTarget(monsters, players, range, invisibles);
	}

	public static void setEntity(EntityLivingBase entity) {
		TrollandiaKillaura.entity = entity;
	}

	@Handler
	public Consumer<EventUpdate> eventConsumer = (event) -> {

		// 12 3.0 10.0
		long clickSpeed = (long) 17.0D;
		buttpee = true;

		if (this.isAbilitato() && mc.theWorld != null) {

			if (getEntity() != null) {
				buttpee = (mc.thePlayer.getHeldItem() != null
						&& mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword && autoBlock
						&& CombatUtil.canBlock(false, true, range + 4.0D, true));
				if (buttpee)
					;
				float[] rots = RotationUtils.getRotations((EntityLivingBase) getEntity());
				boolean gay = (mc.thePlayer.ticksExisted % 1 == 0);
				if (gay) {
					//event.setYaw(rots[0]);
					//event.setPitch(rots[1]);
					mc.thePlayer.rotationYawHead = rots[0];
					mc.thePlayer.rotationPitchHead = rots[1];
					mc.thePlayer.renderYawOffset = rots[0];
				}
				if (timer.delay((float) (1000L / (clickSpeed + randomCPS))) && event.isPre()) {
					randomCPS = RandomUtils.nextInt(1, 2);
					mc.thePlayer.swingItem();
					//this.mc.playerController.attackEntity(this.mc.thePlayer, getEntity());
					 mc.thePlayer.sendQueue.addToSendQueue((Packet) new C02PacketUseEntity(entity,
					 C02PacketUseEntity.Action.ATTACK));
					timer.reset();
				}
			}
		}
	};

	@Handler
	public void onBypass(EventPackets e) {
		boolean malachielnudo2 = (mc.thePlayer.ticksExisted % 15 == 0);
		boolean serena_mi_manchi = (mc.thePlayer.ticksExisted % 45 == 0);
		if (e.getPacket() instanceof net.minecraft.network.play.server.S2DPacketOpenWindow) {
			e.cancel();
		}
		if (e.getPacket() instanceof net.minecraft.network.play.server.S00PacketKeepAlive) {
			S00PacketKeepAlive packet = new S00PacketKeepAlive();
			packet.get(4000);
			e.cancel();
		}
		if (e.getPacket() instanceof net.minecraft.network.play.client.C0EPacketClickWindow) {
			e.cancel();
		}
	}

	public static void sendPacketSilent(Packet packet) {
		mc.getNetHandler().getNetworkManager().sendPacket(packet);
	}

	public static void sendPacket(Packet packet) {
		mc.thePlayer.sendQueue.addToSendQueue(packet);
	}

	public void Disabilitato() {
		super.Disabilitato();
	}
}
