package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.manager.Module;
import net.minecraft.util.ChatComponentText;

public class PacketSniffer extends Module {

	public PacketSniffer(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}
	
	@Handler
	public void Netty(EventNettyPackets e) {
		if(this.isAbilitato() && e.getPacket() != null && mc.theWorld != null) {
		String Packet1 = e.getPacket().toString();
		String Packet = Packet1.substring(0, Packet1.indexOf("@"));
		String PacketFinal = Packet.replace("net.minecraft.network.play.server.", "");
		String PacketBello = PacketFinal.replace("$", ".");
		String PacketUltimoDIOCANE = PacketBello.replace("C03PacketPlayer.", "");
		String GangBang = PacketUltimoDIOCANE.replace("Entity_S14PacketEntity.", "");
		mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§7[§eGamePacket§7] §8-> §7" + GangBang));
	}
   }
}
