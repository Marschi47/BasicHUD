package com.github.marschi47.basichud;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.Gui;
import java.io.IOException;

public class EditHudGui extends GuiScreen {
    private String draggingElement = null;
    private int dragOffsetActualX = 0;
    private int dragOffsetActualY = 0;

    // decoupled drag state
    private int tempX = 0;
    private int tempY = 0;

    private static final int GRID_SIZE = 4;

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 10, 150, 20, "Open more Settings"));
    }

    private int resolveX(int configX, int elementWidth, boolean rightAlign) {
        return rightAlign ? this.width - configX - elementWidth : configX;
    }

    private int resolveY(int configY, int elementHeight, boolean bottomAlign) {
        return bottomAlign ? this.height - configY - elementHeight : configY;
    }

    private int getFpsWidth() {
        return mc.fontRendererObj.getStringWidth("FPS: 144");
    }

    private int getFpsHeight() {
        return mc.fontRendererObj.FONT_HEIGHT;
    }

    private int getPingWidth() {
        return mc.fontRendererObj.getStringWidth("Ping: 20ms");
    }

    private int getPingHeight() {
        return mc.fontRendererObj.FONT_HEIGHT;
    }

    private int getCpsWidth() {
        return mc.fontRendererObj.getStringWidth("CPS: 12|12");
    }

    private int getCpsHeight() {
        return mc.fontRendererObj.FONT_HEIGHT;
    }

    private int getKeystrokesWidth() {
        return (18 * 3) + (2 * 2);
    }

    private int getKeystrokesHeight() {
        return (18 * 5) + (2 * 4);
    }

    private int getPotionsWidth() {
        return Math.max(mc.fontRendererObj.getStringWidth("Speed II: 1:30"),
                mc.fontRendererObj.getStringWidth("Jump Boost: 0:45"));
    }

    private int getPotionsHeight() {
        return (mc.fontRendererObj.FONT_HEIGHT + 2) * 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        // FPS
        if (MyModConfig.fpsEnabled) {
            String text = "FPS: 144";
            int w = getFpsWidth();
            int h = getFpsHeight();
            int actualX, actualY;
            if (draggingElement != null && draggingElement.equals("FPS")) {
                actualX = tempX;
                actualY = tempY;
            } else {
                actualX = resolveX(MyModConfig.fpsX, w, MyModConfig.fpsRightAlign);
                actualY = resolveY(MyModConfig.fpsY, h, MyModConfig.fpsBottomAlign);
            }
            drawBox(actualX, actualY, w, h, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text, actualX, actualY, 0xFFFFFF);
        }

        // Ping
        if (MyModConfig.pingEnabled) {
            String text = "Ping: 20ms";
            int w = getPingWidth();
            int h = getPingHeight();
            int actualX, actualY;
            if (draggingElement != null && draggingElement.equals("PING")) {
                actualX = tempX;
                actualY = tempY;
            } else {
                actualX = resolveX(MyModConfig.pingX, w, MyModConfig.pingRightAlign);
                actualY = resolveY(MyModConfig.pingY, h, MyModConfig.pingBottomAlign);
            }
            drawBox(actualX, actualY, w, h, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text, actualX, actualY, 0xFFFFFF);
        }

        // CPS
        if (MyModConfig.cpsEnabled) {
            String text = "CPS: 12|12";
            int w = getCpsWidth();
            int h = getCpsHeight();
            int actualX, actualY;
            if (draggingElement != null && draggingElement.equals("CPS")) {
                actualX = tempX;
                actualY = tempY;
            } else {
                actualX = resolveX(MyModConfig.cpsX, w, MyModConfig.cpsRightAlign);
                actualY = resolveY(MyModConfig.cpsY, h, MyModConfig.cpsBottomAlign);
            }
            drawBox(actualX, actualY, w, h, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text, actualX, actualY, 0xFFFFFF);
        }

        // Keystrokes
        if (MyModConfig.keystrokesEnabled) {
            int w = getKeystrokesWidth();
            int h = getKeystrokesHeight();
            int actualX, actualY;
            if (draggingElement != null && draggingElement.equals("KEYSTROKES")) {
                actualX = tempX;
                actualY = tempY;
            } else {
                actualX = resolveX(MyModConfig.keystrokesX, w, MyModConfig.keystrokesRightAlign);
                actualY = resolveY(MyModConfig.keystrokesY, h, MyModConfig.keystrokesBottomAlign);
            }
            drawBox(actualX, actualY, w, h, mouseX, mouseY);
            Gui.drawRect(actualX, actualY, actualX + w, actualY + h, 0x55000000);
            mc.fontRendererObj.drawStringWithShadow("Keys",
                    actualX + w / 2 - mc.fontRendererObj.getStringWidth("Keys") / 2, actualY + h / 2 - 4, 0xFFFFFF);
        }

        // Potions
        if (MyModConfig.potionHudEnabled) {
            String text1 = "Speed II: 1:30";
            String text2 = "Jump Boost: 0:45";
            int w = getPotionsWidth();
            int h = getPotionsHeight();
            int actualX, actualY;
            if (draggingElement != null && draggingElement.equals("POTIONS")) {
                actualX = tempX;
                actualY = tempY;
            } else {
                actualX = resolveX(MyModConfig.potionHudX, w, MyModConfig.potionHudRightAlign);
                actualY = resolveY(MyModConfig.potionHudY, h, MyModConfig.potionHudBottomAlign);
            }
            drawBox(actualX, actualY, w, h, mouseX, mouseY);
            mc.fontRendererObj.drawStringWithShadow(text1, actualX, actualY, 0xFFFFFF);
            mc.fontRendererObj.drawStringWithShadow(text2, actualX, actualY + mc.fontRendererObj.FONT_HEIGHT + 2,
                    0xFFFFFF);
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
                int w = getFpsWidth();
                int h = getFpsHeight();
                int actualX = resolveX(MyModConfig.fpsX, w, MyModConfig.fpsRightAlign);
                int actualY = resolveY(MyModConfig.fpsY, h, MyModConfig.fpsBottomAlign);
                if (isHovered(actualX, actualY, w, h, mouseX, mouseY)) {
                    setDragging("FPS", actualX, actualY, mouseX, mouseY);
                    return;
                }
            }
            // Ping
            if (MyModConfig.pingEnabled) {
                int w = getPingWidth();
                int h = getPingHeight();
                int actualX = resolveX(MyModConfig.pingX, w, MyModConfig.pingRightAlign);
                int actualY = resolveY(MyModConfig.pingY, h, MyModConfig.pingBottomAlign);
                if (isHovered(actualX, actualY, w, h, mouseX, mouseY)) {
                    setDragging("PING", actualX, actualY, mouseX, mouseY);
                    return;
                }
            }
            // CPS
            if (MyModConfig.cpsEnabled) {
                int w = getCpsWidth();
                int h = getCpsHeight();
                int actualX = resolveX(MyModConfig.cpsX, w, MyModConfig.cpsRightAlign);
                int actualY = resolveY(MyModConfig.cpsY, h, MyModConfig.cpsBottomAlign);
                if (isHovered(actualX, actualY, w, h, mouseX, mouseY)) {
                    setDragging("CPS", actualX, actualY, mouseX, mouseY);
                    return;
                }
            }
            // Keystrokes
            if (MyModConfig.keystrokesEnabled) {
                int w = getKeystrokesWidth();
                int h = getKeystrokesHeight();
                int actualX = resolveX(MyModConfig.keystrokesX, w, MyModConfig.keystrokesRightAlign);
                int actualY = resolveY(MyModConfig.keystrokesY, h, MyModConfig.keystrokesBottomAlign);
                if (isHovered(actualX, actualY, w, h, mouseX, mouseY)) {
                    setDragging("KEYSTROKES", actualX, actualY, mouseX, mouseY);
                    return;
                }
            }
            // Potions
            if (MyModConfig.potionHudEnabled) {
                int w = getPotionsWidth();
                int h = getPotionsHeight();
                int actualX = resolveX(MyModConfig.potionHudX, w, MyModConfig.potionHudRightAlign);
                int actualY = resolveY(MyModConfig.potionHudY, h, MyModConfig.potionHudBottomAlign);
                if (isHovered(actualX, actualY, w, h, mouseX, mouseY)) {
                    setDragging("POTIONS", actualX, actualY, mouseX, mouseY);
                    return;
                }
            }
        }
    }

    private void setDragging(String element, int actualX, int actualY, int mouseX, int mouseY) {
        draggingElement = element;
        dragOffsetActualX = mouseX - actualX;
        dragOffsetActualY = mouseY - actualY;
        tempX = actualX;
        tempY = actualY;
    }

    private boolean isHovered(int x, int y, int w, int h, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (draggingElement != null) {
            // Grid snapping: snap to GRID_SIZE pixel grid
            int rawX = mouseX - dragOffsetActualX;
            int rawY = mouseY - dragOffsetActualY;
            tempX = (rawX / GRID_SIZE) * GRID_SIZE;
            tempY = (rawY / GRID_SIZE) * GRID_SIZE;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (draggingElement != null) {
            int w = 0, h = 0;
            switch (draggingElement) {
                case "FPS":
                    w = getFpsWidth();
                    h = getFpsHeight();
                    break;
                case "PING":
                    w = getPingWidth();
                    h = getPingHeight();
                    break;
                case "CPS":
                    w = getCpsWidth();
                    h = getCpsHeight();
                    break;
                case "KEYSTROKES":
                    w = getKeystrokesWidth();
                    h = getKeystrokesHeight();
                    break;
                case "POTIONS":
                    w = getPotionsWidth();
                    h = getPotionsHeight();
                    break;
            }

            int centerX = tempX + w / 2;
            int centerY = tempY + h / 2;
            boolean anchorRight = centerX > this.width / 2;
            boolean anchorBottom = centerY > this.height / 2;

            int configX = anchorRight ? (this.width - tempX - w) : tempX;
            int configY = anchorBottom ? (this.height - tempY - h) : tempY;

            switch (draggingElement) {
                case "FPS":
                    MyModConfig.fpsRightAlign = anchorRight;
                    MyModConfig.fpsBottomAlign = anchorBottom;
                    MyModConfig.fpsX = configX;
                    MyModConfig.fpsY = configY;
                    break;
                case "PING":
                    MyModConfig.pingRightAlign = anchorRight;
                    MyModConfig.pingBottomAlign = anchorBottom;
                    MyModConfig.pingX = configX;
                    MyModConfig.pingY = configY;
                    break;
                case "CPS":
                    MyModConfig.cpsRightAlign = anchorRight;
                    MyModConfig.cpsBottomAlign = anchorBottom;
                    MyModConfig.cpsX = configX;
                    MyModConfig.cpsY = configY;
                    break;
                case "KEYSTROKES":
                    MyModConfig.keystrokesRightAlign = anchorRight;
                    MyModConfig.keystrokesBottomAlign = anchorBottom;
                    MyModConfig.keystrokesX = configX;
                    MyModConfig.keystrokesY = configY;
                    break;
                case "POTIONS":
                    MyModConfig.potionHudRightAlign = anchorRight;
                    MyModConfig.potionHudBottomAlign = anchorBottom;
                    MyModConfig.potionHudX = configX;
                    MyModConfig.potionHudY = configY;
                    break;
            }

            draggingElement = null;

            // write to file
            MyModConfig.config.get("HUD_FPS", "fpsX", 10).set(MyModConfig.fpsX);
            MyModConfig.config.get("HUD_FPS", "fpsY", 10).set(MyModConfig.fpsY);
            MyModConfig.config.get("HUD_FPS", "fpsRightAlign", false).set(MyModConfig.fpsRightAlign);
            MyModConfig.config.get("HUD_FPS", "fpsBottomAlign", false).set(MyModConfig.fpsBottomAlign);

            MyModConfig.config.get("HUD_PING", "pingX", 10).set(MyModConfig.pingX);
            MyModConfig.config.get("HUD_PING", "pingY", 22).set(MyModConfig.pingY);
            MyModConfig.config.get("HUD_PING", "pingRightAlign", false).set(MyModConfig.pingRightAlign);
            MyModConfig.config.get("HUD_PING", "pingBottomAlign", false).set(MyModConfig.pingBottomAlign);

            MyModConfig.config.get("HUD_CPS", "cpsX", 10).set(MyModConfig.cpsX);
            MyModConfig.config.get("HUD_CPS", "cpsY", 34).set(MyModConfig.cpsY);
            MyModConfig.config.get("HUD_CPS", "cpsRightAlign", false).set(MyModConfig.cpsRightAlign);
            MyModConfig.config.get("HUD_CPS", "cpsBottomAlign", false).set(MyModConfig.cpsBottomAlign);

            MyModConfig.config.get("HUD_Keystrokes", "keystrokesX", 5).set(MyModConfig.keystrokesX);
            MyModConfig.config.get("HUD_Keystrokes", "keystrokesY", 10).set(MyModConfig.keystrokesY);
            MyModConfig.config.get("HUD_Keystrokes", "keystrokesRightAlign", true)
                    .set(MyModConfig.keystrokesRightAlign);
            MyModConfig.config.get("HUD_Keystrokes", "keystrokesBottomAlign", false)
                    .set(MyModConfig.keystrokesBottomAlign);

            MyModConfig.config.get("HUD_POTIONS", "potionHudX", 10).set(MyModConfig.potionHudX);
            MyModConfig.config.get("HUD_POTIONS", "potionHudY", 100).set(MyModConfig.potionHudY);
            MyModConfig.config.get("HUD_POTIONS", "potionHudRightAlign", false).set(MyModConfig.potionHudRightAlign);
            MyModConfig.config.get("HUD_POTIONS", "potionHudBottomAlign", false).set(MyModConfig.potionHudBottomAlign);

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