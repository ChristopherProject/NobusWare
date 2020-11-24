package it.nobusware.client.manager;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import it.nobusware.client.manager.Module.Category;
import it.nobusware.client.mods.AntiBot;
import it.nobusware.client.mods.AntiCactus;
import it.nobusware.client.mods.AntiVPN;
import it.nobusware.client.mods.Authme;
import it.nobusware.client.mods.AutoArmor;
import it.nobusware.client.mods.AutoClicker;
import it.nobusware.client.mods.AutoSoup;
import it.nobusware.client.mods.BPacketReader;
import it.nobusware.client.mods.BanExploit;
import it.nobusware.client.mods.CazzoDuro;
import it.nobusware.client.mods.Chams;
import it.nobusware.client.mods.Criticals;
import it.nobusware.client.mods.Disabler;
import it.nobusware.client.mods.DragChat;
import it.nobusware.client.mods.DrawSexyAnime;
import it.nobusware.client.mods.EntityESP;
import it.nobusware.client.mods.EverKick;
import it.nobusware.client.mods.FindPacketLog;
import it.nobusware.client.mods.Flight;
import it.nobusware.client.mods.ForwardCommandAbuse;
import it.nobusware.client.mods.FreeParolaccie;
import it.nobusware.client.mods.FullBright;
import it.nobusware.client.mods.GodMode;
import it.nobusware.client.mods.Hud;
import it.nobusware.client.mods.IstantUse;
import it.nobusware.client.mods.NameProtect;
import it.nobusware.client.mods.NameTags;
import it.nobusware.client.mods.NoFall;
import it.nobusware.client.mods.PacketSniffer;
import it.nobusware.client.mods.Phase;
import it.nobusware.client.mods.Rotate;
import it.nobusware.client.mods.SkinSpammer;
import it.nobusware.client.mods.Speed;
import it.nobusware.client.mods.Step;
import it.nobusware.client.mods.StupidFlood;
import it.nobusware.client.mods.Velocity;
import it.nobusware.client.mods.VerusFloat;
import it.nobusware.client.mods.aura.killaura;
import it.nobusware.client.mods.aura.modes.Verus;
import it.nobusware.client.mods.crashers.CrasherA;
import it.nobusware.client.mods.crashers.CrasherB;
import it.nobusware.client.mods.crashers.CrasherC;
import it.nobusware.client.mods.crashers.CrasherE;
import it.nobusware.client.mods.crashers.CrasherF;
import it.nobusware.client.mods.crashers.CrasherI;
import it.nobusware.client.mods.crashers.CrasherL;
import it.nobusware.client.mods.crashers.CrasherV;
import it.nobusware.client.mods.crashers.CrasherZ;
import it.nobusware.client.render.clickgui.ClickGUI;

public class ModuleManager {
	
	public static final ArrayList <Module> Hack_Del_Client = new ArrayList();

