package com.example.yeol.viewflippertest_03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    Button btnPre, btnNext;
    ViewFlipper btnFlip1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPre = (Button) findViewById(R.id.btnPre);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnFlip1 = (ViewFlipper) findViewById(R.id.viewFlip1);

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFlip1.startFlipping();
                btnFlip1.setFlipInterval(1000);

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFlip1.stopFlipping();
                btnFlip1.setFlipInterval(1000);

            }
        });

    }
}
