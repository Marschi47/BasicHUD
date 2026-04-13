package com.github.marschi47.basichud.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class FpsElement extends HudElement {

    public FpsElement() {
        super("fps", "HUD_FPS",
                true, 10, 10,
                false, false,
                "FFFFFF", false, false);
    }

    @Override
    public int getWidth() {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        return fr.getStringWidth("FPS: 144");
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight) {
        int fps = Minecraft.getDebugFPS();
        String fpsText = "FPS: " + fps;
        int textWidth = fr.getStringWidth(fpsText);

        int xPos = rightAlign ? screenWidth - x - textWidth : x;
        int yPos = bottomAlign ? screenHeight - y - fr.FONT_HEIGHT : y;

        if (background) {
            drawBackgroundBox(xPos - 2, yPos - 2, textWidth + 4, fr.FONT_HEIGHT + 3);
        }

        int color = chroma ? getChromaColor(xPos, yPos) : colorInt;
        fr.drawStringWithShadow(fpsText, xPos, yPos, color);
    }

    @Override
    public void renderPreview(FontRenderer fr, int drawX, int drawY) {
        fr.drawStringWithShadow("FPS: 144", drawX, drawY, 0xFFFFFF);
    }
}
