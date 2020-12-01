package com.example.yeol.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yeol on 2019-03-05.
 */

public class FiveTabActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fivetab);

        LineChart lineChart = (LineChart) findViewById(R.id.chartt);

        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        entries.add(new Entry(16f, 6));
        entries.add(new Entry(5f, 7));
        entries.add(new Entry(3f, 8));
        entries.add(new Entry(7f, 10));
        entries.add(new Entry(9f, 11));

        LineDataSet setComp = new LineDataSet(entries, "시간대");
        setComp.setColor(Color.BLACK);

        ArrayList<String> asdf = new ArrayList<String>();
        asdf.add("February");
        asdf.add("March");
        asdf.add("April");
        asdf.add("May");
        asdf.add("June");
        asdf.add("July");
        asdf.add("August");
        asdf.add("September");
        asdf.add("October");
        asdf.add("November");
        asdf.add("December");


        LineData lineData = new LineData(setComp);
        lineChart.setData(lineData);
        lineChart.invalidate();

        final TabHost tabHost = (TabHost) findViewById(R.id.tabhost2);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("1");
        tab1.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_home, null));
        tab1.setContent(R.id.tab11);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("2");
        tab2.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_history, null));
        tab2.setContent(R.id.tab22);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("3");
        tab3.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_start, null));
        tab3.setContent(R.id.tab33);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("4");
        tab4.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_linec, null));
        tab4.setContent(R.id.tab44);
        tabHost.addTab(tab4);

        TabHost.TabSpec tab5 = tabHost.newTabSpec("5");
        tab5.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_man, null));
        tab5.setContent(R.id.tab55);
        tabHost.addTab(tab5);

        tabHost.setCurrentTab(1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mtool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

      }

}