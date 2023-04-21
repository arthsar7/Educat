package ru.student.detected.educator.data.data_sources.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import ru.student.detected.educator.data.models.Question;

@Entity
public class QuestionEntity {
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "question")
    public String question;
    @ColumnInfo(name = "answer")
    public String answer;

    @ColumnInfo(name = "variants")

    public List<String> variants;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "difficulty")
    public int difficulty;
    public QuestionEntity(String question, String answer, List<String> variants, String description, int difficulty) {
        this.question = question;
        this.answer = answer;
        this.variants = variants;
        this.description = description;
        this.difficulty = difficulty;
    }


    public Question toDomainModel() {
        return new Question(question, answer, variants, description, difficulty);
    }


}
