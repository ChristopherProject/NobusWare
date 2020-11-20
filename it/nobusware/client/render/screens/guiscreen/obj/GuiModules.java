package it.nobusware.client.render.screens.guiscreen.obj;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import it.nobusware.client.manager.Module.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiModules extends GuiScreen {

	private final static Minecraft mc = Minecraft.getMinecraft();
	private final static FontRenderer f = mc.fontRendererObj;

	private Frame selected = null;
	private ArrayList<Frame> frames = new ArrayList();
	private static boolean hovered;

	public GuiModules() {
		int x = 150;
		for (Category cat : Category.values()) {
			frames.add(new Frame(cat, x, 100));
			x += 150;
		}
	}

	public void initGui() {
		if (OpenGlHelper.shadersSupported
				&& mc.func_175606_aa() instanceof net.minecraft.entity.player.EntityPlayer) {
			if (mc.entityRenderer.theShaderGroup != null)
				mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.func_175069_a(new ResourceLocation("shaders/post/blur.json"));
		}
		mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.HIDDEN;
	}

	public void onGuiClosed() {
		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}
		mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
	}
	
	public static boolean hover() {
		
		return hovered;
	}
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		for (Frame fr : frames) {
			fr.draw(selected == null);
			setHovered(mouseX >= fr.getX() - f.getStringWidth(fr.getCat().toString()) / 2
				&& mouseY >= fr.getY() - 20 && mouseX <= fr.getX() + f.getStringWidth(fr.getCat().toString()) / 2
				&& mouseY <= fr.getY());
			if (Mouse.isButtonDown(0)) {
				if (hover() && selected == null) {
					selected = fr;
				}
			} else {
				selected = null;
			}
			if (selected != null) {
				selected.setX(MathHelper.clamp_int(mouseX, 10, width - 10));
				selected.setY(MathHelper.clamp_int(mouseY, 10, height - 10));
			}
		}
	}
}
