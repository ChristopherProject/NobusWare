package it.nobusware.client.commands;

import java.io.IOException;

import ch.qos.logback.core.net.server.Client;
import it.nobusware.client.config.Config;
import it.nobusware.client.manager.Command;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;

public class settings extends Command {

	@Override
	public String getAlias() {
		return "config";
	}

	@Override
	public String getDescription() {
		return "Config System Della Client";
	}

	@Override
	public String getSyntax() {
		return ".config <help>";
	}

	@Override
	public void onCommand(String comando, String[] s) throws Exception {
	    if (comando.isEmpty() || s.length == 0) {
	    	ChatUtils.print("§econfig list - Mostra La Lista.");
			ChatUtils.print("§econfig create name - Crea un Config.");
			ChatUtils.print("§econfig delete name - Cancella Un Config.");
			ChatUtils.print("§econfig reload. - Ricarica Tutti i Config");
			ChatUtils.print("§econfig clear. - Cancella Tutti I Config");
	    	return;
	    }else {
	    	switch (s[0]) {
			case "list":
				if (!Minecraft.getMinecraft().getNobita().getConfigManager().getConfigs().isEmpty()) {
					ChatUtils.print("Config Disponibili:");
					Minecraft.getMinecraft().getNobita().getConfigManager().getConfigs().forEach(cfg -> ChatUtils.print(cfg.getName()));
					break;
				}
				ChatUtils.print("Non Hai Config Salvati!");
				break;
			case "create":
				if (!Minecraft.getMinecraft().getNobita().getConfigManager().isConfig(s[1])) {
					Minecraft.getMinecraft().getNobita().getConfigManager().saveConfig(s[1], (s.length > 2 && s[2].equalsIgnoreCase("keys")));
					Minecraft.getMinecraft().getNobita().getConfigManager().getConfigs().add(new Config(s[1]));
					ChatUtils.print("Creato Il Config " + s[1] + ((s.length > 2 && s[2].equalsIgnoreCase("keys")) ? " Con i Bind Inclusi" : "") + ".");
					break;
				}
				ChatUtils.print("Esiste il Config " + s[1] + " §cNot Created.");
				break;
			case "delete":
				if (Minecraft.getMinecraft().getNobita().getConfigManager().isConfig(s[1])) {
					Minecraft.getMinecraft().getNobita().getConfigManager().deleteConfig(s[1]);
					ChatUtils.print("§cConfig §b" + s[1] + " §eCancellato.");
					break;
				}
				ChatUtils.print("§eImpossibile Cancellare il Config §b" + s[1]);
				break;
			case "reload":
				Minecraft.getMinecraft().getNobita().getConfigManager().getConfigs().clear();
				Minecraft.getMinecraft().getNobita().getConfigManager().load();
				ChatUtils.print("§aTutti i " + Minecraft.getMinecraft().getNobita().getConfigManager().getConfigs().size() + " Sono Stati Ricaricati.");
				break;
			case "clear":
				try {
					if (!Minecraft.getMinecraft().getNobita().getConfigManager().getConfigs().isEmpty()) {
						Minecraft.getMinecraft().getNobita().getConfigManager().clear();
						Minecraft.getMinecraft().getNobita().getConfigManager().getConfigs().clear();
						ChatUtils.print("§4Ho Cancellato Tutti i Config.");
						break;
					}
					ChatUtils.print("§cNon Hai Config Salvati.");
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "load":
				if (Minecraft.getMinecraft().getNobita().getConfigManager().isConfig(s[1])) {
					Minecraft.getMinecraft().getNobita().getConfigManager().loadConfig(s[1]);
					ChatUtils.print("§b"+ s[1] + " §eCaricato.");
					break;
				}
				ChatUtils.print("§eImpossibile Caricare il Config §b" + s[1]);
				break;
			}
		}
	}
}