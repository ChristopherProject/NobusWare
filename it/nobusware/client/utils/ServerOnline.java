package it.nobusware.client.utils;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import it.nobusware.client.mods.aura.killaura;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ServerOnline {

	private static Minecraft mc = Minecraft.getMinecraft();

	public static List<String> getAllPlayers() {
		List<String> players = new ArrayList();
		NetHandlerPlayClient nethandlerplayclient = mc.thePlayer.sendQueue;
		Iterator NPIs = nethandlerplayclient.func_175106_d().iterator();
		while (NPIs.hasNext()) {
			NetworkPlayerInfo NPI = (NetworkPlayerInfo) NPIs.next();
			if (!NPI.func_178845_a().getName().equalsIgnoreCase(mc.getSession().getUsername())) {
				players.add(NPI.func_178845_a().getName());
			}

		}
		return players;
	}
	
	public void RenderEntityInfo(EntityLivingBase e, double reach) {
		if (mc.getNobita().getModManager().Prendi(killaura.class).isAbilitato()
				&& mc.thePlayer.getDistanceToEntity(e) <= reach && Minecraft.getMinecraft().thePlayer != e
				&& !Minecraft.getMinecraft().getSession().getUsername().equals(e)
				&& Minecraft.getMinecraft().thePlayer != null && e != null && e instanceof EntityPlayer) {
			// drawrect
			MyPersonalPrintRect(-1, 2, 4, 114, 0xff99CCFF);
			MyPersonalPrintRect(2, 2, 115, 114, 0x20ffddff);
			// strings
			NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getNetHandler()
					.func_175102_a(e.getUniqueID());
			String ping = (networkPlayerInfo == null) ? "0§ams" : (networkPlayerInfo.getResponseTime() + "§ams");
			final String playerName = e.getName();
			// rendering string
			mc.fontRendererObj.func_175063_a("§bPing" + " " + ChatFormatting.DARK_GREEN + ping, 6, 67 + 0 + 36, -1);
			mc.fontRendererObj.func_175063_a(
					"§bHealt" + " " + getHealth(e)
							+ it.nobusware.client.utils.factory.MathUtils.round(e.getHealth() / 2, 2),
					30 + 26, 67 + 0 + 36, -1);
			mc.fontRendererObj.func_175063_a("§bIGN" + " " + ChatFormatting.RED + playerName, 37, 88 - 35 + 0 + 36, -1);
			// skin downloader
			try {
				AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(playerName), playerName)
						.loadTexture(Minecraft.getMinecraft().getResourceManager());
				Minecraft.getMinecraft().getTextureManager()
						.bindTexture(AbstractClientPlayer.getLocationSkin(playerName));
				GL11.glColor4f(1F, 1F, 1F, 1F);
				Gui.drawScaledCustomSizeModalRect(6, 67 + 0 + 4, 8, 8, 8, 8, 28, 28, 64, 64);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			// start bar
			double inc = 43 / e.getMaxHealth();
			// endbar
			double end = inc * (e.getHealth() > e.getMaxHealth() ? e.getMaxHealth() : e.getHealth());
			// render healthbar
			RenderUtils.drawBorderedRect(45, 67 + 0 + 10, 44, 6, (float) 0.5, new Color(0).getRGB(),
					new Color(35, 35, 35).getRGB());
			RenderUtils.drawRect(45.5f, 67 + 0 + 10.5f, (float) end, 5, getHealthColor(e));
		}
	}

	private void MyPersonalPrintRect(int x, int y, int altezza, int lunghezza, int colore) {
		GuiScreen.drawRect(x, y, altezza, lunghezza, colore);
	}

	private ChatFormatting getHealth(EntityLivingBase player) {
		final double health = Math.ceil(player.getHealth());
		final double maxHealth = player.getMaxHealth();
		final double percentage = 100 * (health / maxHealth);
		if (percentage > 85)
			return ChatFormatting.DARK_GREEN;
		else if (percentage > 75)
			return ChatFormatting.GREEN;
		else if (percentage > 50)
			return ChatFormatting.YELLOW;
		else if (percentage > 25)
			return ChatFormatting.RED;
		else if (percentage > 0)
			return ChatFormatting.DARK_RED;
		return ChatFormatting.BLACK;
	}

	private int getHealthColor(EntityLivingBase player) {
		float f = player.getHealth();
		float f1 = player.getMaxHealth();
		float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
		return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 0.45F) | 0xFF000000;
	}
}