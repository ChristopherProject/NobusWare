package it.nobusware.client.render.screens.guiscreen;

import org.lwjgl.input.Keyboard;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.render.screens.guiscreen.obj.GuiModules;

public class ClickGUI extends Module {

	public ClickGUI(String nome_mod, int tasto, String nome_array_printed, Category categoria, boolean hasSettings) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	public void Abilitato() {
		mc.displayGuiScreen(new GuiModules());
	}

	@Handler
	public void onUpdate(final EventUpdate e) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			this.setAbilitato(false);
		}
	}
}
