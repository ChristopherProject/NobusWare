package it.nobusware.client.commands;

import org.lwjgl.input.Keyboard;

import it.nobusware.client.manager.Command;
import it.nobusware.client.manager.Module;
import it.nobusware.client.manager.ModuleManager;
import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class Bind extends Command {
  public static int Killaura;
  
  public String getAlias() {
    return "bind";
  }
  
  public String getDescription() {
    return "Imposta Il Modulo Su Un Tasto.";
  }
  
  public String getSyntax() {
    return ".bind <Modulo> <Tasto>";
  }
  
  public void onCommand(String command, String[] args) throws Exception {
    if (command.isEmpty()) {
    	ChatUtils.print("§cFormato errato, formato: "+ getSyntax());
    } 
    String mod = args[0];
    int tasto = Keyboard.getKeyIndex(args[1].toUpperCase());
    for (Module m : Minecraft.getMinecraft().getNobita().getModManager().getHackDelClient()) {
      if (m.getNome_mod().equalsIgnoreCase(mod)) {
        m.setTasto(tasto);
        ChatUtils.print(String.valueOf(String.valueOf(m.getNome_mod())) + " configurato su " + Keyboard.getKeyName(tasto));
        return;
      } 
    } 
  }
}
