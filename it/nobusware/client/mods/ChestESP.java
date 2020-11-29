package it.nobusware.client.mods;

import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.EventRenderer3D;
import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.old.BetterRenderUI;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;

public class ChestESP extends Module {

	public ChestESP(String nome_mod, int tasto, String nome_array_printed, Category categoria) {
		super(nome_mod, tasto, nome_array_printed, categoria);
	}

	private Framebuffer blockFBO;

	@Handler
	public void onRender(EventRenderer3D event) {
			Iterator var3 = this.mc.theWorld.loadedTileEntityList.iterator();
			while (var3.hasNext()) {
				Object o = var3.next();
				if (o instanceof TileEntityChest) {
					TileEntityChest entity7 = (TileEntityChest) o;
					Block x2 = this.mc.theWorld.getBlockState(entity7.getPos()).getBlock();
					Block border = this.mc.theWorld.getBlockState(
							new BlockPos(entity7.getPos().getX(), entity7.getPos().getY(), entity7.getPos().getZ() - 1))
							.getBlock();
					Block y2 = this.mc.theWorld.getBlockState(
							new BlockPos(entity7.getPos().getX(), entity7.getPos().getY(), entity7.getPos().getZ() + 1))
							.getBlock();
					Block border3 = this.mc.theWorld.getBlockState(
							new BlockPos(entity7.getPos().getX() - 1, entity7.getPos().getY(), entity7.getPos().getZ()))
							.getBlock();
					Block z2 = this.mc.theWorld.getBlockState(
							new BlockPos(entity7.getPos().getX() + 1, entity7.getPos().getY(), entity7.getPos().getZ()))
							.getBlock();
					double x = entity7.getPos().getX() - (this.mc.getRenderManager()).viewerPosX;
					double y = entity7.getPos().getY() - (this.mc.getRenderManager()).viewerPosY;
					double z = entity7.getPos().getZ() - (this.mc.getRenderManager()).viewerPosZ;
					GL11.glPushMatrix();
					RenderHelper.disableStandardItemLighting();
					if (x2 == Blocks.chest) {
						if (border != Blocks.chest)
							if (y2 == Blocks.chest) {
								draw((Block) Blocks.chest, x, y, z, 1.0D, 1.0D, 2.0D);
							} else if (z2 == Blocks.chest) {
								draw((Block) Blocks.chest, x, y, z, 2.0D, 1.0D, 1.0D);
							} else if (z2 == Blocks.chest) {
								draw((Block) Blocks.chest, x, y, z, 1.0D, 1.0D, 1.0D);
							} else if (border != Blocks.chest && y2 != Blocks.chest && border3 != Blocks.chest
									&& z2 != Blocks.chest) {
								draw((Block) Blocks.chest, x, y, z, 1.0D, 1.0D, 1.0D);
							}
					} else if (x2 == Blocks.trapped_chest && border != Blocks.trapped_chest) {
						if (y2 == Blocks.trapped_chest) {
							draw(Blocks.trapped_chest, x, y, z, 1.0D, 1.0D, 2.0D);
						} else if (z2 == Blocks.trapped_chest) {
							draw(Blocks.trapped_chest, x, y, z, 2.0D, 1.0D, 1.0D);
						} else if (z2 == Blocks.trapped_chest) {
							draw(Blocks.trapped_chest, x, y, z, 1.0D, 1.0D, 1.0D);
						} else if (border != Blocks.trapped_chest && y2 != Blocks.trapped_chest
								&& border3 != Blocks.trapped_chest && z2 != Blocks.trapped_chest) {
							draw(Blocks.trapped_chest, x, y, z, 1.0D, 1.0D, 1.0D);
						}
					}
					RenderHelper.enableStandardItemLighting();
					GL11.glPopMatrix();
					continue;
				}
				if (o instanceof TileEntityEnderChest) {
					TileEntityEnderChest entity71 = (TileEntityEnderChest) o;
					double x21 = entity71.getPos().getX() - (this.mc.getRenderManager()).viewerPosX;
					double y21 = entity71.getPos().getY() - (this.mc.getRenderManager()).viewerPosY;
					double z21 = entity71.getPos().getZ() - (this.mc.getRenderManager()).viewerPosZ;
					GL11.glPushMatrix();
					RenderHelper.disableStandardItemLighting();
					draw(Blocks.ender_chest, x21, y21, z21, 1.0D, 1.0D, 1.0D);
					RenderHelper.enableStandardItemLighting();
					GL11.glPopMatrix();
				}
			}
	}

