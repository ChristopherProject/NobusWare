package it.nobusware.client.render.screens.guiscreen.obj;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.fontmanager.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class ModuleButton{
	
	private final static Minecraft mc = Minecraft.getMinecraft();
	private final static FontRenderer f = mc.fontRendererObj;
	
	private int toggled = 0;
	private float x;
	private float y;
	private float width;
	private float height;
	private Module mod;

	public ModuleButton(Module mod, float x, float y, float width, float height){
		this.mod = mod;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw(int mouseX, int mouseY){
		String text = mod.getNome_mod();
		UnicodeFontRenderer font = Minecraft.getMinecraft().getNobita().getFontManager().VERDANA20;
		
		//Gui.drawRect(x - width / 2, y - height / 2, x + width / 2, y + height / 2, getHovered(mouseX, mouseY) ? Color.gray.getRGB() : Color.DARK_GRAY.getRGB());
	    font.drawString(text, x - f.getStringWidth(text) / 2, y - height / 4, mod.isAbilitato() ? 0xff67ECC3 : Color.white.darker().getRGB());
	}
	
	public void onUpdate(boolean update, int mouseX, int mouseY){
		if(getHovered(mouseX, mouseY) && update && Mouse.isButtonDown(0)){
			if(toggled == 0){
				//mc.thePlayer.playSound("nobita/a.mp3", 0.5f, 1.0f);
				mc.thePlayer.playSound("random.click", 0.5f, 1.0f);
				mod.toggle();
				toggled = 1;
			}
		}else{
			toggled = 0;
		}
	}
	
	public boolean getHovered(int mouseX, int mouseY){
		return mouseX >= x - width / 2 && mouseY >= y - height / 2 && mouseX <= x + width / 2 && mouseY <= y + height / 2;
	}

	public float getX(){
		return x;
	}

	public void setX(float x){
		this.x = x;
	}

	public float getY(){
		return y;
	}

	public void setY(float y){
		this.y = y;
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}

	public Module getMod(){
		return mod;
	}
}
