package com.github.marschi47.basichud.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetHandlerPlayClient;

public class PingElement extends HudElement {

    public PingElement() {
        super("ping", "HUD_PING",
                true, 10, 22,
                false, false,
                "FFFFFF", false, false);
    }

    @Override
    public int getWidth() {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        return fr.getStringWidth("Ping: 20ms");
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight) {
        int ping = getPlayerPing();
        if (ping > 0) {
            String pingText = "Ping: " + ping + "ms";
            int textWidth = fr.getStringWidth(pingText);

            int xPos = rightAlign ? screenWidth - x - textWidth : x;
            int yPos = bottomAlign ? screenHeight - y - fr.FONT_HEIGHT : y;

            if (background) {
                drawBackgroundBox(xPos - 2, yPos - 2, textWidth + 4, fr.FONT_HEIGHT + 3);
            }

            int color = chroma ? getChromaColor(xPos, yPos) : colorInt;
            fr.drawStringWithShadow(pingText, xPos, yPos, color);
        }
    }

    @Override
    public void renderPreview(FontRenderer fr, int drawX, int drawY) {
        fr.drawStringWithShadow("Ping: 20ms", drawX, drawY, 0xFFFFFF);
    }

    private int getPlayerPing() {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.isSingleplayer() && mc.thePlayer != null) {
            try {
                NetHandlerPlayClient connection = mc.getNetHandler();
                if (connection != null) {
                    return connection.getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
                }
            } catch (Exception e) {
                // ignore
            }
        }
        return 0;
    }
}
