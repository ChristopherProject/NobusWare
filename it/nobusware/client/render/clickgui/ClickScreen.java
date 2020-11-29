package it.nobusware.client.render.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import it.nobusware.client.manager.Module;
import it.nobusware.client.render.clickgui.frame.Frame;
import it.nobusware.client.render.clickgui.frame.frames.CategoryFrame;
import it.nobusware.client.render.clickgui.utils.RenderUtilCL;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ClickScreen extends GuiScreen {
	
	private ArrayList<Frame> frames = new ArrayList<>();

	public void init() {
		float x = 2.0F;
		for (Module.Category category : Module.Category.values()) {
			this.frames.add(new CategoryFrame(category, x, 2.0F, 110.0F, 20.0F));
			x += 112.0F;
		}
		this.frames.forEach(frame -> frame.init());
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		ScaledResolution scaledResolution = RenderUtilCL.getResolution();
		this.frames.forEach(frame -> frame.drawScreen(mouseX, mouseY, scaledResolution));
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		this.frames.forEach(frame -> frame.keyTyped(typedChar, keyCode));
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.frames.forEach(frame -> frame.mouseClicked(mouseX, mouseY, mouseButton));
	}

	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		this.frames.forEach(frame -> frame.mouseReleased(mouseX, mouseY, state));
	}

	public void onGuiClosed() {
		super.onGuiClosed();
		this.frames.forEach(frame -> frame.setDragging(false));
	}

	public boolean doesGuiPauseGame() {
		return false;
	}
}