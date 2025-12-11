package com.github.marschi47.basichud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudRenderer {

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = mc.fontRendererObj;

        if (mc.gameSettings.showDebugInfo) return;

        // FPS
        int fps = Minecraft.getDebugFPS();
        if (MyModConfig.fpsEnabled) {
            fr.drawStringWithShadow("FPS: " + fps, MyModConfig.fpsX, MyModConfig.fpsY, hexToInt(MyModConfig.fpsColor));
        }

        // PING
        int ping = getPlayerPing(mc);
        if (ping > 0 && MyModConfig.pingEnabled) {
            fr.drawStringWithShadow("Ping: " + ping + "ms", MyModConfig.pingX, MyModConfig.pingY, hexToInt(MyModConfig.pingColor));
        }

        // CPS
        if(MyModConfig.cpsEnabled) {
            fr.drawStringWithShadow("CPS: " + CpsTracker.rightCps + "|" + CpsTracker.leftCps, MyModConfig.cpsX, MyModConfig.cpsY, hexToInt(MyModConfig.cpsColor));
        }

        if(MyModConfig.keystrokesEnabled) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            int width = sr.getScaledWidth();

            fr.drawStringWithShadow("W", width - 35, 10, getKeyColor(KeystrokesTracker.wPressed));
            fr.drawStringWithShadow("A", width - 55, 24, getKeyColor(KeystrokesTracker.aPressed));
            fr.drawStringWithShadow("S", width - 35, 24, getKeyColor(KeystrokesTracker.sPressed));
            fr.drawStringWithShadow("D", width - 15, 24, getKeyColor(KeystrokesTracker.dPressed));
            fr.drawStringWithShadow("LMB", width - 58, 38, getKeyColor(KeystrokesTracker.leftClickPressed));
            fr.drawStringWithShadow("RMB", width - 26, 38, getKeyColor(KeystrokesTracker.rightClickPressed));
            fr.drawStringWithShadow("Space", width - 48, 52, getKeyColor(KeystrokesTracker.spacePressed));
            fr.drawStringWithShadow("Shift", width - 46, 66, getKeyColor(KeystrokesTracker.shiftPressed));
        }
    }

    // Helper function to decide color based on press state
    private int getKeyColor(boolean pressed) {
        return pressed ? 0xFF00FF00 : 0xFFFFFFFF;
    }

    /**
     * convert Hex String to Integer
     * default is white (0xFFFFFF) if invalid
     */
    private int hexToInt(String hex) {
        try {
            // Parses string as base 16 (Hex)
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return 0xFFFFFF; // return white if invalid
        }
    }

    // helper method to get ping
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
