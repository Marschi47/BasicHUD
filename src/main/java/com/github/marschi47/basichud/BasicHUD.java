package com.github.marschi47.basichud;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "basichud", useMetadata=true)
public class BasicHUD {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new HudRenderer());

        //CPS Tracker
        CpsTracker tracker = new CpsTracker();
        MinecraftForge.EVENT_BUS.register(tracker); // mouse input
        FMLCommonHandler.instance().bus().register(tracker); // client ticks
    }
}
