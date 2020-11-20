package it.nobusware.client.render.clickgui.frame.component.impl;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import it.nobusware.client.render.clickgui.frame.component.Component;
import it.nobusware.client.render.clickgui.utils.RenderUtilCL;
import it.nobusware.client.utils.MouseUtil;
import it.nobusware.client.utils.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;

public class NumberComponent extends Component {
  private NumberValue numberValue;
  
  private boolean sliding;
  
  public NumberComponent(NumberValue numberValue, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
    super(numberValue.getLabel(), parentX, parentY, offsetX, offsetY, width, height);
    this.numberValue = numberValue;
  }
  
  public void drawScreen(int mouseX, int mouseY, ScaledResolution scaledResolution) {
    super.drawScreen(mouseX, mouseY, scaledResolution);
    float startX = getFinishedX() + 4.5F;
    float sliderwidth = getWidth() - 9.0F;
    float length = MathHelper.floor_double(((this.numberValue.getValue().floatValue() - this.numberValue.getMinimum().floatValue()) / (this.numberValue.getMaximum().floatValue() - this.numberValue.getMinimum().floatValue()) * sliderwidth));
    RenderUtilCL.drawRect(getFinishedX(), getFinishedY(), getWidth(), getDefaultHeight(), (new Color(0, 0, 0, 120)).getRGB());
    RenderUtilCL.drawBorderedRect((getFinishedX() + 4.0F), getFinishedY(), (getWidth() - 8.0F), getHeight(), (float) 0.5D, (new Color(0, 0, 0)).getRGB(), 0);
    RenderUtilCL.drawRect(getFinishedX() + 4.5D, getFinishedY() + 0.5D, length, (getHeight() - 1.0F), 0xff67ECC3);
    (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(getLabel() + ": " + this.numberValue.getValue(), getFinishedX() + getWidth() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.getStringWidth(getLabel() + ": " + this.numberValue.getValue()) / 2), getFinishedY() + 3.0F, -1);
    if (this.sliding) {
      if (this.numberValue.getValue() instanceof Double)
        this.numberValue.setValue(Double.valueOf(round((mouseX - startX) * (this.numberValue.getMaximum().doubleValue() - this.numberValue.getMinimum().doubleValue()) / sliderwidth + this.numberValue.getMinimum().doubleValue(), 2))); 
      if (this.numberValue.getValue() instanceof Float)
        this.numberValue.setValue(Float.valueOf((float)round(((mouseX - startX) * (this.numberValue.getMaximum().floatValue() - this.numberValue.getMinimum().floatValue()) / sliderwidth + this.numberValue.getMinimum().floatValue()), 2))); 
      if (this.numberValue.getValue() instanceof Long)
        this.numberValue.setValue(Long.valueOf((long)round(((mouseX - startX) * (float)(this.numberValue.getMaximum().longValue() - this.numberValue.getMinimum().longValue()) / sliderwidth + (float)this.numberValue.getMinimum().longValue()), 2))); 
      if (this.numberValue.getValue() instanceof Integer)
        this.numberValue.setValue(Integer.valueOf((int)round(((mouseX - startX) * (this.numberValue.getMaximum().intValue() - this.numberValue.getMinimum().intValue()) / sliderwidth + this.numberValue.getMinimum().intValue()), 2))); 
      if (this.numberValue.getValue() instanceof Short)
        this.numberValue.setValue(Short.valueOf((short)(int)round(((mouseX - startX) * (this.numberValue.getMaximum().shortValue() - this.numberValue.getMinimum().shortValue()) / sliderwidth + this.numberValue.getMinimum().shortValue()), 2))); 
      if (this.numberValue.getValue() instanceof Byte)
        this.numberValue.setValue(Byte.valueOf((byte)(int)round(((mouseX - startX) * (this.numberValue.getMaximum().byteValue() - this.numberValue.getMinimum().byteValue()) / sliderwidth + this.numberValue.getMinimum().byteValue()), 2))); 
    } 
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (MouseUtil.mouseWithinBounds(mouseX, mouseY, (getFinishedX() + 4.0F), getFinishedY(), (getWidth() - 8.0F), getHeight()) && mouseButton == 0)
      this.sliding = true; 
  }
  
  public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    super.mouseReleased(mouseX, mouseY, mouseButton);
    if (this.sliding)
      this.sliding = false; 
  }
  
  private double round(double val, int places) {
    double value = Math.round(val / this.numberValue.getInc().doubleValue()) * this.numberValue.getInc().doubleValue();
    if (places < 0)
      throw new IllegalArgumentException(); 
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
