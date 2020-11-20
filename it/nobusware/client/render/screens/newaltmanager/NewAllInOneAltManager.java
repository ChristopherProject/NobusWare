package it.nobusware.client.render.screens.newaltmanager;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import it.nobusware.client.render.screens.newaltmanager.thealteningapi.TheAlteningAuthentication;
import it.nobusware.client.utils.ExpandButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;

public class NewAllInOneAltManager extends GuiScreen {
	public static GuiTextField email;
	public static GuiPasswordField pass;
	public GuiScreen oldscreen;

	public NewAllInOneAltManager(final GuiScreen old) {
		this.oldscreen = old;
	}

	@Override
	public void initGui() {
		
		Keyboard.enableRepeatEvents(true);
		
		this.buttonList.add(new ExpandButton(1, this.width / 2 - 100, this.height / 4 + 80, "Account Login"));
		this.buttonList.add(new ExpandButton(11, this.width / 2 - 100, this.height / 4 + 112 + 12, "ClipBoard"));
		this.buttonList.add(new ExpandButton(111, this.width / 2 - 100, this.height / 4 + 102, "TheAltening"));

		this.buttonList.add(new ExpandButton(2, this.width / 2 - 100, this.height / 4 + 96 + 50, "Back"));
		this.email = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 100, 76, 200, 20);
		this.pass = new GuiPasswordField(4, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
		this.email.setMaxStringLength(50);
		this.pass.setMaxStringLength(50);
		

	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		this.mc.getNobita().getDiscord().update("Schermata AltManager", this.mc.getSession().getUsername() + " - NobusWare Client");
		this.email.updateCursorCounter();
		this.pass.updateCursorCounter();
	}

	@Override
	protected void mouseClicked(final int x, final int y, final int m) {
		this.email.mouseClicked(x, y, m);
		this.pass.mouseClicked(x, y, m);
		try {
			super.mouseClicked(x, y, m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void keyTyped(final char c, final int i) {
		this.email.textboxKeyTyped(c, i);
		this.pass.textboxKeyTyped(c, i);
		if (c == '\t') {
			if (this.email.isFocused()) {
				this.email.setFocused(false);
				this.pass.setFocused(true);
			} else {
				this.email.setFocused(true);
				this.pass.setFocused(false);
			}
		}
		if (c == '\r') {
			try {
				this.actionPerformed((GuiButton) this.buttonList.get(0));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		this.drawDefaultBackground();
		this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
		Minecraft.getMinecraft().fontRendererObj.func_175063_a(mc.getSession().getUsername().toString(), 1, 1, 0xff12ff00);
		Minecraft.getMinecraft().fontRendererObj.func_175063_a("Support TheAltening", 1, 10, 0xff12ff00);
		Minecraft.getMinecraft().fontRendererObj.func_175063_a("§aThank To §eMrTheShy §aFor Make It Possible.", 1, this.height - 10, 0xff12ff00);
		this.drawCenteredString(this.mc.fontRendererObj, "Email/Nick", this.width / 2 - 77, 66, -1);
		this.drawCenteredString(this.mc.fontRendererObj, "Password", this.width / 2 - 77, 106, -1);
		this.email.drawTextBox();
		this.pass.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 2) {
			this.mc.displayGuiScreen(this.oldscreen);
		}
		if (button.id == 111) {
			Desktop desktop = java.awt.Desktop.getDesktop();
			try {
				//specify the protocol along with the URL
				URI oURL = new URI(
						"https://thealtening.com/free/free-minecraft-alts");
				desktop.browse(oURL);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (button.id == 11) {
			try {
				String data = (String)Toolkit.getDefaultToolkit().getSystemClipboard() .getData(DataFlavor.stringFlavor);
					if (!data.contains(":")) {
						return;
					}
					String[] credentials = data.split(":");
					this.email.setText(credentials[0]);
					this.pass.setText(credentials[1]);
			} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
				System.out.println("Failed Importing Alt");
			}
	
		}
		if (button.id == 1) {
			if (this.pass.getText().isEmpty()) {
				// sp login

				if (!this.email.getText().trim().isEmpty()) {
					this.mc.session = new Session(this.email.getText().trim(), "-", "-", "Legacy");
				} else {
				}

			} else if (!this.email.getText().trim().isEmpty()) {
				// premium login mojang
				TheAlteningAuthentication.mojang();
				final YggdrasilUserAuthentication a = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(
						Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
				a.setUsername(this.email.getText().trim());
				a.setPassword(this.pass.getText().trim());
				try {
					a.logIn();
					this.mc.session = new Session(a.getSelectedProfile().getName(),
							a.getSelectedProfile().getId().toString(), a.getAuthenticatedToken(), "mojang");

				} catch (Exception e) {

					// premium login thealtening
					TheAlteningAuthentication.theAltening();

					a.setUsername(this.email.getText().trim());
					a.setPassword(this.pass.getText().trim());
					try {
						a.logIn();
						this.mc.session = new Session(a.getSelectedProfile().getName(),
								a.getSelectedProfile().getId().toString(), a.getAuthenticatedToken(), "mojang");
					} catch (Exception ee) {
					}

				}
			}
		}
	}

}
