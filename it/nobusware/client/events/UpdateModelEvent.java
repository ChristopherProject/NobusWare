package it.nobusware.client.events;

import QuarantineAPI.Event;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;


public final class UpdateModelEvent extends Event {

	private final EntityPlayer player;

	private final ModelPlayer model;

	public UpdateModelEvent(EntityPlayer player, ModelPlayer model) {
		this.player = player;
		this.model = model;
	}

	public final EntityPlayer getPlayer() {
		return this.player;
	}

	public final ModelPlayer getModel() {
		return this.model;
	}
}
