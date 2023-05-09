package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.student.detected.educator.data.models.Pair;
import ru.student.detected.educator.data.repositories.PairRepository;

public class PairViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Pair>> pairList = new MutableLiveData<>();
    private PairRepository pairRepository;
    private static final List<Pair> ALL_PAIRS = new ArrayList<>();
    public PairViewModel(@NonNull Application application) {
        super(application);
        pairRepository = new PairRepository();
        ALL_PAIRS.addAll(pairRepository.getPairs(application));
        pairList.setValue(ALL_PAIRS);
    }
    public Map<String, String> getMap() {
        return ALL_PAIRS.stream().collect(Collectors.toMap(Pair::getEngWord, Pair::getRusWord, (oldValue, newValue) -> newValue));
    }
    public void updatePair(){;
        Collections.shuffle(ALL_PAIRS);
        pairList.setValue(ALL_PAIRS);
    }
    public void removePair(Pair pair)
    {
        if(pairList.getValue()!=null){
            List<Pair> list = pairList.getValue();
            list.remove(pair);
            pairList.setValue(list);
        }
    }
    public void removePair(int position) {
        if(pairList.getValue()!=null){
            List<Pair> list = pairList.getValue();
            list.remove(position);
            pairList.setValue(list);
        }

    }
    public LiveData<List<Pair>> getPairs() {
        return pairList;
    }
    public int getDifficultyByWords(String engWord, String rusWord) {
        for (Pair pair : ALL_PAIRS) {
            if (pair.getEngWord().equals(engWord) && pair.getRusWord().equals(rusWord)) {
                return pair.getDifficulty();
            }
        }
        return 0;
    }

}
