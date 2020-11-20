package it.nobusware.client.utils;

import java.util.ArrayList;

import it.nobusware.client.manager.Module;
import it.nobusware.client.manager.Module.Category;
import net.minecraft.client.Minecraft;

public class ModuleUtils{
	
	private final static Minecraft mc = Minecraft.getMinecraft();
	
	/** Prende I Moduli Per Categoria **/
	public static ArrayList<Module> getModsByCat(Category cat) {
		ArrayList<Module> mods = new ArrayList<Module>();
		for(Module m : mc.getNobita().getModManager().getHackDelClient()) {
			if(m.getCategoria() == cat){
				mods.add(m);
			}
		}
		return mods;
	}
	
	/** Nome Più Lungo Di Una Certa Categoria **/
	public static Module getLongName(Category cat){
		Module m = null;
		ArrayList<Module> mods = getModsByCat(cat);
		int max = 0;
		for(Module m2 : mods){
			if(max < m2.getNome_mod().length()){
				max = m2.getNome_mod().length();
				m = m2;
			}
		}
		return m;
	}
}
