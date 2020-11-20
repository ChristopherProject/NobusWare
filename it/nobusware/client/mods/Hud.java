package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventRendererGUI2D;
import it.nobusware.client.events.KeyPressedEvent;
import it.nobusware.client.manager.Module;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.mods.aura.modes.Single;
import it.nobusware.client.render.tabgui.TabGui;
import it.nobusware.client.render.tabgui.TabguiItem;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;

public class Hud extends Module {

	static TabGui tab = new TabGui();
	
	public Hud(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}
	
	@Handler
	public void onDrawHud(final EventRendererGUI2D e) {
		tab.drawGui(4, 25);
	}
}
