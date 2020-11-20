package it.nobusware.client.commands;

import it.nobusware.client.manager.Command;
import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.DoxUtils;

public class byemom  extends Command{

	@Override
	public String getAlias() {
		return "byemom";
	}

	@Override
	public String getDescription() {
		return "this is a meme xd";
	}

	@Override
	public String getSyntax() {
		return ".byemom <username>";
	}

	@Override
	public void onCommand(String comando, String[] args) throws Exception {
		if(args[0].isEmpty()) {
			ChatUtils.print("Bye Mooooom");
		}else {
			String username = args[0];
			DoxUtils.onSearch(username);
		}
	}
}