package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.student.detected.educator.data.models.Theory;
import ru.student.detected.educator.data.repositories.TheoryRepository;

public class TheoryViewModel extends AndroidViewModel {
    private TheoryRepository repository;
    private LiveData<List<Theory>> theories;
    public TheoryViewModel(@NonNull Application application) {
        super(application);
        repository = new TheoryRepository();
        theories = repository.getTheoryData(application);
    }

    public LiveData<List<Theory>> getTheories() {
        return theories;
    }
    public void addTheories(Theory theories) {

    }
}