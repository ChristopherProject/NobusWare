package it.nobusware.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

public class UtilsGraphicalEntity {
	
	 public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
	    {
	        GlStateManager.enableColorMaterial();
	        GlStateManager.pushMatrix();
	        GlStateManager.translate((float)p_147046_0_, (float)p_147046_1_, 50.0F);
	        GlStateManager.scale((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
	        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
	        float var6 = p_147046_5_.renderYawOffset;
	        float var7 = p_147046_5_.rotationYaw;
	        float var8 = p_147046_5_.rotationPitch;
	        float var9 = p_147046_5_.prevRotationYawHead;
	        float var10 = p_147046_5_.rotationYawHead;
	        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
	        RenderHelper.enableStandardItemLighting();
	        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
	        p_147046_5_.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 20.0F;
	        p_147046_5_.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 40.0F;
	        p_147046_5_.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F;
	        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
	        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
	        GlStateManager.translate(0.0F, 0.0F, 0.0F);
	        RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
	        var11.func_178631_a(180.0F);
	        var11.func_178633_a(false);
	        var11.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
	        var11.func_178633_a(true);
	        p_147046_5_.renderYawOffset = var6;
	        p_147046_5_.rotationYaw = var7;
	        p_147046_5_.rotationPitch = var8;
	        p_147046_5_.prevRotationYawHead = var9;
	        p_147046_5_.rotationYawHead = var10;
	        GlStateManager.popMatrix();
	        RenderHelper.disableStandardItemLighting();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.func_179090_x();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	    }

}
