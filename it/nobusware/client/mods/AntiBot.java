package it.nobusware.client.mods;

import java.util.Iterator;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S38PacketPlayerListItem;

public class AntiBot extends Module {

	public AntiBot(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Override
	public void Abilitato() {
		super.Abilitato();
	}// 0xrqw-8ww5x@alt.com
		// bhq9f-6ab7q@alt.com

	@Handler
	public void onPackets(EventNettyPackets e) {
		if (e.getPacket() instanceof S0CPacketSpawnPlayer) {
			S0CPacketSpawnPlayer p = (S0CPacketSpawnPlayer) e.getPacket();

		}
	      if (e.getPacket() instanceof S2APacketParticles) {
	            e.cancel();
	        }
	      
		if (e.getPacket() instanceof S38PacketPlayerListItem) {
			// e.cancel();
		}
	}

	@Handler
	public void onUpdate(EventUpdate e) {
		if (this.isAbilitato() && (mc.thePlayer != null && mc.theWorld != null)) {

			Iterator<Entity> entities = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();
			while (entities.hasNext()) {
				Object theObject = entities.next();
				if (theObject instanceof EntityLivingBase) {
					EntityLivingBase entityplayer = (EntityLivingBase) theObject;
					if (entityplayer == mc.thePlayer)
						continue;
					if (entityplayer != null &&entityplayer.getName().toLowerCase().contains("\u00a7c\u00a7c") && entityplayer.getName().contains("\u00a7c") && entityplayer != mc.thePlayer && Float.isNaN(entityplayer.getHealth()) && !entityplayer.onGround && entityplayer.getDisplayName()
							.getFormattedText().equalsIgnoreCase(String.valueOf(entityplayer.getName()) + "§r") &&
							!mc.thePlayer.getDisplayName().getFormattedText()
							.equalsIgnoreCase(String.valueOf(mc.thePlayer.getName()) + "§r")
							&& entityplayer.getUniqueID() == null && entityplayer.getHealth() >= 0.0F
							&& entityplayer instanceof EntityPlayer)
						System.out.println(entityplayer.getName());
						Minecraft.getMinecraft().theWorld.removeEntity((Entity) entityplayer);
				}

			}
		}
	}
}
