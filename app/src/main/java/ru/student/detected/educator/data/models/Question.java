package ru.student.detected.educator.data.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
    private final String question;
    private final int answer;
    private final List<String> variants;

    public Question(String question, int answer) {
        this.question = question;
        this.answer = answer;
        variants = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(String... variant) {
        Collections.addAll(this.variants, variant);
    }

}
