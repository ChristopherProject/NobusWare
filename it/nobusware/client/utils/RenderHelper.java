package it.nobusware.client.utils;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;
import java.nio.FloatBuffer;

public class RenderHelper {
	private static final ScaledResolution sr;

	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	private static final Vec3d LIGHT0_POS = new Vec3d(0.20000000298023224D, 1.0D, -0.699999988079071D).normalize();
	private static final Vec3d LIGHT1_POS = new Vec3d(-0.20000000298023224D, 1.0D, 0.699999988079071D).normalize();

	static {
		sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);
	}

	public static final ScaledResolution getScaledRes() {
		final ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft(),
				Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		return scaledRes;
	}

	public static void drawHollowRect(final float posX, final float posY, final float posX2, final float posY2,
			final float width, final int color, final boolean center) {
		final float corners = width / 2.0f;
		final float side = width / 2.0f;
		if (center) {
			drawRect(posX - side, posY - corners, posX + side, posY2 + corners, color);
			drawRect(posX2 - side, posY - corners, posX2 + side, posY2 + corners, color);
			drawRect(posX - corners, posY - side, posX2 + corners, posY + side, color);
			drawRect(posX - corners, posY2 - side, posX2 + corners, posY2 + side, color);
		} else {
			drawRect(posX - width, posY - corners, posX, posY2 + corners, color);
			drawRect(posX2, posY - corners, posX2 + width, posY2 + corners, color);
			drawRect(posX - corners, posY - width, posX2 + corners, posY, color);
			drawRect(posX - corners, posY2, posX2 + corners, posY2 + width, color);
		}
	}

	public static void drawGradientBorderedRect(final float posX, final float posY, final float posX2,
			final float posY2, final float width, final int color, final int startColor, final int endColor,
			final boolean center) {
		drawGradientRect(posX, posY, posX2, posY2, startColor, endColor);
		drawHollowRect(posX, posY, posX2, posY2, width, color, center);
	}

	public static void renderImage(final ResourceLocation src, final int x, final int y, final int w, final int h) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(src);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.blendFunc(770, 771);
		GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
		GL11.glEnable(3042);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, w, h, w, h);
		GL11.glPopMatrix();
	}

	public static void renderImage(final String src, final int x, final int y, final int w, final int h) {
		renderImage(new ResourceLocation(src), x, y, w, h);
	}

	public static void drawCoolLines(final AxisAlignedBB mask) {
		GL11.glPushMatrix();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1,
			final int col1, final int col2) {
		drawRect(x, y, x2, y2, col2);
		final float f = (col1 >> 24 & 0xFF) / 255.0f;
		final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
		final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
		final float f4 = (col1 & 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(f2, f3, f4, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(1);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static void drawBorderedCorneredRect(final float x, final float y, final float x2, final float y2,
			final float lineWidth, final int lineColor, final int bgColor) {
		drawRect(x, y, x2, y2, bgColor);
		final float f = (lineColor >> 24 & 0xFF) / 255.0f;
		final float f2 = (lineColor >> 16 & 0xFF) / 255.0f;
		final float f3 = (lineColor >> 8 & 0xFF) / 255.0f;
		final float f4 = (lineColor & 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glEnable(3553);
		drawRect(x - 1.0f, y, x2 + 1.0f, y - lineWidth, lineColor);
		drawRect(x, y, x - lineWidth, y2, lineColor);
		drawRect(x - 1.0f, y2, x2 + 1.0f, y2 + lineWidth, lineColor);
		drawRect(x2, y, x2 + lineWidth, y2, lineColor);
		GL11.glDisable(3553);
		GL11.glDisable(3042);
	}

	public static double interp(final double from, final double to, final double pct) {
		return from + (to - from) * pct;
	}

	public static double interpPlayerX() {
		return interp(Minecraft.getMinecraft().thePlayer.lastTickPosX, Minecraft.getMinecraft().thePlayer.posX,
				Minecraft.getMinecraft().timer.renderPartialTicks);
	}

	public static double interpPlayerY() {
		return interp(Minecraft.getMinecraft().thePlayer.lastTickPosY, Minecraft.getMinecraft().thePlayer.posY,
				Minecraft.getMinecraft().timer.renderPartialTicks);
	}

	public static double interpPlayerZ() {
		return interp(Minecraft.getMinecraft().thePlayer.lastTickPosZ, Minecraft.getMinecraft().thePlayer.posZ,
				Minecraft.getMinecraft().timer.renderPartialTicks);
	}

	public static void glColor(final Color color) {
		GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
				color.getAlpha() / 255.0f);
	}

	public static void glColor(final int hex) {
		final float alpha = (hex >> 24 & 0xFF) / 255.0f;
		final float red = (hex >> 16 & 0xFF) / 255.0f;
		final float green = (hex >> 8 & 0xFF) / 255.0f;
		final float blue = (hex & 0xFF) / 255.0f;
		GL11.glColor4f(red, green, blue, alpha);
	}

	public static void drawGradientRect(final float x, final float y, final float x1, final float y1,
			final int topColor, final int bottomColor) {
		GL11.glEnable(1536);
		GL11.glShadeModel(7425);
		GL11.glBegin(7);
		glColor(topColor);
		GL11.glVertex2f(x, y1);
		GL11.glVertex2f(x1, y1);
		glColor(bottomColor);
		GL11.glVertex2f(x1, y);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
		GL11.glShadeModel(7424);
		GL11.glDisable(1536);
	}

	public static void drawLines(final AxisAlignedBB mask) {
		GL11.glPushMatrix();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
		GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
		GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
		GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static void drawRect(final float g, final float h, final float i, final float j, final int col1) {
		final float f = (col1 >> 24 & 0xFF) / 255.0f;
		final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
		final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
		final float f4 = (col1 & 0xFF) / 255.0f;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(f2, f3, f4, f);
		GL11.glBegin(7);
		GL11.glVertex2d((double) i, (double) h);
		GL11.glVertex2d((double) g, (double) h);
		GL11.glVertex2d((double) g, (double) j);
		GL11.glVertex2d((double) i, (double) j);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_,
			double p_74517_6_) {
		return setColorBuffer((float) p_74517_0_, (float) p_74517_2_, (float) p_74517_4_, (float) p_74517_6_);
	}

	private static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
		colorBuffer.clear();
		colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
		colorBuffer.flip();
		return colorBuffer;
	}
}