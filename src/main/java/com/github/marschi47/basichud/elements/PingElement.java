package com.github.marschi47.basichud.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetHandlerPlayClient;

public class PingElement extends HudElement {

    private int lastPing = -1;
    private String cachedPingText = "Ping: 0ms";
    private int cachedTextWidth = -1;
    private long lastPingUpdateTime = 0;

    public PingElement() {
        super("ping", "HUD_PING",
                true, 10, 22,
                false, false,
                "FFFFFF", false, false);
    }

    @Override
    public int getWidth() {
        if (cachedTextWidth == -1) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            cachedTextWidth = fr.getStringWidth(cachedPingText);
        }
        return cachedTextWidth;
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPingUpdateTime >= 1000) {
            lastPingUpdateTime = currentTime;
            int ping = getPlayerPing();
            if (ping != lastPing || cachedTextWidth == -1) {
                lastPing = ping;
                cachedPingText = "Ping: " + lastPing + "ms";
                cachedTextWidth = fr.getStringWidth(cachedPingText);
            }
        }

        if (lastPing > 0) {
            int xPos = rightAlign ? screenWidth - x - cachedTextWidth : x;
            int yPos = bottomAlign ? screenHeight - y - fr.FONT_HEIGHT : y;

            if (background) {
                drawBackgroundBox(xPos - 2, yPos - 2, cachedTextWidth + 4, fr.FONT_HEIGHT + 3);
            }

            int color = chroma ? getChromaColor(xPos, yPos) : colorInt;
            fr.drawStringWithShadow(cachedPingText, xPos, yPos, color);
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
