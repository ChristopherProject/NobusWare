package it.nobusware.client.events;

import QuarantineAPI.Event;
import net.minecraft.network.Packet;

public class EventNettyPackets extends Event {

	private Packet packet;

	public Packet getPacket() {
		return this.packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public EventNettyPackets(Packet packet) {
		this.packet = packet;
	}
}