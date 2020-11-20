package it.nobusware.client.render.clickgui.frame;

import java.awt.Color;
import java.util.ArrayList;

import it.nobusware.client.render.clickgui.frame.component.Component;
import it.nobusware.client.render.clickgui.utils.RenderUtilCL;
import it.nobusware.client.utils.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Frame {
  private float posX;
  
  private float posY;
  
  private float lastPosX;
  
  private float lastPosY;
  
  private float width;
  
  private float height;
  
  private String label;
  
  private boolean dragging;
  
  private boolean extended;
  
  private ArrayList<Component> components = new ArrayList<>();
  
  public Frame(String label, float posX, float posY, float width, float height) {
    this.label = label;
    this.posX = posX;
    this.posY = posY;
    this.width = width;
    this.height = height;
  }
  
  public void init() {
    this.components.forEach(Component::init);
  }
  
  public void updatePosition() {
    this.components.forEach(component -> component.updatePosition(getPosX(), getPosY()));
  }
  
  public void drawScreen(int mouseX, int mouseY, ScaledResolution scaledResolution) {
   RenderUtilCL.drawRect(this.posX, this.posY, this.width, this.height,0xff67ECC3);
    (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(getLabel(), getPosX() + getWidth() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.getStringWidth(getLabel()) / 2), getPosY() + getHeight() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.FONT_HEIGHT / 2), -1);
    if (isDragging()) {
      setPosX(mouseX + getLastPosX());
      setPosY(mouseY + getLastPosY());
      if (getPosX() < 0.0F)
        setPosX(0.0F); 
      if (getPosX() + getWidth() > scaledResolution.getScaledWidth())
        setPosX(scaledResolution.getScaledWidth() - getWidth()); 
      if (getPosY() < 0.0F)
        setPosY(0.0F); 
      if (getPosY() + getHeight() > scaledResolution.getScaledHeight())
        setPosY(scaledResolution.getScaledHeight() - getHeight()); 
      updatePosition();
    } 
    if (isExtended())
      this.components.forEach(component -> component.drawScreen(mouseX, mouseY, scaledResolution)); 
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    boolean hovered = MouseUtil.mouseWithinBounds(mouseX, mouseY, getPosX(), getPosY(), getWidth(), getHeight());
    switch (mouseButton) {
      case 0:
        if (hovered) {
          setDragging(true);
          setLastPosX(getPosX() - mouseX);
          setLastPosY(getPosY() - mouseY);
        } 
        break;
      case 1:
        if (hovered)
          setExtended(!isExtended()); 
        break;
    } 
    if (isExtended())
      this.components.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton)); 
  }
  
  public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    switch (mouseButton) {
      case 0:
        if (isDragging())
          setDragging(false); 
        break;
    } 
    if (isExtended())
      this.components.forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton)); 
  }
  
  public void keyTyped(char typedChar, int keyCode) {
    if (isExtended())
      this.components.forEach(component -> component.keyTyped(typedChar, keyCode)); 
  }
  
  public ArrayList<Component> getComponents() {
    return this.components;
  }
  
  public float getPosX() {
    return this.posX;
  }
  
  public void setPosX(float posX) {
    this.posX = posX;
  }
  
  public float getPosY() {
    return this.posY;
  }
  
  public void setPosY(float posY) {
    this.posY = posY;
  }
  
  public float getLastPosX() {
    return this.lastPosX;
  }
  
  public void setLastPosX(float lastPosX) {
    this.lastPosX = lastPosX;
  }
  
  public float getLastPosY() {
    return this.lastPosY;
  }
  
  public void setLastPosY(float lastPosY) {
    this.lastPosY = lastPosY;
  }
  
  public float getWidth() {
    return this.width;
  }
  
  public void setWidth(float width) {
    this.width = width;
  }
  
  public float getHeight() {
    return this.height;
  }
  
  public void setHeight(float height) {
    this.height = height;
  }
  
  public String getLabel() {
    return this.label;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }
  
  public boolean isDragging() {
    return this.dragging;
  }
  
  public void setDragging(boolean dragging) {
    this.dragging = dragging;
  }
  
  public boolean isExtended() {
    return this.extended;
  }
  
  public void setExtended(boolean extended) {
    this.extended = extended;
  }
}
