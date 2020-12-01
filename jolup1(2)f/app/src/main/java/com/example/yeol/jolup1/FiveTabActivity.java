package com.example.yeol.jolup1;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LauncherActivity;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.drm.DrmStore;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yeol on 2019-04-03.
 */

public class FiveTabActivity extends AppCompatActivity {

    //블루투스 어뎁더
    BluetoothAdapter BA;

    //list 관련 변수
    private ListView listv1;
    private CheckListAdapter adapter;
    private List<CheckList> checkList;
    //리스트 날짜 변수
    String d;
    long mNow;
    TextView date;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM");
    Calendar cal = Calendar.getInstance();  //현재시간 담는 calendar객체
    Date mDate = cal.getTime();     //calendar 객체가 저장하고있는 시간을 Date형태로 리턴
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    String formaStr = sdf.format(mDate);
    String start; //리스트 가져올 시작날짜
    String end; // 리스트 가져올 끝날짜
    //메인화면에 보일 리스트 개수(n일차에서 n에 해당)
    int list_size;
    //차트관련 변수
    RadarChart radari;
    String[] labels = {"보폭", "균형", "발각도", "3박자", "지지분포"};
    TextView r;
    TextView nday;
    //id 저장할 변수
    String idd;
    int ite;


    //메인화면 관련 객체
    Button balLeft;
    Button balRight;

    Button threeLBack;
    Button threeLMiddle;
    Button threeLFront;
    Button threeRBack;
    Button threeRMiddle;
    Button threeRFront;

    Button supportLeft;
    Button supportRight;

    int bal_l;
    int bal_r;
    int tl_back;
    int tl_middle;
    int tl_front;
    int tr_back;
    int tr_middle;
    int tr_front;

    int numl;
    int numl1;
    int numl2;
    int numr;
    int numr1;
    int numr2;

    YouTubePlayerView youtubeView, youtubeView1, youtubeView2, youtubeView3;
    Button bt, bt1, bt2, bt3;
    YouTubePlayer.OnInitializedListener listener;


