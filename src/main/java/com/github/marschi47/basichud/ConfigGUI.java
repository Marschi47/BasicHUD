package com.github.marschi47.basichud;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ConfigGUI extends GuiConfig {

    public ConfigGUI(GuiScreen parentScreen) {
        super(
                parentScreen,
                getConfigElements(),
                "basichud",
                false,
                false,
                "BasicHUD Configuration"
        );
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();
        Configuration cfg = MyModConfig.config;

        // FPS list
        List<IConfigElement> fpsList = new ArrayList<>();
        fpsList.add(new ConfigElement(cfg.get("HUD_FPS", "fpsEnabled", true, "If FPS should be displayed")));
        fpsList.add(new ConfigElement(cfg.get("HUD_FPS", "fpsX", 10, "X Axis for FPS Display (0 is left)")));
        fpsList.add(new ConfigElement(cfg.get("HUD_FPS", "fpsY", 10, "Y Axis for FPS Display (0 is top)")));
        fpsList.add(new ConfigElement(cfg.get("HUD_FPS", "fpsColor", "FFFFFF", "Color of FPS Display (Hex Code)")));

        // FPS button
        list.add(new DummyConfigElement.DummyCategoryElement("FPS Settings", "basichud.config.fps", fpsList));

        // ping list
        List<IConfigElement> pingList = new ArrayList<>();
        pingList.add(new ConfigElement(cfg.get("HUD_PING", "pingEnabled", true, "If Ping should be displayed")));
        pingList.add(new ConfigElement(cfg.get("HUD_PING", "pingX", 10, "X Axis for Ping Display (0 is left)")));
        pingList.add(new ConfigElement(cfg.get("HUD_PING", "pingY", 22, "Y Axis for Ping Display (0 is top)")));
        pingList.add(new ConfigElement(cfg.get("HUD_PING", "pingColor", "FFFFFF", "Color of Ping Display (Hex Code)")));

        // ping button
        list.add(new DummyConfigElement.DummyCategoryElement("Ping Settings", "basichud.config.ping", pingList));

        // CPS list
        List<IConfigElement> cpsList = new ArrayList<>();
        cpsList.add(new ConfigElement(cfg.get("HUD_CPS", "cpsEnabled", true, "If CPS should be displayed")));
        cpsList.add(new ConfigElement(cfg.get("HUD_CPS", "cpsX", 10, "X Axis for CPS Display (0 is left)")));
        cpsList.add(new ConfigElement(cfg.get("HUD_CPS", "cpsY", 34, "Y Axis for CPS Display (0 is top)")));
        cpsList.add(new ConfigElement(cfg.get("HUD_CPS", "cpsColor", "FFFFFF", "Color of CPS Display (Hex Code)")));

        // CPS button
        list.add(new DummyConfigElement.DummyCategoryElement("CPS Settings", "basichud.config.cps", cpsList));

        // keystrokes list
        List<IConfigElement> keyList = new ArrayList<>();
        keyList.add(new ConfigElement(cfg.get("HUD_Keystrokes", "KeystrokesEnabled", true, "If Keystrokes should be displayed")));
        keyList.add(new ConfigElement(cfg.get("HUD_Keystrokes", "keystrokesColor", "FFFFFF", "Color of Keystrokes Display (Hex Code)")));
        keyList.add(new ConfigElement(cfg.get("HUD_Keystrokes", "keystrokesActivatedColor", "00FF00", "Color of Keystrokes Display when activated (Hex Code)")));

        // keysrtokes button
        list.add(new DummyConfigElement.DummyCategoryElement("Keystrokes Settings", "basichud.config.keystrokes", keyList));

        return list;
    }
}