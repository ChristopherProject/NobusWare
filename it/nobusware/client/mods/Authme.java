package it.nobusware.client.mods;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import io.netty.buffer.Unpooled;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.BungeeChannelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class Authme extends Module {
	
	public Authme(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	public void Abilitato() {
		BungeeChannelUtil.AuthmeForceLoginExploit(mc.getSession().getUsername());
	}
}
