package com.icbat.game.tradesong.observation.notifications;

import com.icbat.game.tradesong.Items;
import com.icbat.game.tradesong.observation.Notification;

/***/
public class GatherNotification extends Notification {
    public GatherNotification(Items.Item contents) {
        super("GATHER", contents);
    }
}