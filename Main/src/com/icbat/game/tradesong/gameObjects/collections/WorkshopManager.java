package com.icbat.game.tradesong.gameObjects.collections;

import com.icbat.game.tradesong.gameObjects.Workshop;

import java.util.LinkedList;

public class WorkshopManager {
    private LinkedList<Workshop> workshops = new LinkedList<Workshop>();
    private float timeCount = 0;
    private static final int TIME_PER_STEP = 30;

    public WorkshopManager() {
        workshops.add(new Workshop());
    }

    public void actOnEachWorkshop(float delta) {
        timeCount += delta;
        if (timeCount > TIME_PER_STEP) {
            timeCount -= TIME_PER_STEP;
            for (Workshop shop : workshops) {
                shop.doWork();
            }
        }
    }
}