package com.example.ioanna.kidseducationgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ioanna.kidseducationgame.R.id.question_text;

public class ColorQuizActivity extends AppCompatActivity {
    static final int QUIZ_QUESTIONS = 7;

    private int score = 0;
    private String selectedAnswer = "";
    private int characterID;
    private int currentQuestion = 0;
    private String correctAnswer;
    //Mickey's questions
    private Question[] mickeyQuestions = new Question[QUIZ_QUESTIONS];
    //Minnie's questions
    private Question[] minnieQuestions = new Question[QUIZ_QUESTIONS];
    private RadioGroup radioBtnGroup;
    private Button submitRadioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_color_quiz);
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                loadQuiz(extras);
            } else {
                //newString= extras.getString("STRING_I_NEED");
            }
            radioBtnGroup=(RadioGroup)findViewById(R.id.RadioBtnGroup);
        }
    }

    //Load color quiz based on selected character
    private void loadQuiz(Bundle extras){
        characterID= extras.getInt("CHARACTERID");
        TextView header = (TextView) findViewById(R.id.header_text);
        ImageView character = (ImageView) findViewById(R.id.selected_character_img);
        TextView question = (TextView) findViewById(R.id.question_text);
        View horizLine1 = (View) findViewById(R.id.horizontal_line1);
        View horizLine2 = (View) findViewById(R.id.horizontal_line2);
        View btnHorizLine3 = (View) findViewById(R.id.btn_horizontal_line3);
        View btnHorizLine4 = (View) findViewById(R.id.btn_horizontal_line4);
        View radioBtnHorizLine3 = (View) findViewById(R.id.radio_horizontal_line3);
        View radioBtnHorizLine4 = (View) findViewById(R.id.radio_horizontal_line4);
        switch (characterID){
            case 1: header.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    header.setText(getString(R.string.MickeyColors));
                    question.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    horizLine1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    horizLine2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    btnHorizLine3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    btnHorizLine4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    radioBtnHorizLine3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    radioBtnHorizLine4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MickeyTextColor));
                    //Load character
                    character.setImageResource(R.drawable.mickey);
                    //Set questions
                    setMickeyQuestions();
                    //Load question
                    loadMickeyQuestion();
                    break;
            case 2: header.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    header.setText(getString(R.string.MinnieColors));
                    question.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    horizLine1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    horizLine2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    btnHorizLine3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    btnHorizLine4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    radioBtnHorizLine3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    radioBtnHorizLine4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.MinnieTextColor));
                    //Load character
                    character.setImageResource(R.drawable.minnie);
                    //Set questions
                    setMinnieQuestions();
                    //Load question
                    loadMinnieQuestion();
                    break;
        }
    }

    //Set Mickey's questions and answers
    private void setMickeyQuestions(){
        //Question1
        mickeyQuestions[0] = new Question("What is the color of Mickey Mouse's shoes?",Question.Colors.Yellow, Question.QuestionType.Button);
        //Question2
        mickeyQuestions[1] = new Question("What is the color of Mickey Mouse's trouser?",Question.Colors.Red, Question.QuestionType.FreeText);
        //Question3
        mickeyQuestions[2] = new Question("What is the color of Mickey Mouse's ears?",Question.Colors.Black, Question.QuestionType.Button);
        //Question4
        mickeyQuestions[3] = new Question("What is the color of Mickey Mouse's trouser?",Question.Colors.Red, Question.QuestionType.RadioButton);
        //Question5
        mickeyQuestions[4] = new Question("What is the color of Mickey Mouse's nose?",Question.Colors.Black, Question.QuestionType.Button);
        //Question6
        mickeyQuestions[5] = new Question("What is the color of Mickey Mouse's gloves?",Question.Colors.White, Question.QuestionType.RadioButton);
        //Question7
        mickeyQuestions[6] =  new Question("What is the color of Mickey Mouse's shoes?",Question.Colors.Yellow, Question.QuestionType.FreeText);
    }

    private void loadMickeyQuestion(){
        TextView question = (TextView) findViewById(question_text);
        question.setText(mickeyQuestions[currentQuestion].getQuestion());
        correctAnswer = mickeyQuestions[currentQuestion].getAnswer().name();
        setPossibleAnswers(mickeyQuestions);
        if(mickeyQuestions[currentQuestion].getQuestionType() != Question.QuestionType.FreeText)
            disableSubmitBtn();
    }

    //Set Minnie's questions and answers
    private void setMinnieQuestions(){
        //Question1
        minnieQuestions[0] = new Question("What is the color of Minnie Mouse's dress?",Question.Colors.Pink,Question.QuestionType.Button);
        //Question2
        minnieQuestions[1] = new Question("What is the color of Minnie Mouse's ears?",Question.Colors.Black, Question.QuestionType.FreeText);
        //Question3
        minnieQuestions[2] = new Question("What is the color of Minnie Mouse's nose?",Question.Colors.Black, Question.QuestionType.Button);;
        //Question4
        minnieQuestions[3] = new Question("What is the color of Minnie Mouse's ears?",Question.Colors.Black, Question.QuestionType.RadioButton);
        //Question5
        minnieQuestions[4] = new Question("What is the color of Minnie Mouse's shoes?",Question.Colors.Pink, Question.QuestionType.Button);
        //Question6
        minnieQuestions[5] = new Question("What is the color of Minnie Mouse's gloves?",Question.Colors.White,Question.QuestionType.RadioButton);
        //Question7
        minnieQuestions[6] = new Question("What is the color of Minnie Mouse's dress?",Question.Colors.Pink, Question.QuestionType.FreeText);
    }

    private void loadMinnieQuestion(){
        TextView question = (TextView) findViewById(question_text);
        question.setText(minnieQuestions[currentQuestion].getQuestion());
        correctAnswer = minnieQuestions[currentQuestion].getAnswer().name();
        setPossibleAnswers(minnieQuestions);
        if(minnieQuestions[currentQuestion].getQuestionType() != Question.QuestionType.FreeText)
            disableSubmitBtn();
    }

    private void setPossibleAnswers(Question[] quest){
        LinearLayout layout;
        if(quest[currentQuestion].getQuestionType().equals(Question.QuestionType.Button)) {
            setPossibleAnswersToButtons(quest);
            layout = (LinearLayout) findViewById(R.id.buttonArea);
            layout.setVisibility(View.VISIBLE);
        }
        else if (quest[currentQuestion].getQuestionType().equals(Question.QuestionType.RadioButton)) {
            setPossibleAnswersToRadioButtons(quest);
            layout = (LinearLayout) findViewById(R.id.RadioButtonArea);
            layout.setVisibility(View.VISIBLE);
        }
        else if(quest[currentQuestion].getQuestionType().equals(Question.QuestionType.FreeText)){
            layout = (LinearLayout) findViewById(R.id.FreeTextArea);
            layout.setVisibility(View.VISIBLE);
        }
    }

    private void hideAnswerAreas(){
        LinearLayout layout;
        layout = (LinearLayout) findViewById(R.id.buttonArea);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.RadioButtonArea);
        layout.setVisibility(View.GONE);
        layout = (LinearLayout) findViewById(R.id.FreeTextArea);
        layout.setVisibility(View.GONE);
    }

    private void setPossibleAnswersToButtons(Question[] quest){
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setText(quest[currentQuestion].getPossibleAnswers()[0].name());
        setButtonTextColor(quest[currentQuestion].getPossibleAnswers()[0],button1);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setText(quest[currentQuestion].getPossibleAnswers()[1].name());
        setButtonTextColor(quest[currentQuestion].getPossibleAnswers()[1],button2);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setText(quest[currentQuestion].getPossibleAnswers()[2].name());
        setButtonTextColor(quest[currentQuestion].getPossibleAnswers()[2],button3);

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setText(quest[currentQuestion].getPossibleAnswers()[3].name());
        setButtonTextColor(quest[currentQuestion].getPossibleAnswers()[3],button4);
    }

    private void setPossibleAnswersToRadioButtons(Question[] quest){
        RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton1.setText(quest[currentQuestion].getPossibleAnswers()[0].name());
        setRadioButtonTextColor(quest[currentQuestion].getPossibleAnswers()[0],radioButton1);

        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton2.setText(quest[currentQuestion].getPossibleAnswers()[1].name());
        setRadioButtonTextColor(quest[currentQuestion].getPossibleAnswers()[1],radioButton2);

        RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton3.setText(quest[currentQuestion].getPossibleAnswers()[2].name());
        setRadioButtonTextColor(quest[currentQuestion].getPossibleAnswers()[2],radioButton3);

        RadioButton radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton4.setText(quest[currentQuestion].getPossibleAnswers()[3].name());
        setRadioButtonTextColor(quest[currentQuestion].getPossibleAnswers()[3],radioButton4);
    }

    //Click on first answer
    public void button1OnClick(View view){
        Button button = (Button) findViewById(R.id.button1);
        selectedAnswer= button.getText().toString();
        pressButton(button);
        enableSubmitBtn();
    }

    //Click on second answer
    public void button2OnClick(View view){
        Button button = (Button) findViewById(R.id.button2);
        selectedAnswer= button.getText().toString();
        pressButton(button);
        enableSubmitBtn();
    }

    //Click on third answer
    public void button3OnClick(View view){
        Button button = (Button) findViewById(R.id.button3);
        selectedAnswer= button.getText().toString();
        pressButton(button);
        enableSubmitBtn();
    }

    //Click on fourth answer
    public void button4OnClick(View view){
        Button button = (Button) findViewById(R.id.button4);
        selectedAnswer= button.getText().toString();
        pressButton(button);
        enableSubmitBtn();
    }

    //Enable submit button
    private void enableSubmitBtn(){
        Button btn = (Button) findViewById(R.id.btnSubmitColorAnswer);
        if(btn.isEnabled()) return;
        btn.setEnabled(true);
    }

    private void disableSubmitBtn(){
        Button btn = (Button) findViewById(R.id.btnSubmitColorAnswer);
        btn.setEnabled(false);
    }

    //Enable submit button when a radio button is checked
    public void enableSubmitBtn(View view){
        Button btn = (Button) findViewById(R.id.btnSubmitColorAnswer);
        if(btn.isEnabled()) return;
        btn.setEnabled(true);
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

    private void resetButtonArea(){
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGray));
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGray));
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGray));
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGray));
    }

    private void checkAnswer(String possibleAnswer){
        if (correctAnswer.toLowerCase().equals(possibleAnswer.toLowerCase())) {
            score++;
        }
    }

    private void loadNextQuestion(){
        hideAnswerAreas();
        resetRadioButton();
        resetFreerText();
        resetButtonArea();
        //Set next question
        currentQuestion++;
        if (currentQuestion < QUIZ_QUESTIONS)
        {
            if(characterID == 1)
                loadMickeyQuestion();
            else
                loadMinnieQuestion();
        }
        else {
            Button btn = (Button) findViewById(R.id.btnSubmitColorAnswer);
            btn.setVisibility(View.GONE);
            TextView quest = (TextView) findViewById(R.id.question_text);
            quest.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),getString(R.string.Congratulations) + " " + score + " of " + QUIZ_QUESTIONS ,Toast.LENGTH_LONG).show();
            openDialog();
        }
    }

    private void setButtonTextColor(Question.Colors posAnswer, Button btn){
        switch (posAnswer){
            case Black:
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.black, 0, 0, 0);
                    break;
            case White:
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.white, 0, 0, 0);
                    break;
            case Red:
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red, 0, 0, 0);
                    break;
            case Green:
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green, 0, 0, 0);
                    break;
            case Blue:
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.blue, 0, 0, 0);
                    break;
            case Pink:
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pink, 0, 0, 0);
                    break;
            case Yellow:
                    btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.yelllow, 0, 0, 0);
                    break;

        }
    }

    private void setRadioButtonTextColor(Question.Colors posAnswer, RadioButton btn){
        switch (posAnswer){
            case Black:
                btn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
                break;
            case White:
                btn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                break;
            case Red:
                btn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                break;
            case Green:
                btn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                break;
            case Blue:
                btn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.blue));
                break;
            case Pink:
                btn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.pink));
                break;
            case Yellow:
                btn.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));
                break;
        }
    }

    private void openDialog() {;
        AlertDialog winDialog = new AlertDialog.Builder(this,R.style.CustomAlertDialogTheme).create();
        winDialog.setTitle(getString(R.string.ColorGame));
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

    private void resetRadioButton(){
        int selectedId = radioBtnGroup.getCheckedRadioButtonId();
        if(selectedId == -1) return;
        RadioButton seletcedRadioButton = (RadioButton)findViewById(selectedId);
        seletcedRadioButton.setChecked(false);
    }

    private void resetFreerText(){
        EditText answer = (EditText) findViewById(R.id.freeTextAnswer);
        answer.getText().clear();
    }

    public void submitAnswer(View view){
        switch (characterID) {
            case 1: if(mickeyQuestions[currentQuestion].getQuestionType() == Question.QuestionType.FreeText) {
                        EditText answer = (EditText) findViewById(R.id.freeTextAnswer);
                        selectedAnswer = String.valueOf(answer);
                    }
                    else if(mickeyQuestions[currentQuestion].getQuestionType() == Question.QuestionType.RadioButton){
                        int selectedId = radioBtnGroup.getCheckedRadioButtonId();
                        RadioButton seletcedRadioButton = (RadioButton)findViewById(selectedId);
                        selectedAnswer = (String) seletcedRadioButton.getText();
                    }
                break;
            case 2:if(minnieQuestions[currentQuestion].getQuestionType() == Question.QuestionType.FreeText) {
                        EditText answer = (EditText) findViewById(R.id.freeTextAnswer);
                        selectedAnswer = String.valueOf(answer);
                    }
                    else if(minnieQuestions[currentQuestion].getQuestionType() == Question.QuestionType.RadioButton){
                        int selectedId = radioBtnGroup.getCheckedRadioButtonId();
                        RadioButton seletcedRadioButton = (RadioButton)findViewById(selectedId);
                        selectedAnswer = (String) seletcedRadioButton.getText();
                    }
                break;
        }
        checkAnswer(selectedAnswer);
        //Clean prievious selected answer
        selectedAnswer = "";
        loadNextQuestion();
    }
}
