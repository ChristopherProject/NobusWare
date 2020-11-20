package it.nobusware.client.mods;

import java.util.ArrayList;
import java.util.List;

import it.nobusware.client.manager.Module;

public class Xray extends Module {

	private static List<Integer> blocks = new ArrayList<Integer>();

	public Xray(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
		blocks.add(16);
		blocks.add(56);
		blocks.add(14);
		blocks.add(15);
		blocks.add(129);
		blocks.add(73);
	}

	public static boolean enabled;
	public float oldgamma;

	@Override
	public void Abilitato() {
		this.enabled = true;
		this.oldgamma = this.mc.gameSettings.gammaSetting;
		this.mc.gameSettings.gammaSetting = 10.0f;
		this.mc.gameSettings.ambientOcclusion = 0;
		this.mc.renderGlobal.loadRenderers();
		super.Abilitato();
	}

	@Override
	public void Disabilitato() {
		this.enabled = false;
		this.mc.gameSettings.gammaSetting = this.oldgamma;
		this.mc.gameSettings.ambientOcclusion = 1;
		this.mc.renderGlobal.loadRenderers();
		super.Disabilitato();
	}

	public static List<Integer> getBlocks() {
		return blocks;
	}
}
