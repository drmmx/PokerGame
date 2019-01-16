package com.drmmx.devmax.pokergame.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.drmmx.devmax.pokergame.R;

public class PlayerCountActivity extends AppCompatActivity {

    int playerCount = 2;
    int playerMoney = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_count);
    }

    public void increasePlayerCount(View view) {
        if(playerCount < 10)
            playerCount = playerCount + 1;
        displayPlayerCount(playerCount);
    }

    public void decreasePlayerCount(View view) {
        if(playerCount > 2)
            playerCount = playerCount - 1;
        displayPlayerCount(playerCount);
    }

    private void displayPlayerCount(int number) {
        TextView displayInteger = findViewById(R.id.playerCount);
        displayInteger.setText(String.valueOf(number));
    }

    public void increasePlayerMoney(View view){
        playerMoney = playerMoney + 5000;
        displayPlayerMoney(playerMoney);
    }

    public void decreasePlayerMoney(View view){
        if(playerMoney > 5000)
            playerMoney = playerMoney - 5000;
        displayPlayerMoney(playerMoney);
    }

    public void displayPlayerMoney(int number){
        TextView displayInteger = findViewById(R.id.playerMoney);
        displayInteger.setText(new StringBuilder("$").append(String.valueOf(number)));
    }

    public void sendPlayerCount(View view){
        Intent intent = new Intent(this, NamePlayersActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("com.drmmx.devmax.playerAmount", playerCount);
        extras.putInt("com.drmmx.devmax.playerStartingMoney", playerMoney);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
