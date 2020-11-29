package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.utils.Timer;
import it.nobusware.client.utils.value.impl.EnumValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S2APacketParticles;
import org.apache.commons.lang3.RandomUtils;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class Disabler extends Module {

	private final EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.VERUS);

	public Disabler(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(this.mode);
	}

	private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();
	private final Timer timer = new Timer();
	private final Timer timer1 = new Timer();

	@Handler
	public Consumer<EventUpdate> update = (event) -> {
		 if (this.mode.getValue() == Mode.VERUS_INFINITE || this.mode.getValue() == Mode.VERUS) {
			 //he flag when i eating i try to fix it but im not sure to fix (for complete work use disabler(verus with killaura trollandia));
			 //fixed with new lock method. (fix and we have full disabler)
			if ((timer.delay(900L)) && (mc.thePlayer.isMoving() || this.lock())) {
				if (!packetQueue.isEmpty() && !doHittingProcess()) {
					mc.thePlayer.sendQueue.noEventPacket(packetQueue.poll());
				}
				timer.reset();
			}

			if (timer1.delay(1200L) && mc.getNobita().getModManager().Prendi(killaura.class).isAbilitato() || (mc.getNobita().getModManager().Prendi(Speed.class).isAbilitato() || mc.getNobita().getModManager().Prendi(Flight.class).isAbilitato() && mc.thePlayer.isMoving()) && !Flight.check) {
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
	public Consumer<EventNettyPackets> eventConsumer0 = (event) -> {
		if (event.getPacket() instanceof S2APacketParticles) {
			event.cancel();
		}
		if (event.getPacket() instanceof S08PacketPlayerPosLook) {
			S08PacketPlayerPosLook playerPosLook = (S08PacketPlayerPosLook) event.getPacket();
			playerPosLook.field_148938_b += 1.0E-4;
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
		} else  if (this.mode.getValue() == Mode.VERUS_INFINITE) {
			//System.out.println(event.getPacket());
			if (event.getPacket() instanceof C00PacketKeepAlive) {
				if (timer1.delay(1400L)) {
					mc.thePlayer.sendQueue.noEventPacket(new C00PacketKeepAlive(Integer.MAX_VALUE + new Random().nextInt(100)));// RandomUtils.nextInt(15345345,																		// 18345345);
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
		
				if(mc.thePlayer.ticksExisted % 3 != 0 ) {
					event.cancel();
				}
				
				if (mc.thePlayer.ticksExisted % 3 != 0 && !mc.thePlayer.isMovingOnGround() && !mc.getNobita().getModManager().Prendi(NoFall.class).isAbilitato()) {
					//Start value must be smaller or equal to end value
					
					double max =(mc.thePlayer.posY - 0.992D);
					pos.y =  +(RandomUtils.nextDouble(10.60508745964098D, 101.41138779393725D));
					pos.x = RandomUtils.nextFloat(0.8412349224090576F, 0.9530588388442993F);
					pos.z = -0.43534232F;
					pos.field_149480_h = true;
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
		}else if (this.mode.getValue() == Mode.HYPIXEL) {
              if (mc.thePlayer.ticksExisted % 25 == 0) {
                  PlayerCapabilities pc = new PlayerCapabilities();
                  pc.isFlying = true;
                  pc.setFlySpeed(Float.NaN);
                  mc.thePlayer.sendQueue.noEventPacket(new C13PacketPlayerAbilities(pc));
              }
              if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
                  event.setPacket(new C0FPacketConfirmTransaction(Integer.MIN_VALUE, Short.MAX_VALUE, true));
              }
      		if (event.getPacket() instanceof C05PacketPlayerLook
					|| event.getPacket() instanceof S08PacketPlayerPosLook) {
				event.cancel();
			}
      		
      		if (event.getPacket() instanceof C03PacketPlayer) {
      			if (mc.thePlayer.ticksExisted % 136 == 0) 
      			event.cancel();
      		}

			if (event.getPacket() instanceof C0EPacketClickWindow) {
				C0EPacketClickWindow pc2 = (C0EPacketClickWindow) event.getPacket();
				if (mc.thePlayer.ticksExisted % 3 == 0) {
					pc2.windowId = 0;
					pc2.slotId = -999;
					pc2.usedButton = 0;
					pc2.actionNumber = 1;
					pc2.clickedItem = null;
					pc2.mode = 4;
				}
			}
		}else if (this.mode.getValue() == Mode.VERUS) {
			if (event.getPacket() instanceof C00PacketKeepAlive) {
				if (timer1.delay(1235L)) {
	                C00PacketKeepAlive packetKeepAlive = (C00PacketKeepAlive) event.getPacket();
	                packetKeepAlive.key -= RandomUtils.nextInt(1308718, 1310768);
				}
				if (timer1.delay(1455L)) {
					mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings("en_US", 8, EntityPlayer.EnumChatVisibility.FULL, true, 127));
					mc.thePlayer.sendQueue.noEventPacket(new C0DPacketCloseWindow(0));
				}
				else if(timer1.delay(1600L)) {
					timer1.reset();
				}
				event.cancel();
			}else if (event.getPacket() instanceof C03PacketPlayer) {
				C03PacketPlayer pos = (C03PacketPlayer) event.getPacket();
				if(mc.thePlayer.ticksExisted % 3 != 0 && (mc.thePlayer.isMoving() || lock())) {
					//jump posistion flag
					if(!mc.thePlayer.isMovingOnGround() && !mc.thePlayer.isJumping && !mc.getNobita().getModManager().Prendi(NoFall.class).isAbilitato() && timer.delay(1400L)) {
						mc.thePlayer.sendQueue.noEventPacket(new C18PacketSpectate(mc.thePlayer.getGameProfile().getId()));
						//Start value must be smaller or equal to end value
						double max =(mc.thePlayer.posY - 0.992D);
						pos.y =  +(RandomUtils.nextDouble(10.60508745964098D, 101.41138779393725D));
						pos.x = RandomUtils.nextFloat(0.8412349224090576F, 0.9530588388442993F);
						pos.z = -0.43534232F;
						pos.field_149480_h = true;
					}
					if (doHittingProcess()) {
						mc.thePlayer.sendQueue.noEventPacket(new C0CPacketInput(1.0F, 1.0F, true, true));	
					}
					event.cancel();
				}
			}
			else if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
				if (!doHittingProcess() ||  !mc.thePlayer.isJumping )
				packetQueue.add(event.getPacket());
				event.cancel();
			}
			if (mc.thePlayer != null && mc.thePlayer.ticksExisted <= 7) {
				timer.reset();
				packetQueue.clear();
			}
			else if (event.getPacket() instanceof C05PacketPlayerLook
					|| event.getPacket() instanceof S08PacketPlayerPosLook) {
				event.cancel();
			}
		}
	};
	
	public boolean lock() {
		return !(!mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed
				&& !mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed);
	}
	
	public static int GeneraNumeroRandomico(float min, float max) {
		Random rand = new Random();
		int randomNum = (int) Float.parseFloat(String.valueOf(rand.nextInt((int) ((max - min) + 1)) + min));
		return (int) Float.parseFloat(String.valueOf(randomNum));
	}
	
	public static int GeneraNumeroRandomicoOG(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	  }

	public boolean doHittingProcess() {
		return mc.thePlayer.isBlocking() || mc.thePlayer.isSwingInProgress || mc.thePlayer.isUsingItem()
				|| mc.thePlayer.isEating() || (mc.currentScreen instanceof GuiInventory) || (mc.currentScreen instanceof GuiChest);
	}

	private enum Mode {
		VERUS, GHOSTLY, HYPIXEL, VERUS_INFINITE
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