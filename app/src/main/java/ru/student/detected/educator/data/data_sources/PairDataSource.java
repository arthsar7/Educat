package ru.student.detected.educator.data.data_sources;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.student.detected.educator.data.models.Pair;
import ru.student.detected.page1.R;

public class PairDataSource {
    public List<Pair> pairs(Application application) {
        List<Pair> pairs = new ArrayList<>();
        List<String> engWords = Arrays.asList(application.getApplicationContext().getResources().getStringArray(R.array.eng_words));
        List<String> rusWords = Arrays.asList(application.getApplicationContext().getResources().getStringArray(R.array.rus_words));
        int k = 1;
        for(int i = 0; i < rusWords.size(); i++){
            if(k <= 4)
                pairs.add(new Pair(engWords.get(i), rusWords.get(i), 1));
            else if(k <= 8)
                pairs.add(new Pair(engWords.get(i), rusWords.get(i), 2));
            else
                pairs.add(new Pair(engWords.get(i), rusWords.get(i), 3));
            if(k == 12)
                k = 0;
            k++;
        }
        Collections.shuffle(pairs);
        return pairs;
    }
}
