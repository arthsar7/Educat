package ru.student.detected.educator.data.models;

import java.util.List;

public class VideoQuestion {
    private List<String> phrases;
    private int rightPhrase;
    private int videoId;

    public VideoQuestion(List<String> phrases, int rightPhrase, int videoId) {
        this.phrases = phrases;
        this.rightPhrase = rightPhrase;
        this.videoId = videoId;
    }

    public List<String> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<String> phrases) {
        this.phrases = phrases;
    }

    public int getRightPhrase() {
        return rightPhrase;
    }

    public void setRightPhrase(int rightPhrase) {
        this.rightPhrase = rightPhrase;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
}
