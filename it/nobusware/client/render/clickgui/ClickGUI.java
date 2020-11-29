package it.nobusware.client.render.clickgui;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.KeyPressedEvent;
import it.nobusware.client.manager.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ClickGUI extends Module {

	private ClickScreen clickgui;

	public ClickGUI(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	public void Abilitato() {
		if (this.clickgui == null) {
			this.clickgui = new ClickScreen();
			this.clickgui.init();
		}
		Minecraft.getMinecraft().displayGuiScreen(this.clickgui);
		setAbilitato(false);
	}
}
