package ru.student.detected.educator.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.student.detected.educator.data.data_sources.TheoryDataSource;
import ru.student.detected.educator.data.models.Theory;

public class TheoryRepository {
    private final TheoryDataSource theoryDataSource;
    public TheoryRepository() {
        theoryDataSource = new TheoryDataSource();
    }

    public LiveData<List<Theory>> getTheoryData(Application application) {
        return theoryDataSource.theories(application);
    }
}
