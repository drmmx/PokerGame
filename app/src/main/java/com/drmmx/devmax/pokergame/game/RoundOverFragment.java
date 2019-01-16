package com.drmmx.devmax.pokergame.game;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.drmmx.devmax.pokergame.R;

import java.util.ArrayList;

public class RoundOverFragment extends Fragment implements View.OnClickListener {

    public interface Listener {
        void onShowStateButtonClicked();
    }

    RoundOverFragment.Listener mListener = null;

    int playerAmount;
    ArrayList<String> winnerInfo;
    String river;

    public void setArguments(int iPlayerAmount, ArrayList<String> iWinnerInfo, String iRiver){
        playerAmount = iPlayerAmount;
        winnerInfo = iWinnerInfo;
        river = iRiver;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_round_over, container, false);

        LinearLayout displayWinners = v.findViewById(R.id.displayWinners);
        displayWinners.setGravity(1);

        TextView winners = new TextView(getActivity());
        winners.setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
        winners.setGravity(Gravity.CENTER);
        StringBuilder winnerNames = new StringBuilder();
        for(int i = 1; i < (winnerInfo.size() - (playerAmount*3)); i++)
            winnerNames.append(winnerInfo.get(i));
        winners.setText(winnerNames.toString());
        winners.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        winners.setTextSize(34);
        winners.setTypeface(null, Typeface.BOLD);
        displayWinners.addView(winners);

        TextView riverText = new TextView(getActivity());
        riverText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
        riverText.setText(new StringBuilder("River - ").append(river));
        riverText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        riverText.setTextSize(24);
        displayWinners.addView(riverText);

        TextView[] playerTextView = new TextView[playerAmount];
        int j = 0;
        for(int i = (winnerInfo.size() - (playerAmount*3)); i < (winnerInfo.size()); i=i+3){
            playerTextView[j] = new TextView(getActivity());
            playerTextView[j].setTextColor(ContextCompat.getColor(v.getContext(), R.color.gray));
            playerTextView[j].setText(new StringBuilder(winnerInfo.get(i)).append(winnerInfo.get(i+1)).append(winnerInfo.get(i+2)));
            playerTextView[j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            playerTextView[j].setTextSize(24);
            displayWinners.addView(playerTextView[j]);
            j++;
        }

        Button Enter = new Button(getActivity());
        Enter.setText(String.valueOf("Continue"));
        Enter.setTextSize(14);
        Enter.setOnClickListener(this);
        Enter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        displayWinners.addView(Enter);
        return v;
    }

    public void setListener(RoundOverFragment.Listener l) {
        mListener = l;
    }

    @Override
    public void onClick(View v){
        mListener.onShowStateButtonClicked();
    }
}
