package com.drmmx.devmax.pokergame.game;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.drmmx.devmax.pokergame.R;

import java.util.ArrayList;

public class GameStateFragment extends Fragment implements View.OnClickListener {

    public interface Listener {
        void onStartRoundButtonClicked();
    }

    Listener mListener = null;

    ArrayList<Player> players;
    int blind;
    int smallBlind;
    int gameRound;

    public void setArguments(ArrayList<Player> iPlayers, int iBlind, int iSmallBlind, int iGameRound){
        players = iPlayers;
        blind = iBlind;
        smallBlind = iSmallBlind;
        gameRound = iGameRound;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_state, container, false);

        LinearLayout playerNameScroll = v.findViewById(R.id.LinearLayout);
        playerNameScroll.setGravity(1);

        TextView displayRound = new TextView(getActivity());
        displayRound.setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
        displayRound.setText(new StringBuilder("Round ").append(gameRound));
        displayRound.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        displayRound.setTextSize(56);
        displayRound.setTypeface(null, Typeface.BOLD);
        playerNameScroll.addView(displayRound);

        TextView blindTitle = new TextView(getActivity());
        blindTitle.setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
        blindTitle.setText(String.valueOf("Blinds"));
        blindTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        blindTitle.setTextSize(34);
        blindTitle.setTypeface(null, Typeface.BOLD);
        playerNameScroll.addView(blindTitle);

        TextView blind1 = new TextView(getActivity());
        blind1.setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
        blind1.setText(new StringBuilder(players.get(blind).printName()).append(" - $").append(smallBlind));
        blind1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        blind1.setTextSize(24);
        playerNameScroll.addView(blind1);

        TextView blind2 = new TextView(getActivity());
        blind2.setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
        blind2.setText(new StringBuilder(players.get((blind + 1) % players.size()).printName()).append(" - $").append(smallBlind * 2));
        blind2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        blind2.setTextSize(24);
        playerNameScroll.addView(blind2);

        TextView playerTitle = new TextView(getActivity());
        playerTitle.setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
        playerTitle.setText(String.valueOf("Players"));
        playerTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        playerTitle.setTextSize(34);
        playerTitle.setTypeface(null, Typeface.BOLD);
        playerNameScroll.addView(playerTitle);

        TextView[] playerTextView = new TextView[players.size()];
        for(int i = 0; i < players.size(); i++){
            playerTextView[i] = new TextView(getActivity());
            playerTextView[i].setText(new StringBuilder(players.get(i).printName()).append(" - $").append(players.get(i).printMoney()));
            playerTextView[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            playerTextView[i].setTextSize(24);
            playerTextView[i].setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
            playerNameScroll.addView(playerTextView[i]);
        }

        Button enterBtn = new Button(getActivity());
        enterBtn.setText(String.valueOf("Continue"));
        enterBtn.setTextSize(14);
        enterBtn.setOnClickListener(this);
        enterBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        playerNameScroll.addView(enterBtn);

        return v;
    }

    public void setListener(Listener l) {
        mListener = l;
    }

    @Override
    public void onClick(View v){
        mListener.onStartRoundButtonClicked();
    }
}
