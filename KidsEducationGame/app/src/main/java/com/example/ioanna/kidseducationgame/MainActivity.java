package com.example.ioanna.kidseducationgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner selectedCharacter;
    private int characterID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedCharacter = (Spinner) findViewById(R.id.spinner_character);
        selectedCharacter.setOnItemSelectedListener(new ItemSelectedListener());
    }

    //Start color quiz
    public void startColorQuiz(View view){
        //Check if user selects a character
        if (isValid()){
            Intent colorQuiz = new Intent(this, ColorQuizActivity.class);
            colorQuiz.putExtra("CHARACTERID",characterID);
            startActivity(colorQuiz);
        } else
            Toast.makeText(getApplicationContext(),getString(R.string.ColorGameSelectCharacter),Toast.LENGTH_LONG).show();
    }

    public void startNumberingQuiz(View view){
        //Check if user selects a character
        if (isValid()){
            Intent numberingQuiz = new Intent(this, NumberActivity.class);
            numberingQuiz.putExtra("CHARACTERID",characterID);
            startActivity(numberingQuiz);
        } else
            Toast.makeText(getApplicationContext(),getString(R.string.NumberingGameSelectCharacter),Toast.LENGTH_LONG).show();
    }

    //Add character image based on selected character
    private void showSelectedCharacter(int drawableID){
        ImageView image = (ImageView) findViewById(R.id.selected_character_img);
        image.setImageResource(drawableID);
    }

    //Remove character image
    private void removeCharacter(){
        ImageView image = (ImageView) findViewById(R.id.selected_character_img);
        image.setImageDrawable(null);
    }

    private boolean isValid(){
        if (characterID > 0)
            return true;
        else
            return false;
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
        //get strings of first item
        String firstItem = String.valueOf(selectedCharacter.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            characterID = pos;
            if(pos > 0) {
                switch (pos){
                    case 1: showSelectedCharacter(R.drawable.mickey);
                            break;
                    case 2: showSelectedCharacter(R.drawable.minnie);
                            break;
                }
                Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString() + " character has selected!",Toast.LENGTH_LONG).show();
                Log.i("A character has been selected",pos + " - " + parent.getItemAtPosition(pos).toString() );
            }else{
                //Remove character image
                removeCharacter();
                Toast.makeText(parent.getContext(),getString(R.string.SelectCharacter),Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }
}
