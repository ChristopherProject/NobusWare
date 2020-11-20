package it.nobusware.client.utils;

import it.nobusware.client.NobusWare;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.fontmanager.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ArrayListUtil {

	public static int y;

	public static final void drawModuleNames(ScaledResolution scaledResolution) {
		int yPos = 0;
		UnicodeFontRenderer font = Minecraft.getMinecraft().getNobita().getFontManager().VERDANA18;
		for (Module mod : Minecraft.getMinecraft().getNobita().getModManager().getHackDelClient()) {
			if (mod.isAbilitato()) {
				final String name = mod.getNome_array_printed();
				final float xPos = scaledResolution.getScaledWidth() - font.getStringWidth(name);
				final int rbw = ColorUtilsArray.getRainbow(-8000, yPos * -32).getRGB();// 0xff33FFFF; 32
				font.drawStringWithShadow(name, xPos - 2.63f, yPos - 1f, rbw);
				yPos += 13;
				y = yPos;
			}
		}
	}
}
