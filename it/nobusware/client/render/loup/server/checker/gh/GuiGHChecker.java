package it.nobusware.client.render.loup.server.checker.gh;

import java.io.IOException;
import java.net.UnknownHostException;

import org.lwjgl.input.Keyboard;

import it.nobusware.client.render.loup.server.GuiServerChecks;
import it.nobusware.client.utils.ExpandButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;

public class GuiGHChecker extends GuiScreen {
  private static final String[] stateStrings = new String[] { "", "§2Searching...", 
      "§2Resolving...", "§4Unknown Host!", "§4Cancelled!", "§2Done!", 
      "§4An error occurred!" };
  
  private GuiMultiplayer prevMenu;
  
  private GuiServerChecks prevPls;
  
  private GuiTextField ipBox;
  
  private GuiTextField portBox;
  
  private GuiTextField maxThreadsBox;
  
  private int checked;
  
  private int working;
  
  public GuiGHChecker(GuiMultiplayer prevMultiplayerMenu, GuiServerChecks prevPlus) {
    this.prevMenu = prevMultiplayerMenu;
    this.prevPls = prevPlus;
  }
  
  public void updateScreen() {
    this.ipBox.updateCursorCounter();
    ((GuiButton)this.buttonList.get(0)).displayString = "Search";
    this.ipBox.setEnabled(true);
    this.portBox.setEnabled(true);
    this.maxThreadsBox.setEnabled(true);
    ((GuiButton)this.buttonList.get(0)).enabled = (
      isInteger(this.maxThreadsBox.getText()) && 
      !this.ipBox.getText().isEmpty());
  }
  
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    this.working = 0;
    this.checked = 0;
    this.buttonList.clear();
    this.buttonList.add(new ExpandButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12 + 16, 
          "Check"));
    this.buttonList.add(new ExpandButton(2, this.width / 2 - 100, this.height / 4 + 120 + 12 + 16, 
          "Back"));
    this.ipBox = 
      new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 
        this.height / 4 + 34, 200, 20);
    this.ipBox.setMaxStringLength(200);
    this.ipBox.setText("127.0.0.1");
    this.ipBox.setFocused(true);
    this.portBox = 
      new GuiTextField(1, this.fontRendererObj, this.width / 2 - 32, 
        this.height / 4 + 76, 40, 12);
    this.portBox.setMaxStringLength(5);
    this.portBox.setFocused(false);
    this.portBox.setText(Integer.toString(25599));
    this.maxThreadsBox = 
      new GuiTextField(1, this.fontRendererObj, this.width / 2 - 32, 
        this.height / 4 + 58, 40, 12);
    this.maxThreadsBox.setMaxStringLength(5);
    this.maxThreadsBox.setFocused(false);
    this.maxThreadsBox.setText(Integer.toString(25500));
  }
  
  public void onGuiClosed() {
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton clickedButton) {
    if (clickedButton.enabled)
      if (clickedButton.id == 0) {
        (new Thread("ports") {
            public void run() {
              try {
                for (int i = Integer.parseInt(GuiGHChecker.this.maxThreadsBox.getText()); i <= Integer.parseInt(GuiGHChecker.this.portBox.getText()); i++) {
                  String serverip = String.valueOf(GuiGHChecker.this.ipBox.getText()) + ":" + i;
                  OldServerPinger pinger = new OldServerPinger();
                  try {
                    ServerData data = new ServerData("§aGH Checking " + i, serverip);
                    pinger.ping(data);
                    Thread.sleep(500L);
                    if (data.pingToServer > 0L) {
                      GuiGHChecker.this.prevMenu.savedServerList.addServerData(data);
                      GuiGHChecker.this.working = GuiGHChecker.this.working + 1;
                    } 
                  } catch (UnknownHostException unknownHostException) {
                  
                  } catch (Exception exception) {}
                  pinger.clearPendingNetworks();
                  GuiGHChecker.this.checked = GuiGHChecker.this.checked + 1;
                  GuiGHChecker.this.prevMenu.savedServerList.saveServerList();
                  GuiGHChecker.this.prevMenu.serverListSelector.func_148195_a(GuiGHChecker.this.prevMenu.savedServerList);
                } 
              } catch (Exception exception) {}
            }
          }).start();
      } else if (clickedButton.id == 2) {
        this.mc.displayGuiScreen((GuiScreen)this.prevPls);
      }  
  }
  
  private boolean serverInList(String ip) {
    for (int i = 0; i < this.prevMenu.savedServerList.countServers(); i++) {
      if ((this.prevMenu.savedServerList.getServerData(i)).serverIP.equals(ip))
        return true; 
    } 
    return false;
  }
  
  protected void keyTyped(char par1, int par2) {
    this.ipBox.textboxKeyTyped(par1, par2);
    this.maxThreadsBox.textboxKeyTyped(par1, par2);
    this.portBox.textboxKeyTyped(par1, par2);
    if (par2 == 28 || par2 == 156)
      actionPerformed((GuiButton) this.buttonList.get(0)); 
  }
  
  protected void mouseClicked(int par1, int par2, int par3) throws IOException {
    super.mouseClicked(par1, par2, par3);
    this.ipBox.mouseClicked(par1, par2, par3);
    this.maxThreadsBox.mouseClicked(par1, par2, par3);
    this.portBox.mouseClicked(par1, par2, par3);
  }
  
  public void drawScreen(int par1, int par2, float par3) {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "Server checker (GH Bypass)", this.width / 2, 20, 
        16777215);
    drawCenteredString(this.fontRendererObj, 
        "Checker� porte in un Port Range", this.width / 2, 40, 
        10526880);
    drawCenteredString(this.fontRendererObj, 
        "of the IP you type into the field below.", this.width / 2, 50, 10526880);
    drawCenteredString(this.fontRendererObj, 
        "The servers it finds will be added to your server list.", 
        this.width / 2, 60, 10526880);
    drawString(this.fontRendererObj, "Server address:", this.width / 2 - 100, 
        this.height / 4 + 24, 10526880);
    this.ipBox.drawTextBox();
    this.portBox.drawTextBox();
    drawString(this.fontRendererObj, "Port number:", this.width / 2 - 100, 
        this.height / 4 + 60, 10526880);
    this.maxThreadsBox.drawTextBox();
    drawString(this.fontRendererObj, "Port number:", this.width / 2 - 100, 
        this.height / 4 + 60 + 20, 10526880);
    drawCenteredString(this.fontRendererObj, "", this.width / 2, 
        this.height / 4 + 1, 10526880);
    drawString(this.fontRendererObj, "Checked: " + this.checked, 
        this.width / 2 - 100, this.height / 4 + 84 + 16, 10526880);
    drawString(this.fontRendererObj, "Working: " + this.working, this.width / 2 - 100, 
        this.height / 4 + 94 + 16, 10526880);
    super.drawScreen(par1, par2, par3);
  }
  
  public boolean isInteger(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    } 
    return true;
  }
}
