package com.example.quizapp;

import androidx.annotation.NonNull;

public class Question {

    private String questionText;
    private String[] options;
    private int correctAnswerIndex;
    private int selectedOption = -1;

    private int[] optionColors;
    private boolean[] optionInteractibility;
    private boolean saveInteractability;

    public Question(String QuestionText , String[] options , int correctAnswerIndex){

        this.questionText = QuestionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.optionColors = new int[4]; //default color
        this.optionInteractibility = new boolean[]{true , true , true , true};
        this.saveInteractability = true;
    }

    public String getQuestionText(){
        return questionText;
    }

    public String[] getOptions(){
        return options;
    }

    public int getCorrectAnswerIndex(){
        return correctAnswerIndex;
    }

    public void setOptions( String[] options){
        this.options = options;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex){
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public int getSelectedOption(){

        return this.selectedOption;
    }

    public void setSelectedOption(int selectedOption){
        this.selectedOption = selectedOption;
    }

    public int[] getOptionColors(){
        return optionColors;
    }
    public void setOptionColors(int[] optionColors){
        this.optionColors = optionColors;
    }
    public boolean[] getOptionInteractibility(){
        return optionInteractibility;
    }
    public void setOptionInteractibility(boolean[] optionInteractibility){
        this.optionInteractibility = optionInteractibility;
    }

    public boolean getSaveInteractability(){
        return saveInteractability;
    }
    public void setSaveInteractability(boolean saveInteractability){
        this.saveInteractability = saveInteractability;
    }
}
