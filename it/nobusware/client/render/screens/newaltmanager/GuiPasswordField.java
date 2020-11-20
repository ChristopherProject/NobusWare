package it.nobusware.client.render.screens.newaltmanager;

import joptsimple.internal.*;
import net.minecraft.client.gui.*;

public class GuiPasswordField extends GuiTextField
{
    public GuiPasswordField(final int componentId, final FontRenderer fontrendererObj, final int x, final int y, final int par5Width, final int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
    }
    
    @Override
    public void drawTextBox() {
        final String password = this.getText();
        this.replaceText(Strings.repeat('*', this.getText().length()));
        super.drawTextBox();
        this.replaceText(password);
    }
    
    @Override
    public boolean textboxKeyTyped(final char typedChar, final int keyCode) {
        return !GuiScreen.func_175280_f(keyCode) && !GuiScreen.func_175277_d(keyCode) && super.textboxKeyTyped(typedChar, keyCode);
    }
    
    @Override
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final String password = this.getText();
        this.replaceText(Strings.repeat('*', this.getText().length()));
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.replaceText(password);
		return false;
    }
    
    private void replaceText(final String newText) {
        final int cursorPosition = this.getCursorPosition();
        final int selectionEnd = this.getSelectionEnd();
        this.setText(newText);
        this.setCursorPosition(cursorPosition);
        this.setSelectionPos(selectionEnd);
    }
}
