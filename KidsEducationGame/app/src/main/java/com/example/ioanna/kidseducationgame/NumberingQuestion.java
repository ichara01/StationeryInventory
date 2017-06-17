package com.example.ioanna.kidseducationgame;

import java.util.Random;

/**
 * Created by Ioanna on 11/04/2017.
 */

public class NumberingQuestion {
    static final int POSSIBLE_ANSWERS = 4;
    static final int MULTI_CORRECT_ANSWERS = 2;

    public enum Numbers{
        One,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine
    }

    public enum NumberingQuestionType{
        Button,
        Checkbox
    }

    private int number;
    private Random random = new Random();

    //Numbering quiz
    private Numbers answer;
    private String[] multipleAnswers = new String[MULTI_CORRECT_ANSWERS] ;
    private String[] possibleAnswersForCheckBox = new String[POSSIBLE_ANSWERS];
    private NumberingQuestionType type;
    private Numbers[] possibleAnswers = new Numbers[POSSIBLE_ANSWERS];
    private Numbers[] numbers = Numbers.values();

    public NumberingQuestion(int num, Numbers answer) {
        this.number = num;
        this.answer = answer;
        this.type = NumberingQuestionType.Button;
        setPossibleAnswers();
    }

    public NumberingQuestion(int num, String[] manswers) {
        this.number = num;
        this.multipleAnswers = manswers;
        this.type = NumberingQuestionType.Checkbox;
        setPossibleAnswersForCheckbox();
    }

    public void setPossibleAnswersForCheckbox(){
        possibleAnswersForCheckBox[0] = multipleAnswers[0];
        possibleAnswersForCheckBox[1] = multipleAnswers[1];
        String cls;
        //Set randomly possible answers
        for(int i=2; i< POSSIBLE_ANSWERS;++i){
            cls = numbers[random.nextInt(numbers.length)].toString();
            possibleAnswersForCheckBox[i] = cls +  " + " + numbers[number];
        }
        shuffleStringArray(possibleAnswersForCheckBox);
    }

    public void setPossibleAnswers(){
        possibleAnswers[0] = answer;
        NumberingQuestion.Numbers cls;
        //Set randomly possible answers
        for(int i=1; i< POSSIBLE_ANSWERS;++i){
            cls = numbers[random.nextInt(numbers.length)];
            while (cls == answer)
                cls = numbers[random.nextInt(numbers.length)];
            possibleAnswers[i] = cls;
        }
        //Shuffle possible answers
        shuffleArray(possibleAnswers);
    }

    public int getQuestionNumber(){
        return this.number;
    }

    public NumberingQuestion.Numbers getAnswer(){
        return this.answer;
    }

    public String[] getMultiAnswer(){
        return this.multipleAnswers;
    }

    public NumberingQuestionType getQuestionType(){
        return this.type;
    }

    public NumberingQuestion.Numbers[] getPossibleAnswers(){
        return this.possibleAnswers;
    }

    public String[] getPossibleAnswersForCheckBox(){
        return this.possibleAnswersForCheckBox;
    }

    //Shuffle an array
    private void shuffleArray(NumberingQuestion.Numbers[] ar) {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);
            // Simple swap
            NumberingQuestion.Numbers a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private void shuffleStringArray(String[] ar) {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
