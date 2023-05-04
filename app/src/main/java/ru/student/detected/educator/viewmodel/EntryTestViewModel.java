package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EntryTestViewModel extends AndroidViewModel {
    public static final int ENTRY_TEST_STAGES = 5;
    private final MutableLiveData<Integer> difficulty = new MutableLiveData<>();
    private final MutableLiveData<Integer> points = new MutableLiveData<>();
    public final MutableLiveData<Integer> steps = new MutableLiveData<>();
    public EntryTestViewModel(@NonNull Application application) {
        super(application);
        points.setValue(0);
        steps.setValue(0);
    }
    public void setDifficulty(int difficulty) {
        this.difficulty.setValue(difficulty);
    }

    public LiveData<Integer> getDifficulty() {
        return difficulty;
    }
    public LiveData<Integer> getPoints() {
        return points;
    }
    public LiveData<Integer> getSteps() {
        return steps;
    }
    public void addStep(){
        if(steps.getValue()!= null) {
            steps.setValue(steps.getValue() + 1);
        }
    }
    public void setPoints(int points) {
        this.points.setValue(points);
    }
    public void addPoints(int points) {
        if(this.points.getValue() != null) {
            this.points.setValue(this.points.getValue() + points);
        }
    }

    public void setSteps(int i) {
        this.steps.setValue(i);
    }
}