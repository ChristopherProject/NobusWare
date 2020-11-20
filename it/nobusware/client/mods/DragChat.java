package it.nobusware.client.mods;

import it.nobusware.client.manager.Module;
import it.nobusware.client.render.override.GuiNewChatOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;

public class DragChat extends Module {

	
	public DragChat(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}


	public void Abilitato() {
		 Minecraft.getMinecraft().ingameGUI.persistantChatGUI = (GuiNewChat)new GuiNewChatOverride(Minecraft.getMinecraft());
	}
	

	public void Disabilitato() {
		 Minecraft.getMinecraft().ingameGUI.persistantChatGUI = new GuiNewChat(Minecraft.getMinecraft());
	}

}
