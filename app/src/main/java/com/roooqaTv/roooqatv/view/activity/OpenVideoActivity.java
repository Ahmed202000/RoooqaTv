package com.roooqaTv.roooqatv.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.roooqaTv.roooqatv.R;


public class OpenVideoActivity extends AppCompatActivity {

    private String CONTENT_URL ;

    SimpleExoPlayer player;
    PlayerView playerView;
    ImageView fullscreenButton;
    boolean fullscreen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_video);

        CONTENT_URL = getIntent().getExtras().getString("Link");

        Log.d("sssss",CONTENT_URL);

        player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());

        playerView = findViewById(R.id.activity_Open_Video);

        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {


                if(fullscreen) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(OpenVideoActivity.this, R.drawable.ic_fullscreen_open));

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

                    fullscreen = false;
                }else{
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(OpenVideoActivity.this, R.drawable.ic_fullscreen_close));

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerView.setLayoutParams(params);

                    fullscreen = true;
                }
            }
        });

        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),Util.getUserAgent(getApplicationContext(),getApplicationContext().getString(R.string.app_name)));

        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(CONTENT_URL));

        player.prepare(videoSource);
        player.setPlayWhenReady(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    public void onDestroy() {
        player.release();
        super.onDestroy();
    }

}