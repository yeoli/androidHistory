package com.example.yeol.jolup1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.v4.os.LocaleListCompat.create;

/**
 * Created by yeol on 2019-04-24.
 */

/*public class ListvActivity extends AppCompatActivity {


    private ListView listv1;
    private CheckListAdapter adapter;
    private List<CheckList> checkList;
    String d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listv);


        final Intent intent = getIntent();
        listv1 = (ListView) findViewById(R.id.listv1);
        checkList = new ArrayList<CheckList>();
        final String idd = intent.getStringExtra("id");
        adapter = new CheckListAdapter(getApplicationContext(), checkList);
        listv1.setAdapter(adapter);

        Response.Listener<String>  responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray arrDateList = jsonResponse.getJSONArray("dateList");

                    Log.i("list", jsonResponse.toString());

                    for(int i=0;i<arrDateList .length();i++)
                    {
                        JSONObject date = arrDateList.getJSONObject(i);
                        String t= date.getString("check_t");
                        d = date.getString("check_date");
                        checkList.add(new CheckList(t, d));
                    }

                    adapter.notifyDataSetChanged();


                } catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        CheckList2Request checkList2Request = new CheckList2Request(idd,responseListener);
        RequestQueue queue = Volley.newRequestQueue(ListvActivity.this);
        queue.add(checkList2Request);


        listv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(getApplicationContext(), FunctionTab.class);
                intent.putExtra("click_date",d);
                startActivity(intent);
            }
        });


    }
}*/
