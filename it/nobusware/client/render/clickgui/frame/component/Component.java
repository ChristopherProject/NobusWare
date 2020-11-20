package it.nobusware.client.render.clickgui.frame.component;

import net.minecraft.client.gui.ScaledResolution;

public class Component {
  private float parentX;
  
  private float parentY;
  
  private float offsetX;
  
  private float offsetY;
  
  private float finishedX;
  
  private float finishedY;
  
  private float width;
  
  private float height;
  
  private float defaultHeight;
  
  private String label;
  
  public Component(String label, float parentX, float parentY, float offsetX, float offsetY, float width, float height) {
    this.label = label;
    this.parentX = parentX;
    this.parentY = parentY;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.finishedX = parentX + offsetX;
    this.finishedY = parentY + offsetY;
    this.width = width;
    this.height = height;
    this.defaultHeight = height;
  }
  
  public void updatePosition(float posX, float posY) {
    this.parentX = posX;
    this.parentY = posY;
    this.finishedX = posX + this.offsetX;
    this.finishedY = posY + this.offsetY;
  }
  
  public void drawScreen(int mouseX, int mouseY, ScaledResolution scaledResolution) {}
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
  
  public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}
  
  public void keyTyped(char typedChar, int keyCode) {}
  
  public void init() {}
  
  public float getParentX() {
    return this.parentX;
  }
  
  public void setParentX(float parentX) {
    this.parentX = parentX;
  }
  
  public float getParentY() {
    return this.parentY;
  }
  
  public void setParentY(float parentY) {
    this.parentY = parentY;
  }
  
  public float getFinishedX() {
    return this.finishedX;
  }
  
  public void setFinishedX(float finishedX) {
    this.finishedX = finishedX;
  }
  
  public float getFinishedY() {
    return this.finishedY;
  }
  
  public void setFinishedY(float finishedY) {
    this.finishedY = finishedY;
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
  
  public float getOffsetX() {
    return this.offsetX;
  }
  
  public void setOffsetX(float offsetX) {
    this.offsetX = offsetX;
  }
  
  public float getOffsetY() {
    return this.offsetY;
  }
  
  public void setOffsetY(float offsetY) {
    this.offsetY = offsetY;
  }
  
  public float getDefaultHeight() {
    return this.defaultHeight;
  }
}