	public ModuleManager() {
		Aggiungi(new AntiCactus("AntiCactus", 0, "AntiCactus", Category.Giocatore));
		Aggiungi(new AntiVPN("AntiVPN", 0, "AntiVPN", Category.Exploits));
		Aggiungi(new Step("Step", 0, "Step", Category.Movimenti));
		Aggiungi(new Chams("Chams", 0, "Chams", Category.Rendering));
		Aggiungi(new AntiBot("AntiBot", 0, "AntiBot", Category.Combatti));
		Aggiungi(new Criticals("Criticals", 0, "Criticals", Category.Combatti));
		Aggiungi(new NoFall("NoFall", 0, "NoFall", Category.Movimenti));
		Aggiungi(new Rotate("Rotation", 0, "Rotation", Category.Funny));
		Aggiungi(new AutoArmor("AutoArmor", 0, "AutoArmor", Category.Combatti));
		Aggiungi(new NameTags("NameTags", 0, "NameTags", Category.Rendering));
		Aggiungi(new BPacketReader("BPacketReader", 0, "BungeePacketReader", Category.Exploits));
		Aggiungi(new PacketSniffer("GPacketReader", 0, "GamePacketReader", Category.Exploits));
		Aggiungi(new EntityESP("EntityESP", 0, "EntityESP", Category.Rendering));
		Aggiungi(new Authme("Authme", 0, "Authme", Category.Exploits));
		Aggiungi(new CazzoDuro("CazzoDuro", 0, "CazzoDuro", Category.Funny));
		Aggiungi(new DrawSexyAnime("AnimeSkin", 0, "AnimeSkin", Category.Funny));
		Aggiungi(new Velocity("Velocity", 0, "Velocity", Category.Combatti));
		Aggiungi(new GodMode("GodMode", 0, "GodMode", Category.Exploits));
		Aggiungi(new Speed("Speed", Keyboard.KEY_X, "Speed", Category.Movimenti));
		Aggiungi(new Phase("Phase", 0, "Phase", Category.Movimenti));
		Aggiungi(new Flight("Flight", Keyboard.KEY_F, "Flight", Category.Movimenti));
		Aggiungi(new killaura("Killaura", Keyboard.KEY_R, "Killaura", Category.Combatti));
		Aggiungi(new AutoSoup("AutoSoup", 0, "AutoSoup", Category.Combatti));
		Aggiungi(new ClickGUI("ClickScreen", Keyboard.KEY_RSHIFT, "", Category.Rendering));
		Aggiungi(new IstantUse("IstantUse", 0, "IstantUse", Category.Giocatore));
		Aggiungi(new DragChat("CostumChat", 0, "", Category.Rendering));
		Aggiungi(new BanExploit("VerusBungee", 0, "VerusBungeeExploit", Category.Exploits));
		Aggiungi(new ForwardCommandAbuse("ForwardAbuse", 0, "ForwardCommandAbuse", Category.Exploits));
		Aggiungi(new EverKick("EverKick", 0, "EverKicked", Category.Exploits));
		Aggiungi(new AutoClicker("AutoClick", 0, "AutoClicker", Category.Combatti));
		Aggiungi(new CrasherA("CrasherA", 0, "CrasherA", Category.Crashers));
		Aggiungi(new CrasherB("CrasherB", 0, "CrasherB", Category.Crashers));
		Aggiungi(new CrasherE("CrasherE", 0, "CrasherE", Category.Crashers));
		Aggiungi(new CrasherC("CrasherC", 0, "CrasherC", Category.Crashers));
		Aggiungi(new CrasherF("CrasherF", 0, "CrasherF", Category.Crashers));
		Aggiungi(new CrasherI("CrasherI", 0, "CrasherI", Category.Crashers));
		Aggiungi(new CrasherL("CrasherL", 0, "CrasherL", Category.Crashers));
		Aggiungi(new CrasherV("CrasherV", 0, "CrasherV", Category.Crashers));
		Aggiungi(new CrasherZ("CrasherZ", 0, "CrasherZ", Category.Crashers));
		Aggiungi(new StupidFlood("StupidFlood", 0, "StupidFlood", Category.Funny));
		Aggiungi(new SkinSpammer("SkinSpammer", 0, "SkinBroadCastSpammer", Category.Exploits));
		Aggiungi(new FindPacketLog("FindPacketLog", 0, "FindPacketLog", Category.Exploits));
		Aggiungi(new FreeParolaccie("AntiChatCensored", 0, "AntiChatCensored", Category.Funny));
		Aggiungi(new NameProtect("NameProtect", 0, "NameProtect", Category.Giocatore));
		Aggiungi(new VerusFloat("VerusFloat", 0, "VerusFloat", Category.Giocatore));
		Aggiungi(new FullBright("FullBright", 0, "FullBright", Category.Rendering));
		Aggiungi(new Disabler("Disabler", 0, "Disabler", Category.Exploits));
		Aggiungi(new Hud("Hud", 0, "", Category.Rendering));
		System.out.println("Loaded " + this.getHackDelClient().size() + " Modules.");
	}
	
	static void Aggiungi(Module hack_da_aggiungere_alla_lista) {
		getHackDelClient().add(hack_da_aggiungere_alla_lista);
	}
	
	public Module Prendi(final Class moduleClass) {
		for (final Module m : Hack_Del_Client) {
			if (m.getClass() != moduleClass) {
				continue;
			}
			return m;
		}
		return null;
	}
	
	
	  public Module getModByName(String name) {
		    for (Module module : Hack_Del_Client) {
		      if (module.getNome_mod().trim().equalsIgnoreCase(name.trim()))
		        return module; 
		    } 
		    return null;
		  }
	  
	public List<Module> getModules(Category category) {
		List<Module> l = new ArrayList<>();
		for (Module m : this.Hack_Del_Client) {
			if (m.getCategoria() == category)
				l.add(m);
		}
		return l;
	}
	
	public static ArrayList<Module> getHackDelClient() {
		return Hack_Del_Client;
	} 
}