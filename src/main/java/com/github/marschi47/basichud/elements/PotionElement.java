package com.github.marschi47.basichud.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;

import java.util.Collection;

public class PotionElement extends HudElement {

    public PotionElement() {
        super("potionHud", "HUD_POTIONS",
                true, 10, 100,
                false, false,
                "FFFFFF", false, false);
    }

    @Override
    public int getWidth() {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        return Math.max(fr.getStringWidth("Speed II: 1:30"),
                fr.getStringWidth("Jump Boost: 0:45"));
    }

    @Override
    public int getHeight() {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        return (fr.FONT_HEIGHT + 2) * 2;
    }

    @Override
    public int resolveX(int screenWidth) {
        // Potion element uses a slightly different alignment approach for right-align
        // (the original code used screenWidth - x, not screenWidth - x - width)
        return rightAlign ? screenWidth - x : x;
    }

    @Override
    public int resolveY(int screenHeight) {
        // For the editor, use estimated height
        return bottomAlign ? screenHeight - y - getHeight() : y;
    }

    @Override
    public void render(FontRenderer fr, int drawX, int drawY, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getMinecraft();
        Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();

        if (!effects.isEmpty()) {
            int lineHeight = fr.FONT_HEIGHT + 2;
            int totalListHeight = effects.size() * lineHeight;

            int xPos = rightAlign ? screenWidth - x : x;
            int yOffset = bottomAlign ? screenHeight - y - totalListHeight : y;

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

                int lineDrawX = xPos;
                if (rightAlign) {
                    lineDrawX = xPos - fr.getStringWidth(displayText);
                }

                if (background) {
                    drawBackgroundBox(lineDrawX - 2, yOffset - 1, fr.getStringWidth(displayText) + 4,
                            fr.FONT_HEIGHT + 1);
                }

                int renderColor = chroma ? getChromaColor(lineDrawX, yOffset) : colorInt;

                fr.drawStringWithShadow(displayText, lineDrawX, yOffset, renderColor);
                yOffset += lineHeight;
            }
        }
    }

    @Override
    public void renderPreview(FontRenderer fr, int drawX, int drawY) {
        fr.drawStringWithShadow("Speed II: 1:30", drawX, drawY, 0xFFFFFF);
        fr.drawStringWithShadow("Jump Boost: 0:45", drawX, drawY + fr.FONT_HEIGHT + 2, 0xFFFFFF);
    }

    @Override
    public void loadFromConfig(Configuration config) {
        enabled = config.getBoolean("potionHudEnabled", configCategory, defaultEnabled,
                "If Active Potions are shown");
        rightAlign = config.getBoolean("potionHudRightAlign", configCategory, defaultRightAlign,
                "Align to the right");
        bottomAlign = config.getBoolean("potionHudBottomAlign", configCategory, defaultBottomAlign,
                "Align to the bottom");
        x = config.getInt("potionHudX", configCategory, defaultX, 0, 10000, "X Position of Potion Effect HUD");
        y = config.getInt("potionHudY", configCategory, defaultY, 0, 10000, "Y Position of Potion Effect HUD");
        color = config.getString("potionHudColor", configCategory, defaultColor, "Hex color code");
        chroma = config.getBoolean("potionHudChroma", configCategory, defaultChroma, "Enable RGB/Chroma Effect");
        background = config.getBoolean("potionHudBackground", configCategory, defaultBackground, "Enable Background");
        colorInt = safeHexToInt(color);
    }

    @Override
    public void savePositionToConfig(Configuration config) {
        config.get(configCategory, "potionHudX", defaultX).set(x);
        config.get(configCategory, "potionHudY", defaultY).set(y);
        config.get(configCategory, "potionHudRightAlign", defaultRightAlign).set(rightAlign);
        config.get(configCategory, "potionHudBottomAlign", defaultBottomAlign).set(bottomAlign);
    }

    @Override
    public void saveToConfig(Configuration config) {
        config.get(configCategory, "potionHudEnabled", defaultEnabled).set(enabled);
        config.get(configCategory, "potionHudRightAlign", defaultRightAlign).set(rightAlign);
        config.get(configCategory, "potionHudBottomAlign", defaultBottomAlign).set(bottomAlign);
        config.get(configCategory, "potionHudX", defaultX).set(x);
        config.get(configCategory, "potionHudY", defaultY).set(y);
        config.get(configCategory, "potionHudColor", defaultColor).set(color);
        config.get(configCategory, "potionHudChroma", defaultChroma).set(chroma);
        config.get(configCategory, "potionHudBackground", defaultBackground).set(background);
    }
}
