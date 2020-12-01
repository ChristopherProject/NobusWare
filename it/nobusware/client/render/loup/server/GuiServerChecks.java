package it.nobusware.client.render.loup.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import it.nobusware.client.render.loup.GuiPlus;
import it.nobusware.client.render.loup.server.checker.GuiPortChecker;
import it.nobusware.client.render.loup.server.checker.gh.GuiGHChecker;
import it.nobusware.client.render.loup.server.finder.GuiServerFinder;
import it.nobusware.client.utils.ExpandButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiServerChecks extends GuiScreen {
	protected GuiPlus parentScreen;

	protected String messageLine1;

	private String messageLine2;

	private final List field_175298_s = Lists.newArrayList();

	protected String confirmButtonText;

	protected String cancelButtonText;

	protected int parentButtonClickedId;

	private int ticksUntilEnable;

	private static final String __OBFID = "CL_00000684";

	public GuiServerChecks(GuiPlus p_i1082_1_) {
		this.parentScreen = p_i1082_1_;
	}

	public void initGui() {
		this.buttonList.add(new ExpandButton(25564, this.width / 2 - 100, 62, 200, 20, "Server Finder"));
		this.buttonList.add(new ExpandButton(25565, this.width / 2 - 100, 87, 200, 20, "Server Checker"));
		this.buttonList.add(new ExpandButton(25566, this.width / 2 - 100, 112, 200, 20, "Port Scanner"));
		this.buttonList
				.add(new ExpandButton(666, this.width / 2 - 100, this.height - 50, 200, 20, "Back to connection utils"));
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled)
			if (button.id == 666) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else if (button.id == 25564) {
				this.mc.displayGuiScreen(new GuiServerFinder(this.parentScreen.parentScreen, this));
			} else if (button.id == 25565) {
				this.mc.displayGuiScreen(new GuiPortChecker(this.parentScreen.parentScreen, this));
			} else if (button.id == 25566) {
				this.mc.displayGuiScreen(new GuiGHChecker(this.parentScreen.parentScreen, this));
			}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(this.fontRendererObj, "Server Connection Utils", this.width / 2, 20, 16777215);
		drawCenteredString(this.fontRendererObj, "Here you can find some connection utils.", this.width / 2, 35,
				10526880);
		drawCenteredString(this.fontRendererObj, "Press back to return to connection utils", this.width / 2, 45,
				10526880);
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