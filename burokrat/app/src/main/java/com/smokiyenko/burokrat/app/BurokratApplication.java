package com.smokiyenko.burokrat.app;

import android.app.Application;

import de.greenrobot.event.EventBus;

/**
 * Created by s.mokiyenko on 9/6/14.
 */
public class BurokratApplication extends Application {

    private static EventBus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        eventBus = new EventBus();
    }

    public static EventBus getEventBus() {
        return eventBus;
    }
}
