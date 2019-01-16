package com.drmmx.devmax.pokergame.game;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.drmmx.devmax.pokergame.R;

import java.util.ArrayList;

public class PlayGameUserInputFragment extends Fragment implements View.OnClickListener {

    public interface Listener {
        void onFoldButtonClicked();
        void onCallButtonClicked();
        void onBetButtonClicked(int betAmount);
    }

    PlayGameUserInputFragment.Listener mListener = null;

    Board currentGame;
    ArrayList<Player> players;
    int loop;

    public void setArguments(Board iCurrentGame, ArrayList<Player> iPlayers, int iLoop){
        currentGame = iCurrentGame;
        players = iPlayers;
        loop = iLoop;
    }

    public void updateUI(Board currentGame, ArrayList<Player> players, int iLoop){
        loop = iLoop;
        if (getActivity() == null) return;
        TextView playerInformation = getActivity().findViewById(R.id.PlayGameUserInput_playerInformation);
        StringBuilder playerInformationString = new StringBuilder();
        playerInformation.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray));
        for (int i = 0; i < players.size() - 1; i++) {
            if (players.get(i).printState().equals("folded"))
                playerInformationString.append(players.get(i).printName()).append(" (folded)- $").append(players.get(i).printMoney()).append("   ");
            else
                playerInformationString.append(players.get(i).printName()).append(" - $").append(players.get(i).printMoney()).append("   ");
        }
        if (players.get(players.size() - 1).printState().equals("folded"))
            playerInformationString.append(players.get(players.size() - 1).printName()).append(" (folded)- $").append(players.get(players.size() - 1).printMoney());
        else
            playerInformationString.append(players.get(players.size() - 1).printName()).append(" - $").append(players.get(players.size() - 1).printMoney());
        playerInformation.setText(playerInformationString.toString());
        TextView PlayerName = getActivity().findViewById(R.id.PlayerName);
        PlayerName.setText(new StringBuilder("It is now your turn ").append(players.get(loop).printName()));
        TextView River = getActivity().findViewById(R.id.River);
        River.setText(new StringBuilder("River: ").append(currentGame.getRiver().print()));
        Button Call = getActivity().findViewById(R.id.Call);
        Call.setText(Game.callButton(players.get(loop), currentGame));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play_game_user_input, container, false);

        LinearLayout playerNameScroll = v.findViewById(R.id.PlayerInfo);
        TextView playerInformation = new TextView(getActivity());
        playerInformation.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray));
        playerInformation.setId(R.id.PlayGameUserInput_playerInformation);
        StringBuilder playerInformationString = new StringBuilder();
        for (int i = 0; i < players.size() - 1; i++) {
            if (players.get(i).printState().equals("folded"))
                playerInformationString.append(players.get(i).printName()).append(" (folded)- $").append(players.get(i).printMoney()).append("   ");
            else
                playerInformationString.append(players.get(i).printName()).append(" - $").append(players.get(i).printMoney()).append("   ");
        }
        if (players.get(players.size() - 1).printState().equals("folded"))
            playerInformationString.append(players.get(players.size() - 1).printName()).append(" (folded)- $").append(players.get(players.size() - 1).printMoney());
        else
            playerInformationString.append(players.get(players.size() - 1).printName()).append(" - $").append(players.get(players.size() - 1).printMoney());
        playerInformation.setText(playerInformationString.toString());
        playerInformation.setTextSize(18);
        playerNameScroll.addView(playerInformation);
        TextView PlayerName = v.findViewById(R.id.PlayerName);
        PlayerName.setText(new StringBuilder("It is now your turn ").append(players.get(loop).printName()));
        TextView River = v.findViewById(R.id.River);
        River.setText(new StringBuilder("River: ").append(currentGame.getRiver().print()));
        Button Hand = v.findViewById(R.id.Hand);
        Hand.setOnClickListener(this);
        Button Call = v.findViewById(R.id.Call);
        Call.setText(Game.callButton(players.get(loop), currentGame));
        Call.setOnClickListener(this);
        Button Fold = v.findViewById(R.id.Fold);
        Fold.setOnClickListener(this);
        Button Bet = v.findViewById(R.id.Bet);
        Bet.setOnClickListener(this);

        return v;
    }

    public void setListener(PlayGameUserInputFragment.Listener l) {
        mListener = l;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.Hand:
                onHandButtonClicked();
                break;
            case R.id.Fold:
                mListener.onFoldButtonClicked();
                break;
            case R.id.Call:
                mListener.onCallButtonClicked();
                break;
            case R.id.Bet:
                openBetDialog();
                break;
        }
    }

    public void onHandButtonClicked(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hand")
                .setMessage(players.get(loop).getHand().print())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { }
                }).show();
    }

    public void openBetDialog(){
        final int minBet = currentGame.getMaxBet() - players.get(loop).getAmountBet();

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout displayBetAmount = new LinearLayout(getActivity());
        displayBetAmount.setOrientation(LinearLayout.HORIZONTAL);
        displayBetAmount.setGravity(Gravity.CENTER);

        final TextView betAmountTitle = new TextView(getActivity());
        betAmountTitle.setText("Bet: $");
        displayBetAmount.addView(betAmountTitle);

        final EditText showBetAmount = new EditText(getActivity());
        showBetAmount.setText(String.valueOf(minBet));
        showBetAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        displayBetAmount.addView(showBetAmount);

        layout.addView(displayBetAmount);
        showBetAmount.setSelection(showBetAmount.getText().length());

        final SeekBar betAmount = new SeekBar(getActivity());
        betAmount.setMax(players.get(loop).printMoney() - minBet);
        betAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showBetAmount.setText(String.valueOf(betAmount.getProgress() + minBet));
                showBetAmount.setSelection(showBetAmount.getText().length());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        layout.addView(betAmount);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Bet")
                .setMessage("Choose amount to bet:")
                .setView(layout)
                .setPositiveButton("Bet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(Integer.parseInt(showBetAmount.getText().toString()) < minBet || Integer.parseInt(showBetAmount.getText().toString()) > players.get(loop).printMoney()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Error")
                                    .setMessage("Bet amount must be between $" + minBet + " and $" + players.get(loop).printMoney())
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) { }
                                    }).show();
                        }
                        else
                            mListener.onBetButtonClicked(Integer.parseInt(showBetAmount.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) { }
                }).show();
    }
}
