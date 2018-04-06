package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import base.BaseActivity;
import utils.IntentUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentUtil.gotoActivity(this, BaseActivity.class);
        finish();
    }
}
