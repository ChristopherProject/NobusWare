package it.nobusware.client.mods;

import java.util.function.Consumer;

import org.apache.commons.lang3.RandomUtils;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.CollisionEvent;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.events.MoveEvent;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.PlayerUtils;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;

public class Step extends Module {
	
	public Step(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
	public Consumer<EventUpdate> eventConsumer = (event) -> {
		if(this.isAbilitato()) {
			if  (!PlayerUtils.isInLiquid() && mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround) {
				Timer.timerSpeed = 1.0F;
				mc.thePlayer.boundingBox.offsetAndUpdate(0,0.3,0);
			}
		}
	};
	
	@Handler
	public void PKTest(EventPackets e) {
		if (e.getPacket() instanceof C00PacketKeepAlive) {
			C00PacketKeepAlive packetKeepAlive = (C00PacketKeepAlive) e.getPacket();
			packetKeepAlive.key -= RandomUtils.nextInt(3, 128);
		}if(e.getPacket() instanceof C03PacketPlayer) {
			C03PacketPlayer c03 = (C03PacketPlayer) e.getPacket();
			if(mc.thePlayer.ticksExisted % 33 ==0) {
				c03.y -= 0.999D;
				e.cancel();
			}
			
		}
	}
}
