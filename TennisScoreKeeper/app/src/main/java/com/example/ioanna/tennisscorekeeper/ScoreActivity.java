package com.example.ioanna.tennisscorekeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    //Count how many sets win each player
    int player1SetsWins = 0;
    int player2SetsWins = 0;
    //Count Tie Break Point
    private int player1TieBreakPoints = 0;
    private int player2TieBreakPoints = 0;
    //Deuce flag
    private boolean deuceMode = false;
    //Player1 Advantage flag
    private boolean player1AdMode = false;
    //Player2 Advantage flag
    private boolean player2AdMode = false;
    //Tie Break mode
    private boolean tieBreakMode = false;
    //Maximun set number
    private int maxSets;
    //Number of current set
    private int numOfSets = 1;
    //Match timer
    private Chronometer matchTimer;
    private boolean resetMatch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_score);
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                LoadPlayers(extras);
            } else {
                //newString= extras.getString("STRING_I_NEED");
            }
            //Initialize score and sets
            resetPoints();
            resetCurrentSet();
            //Start match timer
            //Match Timer
            matchTimer = (Chronometer) findViewById(R.id.matchTimer); // initiate a chronometer
            matchTimer.setFormat("Time - %s"); // set the format for a chronometer
            matchTimer.start(); // start a chronometer
        }
    }

    //Initialize score
    private void scoreInitialization(){
        resetPoints();
        resetAllSets();
    }

    private void LoadPlayers(Bundle extras){
        String gameType= extras.getString("GAMETYPE");
        TextView playersTeamsView = (TextView) findViewById(R.id.players_teams);
        if (gameType.equals("single")){
            playersTeamsView.setText("Players");
        }
        else{
            playersTeamsView.setText("Teams");
        }
        //Score Area
        //Player1
        TextView player1View = (TextView) findViewById(R.id.player_team1);
        player1View.setText(extras.getString("TEAM1"));
        //Player2
        TextView player2View = (TextView) findViewById(R.id.player_team2);
        player2View.setText(extras.getString("TEAM2"));
        //Button Area
        //Player1
        TextView player1ButView = (TextView) findViewById(R.id.player1_but_section);
        player1ButView.setText(extras.getString("TEAM1"));
        //Player2
        TextView player2ButView = (TextView) findViewById(R.id.player2_but_section);
        player2ButView.setText(extras.getString("TEAM2"));

        //Get max match sets
        maxSets = extras.getInt("SETS");
    }

    //Change player1 's score
    public void player1GetPoint(View view){
        try{
            if (tieBreakMode)   {
                TextView setsView = (TextView)findViewById(getResources().getIdentifier( "player1Set" + numOfSets , "id", getPackageName()));
                getTieBreakPoint(setsView,true);
            }
            else{
                player1GetGamePoint();
            }
        }
       catch (Exception e){

       }
    }

    //Change player2 's score
    public void player2GetPoint(View view){
        try{
            if (tieBreakMode)   {
                TextView setsView = (TextView)findViewById(getResources().getIdentifier( "player2Set" + numOfSets , "id", getPackageName()));
                getTieBreakPoint(setsView,false);
            }
            else{
                player2GetGamePoint();
            }
        }catch (Exception e){

        }
    }

    //Player get Tie Break point
    private void getTieBreakPoint(TextView setView, Boolean player1){
        try{
            int points;
            if (player1)
                points = ++player1TieBreakPoints;
            else
                points = ++player2TieBreakPoints;

            int gameValue = Integer.parseInt((setView.getText().toString()).substring(0,1));
            SpannableStringBuilder cs = new SpannableStringBuilder("" +  gameValue + points );
            cs.setSpan(new SuperscriptSpan(), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setView.setText(cs, TextView.BufferType.SPANNABLE);
            //Winning the Tie Break: Player need to reach  at least 7 points but have to be clear by at least 2 points
            if (player1TieBreakPoints >=7 && (player1TieBreakPoints - player2TieBreakPoints) > 1 ){
                resetTieBreakPoints();
                //Player 1 win the tie break and also the set
                player1SetsWins++;
                tieBreakMode = false;
                showInformaton(null);
                //Find the winner or create a new set
                continueGame();
            }
            else if(player2TieBreakPoints >=7 && (player2TieBreakPoints - player1TieBreakPoints) > 1){
                resetTieBreakPoints();
                //Player 2 win the tie break and also the set
                player2SetsWins++;
                tieBreakMode = false;
                showInformaton(null);
                //Find the winner or create a new set
                continueGame();
            }
        }catch (Exception e){

        }
    }

    //Player 1 get game point
    private void player1GetGamePoint(){
        TextView pointsView = (TextView) findViewById(R.id.player1Points);
        TextView setsView = (TextView)findViewById(getResources().getIdentifier( "player1Set" + numOfSets , "id", getPackageName()));
        TextView competitorPointsView = (TextView) findViewById(R.id.player2Points);
        TextView competitorSetsView  = (TextView) findViewById(getResources().getIdentifier( "player2Set" + numOfSets , "id", getPackageName()));
        //Player who win the first point after deuce, he/she is in advantage
        if (deuceMode == true){
            player1AdMode = true;
            setPlayerAdvantage((TextView) findViewById(R.id.player1Adv));
            deuceMode = false;
            showInformaton(null);
        }
        else if(player2AdMode == true){
            //if player2 is in advantage and player1 wins, it goes back to deuce
            deuceMode = true;
            showInformaton("Deuce");
            player2AdMode = false;
            removePlayerAdvantage((TextView) findViewById(R.id.player2Adv));
        }
        else if(player1AdMode == true) {
            //Player1 wins two points in a row
            player1AdMode = false;
            removePlayerAdvantage((TextView) findViewById(R.id.player1Adv));
            //Get the game
            updateGameScore(setsView,competitorSetsView,true);
        }
        else {
            //Get the point
            updatePointScore(pointsView, setsView,competitorPointsView,competitorSetsView,true);
        }
    }

    //Player 2 get game point
    private void player2GetGamePoint(){
        TextView pointsView = (TextView) findViewById(R.id.player2Points);
        TextView setsView = (TextView)findViewById(getResources().getIdentifier( "player2Set" + numOfSets , "id", getPackageName()));
        TextView competitorPointsView = (TextView) findViewById(R.id.player1Points);
        TextView competitorSetsView  = (TextView) findViewById(getResources().getIdentifier( "player1Set" + numOfSets , "id", getPackageName()));
        //Player who win the first point after deuce, he/she is in advantage
        if (deuceMode == true){
            player2AdMode = true;
            setPlayerAdvantage((TextView) findViewById(R.id.player2Adv));
            deuceMode = false;
            showInformaton(null);
        }
        else if(player1AdMode == true){
            //if player1 is in advantage and player2 wins, it goes back to deuce
            deuceMode = true;
            showInformaton("Deuce");
            player1AdMode = false;
            removePlayerAdvantage((TextView) findViewById(R.id.player1Adv));
        }
        else if(player2AdMode == true) {
            //Player2 wins two points in a row
            player2AdMode = false;
            removePlayerAdvantage((TextView) findViewById(R.id.player2Adv));
            //Get the game
            updateGameScore(setsView,competitorSetsView, false);
        }
        else {
            //Get the point
            updatePointScore(pointsView, setsView,competitorPointsView,competitorSetsView,false);
        }
    }

    //Calculate next point
    private void updatePointScore(TextView winnerPointsView, TextView winnerSetsView,TextView competitorPointsView,TextView competitorSetsView, boolean player1){
        //Get Points
        int points = Integer.parseInt(winnerPointsView.getText().toString());
        int competitorPoints = Integer.parseInt(competitorPointsView.getText().toString());
        switch (points) {
            case 0: winnerPointsView.setText("" + 15);
                    break;
            case 15: winnerPointsView.setText("" +  30);
                     break;
            case 30: winnerPointsView.setText("" +  40);
                     //Deuce - Score is tied at 40
                     if (competitorPoints == 40){
                         deuceMode = true;
                         showInformaton("Deuce");
                     }
                      break;
            case 40://after 40 point get a game
                    if (competitorPoints != 40){
                        updateGameScore(winnerSetsView,competitorSetsView,player1);
                     }
        }
    }

    //Update game score
    private void updateGameScore(TextView winnerSetsView,TextView compSetsView, boolean player1){
        //Get players score
        int winnerGameValue = Integer.parseInt(winnerSetsView.getText().toString()) + 1;
        int compGameValue = Integer.parseInt(compSetsView.getText().toString());

        //Update set - game value
        winnerSetsView.setText("" +  winnerGameValue);

        //Winning a set: Player need to reach 6 games but have to be clear by at least 2 games
        if( winnerGameValue >= 6 && ((winnerGameValue - compGameValue) > 1 )) {
            //Win the current Set
            getTheSet(player1);
        }
        else if(winnerGameValue == 6 && compGameValue == 6){
            // A tiebreak is played to decide the winner of the set
            // when the each of the players have won six games each
            showInformaton("Set Tie Break");
            tieBreakMode = true;
        }
        //Reset Points
        resetPoints();
}

    //Update set score
    private void getTheSet(boolean player1){
        //Increase set winner counter
        if (player1 == true)
            player1SetsWins++;
        else
            player2SetsWins++;
        //Find the winner or create a new set
        continueGame();
        //Reset Points
        resetPoints();
    }

    //Reset points for player1 and player2
    private void resetPoints(){
        //Player1
        TextView player1PointsView = (TextView) findViewById(R.id.player1Points);
        player1PointsView.setText("0");
        //Player2
        TextView player2PointsView = (TextView) findViewById(R.id.player2Points);
        player2PointsView.setText("0");
    }

    //Reset all sets for player1 and player2
    private void resetAllSets(){
        try{
            for (int i=1;i<=maxSets;++i){
                //Player1
                TextView player1SetsView = (TextView)findViewById(getResources().getIdentifier( "player1Set" + i , "id", getPackageName()));
                player1SetsView.setText("0");
                //Player2
                TextView player2SetsView = (TextView)findViewById(getResources().getIdentifier( "player2Set" + i , "id", getPackageName()));
                player2SetsView.setText("0");
            }
        }catch (Exception ex){

        }
    }

    //Reset current set
    private void resetCurrentSet(){
        try{
            //Player1
            TextView player1SetsView = (TextView)findViewById(getResources().getIdentifier( "player1Set" + numOfSets , "id", getPackageName()));
            player1SetsView.setText("0");
            //Player2
            TextView player2SetsView = (TextView)findViewById(getResources().getIdentifier( "player2Set" + numOfSets , "id", getPackageName()));
            player2SetsView.setText("0");
        }catch (Exception ex){

        }
    }

    //Create set column on score table
    private void createNextSetColumn(){
        try{
            if (numOfSets < maxSets) {
                numOfSets++;
                LinearLayout scoreLayout = (LinearLayout) findViewById(R.id.layout_score);

                //Create new linear layout
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                //Create new TextView for the next Set
                TextView nextSet = new TextView(new ContextThemeWrapper(this, R.style.ScoreHeader));
                nextSet.setText("Set " + numOfSets);
                layout.addView(nextSet,0);

                //Create new TextView of the next Set for the Player 1
                TextView nextPlayer1Set = new TextView(new ContextThemeWrapper(this, R.style.ScoreItems));
                nextPlayer1Set.setId(getResources().getIdentifier( "player1Set" + numOfSets , "id", getPackageName()));
                layout.addView(nextPlayer1Set);

                //Create new TextView of the next Set for the Player 2
                TextView nextPlayer2Set = new TextView(new ContextThemeWrapper(this, R.style.ScoreItems));
                nextPlayer2Set.setId(getResources().getIdentifier( "player2Set" + numOfSets , "id", getPackageName()));
                layout.addView(nextPlayer2Set,2);

                //Add new layout to the score layout
                scoreLayout.addView(layout,numOfSets);
                //Reset new set
                resetCurrentSet();
            }
        }catch (Exception ex){

        }
    }

    //Disable point buttons
    private void disablePointButtons(){
        TextView butPlayer1Points = (TextView) findViewById(R.id.btnPlayer1Points);
        butPlayer1Points.setEnabled(false);
        TextView butPlayer2Points = (TextView) findViewById(R.id.btnPlayer2Points);
        butPlayer2Points.setEnabled(false);
    }

    //Show general info
    private void showInformaton(String message){
        TextView txtMessage = (TextView) findViewById(R.id.info_area);
        txtMessage.setText(message);
    }

    //Set player advantage
    private void setPlayerAdvantage(TextView userInfoView){
        userInfoView.setText("Ads");
    }

    //Remove player advantage
    private void removePlayerAdvantage(TextView userInfoView){
        userInfoView.setText(null);
    }

    //Show match winner
    private void getMatchWiiner(){
        //Disable point buttons
        disablePointButtons();
        //Stop timer
        matchTimer.stop();
        //Find match winner
        if (player1SetsWins > player2SetsWins) {
            TextView player1View = (TextView) findViewById(R.id.player_team1);
            showInformaton("Match Winner: " +  player1View.getText().toString());
            openDialog("Match Winner",player1View.getText().toString() + " !!");
        }
        else {
            TextView player2View = (TextView) findViewById(R.id.player_team2);
            showInformaton("Match Winner: " + player2View.getText().toString());
            openDialog("Match Winner",player2View.getText().toString()+ " !!");
        }
    }

    private void openDialog(String title, String msg) {;
        AlertDialog winDialog = new AlertDialog.Builder(this,R.style.CustomAlertDialogTheme).create();
        winDialog.setTitle(title);
        winDialog.setMessage(msg);
        winDialog.setCancelable(false);
        winDialog.setButton(AlertDialog.BUTTON_POSITIVE, "New Match",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent scoreLayout = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(scoreLayout);
                    }
                });
        winDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reset Match",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Reset relative counters
                        numOfSets=1;
                        player1SetsWins = 0;
                        player2SetsWins = 0;
                        resetMatch = true;
                        //Initialize score
                        scoreInitialization();
                        //Enable points buttons
                        TextView butPlayer1Points = (TextView) findViewById(R.id.btnPlayer1Points);
                        butPlayer1Points.setEnabled(true);
                        TextView butPlayer2Points = (TextView) findViewById(R.id.btnPlayer2Points);
                        butPlayer2Points.setEnabled(true);
                        //Restart timer
                        matchTimer.stop();
                        matchTimer.start();
                    }
                });
        winDialog.show();
    }

    public void resetMatch(View view){
        //Reset relative counters
        numOfSets=1;
        player1SetsWins = 0;
        player2SetsWins = 0;
        //Initialize score
        scoreInitialization();
        //Enable points buttons
        TextView butPlayer1Points = (TextView) findViewById(R.id.btnPlayer1Points);
        butPlayer1Points.setEnabled(true);
        TextView butPlayer2Points = (TextView) findViewById(R.id.btnPlayer2Points);
        butPlayer2Points.setEnabled(true);
        resetMatch = true;
        //Restart timer
        matchTimer.setBase(SystemClock.elapsedRealtime());
        matchTimer.start();
    }

    //Reset Tie Break points
    private void resetTieBreakPoints(){
        player1TieBreakPoints = 0;
        player2TieBreakPoints = 0;
    }

    //Check if the match reached the max sets and find the winner
    // or create the next set
    private void continueGame(){
        if (player1SetsWins + player2SetsWins == maxSets){
            getMatchWiiner();
        }
        else {
            if (!resetMatch)
                //Create new set if needed
                createNextSetColumn();
            else
                numOfSets++;
        }
    }
}