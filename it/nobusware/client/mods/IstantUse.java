package it.nobusware.client.mods;

import java.util.function.Consumer;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class IstantUse extends Module {

	public IstantUse(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public Consumer<EventUpdate> onUpdate = (event) -> {
		if (mc.thePlayer.isEating()
				&& !(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
			for (int i = 0; i < 32; i++) {
				mc.thePlayer.sendQueue.noEventPacket(new C03PacketPlayer());
			}
		}
	};
}
