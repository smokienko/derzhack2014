package com.smokiyenko.burokrat.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;



public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start Login BaseBurokratActivity
        final Handler handler = new Handler();
        final Runnable activityStartRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
            }
        } ;
        handler.postDelayed(activityStartRunnable,3000);
    }

}
