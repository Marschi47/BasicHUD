package com.github.marschi47.basichud;

import com.github.marschi47.basichud.elements.HudElement;
import com.github.marschi47.basichud.elements.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

        for (HudElement element : HudElementRegistry.getElements()) {
            if (element.enabled) {
                int drawX = element.resolveX(screenWidth);
                int drawY = element.resolveY(screenHeight);
                element.render(fr, drawX, drawY, screenWidth, screenHeight);
            }
        }
    }
}