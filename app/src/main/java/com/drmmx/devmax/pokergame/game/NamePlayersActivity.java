package com.drmmx.devmax.pokergame.game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.drmmx.devmax.pokergame.R;

public class NamePlayersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_players);
        final Context context = this;

        Bundle extras = getIntent().getExtras();
        final int playerAmount = extras.getInt("com.drmmx.devmax.playerAmount", 0);
        final int playerMoney = extras.getInt("com.drmmx.devmax.playerStartingMoney", 0);

        final EditText[] playerNames = new EditText[playerAmount];
        TextView[] playerLabels = new TextView[playerAmount];
        LinearLayout playerNameScroll = findViewById(R.id.LinearLayout);
        playerNameScroll.setGravity(1);
        for(int i = 0; i < playerAmount; i++) {
            playerLabels[i] = new TextView(this);
            playerLabels[i].setText(new StringBuilder("Player ").append(i+1).append(" name:"));
            playerLabels[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            playerLabels[i].setMaxLines(1);
            playerLabels[i].setTextSize(25);
            playerLabels[i].setTextColor(ContextCompat.getColor(context, R.color.gray));
            playerNameScroll.addView(playerLabels[i]);
            playerNames[i] = new EditText(this);
            playerNames[i].setLayoutParams(new LinearLayout.LayoutParams(1000, LinearLayout.LayoutParams.WRAP_CONTENT));
            playerNames[i].setSingleLine(true);
            playerNames[i].setTextSize(25);
            playerNames[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            playerNames[i].setTextColor(ContextCompat.getColor(context, R.color.gray));
            playerNameScroll.addView(playerNames[i]);
        }

        Button Enter = new Button(this);
        Enter.setText(String.valueOf("Continue"));
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean duplicates=false;
                for (int j=0;j<playerAmount;j++) {
                    for (int k = j + 1; k < playerAmount; k++) {
                        if ((k != j && playerNames[k].getText().toString().equals(playerNames[j].getText().toString())) || (playerNames[j].getText().toString().isEmpty() || playerNames[k].getText().toString().isEmpty()))
                            duplicates = true;
                    }
                }
                if(!duplicates) {
                    String[] playerNameArray = new String[playerAmount];
                    for(int i = 0; i < playerAmount; i++) {
                        playerNameArray[i] = playerNames[i].getText().toString();
                    }
                    sendPlayerInfo(playerNameArray, playerMoney);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Error")
                            .setMessage("All names must be unique.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) { }
                            }).show();
                }
            }
        });
        Enter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        playerNameScroll.addView(Enter);
    }

    public void sendPlayerInfo(String[] playerNameArray, int playerMoney){
        Intent intent = new Intent (this, PlayGameActivity.class);
        Bundle extras = new Bundle();
        extras.putStringArray("com.drmmx.devmax.playerNameArray", playerNameArray);
        extras.putInt("com.drmmx.devmax.playerStartingMoney", playerMoney);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
