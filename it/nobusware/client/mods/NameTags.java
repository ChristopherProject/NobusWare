package it.nobusware.client.mods;

import org.lwjgl.opengl.GL11;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventRenderer3D;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.ChatColor;
import it.nobusware.client.utils.GLUtil;
import it.nobusware.client.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class NameTags extends Module {

    public NameTags(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	@Handler
    public void onRender3D(EventRenderer3D event) {
        for (final Object o : this.mc.theWorld.playerEntities) {
            final EntityPlayer p = (EntityPlayer)o;
            if (p != this.mc.func_175606_aa() && p.isEntityAlive()) {
                final double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosX;
                final double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosY;
                final double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosZ;
                this.renderNameTag(p, p.getName(), pX, pY, pZ);
                }
            }
    }
    
    public void renderNameTag(final EntityPlayer entity, String tag, final double pX, double pY, final double pZ) {
        final FontRenderer var12 = this.mc.fontRendererObj;
        pY += (entity.isSneaking() ? 0.5 : 0.7);
        float var2 = this.mc.thePlayer.getDistanceToEntity(entity) / 4.0f;
        if (var2 < 1.6f) {
            var2 = 1.6f;
        }
        int colour = 16777215;
        if (entity.isInvisible()) {
            colour = 16756480;
        }
        else if (entity.isSneaking()) {
            colour = 11468800;
        }
        final double health = Math.ceil(entity.getHealth() + entity.getAbsorptionAmount()) / 2.0;
        ChatColor healthCol;
        if (health < 5.0) {
            healthCol = ChatColor.RED;
        }
        else if (health > 5.0 && health < 6.5) {
            healthCol = ChatColor.YELLOW;
        }
        else {
            healthCol = ChatColor.GREEN;
        }
        if (Math.floor(health) == health) {
            tag = tag + " " + healthCol + (int)Math.floor(health);
        }
        else {
            tag = tag + " " + healthCol + health;
        }
        final RenderManager renderManager = this.mc.getRenderManager();
        final int color = 16776960;
        float scale = var2 * 2.0f;
        scale /= 100.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)pX, (float)pY + 1.4f, (float)pZ);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-scale, -scale, scale);
        GLUtil.setGLCap(2896, false);
        GLUtil.setGLCap(2929, false);
        final Tessellator var3 = Tessellator.getInstance();
        final WorldRenderer var4 = var3.getWorldRenderer();
        final int width = this.mc.fontRendererObj.getStringWidth(tag) / 2;
        GLUtil.setGLCap(3042, true);
        GL11.glBlendFunc(770, 771);
        RenderUtils.drawBorderedRect(-width - 2, -(this.mc.fontRendererObj.FONT_HEIGHT + 1), width + 2, 2.0f, 1.0f, -16777216, 1593835520);
        var12.func_175065_a(tag, -width, -(this.mc.fontRendererObj.FONT_HEIGHT - 1), colour, true);
        GL11.glPushMatrix();
            int xOffset = 0;
            if (entity.getHeldItem() != null) {
                xOffset -= 8;
                final ItemStack renderStack = entity.getHeldItem().copy();
                if (renderStack.hasEffect() && (renderStack.getItem() instanceof ItemTool || renderStack.getItem() instanceof ItemArmor)) {
                    renderStack.stackSize = 1;
                }
                this.renderItemStack(renderStack, xOffset, -26);
                xOffset += 16;
            }
            for (final ItemStack armourStack : entity.inventory.armorInventory) {
                if (armourStack != null) {
                    final ItemStack renderStack2 = armourStack.copy();
                    if (renderStack2.hasEffect() && (renderStack2.getItem() instanceof ItemTool || renderStack2.getItem() instanceof ItemArmor)) {
                        renderStack2.stackSize = 1;
                    }
                    this.renderItemStack(renderStack2, xOffset, -26);
                    xOffset += 16;
            }
        }
        GL11.glPopMatrix();
        GLUtil.revertAllCaps();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void renderItemStack(final ItemStack stack, final int x, final int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        this.mc.getRenderItem().zLevel = -150.0f;
        this.whatTheFuckOpenGLThisFixesItemGlint();
        this.mc.getRenderItem().func_180450_b(stack, x, y);
        this.mc.getRenderItem().func_175030_a(this.mc.fontRendererObj, stack, x, y);
        this.mc.getRenderItem().zLevel = 0.0f;
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        this.renderEnchantText(stack, x, y);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    public void renderEnchantText(final ItemStack stack, final int x, final int y) {
        int encY = y - 24;
        if (stack.getItem() instanceof ItemArmor) {
            final int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack);
            final int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
            final int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (pLevel > 0) {
                this.mc.fontRendererObj.drawString("p" + pLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (tLevel > 0) {
                this.mc.fontRendererObj.drawString("t" + tLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel > 0) {
                this.mc.fontRendererObj.drawString("u" + uLevel, x * 2, encY, 16777215);
                encY += 7;
            }
        }
        if (stack.getItem() instanceof ItemBow) {
            final int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            final int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            final int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
            final int uLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (sLevel > 0) {
                this.mc.fontRendererObj.drawString("d" + sLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (kLevel > 0) {
                this.mc.fontRendererObj.drawString("k" + kLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (fLevel > 0) {
                this.mc.fontRendererObj.drawString("f" + fLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel2 > 0) {
                this.mc.fontRendererObj.drawString("u" + uLevel2, x * 2, encY, 16777215);
                encY += 7;
            }
        }
        if (stack.getItem() instanceof ItemSword) {
            final int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, stack);
            final int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180313_o.effectId, stack);
            final int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            final int uLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (sLevel > 0) {
                this.mc.fontRendererObj.drawString("s" + sLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (kLevel > 0) {
                this.mc.fontRendererObj.drawString("k" + kLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (fLevel > 0) {
                this.mc.fontRendererObj.drawString("f" + fLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel2 > 0) {
                this.mc.fontRendererObj.drawString("u" + uLevel2, x * 2, encY, 16777215);
            }
        }
    }
    
    public void whatTheFuckOpenGLThisFixesItemGlint() {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.func_179090_x();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }
}
