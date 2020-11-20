package it.nobusware.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils{
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static void print(String text){
		mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("§7[§a§lNobusWare§7] "  + text).setChatStyle((new ChatStyle().setColor(EnumChatFormatting.GRAY))));
    }
	
	public static String sendBypassedString(String text){
		String finalText = ""; 
        for(int i = 0; i < text.length(); i++){
			finalText += "⛄" + text.charAt(i) + "⛄";
		}
        return finalText;
	}
}
