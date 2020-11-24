package it.nobusware.client.mods.aura;

import java.util.function.Consumer;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventPackets;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.aura.modes.Multi;
import it.nobusware.client.mods.aura.modes.Single;
import it.nobusware.client.mods.aura.modes.Switch;
import it.nobusware.client.mods.aura.modes.Verus;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.EnumValue;
import it.nobusware.client.utils.value.impl.NumberValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class killaura extends Module {
	
	private EnumValue<Mode> mode = new EnumValue("Mode", Mode.VERUS);
	private static NumberValue<Double> range = new NumberValue("Range", Double.valueOf(4.9D), Double.valueOf(1.0F), Double.valueOf(7.0F), Double.valueOf(0.1F));
	private NumberValue<Double> cps = new NumberValue("CPS", Double.valueOf(17.0D), Double.valueOf(1.0F), Double.valueOf(17.0F), Double.valueOf(0.1F));

	public killaura(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.mode, this.cps, this.range });
	}

	public static boolean isBlocking = false;
	public static EntityLivingBase currentEntity;

	@Handler
	public Consumer<EventUpdate> onUpdate = (event) -> {
		if (!this.isAbilitato()) {
			return;
		} else {
			if (this.mode.getValue() == Mode.SINGLE) {
				Single.doUpdate(this, event, mc);
			} else if (this.mode.getValue() == Mode.MULTI) {
				Single.autoblfake = false;
				Multi.doUpdate(this, event, mc);
			}else if (this.mode.getValue() == Mode.SWITCH) {
				Single.autoblfake = false;
				Switch.doUpdate(this, event, mc);
			}else if (this.mode.getValue() == Mode.VERUS) {
				Single.autoblfake = false;
				Verus.doUpdate(this, event, mc);
			}
		}
	};
	
	@Handler
	public Consumer<EventPackets> packet = (event) -> {
		 if (this.mode.getValue() == Mode.VERUS) {
			 Verus.doPackets(this, event, mc);
		 }
	};
	
	
	@Override
	public void Disabilitato() {
		Single.autoblfake = false;
		isBlocking = false;
		currentEntity = null;
		if (isBlocking) {
			sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0.221, 0.221, 0.213), EnumFacing.UP));
		}
	}
	
	public static NumberValue<Double> getRange() {
		return range;
	}

	public NumberValue<Double> getCps() {
		return cps;
	}

	private enum Mode {
		SINGLE, MULTI, SWITCH, VERUS;
	}
	
    public static void sendPacketSilent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
    
    public static void sendPacket(Packet packet) {
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
}
