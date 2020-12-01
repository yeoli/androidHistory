package com.example.yeol.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeol on 2019-02-07.
 */

public class S2taticActivity extends MainActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2tatic);

        LineChart lineChart = (LineChart) findViewById(R.id.chart);

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

        LineDataSet setComp = new LineDataSet(entries, "시간대별");
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


    }

}


