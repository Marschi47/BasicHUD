package com.github.marschi47.basichud;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;

public class SettingsGui extends GuiScreen {
    private final GuiScreen parentScreen;

    public SettingsGui(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        super.initGui();
        int centerX = this.width / 2;
        int startY = 50;

        // Button ID 1: Toggle FPS
        this.buttonList.add(new GuiToggleButton(1, centerX - 100, startY, 200, 20, "Show FPS", MyModConfig.fpsEnabled));

        // Button ID 2: Toggle Ping
        this.buttonList.add(new GuiToggleButton(2, centerX - 100, startY + 25, 200, 20, "Show Ping", MyModConfig.pingEnabled));

        // Button ID 3: Toggle CPS
        this.buttonList.add(new GuiToggleButton(3, centerX - 100, startY + 50, 200, 20, "Show CPS", MyModConfig.cpsEnabled));

        // Button ID 100: Back Button
        this.buttonList.add(new GuiButton(100, centerX - 100, this.height - 40, 200, 20, "Back to Editor"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(mc.fontRendererObj, "BasicHUD Settings", this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button instanceof GuiToggleButton) {
            GuiToggleButton toggle = (GuiToggleButton) button;
            toggle.toggle(); // Flip the visual state

            // Update the actual config variables
            if (button.id == 1) MyModConfig.fpsEnabled = toggle.state;
            if (button.id == 2) MyModConfig.pingEnabled = toggle.state;
            if (button.id == 3) MyModConfig.cpsEnabled = toggle.state;

            // Save to file
            MyModConfig.config.get("HUD_FPS", "fpsEnabled", true).set(MyModConfig.fpsEnabled);
            MyModConfig.config.get("HUD_PING", "pingEnabled", true).set(MyModConfig.pingEnabled);
            MyModConfig.config.get("HUD_CPS", "cpsEnabled", true).set(MyModConfig.cpsEnabled);
            MyModConfig.config.save();
        }

        if (button.id == 100) {
            mc.displayGuiScreen(parentScreen); // Go back to the drag/drop screen
        }
    }
}