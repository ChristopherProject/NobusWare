package it.nobusware.client.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {

	 private static final Frustum frustrum = new Frustum();
	
	public static ScaledResolution getResolution() {
		return new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayHeight,
				Minecraft.getMinecraft().displayWidth);
	}

	public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
	}

	public static boolean isInViewFrustrum(Entity entity) {
		return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
	}

	private static boolean isInViewFrustrum(AxisAlignedBB bb) {
		Entity current = Minecraft.getMinecraft().func_175606_aa();
		frustrum.setPosition(current.posX, current.posY, current.posZ);
		return frustrum.isBoundingBoxInFrustum(bb);
	}
	  public static void drawBorderRect(float left, float top, float right, float bottom, int bcolor, int icolor, float f) {
		    Gui.drawRect(left + f, top + f, right - f, bottom - f, icolor);
		    Gui.drawRect(left, top, left + f, bottom, bcolor);
		    Gui.drawRect(left + f, top, right, top + f, bcolor);
		    Gui.drawRect(left + f, bottom - f, right, bottom, bcolor);
		    Gui.drawRect(right - f, top + f, right, bottom - f, bcolor);
		  }

	public static void drawRect(float g, float h, float i, float j, int col1) {
		float f = (col1 >> 24 & 0xFF) / 255.0F;
		float f1 = (col1 >> 16 & 0xFF) / 255.0F;
		float f2 = (col1 >> 8 & 0xFF) / 255.0F;
		float f3 = (col1 & 0xFF) / 255.0F;
		GlStateManager.enableBlend();
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GlStateManager.pushMatrix();
		GlStateManager.color(f1, f2, f3, f);
		GL11.glBegin(7);
		GL11.glVertex2d(i, h);
		GL11.glVertex2d(g, h);
		GL11.glVertex2d(g, j);
		GL11.glVertex2d(i, j);
		GL11.glEnd();
		GlStateManager.popMatrix();
		GL11.glEnable(3553);
		GlStateManager.disableBlend();
		GL11.glDisable(2848);
	}

	public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
		drawRect(x, y, x2, y2, col2);
		float f = (col1 >> 24 & 0xFF) / 255.0F;
		float f1 = (col1 >> 16 & 0xFF) / 255.0F;
		float f2 = (col1 >> 8 & 0xFF) / 255.0F;
		float f3 = (col1 & 0xFF) / 255.0F;
		GlStateManager.enableBlend();
		GL11.glDisable(3553);
		GlStateManager.blendFunc(770, 771);
		GL11.glEnable(2848);
		GlStateManager.pushMatrix();
		GlStateManager.color(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(1);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GlStateManager.popMatrix();
		GL11.glEnable(3553);
		GlStateManager.disableBlend();
		GL11.glDisable(2848);
	}

	 
	  public static void drawRectangle(double left, double top, double right, double bottom, Color color) {
	    float alpha = (color.getRGB() >> 24 & 0xFF) / 255.0F;
	    float red = (color.getRGB() >> 16 & 0xFF) / 255.0F;
	    float green = (color.getRGB() >> 8 & 0xFF) / 255.0F;
	    float blue = (color.getRGB() & 0xFF) / 255.0F;
	    WorldRenderer bufferbuilder = Tessellator.instance.getWorldRenderer();
	    Tessellator tessellator = Tessellator.instance;
	    GlStateManager.enableBlend();
	    GlStateManager.func_179090_x();
	    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	    GlStateManager.color(red, green, blue, alpha);
	    bufferbuilder.startDrawing(7);
	    bufferbuilder.addVertex(left, bottom, 0.0D);
	    bufferbuilder.addVertex(right, bottom, 0.0D);
	    bufferbuilder.addVertex(right, top, 0.0D);
	    bufferbuilder.addVertex(left, top, 0.0D);
	    tessellator.draw();
	    GlStateManager.func_179098_w();
	    GlStateManager.disableBlend();
	  }

	  public static void drawHsvScale(double left, double top, double right, double bottom) {
		    float width = (float)(right - left);
		    float i;
		    for (i = 0.0F; i < width; i++) {
		      double posX = left + i;
		      int color = Color.getHSBColor(i / width, 1.0F, 1.0F).getRGB();
		      Gui.drawRect(posX, top, (float) (posX + 1.0D), bottom, color);
		    } 
		  }
}
