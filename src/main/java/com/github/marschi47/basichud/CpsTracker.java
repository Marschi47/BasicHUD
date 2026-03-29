package com.github.marschi47.basichud;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayDeque;
import java.util.Queue;

public class CpsTracker {

    // final cps count
    public static int leftCps = 0;
    public static int rightCps = 0;
    private static final Queue<Long> rCLICKS = new ArrayDeque<>();
    private static final Queue<Long> lCLICKS = new ArrayDeque<>();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            long currentTime = System.currentTimeMillis();

            // if click add to queue
            if (MouseInputHandler.leftClickedThisTick) {
                lCLICKS.add(currentTime);
                MouseInputHandler.leftClickedThisTick = false; // reset flag
            }
            if (MouseInputHandler.rightClickedThisTick) {
                rCLICKS.add(currentTime);
                MouseInputHandler.rightClickedThisTick = false; // reset flag
            }

            // older than 1 sec gets removed
            while (!lCLICKS.isEmpty() && lCLICKS.peek() < currentTime - 1000) {
                lCLICKS.poll();
            }

            while (!rCLICKS.isEmpty() && rCLICKS.peek() < currentTime - 1000) {
                rCLICKS.poll();
            }

            // set final count
            leftCps = lCLICKS.size();
            rightCps = rCLICKS.size();
        }
    }
}