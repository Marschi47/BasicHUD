package com.github.marschi47.basichud;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
        modid = "basichud",
        useMetadata=true,
        guiFactory = "com.github.marschi47.basichud.GuiFactory"
)
public class BasicHUD {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // config file
        MyModConfig.init(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // main mod class for ConfigChangedEvent
        MinecraftForge.EVENT_BUS.register(this);

        // HudRenderer instance
        MinecraftForge.EVENT_BUS.register(new HudRenderer());

        // CpsTracker instance
        MinecraftForge.EVENT_BUS.register(new CpsTracker());

        // keystrokes
        MinecraftForge.EVENT_BUS.register(new KeystrokesTracker());
    }

    // auto when "Done" button is clicked
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        // for this mod
        if (event.modID.equals("basichud")) {
            // reload the config
            MyModConfig.loadConfig();
        }
    }
}
