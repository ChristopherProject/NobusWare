package it.nobusware.client.utils.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;

public final class RenderUtils {
  private static final Minecraft mc = Minecraft.getMinecraft();
  
  private static final net.minecraft.client.renderer.culling.Frustrum frustrum = new net.minecraft.client.renderer.culling.Frustrum();
  
  public static double interpolate(double current, double old, double scale) {
    return old + (current - old) * scale;
  }
  
  public static boolean isInViewFrustrum(Entity entity) {
    return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
  }
  
  private static boolean isInViewFrustrum(AxisAlignedBB bb) {
    Entity current = mc.func_175606_aa();
    frustrum.setPosition(current.posX, current.posY, current.posZ);
    return frustrum.isBoundingBoxInFrustum(bb);
  }
 
  
  public static void startSmooth() {
    GL11.glEnable(2848);
    GL11.glEnable(2881);
    GL11.glEnable(2832);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glHint(3154, 4354);
    GL11.glHint(3155, 4354);
    GL11.glHint(3153, 4354);
  }
  
  public static void endSmooth() {
    GL11.glDisable(2848);
    GL11.glDisable(2881);
    GL11.glEnable(2832);
  }
  public static void prepareScissorBox(float x, float y, float x2, float y2) {
	    ScaledResolution scale = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	    int factor = scale.getScaleFactor();
	    GL11.glScissor((int)(x * factor), (int)((scale.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
	  }
}
