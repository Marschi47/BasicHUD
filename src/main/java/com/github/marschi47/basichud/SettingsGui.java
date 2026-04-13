package com.github.marschi47.basichud;

import com.github.marschi47.basichud.elements.HudElement;
import com.github.marschi47.basichud.elements.HudElementRegistry;
import com.github.marschi47.basichud.elements.KeystrokesElement;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.Gui;

import java.io.IOException;

public class SettingsGui extends GuiScreen {
    private final GuiScreen parentScreen;
    private boolean skipSaveOnClose = false;

    private GuiTextField fpsColorField;
    private GuiTextField pingColorField;
    private GuiTextField cpsColorField;
    private GuiTextField keystrokesColorField;
    private GuiTextField keystrokesActivatedColorField;
    private GuiTextField potionHudColorField;

    public SettingsGui(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    // --- Helper to look up elements by ID ---
    private HudElement el(String id) {
        return HudElementRegistry.getById(id);
    }

    @Override
    public void initGui() {
        super.initGui();
        int centerX = this.width / 2;
        int col1 = centerX - 205;
        int col2 = centerX + 5;
        int btnW = 200;
        int btnH = 20;
        int gap = 24;

        HudElement fps = el("fps");
        HudElement ping = el("ping");
        HudElement cps = el("cps");
        HudElement keystrokes = el("keystrokes");
        HudElement potionHud = el("potionHud");

        // FPS (left column)
        int y = 40;
        this.buttonList.add(new GuiToggleButton(1, col1, y, btnW, btnH, "Show FPS", fps.enabled));
        this.buttonList
                .add(new GuiToggleButton(12, col1, y + gap, btnW, btnH, "FPS Background", fps.background));
        this.buttonList
                .add(new GuiToggleButton(13, col1, y + gap * 2, btnW, btnH, "FPS Chroma", fps.chroma));
        fpsColorField = createColorField(col1, y + gap * 3, btnW, btnH, fps.color);

        // Ping (right column)
        this.buttonList.add(new GuiToggleButton(2, col2, y, btnW, btnH, "Show Ping", ping.enabled));
        this.buttonList
                .add(new GuiToggleButton(22, col2, y + gap, btnW, btnH, "Ping Background", ping.background));
        this.buttonList
                .add(new GuiToggleButton(23, col2, y + gap * 2, btnW, btnH, "Ping Chroma", ping.chroma));
        pingColorField = createColorField(col2, y + gap * 3, btnW, btnH, ping.color);

        // CPS (left column, second block)
        int y2 = y + gap * 5;
        this.buttonList.add(new GuiToggleButton(3, col1, y2, btnW, btnH, "Show CPS", cps.enabled));
        this.buttonList
                .add(new GuiToggleButton(32, col1, y2 + gap, btnW, btnH, "CPS Background", cps.background));
        this.buttonList
                .add(new GuiToggleButton(33, col1, y2 + gap * 2, btnW, btnH, "CPS Chroma", cps.chroma));
        cpsColorField = createColorField(col1, y2 + gap * 3, btnW, btnH, cps.color);

        // Keystrokes (right column, second block)
        this.buttonList
                .add(new GuiToggleButton(4, col2, y2, btnW, btnH, "Show Keystrokes", keystrokes.enabled));
        this.buttonList.add(new GuiToggleButton(42, col2, y2 + gap, btnW, btnH, "Keystrokes Background",
                keystrokes.background));
        this.buttonList.add(new GuiToggleButton(43, col2, y2 + gap * 2, btnW, btnH, "Keystrokes Chroma",
                keystrokes.chroma));
        keystrokesColorField = createColorField(col2, y2 + gap * 3, btnW, btnH, keystrokes.color);
        keystrokesActivatedColorField = createColorField(col2, y2 + gap * 4, btnW, btnH,
                ((KeystrokesElement) keystrokes).activatedColor);

        // Potion HUD (centered, third block)
        int y3 = y2 + gap * 6;
        int potCenterX = centerX - 100;
        this.buttonList.add(
                new GuiToggleButton(5, potCenterX, y3, btnW, btnH, "Show Potion HUD", potionHud.enabled));
        this.buttonList.add(new GuiToggleButton(52, potCenterX, y3 + gap, btnW, btnH, "Potion HUD Background",
                potionHud.background));
        this.buttonList.add(new GuiToggleButton(53, potCenterX, y3 + gap * 2, btnW, btnH, "Potion HUD Chroma",
                potionHud.chroma));
        potionHudColorField = createColorField(potCenterX, y3 + gap * 3, btnW, btnH, potionHud.color);

        // Bottom Buttons
        this.buttonList.add(new GuiButton(100, centerX - 205, this.height - 30, 200, 20, "Back to Position Editor"));
        this.buttonList.add(new GuiButton(101, centerX + 5, this.height - 30, 200, 20, "\u00a7cReset to Default"));
    }

    private GuiTextField createColorField(int x, int y, int width, int height, String currentValue) {
        GuiTextField field = new GuiTextField(0, mc.fontRendererObj, x + 20, y, width - 20, height);
        field.setMaxStringLength(6);
        field.setText(currentValue);
        field.setValidator(input -> input != null && input.matches("[0-9a-fA-F]*"));
        return field;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(mc.fontRendererObj, "BasicHUD Settings", this.width / 2, 15, 0xFFFFFF);

        int centerX = this.width / 2;
        int col1 = centerX - 205;
        int col2 = centerX + 5;
        int gap = 24;
        int y1Label = 30;
        int y2Label = 30 + gap * 5;
        int y3Label = y2Label + gap * 6;

        mc.fontRendererObj.drawStringWithShadow("FPS", col1, y1Label, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow("Ping", col2, y1Label, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow("CPS", col1, y2Label, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow("Keystrokes", col2, y2Label, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow("Potion HUD", centerX - 40, y3Label, 0xF3F3F3);

        // Color field labels (# prefix)
        int y = 40;
        drawColorPreview(col1, y + gap * 3, fpsColorField, null);
        drawColorPreview(col2, y + gap * 3, pingColorField, null);

        int y2 = y + gap * 5;
        drawColorPreview(col1, y2 + gap * 3, cpsColorField, null);
        drawColorPreview(col2, y2 + gap * 3, keystrokesColorField, null);
        drawColorPreview(col2, y2 + gap * 4, keystrokesActivatedColorField, "Active");

        int y3 = y2 + gap * 6;
        int potCenterX = centerX - 100;
        drawColorPreview(potCenterX, y3 + gap * 3, potionHudColorField, null);

        fpsColorField.drawTextBox();
        pingColorField.drawTextBox();
        cpsColorField.drawTextBox();
        keystrokesColorField.drawTextBox();
        keystrokesActivatedColorField.drawTextBox();
        potionHudColorField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawColorPreview(int x, int y, GuiTextField field, String label) {
        String hex = field.getText();
        int previewColor = 0xFFFFFF;
        if (hex.length() == 6) {
            try {
                previewColor = Integer.parseInt(hex, 16);
            } catch (NumberFormatException ignored) {
            }
        }
        int size = 16;
        int px = x + 1;
        int py = y + 2;
        Gui.drawRect(px - 1, py - 1, px + size + 1, py + size + 1, 0xFF000000);
        Gui.drawRect(px, py, px + size, py + size, 0xFF000000 | previewColor);
        if (label != null) {
            mc.fontRendererObj.drawStringWithShadow("\u00a77" + label, px + size + 4, y + 6, 0xAAAAAA);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        fpsColorField.textboxKeyTyped(typedChar, keyCode);
        pingColorField.textboxKeyTyped(typedChar, keyCode);
        cpsColorField.textboxKeyTyped(typedChar, keyCode);
        keystrokesColorField.textboxKeyTyped(typedChar, keyCode);
        keystrokesActivatedColorField.textboxKeyTyped(typedChar, keyCode);
        potionHudColorField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        fpsColorField.mouseClicked(mouseX, mouseY, mouseButton);
        pingColorField.mouseClicked(mouseX, mouseY, mouseButton);
        cpsColorField.mouseClicked(mouseX, mouseY, mouseButton);
        keystrokesColorField.mouseClicked(mouseX, mouseY, mouseButton);
        keystrokesActivatedColorField.mouseClicked(mouseX, mouseY, mouseButton);
        potionHudColorField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        fpsColorField.updateCursorCounter();
        pingColorField.updateCursorCounter();
        cpsColorField.updateCursorCounter();
        keystrokesColorField.updateCursorCounter();
        keystrokesActivatedColorField.updateCursorCounter();
        potionHudColorField.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        if (!skipSaveOnClose) {
            saveAllColors();
        }
        super.onGuiClosed();
    }

    private void saveAllColors() {
        HudElement fps = el("fps");
        HudElement ping = el("ping");
        HudElement cps = el("cps");
        KeystrokesElement keystrokes = (KeystrokesElement) el("keystrokes");
        HudElement potionHud = el("potionHud");

        saveColor(fps, fpsColorField.getText());
        saveColor(ping, pingColorField.getText());
        saveColor(cps, cpsColorField.getText());
        saveColor(keystrokes, keystrokesColorField.getText());

        // Keystrokes activated color
        String activatedHex = keystrokesActivatedColorField.getText().toUpperCase();
        if (activatedHex.length() == 6 && activatedHex.matches("[0-9A-F]{6}")
                && !activatedHex.equals(keystrokes.activatedColor)) {
            keystrokes.activatedColor = activatedHex;
            keystrokes.activatedColorInt = Integer.parseInt(activatedHex, 16);
        }

        saveColor(potionHud, potionHudColorField.getText());

        // Save all elements to config
        for (HudElement element : HudElementRegistry.getElements()) {
            element.saveToConfig(MyModConfig.config);
        }
        MyModConfig.config.save();
    }

    private void saveColor(HudElement element, String newValue) {
        String upper = newValue.toUpperCase();
        if (upper.length() == 6 && upper.matches("[0-9A-F]{6}") && !upper.equals(element.color)) {
            element.color = upper;
            element.colorInt = Integer.parseInt(upper, 16);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button instanceof GuiToggleButton) {
            GuiToggleButton toggle = (GuiToggleButton) button;
            toggle.toggle();

            HudElement fps = el("fps");
            HudElement ping = el("ping");
            HudElement cps = el("cps");
            HudElement keystrokes = el("keystrokes");
            HudElement potionHud = el("potionHud");

            switch (button.id) {
                // FPS
                case 1:
                    fps.enabled = toggle.state;
                    break;
                case 12:
                    fps.background = toggle.state;
                    break;
                case 13:
                    fps.chroma = toggle.state;
                    break;
                // Ping
                case 2:
                    ping.enabled = toggle.state;
                    break;
                case 22:
                    ping.background = toggle.state;
                    break;
                case 23:
                    ping.chroma = toggle.state;
                    break;
                // CPS
                case 3:
                    cps.enabled = toggle.state;
                    break;
                case 32:
                    cps.background = toggle.state;
                    break;
                case 33:
                    cps.chroma = toggle.state;
                    break;
                // Keystrokes
                case 4:
                    keystrokes.enabled = toggle.state;
                    break;
                case 42:
                    keystrokes.background = toggle.state;
                    break;
                case 43:
                    keystrokes.chroma = toggle.state;
                    break;
                // Potion HUD
                case 5:
                    potionHud.enabled = toggle.state;
                    break;
                case 52:
                    potionHud.background = toggle.state;
                    break;
                case 53:
                    potionHud.chroma = toggle.state;
                    break;
            }

            // Save the modified element's config
            for (HudElement element : HudElementRegistry.getElements()) {
                element.saveToConfig(MyModConfig.config);
            }
            MyModConfig.config.save();
        }

        if (button.id == 100) {
            mc.displayGuiScreen(parentScreen);
        }

        if (button.id == 101) {
            skipSaveOnClose = true;
            MyModConfig.resetToDefaults();
            mc.displayGuiScreen(new SettingsGui(parentScreen));
        }
    }
}