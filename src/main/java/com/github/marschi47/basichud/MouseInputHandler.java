package com.github.marschi47.basichud;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

public class MouseInputHandler {
    public static boolean leftClickState = false;
    public static boolean rightClickState = false;
    public static boolean leftClickedThisTick = false;
    public static boolean rightClickedThisTick = false;

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        // --- Input Handling (State Tracking) ---
        int button = Mouse.getEventButton();
        boolean state = Mouse.getEventButtonState();

        if (button == 0) { // left click
            leftClickState = state;
            if (state) {
                leftClickedThisTick = true; // for cps tracker
            }
        } else if (button == 1) { // right click
            rightClickState = state;
            if (state) {
                rightClickedThisTick = true; // for cps tracker
            }
        }
    }
}