package it.nobusware.client.mods;

import java.util.Random;
import java.util.UUID;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class ZeroSmaher extends Module{
	
	private Random asd = new Random();

	public ZeroSmaher(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}
	
	@Handler
	public void ZeroSmashSkiddatoDaFabioTNTLOL(EventUpdate e) {
		if(this.isAbilitato() && mc.theWorld != null) {
			new Thread(()->{
				for(int i = 0; i < 4; i++) {
					sendPacketSilent(new C08PacketPlayerBlockPlacement(
							new BlockPos(asd.nextDouble()* i + 9, asd.nextDouble() * i + 9, asd.nextDouble()* i + 9), i,
							mc.thePlayer.getCurrentEquippedItem(), (float)(i *asd.nextDouble()) , (float)(i *asd.nextDouble()), (float)(i *asd.nextDouble())));
				}
			});
		}
	}
	
	   public static void sendPacketSilent(Packet packet) {
	        mc.getNetHandler().getNetworkManager().sendPacket(packet);
	    }

	    public static void sendPacket(Packet packet) {
	        mc.thePlayer.sendQueue.addToSendQueue(packet);
	    }

}
