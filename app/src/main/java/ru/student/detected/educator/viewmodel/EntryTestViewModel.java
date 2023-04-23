package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EntryTestViewModel extends AndroidViewModel {
    private final MutableLiveData<Integer> difficulty = new MutableLiveData<>();
    public EntryTestViewModel(@NonNull Application application) {
        super(application);
    }
    public void setDifficulty(int difficulty) {
        this.difficulty.setValue(difficulty);
    }

    public LiveData<Integer> getDifficulty() {
        return difficulty;
    }
}