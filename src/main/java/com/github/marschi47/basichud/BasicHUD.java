package com.github.marschi47.basichud;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(
        modid = "basichud",
        useMetadata=true,
        guiFactory = "com.github.marschi47.basichud.GuiFactory"
)
public class BasicHUD {
    public static KeyBinding openConfigKey;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // config file
        MyModConfig.init(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // main mod class
        MinecraftForge.EVENT_BUS.register(this);

        // HudRenderer
        MinecraftForge.EVENT_BUS.register(new HudRenderer());

        //Mouse Input Handler
        MinecraftForge.EVENT_BUS.register(new MouseInputHandler());

        // CpsTracker
        MinecraftForge.EVENT_BUS.register(new CpsTracker());

        // keystrokes
        MinecraftForge.EVENT_BUS.register(new KeystrokesTracker());

        // config keybind
        openConfigKey = new KeyBinding("Open Config", Keyboard.KEY_K, "BasicHUD");
        ClientRegistry.registerKeyBinding(openConfigKey);
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

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (openConfigKey.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new ConfigGUI(null));
        }
    }
}
