package com.example.yeol.jolup1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by yeol on 2019-10-21.
 */

public class YouTubeActivity extends YouTubeBaseActivity {
    YouTubePlayerView youtubeView, youtubeView1;
    Button bt, bt1;
    TextView textviewno;
    String idd;
    YouTubePlayer.OnInitializedListener listener, listener1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        final Intent intent = new Intent(this.getIntent());
        idd = intent.getStringExtra("id");
        bt = (Button) findViewById(R.id.youtubeButton);
        bt1 = (Button) findViewById(R.id.youtubeButton1);
        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        youtubeView1 = (YouTubePlayerView) findViewById(R.id.youtubeView1);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("gIsRQyDnNuc");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        listener1 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("nQlbDogfCpE");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youtubeView.initialize("AIzaSyChcWQM5kviatpqSTzwKFlau6GYmB2rCH4", listener);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youtubeView1.initialize("AIzaSyChcWQM5kviatpqSTzwKFlau6GYmB2rCH4", listener1);
            }
        });

    }
}