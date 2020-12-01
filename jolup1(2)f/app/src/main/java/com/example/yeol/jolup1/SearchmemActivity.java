package com.example.yeol.jolup1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by yeol on 2019-10-25.
 */

public class SearchmemActivity extends AppCompatActivity {

    EditText searchname, searchemail;
    Button searchButton, gohomeButton;
    TextView searchid, searchpw;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmem);

        searchname = (EditText) findViewById(R.id.searchname);
        searchemail = (EditText) findViewById(R.id.searchemail);
        searchButton = (Button) findViewById(R.id.searchButton);
        gohomeButton = (Button) findViewById(R.id.gohomeButton);
        searchid = (TextView) findViewById(R.id.searchid);
        searchpw = (TextView) findViewById(R.id.searchpw);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = searchname.getText().toString();
                String userEmail = searchemail.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Log.e("1", "성공공");
                                String userID = jsonResponse.getString("userID");
                                Log.e("dddd", String.valueOf(userID));
                                searchid.setText(userID);
                                String userPassword = jsonResponse.getString("userPassword");
                                searchpw.setText(userPassword);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchmemActivity.this);
                                dialog = builder.setMessage("일치하는 정보가 없습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

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

                SearchmemRequest searchmemRequest = new SearchmemRequest(userName, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SearchmemActivity.this);
                queue.add(searchmemRequest);
            }
        });

        gohomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchmemActivity.this, LoginActivity.class);
                SearchmemActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(SearchmemActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }
}
