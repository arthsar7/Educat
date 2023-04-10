package ru.student.detected.educator.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Collections;
import java.util.List;
@Entity
public class Question {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "question")
    public String question;
    @ColumnInfo(name = "answer")
    public String answer;
    @ColumnInfo(name = "image_id")
    public int imageId;

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    @ColumnInfo(name = "variants")

    private List<String> variants;
    @ColumnInfo(name = "description")
    private String description;

    public Question(String question, String answer, List<String> variants, String description) {
        this.question = question;
        this.answer = answer;
        this.variants =  variants;
        this.description = description;
    }
    public void setVariants(String... variant) {
        Collections.addAll(this.variants, variant);
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
