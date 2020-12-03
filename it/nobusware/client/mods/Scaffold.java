package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.List;

public class Scaffold extends Module {
	
	private int block;
    private static List<Block> blockBlacklist;
    private BlockData data;
    private Timer timer;
    private int slot;
    private int newSlot;
    private int oldSlot;
    
    public Scaffold(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	       this.timer = new Timer();
	        this.slot = -1;
	}
    
    @Handler
    public void onUpdate(final EventUpdate event) {
        if (event.isPre()) {
            this.block = 0;
            while (this.block < 45) {
                this.mc.thePlayer.inventoryContainer.getSlot(this.block).getStack();
                ++this.block;
            }
            final int tempSlot = ScaffoldUtils.getBlockSlot();
            if (ScaffoldUtils.invCheck()) {
                for (int i = 9; i < 36; ++i) {
                    final Item item;
                    if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (item = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) instanceof ItemBlock && !Scaffold.blockBlacklist.contains(((ItemBlock)item).getBlock()) && !((ItemBlock)item).getBlock().getLocalizedName().toLowerCase().contains("chest")) {
                        ScaffoldUtils.swap(i, 7);
                        break;
                    }
                }
            }
            this.data = null;
            this.slot = -1;
            if (tempSlot != -1) {
                this.newSlot = ScaffoldUtils.getBlockSlot();
                this.oldSlot = this.mc.thePlayer.inventory.currentItem;
                this.mc.thePlayer.inventory.currentItem = this.newSlot;
                final BlockPos blockBelow1 = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.9, this.mc.thePlayer.posZ);
                this.mc.thePlayer.inventory.currentItem = this.oldSlot;
                if (this.mc.theWorld.getBlockState(blockBelow1).getBlock() == Blocks.air) {
                    this.data = ScaffoldUtils.getBlockData(blockBelow1);
                    this.slot = tempSlot;
                    if (this.data == null) {
                        this.data = ScaffoldUtils.getBlockData(blockBelow1.offsetDown());
                    }
                    if (this.data == null) {
                        return;
                    }
                    final Vec3d vec = new Vec3d(blockBelow1);
                    final float[] rots = RotationUtils.getRotationFromPosition(this.data.position.getX(), this.data.position.getY(), this.data.position.getZ());
                    this.mc.thePlayer.rotationYawHead = rots[0];
                    this.mc.thePlayer.rotationPitchHead = rots[1];
                    this.mc.thePlayer.renderYawOffset = rots[0];
                    event.setYaw(rots[0]);
                    event.setPitch(rots[1]);
                }
            }
        }
        else {
            if (this.data != null && this.mc.thePlayer.movementInput.jump) {
                if (!this.timer.delay(45.0f)) {
                    return;
                }
            }
            else if (!this.timer.delay(85.0f) || this.slot == -1) {
                return;
            }
            if (this.data == null || this.data.position == null || this.data.face == null) {
                return;
            }
            this.mc.thePlayer.inventory.currentItem = this.newSlot;
            final double randomValue = RandomUtils.nextDouble(0.4049292123, 0.41149292123);
            final EnumFacing[] arrayOfEnumFacing;
            final int length = (arrayOfEnumFacing = EnumFacing.values()).length;
            final Vec3d hitVec = new Vec3d(this.data.position).addVector(randomValue, randomValue, randomValue).add(new Vec3d(this.data.face.getDirectionVec()).scale(randomValue));
            final Vec3d poop = ScaffoldUtils.getVec3d(this.data.position, this.data.face);
            if (this.mc.playerController.placeBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventoryContainer.getSlot(36 + this.slot).getStack(), this.data.position, this.data.face, new Vec3(poop.xCoord, poop.yCoord, poop.zCoord)) && this.mc.thePlayer.movementInput.jump && !this.mc.thePlayer.isMoving() && !this.mc.thePlayer.isPotionActive(Potion.jump)) {
                this.mc.thePlayer.motionY = 0.42;
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            this.mc.thePlayer.inventory.currentItem = this.oldSlot;
            this.timer.reset();
        }
    }
    
    static {
        Scaffold.blockBlacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.tnt, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.sand, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table, Blocks.red_flower, Blocks.double_plant, Blocks.yellow_flower);
    }
}
