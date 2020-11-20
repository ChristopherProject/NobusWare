package it.nobusware.client.utils;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class RenderUtils2D {
  public static ScaledResolution newScaledResolution() {
    return new ScaledResolution(Minecraft.getMinecraft(), (Minecraft.getMinecraft()).displayWidth, (Minecraft.getMinecraft()).displayHeight);
  }
  
  public static int getMouseX() {
    newScaledResolution();
    return Mouse.getX() * newScaledResolution().getScaledWidth() / (Minecraft.getMinecraft()).displayWidth;
  }
  
  public static int getMouseY() {
    newScaledResolution();
    newScaledResolution();
    return newScaledResolution().getScaledHeight() - Mouse.getY() * newScaledResolution().getScaledHeight() / (Minecraft.getMinecraft()).displayHeight - 1;
  }
  
  public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
    x *= 2.0F;
    y *= 2.0F;
    x1 *= 2.0F;
    y1 *= 2.0F;
    GL11.glScalef(0.5F, 0.5F, 0.5F);
    drawVLine(x, y + 1.0F, y1 - 2.0F, borderC);
    drawVLine(x1 - 1.0F, y + 1.0F, y1 - 2.0F, borderC);
    drawHLine(x + 2.0F, x1 - 3.0F, y, borderC);
    drawHLine(x + 2.0F, x1 - 3.0F, y1 - 1.0F, borderC);
    drawHLine(x + 1.0F, x + 1.0F, y + 1.0F, borderC);
    drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
    drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
    drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
    drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
    GL11.glScalef(2.0F, 2.0F, 2.0F);
  }
  
  public static void drawBorderedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
    x *= 2.0F;
    x1 *= 2.0F;
    y *= 2.0F;
    y1 *= 2.0F;
    GL11.glScalef(0.5F, 0.5F, 0.5F);
    drawVLine(x, y, y1 - 1.0F, borderC);
    drawVLine(x1 - 1.0F, y, y1, borderC);
    drawHLine(x, x1 - 1.0F, y, borderC);
    drawHLine(x, x1 - 2.0F, y1 - 1.0F, borderC);
    drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
    GL11.glScalef(2.0F, 2.0F, 2.0F);
  }
  
  public static boolean stringListContains(List<String> list, String needle) {
    for (String s : list) {
      if (s.trim().equalsIgnoreCase(needle.trim()))
        return true; 
    } 
    return false;
  }
  
  public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
    drawRect((float)x, (float)y, (float)x2, (float)y2, col2);
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glPushMatrix();
    GL11.glColor4f(f1, f2, f3, f);
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
    GL11.glPopMatrix();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  
  public static void drawHLine(float par1, float par2, float par3, int par4) {
    if (par2 < par1) {
      float var5 = par1;
      par1 = par2;
      par2 = var5;
    } 
    drawRect(par1, par3, par2 + 1.0F, par3 + 1.0F, par4);
  }
  
  public static void drawVLine(float par1, float par2, float par3, int par4) {
    if (par3 < par2) {
      float var5 = par2;
      par2 = par3;
      par3 = var5;
    } 
    drawRect(par1, par2 + 1.0F, par1 + 1.0F, par3, par4);
  }
  
  public static void drawRect(double d, double e, float paramXEnd, float paramYEnd, int paramColor) {
    float alpha = (paramColor >> 24 & 0xFF) / 255.0F;
    float red = (paramColor >> 16 & 0xFF) / 255.0F;
    float green = (paramColor >> 8 & 0xFF) / 255.0F;
    float blue = (paramColor & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glPushMatrix();
    GL11.glColor4f(red, green, blue, alpha);
    GL11.glBegin(7);
    GL11.glVertex2d(paramXEnd, e);
    GL11.glVertex2d(d, e);
    GL11.glVertex2d(d, paramYEnd);
    GL11.glVertex2d(paramXEnd, paramYEnd);
    GL11.glEnd();
    GL11.glPopMatrix();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  
  public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    float f4 = (col2 >> 24 & 0xFF) / 255.0F;
    float f5 = (col2 >> 16 & 0xFF) / 255.0F;
    float f6 = (col2 >> 8 & 0xFF) / 255.0F;
    float f7 = (col2 & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glShadeModel(7425);
    GL11.glPushMatrix();
    GL11.glBegin(7);
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glVertex2d(x2, y);
    GL11.glVertex2d(x, y);
    GL11.glColor4f(f5, f6, f7, f4);
    GL11.glVertex2d(x, y2);
    GL11.glVertex2d(x2, y2);
    GL11.glEnd();
    GL11.glPopMatrix();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
    GL11.glShadeModel(7424);
  }
  
  public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3) {
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glDisable(3042);
    GL11.glPushMatrix();
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glLineWidth(1.0F);
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
    GL11.glPopMatrix();
    drawGradientRect(x, y, x2, y2, col2, col3);
    GL11.glEnable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2848);
  }
}
