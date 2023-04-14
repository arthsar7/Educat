package ru.student.detected.educator.data.data_sources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.student.detected.educator.data.models.Question;

public class QuestionsDataSource {
    public LiveData<List<Question>> questions() {
        MutableLiveData<List<Question>> result = new MutableLiveData<>();
        new Thread(() -> {
            ArrayList<Question> questionArrayList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                questionArrayList.add(new Question("Question " + i, "Answer " + i, Arrays.asList("a","b","c"), "Description " + i));
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            result.postValue(questionArrayList);
        }).start();

        return result;
    }
}
