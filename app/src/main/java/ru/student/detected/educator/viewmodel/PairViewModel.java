package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.student.detected.educator.data.models.Pair;
import ru.student.detected.educator.data.repositories.PairRepository;

public class PairViewModel extends ViewModel {
    private final List<Pair> pairList;
    public PairViewModel(@NonNull Application application) {
        PairRepository pairRepository = new PairRepository();
        pairList = pairRepository.getPairs(application);
    }
    public List<Pair> getPairs() {
        return pairList;
    }

}
