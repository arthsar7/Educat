package ru.student.detected.educator.data.models;

import java.util.List;

public class DictionaryDB {
    public DictionaryDB() {
    }
    private List<Pair> words;

    public List<Pair> getWords() {
        return words;
    }

    public void setWords(List<Pair> words) {
        this.words = words;
    }
}
