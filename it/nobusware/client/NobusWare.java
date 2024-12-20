package it.nobusware.client;

import java.io.File;
import java.io.FileWriter;

import org.lwjgl.opengl.Display;

import com.github.creeper123123321.viafabric.ViaFabric;

import it.nobusware.client.config.ConfigManager;
import it.nobusware.client.discord.DiscordAgent;
import it.nobusware.client.irc.IrcManager;
import it.nobusware.client.manager.CManager;
import it.nobusware.client.manager.ModuleManager;
import it.nobusware.client.render.GuiNobita;
import it.nobusware.client.render.clickgui.ClickScreen;
import it.nobusware.client.render.screens.newaltmanager.NewAllInOneAltManager;
import it.nobusware.client.utils.ServerOnline;
import it.nobusware.client.utils.files.FileUtils;
import it.nobusware.client.utils.files.allfiles.Keybinds;
import it.nobusware.client.utils.files.allfiles.ToggledMods;
import it.nobusware.client.utils.fontmanager.FontManager;
import net.minecraft.client.Minecraft;

public class NobusWare {

	private final String Autore = "AdrianCode, FakeException";

	private static final it.nobusware.client.render.clickgui.ClickScreen guiClick = new it.nobusware.client.render.clickgui.ClickScreen();

	private GuiNobita nobiGui;
	public static File nobitaDir;
	private File NobusWareDir;
	private ModuleManager modManager;
	private FontManager fontManager;
	private CManager commandManager;
	private ConfigManager configManager;
	private NewAllInOneAltManager accountManager;
	private ServerOnline server;
	private static DiscordAgent discord = new DiscordAgent();

	public static String primColor = "�a";
	public static String secColor = "�f";
	
	public static IrcManager irc;

	public void pre() {
		System.out.println("Avvio NobusWare Client");
		System.out.println("Try To Inizialize Fabric..");
        final ViaFabric viaFabric = new ViaFabric();
        viaFabric.onInitialize();
        System.out.println("Fabric Loaded");
	}
	
	public void Avvio() {
		connectToIRC();
		Display.setTitle("NobusWare Client 1.8.x");
		this.nobiGui = new GuiNobita(Minecraft.getMinecraft());
		this.modManager = new ModuleManager();
		this.fontManager = new FontManager();
		this.commandManager = new CManager();
		nobitaDir = new File((Minecraft.getMinecraft()).mcDataDir + File.separator + "NobusWare");
		if (!nobitaDir.exists())
			nobitaDir.mkdirs();
		NobusWareDir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "NobusWare" + File.separator + "NobusWareAI");
		if (!NobusWareDir.exists())
			NobusWareDir.mkdirs();
	    FileUtils.createFile("keybinds");
	    FileUtils.createFile("toggledmods");
	    FileUtils.createFileAI("username");
	    this.configManager = new ConfigManager(new File(nobitaDir, "configs"));
	    this.configManager.load();
		File file_1 = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "NobusWare" + File.separator + "NobusWareAI" + File.separator + "username.txt");
		if (file_1.exists()) {
			System.out.println("Alias File [Size] -> " + file_1.length());
			if (file_1.length() <= 2) {
				System.out.println("This is Empty File!!");
				try {
					System.out.println("Try To Write Aliases");
					FileWriter myWriter = new FileWriter(file_1);
					myWriter.write(System.getenv().get("USERNAME"));
					myWriter.close();
					System.out.println("Write Alias In File ;)");
				} catch (Exception e) {
					System.out.println("an error occuried" + e.getMessage());
				}
			} else {
				System.out.println("Loaded Alias File.");
			}
		} else {
			System.out.println("Alias File Not Found.");
		}
	    Keybinds.setupBinds();
	    Keybinds.bindKeys();
	    ToggledMods.setupBinds();
	}
	
	public static void connectToIRC() {
		irc = new IrcManager(Minecraft.getMinecraft().session.getUsername());
		irc.connect();
		if (irc.isConnected()) {
			System.out.println("Connected To IRC.");
		} else {
			System.out.println("Error 404 Not Found.");
		}
	}
	
	public void StopClient() {
		System.out.println("Client Stopped");
	}

	public static ClickScreen getGuiClick() {
		return guiClick;
	}
	
	public DiscordAgent getDiscord() {
		return discord;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}

	public GuiNobita getNobiGui() {
		return nobiGui;
	}

	public NewAllInOneAltManager getAccountManager() {
		return accountManager;
	}

	public CManager getCommandManager() {
		return commandManager;
	}

	public FontManager getFontManager() {
		return fontManager;
	}

	public ModuleManager getModManager() {
		return modManager;
	}

	public ServerOnline getServer() {
		return server;
	}
}
