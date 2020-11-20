package it.nobusware.client.commands;

import it.nobusware.client.manager.Command;
import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.test.Player;
import it.nobusware.client.utils.test.ass;
import net.minecraft.client.Minecraft;

public class youtube extends Command {

	public String getAlias() {
		return "youtube";
	}

	public String getDescription() {
		return "Riproduce Canzoni Da Youtube";
	}

	public String getSyntax() {
		return ".youtube <Play/Stop>";
	}

	public void onCommand(String command, String[] args) throws Exception {
		if (command.isEmpty()) {
			ChatUtils.print("§cFormato errato, formato: " + getSyntax());
		}
//		String play_or_not = args[0];
//		String song = args[1];
//		if (song == null) {
//			ChatUtils.print(".youtube play <SongName>");
//			return;
//		}
//		if (play_or_not.equalsIgnoreCase("Play") || song != null) {
//			ass.diocane(song);
//		} 
//		if (play_or_not.equalsIgnoreCase("Stop")) {
//			Player.stop();
//		} 


		else if (!command.isEmpty()) {
			if (args[0].equalsIgnoreCase("play")) {
				if (args[1].isEmpty()) {
					ChatUtils.print(".youtube play <SongName>");
				} else {
					if (!args[1].isEmpty()) {
						String par2 = args[1];
						ass.diocane(par2);
						
					}
				}
			} else if (args[0].equalsIgnoreCase("Stop")) {
				Player.stop();
				ChatUtils.print("stopped");
			}
		}
	}
}
