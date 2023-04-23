package ru.student.detected.educator.data.models;

public class Pair {
    private String engWord;
    private String rusWord;
    private int difficulty;

    public Pair(String engWord, String rusWord, int difficulty) {
        this.engWord = engWord;
        this.rusWord = rusWord;
        this.difficulty = difficulty;
    }

    public Pair() {

    }

    public String getEngWord() {
        return engWord;
    }

    public void setEngWord(String engWord) {
        this.engWord = engWord;
    }

    public String getRusWord() {
        return rusWord;
    }

    public void setRusWord(String rusWord) {
        this.rusWord = rusWord;
    }
    public boolean isRightPair(Pair pair){
        return this.engWord.equals(pair.getEngWord()) && this.rusWord.equals(pair.getRusWord());
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    @Override
    public String toString() {
        return "Pair{" +
                "engWord='" + engWord + '\'' +
                ", rusWord='" + rusWord + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}
