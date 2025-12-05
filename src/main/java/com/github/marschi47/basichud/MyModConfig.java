package com.github.marschi47.basichud;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class MyModConfig {
    public static Configuration config;

    // FPS Settings
    public static boolean fpsEnabled = true;
    public static int fpsX = 10;
    public static int fpsY = 10;
    public static String fpsColor = "FFFFFF"; // White

    // Ping Settings
    public static boolean pingEnabled = true;
    public static int pingX = 10;
    public static int pingY = 22;
    public static String pingColor = "FFFFFF";

    // CPS Settings
    public static boolean cpsEnabled = true;
    public static int cpsX = 10;
    public static int cpsY = 34;
    public static String cpsColor = "FFFFFF";

    public static void init(File configFile) {
        config = new Configuration(configFile);
        loadConfig();
    }

    public static void loadConfig() {
        try {
            // FPS Category
            fpsEnabled = config.getBoolean("fpsEnabled", "HUD_FPS", true, "If FPS is shown");
            fpsX = config.getInt("fpsX", "HUD_FPS", 10, 0, 1000, "X Position of FPS");
            fpsY = config.getInt("fpsY", "HUD_FPS", 10, 0, 1000, "Y Position of FPS");
            fpsColor = config.getString("fpsColor", "HUD_FPS", "FFFFFF", "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");

            // Ping Category
            pingEnabled = config.getBoolean("pingEnabked", "HUD_PING", true, "If Ping is shown");
            pingX = config.getInt("pingX", "HUD_PING", 10, 0, 1000, "X Position of Ping");
            pingY = config.getInt("pingY", "HUD_PING", 22, 0, 1000, "Y Position of Ping");
            pingColor = config.getString("pingColor", "HUD_PING", "FFFFFF", "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");

            // CPS Category
            cpsEnabled = config.getBoolean("cpsEnabled", "HUD_CPS", true, "If CPS is shown");
            cpsX = config.getInt("cpsX", "HUD_CPS", 10, 0, 1000, "X Position of CPS");
            cpsY = config.getInt("cpsY", "HUD_CPS", 34, 0, 1000, "Y Position of CPS");
            cpsColor = config.getString("cpsColor", "HUD_CPS", "FFFFFF", "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");

        } catch (Exception e) {
            System.err.println("Error loading config: " + e.getMessage());
        } finally {
            // in-memory to disk file
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}