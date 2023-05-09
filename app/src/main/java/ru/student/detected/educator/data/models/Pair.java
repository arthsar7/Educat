package ru.student.detected.educator.data.models;

public class Pair implements Comparable<Pair> {
    private String engWord;
    private String rusWord;
    private int difficulty;

    private boolean isFront;

    public Pair(String engWord, String rusWord, int difficulty) {
        this.engWord = engWord;
        this.rusWord = rusWord;
        this.difficulty = difficulty;
        isFront = true;
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

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }

    @Override
    public int compareTo(Pair pair) {
        return this.difficulty - pair.getDifficulty();
    }
}
