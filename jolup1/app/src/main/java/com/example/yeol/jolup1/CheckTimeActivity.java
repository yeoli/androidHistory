package com.example.yeol.jolup1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by demm on 2019-06-09.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.InterruptedByTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.os.SystemClock.elapsedRealtime;

/**
 * Created by yeol on 2019-04-17.
 */

public class CheckTimeActivity extends AppCompatActivity {
    private boolean running;
    private long pauseoffset;
    private AlertDialog dialog;
    String idd;
    int i;
    long ct, ctmin, cthour, ctsecond;
    int total; // 누적된 오늘 측정 시간
    int ch, cm, cs, cr;


    String t;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checktime);

        final Intent intent = getIntent();
        idd = intent.getStringExtra("id");


        final ImageButton startcl = (ImageButton) findViewById(R.id.startcl);
        final ImageButton startpause = (ImageButton) findViewById(R.id.startpause);
        final Chronometer chrono1 = (Chronometer) findViewById(R.id.chrono1);
        Button origin = (Button) findViewById(R.id.origin);
        ImageButton nextcl = (ImageButton) findViewById(R.id.nextcl);
        chrono1.setBase(elapsedRealtime());
        final TextView hep = (TextView) findViewById(R.id.hep); // 현재 날짜
        final TextView ti = (TextView) findViewById(R.id.ti); // 측정된 시간
        final TextView hep1 = (TextView) findViewById(R.id.hep1);
        final TextView hep2 = (TextView) findViewById(R.id.hep2);
        final TextView hep3 = (TextView) findViewById(R.id.hep3);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        startcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running)
                    chrono1.setBase(elapsedRealtime() - pauseoffset);
                chrono1.start();
                running = true;
                chrono1.setTextColor(Color.GREEN);
                hep.setVisibility(view.INVISIBLE);

                Toast.makeText(getApplicationContext(), "측정을 시작합니다.", Toast.LENGTH_LONG).show();
            }
        });

        startpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (running)

                    chrono1.stop();

                ct = (elapsedRealtime() - chrono1.getBase()) / 1000;

                ctmin = ct / 60; //크로노미터 변수 : 분
                ctsecond = ct - (ctmin * 60); //크로노미터 변수 : 초
                cthour = ctmin / 60; //크로노미터 변수 : 시간

                String rr = (cthour + ":" + ctmin + ":" + ctsecond);
                ti.setText(rr);

              /*  total_hep1 += cthour;
                hep1.setText(String.valueOf(total_hep1));
                total_hep2 += ctmin;
                hep2.setText(String.valueOf(total_hep2));
                total_hep3 += ctsecond;
                hep3.setText(String.valueOf(total_hep3)); */

                //같은 날짜인 측정시간 더해서 check_time 업데이트 하기!
                //시간변수 ch(시간), cm(분), cm(초)

                //ch = ch * 3600;
                //cm = cm * 60;

                // cr = ch + cm + cs;

               // ch = cr/3600;
               // cm = (cr/60)%60;
               // cs = (cs%60);

                //ti.setVisibility(view.INVISIBLE);

                pauseoffset = elapsedRealtime() - chrono1.getBase();
                running = false;
                chrono1.setTextColor(Color.BLACK);
                hep.setText(getTime());
              //  hep.setVisibility(view.INVISIBLE);
                Toast.makeText(getApplicationContext(), "측정을 멈췄습니다.", Toast.LENGTH_LONG).show();


            }
        });


        //측정 시간 초기화
        origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chrono1.setBase(elapsedRealtime());
                pauseoffset = 0;
            }
        });

        // 측정된 시간이랑 값을 DB에 넣는 코드
        nextcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String check_date = hep.getText().toString(); // 현재 날짜를 넣을 변수
                String check_t = ti.getText().toString(); // 측정된 시간을 넣을 변수
                String userID = idd;


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            int j = jsonResponse.getInt("i");
                            if(j==1){
                                //중복 확인 테이블
                                if(success){
                                    i = 2;
                                    String time = jsonResponse.getString("check_t");
                                    StringTokenizer st = new StringTokenizer(time, ":");            //아두이노 스캐치에서 받아온 스트링 값 ','으로 구분
                                    ArrayList hms = new ArrayList();
                                    while (st.hasMoreTokens()) {
                                        hms.add(st.nextToken());
                                    }
                                    int h = Integer.parseInt((String)hms.get(0));
                                    int m = Integer.parseInt((String)hms.get(1));
                                    int s = Integer.parseInt((String)hms.get(2));

                                   // Log.e("크르노1",String.valueOf(cthour));
                                   // Log.e("크르노2",String.valueOf(ctmin));
                                    //Log.e("크르노3",String.valueOf(ctsecond));


                                    h += (int) cthour;
                                    m += (int) ctmin;
                                    s += (int) ctsecond;

                                    //같은 날짜인 측정시간 더해서 check_time 업데이트 하기!
                                    //시간변수 ch(시간), cm(분), cm(초)

                                    h = h * 3600;
                                    m = m * 60;

                                    total = h + m + s;

                                    h = total/3600;
                                    m = (total/60)%60;
                                    s = (total%60);

                                    Log.e("dddd",String.valueOf(s));

/*
                                    hep1.setText(String.valueOf(h));
                                    h
                                    h = ch * 3600;
                                    m = cm * 60;

                                    total = ch + cm + cs;

                                    h = total/3600;
                                    m = (total/60)%60;
                                    s = (total%60);*/

                                    t = String.valueOf(h)+":"+String.valueOf(m)+":"+String.valueOf(s);

                                }else{
                                    i = 1;
                                }

                            }else if(j==2){
                                //테이블 갱신
                                AlertDialog.Builder builder = new AlertDialog.Builder(CheckTimeActivity.this);
                                dialog = builder.setMessage("메인화면으로 돌아갑니다.\n(두번째 탭에서 확인하세요.)")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(CheckTimeActivity.this, FiveTabActivity.class);
                                                intent.putExtra("id",idd);
                                                startActivityForResult(intent,200);
                                                finish();
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }else{
                                //테이블 추가
                                AlertDialog.Builder builder = new AlertDialog.Builder(CheckTimeActivity.this);
                                dialog = builder.setMessage("메인화면으로 돌아갑니다.\n(두번째 탭에서 확인하세요.)")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(CheckTimeActivity.this, FiveTabActivity.class);
                                                intent.putExtra("id",idd);
                                                startActivityForResult(intent,200);
                                                finish();
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(CheckTimeActivity.this);
                switch (i){
                    case 0:
                        ListCheckRequest listCheckRequest = new ListCheckRequest(check_date, userID,responseListener);
                        queue.add(listCheckRequest);
                    case 1:
                        if(i==1) {
                            CheckListRequest checkListRequest = new CheckListRequest(check_date, check_t, userID, responseListener);
                            queue.add(checkListRequest);
                        }
                    case 2:
                        if(i==2){
                            ListtimeUpdateRequest listtimeUpdateRequest = new ListtimeUpdateRequest(userID,t,check_date,responseListener);
                            queue.add(listtimeUpdateRequest);
                        }
                }
            }
        });


              /*  Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CheckTimeActivity.this);
                                dialog = builder.setMessage("메인화면으로 돌아갑니다.\n(두번째 탭에서 확인하세요.)")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(CheckTimeActivity.this, FiveTabActivity.class);
                                                intent.putExtra("id", idd);
                                                startActivityForResult(intent, 200);
                                                // CheckTimeActivity.this.startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create();
                                dialog.show();

                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                CheckListRequest checkListRequest = new CheckListRequest(check_date, check_t, userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CheckTimeActivity.this);
                queue.add(checkListRequest);

            }
        });
        */

    }


    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                // Intent intent = new Intent(CheckTimeActivity.this, FiveTabActivity.class);
                // intent.putExtra("id",idd);
                // startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}