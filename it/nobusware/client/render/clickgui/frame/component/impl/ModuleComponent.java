package it.nobusware.client.render.clickgui.frame.component.impl;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import it.nobusware.client.manager.Module;
import it.nobusware.client.render.clickgui.frame.component.Component;
import it.nobusware.client.render.clickgui.utils.RenderUtilCL;
import it.nobusware.client.utils.ChatUtils;
import it.nobusware.client.utils.MouseUtil;
import it.nobusware.client.utils.value.Value;
import it.nobusware.client.utils.value.impl.BooleanValue;
import it.nobusware.client.utils.value.impl.EnumValue;
import it.nobusware.client.utils.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ModuleComponent extends Component {
  private Module module;
  
  private boolean binding;
  
  private boolean extended;
  
  private ArrayList<Component> components = new ArrayList<>();
  
  public ModuleComponent(Module module, float posX, float posY, float offsetX, float offsetY, float width, float height) {
    super(module.getNome_mod(), posX, posY, offsetX, offsetY, width, height);
    this.module = module;
  }
  
  public void init() {
    super.init();
    if (this.module.getValues().isEmpty()) {
    	 return; 
    }
     
    float offY = getDefaultHeight() + getOffsetY();
    for (Value value : this.module.getValues()) {
      if (value instanceof BooleanValue) {
        this.components.add(new BooleanComponent((BooleanValue)value, getFinishedX(), getFinishedY(), 4.0F, offY, getWidth() - 4.0F, 12.0F));
        offY += 12.0F;
        continue;
      } 
      if (value instanceof NumberValue) {
        this.components.add(new NumberComponent((NumberValue)value, getFinishedX(), getFinishedY(), 4.0F, offY, getWidth() - 4.0F, 12.0F));
        offY += 12.0F;
        continue;
      } 
      if (value instanceof EnumValue) {
        this.components.add(new EnumComponent((EnumValue)value, getFinishedX(), getFinishedY(), 4.0F, offY, getWidth() - 4.0F, 12.0F));
        offY += 12.0F;
      } 
    } 
    this.components.forEach(Component::init);
  }
  
  public void updatePosition(float posX, float posY) {
    super.updatePosition(posX, posY);
    this.components.forEach(component -> component.updatePosition(posX, posY));
  }
  
  public void drawScreen(int mouseX, int mouseY, ScaledResolution scaledResolution) {
    super.drawScreen(mouseX, mouseY, scaledResolution);
    RenderUtilCL.drawRect(getFinishedX(), getFinishedY(), getWidth(), getDefaultHeight(), (new Color(0, 0, 0, 120)).getRGB());
    (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(isBinding() ? "Premi Un Tasto..." : getLabel(), getFinishedX() + getWidth() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.getStringWidth(isBinding() ? "Premi Un Tasto..." : getLabel()) / 2), getFinishedY() + getDefaultHeight() / 2.0F - ((Minecraft.getMinecraft()).fontRendererObj.FONT_HEIGHT / 2), this.module.isAbilitato() ? -1 : -8355712);
    float valueHeight = getDefaultHeight();
    for (Component component : this.components)
      valueHeight += component.getHeight(); 
    if (this.extended) {
      if (getHeight() != valueHeight)
        setHeight(valueHeight); 
    } else if (getHeight() != getDefaultHeight()) {
      setHeight(getDefaultHeight());
    } 
    if (this.extended)
      this.components.forEach(component -> component.drawScreen(mouseX, mouseY, scaledResolution)); 
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    if (MouseUtil.mouseWithinBounds(mouseX, mouseY, getFinishedX(), getFinishedY(), getWidth(), getDefaultHeight()))
      switch (mouseButton) {
        case 0:
          this.module.toggle();
          break;
        case 1:
          setExtended(!isExtended());
          break;
        case 2:
          setBinding(!isBinding());
          break;
      }  
    if (this.extended)
      this.components.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton)); 
  }
  
  public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    super.mouseReleased(mouseX, mouseY, mouseButton);
    if (this.extended)
      this.components.forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton)); 
  }
  
  public void keyTyped(char typedChar, int keyCode) {
    super.keyTyped(typedChar, keyCode);
    if (isBinding()) {
      this.module.setTasto((keyCode == 14) ? 0 : keyCode);
      ChatUtils.print(this.module.getNome_mod() + " Configurato su " + Keyboard.getKeyName(this.module.getTasto()) + ".");
      setBinding(false);
    } 
    if (this.extended)
      this.components.forEach(component -> component.keyTyped(typedChar, keyCode)); 
  }
  
  public Module getModule() {
    return this.module;
  }
  
  public boolean isBinding() {
    return this.binding;
  }
  
  public void setBinding(boolean binding) {
    this.binding = binding;
  }
  
  public boolean isExtended() {
    return this.extended;
  }
  
  public void setExtended(boolean extended) {
    this.extended = extended;
  }
  
  public ArrayList<Component> getComponents() {
    return this.components;
  }
}
