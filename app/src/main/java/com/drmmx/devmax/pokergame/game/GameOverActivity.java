package com.drmmx.devmax.pokergame.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.drmmx.devmax.pokergame.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        final String winnerName = intent.getStringExtra("com.drmmx.devmax.winnerName");

        TextView WinnerName = findViewById(R.id.winnerName);
        WinnerName.setText(new StringBuilder(winnerName).append(" has won the game! Congratulations!"));
    }

    public void restartGame(View view) {
        Intent i = new Intent(this, HomeScreenActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }

    @Override
    public void onBackPressed() { }
}
