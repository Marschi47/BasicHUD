package com.github.marschi47.basichud;

import com.github.marschi47.basichud.elements.HudElement;
import com.github.marschi47.basichud.elements.HudElementRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.Gui;

import java.io.IOException;

public class EditHudGui extends GuiScreen {
    private HudElement draggingElement = null;
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

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        for (HudElement element : HudElementRegistry.getElements()) {
            if (element.enabled) {
                int w = element.getWidth();
                int h = element.getHeight();
                int actualX, actualY;
                if (draggingElement == element) {
                    actualX = tempX;
                    actualY = tempY;
                } else {
                    actualX = element.resolveX(this.width);
                    actualY = element.resolveY(this.height);
                }
                drawBox(actualX, actualY, w, h, mouseX, mouseY);
                element.renderPreview(mc.fontRendererObj, actualX, actualY);
            }
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
            for (HudElement element : HudElementRegistry.getElements()) {
                if (element.enabled) {
                    int w = element.getWidth();
                    int h = element.getHeight();
                    int actualX = element.resolveX(this.width);
                    int actualY = element.resolveY(this.height);
                    if (isHovered(actualX, actualY, w, h, mouseX, mouseY)) {
                        draggingElement = element;
                        dragOffsetActualX = mouseX - actualX;
                        dragOffsetActualY = mouseY - actualY;
                        tempX = actualX;
                        tempY = actualY;
                        return;
                    }
                }
            }
        }
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
            int w = draggingElement.getWidth();
            int h = draggingElement.getHeight();

            int centerX = tempX + w / 2;
            int centerY = tempY + h / 2;
            boolean anchorRight = centerX > this.width / 2;
            boolean anchorBottom = centerY > this.height / 2;

            int configX = anchorRight ? (this.width - tempX - w) : tempX;
            int configY = anchorBottom ? (this.height - tempY - h) : tempY;

            draggingElement.rightAlign = anchorRight;
            draggingElement.bottomAlign = anchorBottom;
            draggingElement.x = configX;
            draggingElement.y = configY;

            draggingElement.savePositionToConfig(MyModConfig.config);
            draggingElement = null;

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