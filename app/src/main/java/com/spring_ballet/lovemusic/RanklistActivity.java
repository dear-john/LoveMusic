package com.spring_ballet.lovemusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class RanklistActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranklist);
        Toolbar toolbar = findViewById(R.id.ranklist_toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_ranklist_back).setOnClickListener(this);
        TextView ranklistName = findViewById(R.id.tv_ranklist_name);
        switch (Integer.parseInt(getIntent().getStringExtra("data"))) {
            case 1:
                ranklistName.setText(R.string.new_music);
                break;
            case 2:
                ranklistName.setText(R.string.hot_music);
                break;
            case 16:
                ranklistName.setText(R.string.pop_music);
                break;
            case 21:
                ranklistName.setText(R.string.europe_music);
                break;
            case 22:
                ranklistName.setText(R.string.classical_music);
                break;
            case 24:
                ranklistName.setText(R.string.movie_music);
                break;
            case 25:
                ranklistName.setText(R.string.net_music);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_ranklist_back:
                finish();
                break;
        }
    }
}
