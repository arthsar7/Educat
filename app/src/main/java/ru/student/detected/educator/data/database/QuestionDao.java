package ru.student.detected.educator.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.student.detected.educator.data.models.Question;

@Dao
public interface QuestionDao {
    @Insert
    void insertAll(Question... question);
    @Query("SELECT * FROM question")
    LiveData<List<Question>> getAllQuestions();
    @Query("DELETE FROM question")
    public void deleteAll();
}
