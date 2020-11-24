package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class NoSlowDown extends Module {

	public NoSlowDown(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public void onUpdate(EventUpdate e) {
		if (this.mc.thePlayer.isBlocking() && e.isPre()) {
			this.mc.thePlayer.sendQueue.addToSendQueue((Packet) new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,BlockPos.ORIGIN, EnumFacing.UP));
		}
		else if (e.isPost() && this.mc.thePlayer.isBlocking()) {
			this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(), 71999999);
			this.mc.playerController.sendUseItem((EntityPlayer) this.mc.thePlayer, (World) this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem());
		}
	}
}