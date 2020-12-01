package it.nobusware.client.render.parrot;

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

public class GuiUtilities extends GuiScreen {
	protected GuiPlus parentScreen;

	protected String messageLine1;

	private String messageLine2;

	private final List field_175298_s = Lists.newArrayList();

	protected String confirmButtonText;

	protected String cancelButtonText;

	protected int parentButtonClickedId;

	private int ticksUntilEnable;

	private static final String __OBFID = "CL_00000684";

	public GuiUtilities(GuiPlus p_i1082_1_) {
		this.parentScreen = p_i1082_1_;
	}

	public void initGui() {
		this.buttonList.add(new ExpandButton(1, this.width / 2 - 100, 62, 200, 20, "Dedicate IP Finder"));
		this.buttonList.add(new ExpandButton(2, this.width / 2 - 100, 87, 200, 20, "Session Stealer"));
		this.buttonList.add(new ExpandButton(3, this.width / 2 - 100, 112, 200, 20, "Subdomains Scanner"));
		this.buttonList.add(new ExpandButton(666, this.width / 2 - 100, this.height - 50, 200, 20, "Back to connection utils"));
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled)
			if (button.id == 666) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else if (button.id == 1) {
				this.mc.displayGuiScreen(new GuiDedFinder(this));
			} else if (button.id == 2) {
				this.mc.displayGuiScreen(new GuiSessionStealer(this));
			} else if (button.id == 3) {
				this.mc.displayGuiScreen(new GuiSubDomain(this));
			}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(this.fontRendererObj, "Utilities", this.width / 2, 20, 16777215);
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