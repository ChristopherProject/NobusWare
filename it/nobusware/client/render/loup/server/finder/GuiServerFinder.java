package it.nobusware.client.render.loup.server.finder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import it.nobusware.client.render.loup.server.GuiServerChecks;
import it.nobusware.client.utils.ExpandButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;

public class GuiServerFinder extends GuiScreen {
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
  
  private ServerFinderState state;
  
  enum ServerFinderState {
    NOT_RUNNING, SEARCHING, RESOLVING, UNKNOWN_HOST, CANCELLED, DONE, ERROR;
    
    public boolean isRunning() {
      return !(this != SEARCHING && this != RESOLVING);
    }
    
    public String toString() {
      return GuiServerFinder.stateStrings[ordinal()];
    }
  }
  
  public GuiServerFinder(GuiMultiplayer prevMultiplayerMenu, GuiServerChecks prevPlus) {
    this.prevMenu = prevMultiplayerMenu;
    this.prevPls = prevPlus;
  }
  
  public void updateScreen() {
    this.ipBox.updateCursorCounter();
    ((GuiButton)this.buttonList.get(0)).displayString = 
      this.state.isRunning() ? "Cancel" : "Search";
    this.ipBox.setEnabled(!this.state.isRunning());
    this.portBox.setEnabled(!this.state.isRunning());
    this.maxThreadsBox.setEnabled(!this.state.isRunning());
    ((GuiButton)this.buttonList.get(0)).enabled = (
      isInteger(this.maxThreadsBox.getText()) && 
      !this.ipBox.getText().isEmpty());
  }
  
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    this.buttonList.clear();
    this.buttonList.add(new ExpandButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12 + 16, 
          "Search"));
    this.buttonList.add(new ExpandButton(2, this.width / 2 - 100, this.height / 4 + 120 + 12 + 16, 
          "Back"));
    this.ipBox = 
      new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 
        this.height / 4 + 34, 200, 20);
    this.ipBox.setMaxStringLength(200);
    this.ipBox.setFocused(true);
    this.portBox = 
      new GuiTextField(1, this.fontRendererObj, this.width / 2 - 32, 
        this.height / 4 + 76, 40, 12);
    this.portBox.setMaxStringLength(5);
    this.portBox.setFocused(true);
    this.portBox.setText(
        Integer.toString(25565));
    this.maxThreadsBox = 
      new GuiTextField(1, this.fontRendererObj, this.width / 2 - 32, 
        this.height / 4 + 58, 48, 12);
    this.maxThreadsBox.setMaxStringLength(5);
    this.maxThreadsBox.setFocused(false);
    this.maxThreadsBox.setText(
        Integer.toString(128));
    this.state = ServerFinderState.NOT_RUNNING;
  }
  
  public void onGuiClosed() {
    this.state = ServerFinderState.CANCELLED;
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton clickedButton) {
    if (clickedButton.enabled)
      if (clickedButton.id == 0) {
        if (this.state.isRunning()) {
          this.state = ServerFinderState.CANCELLED;
        } else {
          this.state = ServerFinderState.RESOLVING;
          this.checked = 0;
          this.working = 0;
          (new Thread("Server Finder") {
              public void run() {
                try {
                  InetAddress addr = 
                    InetAddress.getByName(GuiServerFinder.this.ipBox.getText()
                      .split(":")[0].trim());
                  int port = Integer.parseInt(GuiServerFinder.this.portBox.getText());
                  int[] ipParts = new int[4];
                  for (int i = 0; i < 4; i++)
                    ipParts[i] = addr.getAddress()[i] & 0xFF; 
                  GuiServerFinder.this.state = GuiServerFinder.ServerFinderState.SEARCHING;
                  ArrayList<Checker> pingers = 
                    new ArrayList<Checker>();
                  int[] changes = { 0, 1, -1, 2, -2, 3, -3 };
                  byte b;
                  int j, arrayOfInt1[];
                  for (j = (arrayOfInt1 = changes).length, b = 0; b < j; ) {
                    int change = arrayOfInt1[b];
                    for (int i2 = 0; i2 <= 1020; i2++) {
                      if (GuiServerFinder.this.state == GuiServerFinder.ServerFinderState.CANCELLED)
                        return; 
                      int[] ipParts2 = (int[])ipParts.clone();
                      ipParts2[2] = 
                        ipParts[2] + change & 0xFF;
                      ipParts2[3] = i2;
                      String ip = 
                        String.valueOf(ipParts2[0]) + "." + ipParts2[1] + 
                        "." + ipParts2[2] + "." + 
                        ipParts2[3];
                      Checker pinger = 
                        new Checker();
                      pinger.ping(ip, port);
                      pingers.add(pinger);
                      while (pingers.size() >= Integer.parseInt(GuiServerFinder.this.maxThreadsBox.getText())) {
                        if (GuiServerFinder.this.state == GuiServerFinder.ServerFinderState.CANCELLED)
                          return; 
                        GuiServerFinder.this.updatePingers(pingers);
                      } 
                    } 
                    b++;
                  } 
                  while (pingers.size() > 0) {
                    if (GuiServerFinder.this.state == GuiServerFinder.ServerFinderState.CANCELLED)
                      return; 
                    GuiServerFinder.this.updatePingers(pingers);
                  } 
                  GuiServerFinder.this.state = GuiServerFinder.ServerFinderState.DONE;
                } catch (UnknownHostException e) {
                  GuiServerFinder.this.state = GuiServerFinder.ServerFinderState.UNKNOWN_HOST;
                } catch (Exception e) {
                  e.printStackTrace();
                  GuiServerFinder.this.state = GuiServerFinder.ServerFinderState.ERROR;
                } 
              }
            }).start();
        } 
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
  
  private void updatePingers(ArrayList<Checker> pingers) {
    for (int i = 0; i < pingers.size(); i++) {
      if (!((Checker)pingers.get(i)).isStillPinging()) {
        this.checked++;
        if (((Checker)pingers.get(i)).isWorking()) {
          this.working++;
          if (!serverInList(((Checker)pingers.get(i)).server.serverIP)) {
            this.prevMenu.savedServerList
              .addServerData(new ServerData("§cPlease grief me #" + 
                  this.working, ((Checker)pingers.get(i)).server.serverIP));
            this.prevMenu.savedServerList
              .saveServerList();
            this.prevMenu.serverListSelector
              .func_148192_c(-1);
            this.prevMenu.serverListSelector
              .func_148195_a(this.prevMenu.savedServerList);
          } 
        } 
        pingers.remove(i);
      } 
    } 
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
  }
  
  public void drawScreen(int par1, int par2, float par3) {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "Server Finder", this.width / 2, 20, 
        16777215);
    drawCenteredString(this.fontRendererObj, 
        "This will search for servers with similar IPs", this.width / 2, 40, 
        10526880);
    drawCenteredString(this.fontRendererObj, 
        "to the IP you type into the field below.", this.width / 2, 50, 10526880);
    drawCenteredString(this.fontRendererObj, 
        "The servers it finds will be added to your server list.", 
        this.width / 2, 60, 10526880);
    drawString(this.fontRendererObj, "Server address:", this.width / 2 - 100, 
        this.height / 4 + 24, 10526880);
    this.ipBox.drawTextBox();
    this.portBox.drawTextBox();
    drawString(this.fontRendererObj, "Max. threads:", this.width / 2 - 100, 
        this.height / 4 + 60, 10526880);
    this.maxThreadsBox.drawTextBox();
    drawString(this.fontRendererObj, "Port number:", this.width / 2 - 100, 
        this.height / 4 + 60 + 20, 10526880);
    drawCenteredString(this.fontRendererObj, this.state.toString(), this.width / 2, 
        this.height / 4 + 1, 10526880);
    drawString(this.fontRendererObj, "Checked: " + this.checked + " / 7147", 
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
