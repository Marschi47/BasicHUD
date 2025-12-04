package com.github.marschi47.basichud;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
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

    /**
     * finds all categories from config and adds them to gui
     */
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        // loop through every category name in config
        for (String categoryName : MyModConfig.config.getCategoryNames()) {

            // get category object
            net.minecraftforge.common.config.ConfigCategory category = MyModConfig.config.getCategory(categoryName);

            // add all properties from categories to the list
            list.addAll(new ConfigElement(category).getChildElements());
        }

        return list;
    }
}