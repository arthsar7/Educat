package ru.student.detected.educator.data.repositories;

import android.app.Application;

import java.util.List;

import ru.student.detected.educator.data.data_sources.PairDataSource;
import ru.student.detected.educator.data.models.Pair;

public class PairRepository {
    private final PairDataSource pairDataSource;

    public PairRepository() {
        this.pairDataSource = new PairDataSource();
    }
    public List<Pair> getPairs(Application application) {
        return pairDataSource.pairs(application);
    }
}
