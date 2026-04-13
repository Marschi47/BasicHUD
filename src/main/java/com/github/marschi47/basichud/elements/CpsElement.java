package com.github.marschi47.basichud.elements;

import com.github.marschi47.basichud.CpsTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class CpsElement extends HudElement {

    public CpsElement() {
        super("cps", "HUD_CPS",
                true, 10, 34,
                false, false,
                "FFFFFF", false, false);
    }

    @Override
    public int getWidth() {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        return fr.getStringWidth("CPS: 12|12");
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight) {
        String cpsText = "CPS: " + CpsTracker.leftCps + "|" + CpsTracker.rightCps;
        int textWidth = fr.getStringWidth(cpsText);

        int xPos = rightAlign ? screenWidth - x - textWidth : x;
        int yPos = bottomAlign ? screenHeight - y - fr.FONT_HEIGHT : y;

        if (background) {
            drawBackgroundBox(xPos - 2, yPos - 2, textWidth + 4, fr.FONT_HEIGHT + 3);
        }

        int color = chroma ? getChromaColor(xPos, yPos) : colorInt;
        fr.drawStringWithShadow(cpsText, xPos, yPos, color);
    }

    @Override
    public void renderPreview(FontRenderer fr, int drawX, int drawY) {
        fr.drawStringWithShadow("CPS: 12|12", drawX, drawY, 0xFFFFFF);
    }
}
