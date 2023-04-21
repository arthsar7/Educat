package ru.student.detected.educator.data.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    private final List<Question> questions;
    private int points;
    private boolean isUnlocked;

    public int getPoints() {
        return points;
    }
    public Test(List<Question> questions){
        points = 0;
        this.questions = questions;
        isUnlocked = false;
    }
    public Test(){
        points = 0;
        questions = new ArrayList<>();
    }
    public void checkAnswer(@NonNull String userAnswer, int num){
        if (userAnswer.equals(questions.get(num).getAnswer())){
            points++;
        }
    }
    public void unlock(){
        isUnlocked = true;
    }
    private void setPoints(int points) {
        this.points = points;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }
}
