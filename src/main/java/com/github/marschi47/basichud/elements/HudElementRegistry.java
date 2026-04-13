package com.github.marschi47.basichud.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HudElementRegistry {

    private static final List<HudElement> elementList = new ArrayList<>();

    public static void init() {
        if (elementList.isEmpty()) {
            elementList.add(new FpsElement());
            elementList.add(new PingElement());
            elementList.add(new CpsElement());
            elementList.add(new KeystrokesElement());
            elementList.add(new PotionElement());
        }
    }

    public static List<HudElement> getElements() {
        return Collections.unmodifiableList(elementList);
    }

    public static HudElement getById(String id) {
        for (HudElement el : elementList) {
            if (el.getId().equals(id)) {
                return el;
            }
        }
        return null;
    }
}
