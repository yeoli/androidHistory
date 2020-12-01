package com.example.yeol.jolup1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

/**
 * Created by yeol on 2019-04-04.
 */

public class FunctionTab extends AppCompatActivity {
    TextView txt1le, txt1ri, year, balle, balri,th_lf, th_lm, th_lb,th_rf, th_rm,th_rb,sup_le, sup_ri;
    int x, y, d = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functiontab);

        th_lf = (TextView) findViewById(R.id.th_lf);
        th_lm = (TextView) findViewById(R.id.th_lm);
        th_lb = (TextView) findViewById(R.id.th_lb);
        th_rf = (TextView) findViewById(R.id.th_rf);
        th_rm = (TextView) findViewById(R.id.th_rm);
        th_rb = (TextView) findViewById(R.id.th_rb);
        balle = (TextView) findViewById(R.id.balle);
        balri = (TextView) findViewById(R.id.balri);
        sup_le = (TextView) findViewById(R.id.sup_le);
        sup_ri = (TextView) findViewById(R.id.sup_ri);
        year = (TextView) findViewById(R.id.year);


        Intent intent = new Intent(this.getIntent());
        final String idd = intent.getStringExtra("id");
        final String check_date = intent.getStringExtra("check_date");


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    int index = jsonResponse.getInt("i");
                    JSONArray arrDateList = jsonResponse.getJSONArray("dateList");

                    if (success) {
                        switch (index) {
                            //양발균형
                            case 1:

                                int sum_l = 0;
                                int sum_r = 0;
                                int i;

                                for (i = 0; i < arrDateList.length(); i++) {
                                    JSONObject date = arrDateList.getJSONObject(i);

                                    int t = date.getInt("bal_l");
                                    int d = date.getInt("bal_r");
                                    sum_l += t;
                                    sum_r += d;

                                }

                                balle.setText(String.valueOf(sum_l / i));
                                balri.setText(String.valueOf(sum_r / i));
                                break;

                            //3박자
                            case 2:

                                int sum_lf = 0;
                                int sum_lm = 0;
                                int sum_lb = 0;
                                int sum_rf = 0;
                                int sum_rm = 0;
                                int sum_rb = 0;
                                int coL = 0;
                                int coR = 0;
                                int i1;

                                for (i1 = 0; i1 < arrDateList.length(); i1++) {
                                    JSONObject date = arrDateList.getJSONObject(i1);
                                    int th_f = date.getInt("front");
                                    int th_m = date.getInt("middle");
                                    int th_b = date.getInt("back");
                                    int count = date.getInt("count");


                                    if (count == 0) {
                                        sum_lf += th_f;
                                        sum_lm += th_m;
                                        sum_lb += th_b;
                                        coL += 1;
                                    } else {
                                        sum_rf += th_f;
                                        sum_rm += th_m;
                                        sum_rb += th_b;
                                        coR += 1;
                                    }


                                }

                                th_lf.setText(String.valueOf(sum_lf/coL));
                                th_lm.setText(String.valueOf(sum_lm/coL));
                                th_lb.setText(String.valueOf(sum_lb/coL));
                                th_rf.setText(String.valueOf(sum_rf/coR));
                                th_rm.setText(String.valueOf(sum_rm/coR));
                                th_rb.setText(String.valueOf(sum_rb/coR));

                                break;

                            //지지분포
                              case 3:

                                //order_s를 string으로 간주함.

                                int numl = 0;
                                int numl1 = 0;
                                int numl2 = 0;
                                int numr = 0;
                                int numr1 = 0;
                                int numr2 = 0;

                                int i2;

                                for (i2 = 0; i2 < arrDateList.length(); i2++) {
                                    JSONObject date = arrDateList.getJSONObject(i2);

                                    String shun = date.getString("order_s");
                                    int count_s = date.getInt("count_s");


                                    if (count_s == 0 && shun.equals("4321")) {
                                        numl += 1;
                                    } else if (count_s == 0 && shun.equals("4213")) {
                                        numl1 += 1;//(안쪽)
                                    } else if (count_s == 0 && shun.equals("4312")) {
                                        numl2 += 1;//(바깥)
                                    } else if (count_s == 1 && shun.equals("4321")) {
                                        numr += 1;
                                    } else if (count_s == 1 && shun.equals("4213")) {
                                        numr1 += 1;//(안쪽)
                                    } else {
                                        numr2 += 1;//(바깥)
                                    }
                                }

                                if (numl >= numl1 && numl>= numl2)
                                    sup_le.setText("일반디딤으로 정상입니다.");
                                else if (numl1 >numl2)
                                    sup_le.setText("안쪽 디딤입니다.");
                                else
                                    sup_le.setText("바깥 디딤입니다.");

                                if (numr >= numr1 && numr>= numr2)
                                    sup_ri.setText("일반디딤으로 정상입니다.");
                                else if (numr1 >numr2)
                                    sup_ri.setText("안쪽 디딤입니다.");
                                else
                                    sup_ri.setText("바깥 디딤입니다.");
                                break;
                        }
                    }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                } ;


        SupgetRequest supgetRequest = new SupgetRequest(idd, check_date, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FunctionTab.this);
        queue.add(supgetRequest);

        BalgetRequest balgetRequest = new BalgetRequest(idd, check_date, responseListener);
        queue.add(balgetRequest);

        ThregetRequest thregetRequest = new ThregetRequest(idd, check_date, responseListener);
        queue.add(thregetRequest);

        year.setText(check_date);

        final TabHost tabHost = (TabHost)findViewById(R.id.tabhostt2);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("1").setIndicator("균형");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("2").setIndicator("삼박자");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("3").setIndicator("지지분포");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);

      /*  TabHost.TabSpec tab4 = tabHost.newTabSpec("4").setIndicator("3박자");
        tab4.setContent(R.id.tab4);
        tabHost.addTab(tab4);

        TabHost.TabSpec tab5 = tabHost.newTabSpec("5").setIndicator("지지분포");
        tab5.setContent(R.id.tab5);
        tabHost.addTab(tab5);*/

    }

    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearr, int month, int dayOfMonth) {
              x = yearr;
               y = month;
               d = dayOfMonth;

               year.setText(String.format("%d년 %d월 %d일", yearr, month+1, dayOfMonth));


            }
        }, 2019, 1, 11);

        datePickerDialog.setMessage("달력");
        datePickerDialog.show();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
               //Intent intent = new Intent(FunctionTab.this, LoginActivity.class);
                //startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
