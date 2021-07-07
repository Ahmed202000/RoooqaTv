package com.roooqaTv.roooqatv.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fivemin.chief.nonetworklibrary.networkBroadcast.NoNet;
import com.roooqaTv.roooqatv.R;


public class SplashActivity extends AppCompatActivity {


    //private NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                finish();

            }
        },4000);


    }


}