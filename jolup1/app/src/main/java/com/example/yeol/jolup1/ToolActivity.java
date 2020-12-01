package com.example.yeol.jolup1;

import android.content.ClipData;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;


/**
 * Created by yeol on 2019-04-05.
 */

public class ToolActivity extends AppCompatActivity {

    Toolbar tb = (Toolbar) findViewById(R.id.mtool_bar) ;
       ActionBar ab = getSupportActionBar() ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu) ;
        return  true;
        }

    }
