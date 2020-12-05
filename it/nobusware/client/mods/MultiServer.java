package it.nobusware.client.mods;

import it.nobusware.client.manager.Module;
import net.minecraft.client.gui.GuiMultiplayer;

public class MultiServer extends Module {

    public MultiServer(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
        super(nome_mod, tasto, nome_array_printed, categoria);
    }

    @Override
    public void Abilitato() {
        mc.displayGuiScreen(new GuiMultiplayer(null));
        toggle();
    }
}
