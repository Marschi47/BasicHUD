package com.github.marschi47.basichud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.Gui;

public class GuiToggleButton extends GuiButton {
    public boolean state;
    private String baseText;

    public GuiToggleButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean initialState) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.baseText = buttonText;
        this.state = initialState;
        updateText();
    }

    private void updateText() {
        this.displayString = this.baseText + ": " + (state ? "ON" : "OFF");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition &&
                    mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            int color = state ? 0xFF2ECC71 : 0xFFE74C3C;
            int hoverColor = state ? 0xFF27AE60 : 0xFFC0392B;
            int bgColor = this.hovered ? hoverColor : color;

            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0xFF000000);
            Gui.drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, bgColor);

            Gui.drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + 2, 0x40FFFFFF);
            Gui.drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + 2, this.yPosition + this.height - 1, 0x40FFFFFF);
            Gui.drawRect(this.xPosition + 1, this.yPosition + this.height - 2, this.xPosition + this.width - 1, this.yPosition + this.height - 1, 0x40000000);
            Gui.drawRect(this.xPosition + this.width - 2, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, 0x40000000);

            int textColor = this.hovered ? 0xFFFFFFA0 : 0xFFFFFFFF;
            this.drawCenteredString(mc.fontRendererObj, this.displayString,
                    this.xPosition + this.width / 2,
                    this.yPosition + (this.height - 8) / 2,
                    textColor);
        }
    }

    public void toggle() {
        this.state = !this.state;
        updateText();
    }
}