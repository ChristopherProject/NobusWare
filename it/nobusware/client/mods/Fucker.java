package it.nobusware.client.mods;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.manager.Module.Category;
import it.nobusware.client.utils.RotationUtils;
import it.nobusware.client.utils.Timer;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

public class Fucker extends Module {

	public Fucker(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	ArrayList positions = null;
	private Timer timer = new Timer();
	private double reach = 4.5D;
	private double delay = 50.0D;
	private boolean teleport = false;

	@Handler
	public Consumer<EventUpdate> eventConsumer = (event) -> {
		try {
			Iterator positions = BlockPos
					.getAllInBox(
							mc.thePlayer.getPosition()
									.subtract(new Vec3i(((Double) this.reach).doubleValue(),
											((Double) this.reach).doubleValue(), ((Double) this.reach).doubleValue())),
							this.mc.thePlayer.getPosition()
									.add(new Vec3i(((Double) this.reach).doubleValue(),
											((Double) this.reach).doubleValue(), ((Double) this.reach).doubleValue())))
					.iterator();
			BlockPos bedPos = null;

			while ((bedPos = (BlockPos) positions.next()) != null
					&& !((this.mc.theWorld.getBlockState(bedPos).getBlock() instanceof BlockBed)
							|| (this.mc.theWorld.getBlockState(bedPos).getBlock() instanceof BlockCake)
							|| (this.mc.theWorld.getBlockState(bedPos).getBlock() instanceof BlockDragonEgg))) {
				;
			}

			if (bedPos instanceof BlockPos) {
				float[] rot = RotationUtils.getRotationFromPosition((double) bedPos.getX(), (double) bedPos.getY(),
						(double) bedPos.getZ());
				event.yaw = rot[0];
				event.pitch = rot[1];
				if (this.timer.delay((long) ((Double) this.delay).intValue())) {
					this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
							C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, bedPos, EnumFacing.DOWN));
					this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
							C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, bedPos, EnumFacing.DOWN));
					this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
							C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, bedPos, EnumFacing.DOWN));
					this.mc.thePlayer.swingItem();

					this.timer.reset();
				}

			}
		} catch (Exception e) {
		}
	};
}