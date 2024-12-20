package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public abstract class GuiSlot
{
    protected final Minecraft mc;
    protected int width;
    protected int height;

    /** The top of the slot container. Affects the overlays and scrolling. */
    protected int top;

    /** The bottom of the slot container. Affects the overlays and scrolling. */
    protected int bottom;
    protected int right;
    protected int left;

    /** The height of a slot. */
    protected final int slotHeight;

    /** The buttonID of the button used to scroll up */
    private int scrollUpButtonID;

    /** The buttonID of the button used to scroll down */
    private int scrollDownButtonID;
    protected int mouseX;
    protected int mouseY;
    protected boolean field_148163_i = true;

    /** Where the mouse was in the window when you first clicked to scroll */
    protected float initialClickY = -2.0F;

    /**
     * What to multiply the amount you moved your mouse by (used for slowing down scrolling when over the items and not
     * on the scroll bar)
     */
    protected float scrollMultiplier;

    /** How far down this slot has been scrolled */
    protected float amountScrolled;

    /** The element in the list that was selected */
    protected int selectedElement = -1;

    /** The time when this button was last clicked. */
    protected long lastClicked;
    protected boolean field_178041_q = true;

    /**
     * Set to true if a selected element in this gui will show an outline box
     */
    protected boolean showSelectionBox = true;
    protected boolean hasListHeader;
    protected int headerPadding;
    private boolean enabled = true;
    private static final String __OBFID = "CL_00000679";

    public GuiSlot(Minecraft mcIn, int width, int height, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_)
    {
        this.mc = mcIn;
        this.width = width;
        this.height = height;
        this.top = p_i1052_4_;
        this.bottom = p_i1052_5_;
        this.slotHeight = p_i1052_6_;
        this.left = 0;
        this.right = width;
    }

    public void setDimensions(int p_148122_1_, int p_148122_2_, int p_148122_3_, int p_148122_4_)
    {
        this.width = p_148122_1_;
        this.height = p_148122_2_;
        this.top = p_148122_3_;
        this.bottom = p_148122_4_;
        this.left = 0;
        this.right = p_148122_1_;
    }

    public void setShowSelectionBox(boolean p_148130_1_)
    {
        this.showSelectionBox = p_148130_1_;
    }

    /**
     * Sets hasListHeader and headerHeight. Params: hasListHeader, headerHeight. If hasListHeader is false headerHeight
     * is set to 0.
     */
    protected void setHasListHeader(boolean p_148133_1_, int p_148133_2_)
    {
        this.hasListHeader = p_148133_1_;
        this.headerPadding = p_148133_2_;

        if (!p_148133_1_)
        {
            this.headerPadding = 0;
        }
    }

    protected abstract int getSize();

    /**
     * The element in the slot that was clicked, boolean for whether it was double clicked or not
     */
    protected abstract void elementClicked(int var1, boolean var2, int var3, int var4);

    /**
     * Returns true if the element passed in is currently selected
     */
    protected abstract boolean isSelected(int var1);

    /**
     * Return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return this.getSize() * this.slotHeight + this.headerPadding;
    }

    protected abstract void drawBackground();

    protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_) {}

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6);

    /**
     * Handles drawing a list's header row.
     */
    protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {}

    protected void func_148132_a(int p_148132_1_, int p_148132_2_) {}

    protected void func_148142_b(int p_148142_1_, int p_148142_2_) {}

    public int getSlotIndexFromScreenCoords(int p_148124_1_, int p_148124_2_)
    {
        int var3 = this.left + this.width / 2 - this.getListWidth() / 2;
        int var4 = this.left + this.width / 2 + this.getListWidth() / 2;
        int var5 = p_148124_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
        int var6 = var5 / this.slotHeight;
        return p_148124_1_ < this.getScrollBarX() && p_148124_1_ >= var3 && p_148124_1_ <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize() ? var6 : -1;
    }

    /**
     * Registers the IDs that can be used for the scrollbar's up/down buttons.
     */
    public void registerScrollButtons(int p_148134_1_, int p_148134_2_)
    {
        this.scrollUpButtonID = p_148134_1_;
        this.scrollDownButtonID = p_148134_2_;
    }

    /**
     * Stop the thing from scrolling out of bounds
     */
    protected void bindAmountScrolled()
    {
        int var1 = this.func_148135_f();

        if (var1 < 0)
        {
            var1 /= 2;
        }

        if (!this.field_148163_i && var1 < 0)
        {
            var1 = 0;
        }

        this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0F, (float)var1);
    }

    public int func_148135_f()
    {
        return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4));
    }

    /**
     * Returns the amountScrolled field as an integer.
     */
    public int getAmountScrolled()
    {
        return (int)this.amountScrolled;
    }

    public boolean isMouseYWithinSlotBounds(int p_148141_1_)
    {
        return p_148141_1_ >= this.top && p_148141_1_ <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right;
    }

    /**
     * Scrolls the slot by the given amount. A positive value scrolls down, and a negative value scrolls up.
     */
    public void scrollBy(int p_148145_1_)
    {
        this.amountScrolled += (float)p_148145_1_;
        this.bindAmountScrolled();
        this.initialClickY = -2.0F;
    }

    public void actionPerformed(GuiButton p_148147_1_)
    {
        if (p_148147_1_.enabled)
        {
            if (p_148147_1_.id == this.scrollUpButtonID)
            {
                this.amountScrolled -= (float)(this.slotHeight * 2 / 3);
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            }
            else if (p_148147_1_.id == this.scrollDownButtonID)
            {
                this.amountScrolled += (float)(this.slotHeight * 2 / 3);
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            }
        }
    }
    
    public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
        if (this.field_178041_q) {
        	
          this.mouseX = mouseXIn;
          this.mouseY = mouseYIn;
      
          int i = getScrollBarX();
          int j = i + 6;
          this.bindAmountScrolled();
          GlStateManager.disableLighting();
          GlStateManager.disableFog();
          Tessellator tessellator = Tessellator.getInstance();
          WorldRenderer worldrenderer = tessellator.getWorldRenderer();
          //this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
         // GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
          float f = 32.0F;

          int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
          int l = this.top + 4 - (int)this.amountScrolled;
          if (this.hasListHeader)
          drawListHeader(k, l, tessellator); 
          drawSelectionBox(k, l, mouseXIn, mouseYIn);
          GlStateManager.disableDepth();
          int i1 = 4;
          //overlayBackground(0, this.top, 255, 255);
          overlayBackground(this.bottom, this.height, 255, 255);
          GlStateManager.enableBlend();
          GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
          GlStateManager.disableAlpha();
          GlStateManager.shadeModel(7425);
          GlStateManager.func_179090_x();
   
          int j1 = func_148135_f();
          if (j1 > 0) {
            int k1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
            k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
            int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
            if (l1 < this.top)
              l1 = this.top; 
            worldrenderer.startDrawingQuads();
            worldrenderer.func_178974_a(0, 255);
            worldrenderer.addVertexWithUV((double)i, (double)this.bottom, 0.0D, 0.0D, 1.0D);
            worldrenderer.addVertexWithUV((double)j, (double)this.bottom, 0.0D, 1.0D, 1.0D);
            worldrenderer.addVertexWithUV((double)j, (double)this.top, 0.0D, 1.0D, 0.0D);
            worldrenderer.addVertexWithUV((double)i, (double)this.top, 0.0D, 0.0D, 0.0D);
            worldrenderer.draw();
            worldrenderer.startDrawingQuads();
            worldrenderer.func_178974_a(8421504, 255);
            worldrenderer.addVertexWithUV((double)i, (double)(l1 + k1), 0.0D, 0.0D, 1.0D);
            worldrenderer.addVertexWithUV((double)j, (double)(l1 + k1), 0.0D, 1.0D, 1.0D);
            worldrenderer.addVertexWithUV((double)j, (double)l1, 0.0D, 1.0D, 0.0D);
            worldrenderer.addVertexWithUV((double)i, (double)l1, 0.0D, 0.0D, 0.0D);
            worldrenderer.draw();
            worldrenderer.startDrawingQuads();
            worldrenderer.func_178974_a(12632256, 255);
            worldrenderer.addVertexWithUV((double)i, (double)(l1 + k1 - 1), 0.0D, 0.0D, 1.0D);
            worldrenderer.addVertexWithUV((double)(j - 1), (double)(l1 + k1 - 1), 0.0D, 1.0D, 1.0D);
            worldrenderer.addVertexWithUV((double)(j - 1), (double)l1, 0.0D, 1.0D, 0.0D);
            worldrenderer.addVertexWithUV((double)i, (double)l1, 0.0D, 0.0D, 0.0D);
            tessellator.draw();
          } 
          func_148142_b(mouseXIn, mouseYIn);
          GlStateManager.func_179098_w();
          GlStateManager.shadeModel(7424);
          GlStateManager.enableAlpha();
          GlStateManager.disableBlend();
        } 
    }

    public void func_178039_p()
    {
        if (this.isMouseYWithinSlotBounds(this.mouseY))
        {
            if (Mouse.isButtonDown(0) && this.getEnabled())
            {
                if (this.initialClickY == -1.0F)
                {
                    boolean var1 = true;

                    if (this.mouseY >= this.top && this.mouseY <= this.bottom)
                    {
                        int var2 = this.width / 2 - this.getListWidth() / 2;
                        int var3 = this.width / 2 + this.getListWidth() / 2;
                        int var4 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                        int var5 = var4 / this.slotHeight;

                        if (this.mouseX >= var2 && this.mouseX <= var3 && var5 >= 0 && var4 >= 0 && var5 < this.getSize())
                        {
                            boolean var6 = var5 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                            this.elementClicked(var5, var6, this.mouseX, this.mouseY);
                            this.selectedElement = var5;
                            this.lastClicked = Minecraft.getSystemTime();
                        }
                        else if (this.mouseX >= var2 && this.mouseX <= var3 && var4 < 0)
                        {
                            this.func_148132_a(this.mouseX - var2, this.mouseY - this.top + (int)this.amountScrolled - 4);
                            var1 = false;
                        }

                        int var11 = this.getScrollBarX();
                        int var7 = var11 + 6;

                        if (this.mouseX >= var11 && this.mouseX <= var7)
                        {
                            this.scrollMultiplier = -1.0F;
                            int var8 = this.func_148135_f();

                            if (var8 < 1)
                            {
                                var8 = 1;
                            }

                            int var9 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());
                            var9 = MathHelper.clamp_int(var9, 32, this.bottom - this.top - 8);
                            this.scrollMultiplier /= (float)(this.bottom - this.top - var9) / (float)var8;
                        }
                        else
                        {
                            this.scrollMultiplier = 1.0F;
                        }

                        if (var1)
                        {
                            this.initialClickY = (float)this.mouseY;
                        }
                        else
                        {
                            this.initialClickY = -2.0F;
                        }
                    }
                    else
                    {
                        this.initialClickY = -2.0F;
                    }
                }
                else if (this.initialClickY >= 0.0F)
                {
                    this.amountScrolled -= ((float)this.mouseY - this.initialClickY) * this.scrollMultiplier;
                    this.initialClickY = (float)this.mouseY;
                }
            }
            else
            {
                this.initialClickY = -1.0F;
            }

            int var10 = Mouse.getEventDWheel();

            if (var10 != 0)
            {
                if (var10 > 0)
                {
                    var10 = -1;
                }
                else if (var10 < 0)
                {
                    var10 = 1;
                }

                this.amountScrolled += (float)(var10 * this.slotHeight / 2);
            }
        }
    }

    public void setEnabled(boolean p_148143_1_)
    {
        this.enabled = p_148143_1_;
    }

    public boolean getEnabled()
    {
        return this.enabled;
    }

    /**
     * Gets the width of the list
     */
    public int getListWidth()
    {
        return 220;
    }

    /**
     * Draws the selection box around the selected slot element.
     */
    protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int p_148120_3_, int p_148120_4_)
    {
        int var5 = this.getSize();
        Tessellator var6 = Tessellator.getInstance();
        WorldRenderer var7 = var6.getWorldRenderer();

        for (int var8 = 0; var8 < var5; ++var8)
        {
            int var9 = p_148120_2_ + var8 * this.slotHeight + this.headerPadding;
            int var10 = this.slotHeight - 4;

            if (var9 > this.bottom || var9 + var10 < this.top)
            {
                this.func_178040_a(var8, p_148120_1_, var9);
            }

            if (this.showSelectionBox && this.isSelected(var8))
            {
                int var11 = this.left + (this.width / 2 - this.getListWidth() / 2);
                int var12 = this.left + this.width / 2 + this.getListWidth() / 2;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.func_179090_x();
                var7.startDrawingQuads();
                var7.func_178991_c(8421504);
                var7.addVertexWithUV((double)var11, (double)(var9 + var10 + 2), 0.0D, 0.0D, 1.0D);
                var7.addVertexWithUV((double)var12, (double)(var9 + var10 + 2), 0.0D, 1.0D, 1.0D);
                var7.addVertexWithUV((double)var12, (double)(var9 - 2), 0.0D, 1.0D, 0.0D);
                var7.addVertexWithUV((double)var11, (double)(var9 - 2), 0.0D, 0.0D, 0.0D);
                var7.func_178991_c(0);
                var7.addVertexWithUV((double)(var11 + 1), (double)(var9 + var10 + 1), 0.0D, 0.0D, 1.0D);
                var7.addVertexWithUV((double)(var12 - 1), (double)(var9 + var10 + 1), 0.0D, 1.0D, 1.0D);
         
                var6.draw();
                GlStateManager.func_179098_w();
            }

            this.drawSlot(var8, p_148120_1_, var9, var10, p_148120_3_, p_148120_4_);
        }
    }

    protected int getScrollBarX()
    {
        return this.width / 2 + 124;
    }

    /**
     * Overlays the background to hide scrolled items
     */
    protected void overlayBackground(int p_148136_1_, int p_148136_2_, int p_148136_3_, int p_148136_4_)
    {
    	
    	ScaledResolution s1 = new ScaledResolution(this.mc, this.mc.displayWidth,this.mc.displayHeight);
        
        Tessellator var5 = Tessellator.getInstance();
        WorldRenderer var6 = var5.getWorldRenderer();
       
        // this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GuiMultiplayer.drawRect(0.0F, (s1.getScaledHeight() - 64), s1.getScaledWidth(), s1.getScaledHeight(), -1156483648);
        
        float var7 = 32.0F;
       
    }

    /**
     * Sets the left and right bounds of the slot. Param is the left bound, right is calculated as left + width.
     */
    public void setSlotXBoundsFromLeft(int p_148140_1_)
    {
        this.left = p_148140_1_;
        this.right = p_148140_1_ + this.width;
    }

    public int getSlotHeight()
    {
        return this.slotHeight;
    }
}
