package it.nobusware.client.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class ColorUtilsArray {
	

	    public static Color getRGB(final int speed, final int offset) {
	        return ColorUtilsArray.getRGB(speed, offset, System.currentTimeMillis());
	    }

	    public static Color getRGB(final int speed, final int offset, final long time) {
	        return ColorUtilsArray.getRGB(speed, offset, time, 1.0f);
	    }

	    public static Color getRGB(final int speed, final int offset, final long time, final float s) {
	        float hue = (time + offset) % speed;
	        return Color.getHSBColor(hue / speed, s, 1.0f);
	    }

	    public static Color getRainbow(final int speed, final int offset) {
	        float hue = (System.currentTimeMillis() + offset) % speed;
	        return Color.getHSBColor(hue / speed, 0.5f, 1f);
	    }


	    public static Color glColor(final int hex) {
	        final float alpha = (hex >> 24 & 255) / 256.0f;
	        final float red = (hex >> 16 & 255) / 255.0f;
	        final float green = (hex >> 8 & 255) / 255.0f;
	        final float blue = (hex & 255) / 255.0f;
	        GL11.glColor4f(red, green, blue, alpha);
	        return new Color(red, green, blue, alpha);
	    }

}
