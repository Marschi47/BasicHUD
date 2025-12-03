package com.github.marschi47.basichud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudRenderer {
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = mc.fontRendererObj;

        // not render when f3
        if (mc.gameSettings.showDebugInfo) return;

        // --- FPS ---
        int fps = Minecraft.getDebugFPS();
        fr.drawStringWithShadow("FPS: " + fps, 10, 10, 0xFFFFFF);

        // --- PING ---
        int ping = getPlayerPing(mc);
        // display only in multiplayer
        if (ping > 0) {
            fr.drawStringWithShadow("Ping: " + ping + "ms", 10, 22, 0xFFFFFF);
        }

        // --- CPS ---
        fr.drawStringWithShadow("CPS: " + CpsTracker.rightCps + "|" + CpsTracker.leftCps, 10, 34, 0xFFFFFF);
    }

    // Helper method to get ping
    private int getPlayerPing(Minecraft mc) {
        if (!mc.isSingleplayer() && mc.thePlayer != null) {
            try {
                NetHandlerPlayClient connection = mc.getNetHandler();
                if (connection != null) {
                    // latency from player list data
                    return connection.getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
                }
            } catch (Exception e) {
                // ignore
            }
        }
        return 0;
    }
}
