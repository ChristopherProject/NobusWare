package it.nobusware.client.utils;

import org.lwjgl.opengl.GL11;

import java.awt.*;

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

    public static int getColor(Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int brightness) {
        return getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(int red, int green, int blue) {
        return getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }

}
