package it.nobusware.client.AI;

import it.nobusware.client.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class NobusWareMessageAI {
	
	public static void send(String text){
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§7[§bNobusWareAI§7] §e"  + text));
    }
}
