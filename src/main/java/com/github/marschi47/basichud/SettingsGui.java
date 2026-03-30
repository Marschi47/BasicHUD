package com.github.marschi47.basichud;

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

    @Override
    public void initGui() {
        super.initGui();
        int centerX = this.width / 2;
        int col1 = centerX - 205;
        int col2 = centerX + 5;
        int btnW = 200;
        int btnH = 20;
        int gap = 24;

        // FPS (left column)
        int y = 40;
        this.buttonList.add(new GuiToggleButton(1, col1, y, btnW, btnH, "Show FPS", MyModConfig.fpsEnabled));
        this.buttonList
                .add(new GuiToggleButton(12, col1, y + gap, btnW, btnH, "FPS Background", MyModConfig.fpsBackground));
        this.buttonList
                .add(new GuiToggleButton(13, col1, y + gap * 2, btnW, btnH, "FPS Chroma", MyModConfig.fpsChroma));
        fpsColorField = createColorField(col1, y + gap * 3, btnW, btnH, MyModConfig.fpsColor);

        // Ping (right column)
        this.buttonList.add(new GuiToggleButton(2, col2, y, btnW, btnH, "Show Ping", MyModConfig.pingEnabled));
        this.buttonList
                .add(new GuiToggleButton(22, col2, y + gap, btnW, btnH, "Ping Background", MyModConfig.pingBackground));
        this.buttonList
                .add(new GuiToggleButton(23, col2, y + gap * 2, btnW, btnH, "Ping Chroma", MyModConfig.pingChroma));
        pingColorField = createColorField(col2, y + gap * 3, btnW, btnH, MyModConfig.pingColor);

        // CPS (left column, second block)
        int y2 = y + gap * 5;
        this.buttonList.add(new GuiToggleButton(3, col1, y2, btnW, btnH, "Show CPS", MyModConfig.cpsEnabled));
        this.buttonList
                .add(new GuiToggleButton(32, col1, y2 + gap, btnW, btnH, "CPS Background", MyModConfig.cpsBackground));
        this.buttonList
                .add(new GuiToggleButton(33, col1, y2 + gap * 2, btnW, btnH, "CPS Chroma", MyModConfig.cpsChroma));
        cpsColorField = createColorField(col1, y2 + gap * 3, btnW, btnH, MyModConfig.cpsColor);

        // Keystrokes (right column, second block)
        this.buttonList
                .add(new GuiToggleButton(4, col2, y2, btnW, btnH, "Show Keystrokes", MyModConfig.keystrokesEnabled));
        this.buttonList.add(new GuiToggleButton(42, col2, y2 + gap, btnW, btnH, "Keystrokes Background",
                MyModConfig.keystrokesBackground));
        this.buttonList.add(new GuiToggleButton(43, col2, y2 + gap * 2, btnW, btnH, "Keystrokes Chroma",
                MyModConfig.keystrokesChroma));
        keystrokesColorField = createColorField(col2, y2 + gap * 3, btnW, btnH, MyModConfig.keystrokesColor);
        keystrokesActivatedColorField = createColorField(col2, y2 + gap * 4, btnW, btnH,
                MyModConfig.keystrokesActivatedColor);

        // Potion HUD (centered, third block)
        int y3 = y2 + gap * 6;
        int potCenterX = centerX - 100;
        this.buttonList.add(
                new GuiToggleButton(5, potCenterX, y3, btnW, btnH, "Show Potion HUD", MyModConfig.potionHudEnabled));
        this.buttonList.add(new GuiToggleButton(52, potCenterX, y3 + gap, btnW, btnH, "Potion HUD Background",
                MyModConfig.potionHudBackground));
        this.buttonList.add(new GuiToggleButton(53, potCenterX, y3 + gap * 2, btnW, btnH, "Potion HUD Chroma",
                MyModConfig.potionHudChroma));
        potionHudColorField = createColorField(potCenterX, y3 + gap * 3, btnW, btnH, MyModConfig.potionHudColor);

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
        saveColor("HUD_FPS", "fpsColor", fpsColorField.getText(), MyModConfig.fpsColor, (val) -> {
            MyModConfig.fpsColor = val;
            MyModConfig.fpsColorInt = Integer.parseInt(val, 16);
        });
        saveColor("HUD_PING", "pingColor", pingColorField.getText(), MyModConfig.pingColor, (val) -> {
            MyModConfig.pingColor = val;
            MyModConfig.pingColorInt = Integer.parseInt(val, 16);
        });
        saveColor("HUD_CPS", "cpsColor", cpsColorField.getText(), MyModConfig.cpsColor, (val) -> {
            MyModConfig.cpsColor = val;
            MyModConfig.cpsColorInt = Integer.parseInt(val, 16);
        });
        saveColor("HUD_Keystrokes", "keystrokesColor", keystrokesColorField.getText(), MyModConfig.keystrokesColor,
                (val) -> {
                    MyModConfig.keystrokesColor = val;
                    MyModConfig.keystrokesColorInt = Integer.parseInt(val, 16);
                });
        saveColor("HUD_Keystrokes", "keystrokesActivatedColor", keystrokesActivatedColorField.getText(),
                MyModConfig.keystrokesActivatedColor, (val) -> {
                    MyModConfig.keystrokesActivatedColor = val;
                    MyModConfig.keystrokesActivatedColorInt = Integer.parseInt(val, 16);
                });
        saveColor("HUD_POTIONS", "potionHudColor", potionHudColorField.getText(), MyModConfig.potionHudColor, (val) -> {
            MyModConfig.potionHudColor = val;
            MyModConfig.potionHudColorInt = Integer.parseInt(val, 16);
        });
        MyModConfig.config.save();
    }

    private void saveColor(String category, String key, String newValue, String currentValue,
            java.util.function.Consumer<String> applier) {
        String upper = newValue.toUpperCase();
        if (upper.length() == 6 && upper.matches("[0-9A-F]{6}") && !upper.equals(currentValue)) {
            applier.accept(upper);
            MyModConfig.config.get(category, key, "FFFFFF").set(upper);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button instanceof GuiToggleButton) {
            GuiToggleButton toggle = (GuiToggleButton) button;
            toggle.toggle();

            switch (button.id) {
                // FPS
                case 1:
                    MyModConfig.fpsEnabled = toggle.state;
                    save("HUD_FPS", "fpsEnabled", true, toggle.state);
                    break;
                case 12:
                    MyModConfig.fpsBackground = toggle.state;
                    save("HUD_FPS", "fpsBackground", false, toggle.state);
                    break;
                case 13:
                    MyModConfig.fpsChroma = toggle.state;
                    save("HUD_FPS", "fpsChroma", false, toggle.state);
                    break;
                // Ping
                case 2:
                    MyModConfig.pingEnabled = toggle.state;
                    save("HUD_PING", "pingEnabled", true, toggle.state);
                    break;
                case 22:
                    MyModConfig.pingBackground = toggle.state;
                    save("HUD_PING", "pingBackground", false, toggle.state);
                    break;
                case 23:
                    MyModConfig.pingChroma = toggle.state;
                    save("HUD_PING", "pingChroma", false, toggle.state);
                    break;
                // CPS
                case 3:
                    MyModConfig.cpsEnabled = toggle.state;
                    save("HUD_CPS", "cpsEnabled", true, toggle.state);
                    break;
                case 32:
                    MyModConfig.cpsBackground = toggle.state;
                    save("HUD_CPS", "cpsBackground", false, toggle.state);
                    break;
                case 33:
                    MyModConfig.cpsChroma = toggle.state;
                    save("HUD_CPS", "cpsChroma", false, toggle.state);
                    break;
                // Keystrokes
                case 4:
                    MyModConfig.keystrokesEnabled = toggle.state;
                    save("HUD_Keystrokes", "KeystrokesEnabled", true, toggle.state);
                    break;
                case 42:
                    MyModConfig.keystrokesBackground = toggle.state;
                    save("HUD_Keystrokes", "keystrokesBackground", false, toggle.state);
                    break;
                case 43:
                    MyModConfig.keystrokesChroma = toggle.state;
                    save("HUD_Keystrokes", "keystrokesChroma", false, toggle.state);
                    break;
                // Potion HUD
                case 5:
                    MyModConfig.potionHudEnabled = toggle.state;
                    save("HUD_POTIONS", "potionHudEnabled", true, toggle.state);
                    break;
                case 52:
                    MyModConfig.potionHudBackground = toggle.state;
                    save("HUD_POTIONS", "potionHudBackground", false, toggle.state);
                    break;
                case 53:
                    MyModConfig.potionHudChroma = toggle.state;
                    save("HUD_POTIONS", "potionHudChroma", false, toggle.state);
                    break;
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

    private void save(String category, String key, boolean defaultVal, boolean value) {
        MyModConfig.config.get(category, key, defaultVal).set(value);
    }
}