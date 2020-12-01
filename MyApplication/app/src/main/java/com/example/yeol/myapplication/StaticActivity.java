package com.example.yeol.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by yeol on 2019-01-22.
 */

@SuppressWarnings("deprecation")
public class StaticActivity extends MainActivity {
    TextView txt1le,txt1ri,txt2le,txt2ri,year,month,day;
    ImageView ta1,img1le,img1ri,img2ri,img2le,imgbe,imgaf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        txt1le = (TextView) findViewById(R.id.txt1le);
        txt1ri = (TextView) findViewById(R.id.txt1ri);
        txt2le = (TextView) findViewById(R.id.txt2le);
        txt2ri = (TextView) findViewById(R.id.txt2ri);
        year = (TextView) findViewById(R.id.year);
        month = (TextView) findViewById(R.id.month);
        day = (TextView) findViewById(R.id.day);
        ta1 = (ImageView) findViewById(R.id.ta1);
        imgbe = (ImageView) findViewById(R.id.imgbe);
        imgaf = (ImageView) findViewById(R.id.imgaf);
        final Calendar calendar1 = Calendar.getInstance();



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("1").setIndicator("균형");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("2").setIndicator("보폭");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("3").setIndicator("발각도");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("4").setIndicator("3박자");
        tab4.setContent(R.id.tab4);
        tabHost.addTab(tab4);

        TabHost.TabSpec tab5 = tabHost.newTabSpec("5").setIndicator("지지분포");
        tab5.setContent(R.id.tab5);
        tabHost.addTab(tab5);

        tabHost.setCurrentTab(1);

        imgbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                year.setText(Integer.toString(calendar1.get(Calendar.YEAR)));
                month.setText(Integer.toString(calendar1.get(Calendar.MONTH)+1));
                day.setText(Integer.toString(calendar1.get(Calendar.DATE)-1));



            }
        });

        imgaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar1 = Calendar.getInstance();

                year.setText(Integer.toString(calendar1.get(Calendar.YEAR)));
                month.setText(Integer.toString(calendar1.get(Calendar.MONTH)+1));
                day.setText(Integer.toString(calendar1.get(Calendar.DATE)+1));

            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(StaticActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

