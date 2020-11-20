package it.nobusware.client.mods;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.RenderEntityEvent;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ColorUtilsArray;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;

public class Chams extends Module{
	
	public Chams(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
    public Consumer<RenderEntityEvent> onEvent = (event) -> {
        if (event.pre) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -2000000F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            Color color = ColorUtilsArray.glColor(ColorUtilsArray.getRainbow(-8000, (int)event.getEntityLivingBase().getEyeHeight() *  -32).getRGB());//ColorUtilsArray.glColor(0xff66FFFF);
            GlStateManager.enableLighting();
            FloatBuffer floatBuffer = GLAllocation.createDirectFloatBuffer(16);
            floatBuffer.put(1.0f).put(1.0f).put(1.0f).put(1.0f);
            floatBuffer.flip();
            GL11.glLight(GL11.GL_LIGHT7, GL11.GL_SPECULAR, floatBuffer);
            floatBuffer.clear();
            FloatBuffer pointer = GLAllocation.createDirectFloatBuffer(16);
            pointer.put(color.getRed() / 255.0f).put(color.getGreen() / 255.0f).put(color.getBlue() / 255.0f).put(1.0F);
            pointer.flip();
            GL11.glColorPointer(4, GL11.GL_COLOR_ARRAY_STRIDE, pointer);
            pointer.clear();
            GlStateManager.disableLighting();
        } else {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPolygonOffset(1.0F, 2000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPopMatrix();
        }
    };

}
