package com.example.yeol.jolup1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by yeol on 2019-04-04.
 */

public class IntroActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent = new Intent (getApplicationContext(),LoginActivity.class);
               startActivity(intent);
               finish();

            }
        }, 2000);
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}


