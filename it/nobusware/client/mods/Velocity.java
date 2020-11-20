package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.manager.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Velocity extends Module {

	public Velocity(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public void onPacket(EventNettyPackets ev) {
		if (ev.getPacket() instanceof S12PacketEntityVelocity) {
			S12PacketEntityVelocity p = (S12PacketEntityVelocity) ev.getPacket();
			Entity var2 = Minecraft.getMinecraft().theWorld.getEntityByID(p.func_149412_c());
			if (var2 == Minecraft.getMinecraft().thePlayer) {
				ev.cancel();
			}
		}
	}
}