package it.nobusware.client.render.clickgui.frame.component.impl;

import java.awt.Color;

import it.nobusware.client.render.clickgui.frame.component.Component;
import it.nobusware.client.render.clickgui.utils.RenderUtilCL;
import it.nobusware.client.utils.MouseUtil;
import it.nobusware.client.utils.value.impl.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class BooleanComponent extends Component {
	
  private BooleanValue booleanValue;
  
  public BooleanComponent(BooleanValue booleanValue, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
    super(booleanValue.getLabel(), parentX, parentY, offsetX, offsetY, width, height);
    this.booleanValue = booleanValue;
  }
  
  public void drawScreen(int mouseX, int mouseY, ScaledResolution scaledResolution) {
    super.drawScreen(mouseX, mouseY, scaledResolution);
    RenderUtilCL.drawRect(getFinishedX(), getFinishedY(), getWidth(), getDefaultHeight(), (new Color(0, 0, 0, 120)).getRGB());
    (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(getLabel(), getFinishedX() + getWidth() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.getStringWidth(getLabel()) / 2), getFinishedY() + getHeight() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.FONT_HEIGHT / 2), this.booleanValue.isEnabled() ? -1 : -8355712);
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX(), getFinishedY(), getWidth(), getHeight()) && mouseButton == 0)
      this.booleanValue.setEnabled(!this.booleanValue.isEnabled()); 
  }
  
  public BooleanValue getBooleanValue() {
    return this.booleanValue;
  }
}
