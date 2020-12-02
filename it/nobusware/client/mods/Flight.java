package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.commands.settings;
import it.nobusware.client.events.CollisionEvent;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.events.MoveEvent;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.MoveUtils;
import it.nobusware.client.utils.Timer;
import it.nobusware.client.utils.value.impl.BooleanValue;
import it.nobusware.client.utils.value.impl.EnumValue;
import it.nobusware.client.utils.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.world.WorldSettings;

import java.util.Random;
import java.util.function.Consumer;

public class Flight extends Module {

	private int clock = 0;

	public static Timer timer = new Timer();
	int level = 1;
	private boolean diocane = false;
	double moveSpeed, lastDist;
	private boolean b2;
	public int stage;
	private boolean decreasing2, hypixelboost, canboost, nigga;
	private double starty;
	private float timervalue;
	public static boolean check = false;
	private final EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.VANILLA);
	private final BooleanValue flag = new BooleanValue("FlagCheck", false);
	private final NumberValue<Float> speed = new NumberValue<>("Speed", 1.4F, 1.0F, 7.0F, 0.1F);

	private final NumberValue<Float> ascend = new NumberValue<>("Ascend", 450.0F, 1.0F, 700.0F, 0.1F);
	private final NumberValue<Integer> packetTimes = new NumberValue<>("Packets", 10, 1, 10, 1);

	public Flight(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(this.mode, this.speed, this.ascend , this.packetTimes, this.flag);
	}

	public void Abilitato() {
		stage = 0;
		level = 1;
		moveSpeed = 0.1D;
		lastDist = 0.0D;
		if (this.isAbilitato() && (this.mode.getValue() == Mode.HYPIXEL)) {
			canboost = true;
			{
				double motionY = 0.40123128;
				timervalue = 1.0F;
				if (mc.thePlayer.onGround) {
					if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)
							&& mc.thePlayer.isCollidedVertically) {
						if (mc.thePlayer.isPotionActive(Potion.jump))
							motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
						mc.thePlayer.motionY = motionY;
					}
					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.28E-10D, mc.thePlayer.posZ);
					level = 1;
					moveSpeed = 0.1D;
					hypixelboost = true;
					lastDist = 0.0D;
				} else {
					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.26E-10D, mc.thePlayer.posZ);
				}
				timer.reset();
			}
		}
	}

	@Handler
	public void eventobellobello(EventUpdate ev) {
		float speed = this.speed.getValue().floatValue();
		if (this.isAbilitato() && (this.mode.getValue() == Mode.VANILLA)) {
			if (mc.thePlayer.ticksExisted % 2 == 0
					&& !mc.getNobita().getModManager().Prendi(Disabler.class).isAbilitato()) {
				mc.thePlayer.sendQueue.noEventPacket(new C0CPacketInput());
			}
			mc.thePlayer.capabilities.allowFlying = true;
			mc.thePlayer.capabilities.isFlying = true;
			MoveUtils.setMotion(speed);
			if (timer.delay(this.ascend.getValue().floatValue())) {
				MoveUtils.fallPacket();
				MoveUtils.ascendPacket();
				timer.reset();
			}
		}
		if (this.isAbilitato() && (this.mode.getValue() == Mode.HYPIXEL)) {
			if (ev.isPre()) {
				double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
				double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
				lastDist = Math.sqrt((xDist * xDist) + (zDist * zDist));
				if (canboost && hypixelboost) {
					timervalue += decreasing2 ? -0.01 : 0.05;
					if (timervalue >= 1.4) {
						decreasing2 = true;
					}
					if (timervalue <= 0.9) {
						decreasing2 = false;
					}
					if (timer.delay(2000)) {
						canboost = false;
					}
				}
				if (mc.gameSettings.keyBindJump.isPressed()) {
					mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + 0.4, mc.thePlayer.posZ);
					mc.thePlayer.motionY = 0.8;
					mc.thePlayer.motionX *= 0.1;
					mc.thePlayer.motionZ *= 0.1;
				}
				if ((mc.thePlayer.ticksExisted % 2) == 0) {
					mc.thePlayer.setPosition(mc.thePlayer.posX,
							mc.thePlayer.posY + getRandomInRange(0.00000000000001235423532523523532521,
									0.0000000000000123542353252352353252 * 10),
							mc.thePlayer.posZ);
				}
				mc.thePlayer.motionY = 0;
			}
		}

		if (this.isAbilitato() && this.mode.getValue().equals(Mode.PACKET)) {
			if (mc.gameSettings.keyBindAttack.pressed || mc.gameSettings.keyBindBack.pressed || mc.gameSettings.keyBindSneak.pressed || mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindJump.pressed){
				mc.playerController.setGameType(WorldSettings.GameType.CREATIVE);
			} else {
				mc.playerController.setGameType(WorldSettings.GameType.SURVIVAL);
			}

			mc.thePlayer.motionY = 0f;

			this.clock += 1;
			if (this.clock >= packetTimes.getValue()) {

				mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
				if (mc.gameSettings.keyBindJump.pressed) {
					if (mc.gameSettings.keyBindSneak.pressed) {
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY + 0.0625 - 0.0625, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
					} else {
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY + 0.0625 - 0, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
					}
				} else if (mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindRight.pressed) {
					mc.thePlayer.motionX = 0;
					mc.thePlayer.motionZ = 0;
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posX + 0.0625 - 0, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
				} else {
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY + 0 - (mc.gameSettings.keyBindSneak.pressed ? 0.0625 : 0), mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
				}
				mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY - 0999f, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
				this.clock = 0;
			}
		}

	}
	
	 @Handler
	 public void update(EventUpdate e) {
		 if(this.isAbilitato() && diocane) {
			 ChatUtils.print("§cStai Flaggando Flag");
			 this.Disabilitato();
		 }
	 }

	@Handler
	public void onMove(MoveEvent e) {
		if (this.isAbilitato() && (this.mode.getValue() == Mode.COLLISION)) {
			if(mc.thePlayer.ticksExisted % 6 == 0 && mc.thePlayer.isMoving())
			mc.thePlayer.sendQueue.noEventPacket(new C0CPacketInput());
			//MoveUtils.setMotion(this.speed.getValue().floatValue());
		}

		
		if (this.isAbilitato() && (this.mode.getValue() == Mode.HYPIXEL)) {
			float yaw = mc.thePlayer.rotationYaw;
			double strafe = mc.thePlayer.movementInput.moveStrafe;
			double forward = mc.thePlayer.movementInput.moveForward;
			double mx = -Math.sin(Math.toRadians(yaw)), mz = Math.cos(Math.toRadians(yaw));
			if (forward == 0.0F && strafe == 0.0F) {
				e.setX(0);
				e.setZ(0);
			}
			if (forward != 0 && strafe != 0) {
				forward = forward * Math.sin(Math.PI / 4);
				strafe = strafe * Math.cos(Math.PI / 4);
			}
			if (hypixelboost) {
				if (level != 1 || mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F) {
					if (level == 2) {
						level = 3;
						moveSpeed *= 2.1499999D;
					} else if (level == 3) {
						level = 4;
						double difference = 0.73D * (lastDist - getBaseMoveSpeed());
						moveSpeed = lastDist - difference;
					} else {
						if (mc.theWorld
								.getCollidingBoundingBoxes(mc.thePlayer,
										mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D))
								.size() > 0 || mc.thePlayer.isCollidedVertically) {
							level = 1;
						}
						moveSpeed = lastDist - lastDist / 159.0D;
					}
				} else {
					level = 2;
					double boost = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.706 : 2.034;
					moveSpeed = boost * getBaseMoveSpeed() - 0.01D;
				}
				moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
				e.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
				e.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
				if (forward == 0.0F && strafe == 0.0F) {
					e.setX(0.0);
					e.setZ(0.0);
				}
				if (timer.delay(1700) && hypixelboost) {
					hypixelboost = false;
				}
			}
		}
		else if(this.isAbilitato() && (this.mode.getValue() == Mode.REDESKY)) {
			//redesky test
			mc.thePlayer.capabilities.isFlying = true;
			net.minecraft.util.Timer.timerSpeed = 1.7F;
		}
	}
	
	@Handler
	public Consumer<EventNettyPackets> eddie = (event) -> {
		if(event.getPacket() instanceof S08PacketPlayerPosLook) {
			if(this.flag.getValue().booleanValue() == true) {
				ChatUtils.print("Possible Flag/LagBack Fly Disabilitala.");
				diocane = true;
			}else {
				diocane = false;
			}
		}
	};

	@Handler
	public Consumer<CollisionEvent> eventConsumer = (event) -> {
		if (mc.thePlayer != null && mc.theWorld != null && this.isAbilitato() && (this.mode.getValue() == Mode.COLLISION) && mc.theWorld != null && !mc.thePlayer.isSneaking() ) {
			check = false;
			event.setBoundingBox(new AxisAlignedBB(-2, -1, -2, 2, 1, 2).offset(event.getX(), event.getY(), event.getZ()));
			if (timer.delay(1700F) && check == true) {
				MoveUtils.fallPacket();
				timer.reset();
			}
		}
	};

	@Override
	public void Disabilitato() {
		if(diocane) {
			diocane = false; 
		}
		if (!mc.thePlayer.capabilities.isCreativeMode) {
			mc.thePlayer.capabilities.allowFlying = false;
			mc.thePlayer.capabilities.isFlying = false;
			net.minecraft.util.Timer.timerSpeed = 1.0F;
			MoveUtils.setMotion(0.15F);
			net.minecraft.util.Timer.timerSpeed = 1.0F;
		}
		level = 1;
		moveSpeed = 0.1D;
		b2 = false;
		lastDist = 0.0D;
	}

	public static double getBaseMoveSpeed() {
		double n = 0.2873;
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
			n *= 1.0 + 0.2
					* (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
		}
		return n;
	}

	private void setSpeed(double speed) {
		Minecraft.getMinecraft().thePlayer.motionX = -(Math.sin(getDirection()) * speed);
		Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(getDirection()) * speed;
	}

	public static float getDirection() {
		float var1 = Minecraft.getMinecraft().thePlayer.rotationYaw;
		if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
			var1 += 180.0f;
		}
		float forward = 1.0f;
		if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
			forward = -0.5f;
		} else if (Minecraft.getMinecraft().thePlayer.moveForward > 0.0f) {
			forward = 0.5f;
		} else {
			forward = 1.0f;
		}
		if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f) {
			var1 -= 90.0f * forward;
		}
		if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f) {
			var1 += 90.0f * forward;
		}
		var1 *= 0.017453292f;
		return var1;
	}

	public static double getRandomInRange(double min, double max) {
		Random random = new Random();
		double range = max - min;
		double scaled = random.nextDouble() * range;
		if (scaled > max) {
			scaled = max;
		}
		double shifted = scaled + min;

		if (shifted > max) {
			shifted = max;
		}
		return shifted;
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

	private enum Mode {
		VANILLA, COLLISION, HYPIXEL, REDESKY, PACKET
	}
}