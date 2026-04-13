package com.github.marschi47.basichud.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.common.config.Configuration;

import java.awt.Color;

public abstract class HudElement {

    protected final String id;
    protected final String configCategory;

    // Shared config properties
    public boolean enabled;
    public int x, y;
    public boolean rightAlign, bottomAlign;
    public String color;
    public int colorInt;
    public boolean chroma;
    public boolean background;

    // Defaults (set by subclass constructor)
    protected final boolean defaultEnabled;
    protected final int defaultX, defaultY;
    protected final boolean defaultRightAlign, defaultBottomAlign;
    protected final String defaultColor;
    protected final boolean defaultChroma, defaultBackground;

    protected HudElement(String id, String configCategory,
            boolean defaultEnabled, int defaultX, int defaultY,
            boolean defaultRightAlign, boolean defaultBottomAlign,
            String defaultColor, boolean defaultChroma, boolean defaultBackground) {
        this.id = id;
        this.configCategory = configCategory;
        this.defaultEnabled = defaultEnabled;
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.defaultRightAlign = defaultRightAlign;
        this.defaultBottomAlign = defaultBottomAlign;
        this.defaultColor = defaultColor;
        this.defaultChroma = defaultChroma;
        this.defaultBackground = defaultBackground;

        // Initialize to defaults
        this.enabled = defaultEnabled;
        this.x = defaultX;
        this.y = defaultY;
        this.rightAlign = defaultRightAlign;
        this.bottomAlign = defaultBottomAlign;
        this.color = defaultColor;
        this.colorInt = safeHexToInt(defaultColor);
        this.chroma = defaultChroma;
        this.background = defaultBackground;
    }

    public String getId() {
        return id;
    }

    public String getConfigCategory() {
        return configCategory;
    }

    // Abstract methods

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight);

    public abstract void renderPreview(FontRenderer fr, int drawX, int drawY);

    // Coordinate resolution

    public int resolveX(int screenWidth) {
        return rightAlign ? screenWidth - x - getWidth() : x;
    }

    public int resolveY(int screenHeight) {
        return bottomAlign ? screenHeight - y - getHeight() : y;
    }

    // Shared rendering helpers

    public int getChromaColor(int px, int py) {
        long speed = 2000;
        float hue = (float) ((System.currentTimeMillis() + (px + py) * 10) % speed) / speed;
        return Color.getHSBColor(hue, 1.0f, 1.0f).getRGB();
    }

    public void drawBackgroundBox(int bx, int by, int width, int height) {
        int backgroundColor = 0x90000000;
        Gui.drawRect(bx, by, bx + width, by + height, backgroundColor);
    }

    // Config

    public void loadFromConfig(Configuration config) {
        enabled = config.getBoolean(id + "Enabled", configCategory, defaultEnabled, "If " + id + " is shown");
        rightAlign = config.getBoolean(id + "RightAlign", configCategory, defaultRightAlign, "Align to the right");
        bottomAlign = config.getBoolean(id + "BottomAlign", configCategory, defaultBottomAlign, "Align to the bottom");
        x = config.getInt(id + "X", configCategory, defaultX, 0, 10000, "X Position of " + id);
        y = config.getInt(id + "Y", configCategory, defaultY, 0, 10000, "Y Position of " + id);
        color = config.getString(id + "Color", configCategory, defaultColor,
                "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");
        chroma = config.getBoolean(id + "Chroma", configCategory, defaultChroma, "Enable RGB/Chroma Effect");
        background = config.getBoolean(id + "Background", configCategory, defaultBackground, "Enable Background");
        colorInt = safeHexToInt(color);
    }

    public void savePositionToConfig(Configuration config) {
        config.get(configCategory, id + "X", defaultX).set(x);
        config.get(configCategory, id + "Y", defaultY).set(y);
        config.get(configCategory, id + "RightAlign", defaultRightAlign).set(rightAlign);
        config.get(configCategory, id + "BottomAlign", defaultBottomAlign).set(bottomAlign);
    }

    public void saveToConfig(Configuration config) {
        config.get(configCategory, id + "Enabled", defaultEnabled).set(enabled);
        config.get(configCategory, id + "RightAlign", defaultRightAlign).set(rightAlign);
        config.get(configCategory, id + "BottomAlign", defaultBottomAlign).set(bottomAlign);
        config.get(configCategory, id + "X", defaultX).set(x);
        config.get(configCategory, id + "Y", defaultY).set(y);
        config.get(configCategory, id + "Color", defaultColor).set(color);
        config.get(configCategory, id + "Chroma", defaultChroma).set(chroma);
        config.get(configCategory, id + "Background", defaultBackground).set(background);
    }

    public void resetToDefaults() {
        enabled = defaultEnabled;
        x = defaultX;
        y = defaultY;
        rightAlign = defaultRightAlign;
        bottomAlign = defaultBottomAlign;
        color = defaultColor;
        colorInt = safeHexToInt(defaultColor);
        chroma = defaultChroma;
        background = defaultBackground;
    }

    // Util

    protected static int safeHexToInt(String hex) {
        try {
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return 0xFFFFFF;
        }
    }
}
