package it.nobusware.client.mods;

import it.nobusware.client.manager.Module;

public class AntiCactus extends Module {

	public AntiCactus(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isCollided;
	
	public void Abilitato() {
		isCollided = true;
	}
	

	public void Disabilitato() {
		isCollided = false;
	}

}
