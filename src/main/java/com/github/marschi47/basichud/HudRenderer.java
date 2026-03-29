package com.github.marschi47.basichud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.client.resources.I18n;
import java.awt.Color;
import java.util.Collection;

public class HudRenderer {

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = mc.fontRendererObj;
        ScaledResolution sr = new ScaledResolution(mc);
        int screenWidth = sr.getScaledWidth();
        int screenHeight = sr.getScaledHeight();

        if (mc.gameSettings.showDebugInfo)
            return;

        // FPS
        if (MyModConfig.fpsEnabled) {
            int fps = Minecraft.getDebugFPS();
            String fpsText = "FPS: " + fps;
            int textWidth = fr.getStringWidth(fpsText);

            int xPos = MyModConfig.fpsRightAlign
                    ? screenWidth - MyModConfig.fpsX - textWidth
                    : MyModConfig.fpsX;
            int yPos = MyModConfig.fpsBottomAlign
                    ? screenHeight - MyModConfig.fpsY - fr.FONT_HEIGHT
                    : MyModConfig.fpsY;

            if (MyModConfig.fpsBackground) {
                drawBackgroundBox(xPos - 2, yPos - 2, textWidth + 4, fr.FONT_HEIGHT + 3);
            }

            int color = MyModConfig.fpsChroma ? getChromaColor(xPos, yPos) : MyModConfig.fpsColorInt;
            fr.drawStringWithShadow(fpsText, xPos, yPos, color);
        }

        // PING
        if (MyModConfig.pingEnabled) {
            int ping = getPlayerPing(mc);
            if (ping > 0) {
                String pingText = "Ping: " + ping + "ms";
                int textWidth = fr.getStringWidth(pingText);

                int xPos = MyModConfig.pingRightAlign
                        ? screenWidth - MyModConfig.pingX - textWidth
                        : MyModConfig.pingX;
                int yPos = MyModConfig.pingBottomAlign
                        ? screenHeight - MyModConfig.pingY - fr.FONT_HEIGHT
                        : MyModConfig.pingY;

                if (MyModConfig.pingBackground) {
                    drawBackgroundBox(xPos - 2, yPos - 2, textWidth + 4, fr.FONT_HEIGHT + 3);
                }

                int color = MyModConfig.pingChroma ? getChromaColor(xPos, yPos) : MyModConfig.pingColorInt;
                fr.drawStringWithShadow(pingText, xPos, yPos, color);
            }
        }

        // CPS
        if (MyModConfig.cpsEnabled) {
            String cpsText = "CPS: " + CpsTracker.leftCps + "|" + CpsTracker.rightCps;
            int textWidth = fr.getStringWidth(cpsText);

            int xPos = MyModConfig.cpsRightAlign
                    ? screenWidth - MyModConfig.cpsX - textWidth
                    : MyModConfig.cpsX;
            int yPos = MyModConfig.cpsBottomAlign
                    ? screenHeight - MyModConfig.cpsY - fr.FONT_HEIGHT
                    : MyModConfig.cpsY;

            if (MyModConfig.cpsBackground) {
                drawBackgroundBox(xPos - 2, yPos - 2, textWidth + 4, fr.FONT_HEIGHT + 3);
            }

            int color = MyModConfig.cpsChroma ? getChromaColor(xPos, yPos) : MyModConfig.cpsColorInt;
            fr.drawStringWithShadow(cpsText, xPos, yPos, color);
        }

