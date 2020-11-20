package it.nobusware.client.render.tabgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

import QuarantineAPI.EventAPI;
import QuarantineAPI.config.annotation.Handler;
import it.nobusware.client.events.KeyPressedEvent;
import it.nobusware.client.manager.Module;
import it.nobusware.client.manager.Module.Category;
import it.nobusware.client.mods.Hud;
import it.nobusware.client.utils.R2DUtils;
import it.nobusware.client.utils.fontmanager.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;

public final class TabGui {
	
  private final Minecraft mc = Minecraft.getMinecraft();
  
  private String colourHighlight = "§f";
  
  private String colourTitle = "§f";
  
  private int guiHeight = 0;
  
  private boolean mainMenu = true;
  
  private int selectedItem = 0;
  
  private int selectedTab = 0;
  
  private int tabHeight = 12;
  
  private final ArrayList<TabguiItem> tabs = new ArrayList<>();
  
  private int transition = 0;
  
  private boolean visible = true;
  
  public TabGui() {
    byte b;
    int i;
    Category[] arrayOfCategory;
    for (i = (arrayOfCategory = Category.values()).length, b = 0; b < i; ) {
      Category category = arrayOfCategory[b];
        String otherLetters = category.name().toLowerCase().substring(1);
        String firstLetter = category.name().substring(0, 1).toUpperCase();
        String name = String.valueOf(firstLetter) + otherLetters;
        TabguiItem tab = new TabguiItem(this, name);
        for (Module m : mc.getNobita().getModManager().getHackDelClient()) {
          if (m.getCategoria().equals(category))
            tab.getModules().add(new TabguiItem.GuiItem(m)); 
        } 
        this.tabs.add(tab); 
      b++;
    } 
    this.guiHeight = this.tabs.size() * this.tabHeight;
    EventAPI.put(this);	
  }
  
  @Handler
  public void onkeyPress(KeyPressedEvent e) {
	   if(!mc.getNobita().getModManager().Prendi(Hud.class).isAbilitato()) {
		   return;
	   }
    KeyPressedEvent input = e;
    switch (input.getEventKey()) {
      case 200:
        if (this.visible) {
          if (this.mainMenu) {
        	  this.selectedTab--;
            if (this.selectedTab < 0)
            	this.selectedTab = this.tabs.size() - 1; 
            this.transition = 3;
            break;
          } 
          this.selectedItem--;
          if (this.selectedItem < 0)
        	  this.selectedItem = ((TabguiItem)this.tabs.get(this.selectedTab)).getModules().size() - 1; 
          if (((TabguiItem)this.tabs.get(this.selectedTab)).getModules().size() > 1)
        	  this.transition = 3; 
        } 
        break;
      case 208:
        if (this.visible) {
          if (this.mainMenu) {
        	  this.selectedTab++;
            if (this.selectedTab > this.tabs.size() - 1)
            	this.selectedTab = 0; 
            this.transition = -6;
            break;
          } 
          this.selectedItem++;
          if (this.selectedItem > ((TabguiItem)this.tabs.get(this.selectedTab)).getModules().size() - 1)
        	  this.selectedItem = 0; 
          if (((TabguiItem)this.tabs.get(this.selectedTab)).getModules().size() > 1)
        	  this.transition = -6; 
        } 
        break;
      case 203:
        if (this.mainMenu) {
        	this.mainMenu = false;
          break;
        } 
        this.mainMenu = true;
        break;
      case 205:
        if (this.mainMenu) {
        	this.mainMenu = false;
        	this.selectedItem = 0;
          break;
        } 
        ((TabguiItem.GuiItem)((TabguiItem)this.tabs.get(this.selectedTab)).getModules().get(this.selectedItem)).getModule().toggle();;
        break;
      case 28:
        if (!this.mainMenu && this.visible)
          ((TabguiItem.GuiItem)((TabguiItem)this.tabs.get(this.selectedTab)).getModules().get(this.selectedItem)).getModule().toggle(); 
        break;
    } 
  }
  
  public static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
  
  public void drawGui(int x, int y) {
    if (!this.visible)
      return; 
    int guiWidth = 60;
    R2DUtils.drawBorderedRect(x, y, (x + 60 - 2), (y + this.guiHeight), -12234410, -2147483648);
    for (int i = 0; i < this.tabs.size(); i++) {
      int transitionTop = !this.mainMenu ? 0 : (this.transition + ((Objects.equals(Integer.valueOf(this.selectedTab), Integer.valueOf(0)) && this.transition < 0) ? -this.transition : 0));
      int transitionBottom = !this.mainMenu ? 0 : (this.transition + ((Objects.equals(Integer.valueOf(this.selectedTab), Integer.valueOf(this.tabs.size() - 1)) && this.transition > 0) ? -this.transition : 0));
      if (Objects.equals(Integer.valueOf(this.selectedTab), Integer.valueOf(i)))
        R2DUtils.drawBorderedRect(x, (i * 12 + y + transitionTop), (x + 60 - 2), (i + y + 12 + i * 11 + transitionBottom), 0xff67ECC3, -2147483648); 
    } 
    int yOff = y + 2;
    for (int j = 0; j < this.tabs.size(); j++) {
      TabguiItem tab = this.tabs.get(j);
      UnicodeFontRenderer font = mc.getNobita().getFontManager().arialBold16;
      font.drawString(String.valueOf(this.colourTitle).replace("§f", "") + tab.getTabName(), (x + 2), yOff, -1);
      if (Objects.equals(Integer.valueOf(this.selectedTab), Integer.valueOf(j)) && !this.mainMenu)
        tab.drawTabMenu(this.mc, x + 60, yOff - 2); 
      yOff += this.tabHeight;
    } 
    if (this.transition > 0) {
      this.transition--;
    } else if (this.transition < 0) {
      this.transition++;
    } 
  }
  
 
  
  public String getColourHightlight() {
    return this.colourHighlight;
  }
  
  public void setColourHighlight(String colourHighlight) {
    this.colourHighlight = colourHighlight;
  }
  
  
  public String getColourTitle() {
    return this.colourTitle;
  }
  
  public void setColourTitle(String colourTitle) {
    this.colourTitle = colourTitle;
  }
  
  public int getSelectedItem() {
    return this.selectedItem;
  }
  
  public int getTabHeight() {
    return this.tabHeight;
  }
  
  public int getTransition() {
    return this.transition;
  }
}
