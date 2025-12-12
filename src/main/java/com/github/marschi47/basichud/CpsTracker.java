package com.github.marschi47.basichud;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class CpsTracker {

    // final cps count
    public static int leftCps = 0;
    public static int rightCps = 0;
    private static final List<Long> rCLICKS = new ArrayList<>();
    private static final List<Long> lCLICKS = new ArrayList<>();



    // calc cps on FML event bus
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            long currentTime = System.currentTimeMillis();

            // if click add to list
            if (MouseInputHandler.leftClickedThisTick) {
                lCLICKS.add(currentTime);
                MouseInputHandler.leftClickedThisTick = false; // reset flag
            }
            if (MouseInputHandler.rightClickedThisTick) {
                rCLICKS.add(currentTime);
                MouseInputHandler.rightClickedThisTick = false; // reset flag
            }

            // remove older than 1sek
            rCLICKS.removeIf(time -> time < currentTime - 1000);
            lCLICKS.removeIf(time -> time < currentTime - 1000);

            // set final count
            leftCps = lCLICKS.size();
            rightCps = rCLICKS.size();
        }
    }
}