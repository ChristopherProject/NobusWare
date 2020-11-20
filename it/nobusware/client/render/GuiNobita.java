package it.nobusware.client.render;

import org.newdawn.slick.Color;

import it.nobusware.client.utils.ArrayListUtil;
import it.nobusware.client.utils.RenderUtils;
import it.nobusware.client.utils.fontmanager.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiNobita extends GuiIngame{

	public GuiNobita(Minecraft mcIn) {
		super(mcIn);
	}

	 public void NobitaRendering() {
		 ScaledResolution res = RenderUtils.getResolution();
		UnicodeFontRenderer font = Minecraft.getMinecraft().getNobita().getFontManager().VERDANA30;
		UnicodeFontRenderer small_font = Minecraft.getMinecraft().getNobita().getFontManager().VERDANA10;
		font.drawString("NobusWare", 4, 2, 0xff67ECC3);
		small_font.drawString("Copyright By AdrianCode", 5, 18, 0xff67ECC3);
		 ArrayListUtil.drawModuleNames(new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight));
			Minecraft.getMinecraft().getNobita().getModManager().getHackDelClient().sort((mod, mod1) -> {
				final String name = mod.getNome_array_printed();
				final String name2 = mod1.getNome_array_printed();
				if (font.getStringWidth(name) < font.getStringWidth(name2)) {
					return 1;
				} else if (font.getStringWidth(name) > font.getStringWidth(name2)) {
					return -1;
				} else {
					return 0;
				}
			});
	 }
}
