package it.nobusware.client.render.clickgui.frame.frames;

import org.apache.commons.lang3.StringUtils;

import it.nobusware.client.manager.Module;
import it.nobusware.client.render.clickgui.frame.Frame;
import it.nobusware.client.render.clickgui.frame.component.Component;
import it.nobusware.client.render.clickgui.frame.component.impl.ModuleComponent;
import it.nobusware.client.utils.ModuleUtils;
import net.minecraft.client.gui.ScaledResolution;

public class CategoryFrame extends Frame {
  private Module.Category category;
  
  public CategoryFrame(Module.Category category, float posX, float posY, float width, float height) {
    super(StringUtils.capitalize(category.name().toLowerCase()), posX, posY, width, height);
    this.category = category;
  }
  
  public void init() {
    float offsetY = getHeight();
    for (Module module : ModuleUtils.getModsByCat(this.category)) {
      getComponents().add(new ModuleComponent(module, getPosX(), getPosY(), 2.0F, offsetY, getWidth() - 4.0F, 20.0F));
      offsetY += 20.0F;
    } 
    getComponents().forEach(Component::init);
  }
  
  public void updatePosition() {
    super.updatePosition();
  }
  
  public void drawScreen(int mouseX, int mouseY, ScaledResolution scaledResolution) {
    super.drawScreen(mouseX, mouseY, scaledResolution);
    float offset = getHeight();
    if (isExtended()) {
      for (Component component : getComponents()) {
        if (component instanceof ModuleComponent) {
          ModuleComponent moduleComponent = (ModuleComponent)component;
          moduleComponent.setOffsetY(offset);
          moduleComponent.getComponents().forEach(component1 -> component1.updatePosition(component.getFinishedX(), component.getFinishedY()));
          float offset1 = offset + component.getDefaultHeight();
          for (Component component1 : moduleComponent.getComponents()) {
            component1.setOffsetY(offset1);
            offset1 += component1.getHeight();
          } 
          offset += component.getHeight();
        } 
      } 
      getComponents().forEach(component -> component.updatePosition(getPosX(), getPosY()));
    } 
  }
  
  public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    super.mouseReleased(mouseX, mouseY, mouseButton);
  }
  
  public void keyTyped(char typedChar, int keyCode) {
    super.keyTyped(typedChar, keyCode);
  }
  
  public Module.Category getCategory() {
    return this.category;
  }
}
