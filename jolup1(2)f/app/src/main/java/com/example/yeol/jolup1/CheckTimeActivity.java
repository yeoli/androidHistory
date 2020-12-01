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

import org.json.JSONArray;
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
import com.github.mikephil.charting.data.BarEntry;

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
    int start = 0;
    private boolean running;
    private long pauseoffset;
    private AlertDialog dialog;
    String idd;

    ConnectThread BCL, BCR;
    BluetoothDevice BDL, BDR;

    BluetoothAdapter BA;

    int Lzero, Lfirst, Lsecond, Lthird;
    int Rzero, Rfirst, Rsecond, Rthird;
    double lhap, rhap;
    int lphap, rphap;
    String Lhap, Rhap;
    double Lf, Lm, Lb, Rf, Rm, Rb;
    double Lpf, Lpm, Lpb, Rpf, Rpm, Rpb;
    int ch, cm, cs, cr;
    String Lfront, Lmiddle, Lback, Rfront, Rmiddle, Rback;
    char Lseq[] = new char[4];
    char Rseq[] = new char[4];
    String LseqR = "";
    String RseqR = "";
    int i;
    long ct, ctmin, cthour, ctsecond;
    int total;
    String t;

    //연결상태 (Thread와 통신메세지)
    final int DISCONNECT = 0;
    final int CONNECTING = 1;
    final int CONNECTED = 2;
    final int INPUTDATA = 999;

    boolean IsConnect0 = false, IsConnect1 = false;
    boolean L_s = false, R_s = false;


    //레이아웃 객체 선언
    FrameLayout frame;
    LinearLayout la_con, la_check;
    //la_loading;

    //연결화면 UI객체 선언
    ImageView cbtnbt1, cbtnbt2;
    TextView ctvname1, ctvname2;
    Button cbtnok;



    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checktime);

        final Intent intent = getIntent();


        final ImageButton startcl = (ImageButton) findViewById(R.id.startcl);
        final ImageButton startpause = (ImageButton) findViewById(R.id.startpause);
        final Chronometer chrono1 = (Chronometer) findViewById(R.id.chrono1);
        Button origin = (Button) findViewById(R.id.origin);
        ImageButton nextcl = (ImageButton) findViewById(R.id.nextcl);
        chrono1.setBase(elapsedRealtime());
        final TextView hep = (TextView) findViewById(R.id.hep);
        final TextView ti = (TextView) findViewById(R.id.ti);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


      /* 블루투스 어뎁터 생성*/
        BA = BluetoothAdapter.getDefaultAdapter();
