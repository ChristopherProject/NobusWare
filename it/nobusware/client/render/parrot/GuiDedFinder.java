package it.nobusware.client.render.parrot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lwjgl.input.Keyboard;

import ch.jamiete.mcping.MinecraftPing;
import ch.jamiete.mcping.MinecraftPingReply;
import it.nobusware.client.utils.ExpandButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiDedFinder extends GuiScreen {
  protected GuiUtilities prevMenu;
  
  protected static GuiTextField IpToScan;
  
  protected static GuiTextField ThreadNum;
  
  protected static GuiTextField FromPort;
  
  protected static GuiTextField ToPort;
  
  protected static String Report;
  
  public GuiDedFinder(GuiUtilities par1GuiScreen) {
    this.prevMenu = par1GuiScreen;
  }
  
  protected static ArrayList<String> sub = new ArrayList<>();
  
  protected static ArrayList<String> Result = new ArrayList<>();
  
  protected static ExecutorService executor;
  
  protected static boolean Started = false;
  
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    int fieldWidth = 200;
    int fieldHeight = 20;
    this.buttonList.clear();
    this.buttonList.add(new ExpandButton(4, width / 2 - 100, 190, 200, 20, "Stop"));
    this.buttonList.add(new ExpandButton(2, width / 2 - 100, 215, 200, 20, "Scan"));
    this.buttonList.add(new ExpandButton(3, width / 2 - 100, 240, 200, 20, "Back"));
    IpToScan = new GuiTextField(1, this.fontRendererObj, GuiScreen.width / 2 - 100, 140, 200, 20);
    if (IpToScan.getText().isEmpty())
      IpToScan.setText("Put a DNS like mydns.it"); 
    ThreadNum = new GuiTextField(1, this.fontRendererObj, GuiScreen.width / 2 - 65, 165, 30, 15);
    ThreadNum.setMaxStringLength(3);
    if (ThreadNum.getText().isEmpty())
      ThreadNum.setText("40"); 
    FromPort = new GuiTextField(1, this.fontRendererObj, GuiScreen.width / 2, 165, 40, 15);
    FromPort.setMaxStringLength(5);
    if (FromPort.getText().isEmpty())
      FromPort.setText("1"); 
    ToPort = new GuiTextField(1, this.fontRendererObj, GuiScreen.width / 2 + 65, 165, 40, 15);
    ToPort.setMaxStringLength(5);
    if (ToPort.getText().isEmpty())
      ToPort.setText("65000"); 
  }
  
  public void onGuiClosed() {
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton button) throws IOException {
    if (button.enabled) {
      if (button.id == 2) {
        executor = Executors.newFixedThreadPool(Integer.parseInt(ThreadNum.getText()));
        ScanForDed(IpToScan.getText(), executor);
      } 
      if (button.id == 3)
        mc.displayGuiScreen(this.prevMenu); 
      if (button.id == 4)
        if (Started) {
          Thread.currentThread().interrupt();
          Started = false;
          Report = "§aThreads succesfully stopped";
        } else {
          Report = "§aNo current Threads open";
        }  
    } 
  }
  
  protected void keyTyped(char par1, int par2) {
    if (par2 == 15) {
      if (IpToScan.isFocused())
        IpToScan.setFocused(false); 
      if (ThreadNum.isFocused())
        ThreadNum.setFocused(false); 
      if (FromPort.isFocused())
        FromPort.setFocused(false); 
      if (ToPort.isFocused())
        ToPort.setFocused(false); 
    } 
    if (IpToScan.isFocused())
      IpToScan.textboxKeyTyped(par1, par2); 
    if (ThreadNum.isFocused())
      ThreadNum.textboxKeyTyped(par1, par2); 
    if (FromPort.isFocused())
      FromPort.textboxKeyTyped(par1, par2); 
    if (ToPort.isFocused())
      ToPort.textboxKeyTyped(par1, par2); 
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    IpToScan.mouseClicked(mouseX, mouseY, mouseButton);
    ThreadNum.mouseClicked(mouseX, mouseY, mouseButton);
    FromPort.mouseClicked(mouseX, mouseY, mouseButton);
    ToPort.mouseClicked(mouseX, mouseY, mouseButton);
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  public void drawScreen(int par1, int par2, float par3) {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "DedFinder", this.width / 2, 20, 16777215);
    drawString(this.fontRendererObj, "§eThread:", GuiScreen.width / 2 - 105, 169, -1);
    drawString(this.fontRendererObj, "§eFrom:", GuiScreen.width / 2 - 30, 169, -1);
    drawString(this.fontRendererObj, "§eTo:", GuiScreen.width / 2 + 45, 169, -1);
    IpToScan.drawTextBox();
    ThreadNum.drawTextBox();
    FromPort.drawTextBox();
    ToPort.drawTextBox();
    if (Report != null)
      drawCenteredString(this.fontRendererObj, Report, GuiScreen.width / 2, 270, -1); 
    super.drawScreen(par1, par2, par3);
  }
  
  public static void ScanForDed(final String Ip, ExecutorService executor) {
    Started = true;
    Report = "§aScanning... (You can do something else in the meantime!)";
    sub.clear();
    sub.addAll(Arrays.asList(new String[] { 
            "www", "build", "web", "dev", "shop", "node-10-bae", "node-9-bae", "node-8-bae", 
            "node-7-bae", "node-6-bae", 
            "node-5-bae", "node-4-bae", "node-3-bae", "node-2-bae", "node-1-bae", 
            "mystic", "staff", "mc", "play", "fun", 
            "eclipse", "web", "sys", "node1", "node2", "node3", "node 4", 
            "node5", "node6", "node7", 
            "node8", "node9", "node10", "node11", "node12", "node13", "node14", "node15", 
            "node16", "node17", 
            "node18", "node19", "node20", "node001", "node002", "node01", "node02", "node003", 
            "sys001", "sys002", 
            "go", "admin", "eggwars", "bedwars", "lobby1", "hub", "builder", "developer", 
            "test", "test1", 
            "forum", "bans", "baneos", "ts3", "sys1", "sys2", "sys3", "sys4", "sys5", "sys6", 
            "sys7", "sys8", "bungee1", "bungee2", "bungee01", "pixelmon", "mods", "bungee", "bungeecord", "array", 
            "spawn", "server", "help", "client", "api", "smtp", "s1", "s2", "s3", "s4", 
            "server1", "server2", 
            "jugar", "login", "mysql", "phpmyadmin", "demo", "na", "eu", "us", 
            "es", "fr", "it", "ru", "support", 
            "developing", "discord", "backup", "buy", "buycraft", 
            "go", "dedicado1", "dedi", "dedi1", "dedi2", 
            "dedi3", "minecraft", "prueba", "pruebas", "ping", 
            "register", "cdn", "stats", "store", "serie", 
            "buildteam", "info", "host", "jogar", "proxy", 
            "vps", "ovh", "partner", "partners", "appeals", "appeal", 
            "store-assets", "builds", "testing", "server", 
            "pvp", "skywars", "survival", "skyblock", "lobby", "hg", 
            "arena", "host", "ovh1", "games", 
            "sys001", "sys002", "node001", "node002", "games001", "games002", 
            "game001", "game002", "game003", "sys001", 
            "us72", "us1", "us2", "us3", "us4", "us5", "goliathdev", 
            "staticassets", "rewards", "rpsrv", 
            "ftp", "ssh", "web", "jobs", "render", "hcf", "grafana", "vote2", 
            "file", "sentry", 
            "enjin", "webserver", "xen", "mco", "monitor", "servidor2", "sadre", "gamehitodrh", 
            "ts" }));
    executor.submit(new Runnable() {
          public void run() {
            for (String subdomain : GuiDedFinder.sub) {
              for (int Port = Integer.parseInt(GuiDedFinder.FromPort.getText()); Port <= 
                Integer.parseInt(GuiDedFinder.ToPort.getText()); Port++) {
                System.out.println(String.valueOf(subdomain) + "." + Ip + " Port: " + Port);
                try {
                  MinecraftPingReply data = (new MinecraftPing()).getPing(String.valueOf(subdomain) + "." + Ip, Port);
                  GuiDedFinder.Result.add("Ip: " + data.getIp() + " Port: " + data.getPort() + " MOTD: " + data.getMotd() + 
                      " Version: " + data.getProtocolVersion() + " Online: " + data.getOnlinePlayers() + 
                      "/" + data.getMaxPlayers());
                } catch (IOException iOException) {}
              } 
            } 
            GuiDedFinder.Started = false;
            if (!GuiDedFinder.Result.isEmpty()) {
              File dir = new File("C:\\Users\\" + System.getenv("USERNAME") + 
                  "\\AppData\\Roaming\\.minecraft\\NobusWare\\DedFinderScan");
              if (!dir.exists())
                dir.mkdir(); 
              Path file = Paths.get("C:\\Users\\" + System.getenv("USERNAME") + 
                  "\\AppData\\Roaming\\.minecraft\\NobusWare\\DedFinderScan\\" + GuiDedFinder.IpToScan.getText() + ".txt", new String[0]);
              try {
                Files.write(file, (Iterable)GuiDedFinder.Result, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]);
              } catch (IOException e) {
                e.printStackTrace();
              } 
              GuiDedFinder.Report = "§aAll responsive Ded were saved in §eC:\\Users\\§e" + System.getenv("USERNAME") + 
                "§e\\AppData\\Roaming\\.minecraft\\NobusWare\\DedFinderScan §a!";
            } else {
              GuiDedFinder.Report = "§cThis DNS §e'" + GuiDedFinder.IpToScan.getText() + "' §chas no responsive Ded!";
            } 
            Thread.currentThread().interrupt();
          }
        });
  }
}
