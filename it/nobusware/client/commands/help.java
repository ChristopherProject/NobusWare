package it.nobusware.client.commands;

import it.nobusware.client.manager.Command;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;

public class help extends Command {
	
	  public static Minecraft mc = Minecraft.getMinecraft();
	  
	  public String getAlias() {
	    return "help";
	  }
	  
	  public String getDescription() {
	    return "Mostra La Lista Comandi";
	  }
	  
	  public String getSyntax() {
	    return ".help";
	  }
	  
	  public void onCommand(String command, String[] args) throws Exception {
		  for(Command com : mc.getNobita().getCommandManager().getCommands()) {
			  ChatUtils.print("[C] " + com.getAlias() + " - " + com.getDescription());
		  }
	  }
}