        // keystrokes
        if (MyModConfig.keystrokesEnabled) {
            int gap = 2;
            int boxSize = 18;
            int height = 18;
            int totalWidth = (boxSize * 3) + (gap * 2);
            int totalHeight = (height * 5) + (gap * 4);

            int xPos = MyModConfig.keystrokesRightAlign
                    ? screenWidth - MyModConfig.keystrokesX - totalWidth
                    : MyModConfig.keystrokesX;
            int yPos = MyModConfig.keystrokesBottomAlign
                    ? screenHeight - MyModConfig.keystrokesY - totalHeight
                    : MyModConfig.keystrokesY;

            // W
            drawKey(fr, "W", xPos + boxSize + gap, yPos, boxSize, height, KeystrokesTracker.wPressed);

            // A S D
            int yRow2 = yPos + height + gap;
            drawKey(fr, "A", xPos, yRow2, boxSize, height, KeystrokesTracker.aPressed);
            drawKey(fr, "S", xPos + boxSize + gap, yRow2, boxSize, height, KeystrokesTracker.sPressed);
            drawKey(fr, "D", xPos + (boxSize + gap) * 2, yRow2, boxSize, height, KeystrokesTracker.dPressed);

            // RMB LMB
            int yRow3 = yRow2 + height + gap;
            int mouseWidth = (totalWidth - gap) / 2;
            drawKey(fr, "LMB", xPos, yRow3, mouseWidth, height, KeystrokesTracker.leftClickPressed());
            drawKey(fr, "RMB", xPos + mouseWidth + gap, yRow3, mouseWidth, height,
                    KeystrokesTracker.rightClickPressed());

            // Space
            int yRow4 = yRow3 + height + gap;
            drawKey(fr, "Space", xPos, yRow4, totalWidth, height, KeystrokesTracker.spacePressed);

            // Shift
            int yRow5 = yRow4 + height + gap;
            drawKey(fr, "Shift", xPos, yRow5, totalWidth, height, KeystrokesTracker.shiftPressed);
        }

        // potions
        if (MyModConfig.potionHudEnabled) {
            Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();

            if (!effects.isEmpty()) {
                int baseColor = MyModConfig.potionHudColorInt;
                int lineHeight = fr.FONT_HEIGHT + 2;
                int totalListHeight = effects.size() * lineHeight;

                int xPos = MyModConfig.potionHudRightAlign
                        ? screenWidth - MyModConfig.potionHudX
                        : MyModConfig.potionHudX;
                int yOffset = MyModConfig.potionHudBottomAlign
                        ? screenHeight - MyModConfig.potionHudY - totalListHeight
                        : MyModConfig.potionHudY;

                for (PotionEffect effect : effects) {
                    Potion potion = Potion.potionTypes[effect.getPotionID()];
                    String name = I18n.format(potion.getName());
                    if (effect.getAmplifier() == 1) {
                        name += " II";
                    } else if (effect.getAmplifier() == 2) {
                        name += " III";
                    } else if (effect.getAmplifier() == 3) {
                        name += " IV";
                    }

                    String duration = Potion.getDurationString(effect);
                    String displayText = name + ": " + duration;

                    int drawX = xPos;
                    if (MyModConfig.potionHudRightAlign) {
                        drawX = xPos - fr.getStringWidth(displayText);
                    }

                    if (MyModConfig.potionHudBackground) {
                        drawBackgroundBox(drawX - 2, yOffset - 1, fr.getStringWidth(displayText) + 4,
                                fr.FONT_HEIGHT + 1);
                    }

                    int color = MyModConfig.potionHudChroma ? getChromaColor(drawX, yOffset) : baseColor;

                    fr.drawStringWithShadow(displayText, drawX, yOffset, color);
                    yOffset += lineHeight;
                }
            }
        }
    }

    private void drawKey(FontRenderer fr, String text, int x, int y, int width, int height, boolean pressed) {
        if (MyModConfig.keystrokesBackground) {
            drawBackgroundBox(x, y, width, height);
        }

        int color;
        if (pressed) {
            color = MyModConfig.keystrokesActivatedColorInt;
        } else if (MyModConfig.keystrokesChroma) {
            color = getChromaColor(x, y);
        } else {
            color = MyModConfig.keystrokesColorInt;
        }

        int textX = x + (width - fr.getStringWidth(text)) / 2;
        int textY = y + (height - fr.FONT_HEIGHT) / 2 + 1;

        fr.drawStringWithShadow(text, textX, textY, color);
    }

    // chroma calc
    private int getChromaColor(int x, int y) {
        long speed = 2000;
        float hue = (float) ((System.currentTimeMillis() + (x + y) * 10) % speed) / speed;
        return Color.getHSBColor(hue, 1.0f, 1.0f).getRGB();
    }

    private void drawBackgroundBox(int x, int y, int width, int height) {
        int backgroundColor = 0x90000000;
        Gui.drawRect(x, y, x + width, y + height, backgroundColor);
    }

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