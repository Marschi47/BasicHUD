package com.github.marschi47.basichud;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class MyModConfig {
    public static Configuration config;

    // FPS Settings
    public static boolean fpsEnabled = true;
    public static boolean fpsRightAlign = false;
    public static int fpsX = 10;
    public static int fpsY = 10;
    public static String fpsColor = "FFFFFF";
    public static boolean fpsChroma = false;
    public static boolean fpsBackground = false;

    // Ping Settings
    public static boolean pingEnabled = true;
    public static boolean pingRightAlign = false;
    public static int pingX = 10;
    public static int pingY = 22;
    public static String pingColor = "FFFFFF";
    public static boolean pingChroma = false;
    public static boolean pingBackground = false;

    // CPS Settings
    public static boolean cpsEnabled = true;
    public static boolean cpsRightAlign = false;
    public static int cpsX = 10;
    public static int cpsY = 34;
    public static String cpsColor = "FFFFFF";
    public static boolean cpsChroma = false;
    public static boolean cpsBackground = false;

    // keystrokes settings
    public static boolean keystrokesEnabled = true;
    public static String keystrokesColor = "FFFFFF";
    public static boolean keystrokesRightAlign = true;
    public static int keystrokesX = 5;
    public static int keystrokesY = 10;
    public static String keystrokesActivatedColor = "00FF00";
    public static boolean keystrokesChroma = false;
    public static boolean keystrokesBackground = false;

    //potion settings
    public static boolean potionHudEnabled = true;
    public static boolean potionHudVerticalCenter = true;
    public static int potionHudX = 10;
    public static int potionHudY = 100;
    public static String potionHudColor = "FFFFFF";
    public static boolean potionHudChroma = false;
    public static boolean potionHudBackground = false;

    public static void init(File configFile) {
        config = new Configuration(configFile);
        loadConfig();
    }

    public static void loadConfig() {
        try {
            // FPS Category
            fpsEnabled = config.getBoolean("fpsEnabled", "HUD_FPS", true, "If FPS is shown");
            fpsRightAlign = config.getBoolean("fpsRightAlign", "HUD_FPS", false, "Align to the right");
            fpsX = config.getInt("fpsX", "HUD_FPS", 10, 0, 1000, "X Position of FPS");
            fpsY = config.getInt("fpsY", "HUD_FPS", 10, 0, 1000, "Y Position of FPS");
            fpsColor = config.getString("fpsColor", "HUD_FPS", "FFFFFF", "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");
            fpsChroma = config.getBoolean("fpsChroma", "HUD_FPS", false, "Enable RGB/Chroma Effect");
            fpsBackground = config.getBoolean("fpsBackground", "HUD_FPS", false, "Enable Background");

            // Ping Category
            pingEnabled = config.getBoolean("pingEnabled", "HUD_PING", true, "If Ping is shown");
            pingRightAlign = config.getBoolean("pingRightAlign", "HUD_PING", false, "Align to the right");
            pingX = config.getInt("pingX", "HUD_PING", 10, 0, 1000, "X Position of Ping");
            pingY = config.getInt("pingY", "HUD_PING", 22, 0, 1000, "Y Position of Ping");
            pingColor = config.getString("pingColor", "HUD_PING", "FFFFFF", "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");
            pingChroma = config.getBoolean("pingChroma", "HUD_PING", false, "Enable RGB/Chroma Effect");
            pingBackground = config.getBoolean("pingBackground", "HUD_PING", false, "Enable Background");

            // CPS Category
            cpsEnabled = config.getBoolean("cpsEnabled", "HUD_CPS", true, "If CPS is shown");
            cpsRightAlign = config.getBoolean("cpsRightAlign", "HUD_CPS", false, "Align to the right");
            cpsX = config.getInt("cpsX", "HUD_CPS", 10, 0, 1000, "X Position of CPS");
            cpsY = config.getInt("cpsY", "HUD_CPS", 34, 0, 1000, "Y Position of CPS");
            cpsColor = config.getString("cpsColor", "HUD_CPS", "FFFFFF", "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");
            cpsChroma = config.getBoolean("cpsChroma", "HUD_CPS", false, "Enable RGB/Chroma Effect");
            cpsBackground = config.getBoolean("cpsBackground", "HUD_CPS", false, "Enable Background");

            // keystrokes
            keystrokesEnabled = config.getBoolean("KeystrokesEnabled", "HUD_Keystrokes", true, "If Keystrokes are shown");
            keystrokesColor = config.getString("keystrokesColor", "HUD_Keystrokes", "FFFFFF", "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");
            keystrokesRightAlign = config.getBoolean("keystrokesRightAlign", "HUD_Keystrokes", true, "Align to the right side of the screen");
            keystrokesX = config.getInt("keystrokesX", "HUD_Keystrokes", 5, 0, 1000, "X Position (or margin from right)");
            keystrokesY = config.getInt("keystrokesY", "HUD_Keystrokes", 10, 0, 1000, "Y Position");
            keystrokesActivatedColor = config.getString("keystrokesActivatedColor", "HUD_Keystrokes", "00FF00", "Hex color code (Red: FF0000, Blue: 0000FF, Green: 00FF00)");
            keystrokesChroma = config.getBoolean("keystrokesChroma", "HUD_Keystrokes", false, "Enable RGB/Chroma Effect");
            keystrokesBackground = config.getBoolean("keystrokesBackground", "HUD_Keystrokes", false, "Enable Background");

            //potions
            potionHudEnabled = config.getBoolean("potionHudEnabled", "HUD_POTIONS", true, "If Active Potions are shown");
            potionHudVerticalCenter = config.getBoolean("potionHudVerticalCenter", "HUD_POTIONS", true, "Auto-Center Vertically");
            potionHudX = config.getInt("potionHudX", "HUD_POTIONS", 10, 0, 1000, "X Position of Potion Effect HUD");
            potionHudY = config.getInt("potionHudY", "HUD_POTIONS", 100, 0, 1000, "Y Position of Potion Effect HUD");
            potionHudColor = config.getString("potionHudColor", "HUD_POTIONS", "FFFFFF", "Hex color code");
            potionHudChroma = config.getBoolean("potionHudChroma", "HUD_POTIONS", false, "Enable RGB/Chroma Effect");
            potionHudBackground = config.getBoolean("potionHudBackground", "HUD_POTIONS", false, "Enable Background");
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