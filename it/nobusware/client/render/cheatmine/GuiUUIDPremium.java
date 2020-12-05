package it.nobusware.client.render.cheatmine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.lwjgl.input.Keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;

import it.nobusware.client.render.parrot.GuiUtilities;
import it.nobusware.client.utils.ExpandButton;
import it.nobusware.client.utils.JsonUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Session;

public class GuiUUIDPremium extends GuiScreen {

	protected GuiFakeParams prevMenu;

	protected GuiTextField uuid;
	
	protected String errorText;

	public GuiUUIDPremium(GuiFakeParams par1GuiScreen) {
		this.prevMenu = par1GuiScreen;
	    this.errorText = "";
	}

	public void updateScreen() {
		this.uuid.updateCursorCounter();
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new ExpandButton(0, width / 2 - 100, height / 4 + 90 + 12, "UUID SPOOF"));
		this.buttonList.add(new ExpandButton(1, width / 2 - 100, height / 4 + 120 + 12, "Back"));
		(this.uuid = new GuiTextField(1, this.fontRendererObj, width / 2 - 100, 60, 200, 20)).setMaxStringLength(65);
		this.uuid.setFocused(true);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}


	protected void actionPerformed(GuiButton button) {
		if (button.enabled)
			if (button.id == 1) {
				mc.displayGuiScreen(this.prevMenu);
			} else if (button.id == 0) {
				Session realSession = this.mc.getSession();
				Session copiedSession = new Session(mc.getSession().getUsername(), realSession.getPlayerID(), realSession.getToken(), Session.Type.LEGACY.name());
				String uuid_premium_by_name = getUUID(this.uuid.getText());
				this.mc.setSession(copiedSession);
				this.mc.setFakeNick(uuid_premium_by_name);
				if (uuid_premium_by_name.equals("UNKNOWN_UUID"))
				this.errorText = "§cFailed INVALID_USERNAME";
				else
				this.errorText = "§eYou Have Spoofed §7-> §a" + uuid_premium_by_name;
			}
	}

	public static String getUUID(String nome) {
		try {
			URL link2 = new URL("https://api.mojang.com/users/profiles/minecraft/" + nome);
			HttpURLConnection connected2 = (HttpURLConnection) link2.openConnection();
			connected2.setRequestMethod("GET");
			connected2.setRequestProperty("Accept", "application/json");
			if (connected2.getResponseCode() == 200) {
				BufferedReader read_string = new BufferedReader(new InputStreamReader(connected2.getInputStream()));
				String pageText = read_string.lines().collect(Collectors.joining("\n"));
				JSONObject object = new JSONObject(pageText);
				read_string.close();
				connected2.disconnect();
				return object.getString("id").toString();
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "UNKNOWN_UUID";
	}

	protected void keyTyped(char par1, int par2) {
		this.uuid.textboxKeyTyped(par1, par2);
		if (par2 == 28 || par2 == 156)
			actionPerformed((GuiButton) this.buttonList.get(0));
	}

	protected void mouseClicked(int par1, int par2, int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
		this.uuid.mouseClicked(par1, par2, par3);
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		drawCenteredString(this.fontRendererObj, "Premium UUID Spoofer", width / 2, 20, 16777215);
		drawString(this.fontRendererObj, "Username (Only Premium):", width / 2 - 100, 47, 10526880);
	    drawCenteredString(this.fontRendererObj,  this.errorText, width / 2, 47 + 42, 16777215);
		this.uuid.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
}