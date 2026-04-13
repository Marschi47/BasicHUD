package com.github.marschi47.basichud;

import com.github.marschi47.basichud.elements.HudElement;
import com.github.marschi47.basichud.elements.HudElementRegistry;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class MyModConfig {
        public static Configuration config;

        public static void init(File configFile) {
                config = new Configuration(configFile);
                loadConfig();
        }

        public static void loadConfig() {
                try {
                        HudElementRegistry.init();
                        for (HudElement element : HudElementRegistry.getElements()) {
                                element.loadFromConfig(config);
                        }
                } catch (Exception e) {
                        System.err.println("Error loading config: " + e.getMessage());
                } finally {
                        if (config.hasChanged()) {
                                config.save();
                        }
                }
        }

        public static void resetToDefaults() {
                for (HudElement element : HudElementRegistry.getElements()) {
                        element.resetToDefaults();
                }

                // Remove all categories and re-save with defaults
                config.removeCategory(config.getCategory("hud_fps"));
                config.removeCategory(config.getCategory("hud_ping"));
                config.removeCategory(config.getCategory("hud_cps"));
                config.removeCategory(config.getCategory("hud_keystrokes"));
                config.removeCategory(config.getCategory("hud_potions"));
                config.save();

                // Re-load to recreate config entries with defaults
                loadConfig();
        }
}