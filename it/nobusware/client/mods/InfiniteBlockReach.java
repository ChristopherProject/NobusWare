package it.nobusware.client.mods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.events.EventRenderer3D;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.utils.PathFinder;
import it.nobusware.client.utils.RenderUtils;
import it.nobusware.client.utils.Timer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockSign;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class InfiniteBlockReach extends Module {

    private ArrayList<Vec3> path = new ArrayList<>();
	private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();
	private final Timer timer = new Timer();
	private final Timer timer1 = new Timer();

    public InfiniteBlockReach(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
        super(nome_mod, tasto, nome_array_printed, categoria);
    }

    public void Abilitato() {
        path.clear();
    	timer.reset();
		timer1.reset();
    }

    @Override
    public void Disabilitato() {
    	packetQueue.clear();
    }
    
    @Handler
    public void onRender(EventRenderer3D e) {
        try {
            final int x = getBlinkBlock().getBlockPos().getX();
            final int y = getBlinkBlock().getBlockPos().getY();
            final int z = getBlinkBlock().getBlockPos().getZ();
            final Block block1 = getBlock(x, y, z);
            final Block block2 = getBlock(x, y + 1, z);
            final Block block3 = getBlock(x, y + 2, z);
            final boolean blockBelow = !(block1 instanceof BlockSign) && block1.getMaterial().isSolid();
            final boolean blockLevel = !(block2 instanceof BlockSign) && block1.getMaterial().isSolid();
            final boolean blockAbove = !(block3 instanceof BlockSign) && block1.getMaterial().isSolid();
            if (getBlock(getBlinkBlock().getBlockPos()).getMaterial() != Material.air && blockBelow && blockLevel && blockAbove && !(getBlock(getBlinkBlock().getBlockPos()) instanceof BlockChest)) {
                GL11.glPushMatrix();
                RenderUtils.pre3D();
                mc.entityRenderer.setupCameraTransform(e.getRenderTick(), 2);

                GL11.glColor4d(0, 0.6, 0, 0.25);
                RenderUtils.drawBoundingBox(new AxisAlignedBB(x - RenderManager.renderPosX, y - RenderManager.renderPosY, z - RenderManager.renderPosZ, x - RenderManager.renderPosX + 1.0, y + getBlock(getBlinkBlock().getBlockPos()).getBlockBoundsMaxY() - RenderManager.renderPosY, z - RenderManager.renderPosZ + 1.0));
                GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                RenderUtils.post3D();
                GL11.glPopMatrix();
            }
        } catch (Exception ignored) {
        }
    }

	@Handler
	public Consumer<EventUpdate> update = (event) -> {
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
	};

    @Handler
    public void onPacket(EventPackets e) {

        if (mc.thePlayer != null && this.isAbilitato()) {
        	if(e.getPacket() instanceof C03PacketPlayer) {
				C03PacketPlayer pos = (C03PacketPlayer) e.getPacket();
        		if(mc.thePlayer.ticksExisted % 3 != 0 && (mc.thePlayer.isMoving() || lock())) {
					if(!mc.thePlayer.isMovingOnGround() && timer.delay(1400L) && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isJumping && !mc.getNobita().getModManager().Prendi(NoFall.class).isAbilitato()) {
						mc.thePlayer.sendQueue.noEventPacket(new C18PacketSpectate(mc.thePlayer.getGameProfile().getId()));
						double max =(mc.thePlayer.posY - 0.992D);
						pos.y =  +(RandomUtils.nextDouble(10.60508745964098D, 101.41138779393725D));
						pos.x = RandomUtils.nextFloat(0.8412349224090576F, 0.9530588388442993F);
						pos.z = -0.43534232F;
						pos.field_149480_h = true;
					}
					if (doHittingProcess()) {
	    				mc.thePlayer.sendQueue.noEventPacket(new C0CPacketInput());
	        			mc.getNetHandler().addToSendQueue(new C00PacketKeepAlive(Integer.MIN_VALUE + (new Random()).nextInt(100)));
					}
					e.cancel();
				}
        	}
        	 if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
				if (!doHittingProcess() || !mc.thePlayer.isJumping)
				packetQueue.add(e.getPacket());
				e.cancel();
			}
			if (mc.thePlayer != null && mc.thePlayer.ticksExisted <= 7) {
				timer.reset();
				packetQueue.clear();
			}
			 if (e.getPacket() instanceof C05PacketPlayerLook
					|| e.getPacket() instanceof S08PacketPlayerPosLook) {
				e.cancel();
			}
            if (e.getPacket() instanceof C08PacketPlayerBlockPlacement) {

                C08PacketPlayerBlockPlacement place = (C08PacketPlayerBlockPlacement) e.getPacket();

                BlockPos pos = place.getBlockPos();
                ItemStack stack = place.getStack();

                double dist = Math.sqrt(mc.thePlayer.getDistanceSq(pos));

                if (dist > 6 && pos.getY() != -1 && (stack != null || mc.theWorld.getBlockState(pos).getBlock() instanceof BlockContainer)) {

                    Vec3 topFrom = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                    Vec3 to = new Vec3(pos.getX(), pos.getY(), pos.getZ());
                    path = computePath(topFrom, to);

                    for (Vec3 pathElm : path) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                    }

                    mc.thePlayer.sendQueue.noEventPacket(place);

                    Collections.reverse(path);

                    for (Vec3 pathElm : path) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                    }

                    e.cancel();
                }
            }

            if (e.getPacket() instanceof C07PacketPlayerDigging) {

                C07PacketPlayerDigging packet = (C07PacketPlayerDigging) e.getPacket();
                C07PacketPlayerDigging.Action act = packet.func_180762_c();
                BlockPos pos = packet.func_179715_a();
                EnumFacing face = packet.func_179714_b();
                double dist = Math.sqrt(mc.thePlayer.getDistanceSq(pos));

                if (dist > 6 && act == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {

                    Vec3 topFrom = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                    Vec3 to = new Vec3(pos.getX(), pos.getY(), pos.getZ());

                    path = computePath(topFrom, to);

                    for (Vec3 pathElm : path) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                    }

                    C07PacketPlayerDigging end = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, face);

                    mc.thePlayer.sendQueue.noEventPacket(packet);
                    mc.thePlayer.sendQueue.noEventPacket(end);

                    Collections.reverse(path);

                    for (Vec3 pathElm : path) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                    }

                    e.cancel();
                } else if (act == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                    e.cancel();
                }
            }
        }
    }
    
	public boolean lock() {
		return !(!mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed
				&& !mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed);
	}
	
    
	public boolean doHittingProcess() {
		return mc.thePlayer.isBlocking() || mc.thePlayer.isSwingInProgress || mc.thePlayer.isUsingItem() || mc.thePlayer.isOnLadder()
				|| mc.thePlayer.isEating() || (mc.currentScreen instanceof GuiInventory) || (mc.currentScreen instanceof GuiChest);
	}

    private ArrayList<Vec3> computePath(Vec3 topFrom, Vec3 to) {
        if (!canPassThrow(new BlockPos(topFrom))) {
            topFrom = topFrom.addVector(0, 1, 0);
        }
        PathFinder pathfinder = new PathFinder(topFrom, to);
        pathfinder.compute();

        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        ArrayList<Vec3> path = new ArrayList<>();
        ArrayList<Vec3> pathFinderPath = pathfinder.getPath();
        for (Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > 5 * 5) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                    double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                    double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                    double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                    double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                    double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                    cordsLoop:
                    for (int x = (int) smallX; x <= bigX; x++) {
                        for (int y = (int) smallY; y <= bigY; y++) {
                            for (int z = (int) smallZ; z <= bigZ; z++) {
                                if (!PathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break cordsLoop;
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            i++;
        }
        return path;
    }

    private boolean canPassThrow(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(new net.minecraft.util.BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }

    public MovingObjectPosition getBlinkBlock() {
        net.minecraft.util.Vec3 var4 = mc.thePlayer.func_174824_e(mc.timer.renderPartialTicks);
        net.minecraft.util.Vec3 var5 = mc.thePlayer.getLook(mc.timer.renderPartialTicks);
        net.minecraft.util.Vec3 var6 = var4.addVector(var5.xCoord * 70, var5.yCoord * 70, var5.zCoord * 70);
        return mc.thePlayer.worldObj.rayTraceBlocks(var4, var6, false, false, true);
    }

    public static Block getBlock(final int x, final int y, final int z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(final BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock();
    }
}