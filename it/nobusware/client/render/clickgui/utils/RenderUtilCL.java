package it.nobusware.client.render.clickgui.utils;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Vector3d;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import it.nobusware.client.utils.Frustum;
import it.nobusware.client.utils.GLUtil;
import it.nobusware.client.utils.RenderUtils2D;
import it.nobusware.client.utils.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;

public class RenderUtilCL {
  private static final Minecraft mc = Minecraft.getMinecraft();
  
  private static final Frustum frustrum = new Frustum();
  
  private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
  
  private static final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
  
  private static final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
  
  public static double interpolate(double current, double old, double scale) {
    return old + (current - old) * scale;
  }
  
  public static float calculateCompensation(float target, float current, long delta, double speed) {
    float diff = current - target;
    if (delta < 1L)
      delta = 1L; 
    if (delta > 1000L)
      delta = 16L; 
    if (diff > speed) {
      double xD = (speed * delta / 16.0D < 0.5D) ? 0.5D : (speed * delta / 16.0D);
      current = (float)(current - xD);
      if (current < target)
        current = target; 
    } else if (diff < -speed) {
      double xD = (speed * delta / 16.0D < 0.5D) ? 0.5D : (speed * delta / 16.0D);
      current = (float)(current + xD);
      if (current > target)
        current = target; 
    } else {
      current = target;
    } 
    return current;
  }
  
  public static ScaledResolution getResolution() {
    return RenderUtils2D.newScaledResolution();
  }
  