/***************** 레이아웃 및 객체 초기화 ******************/
        //framelayout초기화
        frame = (FrameLayout) findViewById(R.id.frame);

        //연결화면 객체 초기화
        la_con = (LinearLayout) findViewById(R.id.lay_con);
        cbtnbt1 = (ImageView) findViewById(R.id.ibtnbt1);
        cbtnbt2 = (ImageView) findViewById(R.id.ibtnbt2);
        ctvname1 = (TextView) findViewById(R.id.tv_btname1);
        ctvname2 = (TextView) findViewById(R.id.tv_btname2);
        cbtnok = (Button) findViewById(R.id.btnok);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(Receiver, filter);



        //측정화면 객체 초기화
        la_check = (LinearLayout) findViewById(R.id.lay_check);

        startcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = 1;
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

                long ct = (elapsedRealtime() - chrono1.getBase()) / 1000;

                long ctmin = ct / 60;
                long ctsecond = ct - (ctmin * 60);
                long cthour = ctmin / 60;

                String rr = (cthour + ":" + ctmin + ":" + ctsecond);
                ti.setText(rr);
                ti.setVisibility(view.INVISIBLE);


                pauseoffset = elapsedRealtime() - chrono1.getBase();
                running = false;
                chrono1.setTextColor(Color.BLACK);
                hep.setText(getTime());
                hep.setVisibility(view.INVISIBLE);
                Toast.makeText(getApplicationContext(), "측정을 멈췄습니다.", Toast.LENGTH_LONG).show();
            }
        });

        //블루투스 연결 확인 버튼
        cbtnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                //성공시 측정화면으로 전환
                                frame.removeAllViews();
                                frame.addView(la_check);
                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                BTsetRequest bTsetRequest = new BTsetRequest(idd, BDL.getAddress(), BDR.getAddress(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(CheckTimeActivity.this);
                queue.add(bTsetRequest);

            }
        });

        //블루투스 왼발 기기선택 버튼
        cbtnbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //왼쪽 기기 연결안된 상태에서 버튼 클릭시
                if (IsConnect0 == false) {
                    Intent tent = new Intent(CheckTimeActivity.this, DevicelistActivity.class);
                    startActivityForResult(tent, 500);
                } else {//왼쪽 기기 연결된 상태에서 버튼 클릭시
                    if (BCL != null) {
                        try {
                            BCL.cancel();
                            BCL = null;

                        } catch (IOException e) {
                        }
                    }
                }
            }
        });

        //블루투스 오른발 기기선택 버튼
        cbtnbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //오른쪽 기기 연결안된 상태에서 버튼 클릭시
                if (IsConnect1 == false) {
                    Intent tent = new Intent(CheckTimeActivity.this, DevicelistActivity.class);
                    startActivityForResult(tent, 400);
                } else { //오른쪽 기기 연결된 상태에서 버튼 클릭시
                    if (BCR != null) {
                        try {
                            BCR.cancel();
                            BCR = null;
                        } catch (IOException e) {
                        }
                    }
                }
            }
        });


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
                            if (j == 1) {
                                //중복 확인 테이블
                                if (success) {
                                    i = 2;
                                    String time = jsonResponse.getString("check_t");
                                    StringTokenizer st = new StringTokenizer(time, ":");            //아두이노 스캐치에서 받아온 스트링 값 ','으로 구분
                                    ArrayList hms = new ArrayList();
                                    while (st.hasMoreTokens()) {
                                        hms.add(st.nextToken());
                                    }
                                    int h = Integer.parseInt((String) hms.get(0));
                                    int m = Integer.parseInt((String) hms.get(1));
                                    int s = Integer.parseInt((String) hms.get(2));

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

                                    h = total / 3600;
                                    m = (total / 60) % 60;
                                    s = (total % 60);

                                    Log.e("dddd", String.valueOf(s));

/*
                                    hep1.setText(String.valueOf(h));
                                    h
                                    h = ch * 3600;
                                    m = cm * 60;

                                    total = ch + cm + cs;

                                    h = total/3600;
                                    m = (total/60)%60;
                                    s = (total%60);*/

                                    t = String.valueOf(h) + ":" + String.valueOf(m) + ":" + String.valueOf(s);

                                } else {
                                    i = 1;
                                }

                            } else if (j == 2) {
                                //테이블 갱신
                                AlertDialog.Builder builder = new AlertDialog.Builder(CheckTimeActivity.this);
                                dialog = builder.setMessage("메인화면으로 돌아갑니다.\n(두번째 탭에서 확인하세요.)")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(CheckTimeActivity.this, FiveTabActivity.class);
                                                intent.putExtra("id", idd);
                                                startActivityForResult(intent, 200);
                                                finish();
                                            }
                                        })
                                        .create();
                                dialog.show();
                            } else {
                                //테이블 추가
                                AlertDialog.Builder builder = new AlertDialog.Builder(CheckTimeActivity.this);
                                dialog = builder.setMessage("메인화면으로 돌아갑니다.\n(두번째 탭에서 확인하세요.)")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(CheckTimeActivity.this, FiveTabActivity.class);
                                                intent.putExtra("id", idd);
                                                startActivityForResult(intent, 200);
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
                switch (i) {
                    case 0:
                        ListCheckRequest listCheckRequest = new ListCheckRequest(check_date, userID, responseListener);
                        queue.add(listCheckRequest);
                    case 1:
                        if (i == 1) {
                            CheckListRequest checkListRequest = new CheckListRequest(check_date, check_t, userID, responseListener);
                            queue.add(checkListRequest);
                        }
                    case 2:
                        if (i == 2) {
                            ListtimeUpdateRequest listtimeUpdateRequest = new ListtimeUpdateRequest(userID, t, check_date, responseListener);
                            queue.add(listtimeUpdateRequest);
                        }
                }
            }
        });


        frame.removeAllViews();
        frame.addView(la_con);

    }

    //기기검색 브로드캐스트 리시버
    private final BroadcastReceiver Receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (action) {
                //기기 연결
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Toast.makeText(CheckTimeActivity.this, device.getName() + "Is Connected!", Toast.LENGTH_SHORT).show();

                    break;
                //기기 연결 해제
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    Toast.makeText(CheckTimeActivity.this, device.getName() + "Is Disconnected!", Toast.LENGTH_SHORT).show();
                    Message m = new Message();
                    if (BDL.getName().equals(device.getName())) {
                        try {
                            if (BCL != null) {
                                BCL.cancel();
                                BCL = null;
                            }
                            m.what = 0;
                        } catch (IOException e) {
                        }
                    } else {
                        try {
                            if (BCR != null) {
                                BCR.cancel();
                                BCR = null;
                            }
                            m.what = 1;
                        } catch (IOException e) {

                        }
                    }
                    m.arg1 = DISCONNECT;
                    handler.sendMessage(m);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == 500) { //기기검색리스트에서 연결할 기기 선택시(왼쪽)
            if (resultcode == RESULT_OK) {
                String address = data.getStringExtra("address");
                BluetoothDevice de = BA.getRemoteDevice(address);
                BDL = de;
                BCL = new ConnectThread(BDL, 0);
                BCL.start();

            }
        } else if (requestcode == 400) { //기기검색리스트에서 연결할 기기 선택시(오른쪽)
            if (resultcode == RESULT_OK) {
                String address = data.getStringExtra("address");
                BluetoothDevice de = BA.getRemoteDevice(address);
                BDR = de;
                BCR = new ConnectThread(BDR, 1);
                BCR.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (BCL != null) {
            try {
                BCL.cancel();
            } catch (IOException e) {
            }
        }
        if (BCR != null) {
            try {
                BCR.cancel();
            } catch (IOException e) {
            }
        }
        super.onDestroy();
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

    //Thread에서 상태변화 받고 UI변경위해
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            RequestQueue queue12 = Volley.newRequestQueue(CheckTimeActivity.this);
            switch (msg.what) {
                case 0:											// 왼발 센서 값일 경우
                    switch (msg.arg1) {
                        case CONNECTED:
                            if (IsConnect0 && IsConnect1) {
                                cbtnok.setEnabled(true);
                            }
                            break;
                        case CONNECTING:
                            break;
                        case DISCONNECT:
                            break;
                        case INPUTDATA:                       //센서 값 받아오는 경우
                            String ls = msg.obj.toString();
                            //아두이노 스캐치에서 받아온 스트링 값 ','으로 구분
                            StringTokenizer lst = new StringTokenizer(ls, ",");
                            ArrayList lsensorval = new ArrayList();
                            while (lst.hasMoreTokens()) {
                                lsensorval.add(lst.nextToken());
                            }
                            Lzero = Integer.parseInt((String) lsensorval.get(0));
                            Lfirst = Integer.parseInt((String) lsensorval.get(1));
                            Lsecond = Integer.parseInt((String) lsensorval.get(2));
                            Lthird = Integer.parseInt((String) lsensorval.get(3));

                            if (Lzero != 0 && Lfirst != 0 && Lsecond != 0 && Lthird != 0) {            //왼발 부위 전체 측정
                                L_s = true;								//양발 균형, 삼박자 측정 true값 설정
                                lhap = Lzero + Lfirst + Lsecond + Lthird;				//왼발 총합 lhap에 저장

                                if(Lthird > 900) Lseq[0]='4';
                            }

                            if(Lseq[0]=='4' && Lthird <= 900){								//걷을 때 측정 측정시작
                                if(Lthird !=0 && Lsecond !=0 && Lfirst!=0 && Lzero!=0){
                                    if(Lseq[1] != '2' && ((Lsecond > Lfirst) || Lseq[1]=='3' )){
                                        Lseq[1]='3';
                                        if(((Lthird + Lsecond) < (Lfirst + Lzero)) && Lfirst > Lzero){
                                            Lseq[2]='2';
                                            Lseq[3]='1';
                                        }else if(((Lthird + Lsecond) < (Lfirst + Lzero)) && Lfirst < Lzero){
                                            Lseq[2]='1';
                                            Lseq[3]='2';
                                        }
                                    }else {
                                        Lseq[1]='2';
                                        if((Lzero + Lsecond) > (Lfirst + Lthird) && Lzero > Lsecond){
                                            Lseq[2]='1';
                                            Lseq[3]='3';
                                        }else if((Lzero + Lsecond) > (Lfirst + Lthird) && Lzero < Lsecond) {
                                            Lseq[2]='3';
                                            Lseq[3]='1';
                                        }
                                    }
                                }
                                if(Lseq[3] !=0 && Lseq[2] !=0 && Lseq[1] != 0 && Lseq[0] !=0){
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("b_success");
                                            if(success) {   //로그인 성공시
                                                Toast.makeText(CheckTimeActivity.this,"성공",Toast.LENGTH_SHORT).show();
                                            }
                                            else {

                                            }
                                        } catch(Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                Lf = Lzero;
                                Lm = (Lfirst + Lsecond)/2;
                                Lb = Lthird;

                                Lpf = (Lf / (Lf + Lm + Lb)) * 100;
                                Lpm = (Lm / (Lf + Lm + Lb)) * 100;
                                Lpb = (Lb / (Lf + Lm + Lb)) * 100;

                                Lfront = String.valueOf((int)Math.round(Lpf));
                                Lmiddle = String.valueOf((int)Math.round(Lpm));
                                Lback = String.valueOf((int)Math.round(Lpb));

                                // sql문 왼발 앞부위, 중간부위, 뒷부위 값 삼박자 테이블 삽입
                                ThreeRequest LthRequest = new ThreeRequest(Lfront, Lmiddle, Lback,idd, "0", responseListener);	//왼발 count 값 0
                                queue12.add(LthRequest);
                                }

                                if(Lseq[3] !=0 && Lseq[2] !=0 && Lseq[1] != 0 && Lseq[0] !=0){

                                    for (int i = 0; i < 4; i++) {
                                        LseqR += Lseq[i];							//지지분포 걷는 부위 순서 스트링값 저장
                                        Lseq[i] = 0;							//동시에 값 초기화
                                    }
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                            } catch(Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    // sql문 왼발 지지분포 LseqR 스트링값을 지지분포 테이블 삽입
                                    SupportRequest spRequest = new SupportRequest(LseqR,idd,"0",responseListener);	//왼발 count 값 0
                                    queue12.add(spRequest);

                                    LseqR = "";
                                }

                            }

                            break;
                    }
                    break;

                case 1:										// 오른발 센서 경우
                    switch (msg.arg1) {
                        case CONNECTED:
                            if (IsConnect0 && IsConnect1) {
                                cbtnok.setEnabled(true);
                            }
                            break;
                        case CONNECTING:
                            break;
                        case DISCONNECT:
                            break;
                        case INPUTDATA:					//센서 값 받아오는 경우
                            String rs = msg.obj.toString();
                            //아두이노 스캐치에서 받아온 스트링 값  ','으로 구분
                            StringTokenizer rst = new StringTokenizer(rs,", ");
                            ArrayList rsensorval = new ArrayList();
                            while(rst.hasMoreTokens()){
                                rsensorval.add(rst.nextToken());
                            }
                            Rzero = Integer.parseInt((String)rsensorval.get(0));
                            Rfirst = Integer.parseInt((String)rsensorval.get(1));
                            Rsecond = Integer.parseInt((String)rsensorval.get(2));
                            Rthird = Integer.parseInt((String)rsensorval.get(3));

                            if(Rzero !=0 && Rfirst!=0 && Rsecond!=0 && Rthird !=0) {            //오른발 부위 전체 측정
                                R_s = true;							//양발 균형, 삼박자 측정 true값 설정
                                rhap = Rzero + Rfirst + Rsecond + Rthird;			//오른발 총합 rhap에 저장

                                if(Rthird > 900) Rseq[0]='4';

                            }


                            if(Rseq[0]=='4' && Rthird < 900){							//걷을 때 측정 시작
                                if(Rthird !=0 && Rsecond !=0 && Rfirst!=0 && Rzero!=0){
                                    if(Rseq[1] != '2' && ((Rsecond > Rfirst) || Rseq[1]=='3')){
                                        Rseq[1]='3';
                                        if((Rthird + Rsecond) < (Rfirst + Rzero) && Rfirst > Rzero){
                                            Rseq[2]='2';
                                            Rseq[3]='1';
                                        }else if((Rthird + Rsecond) < (Rfirst + Rzero) && Rfirst < Rzero){
                                            Rseq[2]='1';
                                            Rseq[3]='2';
                                        }
                                    }else {
                                        Rseq[1]='2';
                                        if((Rzero + Rsecond) > (Rfirst + Rthird) && Rzero > Rsecond){
                                            Rseq[2]='1';
                                            Rseq[3]='3';
                                        }else if((Rzero + Rsecond) > (Rfirst + Rthird) && Rzero < Rsecond){
                                            Rseq[2]='3';
                                            Rseq[3]='1';
                                        }
                                    }
                                }

                                if(Rseq[3] !=0 && Rseq[2] !=0 && Rseq[1] != 0 && Rseq[0] !=0){
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                        } catch(Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                Rf = Rzero;
                                Rm = (Rfirst + Rsecond)/2;
                                Rb = Rthird;

                                Rpf = (Rf / (Rf + Rm + Rb)) * 100;
                                Rpm = (Rm / (Rf + Rm + Rb)) * 100;
                                Rpb = (Rb / (Rf + Rm + Rb)) * 100;

                                Rfront = String.valueOf((int)Math.round(Rpf));
                                Rmiddle = String.valueOf((int)Math.round(Rpm));
                                Rback = String.valueOf((int)Math.round(Rpb));

                                // sql문 오른발 앞부위, 중간부위, 뒷부위 값 삼박자 테이블 삽입
                                ThreeRequest RthRequest = new ThreeRequest(Rfront, Rmiddle, Rback,idd, "1", responseListener);	//오른발 count 값 1
                                queue12.add(RthRequest);
                                }
                                if(Rseq[3] !=0 && Rseq[2] !=0 && Rseq[1] != 0 && Rseq[0] !=0) {
                                    for (int i = 0; i < 4; i++) {
                                        RseqR += Rseq[i];                        //지지분포 걷는 부위 순서 스트링값 저장
                                        Rseq[i] = 0;                        //동시에 값 초기화
                                    }
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    // sql문 오른발 지지분포 LseqR 스트링값을 지지분포 테이블 삽입
                                    SupportRequest spRequest = new SupportRequest(RseqR, idd, "1", responseListener);    // 오른발 count 값 1
                                    queue12.add(spRequest);

                                    RseqR = "";
                                }

                            }

                            break;
                    }
                    break;
            }

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            if(L_s & R_s) {							//왼발과, 오른발 동시에 true인 경우, 양발 균형,삼박자 측정 값 넣기

                lphap = (int)(Math.round((lhap / (lhap + rhap)) * 100));	//Math.round 함수 반올림하는 함수, 퍼센트 값 계산
                rphap = 100 - lphap;

                Lhap = String.valueOf(lphap);
                Rhap = String.valueOf(rphap);

                BalanceRequest blRequest = new BalanceRequest(Lhap, Rhap,idd, responseListener);	// 왼발,오른발 퍼센트 값 데이터베이스 넣기
                queue12.add(blRequest);

                L_s=false;
                R_s=false;

            }

        }
    };

    //기기 연결 쓰레드
    public class ConnectThread extends Thread {

        BluetoothDevice BD;
        BluetoothSocket BS;

        int bluetooth_index;

        ConnectedThread connectedThread;


        final String SPP_UUID_STRING = "00001101-0000-1000-8000-00805F9B34FB"; //SPP UUID
        final UUID SPP_UUID = UUID.fromString(SPP_UUID_STRING);

        ConnectThread(BluetoothDevice device, int index) {
            BD = device;
            bluetooth_index = index;
        }

        @Override
        public void run() {
            sendMessage(CONNECTING);
            try {
                BS = BD.createRfcommSocketToServiceRecord(SPP_UUID);
                BS.connect();
                connectedThread = new ConnectedThread(BS, bluetooth_index);
                connectedThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    BS = (BluetoothSocket) BD.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(BD, 1);
                    BS.connect();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    cancel();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void cancel() throws IOException {
            if (BS != null) {
                BS.close();
                BS = null;
            }
            if (connectedThread != null) {
                connectedThread.cancel();
            }
            sendMessage(DISCONNECT);

        }

        public void sendMessage(int arg) {

            Message m = new Message();
            m.what = bluetooth_index;
            m.arg1 = arg;
            handler.sendMessage(m);

        }
    }

    //기기 연결관리 쓰레드(데이터 송수신)
    public class ConnectedThread extends Thread {
        BluetoothSocket BS;

        int bluetooth_index;

        InputStream in;

        boolean is = false;

        ConnectedThread(BluetoothSocket socket, int index) {
            BS = socket;
            bluetooth_index = index;
            try {
                in = BS.getInputStream();
                is = true;
                if (bluetooth_index == 0) IsConnect0 = is;
                else IsConnect1 = is;
                sendMessage(CONNECTED);
            } catch (IOException e) {
                cancel();
            }
        }

        @Override
        public void run() {

            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

            while (is) {
                try {

                    String s = buffer.readLine();
                    if (!s.equals("")&&start==1) {
                        sendMessage(s);
                    }
                } catch (IOException e) {

                }
            }
        }

        public void cancel() {
            is = false;
            if (bluetooth_index == 0) IsConnect0 = is;
            else IsConnect1 = is;
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sendMessage(DISCONNECT);
        }

        public void sendMessage(Object data) {
            Message m = new Message();
            m.what = bluetooth_index;
            m.arg1 = INPUTDATA;
            m.obj = data;
            handler.sendMessage(m);
        }

        public void sendMessage(int arg) {
            Message m = new Message();
            m.what = bluetooth_index;
            m.arg1 = arg;
            handler.sendMessage(m);
        }
    }

}