package com.icbat.game.tradesong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.icbat.game.tradesong.observation.NotificationManager;
import com.icbat.game.tradesong.observation.notifications.DayEndedNotification;
import com.icbat.game.tradesong.observation.watchers.EndOfDayWatcher;

/**
 * @author icbat
 */
public class Clock extends NotificationManager {

    private static final int dayLengthInMinutes = 5;
    public static final int SECONDS_TO_MINUTE = 60;
    /** Current time in minutes. Initialized to 0 every time you startDay */
    private int currentTime = 0;
    private final Timer dayTimer = new Timer();

    public Clock() {
        this.addWatcher(new EndOfDayWatcher());
    }

    public void startDay() {
        Gdx.app.debug("clock","day started!");
        currentTime = 0;
        stop();
        dayTimer.start();
        dayTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.debug("clock says", "day ended!");
                notifyWatchers(new DayEndedNotification());
            }
        }, dayLengthInMinutes * SECONDS_TO_MINUTE);
        dayTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                currentTime += 1;
            }
        }, 0, SECONDS_TO_MINUTE, dayLengthInMinutes * SECONDS_TO_MINUTE);
        Tradesong.contractGenerator.makeDailyContracts();
    }

    public int getTime() {
        return currentTime;
    }

    public void stop() {
        dayTimer.stop();
        dayTimer.clear();

    }

    public void pause() {
        dayTimer.stop();
    }

    public void resume() {
        dayTimer.start();
    }

    public void scheduleNonRepeatingTask(Timer.Task task, int secondsIntheFuture) {
        dayTimer.scheduleTask(task, secondsIntheFuture);
    }
}