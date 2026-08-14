package com.github.marschi47.basichud.elements;

import com.github.marschi47.basichud.CpsTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class CpsElement extends HudElement {

    private int lastLeftCps = -1;
    private int lastRightCps = -1;
    private String cachedCpsText = "CPS: 0|0";
    private int cachedTextWidth = -1;

    public CpsElement() {
        super("cps", "HUD_CPS",
                true, 10, 34,
                false, false,
                "FFFFFF", false, false);
    }

    @Override
    public int getWidth() {
        if (cachedTextWidth == -1) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            cachedTextWidth = fr.getStringWidth(cachedCpsText);
        }
        return cachedTextWidth;
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight) {
        if (CpsTracker.leftCps != lastLeftCps || CpsTracker.rightCps != lastRightCps || cachedTextWidth == -1) {
            lastLeftCps = CpsTracker.leftCps;
            lastRightCps = CpsTracker.rightCps;
            cachedCpsText = "CPS: " + lastLeftCps + "|" + lastRightCps;
            cachedTextWidth = fr.getStringWidth(cachedCpsText);
        }

        int xPos = rightAlign ? screenWidth - x - cachedTextWidth : x;
        int yPos = bottomAlign ? screenHeight - y - fr.FONT_HEIGHT : y;

        if (background) {
            drawBackgroundBox(xPos - 2, yPos - 2, cachedTextWidth + 4, fr.FONT_HEIGHT + 3);
        }

        int color = chroma ? getChromaColor(xPos, yPos) : colorInt;
        fr.drawStringWithShadow(cachedCpsText, xPos, yPos, color);
    }

    @Override
    public void renderPreview(FontRenderer fr, int drawX, int drawY) {
        fr.drawStringWithShadow("CPS: 0|0", drawX, drawY, 0xFFFFFF);
    }
}
