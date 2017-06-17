/**
 * Created by Ioanna on 09/04/2017.
 */

package com.example.ioanna.kidseducationgame;

import java.util.Random;

public class Question {

    static final int POSSIBLE_ANSWERS = 4;

    public enum Colors{
        Red,
        Green,
        Blue,
        Black,
        White,
        Pink,
        Yellow
    }

    public enum QuestionType{
        FreeText,
        Button,
        RadioButton
    }

    private String question;
    private QuestionType questionType;
    private Random random = new Random();
    //Color quiz
    private Colors answer;
    private Colors[] possibleAnswers = new Colors[POSSIBLE_ANSWERS];
    private Colors[] colors = Colors.values();

    public Question(String question, Colors answer, QuestionType type) {
        this.question = question;
        this.answer = answer;
        this.questionType = type;
        if (!questionType.equals(QuestionType.FreeText))
            setPossibleAnswers();
    }

    public void setPossibleAnswers(){
        possibleAnswers[0] = answer;
        Colors cls;
        //Set randomly possible answers
        for(int i=1; i< POSSIBLE_ANSWERS;++i){
            cls = colors[random.nextInt(colors.length)];
            while (cls == answer)
                cls = colors[random.nextInt(colors.length)];
            possibleAnswers[i] = cls;
        }
        //Shuffle possible answers
        shuffleArray(possibleAnswers);
    }

    public String getQuestion(){
        return this.question;
    }

    public Colors getAnswer(){
        return this.answer;
    }

    public Colors[] getPossibleAnswers(){
        return this.possibleAnswers;
    }

    public QuestionType getQuestionType(){
        return this.questionType;
    }

    //Shuffle an array
    private void shuffleArray(Colors[] ar) {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);
            // Simple swap
            Colors a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
