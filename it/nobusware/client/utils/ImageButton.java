package it.nobusware.client.utils;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ImageButton extends GuiButton
{
    private String src;
    
    public ImageButton(final int buttonId, final int x, final int y, final String buttonText, final String src) {
        this(buttonId, x, y, 200, 20, buttonText, src);
    }
    
    public ImageButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText, final String src) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.src = src;
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int x, final int y) {
        if (this.visible) {
            this.hovered = (x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height);
            if (!this.src.trim().isEmpty()) {
                if (this.hovered) {
                    RenderHelper.renderImage(this.src, this.xPosition + 2, this.yPosition - 2, this.width, this.height);
                }
                else {
                    RenderHelper.renderImage(this.src, this.xPosition, this.yPosition, this.width, this.height);
                }
            }
            this.drawString(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2 - mc.fontRendererObj.getStringWidth(this.displayString) / 2, this.yPosition + this.height + 2, Color.white.getRGB());
        }
    }
    
    public String getSrc() {
        return this.src;
    }
    
    public void setSrc(final String src) {
        this.src = src;
    }
}
