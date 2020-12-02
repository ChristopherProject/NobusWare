package net.minecraft.client.renderer.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import it.nobusware.client.mods.ItemPhisyc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEntityItem extends Render
{
    private final RenderItem field_177080_a;
    private Random field_177079_e = new Random();
    private static final String __OBFID = "CL_00002442";

    public RenderEntityItem(RenderManager p_i46167_1_, RenderItem p_i46167_2_)
    {
        super(p_i46167_1_);
        this.field_177080_a = p_i46167_2_;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }
    
    private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
		ItemStack itemstack = itemIn.getEntityItem();
		Item item = itemstack.getItem();
		if (item == null)
			return 0;
		boolean flag = p_177077_9_.isGui3d();
		int i = func_177078_a(itemstack);
		float f = 0.25F;
		float f1 = MathHelper.sin((itemIn.func_174872_o() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
		if (Minecraft.getMinecraft().getNobita().getModManager().Prendi(ItemPhisyc.class).isAbilitato())
			f1 = 0.0F;
		float f2 = (p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.HEAD)).field_178363_d.y;
		GlStateManager.translate((float) p_177077_2_, (float) p_177077_4_ + f1 + 0.25F * f2, (float) p_177077_6_);
		if (flag || this.renderManager.options != null) {
			float f3 = ((itemIn.func_174872_o() + p_177077_8_) / 20.0F + itemIn.hoverStart) * 57.295776F;
			if (Minecraft.getMinecraft().getNobita().getModManager().Prendi(ItemPhisyc.class).isAbilitato()) {
				if (itemIn.onGround) {
					GL11.glRotatef(itemIn.rotationYaw, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(itemIn.rotationPitch + 90.0F, 1.0F, 0.0F, 0.0F);
				} else {
					GlStateManager.rotate(f3, 0.5F, 0.0F, 0.0F);
				}
			} else {
				GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
			}
		}
		if (!flag) {
			float f6 = -0.0F * (i - 1) * 0.5F;
			float f4 = -0.0F * (i - 1) * 0.5F;
			float f5 = -0.046875F * (i - 1) * 0.5F;
			GlStateManager.translate(f6, f4, f5);
		}
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		return i;
	}
    private int func_177078_a(ItemStack p_177078_1_)
    {
        byte var2 = 1;

        if (p_177078_1_.stackSize > 48)
        {
            var2 = 5;
        }
        else if (p_177078_1_.stackSize > 32)
        {
            var2 = 4;
        }
        else if (p_177078_1_.stackSize > 16)
        {
            var2 = 3;
        }
        else if (p_177078_1_.stackSize > 1)
        {
            var2 = 2;
        }

        return var2;
    }

    public void func_177075_a(EntityItem p_177075_1_, double p_177075_2_, double p_177075_4_, double p_177075_6_, float p_177075_8_, float p_177075_9_)
    {
        ItemStack var10 = p_177075_1_.getEntityItem();
        this.field_177079_e.setSeed(187L);
        boolean var11 = false;

        if (this.bindEntityTexture(p_177075_1_))
        {
            this.renderManager.renderEngine.getTexture(this.func_177076_a(p_177075_1_)).func_174936_b(false, false);
            var11 = true;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel var12 = this.field_177080_a.getItemModelMesher().getItemModel(var10);
        int var13 = this.func_177077_a(p_177075_1_, p_177075_2_, p_177075_4_, p_177075_6_, p_177075_9_, var12);

        for (int var14 = 0; var14 < var13; ++var14)
        {
            if (var12.isAmbientOcclusionEnabled())
            {
                GlStateManager.pushMatrix();

                if (var14 > 0)
                {
                    float var15 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float var16 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float var17 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    GlStateManager.translate(var15, var16, var17);
                }

                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                this.field_177080_a.func_180454_a(var10, var12);
                GlStateManager.popMatrix();
            }
            else
            {
                this.field_177080_a.func_180454_a(var10, var12);
                GlStateManager.translate(0.0F, 0.0F, 0.046875F);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(p_177075_1_);

        if (var11)
        {
            this.renderManager.renderEngine.getTexture(this.func_177076_a(p_177075_1_)).func_174935_a();
        }

        super.doRender(p_177075_1_, p_177075_2_, p_177075_4_, p_177075_6_, p_177075_8_, p_177075_9_);
    }

    protected ResourceLocation func_177076_a(EntityItem p_177076_1_)
    {
        return TextureMap.locationBlocksTexture;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.func_177076_a((EntityItem)p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.func_177075_a((EntityItem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
