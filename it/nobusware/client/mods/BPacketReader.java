package it.nobusware.client.mods;

import java.nio.charset.Charset;

import com.google.common.base.Charsets;

import QuarantineAPI.config.annotation.Handler;
import io.netty.buffer.ByteBuf;
import it.nobusware.client.events.EventNettyPackets;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.EnumValue;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.util.ChatComponentText;

public class BPacketReader extends Module {
	
	private EnumValue<Mode> mode = new EnumValue("Mode", Mode.UTF_8);

	public BPacketReader(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.mode });
	}

	@Handler
	public void Netty(EventNettyPackets e) {
		if (e.getPacket() instanceof S3FPacketCustomPayload) {
			S3FPacketCustomPayload S3F = (S3FPacketCustomPayload) e.getPacket();
			ByteBuf bytes = S3F.getBufferData();
			String diocane = "";
			if (this.mode.getValue() == Mode.ISO88591) {
				diocane = bytes.toString(Charsets.ISO_8859_1);
			} else if (this.mode.getValue() == Mode.US_ASCII) {
				diocane = bytes.toString(Charsets.US_ASCII);
			} else if (this.mode.getValue() == Mode.UTF_16) {
				diocane = bytes.toString(Charsets.UTF_16);
			} else if (this.mode.getValue() == Mode.UTF_16BE) {
				diocane = bytes.toString(Charsets.UTF_16BE);
			} else if (this.mode.getValue() == Mode.UTF_16LE) {
				diocane = bytes.toString(Charsets.UTF_16LE);
			}else if (this.mode.getValue() == Mode.ISO885911) {
				diocane = bytes.toString(Charset.forName("ISO-8859-11"));
			}else if (this.mode.getValue() == Mode.UTF_8) {
				diocane = bytes.toString(Charsets.UTF_8);
			}
			mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("\n§cBChannel: §a" + S3F.getChannelName() + "\n§cMessage §e" + diocane));
		}
	}
	
	private enum Mode {
		ISO88591, US_ASCII, UTF_16, UTF_16BE, UTF_16LE, ISO885911, UTF_8;
	}
}