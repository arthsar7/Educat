package ru.student.detected.educator.data.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.student.detected.educator.data.models.Question;
import ru.student.detected.educator.data.database.QuestionDao;
import ru.student.detected.educator.data.database.QuestionDatabase;

public class QuestionRepository {
    QuestionDao questionDao;

    public QuestionRepository(Context context) {
        QuestionDatabase db = QuestionDatabase.getDatabase(context.getApplicationContext());
        questionDao = db.questionDao();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return questionDao.getAllQuestions();
    }

    public void insertQuestion(Question question) {
        executor.execute(() -> questionDao.insertAll(question));
    }

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    public void deleteAllQuestions(){
        executor.execute(() -> questionDao.deleteAll());
    }
}
