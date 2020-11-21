package it.nobusware.client.mods;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.utils.Timer;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.EnumValue;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class Disabler extends Module {

	private EnumValue<Mode> mode = new EnumValue("Mode", Mode.VERUS);

	public Disabler(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { (EnumValue)this.mode });
	}

	private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue();
	private final Timer timer = new Timer();
	private final Timer timer1 = new Timer();

	@Handler
	public Consumer<EventUpdate> update = (event) -> {
		 if (this.mode.getValue() == Mode.VERUS) {
			 
			if (timer.delay(900L)) {
				if (!packetQueue.isEmpty()) {
					mc.thePlayer.sendQueue.noEventPacket(packetQueue.poll());
				}
				timer.reset();
			}

			if (timer1.delay(1200L) && mc.getNobita().getModManager().Prendi(killaura.class).isAbilitato() || (mc.getNobita().getModManager().Prendi(Speed.class).isAbilitato() || mc.getNobita().getModManager().Prendi(VanillaFly.class).isAbilitato() && mc.thePlayer.isMoving()) && !mc.getNobita().getModManager().Prendi(Collision.class).isAbilitato()) {
				PlayerCapabilities pc = new PlayerCapabilities();
				pc.disableDamage = false;
				pc.isFlying = false;
				pc.allowFlying = false;
				pc.isCreativeMode = false;
				pc.setFlySpeed(0.0F);
				pc.setPlayerWalkSpeed(0.0F);
				mc.thePlayer.sendQueue.noEventPacket(new C13PacketPlayerAbilities(pc));
				timer1.reset();
			}
		}
	};

	@Handler
	public Consumer<EventPackets> packet = (event) -> {

		 if (this.mode.getValue() == Mode.GHOSTLY) {
			if (event.getPacket() instanceof C03PacketPlayer) {
				mc.thePlayer.sendQueue.noEventPacket(new C18PacketSpectate(mc.thePlayer.getGameProfile().getId()));
				mc.thePlayer.sendQueue.noEventPacket(new C0CPacketInput(1.0F, 2.05F, true, true));
			}
			if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
				event.cancel();
			}
			if (event.getPacket() instanceof C00PacketKeepAlive) {
				event.cancel();
			}
		} else  if (this.mode.getValue() == Mode.VERUS) {
			if (event.getPacket() instanceof C00PacketKeepAlive) {
				if (timer1.delay(1400L)) {
					mc.thePlayer.sendQueue
							.noEventPacket(new C00PacketKeepAlive(Integer.MAX_VALUE + new Random().nextInt(100)));// RandomUtils.nextInt(15345345,
																													// 18345345);
					timer1.reset();
				}
				event.cancel();
			}

			if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
				packetQueue.add(event.getPacket());
				event.cancel();
			}

			if (event.getPacket() instanceof C03PacketPlayer) {
				mc.thePlayer.sendQueue.noEventPacket(new C18PacketSpectate(mc.thePlayer.getGameProfile().getId()));
				C03PacketPlayer pos = (C03PacketPlayer) event.getPacket();
				if (mc.thePlayer.ticksExisted % 3 != 0) {
					event.cancel();
				}
				if (mc.thePlayer.ticksExisted % 33 == 0 && !mc.thePlayer.isMovingOnGround()
						&& !mc.getNobita().getModManager().Prendi(NoFall.class).isAbilitato()) {
					pos.y = mc.thePlayer.posY + 0.42F;
					pos.x = 0.7634546;
					pos.z = -0.43534232;
					pos.field_149474_g = true;
					event.cancel();
				}
				if (doHittingProcess())
					mc.thePlayer.sendQueue.noEventPacket(new C0CPacketInput(1.0F, 1.0F, true, true));
			}

			if (mc.thePlayer != null && mc.thePlayer.ticksExisted <= 7) {
				timer.reset();
				packetQueue.clear();
			}

			if (event.getPacket() instanceof C05PacketPlayerLook
					|| event.getPacket() instanceof S08PacketPlayerPosLook) {
				event.cancel();
			}

			if (event.getPacket() instanceof C0EPacketClickWindow) {
				C0EPacketClickWindow pc2 = (C0EPacketClickWindow) event.getPacket();
				if (mc.thePlayer.ticksExisted % 136 == 0) {
					pc2.windowId = 0;
					pc2.slotId = -999;
					pc2.usedButton = 0;
					pc2.actionNumber = 1;
					pc2.clickedItem = null;
					pc2.mode = 4;
				}
			}
		}
	};

	public boolean doHittingProcess() {
		if (mc.thePlayer.isBlocking() || mc.thePlayer.isSwingInProgress || mc.thePlayer.isUsingItem()
				|| mc.thePlayer.isEating()) {
			return true;
		} else {
			return false;
		}
	}

	private enum Mode {
		VERUS, GHOSTLY;
	}

	@Override
	public void Disabilitato() {
		packetQueue.clear();
	}

	@Override
	public void Abilitato() {
		timer.reset();
		timer1.reset();
	}
}