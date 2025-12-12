package com.github.marschi47.basichud;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeystrokesTracker {

    public static boolean wPressed = false;
    public static boolean aPressed = false;
    public static boolean sPressed = false;
    public static boolean dPressed = false;
    public static boolean spacePressed = false;
    public static boolean shiftPressed = false;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        int key = Keyboard.getEventKey();

        boolean state = Keyboard.getEventKeyState();

        if (key == Keyboard.KEY_W) {
            wPressed = state;
        } else if (key == Keyboard.KEY_A) {
            aPressed = state;
        } else if (key == Keyboard.KEY_S) {
            sPressed = state;
        } else if (key == Keyboard.KEY_D) {
            dPressed = state;
        } else if (key == Keyboard.KEY_LSHIFT) {
            shiftPressed = state;
        } else if (key == Keyboard.KEY_SPACE) {
            spacePressed = state;
        }
    }

    public static boolean leftClickPressed() {
        return MouseInputHandler.leftClickState;
    }

    public static boolean rightClickPressed() {
        return MouseInputHandler.rightClickState;
    }
}