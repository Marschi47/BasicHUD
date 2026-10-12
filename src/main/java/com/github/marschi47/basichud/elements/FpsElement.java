package com.github.marschi47.basichud.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class FpsElement extends HudElement {

    private int lastFps = -1;
    private String cachedFpsText = "FPS: 0";
    private int cachedTextWidth = -1;

    public FpsElement() {
        super("fps", "HUD_FPS",
                true, 10, 10,
                false, false,
                "FFFFFF", false, false);
    }

    @Override
    public int getWidth() {
        if (cachedTextWidth == -1) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            cachedTextWidth = fr.getStringWidth(cachedFpsText);
        }
        return cachedTextWidth;
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight) {
        int fps = Minecraft.getDebugFPS();
        if (fps != lastFps || cachedTextWidth == -1) {
            lastFps = fps;
            cachedFpsText = "FPS: " + lastFps;
            cachedTextWidth = fr.getStringWidth(cachedFpsText);
        }

        int xPos = rightAlign ? screenWidth - x - cachedTextWidth : x;
        int yPos = bottomAlign ? screenHeight - y - fr.FONT_HEIGHT : y;

        if (background) {
            drawBackgroundBox(xPos - 2, yPos - 2, cachedTextWidth + 4, fr.FONT_HEIGHT + 3);
        }

        int color = chroma ? getChromaColor(xPos, yPos) : colorInt;
        fr.drawStringWithShadow(cachedFpsText, xPos, yPos, color);
    }

    @Override
    public void renderPreview(FontRenderer fr, int drawX, int drawY) {
        fr.drawStringWithShadow("FPS: 144", drawX, drawY, 0xFFFFFF);
    }
}
