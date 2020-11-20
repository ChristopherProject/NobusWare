package it.nobusware.client.mods;

import org.lwjgl.input.Keyboard;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventRenderer3D;
import it.nobusware.client.manager.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class DrawSexyAnime extends Module {
	
	public DrawSexyAnime(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	static ResourceLocation Skin1;
	static ResourceLocation Skin2;
	static boolean notEnable;
	
	@Handler
	  public void onRender(EventRenderer3D event) {
		DrawSexyAnime.Skin1 = new ResourceLocation("AnimeSkin/Skin01.png");
		DrawSexyAnime.Skin2 = new ResourceLocation("AnimeSkin/Skin02.png");	
	        	for (final Object ok : mc.theWorld.loadedEntityList) {
	        		Entity o = (Entity)ok;
	    			if (o instanceof EntityPlayer) {
	    				final EntityPlayer entity = (EntityPlayer) o;
	    				if (entity == mc.thePlayer) {
	    					continue;
	    				}
	    				
	    				GlStateManager.pushMatrix();
	    				final double n = (float) entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;
	    				final RenderManager renderManager = Minecraft.getMinecraft().renderManager;
	    				final double n2 = n - RenderManager.renderPosX;
	    				final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks;
	    				final RenderManager renderManager2 = Minecraft.getMinecraft().renderManager;
	    				final double n4 = n3 - RenderManager.renderPosY;
	    				final double n5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
	    				final RenderManager renderManager3 = Minecraft.getMinecraft().renderManager;
	    				GlStateManager.translate(n2, n4, n5 - RenderManager.renderPosZ);
	    			      
	    				GlStateManager.rotate(-Minecraft.getMinecraft().renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
	    				GlStateManager.scale(-0.02666667f, -0.02666667f, 0.02666667f);
	    				GlStateManager.disableDepth();
	    				GlStateManager.disableLighting();
	    				
	    				//magic was here

	    				if (entity.hurtTime != 0) {
	    					  this.mc.getTextureManager().bindTexture(Skin2);
		    			      Gui.drawModalRectWithCustomSizedTexture(-45, -79, 1.0F, 1.0F, 96, 96, 96.0F, 96.0F);
	    				}else {
	    					  this.mc.getTextureManager().bindTexture(Skin1);
		    			      Gui.drawModalRectWithCustomSizedTexture(-45, -79, 1.0F, 1.0F, 96, 96, 96.0F, 96.0F);
	    				}
	    				 
	    			      
				Gui.drawRect(0.0, 0.0, (float) 0.0, 0.0, -1);
				GlStateManager.enableDepth();
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				GlStateManager.popMatrix();

			}
		}
	}
}