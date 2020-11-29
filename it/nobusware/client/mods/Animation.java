package it.nobusware.client.mods;

import java.util.function.Consumer;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventUpdate;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.EnumValue;

public class Animation extends Module {

	private EnumValue<Mode> mode = new EnumValue("Mode", Mode.SIDE);
	public static int anim;

	public Animation(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		addValues(new Value[] { this.mode });
	}
	
	//il metodo in cui si va  a mettere � ItemRenderer.renderItemInFirstPerson()
    
	@Handler
    public Consumer<EventUpdate> onEvent = (event) -> {
    	if (this.mode.getValue() == Mode.SIDE) {
    		this.anim = 1;
    	}
    	else if (this.mode.getValue() == Mode.SLAPPED) {
    		this.anim = 2;
    	}
    	else if (this.mode.getValue() == Mode.VANILLA) {
    		this.anim = 3;
    	}
    	else if (this.mode.getValue() == Mode.OLD_BLOCK) {
    		this.anim = 4;
    	}
      	else if (this.mode.getValue() == Mode.ciqui) {
    		this.anim = 5;
    	}
      	else if (this.mode.getValue() == Mode.sei) {
    		this.anim = 6;
    	}
      	else if (this.mode.getValue() == Mode.sette) {
    		this.anim = 7;
    	}
    };

	private enum Mode {
		SIDE, SLAPPED, VANILLA, OLD_BLOCK, ciqui, sei, sette
	}
}