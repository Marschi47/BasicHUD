package com.github.marschi47.basichud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.client.resources.I18n;
import java.util.Collection;

public class HudRenderer {

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = mc.fontRendererObj;
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();

        if (mc.gameSettings.showDebugInfo) return;

        // FPS
        if (MyModConfig.fpsEnabled) {
            int fps = Minecraft.getDebugFPS();
            String fpsText = "FPS: " + fps;

            int xPos;
            if (MyModConfig.fpsRightAlign) {
                int textWidth = fr.getStringWidth(fpsText);
                xPos = width - MyModConfig.fpsX - textWidth;
            } else {
                xPos = MyModConfig.fpsX;
            }

            fr.drawStringWithShadow(fpsText, xPos, MyModConfig.fpsY, hexToInt(MyModConfig.fpsColor));
        }

        // PING
        if (MyModConfig.pingEnabled) {
            int ping = getPlayerPing(mc);
            if (ping > 0) {
                String pingText = "Ping: " + ping + "ms";

                int xPos;
                if (MyModConfig.pingRightAlign) {
                    int textWidth = fr.getStringWidth(pingText);
                    xPos = width - MyModConfig.pingX - textWidth;
                } else {
                    xPos = MyModConfig.pingX;
                }

                fr.drawStringWithShadow(pingText, xPos, MyModConfig.pingY, hexToInt(MyModConfig.pingColor));
            }
        }

        // CPS
        if(MyModConfig.cpsEnabled) {
            String cpsText = "CPS: " + CpsTracker.leftCps + "|" + CpsTracker.rightCps;

            int xPos;
            if (MyModConfig.cpsRightAlign) {
                int textWidth = fr.getStringWidth(cpsText);
                xPos = width - MyModConfig.cpsX - textWidth;
            } else {
                xPos = MyModConfig.cpsX;
            }

            fr.drawStringWithShadow(cpsText, xPos, MyModConfig.cpsY, hexToInt(MyModConfig.cpsColor));
        }

        //keystrokes
        if(MyModConfig.keystrokesEnabled) {

            int hudWidth = 60;

            int xPos;
            int yPos = MyModConfig.keystrokesY;

            if (MyModConfig.keystrokesRightAlign) {
                xPos = width - MyModConfig.keystrokesX - hudWidth;
            } else {
                xPos = MyModConfig.keystrokesX;
            }

            // W
            fr.drawStringWithShadow("W", xPos + 25, yPos, getKeyColor(KeystrokesTracker.wPressed));

            // A S D
            fr.drawStringWithShadow("A", xPos + 5,  yPos + 14, getKeyColor(KeystrokesTracker.aPressed));
            fr.drawStringWithShadow("S", xPos + 25, yPos + 14, getKeyColor(KeystrokesTracker.sPressed));
            fr.drawStringWithShadow("D", xPos + 45, yPos + 14, getKeyColor(KeystrokesTracker.dPressed));

            // Mouse
            fr.drawStringWithShadow("LMB", xPos + 2,  yPos + 28, getKeyColor(KeystrokesTracker.leftClickPressed()));
            fr.drawStringWithShadow("RMB", xPos + 34, yPos + 28, getKeyColor(KeystrokesTracker.rightClickPressed()));

            // Space
            fr.drawStringWithShadow("Space", xPos + 12, yPos + 42, getKeyColor(KeystrokesTracker.spacePressed));

            // Shift
            fr.drawStringWithShadow("Shift", xPos + 14, yPos + 56, getKeyColor(KeystrokesTracker.shiftPressed));
        }

        // POTION EFFECTS
        if (MyModConfig.potionHudEnabled) {
            Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();

            if (!effects.isEmpty()) {
                int xPos = MyModConfig.potionHudX;
                int yOffset = MyModConfig.potionHudY;
                int color = hexToInt(MyModConfig.potionHudColor);
                int lineHeight = fr.FONT_HEIGHT + 2;

                if (MyModConfig.potionHudVerticalCenter) {
                    int screenHeight = sr.getScaledHeight();

                    int totalListHeight = effects.size() * lineHeight;

                    yOffset = (screenHeight / 2) - (totalListHeight / 2);
                }

                for (PotionEffect effect : effects) {
                    Potion potion = Potion.potionTypes[effect.getPotionID()];

                    String name = I18n.format(potion.getName());

                    if (effect.getAmplifier() == 1) {
                        name = name + " II";
                    } else if (effect.getAmplifier() == 2) {
                        name = name + " III";
                    } else if (effect.getAmplifier() == 3) {
                        name = name + " IV";
                    }
                    String duration = Potion.getDurationString(effect);
                    String displayText = name + ": " + duration;

                    fr.drawStringWithShadow(displayText, xPos, yOffset, color);
                    yOffset += lineHeight;
                }
            }
        }
    }

    // Helper function to decide color based on press state
    private int getKeyColor(boolean pressed) {
        return pressed ? hexToInt(MyModConfig.keystrokesActivatedColor) : hexToInt(MyModConfig.keystrokesColor);
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
