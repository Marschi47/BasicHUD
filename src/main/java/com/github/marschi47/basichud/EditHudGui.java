package com.github.marschi47.basichud;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.Gui;
import java.io.IOException;

public class EditHudGui extends GuiScreen {
    private String draggingElement = null;
    private int dragOffsetActualX = 0;
    private int dragOffsetActualY = 0;

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 10, 150, 20, "Open more Settings"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        // FPS
        if (MyModConfig.fpsEnabled) {
            String text = "FPS: 144";
            int w = mc.fontRendererObj.getStringWidth(text);
            int h = mc.fontRendererObj.FONT_HEIGHT;
            int actualX = MyModConfig.fpsRightAlign ? this.width - MyModConfig.fpsX - w : MyModConfig.fpsX;
            drawBox(actualX, MyModConfig.fpsY, w, h, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text, actualX, MyModConfig.fpsY, 0xFFFFFF);
        }

        // Ping
        if (MyModConfig.pingEnabled) {
            String text = "Ping: 20ms";
            int w = mc.fontRendererObj.getStringWidth(text);
            int h = mc.fontRendererObj.FONT_HEIGHT;
            int actualX = MyModConfig.pingRightAlign ? this.width - MyModConfig.pingX - w : MyModConfig.pingX;
            drawBox(actualX, MyModConfig.pingY, w, h, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text, actualX, MyModConfig.pingY, 0xFFFFFF);
        }

        // CPS
        if (MyModConfig.cpsEnabled) {
            String text = "CPS: 12|12";
            int w = mc.fontRendererObj.getStringWidth(text);
            int h = mc.fontRendererObj.FONT_HEIGHT;
            int actualX = MyModConfig.cpsRightAlign ? this.width - MyModConfig.cpsX - w : MyModConfig.cpsX;
            drawBox(actualX, MyModConfig.cpsY, w, h, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text, actualX, MyModConfig.cpsY, 0xFFFFFF);
        }

        // Keystrokes
        if (MyModConfig.keystrokesEnabled) {
            int gap = 2;
            int boxSize = 18;
            int totalWidth = (boxSize * 3) + (gap * 2);
            int totalHeight = (18 * 5) + (gap * 4);
            int actualX = MyModConfig.keystrokesRightAlign ? this.width - MyModConfig.keystrokesX - totalWidth : MyModConfig.keystrokesX;

            drawBox(actualX, MyModConfig.keystrokesY, totalWidth, totalHeight, mouseX, mouseY);
            Gui.drawRect(actualX, MyModConfig.keystrokesY, actualX + totalWidth, MyModConfig.keystrokesY + totalHeight, 0x55000000);
            mc.fontRendererObj.drawStringWithShadow("Keys", actualX + totalWidth / 2 - mc.fontRendererObj.getStringWidth("Keys") / 2, MyModConfig.keystrokesY + totalHeight / 2 - 4, 0xFFFFFF);
        }

