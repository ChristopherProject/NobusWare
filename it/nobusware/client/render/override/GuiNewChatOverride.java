package it.nobusware.client.render.override;

import org.lwjgl.opengl.GL11;

import it.nobusware.client.utils.RenderUtils;
import it.nobusware.client.utils.fontmanager.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class GuiNewChatOverride extends GuiNewChat {
  private int y1;
  
  public GuiNewChatOverride(Minecraft mcIn) {
    super(mcIn);
    this.x = 2;
    this.y = 0;
  }
  
  public void drawChat(int p_146230_1_) {
    if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
      int var2 = getLineCount();
      boolean var3 = false;
      int var4 = 0;
      int var5 = this.field_146253_i.size();
      float var6 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
      if (var5 > 0) {
        if (getChatOpen())
          var3 = true; 
        float var7 = getChatScale();
        int var8 = MathHelper.ceiling_float_int(getChatWidth() / var7);
        GlStateManager.pushMatrix();
        GlStateManager.scale(var7, var7, 1.0F);
        int var9;
        for (var9 = 0; var9 + this.scrollPos < this.field_146253_i.size() && var9 < var2; var9++) {
          ChatLine var10 = (ChatLine) this.field_146253_i.get(var9 + this.scrollPos);
          if (var10 != null) {
            int var11 = p_146230_1_ - var10.getUpdatedCounter();
            if (var11 < 200 || var3) {
              double var12 = var11 / 200.0D;
              var12 = 1.0D - var12;
              var12 *= 10.0D;
              var12 = MathHelper.clamp_double(var12, 0.0D, 1.0D);
              var12 *= var12;
              int var14 = (int)(255.0D * var12);
              if (var3)
                var14 = 255; 
              var14 = (int)(var14 * var6);
              var4++;
            } 
          } 
        } 
        float width = getChatWidth();
        GlStateManager.translate(this.x, this.y, 0.0F);
        this.y1 = -var2 * 9;
        GlStateManager.translate(0.0F, -8.0F, 0.0F);
        int color = this.drag ? Integer.MIN_VALUE : 1157627904;
        int bcolor = this.drag ? 1610612736 : 1073741824;
        if (getChatOpen()) {
          float mmm = (-var2 * 9);
          RenderUtils.drawBorderedRect(0.0F, mmm - 11.0F, width + 3.0F, mmm + 2.5F, 2.0F, bcolor, color);
          UnicodeFontRenderer font = Minecraft.getMinecraft().getNobita().getFontManager().VERDANA15;
  		
          font.drawString("Chat", 3.0F, mmm - 9.0F, -16777216);
          RenderUtils.drawBorderedRect(0.0F, mmm + 4.0F, width + 3.0F, 
              9.0F, 2.0F, bcolor, color);
        } else if (var4 > 0) {
          int mmm = -var4 * 9;
          RenderUtils.drawBorderedRect(0.0F, (mmm + 4), width + 3.0F, 
              9.0F, 2.0F, bcolor, color);
        } 
        GL11.glLineWidth(1.0F);
        GlStateManager.translate(0.0F, 5.0F, 0.0F);
        for (var9 = 0; var9 + this.scrollPos < this.field_146253_i.size() && var9 < var2; var9++) {
          ChatLine var10 = (ChatLine) this.field_146253_i.get(var9 + this.scrollPos);
          if (var10 != null) {
            int var11 = p_146230_1_ - var10.getUpdatedCounter();
            if (var11 < 200 || var3) {
              double var12 = var11 / 200.0D;
              var12 = 1.0D - var12;
              var12 *= 10.0D;
              var12 = MathHelper.clamp_double(var12, 0.0D, 1.0D);
              var12 *= var12;
              int var14 = (int)(255.0D * var12);
              if (var3)
                var14 = 255; 
              var14 = (int)(var14 * var6);
              var4++;
              if (var14 > 0) {
                byte var15 = 0;
                int var16 = -var9 * 9;
                String var17 = var10.getChatComponent().getFormattedText();
                UnicodeFontRenderer font = Minecraft.getMinecraft().getNobita().getFontManager().VERDANA17;
                font.drawStringWithShadow(var17.replace("§r", "").replace("ï¿¸", "").replace("§0", "").replace("§l", "").replace("§m", "").replace("§n", "").replace("§o", "").replace("§p", "").replace("§a", "").replace("§b", "").replace("§2", "").replace("§c", "").replace("§d", "").replace("§e", "").replace("§f", "").replace("§g", "").replace("§1", "").replace("§2", "").replace("§3", "").replace("§4", "").replace("§5", "").replace("§6", "").replace("§7", "").replace("§8", "").replace("§9", ""), var15 + 1.0F, (var16 - 8), 16777215 + (var14 << 24));
                GlStateManager.disableAlpha();
              } 
            } 
          } 
        } 
        GlStateManager.translate(this.x, -this.y, 0.0F);
        if (var3) {
          var9 = this.mc.fontRendererObj.FONT_HEIGHT;
          GlStateManager.translate(-3.0F, 0.0F, 0.0F);
          int var18 = var5 * var9 + var5;
          int var11 = var4 * var9 + var4;
          int var19 = this.scrollPos * var11 / var5;
          int var13 = var11 * var11 / var18;
          if (var18 != var11) {
            int var14 = (var19 > 0) ? 170 : 96;
            int i = this.isScrolled ? 13382451 : 3355562;
          } 
        } 
        GlStateManager.popMatrix();
      } 
    } 
  }
  
  public static final ScaledResolution getScaledResolution() {
	    return new ScaledResolution(Minecraft.getMinecraft(), (Minecraft.getMinecraft()).displayWidth, (Minecraft.getMinecraft()).displayHeight);
	  }
  
  private boolean isMouseOverTitle(int par1, int par2) {
    int height = getScaledResolution().getScaledHeight();
    return (par1 >= this.x && par2 >= this.y + this.y1 - 20 + height - 48 && 
      par1 <= this.x + getChatWidth() + 3 && 
      par2 <= this.y + this.y1 - 6 + height - 48);
  }
  
  
  
  public void mouseClicked(int par1, int par2, int par3) {
    if (isMouseOverTitle(par1, par2) && par3 == 0) {
      this.drag = true;
      this.dragX = this.x - par1;
      this.dragY = this.y - par2;
    } 
  }
  
  public void mouseReleased(int par1, int par2, int par3) {
    if (par3 == 0)
      this.drag = false; 
  }
}
