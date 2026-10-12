package com.github.marschi47.basichud;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CpsTracker {

    // final cps count
    public static int leftCps = 0;
    public static int rightCps = 0;

    private static final int BUFFER_SIZE = 100;
    private static final long[] lClicks = new long[BUFFER_SIZE];
    private static final long[] rClicks = new long[BUFFER_SIZE];
    private static int lHead = 0;
    private static int lTail = 0;
    private static int lCount = 0;
    private static int rHead = 0;
    private static int rTail = 0;
    private static int rCount = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            long currentTime = System.currentTimeMillis();

            // if click add to queue
            if (MouseInputHandler.leftClickedThisTick) {
                if (lCount == BUFFER_SIZE) {
                    lHead = (lHead + 1) % BUFFER_SIZE;
                    lCount--;
                }
                lClicks[lTail] = currentTime;
                lTail = (lTail + 1) % BUFFER_SIZE;
                lCount++;
                MouseInputHandler.leftClickedThisTick = false; // reset flag
            }
            if (MouseInputHandler.rightClickedThisTick) {
                if (rCount == BUFFER_SIZE) {
                    rHead = (rHead + 1) % BUFFER_SIZE;
                    rCount--;
                }
                rClicks[rTail] = currentTime;
                rTail = (rTail + 1) % BUFFER_SIZE;
                rCount++;
                MouseInputHandler.rightClickedThisTick = false; // reset flag
            }

            // older than 1 sec gets removed
            while (lCount > 0 && lClicks[lHead] < currentTime - 1000) {
                lHead = (lHead + 1) % BUFFER_SIZE;
                lCount--;
            }

            while (rCount > 0 && rClicks[rHead] < currentTime - 1000) {
                rHead = (rHead + 1) % BUFFER_SIZE;
                rCount--;
            }

            // set final count
            leftCps = lCount;
            rightCps = rCount;
        }
    }
}