package it.nobusware.client.mods.aura.modes;

import org.apache.commons.lang3.RandomUtils;

import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.utils.CombatUtil;
import it.nobusware.client.utils.RotationUtils;
import it.nobusware.client.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class Single extends Module {

	public Single(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	private static Timer timer = new Timer();
	public static Entity entity;
	public static int randomCPS;
	public static boolean autoblfake = false;

	public static double range = 3.969 + RandomUtils.nextInt(1, 2);

	public static void doUpdate(final killaura killaura, final EventUpdate e, final Minecraft mc) {
		//12 15 13
		long clickSpeed = (long) 9.0D;
		boolean invisibles = true;
		//4 6
		//8.069D;//4.969D;
		boolean players = true;
		boolean monsters = false;
		autoblfake = false;
		
		entity = (EntityLivingBase) CombatUtil.getTarget(monsters, players, range, invisibles);
		killaura.currentEntity = (EntityLivingBase) entity;
		if (killaura.isAbilitato() && mc.theWorld != null && entity != null) {
			autoblfake =  (mc.thePlayer.getHeldItem() != null && 
				      mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword && killaura.isBlocking && 
				      CombatUtil.canBlock(false, true, range + 4.0D, true));
			float[] rots = RotationUtils.getRotations(killaura.currentEntity);
				if (timer.delay((float) (1000L / (clickSpeed))) && e.isPre()) {
					if (mc.thePlayer.ticksExisted % 67 == 0) {
						e.setYaw(rots[0]);
						e.setPitch(rots[1]);
						mc.thePlayer.rotationYawHead = rots[0];
						mc.thePlayer.rotationPitchHead = rots[1];
						mc.thePlayer.renderYawOffset = rots[0];
					}//BlackM44t05
					mc.thePlayer.swingItem();
					mc.thePlayer.attackTargetEntityWithCurrentItem(killaura.currentEntity);
					timer.reset();
				}
			}
	}
	
    public static void sendPacketSilent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
    
    public static void sendPacket(Packet packet) {
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
}
//boolean autoBlock = false;//killaura.isBlocking;
//4.0 + range (2.2 or other one like verus) 3.2
//buttpee = (mc.thePlayer.getHeldItem() != null
//		&& mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword && autoBlock
//		&& CombatUtil.canBlock(false, true, range + 4.2, true));
