package ru.student.detected.educator.data.models;

import java.util.List;

public class Question {
    private String question;
    private String answer;
    private List<String> variants;
    private String description;
    public Question(String question, String answer, List<String> variants, String description) {
    this.question = question;
    this.answer = answer;
    this.variants = variants;
    this.description = description;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