  public static Vec3d to2D(double x, double y, double z) {
    FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
    IntBuffer viewport = BufferUtils.createIntBuffer(16);
    FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
    FloatBuffer projection = BufferUtils.createFloatBuffer(16);
    GL11.glGetFloat(2982, modelView);
    GL11.glGetFloat(2983, projection);
    GL11.glGetInteger(2978, viewport);
    boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCoords);
    if (result)
      return new Vec3d(screenCoords.get(0), (Display.getHeight() - screenCoords.get(1)), screenCoords.get(2)); 
    return null;
  }
  
  public static void drawArrow(float x, float y, int hexColor) {
    GL11.glPushMatrix();
    GL11.glScaled(1.3D, 1.3D, 1.3D);
    x = (float)(x / 1.3D);
    y = (float)(y / 1.3D);
    GL11.glEnable(2848);
    GL11.glDisable(3553);
    GL11.glEnable(3042);
    hexColor(hexColor);
    GL11.glLineWidth(2.0F);
    GL11.glBegin(1);
    GL11.glVertex2d(x, y);
    GL11.glVertex2d((x + 3.0F), (y + 4.0F));
    GL11.glEnd();
    GL11.glBegin(1);
    GL11.glVertex2d((x + 3.0F), (y + 4.0F));
    GL11.glVertex2d((x + 6.0F), y);
    GL11.glEnd();
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glPopMatrix();
  }
  
  public static void drawTracerPointer(float x, float y, float size, float widthDiv, float heightDiv, int color) {
    boolean blend = GL11.glIsEnabled(3042);
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glPushMatrix();
    hexColor(color);
    GL11.glBegin(7);
    GL11.glVertex2d(x, y);
    GL11.glVertex2d((x - size / widthDiv), (y + size));
    GL11.glVertex2d(x, (y + size / heightDiv));
    GL11.glVertex2d((x + size / widthDiv), (y + size));
    GL11.glVertex2d(x, y);
    GL11.glEnd();
    GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.8F);
    GL11.glBegin(2);
    GL11.glVertex2d(x, y);
    GL11.glVertex2d((x - size / widthDiv), (y + size));
    GL11.glVertex2d(x, (y + size / heightDiv));
    GL11.glVertex2d((x + size / widthDiv), (y + size));
    GL11.glVertex2d(x, y);
    GL11.glEnd();
    GL11.glPopMatrix();
    GL11.glEnable(3553);
    if (!blend)
      GL11.glDisable(3042); 
    GL11.glDisable(2848);
  }
  
  public static void hexColor(int hexColor) {
    float red = (hexColor >> 16 & 0xFF) / 255.0F;
    float green = (hexColor >> 8 & 0xFF) / 255.0F;
    float blue = (hexColor & 0xFF) / 255.0F;
    float alpha = (hexColor >> 24 & 0xFF) / 255.0F;
    GL11.glColor4f(red, green, blue, alpha);
  }
  
  
  public static int getRainbow(int speed, int offset) {
    float hue = (float)((System.currentTimeMillis() + offset) % speed);
    hue /= speed;
    return Color.getHSBColor(hue, 0.6F, 1.0F).getRGB();
  }
  
  public static float[] getRGBAs(int rgb) {
    return new float[] { (rgb >> 16 & 0xFF) / 255.0F, (rgb >> 8 & 0xFF) / 255.0F, (rgb & 0xFF) / 255.0F, (rgb >> 24 & 0xFF) / 255.0F };
  }
  
  public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    Minecraft.getMinecraft().getTextureManager().bindTexture(image);
    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
  }
  

  
  public static void enable3D() {
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
  }
  
  public static void disable3D() {
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDepthMask(true);
    GL11.glDisable(3042);
  }
  
  public static void color(int color) {
    GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F);
  }
  
 
  
 
  
  public static Vector3d project(double x, double y, double z) {
    FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
    GL11.glGetFloat(2982, modelview);
    GL11.glGetFloat(2983, projection);
    GL11.glGetInteger(2978, viewport);
    if (GLU.gluProject((float)x, (float)y, (float)z, modelview, projection, viewport, vector))
      return new Vector3d((vector.get(0) / getResolution().getScaleFactor()), ((
          Display.getHeight() - vector.get(1)) / getResolution().getScaleFactor()), vector
          .get(2)); 
    return null;
  }
  
  public static void drawCheckMark(float x, float y, int width, int color) {
    float f = (color >> 24 & 0xFF) / 255.0F;
    float f1 = (color >> 16 & 0xFF) / 255.0F;
    float f2 = (color >> 8 & 0xFF) / 255.0F;
    float f3 = (color & 0xFF) / 255.0F;
    GL11.glPushMatrix();
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glBlendFunc(770, 771);
    GL11.glLineWidth(2.2F);
    GL11.glBegin(3);
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glVertex2d((x + width) - 6.5D, (y + 3.0F));
    GL11.glVertex2d((x + width) - 11.5D, (y + 10.0F));
    GL11.glVertex2d((x + width) - 13.5D, (y + 8.0F));
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glPopMatrix();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
  }
  
  
  public static void prepareScissorBox(ScaledResolution sr, float x, float y, float width, float height) {
    float x2 = x + width;
    float y2 = y + height;
    int factor = sr.getScaleFactor();
    GL11.glScissor((int)(x * factor), (int)((sr.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
  }
  
  public static void drawBorderedRect(double x, double y, double width, double height, double lineSize, int borderColor, int color) {
    Gui.drawRect(x, y, (float) (x + width), y + height, color);
    Gui.drawRect(x, y, (float) (x + width), y + lineSize, borderColor);
    Gui.drawRect(x, y, (float) (x + lineSize), y + height, borderColor);
    Gui.drawRect(x + width, y, (float) (x + width - lineSize), y + height, borderColor);
    Gui.drawRect(x, y + height, (float) (x + width), y + height - lineSize, borderColor);
  }
  
  public static void drawBordered(double x, double y, double x2, double y2, double thickness, int inside, int outline) {
    double fix = 0.0D;
    if (thickness < 1.0D)
      fix = 1.0D; 
    drawRect2(x + thickness, y + thickness, x2 - thickness, y2 - thickness, inside);
    drawRect2(x, y + 1.0D - fix, x + thickness, y2, outline);
    drawRect2(x, y, x2 - 1.0D + fix, y + thickness, outline);
    drawRect2(x2 - thickness, y, x2, y2 - 1.0D + fix, outline);
    drawRect2(x + 1.0D - fix, y2 - thickness, x2, y2, outline);
  }
  
  
  public static void drawRect(double x, double y, double width, double height, int color) {
    Gui.drawRect(x, y, (float) (x + width), y + height, color);
  }
  
  public static void drawRect2(double x, double y, double x2, double y2, int color) {
    Gui.drawRect(x, y, (float) x2, y2, color);
  }
  
  public static void drawBorderedRoundedRect(float x, float y, float width, float height, float radius, float borderSize, int borderC, int insideC) {
    drawRoundedRect(x, y, width, height, radius, borderC);
    drawRoundedRect((x + borderSize), (y + borderSize), (width - borderSize * 2.0F), (height - borderSize * 2.0F), radius, insideC);
  }
  
  public static void drawRoundedRectWithShadow(double x, double y, double width, double height, double radius, int color) {
    drawRoundedRect(x + 2.0D, y + 1.0D, width, height + 1.0D, radius, (new Color(0)).getRGB());
    drawRoundedRect(x, y, width, height, radius, color);
  }
  
  public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
    GlStateManager.enableBlend();
    GlStateManager.func_179090_x();
    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    double x1 = x + width;
    double y1 = y + height;
    float f = (color >> 24 & 0xFF) / 255.0F;
    float f1 = (color >> 16 & 0xFF) / 255.0F;
    float f2 = (color >> 8 & 0xFF) / 255.0F;
    float f3 = (color & 0xFF) / 255.0F;
    GL11.glPushAttrib(0);
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    x *= 2.0D;
    y *= 2.0D;
    x1 *= 2.0D;
    y1 *= 2.0D;
    GL11.glDisable(3553);
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glEnable(2848);
    GL11.glBegin(9);
    int i;
    for (i = 0; i <= 90; i += 3)
      GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
    for (i = 90; i <= 180; i += 3)
      GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
    for (i = 0; i <= 90; i += 3)
      GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
    for (i = 90; i <= 180; i += 3)
      GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glScaled(2.0D, 2.0D, 2.0D);
    GL11.glPopAttrib();
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179098_w();
    GlStateManager.disableBlend();
  }
}
