package it.nobusware.client.events;

import java.util.List;

import QuarantineAPI.Event;
import net.minecraft.network.Packet;

public class EventPackets extends Event {

	private Packet packet;

	public EventPackets(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return this.packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public void queueAndCancel(List<Packet> list) {
		list.add(this.packet);
		cancel();
	}
}
