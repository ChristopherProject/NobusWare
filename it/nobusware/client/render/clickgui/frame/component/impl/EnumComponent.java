package it.nobusware.client.render.clickgui.frame.component.impl;

import java.awt.Color;

import org.apache.commons.lang3.StringUtils;

import it.nobusware.client.render.clickgui.frame.component.Component;
import it.nobusware.client.render.clickgui.utils.RenderUtilCL;
import it.nobusware.client.utils.MouseUtil;
import it.nobusware.client.utils.value.impl.EnumValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class EnumComponent extends Component {
  private EnumValue enumValue;
  
  public EnumComponent(EnumValue enumValue, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
    super(enumValue.getLabel(), parentX, parentY, offsetX, offsetY, width, height);
    this.enumValue = enumValue;
  }
  
  public void drawScreen(int mouseX, int mouseY, ScaledResolution scaledResolution) {
    super.drawScreen(mouseX, mouseY, scaledResolution);
    RenderUtilCL.drawRect(getFinishedX(), getFinishedY(), getWidth(), getDefaultHeight(), (new Color(0, 0, 0, 120)).getRGB());
    (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(getLabel() + ": " + StringUtils.capitalize(getEnumValue().getValue().toString().toLowerCase()), getFinishedX() + getWidth() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.getStringWidth(getLabel() + ": " + StringUtils.capitalize(getEnumValue().getValue().toString().toLowerCase())) / 2), getFinishedY() + getHeight() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.FONT_HEIGHT / 2), -1);
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX(), getFinishedY(), getWidth(), getHeight()))
      if (mouseButton == 0) {
        this.enumValue.increment();
      } else if (mouseButton == 1) {
        this.enumValue.decrement();
      }  
  }
  
  public EnumValue getEnumValue() {
    return this.enumValue;
  }
}
