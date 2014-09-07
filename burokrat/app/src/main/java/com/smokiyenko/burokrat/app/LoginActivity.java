package com.smokiyenko.burokrat.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.greenrobot.event.EventBus;


public class LoginActivity extends BaseBurokratActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

        @SuppressWarnings("Unused")
        public void onEventMainThread(final SessionResponse session){
            BurokratApplication.getEventBus().unregister(this);
            BurokratApplication.getEventBus().postSticky(session);
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

    public void onLoginPressed(final View view) {
//        startActivity(new Intent(this, MainActivity.class));
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        TextView tvPassportId = (TextView) findViewById(R.id.login_passport_id);
        TextView tvPassportSeries = (TextView) findViewById(R.id.login_passport_series);
        BurokratApplication.getClient().login(tvPassportSeries.getText().toString() , tvPassportId.getText().toString());
    }
}
