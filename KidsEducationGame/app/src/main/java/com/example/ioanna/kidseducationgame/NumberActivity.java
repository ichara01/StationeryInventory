package com.example.ioanna.kidseducationgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ioanna.kidseducationgame.NumberingQuestion.MULTI_CORRECT_ANSWERS;
import static com.example.ioanna.kidseducationgame.NumberingQuestion.POSSIBLE_ANSWERS;
import static com.example.ioanna.kidseducationgame.R.id.number_question_img;

public class NumberActivity extends AppCompatActivity {
    static final int QUIZ_QUESTIONS = 12;

    private int score = 0;
    private String selectedAnswer = "";
    private int characterID;
    private int currentQuestion = 0;
    private int correctAnswer;
    //Questions
    private NumberingQuestion[] questions = new NumberingQuestion[QUIZ_QUESTIONS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_number);
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                loadQuiz(extras);
            } else {
                //newString= extras.getString("STRING_I_NEED");
            }
        }
    }

    //Load color quiz based on selected character
    private void loadQuiz(Bundle extras){
        characterID= extras.getInt("CHARACTERID");
        TextView header = (TextView) findViewById(R.id.number_header_text);
        ImageView character = (ImageView) findViewById(R.id.number_selected_character_img);
        View horizLine1 = (View) findViewById(R.id.number_horizontal_line1);
        View horizLine2 = (View) findViewById(R.id.number_horizontal_line2);
        View btnhorizLine3 = (View) findViewById(R.id.numBut_horizontal_line3);
        View btnhorizLine4 = (View) findViewById(R.id.numBut_horizontal_line4);
        View chbhorizLine3 = (View) findViewById(R.id.numchb_horizontal_line3);
        View chbhorizLine4 = (View) findViewById(R.id.numchb_horizontal_line4);
        //Set questions
        setQuestions();
        //Load question
        loadQuestionImage();
        switch (characterID){
            case 1: header.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    header.setText(getString(R.string.MickeyLearnNum));
                    horizLine1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    horizLine2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    btnhorizLine3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    btnhorizLine4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    chbhorizLine3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    chbhorizLine4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    //Load character
                    character.setImageResource(R.drawable.mickey);
                    break;
            case 2: header.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    header.setText(getString(R.string.MinnieLearnNum));
                    horizLine1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    horizLine2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    btnhorizLine3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    btnhorizLine4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    chbhorizLine3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    chbhorizLine4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    //Load character
                    character.setImageResource(R.drawable.minnie);
                    break;
        }
    }

    //Set questions and answers
    private void setQuestions(){
        //Question1
        questions[0] = new NumberingQuestion(1,NumberingQuestion.Numbers.One);
        //Question2
        questions[1] = new NumberingQuestion(2,NumberingQuestion.Numbers.Two);
        //Question3
        questions[2] = new NumberingQuestion(3,NumberingQuestion.Numbers.Three);
        //Question4
        questions[3] = new NumberingQuestion(4,NumberingQuestion.Numbers.Four);
        //Question5
        questions[4] = new NumberingQuestion(4, new String[]{"Three + One","Two + Two"});
        //Question6
        questions[5] = new NumberingQuestion(6, new String[]{"Three + Three","Four + Two"});
        //Question7
        questions[6] = new NumberingQuestion(7,NumberingQuestion.Numbers.Seven);
        //Question8
        questions[7] = new NumberingQuestion(8,NumberingQuestion.Numbers.Eight);
        //Question9
        questions[8] = new NumberingQuestion(9,NumberingQuestion.Numbers.Nine);
        //Question10
        questions[9] = new NumberingQuestion(5,NumberingQuestion.Numbers.Five);
        //Question11
        questions[10] = new NumberingQuestion(6,NumberingQuestion.Numbers.Six);
        //Question12
        questions[11] = new NumberingQuestion(7,new String[]{"Four + Three","Five + Two"});
    }

    private void loadQuestionImage(){
        ImageView questionImg = (ImageView) findViewById(number_question_img);
        switch (questions[currentQuestion].getQuestionNumber()){
            case 1: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_one);
                    else
                        questionImg.setImageResource(R.drawable.minnie_one);
                    break;
            case 2: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_two);
                    else
                        questionImg.setImageResource(R.drawable.minnie_two);
                    break;
            case 3: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_three);
                    else
                        questionImg.setImageResource(R.drawable.minnie_three);
                    break;
            case 4: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_four);
                    else
                        questionImg.setImageResource(R.drawable.minnie_four);
                    break;
            case 5: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_five);
                    else
                        questionImg.setImageResource(R.drawable.minnie_five);
                    break;
            case 6: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_six);
                    else
                        questionImg.setImageResource(R.drawable.minnie_six);
                    break;
            case 7: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_seven);
                    else
                        questionImg.setImageResource(R.drawable.minnie_seven);
                    break;
            case 8: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_eight);
                    else
                        questionImg.setImageResource(R.drawable.minnie_eight);
                    break;
            case 9: if (characterID == 1)
                        questionImg.setImageResource(R.drawable.mickey_nine);
                    else
                        questionImg.setImageResource(R.drawable.minnie_nine);
                    break;
        }
        LinearLayout layout;
        if(questions[currentQuestion].getQuestionType()== NumberingQuestion.NumberingQuestionType.Button) {
            layout = (LinearLayout) findViewById(R.id.NumberingButtonArea);
            layout.setVisibility(View.VISIBLE);
            correctAnswer = getNumber(questions[currentQuestion].getAnswer().name());
            setPossibleAnswers(questions);
        }
        else{
            layout = (LinearLayout) findViewById(R.id.NumberingCheckBoxArea);
            layout.setVisibility(View.VISIBLE);
            setPossibleAnswersForCheckBox(questions);
        }
    }

    private void setPossibleAnswersForCheckBox(NumberingQuestion[] quest){
        CheckBox chb1 = (CheckBox) findViewById(R.id.chOne);
        chb1.setText(quest[currentQuestion].getPossibleAnswersForCheckBox()[0]);

        CheckBox chb2 = (CheckBox) findViewById(R.id.chTwo);
        chb2.setText(quest[currentQuestion].getPossibleAnswersForCheckBox()[1]);

        CheckBox chb3 = (CheckBox) findViewById(R.id.chThree);
        chb3.setText(quest[currentQuestion].getPossibleAnswersForCheckBox()[2]);

        CheckBox chb4 = (CheckBox) findViewById(R.id.chFour);
        chb4.setText(quest[currentQuestion].getPossibleAnswersForCheckBox()[3]);
    }

    private void setPossibleAnswers(NumberingQuestion[] quest){
        Button button1 = (Button) findViewById(R.id.number_button1);
        button1.setText(quest[currentQuestion].getPossibleAnswers()[0].name());

        Button button2 = (Button) findViewById(R.id.number_button2);
        button2.setText(quest[currentQuestion].getPossibleAnswers()[1].name());

        Button button3 = (Button) findViewById(R.id.number_button3);
        button3.setText(quest[currentQuestion].getPossibleAnswers()[2].name());

        Button button4 = (Button) findViewById(R.id.number_button4);
        button4.setText(quest[currentQuestion].getPossibleAnswers()[3].name());
    }

    //Click on first answer
    public void button1OnClick(View view){
        Button button = (Button) findViewById(R.id.number_button1);
        selectedAnswer= button.getText().toString();
        pressButton(button);
        enableSubmitBtn();
    }

    //Click on second answer
    public void button2OnClick(View view){
        Button button = (Button) findViewById(R.id.number_button2);
        selectedAnswer= button.getText().toString();
        pressButton(button);
        enableSubmitBtn();
    }

    //Click on third answer
    public void button3OnClick(View view){
        Button button = (Button) findViewById(R.id.number_button3);
        selectedAnswer= button.getText().toString();
        pressButton(button);
        enableSubmitBtn();
    }

    //Click on fourth answer
    public void button4OnClick(View view){
        Button button = (Button) findViewById(R.id.number_button4);
        selectedAnswer= button.getText().toString();
        pressButton(button);
        enableSubmitBtn();
    }

    private int getNumber(String number){
        if (number.equals(NumberingQuestion.Numbers.One.name()))
                return 1;
        else if (number.equals(NumberingQuestion.Numbers.Two.name()))
            return 2;
        else if (number.equals(NumberingQuestion.Numbers.Three.name()))
            return 3;
        else if (number.equals(NumberingQuestion.Numbers.Four.name()))
            return 4;
        else if (number.equals(NumberingQuestion.Numbers.Five.name()))
            return 5;
        else if (number.equals(NumberingQuestion.Numbers.Six.name()))
            return 6;
        else if (number.equals(NumberingQuestion.Numbers.Seven.name()))
            return 7;
        else if (number.equals(NumberingQuestion.Numbers.Eight.name()))
            return 8;
        else if (number.equals(NumberingQuestion.Numbers.Nine.name()))
            return 9;
        return 0;
    }

    private void openDialog() {;
        AlertDialog winDialog = new AlertDialog.Builder(this,R.style.CustomAlertDialogTheme).create();
        winDialog.setTitle(getString(R.string.NumberingGame));
        winDialog.setMessage(getString(R.string.Congratulations) + " " + score + " of " + QUIZ_QUESTIONS);
        winDialog.setCancelable(false);
        winDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.Exit),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Close app
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
        winDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.StartQuiz),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Select new quiz
                        Intent mainLayout = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainLayout);
                    }
                });
        winDialog.show();
    }

    private void hideAnswerAreas(){
        LinearLayout layout;
        layout = (LinearLayout) findViewById(R.id.NumberingCheckBoxArea);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.NumberingButtonArea);
        layout.setVisibility(View.GONE);
    }

    private void resetCheckboxArea(){
        CheckBox chk1 = (CheckBox) findViewById(R.id.chOne);
        CheckBox chk2 = (CheckBox) findViewById(R.id.chTwo);
        CheckBox chk3= (CheckBox) findViewById(R.id.chThree);
        CheckBox chk4 = (CheckBox) findViewById(R.id.chFour);
        chk1.setChecked(false);
        chk2.setChecked(false);
        chk3.setChecked(false);
        chk4.setChecked(false);
    }

    private void checkButtonAnswer(String possibleAnswer){
        int userAnswer = getNumber(possibleAnswer);
        if (correctAnswer == userAnswer){
            score++;
        }
    }

    public void getCheckboxAnswer(){
        int pos = 0;
        int numAns = 0;
        String[] selectedAns = new String[POSSIBLE_ANSWERS];
        CheckBox chk1 = (CheckBox) findViewById(R.id.chOne);
        if (chk1.isChecked()) {
            numAns++;
            selectedAns[pos] = chk1.getText().toString();
            ++pos;
        }
        CheckBox chk2 = (CheckBox) findViewById(R.id.chTwo);
        if (chk2.isChecked()){
            numAns++;
            selectedAns[pos] = chk2.getText().toString();
            ++pos;
        }
        CheckBox chk3= (CheckBox) findViewById(R.id.chThree);
        if (chk3.isChecked()){
            numAns++;
            selectedAns[pos] = chk3.getText().toString();
            ++pos;
        }
        CheckBox chk4 = (CheckBox) findViewById(R.id.chFour);
        if (chk4.isChecked()){
            numAns++;
            selectedAns[pos] = chk4.getText().toString();
            ++pos;
        }
        checkSelectedAnswer(numAns,selectedAns);
    }

    //Enable submit button when at least one checkbox is checked
    public void enableCheckboxSubmitBtn(View view){
        Button btn = (Button) findViewById(R.id.btnSubmitAnswer);
        CheckBox chk1 = (CheckBox) findViewById(R.id.chOne);
        CheckBox chk2 = (CheckBox) findViewById(R.id.chTwo);
        CheckBox chk3= (CheckBox) findViewById(R.id.chThree);
        CheckBox chk4 = (CheckBox) findViewById(R.id.chFour);
        if(chk1.isChecked() || chk2.isChecked() || chk3.isChecked() || chk4.isChecked())
            btn.setEnabled(true);
        else
            btn.setEnabled(false);
    }

    //Enable submit button when a button is pressed
    private void enableSubmitBtn(){
        Button btn = (Button) findViewById(R.id.btnSubmitAnswer);
        if(btn.isEnabled()) return;
        btn.setEnabled(true);
    }

    private void disableSubmitBtn(){
        Button btn = (Button) findViewById(R.id.btnSubmitAnswer);
        btn.setEnabled(false);
    }

    private void resetButtonArea(){
        Button button1 = (Button) findViewById(R.id.number_button1);
        button1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGray));
        Button button2 = (Button) findViewById(R.id.number_button2);
        button2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGray));
        Button button3 = (Button) findViewById(R.id.number_button3);
        button3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGray));
        Button button4 = (Button) findViewById(R.id.number_button4);
        button4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGray));
    }

    private void pressButton(Button btn){
        resetButtonArea();
        switch (characterID){
            case 1:
                    btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    break;
            case 2:
                    btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    break;
        }
    }

    private void checkSelectedAnswer(int numSelectedAns, String selectedAns[]){
        String answers[] = questions[currentQuestion].getMultiAnswer();
        if (numSelectedAns == MULTI_CORRECT_ANSWERS ){
            if((selectedAns[0].equals(answers[0]) && selectedAns[1].equals(answers[1])) || (selectedAns[0].equals(answers[1]) && selectedAns[1].equals(answers[0]))){
                score++;
            }
        }
    }

    public void submitAnswer(View view){
        if (questions[currentQuestion].getQuestionType() == NumberingQuestion.NumberingQuestionType.Button)
            checkButtonAnswer(selectedAnswer);
        else if (questions[currentQuestion].getQuestionType() == NumberingQuestion.NumberingQuestionType.Checkbox)
            getCheckboxAnswer();
        //Clean prievious selected answer
        selectedAnswer = "";
        loadNextQuestion();
    }

    private void loadNextQuestion(){
        hideAnswerAreas();
        resetButtonArea();
        resetCheckboxArea();
        //Set next question
        currentQuestion++;
        if (currentQuestion < QUIZ_QUESTIONS){
            disableSubmitBtn();
            loadQuestionImage();
        }
        else {
            Button btn = (Button) findViewById(R.id.btnSubmitAnswer);
            btn.setVisibility(View.GONE);
            ImageView img = (ImageView) findViewById(R.id.number_question_img);
            img.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),getString(R.string.Congratulations) + " " + score + " of " + QUIZ_QUESTIONS ,Toast.LENGTH_LONG).show();
            openDialog();
        }
    }
}