        // Potions
        if (MyModConfig.potionHudEnabled) {
            String text1 = "Speed II: 1:30";
            String text2 = "Jump Boost: 0:45";
            int w = Math.max(mc.fontRendererObj.getStringWidth(text1), mc.fontRendererObj.getStringWidth(text2));
            int h = (mc.fontRendererObj.FONT_HEIGHT + 2) * 2;
            int actualX = MyModConfig.potionHudX;
            int actualY = MyModConfig.potionHudVerticalCenter ? (this.height / 2) - (h / 2) : MyModConfig.potionHudY;

            drawBox(actualX, actualY, w, h, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text1, actualX, actualY, 0xFFFFFF);
            mc.fontRendererObj.drawStringWithShadow(text2, actualX, actualY + mc.fontRendererObj.FONT_HEIGHT + 2, 0xFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawBox(int x, int y, int w, int h, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
            Gui.drawRect(x - 2, y - 2, x + w + 2, y + h + 2, 0x55FFFFFF);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            // FPS
            if (MyModConfig.fpsEnabled) {
                int w = mc.fontRendererObj.getStringWidth("FPS: 144");
                int actualX = MyModConfig.fpsRightAlign ? this.width - MyModConfig.fpsX - w : MyModConfig.fpsX;
                if (isHovered(actualX, MyModConfig.fpsY, w, mc.fontRendererObj.FONT_HEIGHT, mouseX, mouseY)) {
                    setDragging("FPS", actualX, MyModConfig.fpsY, mouseX, mouseY); return;
                }
            }
            // Ping
            if (MyModConfig.pingEnabled) {
                int w = mc.fontRendererObj.getStringWidth("Ping: 20ms");
                int actualX = MyModConfig.pingRightAlign ? this.width - MyModConfig.pingX - w : MyModConfig.pingX;
                if (isHovered(actualX, MyModConfig.pingY, w, mc.fontRendererObj.FONT_HEIGHT, mouseX, mouseY)) {
                    setDragging("PING", actualX, MyModConfig.pingY, mouseX, mouseY); return;
                }
            }
            // CPS
            if (MyModConfig.cpsEnabled) {
                int w = mc.fontRendererObj.getStringWidth("CPS: 12|12");
                int actualX = MyModConfig.cpsRightAlign ? this.width - MyModConfig.cpsX - w : MyModConfig.cpsX;
                if (isHovered(actualX, MyModConfig.cpsY, w, mc.fontRendererObj.FONT_HEIGHT, mouseX, mouseY)) {
                    setDragging("CPS", actualX, MyModConfig.cpsY, mouseX, mouseY); return;
                }
            }
            // Keystrokes
            if (MyModConfig.keystrokesEnabled) {
                int w = (18 * 3) + (2 * 2);
                int h = (18 * 5) + (2 * 4);
                int actualX = MyModConfig.keystrokesRightAlign ? this.width - MyModConfig.keystrokesX - w : MyModConfig.keystrokesX;
                if (isHovered(actualX, MyModConfig.keystrokesY, w, h, mouseX, mouseY)) {
                    setDragging("KEYSTROKES", actualX, MyModConfig.keystrokesY, mouseX, mouseY); return;
                }
            }
            // Potions
            if (MyModConfig.potionHudEnabled) {
                int w = mc.fontRendererObj.getStringWidth("Jump Boost: 0:45");
                int h = (mc.fontRendererObj.FONT_HEIGHT + 2) * 2;
                int actualY = MyModConfig.potionHudVerticalCenter ? (this.height / 2) - (h / 2) : MyModConfig.potionHudY;
                if (isHovered(MyModConfig.potionHudX, actualY, w, h, mouseX, mouseY)) {
                    setDragging("POTIONS", MyModConfig.potionHudX, actualY, mouseX, mouseY); return;
                }
            }
        }
    }

    private void setDragging(String element, int actualX, int actualY, int mouseX, int mouseY) {
        draggingElement = element;
        dragOffsetActualX = mouseX - actualX;
        dragOffsetActualY = mouseY - actualY;
    }

    private boolean isHovered(int x, int y, int w, int h, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (draggingElement != null) {
            int newActualX = mouseX - dragOffsetActualX;
            int newActualY = mouseY - dragOffsetActualY;

            if (draggingElement.equals("FPS")) {
                int w = mc.fontRendererObj.getStringWidth("FPS: 144");
                MyModConfig.fpsX = MyModConfig.fpsRightAlign ? this.width - newActualX - w : newActualX;
                MyModConfig.fpsY = newActualY;
            } else if (draggingElement.equals("PING")) {
                int w = mc.fontRendererObj.getStringWidth("Ping: 20ms");
                MyModConfig.pingX = MyModConfig.pingRightAlign ? this.width - newActualX - w : newActualX;
                MyModConfig.pingY = newActualY;
            } else if (draggingElement.equals("CPS")) {
                int w = mc.fontRendererObj.getStringWidth("CPS: 12|12");
                MyModConfig.cpsX = MyModConfig.cpsRightAlign ? this.width - newActualX - w : newActualX;
                MyModConfig.cpsY = newActualY;
            } else if (draggingElement.equals("KEYSTROKES")) {
                int w = (18 * 3) + (2 * 2);
                MyModConfig.keystrokesX = MyModConfig.keystrokesRightAlign ? this.width - newActualX - w : newActualX;
                MyModConfig.keystrokesY = newActualY;
            } else if (draggingElement.equals("POTIONS")) {
                MyModConfig.potionHudX = newActualX;
                if (!MyModConfig.potionHudVerticalCenter) {
                    MyModConfig.potionHudY = newActualY;
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (draggingElement != null) {
            draggingElement = null;

            // write to file
            MyModConfig.config.get("HUD_FPS", "fpsX", 10).set(MyModConfig.fpsX);
            MyModConfig.config.get("HUD_FPS", "fpsY", 10).set(MyModConfig.fpsY);

            MyModConfig.config.get("HUD_PING", "pingX", 10).set(MyModConfig.pingX);
            MyModConfig.config.get("HUD_PING", "pingY", 22).set(MyModConfig.pingY);

            MyModConfig.config.get("HUD_CPS", "cpsX", 10).set(MyModConfig.cpsX);
            MyModConfig.config.get("HUD_CPS", "cpsY", 34).set(MyModConfig.cpsY);

            MyModConfig.config.get("HUD_Keystrokes", "keystrokesX", 5).set(MyModConfig.keystrokesX);
            MyModConfig.config.get("HUD_Keystrokes", "keystrokesY", 10).set(MyModConfig.keystrokesY);

            MyModConfig.config.get("HUD_POTIONS", "potionHudX", 10).set(MyModConfig.potionHudX);
            MyModConfig.config.get("HUD_POTIONS", "potionHudY", 100).set(MyModConfig.potionHudY);

            MyModConfig.config.save();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(new SettingsGui(this));
        }
    }
}