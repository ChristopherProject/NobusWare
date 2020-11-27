package it.nobusware.client.utils;

import java.awt.Color;

import com.github.creeper123123321.viafabric.util.ProtocolSorter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class VerSwitcherButton extends GuiButton {
	
	public int darkBlue = 0xff009ef9;
	
	  private boolean leftHover;
	  
	  private boolean rightHover;
	  
	  private int hoverState;
	  
	  private int hoverState2;
	  
	  private long lastTime;
	  
	  public VerSwitcherButton(int buttonId, int x, int y) {
	    super(buttonId, x, y, "Version");
	    this.lastTime = System.currentTimeMillis();
	  }
	  
	  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
	    if (this.visible) {
	      FontRenderer fontrenderer = mc.fontRendererObj;
	      this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
	      if (this.hovered && this.hoverState < 50) {
	        this.hoverState += 4;
	      } else if (!this.hovered) {
	        this.hoverState = 0;
	      } 
	      Color color = new Color(-1375731712, true);
	      for (int y = 0; y < this.hoverState / 7; y++)
	        color = color.brighter(); 
	      if (!this.hovered)
	        this.lastTime = System.currentTimeMillis(); 
	      if (this.hovered && this.hoverState2 < this.width / 2 && System.currentTimeMillis() - this.lastTime >= 6L) {
	        this.hoverState2 = (int)(this.hoverState2 + (System.currentTimeMillis() - this.lastTime) / 6L);
	        this.lastTime = System.currentTimeMillis();
	      } 
	      if (this.hoverState2 > 100 || !this.hovered)
	        this.hoverState2 = 0; 
	      drawRect((this.xPosition - 50 + 89), (this.yPosition + this.height), (this.xPosition + this.width - 36), this.yPosition, darkBlue);
	      mouseDragged(mc, mouseX, mouseY);
	      int textColor = 14737632;
	      boolean rightHovered = (mouseX >= this.xPosition + 10 + 30 && mouseY >= this.yPosition && mouseX < this.xPosition + this.width - 21 - 30 && mouseY < this.yPosition + this.height);
	      boolean leftHovered = (mouseX >= this.xPosition + 21 + 30 && mouseY >= this.yPosition && mouseX < this.xPosition + this.width - 10 - 30 && mouseY < this.yPosition + this.height);
	      int j2 = 7829367;
	      this.rightHover = false;
	      if (this.enabled && 
	        !rightHovered && leftHovered) {
	        this.rightHover = true;
	        j2 = Color.GREEN.getRGB();
	      } 
	      int j3 = 7829367;
	      this.leftHover = false;
	      if (this.enabled && 
	        !leftHovered && rightHovered) {
	        this.leftHover = true;
	        j3 = Color.GREEN.getRGB();
	      } 
	      Color c = new Color(137, 0, 255);
	      drawCenteredString(fontrenderer, "" + ProtocolSorter.getProtocolName(ProtocolManager.protocol) + "", this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 0xfffff);
	      drawCenteredString(fontrenderer, "<", this.xPosition + this.width / 2 - 85 + 32, this.yPosition + (this.height - 8) / 2, j3);
	      drawCenteredString(fontrenderer, ">", this.xPosition + this.width / 2 + 85 - 30, this.yPosition + (this.height - 8) / 2, j2);
	    } 
	  }
	  
	  public void buttonPressed() {
	    if (this.leftHover) {
	      previousProtocol();
	    } else if (this.rightHover) {
	      nextProtocol();
	    } 
	  }
	  
	  private void previousProtocol() {
	    int next;
	    try {
	      next = ProtocolSorter.getProtocolFromIndex(ProtocolSorter.getIndexOfProtocol(ProtocolManager.protocol) + 1);
	    } catch (Throwable ignored) {
	      next = ProtocolSorter.getProtocolFromIndex(0);
	    } 
	    ProtocolManager.protocol = next;
	  }
	  
	  private void nextProtocol() {
	    int next;
	    try {
	      next = ProtocolSorter.getProtocolFromIndex(ProtocolSorter.getIndexOfProtocol(ProtocolManager.protocol) - 1);
	    } catch (Throwable ignored) {
	      next = ProtocolSorter.getProtocolFromIndex(ProtocolSorter.getProtocols().size() - 1);
	    } 
	    ProtocolManager.protocol = next;
	  }
	}
