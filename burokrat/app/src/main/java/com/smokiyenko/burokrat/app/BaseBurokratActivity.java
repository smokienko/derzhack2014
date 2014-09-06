package com.smokiyenko.burokrat.app;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by s.mokiyenko on 9/6/14.
 */
public class BaseBurokratActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        BurokratApplication.getEventBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        BurokratApplication.getEventBus().unregister(this);
    }
}