    //DB관련 객체
    RequestQueue queue;
    Response.Listener<String> responseListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fivetab);

        /****리스트 관련 객체 초기화**/
        listv1 = (ListView) findViewById(R.id.listv1);
        checkList = new ArrayList<CheckList>();
        adapter = new CheckListAdapter(getApplicationContext(), checkList);
        listv1.setAdapter(adapter);
        //리스트뷰 화면 UI 객체
        ImageView imgb = (ImageView) findViewById(R.id.imgb);
        ImageView imga = (ImageView) findViewById(R.id.imga);
        date = (TextView) findViewById(R.id.date);
        date.setText(formaStr);  //텍스트뷰에 simpleDateFormat 객체 집어넣는다
        nday = (TextView) findViewById(R.id.nday);
        r = (TextView) findViewById(R.id.r);
        //Button youbtn = (Button) findViewById(R.id.youbtn);
        TextView goid = (TextView) findViewById(R.id.goid);

        date.setText(getTime());

        Toolbar tb = (Toolbar) findViewById(R.id.mtool_bar);
        setSupportActionBar(tb);


        //메인화면 관련 객체
        balLeft = (Button) findViewById(R.id.BalLeft);
        balRight = (Button) findViewById(R.id.BalRight);

        threeLBack = (Button) findViewById(R.id.ThreeLBack);
        threeLMiddle = (Button) findViewById(R.id.ThreeLMiddle);
        threeLFront = (Button) findViewById(R.id.ThreeLFront);
        threeRBack = (Button) findViewById(R.id.ThreeRBack);
        threeRMiddle = (Button) findViewById(R.id.ThreeRMiddle);
        threeRFront = (Button) findViewById(R.id.ThreeRFront);

        supportLeft = (Button) findViewById(R.id.SuportLeft);
        supportRight = (Button) findViewById(R.id.SuportRight);

        //측정화면 관련 UI
        ImageButton checkbu = (ImageButton) findViewById(R.id.checkbu);

        ActionBar ab = getSupportActionBar();

        //id 가져옴
        final Intent intent = new Intent(this.getIntent());
        idd = intent.getStringExtra("id");
        goid.setText(idd);


        /*** tab호스트 관련 **/
        final TabHost tabHost = (TabHost) findViewById(R.id.tabhostt);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("1");
        tab1.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.homme, null));
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);


        TabHost.TabSpec tab2 = tabHost.newTabSpec("2");
        tab2.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.listme, null));
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("3");
        tab3.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.checked, null));
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("4");
        tab4.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.graph, null));
        tab4.setContent(R.id.tab4);
        tabHost.addTab(tab4);
        tabHost.setCurrentTab(4);

        /*** tab호스트 관련 **/


        /* 블루투스 어뎁터 생성*/
        BA = BluetoothAdapter.getDefaultAdapter();

        //통계 chart UI 선언
        final BarChart chart_bal = (BarChart) findViewById(R.id.chart_bal);
        final BarChart chart_thre = (BarChart) findViewById(R.id.chart_thre);
        final BarChart chart_sup = (BarChart) findViewById(R.id.chart_sup);

        //DB 리스폰스 리스너
        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    int index = jsonResponse.getInt("i");


                    //차트에 들어갈 데이터 배열
                    ArrayList<BarEntry> barEntries_l = new ArrayList<>();
                    ArrayList<BarEntry> barEntries_r = new ArrayList<>();
                    //양발균형, 지지분포 차트의 x축 라벨
                    String[] days = {"","","","",""};
                    if (success) {
                        switch (index) {
                            //응답한 php가 양발균형 관련 일때
                            case 1:
                                JSONArray arrDateList = jsonResponse.getJSONArray("dateList");
                                for (int i = 0; i < arrDateList.length(); i++) {
                                    JSONObject date = arrDateList.getJSONObject(i);
                                    String check_date = date.getString("check_date");
                                    //DB에서 양발균형값 가져오고, 차트 데이터에 추가
                                    int bal_l = date.getInt("bal_l");
                                    int bal_r = date.getInt("bal_r");
                                    barEntries_l.add(new BarEntry(i, bal_l));
                                    barEntries_r.add(new BarEntry(i, bal_r));
                                    days[i] = check_date;
                                }
                                //양발균형 차트의 데이터 설정
                                BarDataSet barDataSet1 = new BarDataSet(barEntries_l, "왼발 압력 ");
                                barDataSet1.setColor(Color.RED);

                                BarDataSet barDataSet2 = new BarDataSet(barEntries_r, "오른발 압력");
                                barDataSet2.setColor(Color.rgb(164, 228, 251));
                                BarData data = new BarData(barDataSet1, barDataSet2);
                                chart_bal.setData(data);
                                //양발균형 차트 X축 설정
                                XAxis xAxis = chart_bal.getXAxis();
                                xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
                                xAxis.setCenterAxisLabels(true);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setGranularity(1f);
                                xAxis.setAxisMaximum(data.getXMax() + 1f);
                                xAxis.setGranularityEnabled(true);
                                chart_bal.setDragEnabled(true);
                                //보이는 밑의 값
                                chart_bal.setVisibleXRangeMaximum(6);
                                chart_bal.getDescription().setText("");
                                float barSpace = 0.02f;
                                float groupSpace = 0.04f;

                                //바 크기
                                data.setBarWidth(0.46f);
                                chart_bal.getXAxis().setAxisMinimum(0);

                                chart_bal.groupBars(0, groupSpace, barSpace);
                                chart_bal.invalidate();
                                break;

                            //응답한 php가 3박자 관련 일때
                            case 2:
                                JSONArray arrDateList1 = jsonResponse.getJSONArray("dateList");
                                int j = 0;
                                for (int i = 0; i < arrDateList1.length(); i++) {
                                    JSONObject date = arrDateList1.getJSONObject(i);
                                    String check_date = date.getString("check_date");
                                    //날짜 변수에 넣을값이 중복인지 확인
                                    if ((!(j == 0))) {
                                        //날짜가 중복이 아닐경우에만 X축 날짜에 값추가
                                        if (!(days[j - 1].equals(check_date))) {
                                            days[j] = check_date;
                                            j++;
                                        }
                                    } else if (j == 0) {
                                        //처음 가져오는 값 X축 날짜에 추가
                                        days[j] = check_date;
                                        j++;
                                    }
                                    if (date.getInt("count") == 0) {
                                        //양발균형 중 왼발 값
                                        barEntries_l.add(new BarEntry(i, new float[]{date.getLong("front"), date.getLong("middle"), date.getLong("back")}));
                                    } else {
                                        //양발균형 중 오른발 값
                                        barEntries_r.add(new BarEntry(i, new float[]{date.getLong("front"), date.getLong("middle"), date.getLong("back")}));
                                    }
                                }
                                //3박자 stacked bar 데이터 설정
                                int[] colorClassArray = new int[]{Color.RED, Color.rgb(164, 228, 251), Color.rgb(104, 241, 175)};
                                int[] colorClassArray2 = new int[]{Color.rgb(255, 247, 140), Color.rgb(164, 228, 251), Color.rgb(104, 241, 175)};
                                BarDataSet barDataSet_t1 = new BarDataSet(barEntries_l, "왼발");
                                barDataSet_t1.setColors(colorClassArray);
                                barDataSet_t1.setStackLabels(new String[]{"앞꿈치", "중간", "뒷꿈치"});
                                BarDataSet barDataSet_t2 = new BarDataSet(barEntries_r, "오른발");
                                barDataSet_t2.setColors(colorClassArray2);
                                barDataSet_t2.setStackLabels(new String[]{"앞꿈치", "중간", "뒷꿈치"});

                                BarData data_t = new BarData(barDataSet_t1, barDataSet_t2);
                                chart_thre.setData(data_t);
                                //3박자 stacked bar X축  설정
                                XAxis xAxis_t = chart_thre.getXAxis();
                                xAxis_t.setValueFormatter(new IndexAxisValueFormatter(days));
                                xAxis_t.setCenterAxisLabels(true);
                                xAxis_t.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis_t.setGranularity(1f);
                                xAxis_t.setGranularityEnabled(true);
                                chart_thre.setDragEnabled(true);
                                //보이는 밑의 값
                                chart_thre.setVisibleXRangeMaximum(6);
                                chart_thre.getDescription().setText("");
                                float barSpace_t = 0.02f;
                                float groupSpace_t = 0.04f;
                                //바 크기
                                data_t.setBarWidth(0.46f);
                                chart_thre.getXAxis().setAxisMinimum(0);

                                chart_thre.groupBars(0, groupSpace_t, barSpace_t);
                                chart_thre.invalidate();
                                break;
                            //응답한 php가 지지분포 관련 일때
                            case 3:
                                JSONArray arrDateList2 = jsonResponse.getJSONArray("dateList");
                                int s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0, s6 = 0;
                                for (int i = 0; i < arrDateList2.length(); i++) {
                                    JSONObject date = arrDateList2.getJSONObject(i);
                                    int count = date.getInt("count_s");
                                    int order = date.getInt("order_s");
                                    int c = date.getInt("count");
                                    if (count == 0) {
                                        //일반디딤일때
                                        if (order == 4321) {
                                            s1 += c;
                                        } else if (order == 4213) {
                                            //바깥디딤일때
                                            s2 += c;
                                        } else if (order == 4312) {
                                            //안쪽디딤일때
                                            s3 += c;
                                        }
                                    } else {
                                        //일반디딤일때
                                        if (order == 4321) {
                                            s4 += c;
                                        } else if (order == 4213) {
                                            //바깥디딤일때
                                            s5 += c;
                                        } else if (order == 4312) {
                                            //안쪽디딤일때
                                            s6 += c;
                                        }
                                    }
                                }

                                //지지분포 차트 데이터 설정
                                ArrayList<BarEntry> barEntries_s1 = new ArrayList<>();
                                barEntries_s1.add(new BarEntry(0, s1));
                                barEntries_s1.add(new BarEntry(1, s2));
                                barEntries_s1.add(new BarEntry(2, s3));
                                ArrayList<BarEntry> barEntries_s2 = new ArrayList<>();
                                barEntries_s2.add(new BarEntry(0, s4));
                                barEntries_s2.add(new BarEntry(1, s5));
                                barEntries_s2.add(new BarEntry(2, s6));

                                BarDataSet barDataSet_s1 = new BarDataSet(barEntries_s1, "왼발  ");
                                barDataSet_s1.setColor(Color.RED);

                                BarDataSet barDataSet_s2 = new BarDataSet(barEntries_s2, "오른발 ");
                                barDataSet_s2.setColor(Color.rgb(164, 228, 251));
                                BarData data_s = new BarData(barDataSet_s1, barDataSet_s2);
                                chart_sup.setData(data_s);
                                //지지분포 차트 X축 설정
                                XAxis xAxis_s = chart_sup.getXAxis();
                                String[] sup = new String[]{"일반디딤", "바깥디딤", "안쪽디딤"};
                                xAxis_s.setValueFormatter(new IndexAxisValueFormatter(sup));
                                xAxis_s.setCenterAxisLabels(true);
                                xAxis_s.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis_s.setGranularity(1f);
                                xAxis_s.setAxisMaximum(data_s.getXMax() + 1f);
                                xAxis_s.setGranularityEnabled(true);
                                chart_sup.setDragEnabled(true);
                                //보이는 밑의 값
                                chart_sup.getDescription().setText("");
                                float barSpace_s = 0.02f;
                                float groupSpace_s = 0.04f;
                                chart_thre.setVisibleXRangeMaximum(6);
                                //바 크기
                                data_s.setBarWidth(0.46f);
                                chart_sup.getXAxis().setAxisMinimum(0);
                                chart_sup.groupBars(0, groupSpace_s, barSpace_s);
                                chart_sup.invalidate();
                                break;
                            case 4:
                                JSONArray arrDateList3 = jsonResponse.getJSONArray("dateList");
                                //응답한 php가 리스트 관련일때
                                int count = 0;
                                //기존 리스트 삭제
                                checkList.clear();
                                for (int i = 0; i < arrDateList3.length(); i++) {
                                    JSONObject date = arrDateList3.getJSONObject(i);
                                    String t = date.getString("check_t");
                                    d = date.getString("check_date");
                                    checkList.add(new CheckList(t, d));
                                    count += 1;
                                    list_size = count;
                                    adapter.notifyDataSetChanged();
                                }
                                // adapter.notifyDataSetChanged();
                                nday.setText(String.valueOf(count));

                                break;
                            case 5:

                                Double l_bal = jsonResponse.getDouble("leftbalance");
                                Double r_bar = jsonResponse.getDouble("rightbalance");

                                if(l_bal.equals("") && r_bar.equals("")){
                                    balLeft.setText("");
                                    balRight.setText("");
                                }else {
                                    bal_l = (int)(Math.round(l_bal));
                                    bal_r = 100 - bal_l;

                                    balLeft.setText(bal_l + "%");
                                    balRight.setText(bal_r + "%");
                                }

                                break;
                            case 6:

                                Double back_tl = jsonResponse.getDouble("tl_back");
                                Double middle_tl = jsonResponse.getDouble("tl_middle");
                                //Double front_tl = jsonResponse.getDouble("tl_front");

                                if(back_tl.equals("") && middle_tl.equals("")){
                                    threeLBack.setText("");
                                    threeLMiddle.setText("");
                                    threeLFront.setText("");
                                }else {
                                    tl_back = (int)(Math.round(back_tl));
                                    tl_middle = (int)(Math.round(middle_tl));
                                    tl_front = 100 - (tl_back + tl_middle);

                                    threeLBack.setText(tl_back + "%");
                                    threeLMiddle.setText(tl_middle + "%");
                                    threeLFront.setText(tl_front + "%");
                                }

                                break;
                            case 7:

                                Double back_tr = jsonResponse.getDouble("tr_back");
                                Double middle_tr = jsonResponse.getDouble("tr_middle");

                                if(back_tr.equals("") && middle_tr.equals("")){
                                    threeRBack.setText("");
                                    threeRMiddle.setText("");
                                    threeRFront.setText("");
                                }else {
                                    tr_back = (int)(Math.round(back_tr));
                                    tr_middle = (int)(Math.round(middle_tr));
                                    tr_front = 100 - (tr_back + tr_middle);

                                    threeRBack.setText(tr_back + "%");
                                    threeRMiddle.setText(tr_middle + "%");
                                    threeRFront.setText(tr_front + "%");
                                }

                                break;
                            case 8:

                                JSONArray arrDateList8 = jsonResponse.getJSONArray("dateList");
                                for (int i = 0; i < arrDateList8.length(); i++) {
                                    JSONObject date = arrDateList8.getJSONObject(i);

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

                                if(numl == 0 && numl1 == 0 && numl2 ==0){
                                    supportLeft.setText("");
                                }else if (numl >= numl1 && numl>= numl2)
                                    supportLeft.setText("일반 디딤(정상)");
                                else if (numl1 >numl2)
                                    supportLeft.setText("안쪽 디딤(비정상)");
                                else
                                    supportLeft.setText("바깥 디딤(비정상)");

                                if(numr==0 && numr1 ==0 && numr2 ==0){
                                    supportRight.setText("");
                                }else if (numr >= numr1 && numr>= numr2)
                                    supportRight.setText("일반 디딤(정상)");
                                else if (numr1 >numr2)
                                    supportRight.setText("안쪽 디딤(비정상)");
                                else
                                    supportRight.setText("바깥 디딤(비정상)");

                                break;
                        }
                    } else {
                        if (index == 4) {
                            //리스트 관련 DB가 응답했는데 값이 없을시
                            checkList.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    //DB관련 Exception catch문
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };

        //리스트 날짜 DB에 넣을 매개변수 초기화
        start = formaStr + "-00";
        end = formaStr + "-32";
        //양발균형 통계값 가져오는 DB 리퀘스트 설정
        BalchartRequest balchartRequest = new BalchartRequest(idd, responseListener);
        ThrechartRequest threchartRequest = new ThrechartRequest(idd, responseListener);
        SupchartRequest supchartRequest = new SupchartRequest(idd, responseListener);
        CheckList2Request checkList2Request = new CheckList2Request(idd, start, end, responseListener);
        queue = Volley.newRequestQueue(this);
        queue.add(balchartRequest);
        queue.add(threchartRequest);
        queue.add(checkList2Request);
        queue.add(supchartRequest);

        //main화면 양발균형, 삼박자, 지지분포 평균값 설정
        BalmainRequest balmainRequest = new BalmainRequest(idd, responseListener);
        ThreeLmainRequest threeLmainRequest = new ThreeLmainRequest(idd, responseListener);
        ThreeRmainRequest threeRmainRequest = new ThreeRmainRequest(idd, responseListener);
        SupportmainRequest supportmainRequest = new SupportmainRequest(idd, responseListener);
        queue = Volley.newRequestQueue(this);
        queue.add(balmainRequest);
        queue.add(threeLmainRequest);
        queue.add(threeRmainRequest);
        queue.add(supportmainRequest);

        //측정버튼 클릭
        checkbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /* 블루투스 셋업 */
                if (!BA.isEnabled()) {
                    //블루투스 비활성화일시 블루투스 설정 액티비티 불러옴
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(i, 5000);
                } else {
                    //블루투스가 활성화일시 측정화면으로 넘어감
                    Intent intent = new Intent(FiveTabActivity.this, CheckTimeActivity.class);
                    intent.putExtra("id", idd);
                    startActivityForResult(intent, 300);
                }

            }
        });

        //날짜 오른쪽 버튼
        imga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.add(Calendar.MONTH, 1);
                Date nextDay = cal.getTime();
                System.out.print(nextDay);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
                String formaStr1 = sdf1.format(nextDay);
                start = formaStr1 + "-00";
                end = formaStr1 + "-32";
                date.setText(formaStr1);
                CheckList2Request ch = new CheckList2Request(idd, start, end, responseListener);
                queue.add(ch);

            }
        });

        //날짜 왼쪽 버튼
        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.add(Calendar.MONTH, -1);
                Date backDay = cal.getTime();
                System.out.print(backDay);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
                String formaStr2 = sdf2.format(backDay);
                start = formaStr2 + "-00";
                end = formaStr2 + "-32";
                date.setText(formaStr2);
                CheckList2Request ch = new CheckList2Request(idd, start, end, responseListener);
                queue.add(ch);

            }
        });


