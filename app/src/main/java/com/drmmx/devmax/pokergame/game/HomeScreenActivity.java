package com.drmmx.devmax.pokergame.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.drmmx.devmax.pokergame.R;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, PlayerCountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() { }
}
