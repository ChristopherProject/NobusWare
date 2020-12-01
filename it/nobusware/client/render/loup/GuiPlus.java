package it.nobusware.client.render.loup;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import it.nobusware.client.render.cheatmine.GuiFakeParams;
import it.nobusware.client.render.loup.server.GuiServerChecks;
import it.nobusware.client.render.parrot.GuiUtilities;
import it.nobusware.client.render.screens.newaltmanager.NewAllInOneAltManager;
import it.nobusware.client.utils.ExpandButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

public class GuiPlus extends GuiScreen {
	public GuiMultiplayer parentScreen;

	protected String messageLine1;

	private String messageLine2;

	private final List field_175298_s = Lists.newArrayList();

	protected String confirmButtonText;

	protected String cancelButtonText;

	protected int parentButtonClickedId;

	private int ticksUntilEnable;

	private static final String __OBFID = "CL_00000684";

	public GuiPlus(GuiMultiplayer p_i1082_1_) {
		this.parentScreen = p_i1082_1_;
	}

	public void initGui() {
		this.buttonList.add(new ExpandButton(2, this.width / 2 - 100, 62, 200, 20, "Accounts"));
		this.buttonList.add(new ExpandButton(1, this.width / 2 - 100, 87, 200, 20, "Scanners"));
		this.buttonList.add(new ExpandButton(4, this.width / 2 - 100, 112, 200, 20, "Utilities"));
		this.buttonList.add(new ExpandButton(5, this.width / 2 - 100, 137, 200, 20, "UUID Spoofer"));
		this.buttonList.add(new ExpandButton(3, this.width / 2 - 100, this.height - 50, 200, 20, "Back to servers"));
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled)
			if (button.id == 3) {
				this.mc.displayGuiScreen((GuiScreen) this.parentScreen);
			} else if (button.id == 1) {
				this.mc.displayGuiScreen(new GuiServerChecks(this));
			} else if (button.id == 2) {
				this.mc.displayGuiScreen(new NewAllInOneAltManager(this));
			}
			else if (button.id == 4) {
				this.mc.displayGuiScreen(new GuiUtilities(this));
			}else if (button.id == 5) {
				this.mc.displayGuiScreen(new GuiFakeParams(this));
			}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(this.fontRendererObj, "Connection Utils", this.width / 2, 20, 16777215);
		drawCenteredString(this.fontRendererObj, "Here you can find all connection utils.", this.width / 2, 35, 10526880);
		drawCenteredString(this.fontRendererObj, "Press back to return to Multiplayer screen", this.width / 2, 45, 10526880);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void setButtonDelay(int p_146350_1_) {
		this.ticksUntilEnable = p_146350_1_;

		GuiButton var3;
		for (Iterator var2 = this.buttonList.iterator(); var2.hasNext(); var3.enabled = false) {
			var3 = (GuiButton) var2.next();
		}
	}

	public void updateScreen() {
		super.updateScreen();

		if (--this.ticksUntilEnable == 0) {
			GuiButton var2;
			for (Iterator var1 = this.buttonList.iterator(); var1.hasNext(); var2.enabled = true) {
				var2 = (GuiButton) var1.next();
			}
		}
	}
}
