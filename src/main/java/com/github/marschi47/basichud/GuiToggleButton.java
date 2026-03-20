package com.github.marschi47.basichud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.Gui;

public class GuiToggleButton extends GuiButton {
    public boolean state;

    public GuiToggleButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean initialState) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.state = initialState;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            // Draw background (Green if true, Red if false)
            int color = state ? 0xFF2ECC71 : 0xFFE74C3C;
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, color);

            // Draw text
            this.drawCenteredString(mc.fontRendererObj, this.displayString + ": " + (state ? "ON" : "OFF"),
                    this.xPosition + this.width / 2,
                    this.yPosition + (this.height - 8) / 2,
                    0xFFFFFF);
        }
    }

    public void toggle() {
        this.state = !this.state;
    }
}