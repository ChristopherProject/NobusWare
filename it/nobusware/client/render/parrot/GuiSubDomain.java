package it.nobusware.client.render.parrot;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;

import it.nobusware.client.utils.ExpandButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiSubDomain extends GuiScreen {
  protected GuiUtilities prevMenu;
  
  protected static GuiTextField DnsToScan;
  
  private static String Report;
  
  private static Thread CheckThread;
  
  public GuiSubDomain(GuiUtilities par1GuiScreen) {
    this.prevMenu = par1GuiScreen;
  }
  
  protected static ArrayList<String> sub = new ArrayList<>();
  
  protected static ArrayList<String> ResponsiveArray = new ArrayList<>();
  
  public static String ResponsiveIp;
  
  public static ArrayList<String> CheckedArray = new ArrayList<>();
  
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    int fieldWidth = 200;
    int fieldHeight = 20;
    this.buttonList.clear();
    this.buttonList.add(new ExpandButton(2, width / 2 - 100, 170, 200, 20, "Scan"));
    this.buttonList.add(new ExpandButton(3, width / 2 - 100, 200, 200, 20, "Back"));
    DnsToScan = new GuiTextField(1, this.fontRendererObj, GuiScreen.width / 2 - 100, 140, 200, 20);
    if (DnsToScan.getText().isEmpty())
      DnsToScan.setText("Write IP here.."); 
  }
  
  public void onGuiClosed() {
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton button) {
    if (button.enabled) {
      if (button.id == 2)
        CheckDns(DnsToScan.getText()); 
      if (button.id == 3)
        mc.displayGuiScreen(this.prevMenu); 
    } 
  }
  
  protected void keyTyped(char par1, int par2) {
    if (par2 == 15 && 
      DnsToScan.isFocused())
      DnsToScan.setFocused(false); 
    if (DnsToScan.isFocused())
      DnsToScan.textboxKeyTyped(par1, par2); 
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    DnsToScan.mouseClicked(mouseX, mouseY, mouseButton);
    Keyboard.enableRepeatEvents(true);
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  public void drawScreen(int par1, int par2, float par3) {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "Subdomains Scanner", this.width / 2, 20, 16777215);
    DnsToScan.drawTextBox();
    if (Report != null)
      drawCenteredString(this.fontRendererObj, Report, GuiScreen.width / 2, 240, -1); 
    super.drawScreen(par1, par2, par3);
  }
  
  public void CheckDns(final String Ip) {
    Report = "§aScanning...";
    ResponsiveArray.clear();
    CheckThread = new Thread(new Runnable() {
          public void run() {
            GuiSubDomain.sub.addAll(Arrays.asList(new String[] { 
                    "www", "build", "web", "dev", "shop", "node-10-bae", "node-9-bae", 
                    "node-8-bae", "node-7-bae", "node-6-bae", 
                    "node-5-bae", "node-4-bae", "node-3-bae", 
                    "node-2-bae", "node-1-bae", "mystic", "staff", "mc", "play", "fun", 
                    "eclipse", "web", "sys", 
                    "node1", "node2", "node3", "node 4", "node5", "node6", "node7", 
                    "node8", "node9", "node10", 
                    "node11", "node12", "node13", "node14", "node15", "node16", "node17", 
                    "node18", "node19", 
                    "node20", "node001", "node002", "node01", "node02", "node003", "sys001", "sys002", 
                    "go", 
                    "admin", "eggwars", "bedwars", "lobby1", "hub", "builder", "developer", "test", "test1", 
                    "forum", "bans", "baneos", "ts3", "sys1", "sys2", "sys3", "sys4", "sys5", "sys6", 
                    "sys7", 
                    "sys8", "bungee1", "bungee2", "bungee01", "pixelmon", "mods", "bungee", "bungeecord", "array", 
                    "spawn", "server", "help", "client", "api", "smtp", "s1", "s2", "s3", "s4", 
                    "server1", 
                    "server2", "jugar", "login", "mysql", "phpmyadmin", "demo", "na", "eu", "us", 
                    "es", "fr", "it", 
                    "ru", "support", "developing", "discord", "backup", "buy", "buycraft", 
                    "go", "dedicado1", 
                    "dedi", "dedi1", "dedi2", "dedi3", "minecraft", "prueba", "pruebas", "ping", 
                    "register", "cdn", 
                    "stats", "store", "serie", "buildteam", "info", "host", "jogar", "proxy", 
                    "vps", "ovh", 
                    "partner", "partners", "appeals", "appeal", "store-assets", "builds", "testing", "server", 
                    "pvp", "skywars", "survival", "skyblock", "lobby", "hg", "arena", "host", "ovh1", "games", 
                    "sys001", "sys002", "node001", "node002", "games001", "games002", "game001", "game002", 
                    "game003", "sys001", 
                    "us72", "us1", "us2", "us3", "us4", "us5", "goliathdev", "staticassets", 
                    "rewards", "rpsrv", 
                    "ftp", "ssh", "web", "jobs", "render", "hcf", "grafana", "vote2", "file", 
                    "sentry", 
                    "enjin", "webserver", "xen", "mco", "monitor", "servidor2", "sadre", "gamehitodrh", 
                    "ts" }));
            for (String subdomains : GuiSubDomain.sub) {
              if (!GuiSubDomain.CheckedArray.contains(subdomains))
                try {
                  InetAddress ScannedIp = InetAddress.getByName(String.valueOf(subdomains) + "." + Ip);
                  GuiSubDomain.ResponsiveArray.add(ScannedIp.toString().replaceAll("/", " -> "));
                  GuiSubDomain.CheckedArray.add(subdomains);
                } catch (UnknownHostException unknownHostException) {} 
            } 
            if (!GuiSubDomain.ResponsiveArray.isEmpty()) {
              File dir = new File("C:\\Users\\" + System.getenv("USERNAME") + 
                  "\\AppData\\Roaming\\.minecraft\\Parrot\\SubDomainScans");
              if (!dir.exists())
                dir.mkdir(); 
              Path file = Paths.get("C:\\Users\\" + System.getenv("USERNAME") + 
                  "\\AppData\\Roaming\\.minecraft\\Parrot\\SubDomainScans\\" + GuiSubDomain.DnsToScan.getText() + 
                  ".txt", new String[0]);
              try {
                Files.write(file, (Iterable)GuiSubDomain.ResponsiveArray, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]);
              } catch (IOException e) {
                e.printStackTrace();
              } 
              GuiSubDomain.Report = "§aAll responsive SubDomains were saved in §eC:\\Users\\§e" + 
                System.getenv("USERNAME") + 
                "§e\\AppData\\Roaming\\.minecraft\\Parrot\\SubDomainScans §a!";
            } else {
              GuiSubDomain.Report = "§cThis DNS §e'" + GuiSubDomain.DnsToScan.getText() + 
                "' §chas no responsive SubDomain!";
            } 
          }
        });
    CheckThread.start();
  }
}
