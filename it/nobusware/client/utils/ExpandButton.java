package it.nobusware.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class ExpandButton extends GuiButton {
	
	public int darkBlue = 0xff009ef9;
	public int lightBlue = 0xff16c2f7;
	private int x;
	private int y;
	private int x1;
	private int y1;
	int alphaInc;
	int alpha;
	int size;

	public ExpandButton(int par1, int par2, int par3, int par4, int par5, String par6Str){
		super(par1, par2, par3, par4, par5, par6Str);
		this.alphaInc = 100;
		this.alpha = 0;
		this.size = 0;
		this.x = par2;
		this.y = par3;
		this.x1 = par4;
		this.y1 = par5;
	}

	public ExpandButton(int i, int j, int k, String stringParams) {
		this(i, j, k, 200, 20, stringParams);
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean isOverButton = (mouseX >= this.x) && (mouseX <= this.x + this.x1) && (mouseY >= this.y) && (mouseY <= this.y + this.y1);
		if ((isOverButton) && (this.alphaInc <= 150)) {
			this.alphaInc += 5;
		} else if ((!isOverButton) && (this.alphaInc >= 100)) {
			this.alphaInc -= 5;
		}
		if (this.alphaInc > 150) {
			this.alphaInc = 150;
		} else if (this.alphaInc < 100) {
			this.alphaInc = 100;
		}
		if ((isOverButton) && (this.size <= 1)) {
			this.size += 1;
		} else if ((!isOverButton) && (this.size >= 0)) {
			this.size -= 1;
		}
		Gui.drawRect(this.x - this.size, this.y - this.size, this.x + this.x1 + this.size, this.y + this.y1 + this.size,
				darkBlue);
		drawCenteredString(mc.fontRendererObj, this.displayString, this.x + this.x1 / 2, this.y + this.y1 / 2 - 4, -1);
	}
}