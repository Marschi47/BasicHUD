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
        int col1 = centerX - 205; // Left column X
        int col2 = centerX + 5;   // Right column X
        int btnW = 200;
        int btnH = 20;
        int gap = 24;

        // ---- FPS (left column) ----
        int y = 40;
        this.buttonList.add(new GuiToggleButton(1,  col1, y,       btnW, btnH, "Show FPS",         MyModConfig.fpsEnabled));
        this.buttonList.add(new GuiToggleButton(11, col1, y+gap,   btnW, btnH, "FPS Right-Align",  MyModConfig.fpsRightAlign));
        this.buttonList.add(new GuiToggleButton(12, col1, y+gap*2, btnW, btnH, "FPS Background",   MyModConfig.fpsBackground));
        this.buttonList.add(new GuiToggleButton(13, col1, y+gap*3, btnW, btnH, "FPS Chroma",       MyModConfig.fpsChroma));

        // ---- Ping (right column) ----
        this.buttonList.add(new GuiToggleButton(2,  col2, y,       btnW, btnH, "Show Ping",        MyModConfig.pingEnabled));
        this.buttonList.add(new GuiToggleButton(21, col2, y+gap,   btnW, btnH, "Ping Right-Align", MyModConfig.pingRightAlign));
        this.buttonList.add(new GuiToggleButton(22, col2, y+gap*2, btnW, btnH, "Ping Background",  MyModConfig.pingBackground));
        this.buttonList.add(new GuiToggleButton(23, col2, y+gap*3, btnW, btnH, "Ping Chroma",      MyModConfig.pingChroma));

        // ---- CPS (left column, second block) ----
        int y2 = y + gap * 5;
        this.buttonList.add(new GuiToggleButton(3,  col1, y2,       btnW, btnH, "Show CPS",         MyModConfig.cpsEnabled));
        this.buttonList.add(new GuiToggleButton(31, col1, y2+gap,   btnW, btnH, "CPS Right-Align",  MyModConfig.cpsRightAlign));
        this.buttonList.add(new GuiToggleButton(32, col1, y2+gap*2, btnW, btnH, "CPS Background",   MyModConfig.cpsBackground));
        this.buttonList.add(new GuiToggleButton(33, col1, y2+gap*3, btnW, btnH, "CPS Chroma",       MyModConfig.cpsChroma));

        // ---- Keystrokes (right column, second block) ----
        this.buttonList.add(new GuiToggleButton(4,  col2, y2,       btnW, btnH, "Show Keystrokes",        MyModConfig.keystrokesEnabled));
        this.buttonList.add(new GuiToggleButton(41, col2, y2+gap,   btnW, btnH, "Keystrokes Right-Align", MyModConfig.keystrokesRightAlign));
        this.buttonList.add(new GuiToggleButton(42, col2, y2+gap*2, btnW, btnH, "Keystrokes Background",  MyModConfig.keystrokesBackground));
        this.buttonList.add(new GuiToggleButton(43, col2, y2+gap*3, btnW, btnH, "Keystrokes Chroma",      MyModConfig.keystrokesChroma));

        // ---- Potion HUD (centered, third block) ----
        int y3 = y2 + gap * 5;
        int potCenterX = centerX - 100;
        this.buttonList.add(new GuiToggleButton(5,  potCenterX,       y3,       btnW, btnH, "Show Potion HUD",          MyModConfig.potionHudEnabled));
        this.buttonList.add(new GuiToggleButton(51, potCenterX,       y3+gap,   btnW, btnH, "Potion HUD Vertical Center", MyModConfig.potionHudVerticalCenter));
        this.buttonList.add(new GuiToggleButton(52, potCenterX,       y3+gap*2, btnW, btnH, "Potion HUD Background",    MyModConfig.potionHudBackground));
        this.buttonList.add(new GuiToggleButton(53, potCenterX,       y3+gap*3, btnW, btnH, "Potion HUD Chroma",        MyModConfig.potionHudChroma));

        // ---- Back Button ----
        this.buttonList.add(new GuiButton(100, centerX - 100, this.height - 30, 200, 20, "Back to Position Editor"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(mc.fontRendererObj, "BasicHUD Settings", this.width / 2, 15, 0xFFFFFF);

        // Section labels
        int col1 = this.width / 2 - 205;
        int col2 = this.width / 2 + 5;
        int y1Label = 30;
        int y2Label = 30 + 24 * 5;
        int y3Label = y2Label + 24 * 5;

        mc.fontRendererObj.drawStringWithShadow("FPS",        col1,  y1Label, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow("Ping",       col2,  y1Label, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow("CPS",        col1,  y2Label, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow("Keystrokes", col2,  y2Label, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow("Potion HUD", this.width / 2 - 40, y3Label, 0xF3F3F3);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button instanceof GuiToggleButton) {
            GuiToggleButton toggle = (GuiToggleButton) button;
            toggle.toggle();

            switch (button.id) {
                // FPS
                case 1:  MyModConfig.fpsEnabled    = toggle.state; save("HUD_FPS",  "fpsEnabled",    true,  toggle.state); break;
                case 11: MyModConfig.fpsRightAlign  = toggle.state; save("HUD_FPS",  "fpsRightAlign", false, toggle.state); break;
                case 12: MyModConfig.fpsBackground  = toggle.state; save("HUD_FPS",  "fpsBackground", false, toggle.state); break;
                case 13: MyModConfig.fpsChroma      = toggle.state; save("HUD_FPS",  "fpsChroma",     false, toggle.state); break;
                // Ping
                case 2:  MyModConfig.pingEnabled    = toggle.state; save("HUD_PING", "pingEnabled",    true,  toggle.state); break;
                case 21: MyModConfig.pingRightAlign  = toggle.state; save("HUD_PING", "pingRightAlign", false, toggle.state); break;
                case 22: MyModConfig.pingBackground  = toggle.state; save("HUD_PING", "pingBackground", false, toggle.state); break;
                case 23: MyModConfig.pingChroma      = toggle.state; save("HUD_PING", "pingChroma",     false, toggle.state); break;
                // CPS
                case 3:  MyModConfig.cpsEnabled     = toggle.state; save("HUD_CPS",  "cpsEnabled",    true,  toggle.state); break;
                case 31: MyModConfig.cpsRightAlign   = toggle.state; save("HUD_CPS",  "cpsRightAlign", false, toggle.state); break;
                case 32: MyModConfig.cpsBackground   = toggle.state; save("HUD_CPS",  "cpsBackground", false, toggle.state); break;
                case 33: MyModConfig.cpsChroma       = toggle.state; save("HUD_CPS",  "cpsChroma",     false, toggle.state); break;
                // Keystrokes
                case 4:  MyModConfig.keystrokesEnabled    = toggle.state; save("HUD_Keystrokes", "KeystrokesEnabled",    true,  toggle.state); break;
                case 41: MyModConfig.keystrokesRightAlign  = toggle.state; save("HUD_Keystrokes", "keystrokesRightAlign", true,  toggle.state); break;
                case 42: MyModConfig.keystrokesBackground  = toggle.state; save("HUD_Keystrokes", "keystrokesBackground", false, toggle.state); break;
                case 43: MyModConfig.keystrokesChroma      = toggle.state; save("HUD_Keystrokes", "keystrokesChroma",     false, toggle.state); break;
                // Potion HUD
                case 5:  MyModConfig.potionHudEnabled        = toggle.state; save("HUD_POTIONS", "potionHudEnabled",       true,  toggle.state); break;
                case 51: MyModConfig.potionHudVerticalCenter  = toggle.state; save("HUD_POTIONS", "potionHudVerticalCenter", true,  toggle.state); break;
                case 52: MyModConfig.potionHudBackground      = toggle.state; save("HUD_POTIONS", "potionHudBackground",    false, toggle.state); break;
                case 53: MyModConfig.potionHudChroma          = toggle.state; save("HUD_POTIONS", "potionHudChroma",        false, toggle.state); break;
            }

            MyModConfig.config.save();
        }

        if (button.id == 100) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    /** Helper: write a boolean back into the config property */
    private void save(String category, String key, boolean defaultVal, boolean value) {
        MyModConfig.config.get(category, key, defaultVal).set(value);
    }
}