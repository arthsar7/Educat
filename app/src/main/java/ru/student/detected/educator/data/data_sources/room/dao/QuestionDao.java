package ru.student.detected.educator.data.data_sources.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.student.detected.educator.data.data_sources.room.entities.QuestionEntity;

@Dao
public interface QuestionDao {
    @Insert
    void insertAll(QuestionEntity... question);
    @Query("SELECT * FROM QuestionEntity")
    LiveData<List<QuestionEntity>> getAllQuestions();
    @Query("DELETE FROM QuestionEntity")
    void deleteAll();
}
