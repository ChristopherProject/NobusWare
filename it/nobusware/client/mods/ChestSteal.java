package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S30PacketWindowItems;

public class ChestSteal extends Module {

	public ChestSteal(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	public static S30PacketWindowItems packet;
	int delay;
	
	@Handler
	public void onUpdate(EventUpdate event) {
		this.delay++;
		if (this.mc.currentScreen instanceof GuiChest) {
			GuiChest chest = (GuiChest) this.mc.currentScreen;
			if (isChestEmpty(chest)) {
				this.mc.thePlayer.closeScreen();
				packet = null;
			}
			for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); index++) {
				ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
				if (stack != null && this.delay > 4.8D) {
					this.mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, (EntityPlayer) this.mc.thePlayer);
					this.delay = 0;
				}
			}
		}
	}
	  
	private boolean isChestEmpty(GuiChest chest) {
		for (int index = 0; index <= chest.lowerChestInventory.getSizeInventory(); index++) {
			ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
			if (stack != null)
				return false;
		}
		return true;
	}
}