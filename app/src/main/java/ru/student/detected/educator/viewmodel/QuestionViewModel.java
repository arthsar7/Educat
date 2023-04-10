package ru.student.detected.educator.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.student.detected.educator.data.models.Question;
import ru.student.detected.educator.data.repositories.QuestionRepository;

public class QuestionViewModel extends AndroidViewModel {
    QuestionRepository questionRepository;
    LiveData<List<Question>> questionList;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
        questionList = questionRepository.getAllQuestions();
    }
    public LiveData<List<Question>> getAllQuestions(){
        return questionList;
    }
    public void insertQuestion(Question question){
        questionRepository.insertQuestion(question);
    }
    public void deleteAllQuestions(){
        questionRepository.deleteAllQuestions();
    }
}
