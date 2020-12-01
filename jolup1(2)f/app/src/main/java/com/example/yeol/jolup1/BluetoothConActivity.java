package com.example.yeol.jolup1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by yeol on 2019-04-05.
 */

public class BluetoothConActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueconnect);

        Button btnconc = (Button) findViewById(R.id.btnconc);

        btnconc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BluetoothConActivity.this, FiveTabActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}