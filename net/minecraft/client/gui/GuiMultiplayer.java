package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import it.nobusware.client.shader.DrawShader;
import it.nobusware.client.utils.ImageButton;
import it.nobusware.client.utils.VerSwitcherButton;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger = LogManager.getLogger();
    private final OldServerPinger oldServerPinger = new OldServerPinger();
    private GuiScreen parentScreen;
    private ServerSelectionList serverListSelector;
    private ServerList savedServerList;
    private GuiButton btnEditServer;
    private GuiButton btnSelectServer;
    private GuiButton btnDeleteServer;
    private boolean deletingServer;
    private boolean addingServer;
    private boolean editingServer;
    private boolean directConnect;
    private String field_146812_y;
    private ServerData selectedServer;
    private LanServerDetector.LanServerList lanServerList;
    private LanServerDetector.ThreadLanServerFind lanServerDetector;
    private boolean initialized;
    private static final String __OBFID = "CL_00000814";

    public GuiMultiplayer(GuiScreen parentScreen)
    {
        this.parentScreen = parentScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();

        if (!this.initialized)
        {
            this.initialized = true;
            this.savedServerList = new ServerList(this.mc);
            this.savedServerList.loadServerList();
            this.lanServerList = new LanServerDetector.LanServerList();

            try
            {
                this.lanServerDetector = new LanServerDetector.ThreadLanServerFind(this.lanServerList);
                this.lanServerDetector.start();
            }
            catch (Exception var2)
            {
                logger.warn("Unable to start LAN server detection: " + var2.getMessage());
            }

            this.serverListSelector = new ServerSelectionList(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
            this.serverListSelector.func_148195_a(this.savedServerList);
        }
        else
        {
            this.serverListSelector.setDimensions(this.width, this.height, 32, this.height - 64);
        }
        this.createButtons();
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.serverListSelector.func_178039_p();
    }

    public void createButtons() {
        this.buttonList.add(this.btnSelectServer = (GuiButton)new ImageButton(1, width / 2 - 120 - 20 - 20 - 20, height - 62, 40, 40, I18n.format("selectServer.select", new Object[0]), "nobita/button/multiplayer/join.png"));
        this.buttonList.add(new ImageButton(4, width / 2 - 15 - 25, height - 62, 40, 40, "Direct", "nobita/button/multiplayer/direct.png"));
        this.buttonList.add(new ImageButton(3, width / 2 + 10, height - 62, 40, 40, I18n.format("selectServer.add", new Object[0]), "nobita/button/multiplayer/add.png"));
        this.buttonList.add(this.btnEditServer = (GuiButton)new ImageButton(777, width / 2 - 46 - 20 - 20, height - 62, 40, 40, "Reload", "nobita/button/multiplayer/reload.png"));
        this.buttonList.add(this.btnEditServer = (GuiButton)new ImageButton(7, width / 2 - 93 - 20 - 20, height - 62, 40, 40, I18n.format("selectServer.edit", new Object[0]), "nobita/button/multiplayer/edit.png"));
        this.buttonList.add(this.btnDeleteServer = (GuiButton)new ImageButton(2, width / 2 + 47 + 20 + 20 - 27, height - 62, 40, 40, I18n.format("selectServer.delete", new Object[0]), "nobita/button/multiplayer/delete.png"));
        this.buttonList.add(new ImageButton(0, width / 2 + 80 + 20 + 20 + 20 - 34, height - 62, 40, 40, I18n.format("gui.cancel", new Object[0]), "nobita/button/multiplayer/exit.png"));
        selectServer(this.serverListSelector.func_148193_k());
        this.buttonList.add(new VerSwitcherButton(1337, -35, 5));
      }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    	this.mc.getNobita().getDiscord().update("Nel Menu' MultiPlayer", this.mc.getSession().getUsername() + " - NobusWare Client");
        super.updateScreen();

        if (this.lanServerList.getWasUpdated())
        {
            List var1 = this.lanServerList.getLanServers();
            this.lanServerList.setWasNotUpdated();
            this.serverListSelector.func_148194_a(var1);
        }

        this.oldServerPinger.pingPendingNetworks();
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);

        if (this.lanServerDetector != null)
        {
            this.lanServerDetector.interrupt();
            this.lanServerDetector = null;
        }

        this.oldServerPinger.clearPendingNetworks();
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            GuiListExtended.IGuiListEntry var2 = this.serverListSelector.func_148193_k() < 0 ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());

            if (button.id == 2 && var2 instanceof ServerListEntryNormal)
            {
                String var9 = ((ServerListEntryNormal)var2).getServerData().serverName;

                if (var9 != null)
                {
                    this.deletingServer = true;
                    String var4 = I18n.format("selectServer.deleteQuestion", new Object[0]);
                    String var5 = "\'" + var9 + "\' " + I18n.format("selectServer.deleteWarning", new Object[0]);
                    String var6 = I18n.format("selectServer.deleteButton", new Object[0]);
                    String var7 = I18n.format("gui.cancel", new Object[0]);
                    GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, this.serverListSelector.func_148193_k());
                    this.mc.displayGuiScreen(var8);
                }
            }
            else if (button.id == 1)
            {
                this.connectToSelected();
            }
            else if (button.id == 4)
            {
                this.directConnect = true;
                this.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
            }
            else if (button.id == 3)
            {
                this.addingServer = true;
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
            }
            else if (button.id == 7 && var2 instanceof ServerListEntryNormal)
            {
                this.editingServer = true;
                ServerData var3 = ((ServerListEntryNormal)var2).getServerData();
                this.selectedServer = new ServerData(var3.serverName, var3.serverIP);
                this.selectedServer.copyFrom(var3);
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
            }
            else if (button.id == 1337)
                ((VerSwitcherButton)button).buttonPressed(); 
            else if (button.id == 0)
            {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (button.id == 777)
            {
                this.refreshServerList();
            }
        }
    }

    private void refreshServerList()
    {
        this.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
    }

    public void confirmClicked(boolean result, int id)
    {
        GuiListExtended.IGuiListEntry var3 = this.serverListSelector.func_148193_k() < 0 ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());

        if (this.deletingServer)
        {
            this.deletingServer = false;

            if (result && var3 instanceof ServerListEntryNormal)
            {
                this.savedServerList.removeServerData(this.serverListSelector.func_148193_k());
                this.savedServerList.saveServerList();
                this.serverListSelector.func_148192_c(-1);
                this.serverListSelector.func_148195_a(this.savedServerList);
            }

            this.mc.displayGuiScreen(this);
        }
        else if (this.directConnect)
        {
            this.directConnect = false;

            if (result)
            {
                this.connectToServer(this.selectedServer);
            }
            else
            {
                this.mc.displayGuiScreen(this);
            }
        }
        else if (this.addingServer)
        {
            this.addingServer = false;

            if (result)
            {
                this.savedServerList.addServerData(this.selectedServer);
                this.savedServerList.saveServerList();
                this.serverListSelector.func_148192_c(-1);
                this.serverListSelector.func_148195_a(this.savedServerList);
            }

            this.mc.displayGuiScreen(this);
        }
        else if (this.editingServer)
        {
            this.editingServer = false;

            if (result && var3 instanceof ServerListEntryNormal)
            {
                ServerData var4 = ((ServerListEntryNormal)var3).getServerData();
                var4.serverName = this.selectedServer.serverName;
                var4.serverIP = this.selectedServer.serverIP;
                var4.copyFrom(this.selectedServer);
                this.savedServerList.saveServerList();
                this.serverListSelector.func_148195_a(this.savedServerList);
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Fired when a key is typed (except F11 who toggle full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        int var3 = this.serverListSelector.func_148193_k();
        GuiListExtended.IGuiListEntry var4 = var3 < 0 ? null : this.serverListSelector.getListEntry(var3);

        if (keyCode == 63)
        {
            this.refreshServerList();
        }
        else
        {
            if (var3 >= 0)
            {
                if (keyCode == 200)
                {
                    if (isShiftKeyDown())
                    {
                        if (var3 > 0 && var4 instanceof ServerListEntryNormal)
                        {
                            this.savedServerList.swapServers(var3, var3 - 1);
                            this.selectServer(this.serverListSelector.func_148193_k() - 1);
                            this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                            this.serverListSelector.func_148195_a(this.savedServerList);
                        }
                    }
                    else if (var3 > 0)
                    {
                        this.selectServer(this.serverListSelector.func_148193_k() - 1);
                        this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());

                        if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan)
                        {
                            if (this.serverListSelector.func_148193_k() > 0)
                            {
                                this.selectServer(this.serverListSelector.getSize() - 1);
                                this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                            }
                            else
                            {
                                this.selectServer(-1);
                            }
                        }
                    }
                    else
                    {
                        this.selectServer(-1);
                    }
                }
                else if (keyCode == 208)
                {
                    if (isShiftKeyDown())
                    {
                        if (var3 < this.savedServerList.countServers() - 1)
                        {
                            this.savedServerList.swapServers(var3, var3 + 1);
                            this.selectServer(var3 + 1);
                            this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                            this.serverListSelector.func_148195_a(this.savedServerList);
                        }
                    }
                    else if (var3 < this.serverListSelector.getSize())
                    {
                        this.selectServer(this.serverListSelector.func_148193_k() + 1);
                        this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());

                        if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan)
                        {
                            if (this.serverListSelector.func_148193_k() < this.serverListSelector.getSize() - 1)
                            {
                                this.selectServer(this.serverListSelector.getSize() + 1);
                                this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                            }
                            else
                            {
                                this.selectServer(-1);
                            }
                        }
                    }
                    else
                    {
                        this.selectServer(-1);
                    }
                }
                else if (keyCode != 28 && keyCode != 156)
                {
                    super.keyTyped(typedChar, keyCode);
                }
                else
                {
                    this.actionPerformed((GuiButton)this.buttonList.get(2));
                }
            }
            else
            {
                super.keyTyped(typedChar, keyCode);
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	this.field_146812_y = null;
        DrawShader.Reich();
      //  drawRect(0.0F, 0.0F, s1.getScaledWidth(), 32.0F, -2147483648);
      
        
        this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
        //this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.field_146812_y != null)
        {
            this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.field_146812_y)), mouseX, mouseY);
        }
    }
    
    public static void drawRect(float x1, float y1, float x2, float y2, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        color(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
      }
      
      public static void color(int color) {
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
      }

    public void connectToSelected()
    {
        GuiListExtended.IGuiListEntry var1 = this.serverListSelector.func_148193_k() < 0 ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());

        if (var1 instanceof ServerListEntryNormal)
        {
            this.connectToServer(((ServerListEntryNormal)var1).getServerData());
        }
        else if (var1 instanceof ServerListEntryLanDetected)
        {
            LanServerDetector.LanServer var2 = ((ServerListEntryLanDetected)var1).getLanServer();
            this.connectToServer(new ServerData(var2.getServerMotd(), var2.getServerIpPort()));
        }
    }

    private void connectToServer(ServerData server)
    {
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, server));
    }

    public void selectServer(int index)
    {
        this.serverListSelector.func_148192_c(index);
        GuiListExtended.IGuiListEntry var2 = index < 0 ? null : this.serverListSelector.getListEntry(index);
        this.btnSelectServer.enabled = false;
        this.btnEditServer.enabled = false;
        this.btnDeleteServer.enabled = false;

        if (var2 != null && !(var2 instanceof ServerListEntryLanScan))
        {
            this.btnSelectServer.enabled = true;

            if (var2 instanceof ServerListEntryNormal)
            {
                this.btnEditServer.enabled = true;
                this.btnDeleteServer.enabled = true;
            }
        }
    }

    public OldServerPinger getOldServerPinger()
    {
        return this.oldServerPinger;
    }

    public void func_146793_a(String p_146793_1_)
    {
        this.field_146812_y = p_146793_1_;
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.serverListSelector.func_148179_a(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when a mouse button is released.  Args : mouseX, mouseY, releaseButton
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        this.serverListSelector.func_148181_b(mouseX, mouseY, state);
    }

    public ServerList getServerList()
    {
        return this.savedServerList;
    }

    public boolean func_175392_a(ServerListEntryNormal p_175392_1_, int p_175392_2_)
    {
        return p_175392_2_ > 0;
    }

    public boolean func_175394_b(ServerListEntryNormal p_175394_1_, int p_175394_2_)
    {
        return p_175394_2_ < this.savedServerList.countServers() - 1;
    }

    public void func_175391_a(ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_)
    {
        int var4 = p_175391_3_ ? 0 : p_175391_2_ - 1;
        this.savedServerList.swapServers(p_175391_2_, var4);

        if (this.serverListSelector.func_148193_k() == p_175391_2_)
        {
            this.selectServer(var4);
        }

        this.serverListSelector.func_148195_a(this.savedServerList);
    }

    public void func_175393_b(ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_)
    {
        int var4 = p_175393_3_ ? this.savedServerList.countServers() - 1 : p_175393_2_ + 1;
        this.savedServerList.swapServers(p_175393_2_, var4);

        if (this.serverListSelector.func_148193_k() == p_175393_2_)
        {
            this.selectServer(var4);
        }

        this.serverListSelector.func_148195_a(this.savedServerList);
    }
}
