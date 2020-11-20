package it.nobusware.client.render.screens.guiscreen.obj;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import it.nobusware.client.manager.Module;
import it.nobusware.client.manager.Module.Category;
import it.nobusware.client.render.screens.guiscreen.ClickGUI;
import it.nobusware.client.utils.ColorUtilsArray;
import it.nobusware.client.utils.ModuleUtils;
import it.nobusware.client.utils.RenderUtils;
import it.nobusware.client.utils.fontmanager.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class Frame{
	
	private final static Minecraft mc = Minecraft.getMinecraft();
	private final static FontRenderer f = mc.fontRendererObj;
	
	private Category cat;
	private float x;
	private float y;
	private ArrayList<ModuleButton> buttons = new ArrayList();
	
	private static int altezza1 = 90;
	//10 *2
	private static int altezza2 = 115;
	
	public Frame(Category cat, float x, float y){
		this.cat = cat;
		this.x = x;
		this.y = y;
		int yOff = 30;
		int xxOff = 10;
		for(Module m : ModuleUtils.getModsByCat(cat)){
			if(!(m instanceof ClickGUI)){
				buttons.add(new ModuleButton(m, x, y + yOff, 60, 18));
				yOff += 15;
			}
		}
	}

	public void draw(boolean updateBtn){
		int mouseX = getMouseX();
		int mouseY = getMouseY();
		String text = ModuleUtils.getLongName(cat).getNome_mod();
		//rendering
		UnicodeFontRenderer font = Minecraft.getMinecraft().getNobita().getFontManager().VERDANA25;
		RenderUtils.drawImage(new ResourceLocation("nobita/frame.png"),(int) x - 73, (int)y - 20 , 148, 218);
		final int rbw = 0xff67ECC3;
		if (String.valueOf(cat.name()).equals(Category.Combatti.toString())) {
			font.drawString(cat.toString(), x - 10 - f.getStringWidth(cat.toString()) / 2, y - 2, rbw);	
		}
		if (String.valueOf(cat.name()).equals(Category.Movimenti.toString())) {
			font.drawString(cat.toString(), x - 10 - f.getStringWidth(cat.toString()) / 2, y - 2, rbw);	
		}
		if (String.valueOf(cat.name()).equals(Category.Exploits.toString())) {
			font.drawString(cat.toString(), x - 4 - f.getStringWidth(cat.toString()) / 2, y - 2, rbw);	
		}
		if (String.valueOf(cat.name()).equals(Category.Rendering.toString())) {
			font.drawString(cat.toString(), x - 7 - f.getStringWidth(cat.toString()) / 2, y - 2, rbw);	
		}
		
		
		
		for(ModuleButton b : buttons){
			b.draw(mouseX, mouseY);
			b.onUpdate(updateBtn, mouseX, mouseY);
		}
	}
	
	public float getX(){
		return x;
	}

	public void setX(float x){
		this.x = x;
		for(ModuleButton b : buttons){
			b.setX(x);
		}
	}

	public float getY(){
		return y;
	}

	public void setY(float y){
		this.y = y;
		int yOff = 30;
		for(ModuleButton b : buttons){
			b.setY(y + yOff);
			yOff += 15;
		}
	}

	public Category getCat(){
		return cat;
	}

	public ArrayList<ModuleButton> getButtons(){
		return buttons;
	}
	
	//fan altamente laggare
	public static int getMouseX(){
		return Mouse.getX() * GuiScreen.width / mc.displayWidth;
 	}
	
	public static int getMouseY(){
		return GuiScreen.height - Mouse.getY() * GuiScreen.height / mc.displayHeight - 1;
 	}
}
