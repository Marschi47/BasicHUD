package com.github.marschi47.basichud.elements;

import com.github.marschi47.basichud.KeystrokesTracker;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.common.config.Configuration;

public class KeystrokesElement extends HudElement {

    private static final int GAP = 2;
    private static final int BOX_SIZE = 18;
    private static final int ROW_HEIGHT = 18;

    public String activatedColor;
    public int activatedColorInt;
    private final String defaultActivatedColor = "00FF00";

    public KeystrokesElement() {
        super("keystrokes", "HUD_Keystrokes",
                true, 5, 10,
                true, false,
                "FFFFFF", false, false);
        this.activatedColor = defaultActivatedColor;
        this.activatedColorInt = safeHexToInt(defaultActivatedColor);
    }

    @Override
    public int getWidth() {
        return (BOX_SIZE * 3) + (GAP * 2);
    }

    @Override
    public int getHeight() {
        return (ROW_HEIGHT * 5) + (GAP * 4);
    }

    @Override
    public void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight) {
        int totalWidth = getWidth();
        int totalHeight = getHeight();

        int xPos = rightAlign ? screenWidth - x - totalWidth : x;
        int yPos = bottomAlign ? screenHeight - y - totalHeight : y;

        // W
        drawKey(fr, "W", xPos + BOX_SIZE + GAP, yPos, BOX_SIZE, ROW_HEIGHT, KeystrokesTracker.wPressed);

        // A S D
        int yRow2 = yPos + ROW_HEIGHT + GAP;
        drawKey(fr, "A", xPos, yRow2, BOX_SIZE, ROW_HEIGHT, KeystrokesTracker.aPressed);
        drawKey(fr, "S", xPos + BOX_SIZE + GAP, yRow2, BOX_SIZE, ROW_HEIGHT, KeystrokesTracker.sPressed);
        drawKey(fr, "D", xPos + (BOX_SIZE + GAP) * 2, yRow2, BOX_SIZE, ROW_HEIGHT, KeystrokesTracker.dPressed);

        // LMB RMB
        int yRow3 = yRow2 + ROW_HEIGHT + GAP;
        int mouseWidth = (totalWidth - GAP) / 2;
        drawKey(fr, "LMB", xPos, yRow3, mouseWidth, ROW_HEIGHT, KeystrokesTracker.leftClickPressed());
        drawKey(fr, "RMB", xPos + mouseWidth + GAP, yRow3, mouseWidth, ROW_HEIGHT,
                KeystrokesTracker.rightClickPressed());

        // Space
        int yRow4 = yRow3 + ROW_HEIGHT + GAP;
        drawKey(fr, "Space", xPos, yRow4, totalWidth, ROW_HEIGHT, KeystrokesTracker.spacePressed);

        // Shift
        int yRow5 = yRow4 + ROW_HEIGHT + GAP;
        drawKey(fr, "Shift", xPos, yRow5, totalWidth, ROW_HEIGHT, KeystrokesTracker.shiftPressed);
    }

    @Override
    public void renderPreview(FontRenderer fr, int drawX, int drawY) {
        int w = getWidth();
        int h = getHeight();
        Gui.drawRect(drawX, drawY, drawX + w, drawY + h, 0x55000000);
        fr.drawStringWithShadow("Keys",
                drawX + w / 2 - fr.getStringWidth("Keys") / 2, drawY + h / 2 - 4, 0xFFFFFF);
    }

    private void drawKey(FontRenderer fr, String text, int kx, int ky, int width, int height, boolean pressed) {
        if (background) {
            drawBackgroundBox(kx, ky, width, height);
        }

        int drawColor;
        if (pressed) {
            drawColor = activatedColorInt;
        } else if (chroma) {
            drawColor = getChromaColor(kx, ky);
        } else {
            drawColor = colorInt;
        }

        int textX = kx + (width - fr.getStringWidth(text)) / 2;
        int textY = ky + (height - fr.FONT_HEIGHT) / 2 + 1;

        fr.drawStringWithShadow(text, textX, textY, drawColor);
    }

    @Override
    public void loadFromConfig(Configuration config) {
        enabled = config.getBoolean("KeystrokesEnabled", configCategory, defaultEnabled, "If Keystrokes are shown");
        color = config.getString("keystrokesColor", configCategory, defaultColor,
                "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");
        rightAlign = config.getBoolean("keystrokesRightAlign", configCategory, defaultRightAlign,
                "Align to the right side of the screen");
        bottomAlign = config.getBoolean("keystrokesBottomAlign", configCategory, defaultBottomAlign,
                "Align to the bottom");
        x = config.getInt("keystrokesX", configCategory, defaultX, 0, 10000, "X Position (or margin from right)");
        y = config.getInt("keystrokesY", configCategory, defaultY, 0, 10000, "Y Position");
        activatedColor = config.getString("keystrokesActivatedColor", configCategory, defaultActivatedColor,
                "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");
        chroma = config.getBoolean("keystrokesChroma", configCategory, defaultChroma, "Enable RGB/Chroma Effect");
        background = config.getBoolean("keystrokesBackground", configCategory, defaultBackground, "Enable Background");
        colorInt = safeHexToInt(color);
        activatedColorInt = safeHexToInt(activatedColor);
    }

    @Override
    public void savePositionToConfig(Configuration config) {
        config.get(configCategory, "keystrokesX", defaultX).set(x);
        config.get(configCategory, "keystrokesY", defaultY).set(y);
        config.get(configCategory, "keystrokesRightAlign", defaultRightAlign).set(rightAlign);
        config.get(configCategory, "keystrokesBottomAlign", defaultBottomAlign).set(bottomAlign);
    }

    @Override
    public void saveToConfig(Configuration config) {
        config.get(configCategory, "KeystrokesEnabled", defaultEnabled).set(enabled);
        config.get(configCategory, "keystrokesRightAlign", defaultRightAlign).set(rightAlign);
        config.get(configCategory, "keystrokesBottomAlign", defaultBottomAlign).set(bottomAlign);
        config.get(configCategory, "keystrokesX", defaultX).set(x);
        config.get(configCategory, "keystrokesY", defaultY).set(y);
        config.get(configCategory, "keystrokesColor", defaultColor).set(color);
        config.get(configCategory, "keystrokesActivatedColor", defaultActivatedColor).set(activatedColor);
        config.get(configCategory, "keystrokesChroma", defaultChroma).set(chroma);
        config.get(configCategory, "keystrokesBackground", defaultBackground).set(background);
    }

    @Override
    public void resetToDefaults() {
        super.resetToDefaults();
        activatedColor = defaultActivatedColor;
        activatedColorInt = safeHexToInt(defaultActivatedColor);
    }
}
