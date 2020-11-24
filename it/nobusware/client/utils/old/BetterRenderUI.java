package it.nobusware.client.utils.old;

import java.awt.Color;
import java.util.HashMap;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import it.nobusware.client.utils.Frustum;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;

public class BetterRenderUI {
	
  private static final Frustum frustum = new Frustum();
  private static BetterRenderUI.ColorUtils colorUtils = new BetterRenderUI.ColorUtils();
  
  public static double interpolate(double newPos, double oldPos) {
    return oldPos + (newPos - oldPos) * (Minecraft.getMinecraft()).timer.renderPartialTicks;
  }
  
  public static boolean isInFrustumView(Entity ent) {
    Entity current = Minecraft.getMinecraft().func_175606_aa();
    double x = interpolate(current.posX, current.lastTickPosX), y = interpolate(current.posY, current.lastTickPosY);
    double z = interpolate(current.posZ, current.lastTickPosZ);
    frustum.setPosition(x, y, z);
    return !(!frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) && !ent.ignoreFrustumCheck);
  }
  
  public static class R2DUtils {
    public static void enableGL2D() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
    }
    
    public static void disableGL2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
    }
    
    public static void draw2DCorner(Entity e, double posX, double posY, double posZ, int color) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(posX, posY, posZ);
      GL11.glNormal3f(0.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(-0.1D, -0.1D, 0.1D);
      GL11.glDisable(2896);
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GlStateManager.depthMask(true);
      drawRect(7.0D, -20.0D, 7.300000190734863D, -17.5D, color);
      drawRect(-7.300000190734863D, -20.0D, -7.0D, -17.5D, color);
      drawRect(4.0D, -20.299999237060547D, 7.300000190734863D, -20.0D, color);
      drawRect(-7.300000190734863D, -20.299999237060547D, -4.0D, -20.0D, color);
      drawRect(-7.0D, 3.0D, -4.0D, 3.299999952316284D, color);
      drawRect(4.0D, 3.0D, 7.0D, 3.299999952316284D, color);
      drawRect(-7.300000190734863D, 0.8D, -7.0D, 3.299999952316284D, color);
      drawRect(7.0D, 0.5D, 7.300000190734863D, 3.299999952316284D, color);
      drawRect(7.0D, -20.0D, 7.300000190734863D, -17.5D, color);
      drawRect(-7.300000190734863D, -20.0D, -7.0D, -17.5D, color);
      drawRect(4.0D, -20.299999237060547D, 7.300000190734863D, -20.0D, color);
      drawRect(-7.300000190734863D, -20.299999237060547D, -4.0D, -20.0D, color);
      drawRect(-7.0D, 3.0D, -4.0D, 3.299999952316284D, color);
      drawRect(4.0D, 3.0D, 7.0D, 3.299999952316284D, color);
      drawRect(-7.300000190734863D, 0.8D, -7.0D, 3.299999952316284D, color);
      drawRect(7.0D, 0.5D, 7.300000190734863D, 3.299999952316284D, color);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GlStateManager.popMatrix();
    }
    
    public static void drawRect(double x2, double y2, double x1, double y1, int color) {
      enableGL2D();
      glColor(color);
      drawRect(x2, y2, x1, y1);
      disableGL2D();
    }
    
    private static void drawRect(double x2, double y2, double x1, double y1) {
      GL11.glBegin(7);
      GL11.glVertex2d(x2, y1);
      GL11.glVertex2d(x1, y1);
      GL11.glVertex2d(x1, y2);
      GL11.glVertex2d(x2, y2);
      GL11.glEnd();
    }
    
    public static void glColor(int hex) {
      float alpha = (hex >> 24 & 0xFF) / 255.0F;
      float red = (hex >> 16 & 0xFF) / 255.0F;
      float green = (hex >> 8 & 0xFF) / 255.0F;
      float blue = (hex & 0xFF) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void drawRect(float x, float y, float x1, float y1, int color) {
      enableGL2D();
      colorUtils().glColor(color);
      drawRect(x, y, x1, y1);
      disableGL2D();
    }
    
    public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int borderColor) {
      enableGL2D();
      colorUtils().glColor(borderColor);
      drawRect(x + width, y, x1 - width, y + width);
      drawRect(x, y, x + width, y1);
      drawRect(x1 - width, y, x1, y1);
      drawRect(x + width, y1 - width, x1 - width, y1);
      disableGL2D();
    }
    
    public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
      enableGL2D();
      x *= 2.0F;
      x1 *= 2.0F;
      y *= 2.0F;
      y1 *= 2.0F;
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      drawVLine(x, y, y1, borderC);
      drawVLine(x1 - 1.0F, y, y1, borderC);
      drawHLine(x, x1 - 1.0F, y, borderC);
      drawHLine(x, x1 - 2.0F, y1 - 1.0F, borderC);
      drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
    }
    
    public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
      enableGL2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      colorUtils().glColor(topColor);
      GL11.glVertex2f(x, y1);
      GL11.glVertex2f(x1, y1);
      colorUtils().glColor(bottomColor);
      GL11.glVertex2f(x1, y);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      disableGL2D();
    }
    
    public static void drawHLine(float x, float y, float x1, int y1) {
      if (y < x) {
        float var5 = x;
        x = y;
        y = var5;
      } 
      drawRect(x, x1, y + 1.0F, x1 + 1.0F, y1);
    }
    
    public static void drawVLine(float x, float y, float x1, int y1) {
      if (x1 < y) {
        float var5 = y;
        y = x1;
        x1 = var5;
      } 
      drawRect(x, y + 1.0F, x + 1.0F, x1, y1);
    }
    
    public static void drawHLine(float x, float y, float x1, int y1, int y2) {
      if (y < x) {
        float var5 = x;
        x = y;
        y = var5;
      } 
      drawGradientRect(x, x1, y + 1.0F, x1 + 1.0F, y1, y2);
    }
    
    public static void drawRect(float x, float y, float x1, float y1) {
      GL11.glBegin(7);
      GL11.glVertex2f(x, y1);
      GL11.glVertex2f(x1, y1);
      GL11.glVertex2f(x1, y);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
    }
    
    public static void drawTri(double x1, double y1, double x2, double y2, double x3, double y3, double width, Color c) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      colorUtils().glColor(c);
      GL11.glLineWidth((float)width);
      GL11.glBegin(3);
      GL11.glVertex2d(x1, y1);
      GL11.glVertex2d(x2, y2);
      GL11.glVertex2d(x3, y3);
      GL11.glEnd();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
    }
  }
  
  public static class R3DUtils {
    public static void startDrawing() {
      GL11.glEnable(3042);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      (Minecraft.getMinecraft()).entityRenderer.setupCameraTransform((Minecraft.getMinecraft()).timer.renderPartialTicks, 0);
    }
    
    public static void stopDrawing() {
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
    }
    
    public void drawRombo(double x, double y, double z) {
      (Minecraft.getMinecraft()).entityRenderer.setupCameraTransform((Minecraft.getMinecraft()).timer.renderPartialTicks, 0);
      y++;
      GL11.glBegin(4);
      GL11.glVertex3d(x + 0.5D, y, z);
      GL11.glVertex3d(x, y + 1.0D, z);
      GL11.glVertex3d(x, y, z + 0.5D);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x, y, z + 0.5D);
      GL11.glVertex3d(x, y + 1.0D, z);
      GL11.glVertex3d(x + 0.5D, y, z);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x, y, z - 0.5D);
      GL11.glVertex3d(x, y + 1.0D, z);
      GL11.glVertex3d(x - 0.5D, y, z);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x - 0.5D, y, z);
      GL11.glVertex3d(x, y + 1.0D, z);
      GL11.glVertex3d(x, y, z - 0.5D);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x + 0.5D, y, z);
      GL11.glVertex3d(x, y - 1.0D, z);
      GL11.glVertex3d(x, y, z + 0.5D);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x, y, z + 0.5D);
      GL11.glVertex3d(x, y - 1.0D, z);
      GL11.glVertex3d(x + 0.5D, y, z);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x, y, z - 0.5D);
      GL11.glVertex3d(x, y - 1.0D, z);
      GL11.glVertex3d(x - 0.5D, y, z);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x - 0.5D, y, z);
      GL11.glVertex3d(x, y - 1.0D, z);
      GL11.glVertex3d(x, y, z - 0.5D);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x, y, z - 0.5D);
      GL11.glVertex3d(x, y + 1.0D, z);
      GL11.glVertex3d(x + 0.5D, y, z);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x + 0.5D, y, z);
      GL11.glVertex3d(x, y + 1.0D, z);
      GL11.glVertex3d(x, y, z - 0.5D);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x, y, z + 0.5D);
      GL11.glVertex3d(x, y + 1.0D, z);
      GL11.glVertex3d(x - 0.5D, y, z);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x - 0.5D, y, z);
      GL11.glVertex3d(x, y + 1.0D, z);
      GL11.glVertex3d(x, y, z + 0.5D);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x, y, z - 0.5D);
      GL11.glVertex3d(x, y - 1.0D, z);
      GL11.glVertex3d(x + 0.5D, y, z);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x + 0.5D, y, z);
      GL11.glVertex3d(x, y - 1.0D, z);
      GL11.glVertex3d(x, y, z - 0.5D);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x, y, z + 0.5D);
      GL11.glVertex3d(x, y - 1.0D, z);
      GL11.glVertex3d(x - 0.5D, y, z);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(x - 0.5D, y, z);
      GL11.glVertex3d(x, y - 1.0D, z);
      GL11.glVertex3d(x, y, z + 0.5D);
      GL11.glEnd();
    }
    
    public static void drawFilledBox(AxisAlignedBB mask) {
      GL11.glBegin(4);
      GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
      GL11.glEnd();
      GL11.glBegin(4);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
      GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
      GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
      GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
      GL11.glEnd();
    }
    
    public static void drawOutlinedBoundingBox(AxisAlignedBB aabb) {
      GL11.glBegin(3);
      GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
      GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
      GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
      GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
      GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
      GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
      GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
      GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
      GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
      GL11.glEnd();
      GL11.glBegin(1);
      GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
      GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
      GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
      GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
      GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
      GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
      GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
      GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
      GL11.glEnd();
    }
    
    public static void drawOutlinedBox(AxisAlignedBB box) {
      if (box == null)
        return; 
      (Minecraft.getMinecraft()).entityRenderer.setupCameraTransform((Minecraft.getMinecraft()).timer.renderPartialTicks, 0);
      GL11.glBegin(3);
      GL11.glVertex3d(box.minX, box.minY, box.minZ);
      GL11.glVertex3d(box.maxX, box.minY, box.minZ);
      GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
      GL11.glVertex3d(box.minX, box.minY, box.maxZ);
      GL11.glVertex3d(box.minX, box.minY, box.minZ);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex3d(box.minX, box.maxY, box.minZ);
      GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
      GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
      GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
      GL11.glVertex3d(box.minX, box.maxY, box.minZ);
      GL11.glEnd();
      GL11.glBegin(1);
      GL11.glVertex3d(box.minX, box.minY, box.minZ);
      GL11.glVertex3d(box.minX, box.maxY, box.minZ);
      GL11.glVertex3d(box.maxX, box.minY, box.minZ);
      GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
      GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
      GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
      GL11.glVertex3d(box.minX, box.minY, box.maxZ);
      GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
      GL11.glEnd();
    }
    
    public static void drawTracerLine1(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
      boolean temp = (Minecraft.getMinecraft()).gameSettings.viewBobbing;
      (Minecraft.getMinecraft()).gameSettings.viewBobbing = false;
      EntityRenderer var10000 = (Minecraft.getMinecraft()).entityRenderer;
      Timer var10001 = (Minecraft.getMinecraft()).timer;
      var10000.setupCameraTransform((Minecraft.getMinecraft()).timer.renderPartialTicks, 2);
      (Minecraft.getMinecraft()).gameSettings.viewBobbing = temp;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.0F);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(2);
      Minecraft.getMinecraft();
      GL11.glVertex3d(0.0D, 0.0D + (Minecraft.getMinecraft()).thePlayer.getEyeHeight(), 0.0D);
      GL11.glVertex3d(x, y, z);
      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
    }
    
    public static int getBlockColor(Block block) {
      int color = 0;
      switch (Block.getIdFromBlock(block)) {
        case 14:
        case 41:
          color = -1711477173;
          break;
        case 15:
        case 42:
          color = -1715420992;
          break;
        case 16:
        case 173:
          color = -1724434633;
          break;
        case 21:
        case 22:
          color = -1726527803;
          break;
        case 49:
          color = -1724108714;
          break;
        case 54:
        case 146:
          color = -1711292672;
          break;
        case 56:
        case 57:
        case 138:
          color = -1721897739;
          break;
        case 61:
        case 62:
          color = -1711395081;
          break;
        case 73:
        case 74:
        case 152:
          color = -1711341568;
          break;
        case 89:
          color = -1712594866;
          break;
        case 129:
        case 133:
          color = -1726489246;
          break;
        case 130:
          color = -1713438249;
          break;
        case 52:
          color = 805728308;
          break;
        default:
          color = -1711276033;
          break;
      } 
      return (color == 0) ? 806752583 : color;
    }
  }
  
  public static class ColorUtils {
    public int RGBtoHEX(int r, int g, int b, int a) {
      return (a << 24) + (r << 16) + (g << 8) + b;
    }
    
    public static Color getRainbow(long offset, float fade) {
      float hue = (float)(System.nanoTime() + offset) / 8.0E9F % 1.0F;
      long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
      Color c = new Color((int)color);
      return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
    }
    
    public static Color blend(Color color1, Color color2, double ratio) {
      float r = (float)ratio;
      float ir = 1.0F - r;
      float[] rgb1 = new float[3];
      float[] rgb2 = new float[3];
      color1.getColorComponents(rgb1);
      color2.getColorComponents(rgb2);
      Color color = new Color(rgb1[0] * r + rgb2[0] * ir, 
          rgb1[1] * r + rgb2[1] * ir, 
          rgb1[2] * r + rgb2[2] * ir);
      return color;
    }
    
    public static Color glColor(int color, float alpha) {
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
      return new Color(red, green, blue, alpha);
    }
    
    public void glColor(Color color) {
      GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
    }
    
    public Color glColor(int hex) {
      float alpha = (hex >> 24 & 0xFF) / 256.0F;
      float red = (hex >> 16 & 0xFF) / 255.0F;
      float green = (hex >> 8 & 0xFF) / 255.0F;
      float blue = (hex & 0xFF) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
      return new Color(red, green, blue, alpha);
    }
    
    public Color glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
      float red = 0.003921569F * redRGB;
      float green = 0.003921569F * greenRGB;
      float blue = 0.003921569F * blueRGB;
      GL11.glColor4f(red, green, blue, alpha);
      return new Color(red, green, blue, alpha);
    }
    
    public static int transparency(int color, double alpha) {
      Color c = new Color(color);
      float r = 0.003921569F * c.getRed();
      float g = 0.003921569F * c.getGreen();
      float b = 0.003921569F * c.getBlue();
      return (new Color(r, g, b, (float)alpha)).getRGB();
    }
    
    public static float[] getRGBA(int color) {
      float a = (color >> 24 & 0xFF) / 255.0F;
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color & 0xFF) / 255.0F;
      return new float[] { r, g, b, a };
    }
    
    public static int intFromHex(String hex) {
      try {
        return Integer.parseInt(hex, 15);
      } catch (NumberFormatException e) {
        return -1;
      } 
    }
    
    public static String hexFromInt(int color) {
      return hexFromInt(new Color(color));
    }
    
    public static String hexFromInt(Color color) {
      return Integer.toHexString(color.getRGB()).substring(2);
    }
  }
  
  public static final class Stencil {
    private static final Stencil INSTANCE = new Stencil();
    
    private final HashMap<Integer, StencilFunc> stencilFuncs = new HashMap<>();
    
    private int layers = 1;
    
    private boolean renderMask;
    
    public static Stencil getInstance() {
      return INSTANCE;
    }
    
    public void setRenderMask(boolean renderMask) {
      this.renderMask = renderMask;
    }
    
    public static void checkSetupFBO() {
      Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
      if (fbo != null && fbo.depthBuffer > -1) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, (Minecraft.getMinecraft()).displayWidth, (Minecraft.getMinecraft()).displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
        fbo.depthBuffer = -1;
      } 
    }
    
    public static void setupFBO(Framebuffer fbo) {
      EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
      int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
      EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
      EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, (Minecraft.getMinecraft()).displayWidth, (Minecraft.getMinecraft()).displayHeight);
      EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
      EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
    
    public void startLayer() {
      if (this.layers == 1) {
        GL11.glClearStencil(0);
        GL11.glClear(1024);
      } 
      GL11.glEnable(2960);
      this.layers++;
      if (this.layers > getMaximumLayers()) {
        System.out.println("StencilUtil: Reached maximum amount of layers!");
        this.layers = 1;
      } 
    }
    
    public void stopLayer() {
      if (this.layers == 1) {
        System.out.println("StencilUtil: No layers found!");
        return;
      } 
      this.layers--;
      if (this.layers == 1) {
        GL11.glDisable(2960);
      } else {
        StencilFunc lastStencilFunc = this.stencilFuncs.remove(Integer.valueOf(this.layers));
        if (lastStencilFunc != null)
          lastStencilFunc.use(); 
      } 
    }
    
    public void clear() {
      GL11.glClearStencil(0);
      GL11.glClear(1024);
      this.stencilFuncs.clear();
      this.layers = 1;
    }
    
    public void setBuffer() {
      setStencilFunc(new StencilFunc(this, this.renderMask ? 519 : 512, this.layers, getMaximumLayers(), 7681, 7680, 7680));
    }
    
    public void setBuffer(boolean set) {
      setStencilFunc(new StencilFunc(this, this.renderMask ? 519 : 512, set ? this.layers : (this.layers - 1), getMaximumLayers(), 7681, 7681, 7681));
    }
    
    public void cropOutside() {
      setStencilFunc(new StencilFunc(this, 517, this.layers, getMaximumLayers(), 7680, 7680, 7680));
    }
    
    public void cropInside() {
      setStencilFunc(new StencilFunc(this, 514, this.layers, getMaximumLayers(), 7680, 7680, 7680));
    }
    
    public void setStencilFunc(StencilFunc stencilFunc) {
      GL11.glStencilFunc(StencilFunc.func_func, StencilFunc.func_ref, StencilFunc.func_mask);
      GL11.glStencilOp(StencilFunc.op_fail, StencilFunc.op_zfail, StencilFunc.op_zpass);
      this.stencilFuncs.put(Integer.valueOf(this.layers), stencilFunc);
    }
    
    public StencilFunc getStencilFunc() {
      return this.stencilFuncs.get(Integer.valueOf(this.layers));
    }
    
    public int getLayer() {
      return this.layers;
    }
    
    public int getStencilBufferSize() {
      return GL11.glGetInteger(3415);
    }
    
    public int getMaximumLayers() {
      return (int)(Math.pow(2.0D, getStencilBufferSize()) - 1.0D);
    }
    
    public void createCirlce(double x, double y, double radius) {
      GL11.glBegin(6);
      for (int i = 0; i <= 360; i++) {
        double sin = Math.sin(i * Math.PI / 180.0D) * radius;
        double cos = Math.cos(i * Math.PI / 180.0D) * radius;
        GL11.glVertex2d(x + sin, y + cos);
      } 
      GL11.glEnd();
    }
    
    public void createRect(double x, double y, double x2, double y2) {
      GL11.glBegin(7);
      GL11.glVertex2d(x, y2);
      GL11.glVertex2d(x2, y2);
      GL11.glVertex2d(x2, y);
      GL11.glVertex2d(x, y);
      GL11.glEnd();
    }
    
    public static class StencilFunc {
      public static int func_func;
      
      public static int func_ref;
      
      public static int func_mask;
      
      public static int op_fail;
      
      public static int op_zfail;
      
      public static int op_zpass;
      
      public StencilFunc(BetterRenderUI.Stencil paramStencil, int func_func, int func_ref, int func_mask, int op_fail, int op_zfail, int op_zpass) {
        StencilFunc.func_func = func_func;
        StencilFunc.func_ref = func_ref;
        StencilFunc.func_mask = func_mask;
        StencilFunc.op_fail = op_fail;
        StencilFunc.op_zfail = op_zfail;
        StencilFunc.op_zpass = op_zpass;
      }
      
      public void use() {
        GL11.glStencilFunc(func_func, func_ref, func_mask);
        GL11.glStencilOp(op_fail, op_zfail, op_zpass);
      }
    }
  }
  
  public static class Camera {
    private final Minecraft mc;
    
    private Timer timer;
    
    private double posX;
    
    private double posY;
    
    private double posZ;
    
    private float rotationYaw;
    
    private float rotationPitch;
    
    public Camera(Entity entity) {
      this.mc = Minecraft.getMinecraft();
      if (this.timer == null)
        this.timer = this.mc.timer; 
      this.posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * this.timer.renderPartialTicks;
      this.posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * this.timer.renderPartialTicks;
      this.posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * this.timer.renderPartialTicks;
      setRotationYaw(entity.rotationYaw);
      setRotationPitch(entity.rotationPitch);
      if (entity instanceof EntityPlayer && (Minecraft.getMinecraft()).gameSettings.viewBobbing && entity == (Minecraft.getMinecraft()).thePlayer) {
        EntityPlayer living1 = (EntityPlayer)entity;
        setRotationYaw(getRotationYaw() + living1.prevCameraYaw + (living1.cameraYaw - living1.prevCameraYaw) * this.timer.renderPartialTicks);
        setRotationPitch(getRotationPitch() + living1.prevCameraPitch + (living1.cameraPitch - living1.prevCameraPitch) * this.timer.renderPartialTicks);
      } else if (entity instanceof EntityLivingBase) {
        EntityLivingBase living2 = (EntityLivingBase)entity;
        setRotationYaw(living2.rotationYawHead);
      } 
    }
    
    public Camera(Entity entity, double offsetX, double offsetY, double offsetZ, double offsetRotationYaw, double offsetRotationPitch) {
      this.mc = Minecraft.getMinecraft();
      this.posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * this.timer.renderPartialTicks;
      this.posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * this.timer.renderPartialTicks;
      this.posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * this.timer.renderPartialTicks;
      setRotationYaw(entity.rotationYaw);
      setRotationPitch(entity.rotationPitch);
      if (entity instanceof EntityPlayer && (Minecraft.getMinecraft()).gameSettings.viewBobbing && entity == (Minecraft.getMinecraft()).thePlayer) {
        EntityPlayer player = (EntityPlayer)entity;
        setRotationYaw(getRotationYaw() + player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * this.timer.renderPartialTicks);
        setRotationPitch(getRotationPitch() + player.prevCameraPitch + (player.cameraPitch - player.prevCameraPitch) * this.timer.renderPartialTicks);
      } 
      this.posX += offsetX;
      this.posY += offsetY;
      this.posZ += offsetZ;
      this.rotationYaw += (float)offsetRotationYaw;
      this.rotationPitch += (float)offsetRotationPitch;
    }
    
    public Camera(double posX, double posY, double posZ, float rotationYaw, float rotationPitch) {
      this.mc = Minecraft.getMinecraft();
      setPosX(posX);
      this.posY = posY;
      this.posZ = posZ;
      setRotationYaw(rotationYaw);
      setRotationPitch(rotationPitch);
    }
    
    public double getPosX() {
      return this.posX;
    }
    
    public void setPosX(double posX) {
      this.posX = posX;
    }
    
    public double getPosY() {
      return this.posY;
    }
    
    public void setPosY(double posY) {
      this.posY = posY;
    }
    
    public double getPosZ() {
      return this.posZ;
    }
    
    public void setPosZ(double posZ) {
      this.posZ = posZ;
    }
    
    public float getRotationYaw() {
      return this.rotationYaw;
    }
    
    public void setRotationYaw(float rotationYaw) {
      this.rotationYaw = rotationYaw;
    }
    
    public float getRotationPitch() {
      return this.rotationPitch;
    }
    
    public void setRotationPitch(float rotationPitch) {
      this.rotationPitch = rotationPitch;
    }
    
    public static float[] getRotation(double posX1, double posY1, double posZ1, double posX2, double posY2, double posZ2) {
      float[] rotation = new float[2];
      double diffX = posX2 - posX1;
      double diffZ = posZ2 - posZ1;
      double diffY = posY2 - posY1;
      double dist = Math.sqrt(diffZ * diffZ + diffX * diffX);
      double pitch = -Math.toDegrees(Math.atan(diffY / dist));
      rotation[1] = (float)pitch;
      double yaw = 0.0D;
      if (diffZ >= 0.0D && diffX >= 0.0D) {
        yaw = Math.toDegrees(-Math.atan(diffX / diffZ));
      } else if (diffZ >= 0.0D && diffX <= 0.0D) {
        yaw = Math.toDegrees(-Math.atan(diffX / diffZ));
      } else if (diffZ <= 0.0D && diffX >= 0.0D) {
        yaw = -90.0D + Math.toDegrees(Math.atan(diffZ / diffX));
      } else if (diffZ <= 0.0D && diffX <= 0.0D) {
        yaw = 90.0D + Math.toDegrees(Math.atan(diffZ / diffX));
      } 
      rotation[0] = (float)yaw;
      return rotation;
    }
  }
  public static BetterRenderUI.ColorUtils colorUtils() {
	    return colorUtils;
	  }
	  
}
