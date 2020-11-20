package it.nobusware.client.render.tabgui;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

import it.nobusware.client.manager.Module;
import it.nobusware.client.utils.Colors;
import it.nobusware.client.utils.R2DUtils;
import it.nobusware.client.utils.fontmanager.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;


public final class TabguiItem {
	
  private final TabGui gui;
  
  private final ArrayList<GuiItem> modules = new ArrayList<>();
  
  private int menuHeight = 0;
  
  private int menuWidth = 0;
  
  private String tabName;
  
  public TabguiItem(TabGui gui, String tabName) {
    this.gui = gui;
    this.tabName = tabName;
  }
  
  public void drawTabMenu(Minecraft mc, int x, int y) {
    countMenuSize(mc);
    int boxY = y;
    UnicodeFontRenderer font = mc.getNobita().getFontManager().arialBold16;
    //this
    R2DUtils.drawBorderedRect(x, y, (x + this.menuWidth + 2), (y + this.menuHeight), -12234410, -2147483648);
    for (int i = 0; i < this.modules.size(); i++) {
      if (this.gui.getSelectedItem() == i) {
        int transitionTop = this.gui.getTransition() + ((this.gui.getSelectedItem() == 0 && this.gui.getTransition() < 0) ? -this.gui.getTransition() : 0);
        int transitionBottom = this.gui.getTransition() + ((this.gui.getSelectedItem() == this.modules.size() - 1 && this.gui.getTransition() > 0) ? -this.gui.getTransition() : 0);
        R2DUtils.drawBorderedRect(x, (boxY + transitionTop), (x + this.menuWidth + 2), (boxY + 12 + transitionBottom), 0xff67ECC3, -2147483648);
      } 
      Collator collator = Collator.getInstance(Locale.US);
      this.modules.sort((mod1, mod2) -> collator.compare(mod1.getModule().getNome_mod(), mod2.getModule().getNome_mod()));
      font.drawString(((GuiItem)this.modules.get(i)).getModule().getNome_mod(), (x + 2), (y + this.gui.getTabHeight() * i + 2), ((GuiItem)this.modules.get(i)).getModule().isAbilitato() ?  Colors.WHITE.c : Colors.GREY.c);
      boxY += 12;
    } 
  }
  
  private void countMenuSize(Minecraft mc) {
    int maxWidth = 0;
    for (GuiItem module : this.modules) {
      if (mc.fontRendererObj.getStringWidth(module.getModule().getNome_mod()) > maxWidth)
        maxWidth = mc.fontRendererObj.getStringWidth(module.getModule().getNome_mod()) + 4; 
    } 
    this.menuWidth = maxWidth;
    this.menuHeight = this.modules.size() * this.gui.getTabHeight();
  }
  
  public String getTabName() {
    return this.tabName;
  }
  
  public ArrayList<GuiItem> getModules() {
    return this.modules;
  }
  
  public static class GuiItem {
    private final Module mod;
    
    public GuiItem(Module mod) {
      this.mod = mod;
    }
    
    public Module getModule() {
      return this.mod;
    }
  }
}