//리스트 클릭시
        listv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //결과값 액티비티 활성
                Intent intent = new Intent(getApplicationContext(), FunctionTab.class);
                intent.putExtra("id", idd);
                CheckList item = checkList.get(i);
                intent.putExtra("check_date", item.getCheck_date());
                startActivityForResult(intent, 300);
                //finish();
            }
        });
//리스트 길게 클릭시
        listv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            RequestQueue queue1  = Volley.newRequestQueue(FiveTabActivity.this);
            Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ite = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(FiveTabActivity.this);
                builder.setTitle("삭제");
                builder.setMessage("해당 데이터를 삭제하겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CheckList item =checkList.get(ite);
                                ListDeleteRequest listDeleteRequest = new ListDeleteRequest(item.getCheck_date(),idd,responseListener1);
                                queue1.add(listDeleteRequest);
                                //클릭한 리스트 삭제
                                checkList.remove(ite);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),
                                        "삭제되었습니다.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();

                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == 5000) {
            //사용자가 블루투스 활성화 취소 눌렀을시
            if (resultcode == RESULT_CANCELED) {
                Toast.makeText(FiveTabActivity.this, "블루투스 활성화하여야 측정 이용가능합니다.", Toast.LENGTH_SHORT).show();

            } else {
                //사용자가 블루투스 활성화시 측정 화면 활성화
                Intent intent = new Intent(FiveTabActivity.this, CheckTimeActivity.class);
                intent.putExtra("id", idd);
                startActivity(intent);
            }
        }
    }

    //레이더 차트에 값넣는 함수1
    private ArrayList<RadarEntry> dataValues1() {
        ArrayList<RadarEntry> dataVals = new ArrayList<>();
        dataVals.add(new RadarEntry(8));
        dataVals.add(new RadarEntry(9));
        dataVals.add(new RadarEntry(8));
        dataVals.add(new RadarEntry(10));
        dataVals.add(new RadarEntry(9));
        return dataVals;

    }

    //레이더 차트에 값넣는 함수2
    private ArrayList<RadarEntry> dataValues2() {
        ArrayList<RadarEntry> dataVals = new ArrayList<>();
        dataVals.add(new RadarEntry(9));
        dataVals.add(new RadarEntry(6));
        dataVals.add(new RadarEntry(10));
        dataVals.add(new RadarEntry(7));
        dataVals.add(new RadarEntry(10));
        return dataVals;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myinfo:
                Intent intent = new Intent(FiveTabActivity.this, YouTubeActivity.class);
                FiveTabActivity.this.startActivity(intent);
                finish();
               // Toast.makeText(this, "update clicked", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }


}