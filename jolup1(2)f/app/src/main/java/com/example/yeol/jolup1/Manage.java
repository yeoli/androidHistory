package com.example.yeol.jolup1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by yeol on 2019-05-28.
 */

public class Manage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);
        TextView ad = (TextView) findViewById(R.id.ad);
        Intent intent = getIntent();
        ad.setText(intent.getStringExtra("checkList"));

        }
    }
