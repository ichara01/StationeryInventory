package com.example.ioanna.tennisscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ioanna.tennisscorekeeper.R.id.spinner_set_num;

public class MainActivity extends AppCompatActivity {

    private boolean singleGame = false;
    private Spinner spSetsNum;
    private int setsNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spSetsNum = (Spinner) findViewById(spinner_set_num);
        spSetsNum.setOnItemSelectedListener(new ItemSelectedListener());
    }

    /** Called when the user click on the Start button in order to
     *  represent the score layout */
    public void startMatch(View view){
        Intent scoreLayout = new Intent(this, ScoreActivity.class);
        TextView team1InfoView = (TextView) findViewById(R.id.team1_value);
        TextView team2InfoView = (TextView) findViewById(R.id.team2_value);
        //if data is valid, start the match
        if(dataValidation(team1InfoView,team2InfoView))
        {
            if (singleGame == true)
                scoreLayout.putExtra("GAMETYPE","single");
            else
                scoreLayout.putExtra("GAMETYPE","double");
            scoreLayout.putExtra("TEAM1",team1InfoView.getText().toString());
            scoreLayout.putExtra("TEAM2",team2InfoView.getText().toString());
            scoreLayout.putExtra("SETS",setsNum);
            startActivity(scoreLayout);
        }
        else   {
            Toast.makeText(getApplicationContext(),"Please complete all information before start the match",Toast.LENGTH_SHORT).show();
        }
    }
    //Get selected game type
    public void selectedGameType(View view) {
        // Is the button now checked?
        boolean isSelected = ((RadioButton) view).isChecked();
        if(isSelected) {
            //Enable start button
            Button startBtn = (Button) findViewById(R.id.start_match);
            startBtn.setEnabled(true);
        }
        // Check which game type was selected
        switch(view.getId()) {
            case R.id.single_game:
                if (isSelected) {
                    //Show players info form
                    View team1Info = (View) findViewById(R.id.team1_info);
                    team1Info.setVisibility(View.VISIBLE);
                    View team2Info = (View) findViewById(R.id.team2_info);
                    team2Info.setVisibility(View.VISIBLE);
                    //Set players label
                    TextView team1View = (TextView) findViewById(R.id.team1_label);
                    team1View.setText("Player 1:");
                    TextView team2View = (TextView) findViewById(R.id.team2_label);
                    team2View.setText("Player 2:");
                    singleGame = true;
                }
                break;
            case R.id.double_game:
                if (isSelected){
                    //Show teams info form
                    View team1Info = (View) findViewById(R.id.team1_info);
                    team1Info.setVisibility(View.VISIBLE);
                    View team2Info = (View) findViewById(R.id.team2_info);
                    team2Info.setVisibility(View.VISIBLE);
                    //Set teams label
                    TextView team1View = (TextView) findViewById(R.id.team1_label);
                    team1View.setText("Team A:");
                    TextView team2View = (TextView) findViewById(R.id.team2_label);
                    team2View.setText("Team B@");
                    singleGame = false;
                }
                break;
        }
    }

    private boolean dataValidation(TextView team1InfoView,TextView team2InfoView){
        //TeamA / Player1 name is required
        if (team1InfoView.getText().toString().trim().isEmpty())
            return false;
        //TeamB / Player2 name is required
        if (team2InfoView.getText().toString().trim().isEmpty())
            return false;
        return  true;
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spSetsNum.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spSetsNum.getSelectedItem()))) {
                setsNum = Integer.parseInt(String.valueOf(spSetsNum.getSelectedItem()));
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
            } else {
                setsNum = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }
}
