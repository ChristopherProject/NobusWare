package it.nobusware.client.utils.render;

import java.awt.Color;
import java.util.function.Supplier;

public enum Palette {
  GREEN(() -> new Color(0, 255, 138)),
  WHITE(() -> Color.WHITE),
  PURPLE(() -> new Color(198, 139, 255)),
  DARK_PURPLE(() -> new Color(133, 46, 215)),
  BLUE(() -> new Color(116, 202, 255));
  
  private final Supplier<Color> colorSupplier;
  
  Palette(Supplier<Color> colorSupplier) {
    this.colorSupplier = colorSupplier;
  }
  
  public static Color fade(Color color) {
    return fade(color, 2, 100);
  }
  
  public static Color fade(Color color, int index, int count) {
    float[] hsb = new float[3];
    Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
    float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + index / count * 2.0F) % 2.0F - 1.0F);
    brightness = 0.5F + 0.5F * brightness;
    hsb[2] = brightness % 2.0F;
    return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
  }
  
  public Color getColor() {
    return this.colorSupplier.get();
  }
}