	private static void drawChestOutlines(TileEntity ent, float partialTicks) {
		int entityDispList = GL11.glGenLists(1);
		BetterRenderUI.Stencil.getInstance().startLayer();
		GL11.glPushMatrix();
		mc.entityRenderer.setupCameraTransform(partialTicks, 0);
		GL11.glMatrixMode(5888);
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(2884);
		Minecraft.getMinecraft();
		GL11.glDisable(2929);
		GL11.glDepthMask(true);
		BetterRenderUI.Stencil.getInstance().setBuffer(true);
		GL11.glNewList(entityDispList, 4864);
		Minecraft.getMinecraft();
		Iterator var7 = mc.theWorld.loadedTileEntityList.iterator();
		while (var7.hasNext()) {
			Object obj = var7.next();
			TileEntity entity = (TileEntity) obj;
			if (entity instanceof net.minecraft.tileentity.TileEntityLockable) {
				GL11.glLineWidth(3.0F);
				GL11.glEnable(3042);
				GL11.glEnable(2848);
				GL11.glDisable(3553);
				GL11.glPushMatrix();
				GL11.glTranslated(entity.getPos().getX() - RenderManager.renderPosX,
						entity.getPos().getY() - RenderManager.renderPosY,
						entity.getPos().getZ() - RenderManager.renderPosZ);
				GL11.glColor4f(0.31F, 1.31F, 2.18F, 1.0F);
				TileEntityRendererDispatcher.instance.renderTileEntityAt(entity, 0.0D, 0.0D, 0.0D, partialTicks);
				GL11.glEnable(3553);
				GL11.glPopMatrix();
			}
		}
		GL11.glEndList();
		GL11.glPolygonMode(1032, 6913);
		GL11.glCallList(entityDispList);
		GL11.glPolygonMode(1032, 6912);
		GL11.glCallList(entityDispList);
		BetterRenderUI.Stencil.getInstance().setBuffer(false);
		GL11.glPolygonMode(1032, 6914);
		GL11.glCallList(entityDispList);
		BetterRenderUI.Stencil.getInstance().cropInside();
		GL11.glPolygonMode(1032, 6913);
		GL11.glCallList(entityDispList);
		GL11.glPolygonMode(1032, 6912);
		GL11.glCallList(entityDispList);
		GL11.glPolygonMode(1032, 6914);
		(Minecraft.getMinecraft()).entityRenderer.func_175072_h();
		RenderHelper.disableStandardItemLighting();
		(Minecraft.getMinecraft()).entityRenderer.setupOverlayRendering();
		BetterRenderUI.Stencil.getInstance().stopLayer();
		GL11.glDisable(2960);
		GL11.glPopMatrix();
		(Minecraft.getMinecraft()).entityRenderer.func_175072_h();
		RenderHelper.disableStandardItemLighting();
		(Minecraft.getMinecraft()).entityRenderer.setupOverlayRendering();
		GL11.glDeleteLists(entityDispList, 1);
	}

	private static void draw(Block block, double x, double y, double z, double xo, double yo, double zo) {
		GL11.glDisable(2896);
		GL11.glDisable(3553);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glEnable(2848);
		GL11.glDepthMask(false);
		GL11.glLineWidth(0.75F);
		if (block == Blocks.ender_chest) {
			GL11.glColor4f(0.4F, 0.2F, 1.0F, 1.0F);
			x += 0.0650000000745058D;
			y += 0.0D;
			z += 0.06000000074505806D;
			xo -= 0.13000000149011612D;
			yo -= 0.1200000149011612D;
			zo -= 0.12000000149011612D;
		} else if (block == Blocks.chest) {
			GL11.glColor4f(1.0F, 1.0F, 0.0F, 1.0F);
			x += 0.0650000000745058D;
			y += 0.0D;
			z += 0.06000000074505806D;
			xo -= 0.13000000149011612D;
			yo -= 0.1200000149011612D;
			zo -= 0.12000000149011612D;
		} else if (block == Blocks.trapped_chest) {
			GL11.glColor4f(1.0F, 0.6F, 0.0F, 1.0F);
			x += 0.0650000000745058D;
			y += 0.0D;
			z += 0.06000000074505806D;
			xo -= 0.13000000149011612D;
			yo -= 0.1200000149011612D;
			zo -= 0.12000000149011612D;
		}
		BetterRenderUI.R3DUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
		if (block == Blocks.ender_chest) {
			GL11.glColor4f(0.4F, 0.2F, 1.0F, 0.21F);
		} else if (block == Blocks.chest) {
			GL11.glColor4f(1.0F, 1.0F, 0.0F, 0.11F);
		} else if (block == Blocks.trapped_chest) {
			GL11.glColor4f(1.0F, 0.6F, 0.0F, 0.11F);
		}
		BetterRenderUI.R3DUtils.drawFilledBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
		if (block == Blocks.ender_chest) {
			GL11.glColor4f(0.4F, 0.2F, 1.0F, 1.0F);
		} else if (block == Blocks.chest) {
			GL11.glColor4f(1.0F, 1.0F, 0.0F, 1.0F);
		} else if (block == Blocks.trapped_chest) {
			GL11.glColor4f(1.0F, 0.6F, 0.0F, 1.0F);
		}
		BetterRenderUI.R3DUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + xo, y + yo, z + zo));
		GL11.glDepthMask(true);
		GL11.glDisable(2848);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glEnable(3553);
	}
}