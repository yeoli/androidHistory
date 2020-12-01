package com.example.yeol.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etID, etPW;
    Button btnLogin;
    TextView tvjoin, tvsearch;

    boolean dbCreated = false;
    boolean tableCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etID = (EditText) findViewById(R.id.etID);
        etPW = (EditText) findViewById(R.id.etPW);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvjoin = (TextView) findViewById(R.id.tvJoin);
        tvsearch = (TextView) findViewById(R.id.tvsearch);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,StaticActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Bluetooth.class);
                startActivity(intent);
                finish();
            }
        });

        tvjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeviceActivity.class);
                startActivity(intent);
                finish();



            }
        });
    }
}
