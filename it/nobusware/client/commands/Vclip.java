package it.nobusware.client.commands;

import it.nobusware.client.manager.Command;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class Vclip extends Command {
	
  public static Minecraft mc = Minecraft.getMinecraft();
  
  public String getAlias() {
    return "vclip";
  }
  
  public String getDescription() {
    return "Ti Teletrasporta Sopra o Sotto.";
  }
  
  public String getSyntax() {
    return ".vclip <blocks>";
  }
  
  public void onCommand(String command, String[] args) throws Exception {
    if (command.isEmpty()) {
    	ChatUtils.print("§cFormato errato, formato: "+ getSyntax());
    } 
    String blocks = args[0];
    int blocksint = Integer.parseInt(blocks);
    Minecraft.getMinecraft().thePlayer.func_174826_a(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, blocksint, 0.0D));
    ChatUtils.print("Teleported");
  }
}
