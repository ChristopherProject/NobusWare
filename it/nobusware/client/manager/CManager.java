package it.nobusware.client.manager;

import it.nobusware.client.commands.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;

public class CManager {
  private static CManager theCommandManager = new CManager();
  
  private ArrayList<Command> commands;
  
  public static CManager getInstance() {
    return theCommandManager;
  }
  
  private Command[] commands() {
    return new Command[] { 
    		new Bind(),
    		new Vclip(),
    		new help(),
    		new youtube(),
    		new verusfucker(),
    		new  BKick(),
    		new byemom(),
    		new settings(),
            new KickAll()
    };
  }
  
  public CManager() {
    this.commands = new ArrayList<>();
    Command[] arrayOfCommand;
    for (int j = (arrayOfCommand = commands()).length, i = 0; i < j; i++) {
      Command c = arrayOfCommand[i];
      if (c != null)
        this.commands.add(c); 
    } 
  }
  
  public void callCommand(String input) {
    String[] split = input.split(" ");
    String command = split[0];
    String args = input.substring(command.length()).trim();
    for (Command c : getCommands()) {
      if (c.getAlias().equalsIgnoreCase(command)) {
        try {
          c.onCommand(args, args.split(" "));
        } catch (Exception exception) {}
        return;
      } 
    } 
    Minecraft.getMinecraft();
    Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("�cComando Sconosciuto�c."));
  }
  
  public ArrayList<Command> getCommands() {
    return this.commands;
  }
}
