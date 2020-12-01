package com.example.yeol.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

/**
 * Created by yeol on 2018-12-16.
 */

public class StartActivity extends MainActivity {

    Chronometer chrono1;
    Button starttime, finishtime, gorecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        chrono1 = (Chronometer) findViewById(R.id.chrono1);
        starttime = (Button) findViewById(R.id.starttime);
        finishtime = (Button) findViewById(R.id.finishtime);
        gorecord = (Button) findViewById(R.id.gorecord);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono1.setBase(SystemClock.elapsedRealtime());
                chrono1.start();
                chrono1.setTextColor(Color.RED);
            }
        });

        finishtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starttime.setEnabled(true);

                chrono1.stop();
                chrono1.setTextColor(Color.GREEN);

                Toast.makeText(getApplicationContext(), "측정을 시작합니다", Toast.LENGTH_LONG).show();


            }
        });



        gorecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, StaticActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(StartActivity.this, FiveTabActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
     }
}
