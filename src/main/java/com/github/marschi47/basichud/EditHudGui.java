package com.github.marschi47.basichud;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.Gui;
import java.io.IOException;

public class EditHudGui extends GuiScreen {
    private String draggingElement = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    @Override
    public void initGui() {
        super.initGui();
        // button for detailed settings menu
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50, this.height / 2 - 10, 100, 20, "Open Settings"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground(); // Darkens the game background slightly

        // Calculate Bounding Boxes & Draw Dummy Elements

        if (MyModConfig.fpsEnabled) {
            String text = "FPS: 144";
            int width = mc.fontRendererObj.getStringWidth(text);
            int height = mc.fontRendererObj.FONT_HEIGHT;
            drawBox(MyModConfig.fpsX, MyModConfig.fpsY, width, height, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text, MyModConfig.fpsX, MyModConfig.fpsY, 0xFFFFFF);
        }

        if (MyModConfig.pingEnabled) {
            String text = "Ping: 20ms";
            int width = mc.fontRendererObj.getStringWidth(text);
            int height = mc.fontRendererObj.FONT_HEIGHT;
            drawBox(MyModConfig.pingX, MyModConfig.pingY, width, height, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text, MyModConfig.pingX, MyModConfig.pingY, 0xFFFFFF);
        }

        // Add CPS, Keystrokes, etc.

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawBox(int x, int y, int w, int h, int mouseX, int mouseY) {
        // box around the element if hovered
        if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
            Gui.drawRect(x - 2, y - 2, x + w + 2, y + h + 2, 0x55FFFFFF);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            // Check FPS Click
            if (MyModConfig.fpsEnabled && isHovered(MyModConfig.fpsX, MyModConfig.fpsY, mc.fontRendererObj.getStringWidth("FPS: 144"), mc.fontRendererObj.FONT_HEIGHT, mouseX, mouseY)) {
                draggingElement = "FPS";
                dragOffsetX = mouseX - MyModConfig.fpsX;
                dragOffsetY = mouseY - MyModConfig.fpsY;
                return;
            }
            // Check Ping Click
            if (MyModConfig.pingEnabled && isHovered(MyModConfig.pingX, MyModConfig.pingY, mc.fontRendererObj.getStringWidth("Ping: 20ms"), mc.fontRendererObj.FONT_HEIGHT, mouseX, mouseY)) {
                draggingElement = "PING";
                dragOffsetX = mouseX - MyModConfig.pingX;
                dragOffsetY = mouseY - MyModConfig.pingY;
                return;
            }
        }
    }

    private boolean isHovered(int x, int y, int w, int h, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (draggingElement != null) {
            if (draggingElement.equals("FPS")) {
                MyModConfig.fpsX = mouseX - dragOffsetX;
                MyModConfig.fpsY = mouseY - dragOffsetY;
            } else if (draggingElement.equals("PING")) {
                MyModConfig.pingX = mouseX - dragOffsetX;
                MyModConfig.pingY = mouseY - dragOffsetY;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (draggingElement != null) {
            draggingElement = null;
            MyModConfig.config.save(); // Save coordinates when mouse is let go
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(new SettingsGui(this)); // Open the settings menu
        }
    }
}