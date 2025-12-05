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

        // W key
        fr.drawStringWithShadow("W", 100, 100, getKeyColor(KeystrokesTracker.wPressed));
        // A key
        fr.drawStringWithShadow("A", 90, 110, getKeyColor(KeystrokesTracker.aPressed));
        // S key
        fr.drawStringWithShadow("S", 100, 110, getKeyColor(KeystrokesTracker.sPressed));
        // D key
        fr.drawStringWithShadow("D", 110, 110, getKeyColor(KeystrokesTracker.dPressed));
        // Shift key
        fr.drawStringWithShadow(" [  Shift  ] ", 75, 130, getKeyColor(KeystrokesTracker.shiftPressed));
        // Space key
        fr.drawStringWithShadow(" [  Space  ] ", 70, 120, getKeyColor(KeystrokesTracker.spacePressed));
        // LMB
        fr.drawStringWithShadow("LMB", 85, 140, getKeyColor(KeystrokesTracker.leftClickPressed));
        // RMB
        fr.drawStringWithShadow("RMB", 105, 140, getKeyColor(KeystrokesTracker.rightClickPressed));
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
