package com.drmmx.devmax.pokergame.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.drmmx.devmax.pokergame.R;

import java.util.ArrayList;

public class PlayGameActivity extends AppCompatActivity implements GameStateFragment.Listener, PlayGameUserInputFragment.Listener, RoundOverFragment.Listener {
    //Fragments
    GameStateFragment gameStateFragment;
    PlayGameUserInputFragment playGameUserInputFragment;
    RoundOverFragment roundOverFragment;

    //initialize game variables
    Board currentGame = new Board();
    int playerStartingMoney;
    int smallBlind;
    int blind = 0; //start blind with first player
    int gameRound = 1; //start with first round
    ArrayList<Player> players = new ArrayList<>();
    int loop;
    int round = 1;
    CardSet deck = new CardSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        //Get player names from previous page
        Bundle extras = getIntent().getExtras();
        String[] playerNameArray = extras.getStringArray("com.drmmx.devmax.playerNameArray");
        playerStartingMoney = extras.getInt("com.drmmx.devmax.playerStartingMoney", 0);
        smallBlind = playerStartingMoney / 50; //small blind starts as 1/50 of amount of money players have

        players = Game.playerSetup(playerNameArray, playerStartingMoney);
        loop = (blind + 2) % players.size();

        gameStateFragment = new GameStateFragment();
        playGameUserInputFragment = new PlayGameUserInputFragment();
        roundOverFragment = new RoundOverFragment();

        gameStateFragment.setListener(this);
        playGameUserInputFragment.setListener(this);
        roundOverFragment.setListener(this);

        //start by showing game state
        gameStateFragment.setArguments(players, blind, smallBlind, gameRound);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, gameStateFragment).commit();
    }

    // Switch UI to the given fragment
    void switchToFragment(Fragment newFrag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFrag).commit();
    }

    @Override
    public void onStartRoundButtonClicked(){
        startRound();
    }

    @Override
    public void onFoldButtonClicked(){
        Game.turnAction(players.get(loop), players, currentGame, "Fold", 0);
        loop = (loop + 1) % players.size();
        takeTurn();
    }

    @Override
    public void onCallButtonClicked(){
        String callButtonText = Game.callButton(players.get(loop), currentGame);
        if(callButtonText.equals("Check"))
            Game.turnAction(players.get(loop), players, currentGame, "Check", 0);
        else
            Game.turnAction(players.get(loop), players, currentGame, "Call", 0);
        loop = (loop + 1) % players.size();
        takeTurn();
    }

    @Override
    public void onBetButtonClicked(int betAmount){
        Game.turnAction(players.get(loop), players, currentGame, "Bet", betAmount);
        loop = (loop + 1) % players.size();
        takeTurn();
    }

    @Override
    public void onShowStateButtonClicked(){
        gameStateFragment.setArguments(players, blind, smallBlind, gameRound);
        switchToFragment(gameStateFragment);
    }

    public void startRound(){
        deck = Functions.populate(deck); //fill deck w/ all 52 cards
        deck.shuffle();
        Game.roundSetup(currentGame, players, blind, smallBlind, deck); //deal cards and pay blinds
        playGameUserInputFragment.setArguments(currentGame, players, loop);
        switchToFragment(playGameUserInputFragment);
        takeTurn();
    }

    public void takeTurn(){
        ArrayList<String> roundState;
        roundState = Game.iterateBetRound(currentGame, players, round, deck);
        if(!roundState.get(0).equals("continue") && !roundState.get(0).equals("nextRound")){
            roundOver(roundState);
        }
        else {
            if(roundState.get(0).equals("nextRound"))
                round++;
            if (players.get(loop).printMoney() == 0) {
                players.get(loop).setState("active|b");
                loop = (loop + 1) % players.size();
                takeTurn();
            } else if (!players.get(loop).printState().equals("folded")) {
                playGameUserInputFragment.updateUI(currentGame, players, loop);
            }  else {
                loop = (loop + 1) % players.size();
                takeTurn();
            }
        }
    }

    public void roundOver(ArrayList<String> winnerInfo){
        int currentPlayers = 0;
        for (int i = 0; i < players.size(); i++) { //find amount of players still in round
            if (!players.get(i).printState().equals("folded"))
                currentPlayers++;
        }
        for (int i = 0; i < players.size();) { //remove players if they busted
            if (players.get(i).printMoney() == 0) {
                players.remove(i);
                i = 0;
            }
            else
                i++;
        }
        if(players.size() == 1){
            gameOver(players.get(0).printName());
        }
        else {
            roundOverFragment.setArguments(currentPlayers, winnerInfo, currentGame.getRiver().print());
            switchToFragment(roundOverFragment);
            Game.resetRound(currentGame, players);
            if (gameRound % 5 == 0)
                smallBlind += 200;
            blind = (blind + 1) % players.size();
            gameRound = gameRound + 1;
            loop = (blind + 2) % players.size();
            round = 1;
        }
    }

    public void gameOver(String winnerName){
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("com.drmmx.devmax.winnerName", winnerName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() { }
}
