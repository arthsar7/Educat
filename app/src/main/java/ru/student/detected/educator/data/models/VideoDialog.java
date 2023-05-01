package ru.student.detected.educator.data.models;

import java.util.List;

public class VideoDialog {
    private List<String> answers;
    private int rightI;
    private int videoQuestionId;
    private int videoAnswerId;

    public VideoDialog(List<String> answers, int rightI, int videoQuestionId, int videoAnswerId) {
        this.answers = answers;
        this.rightI = rightI;
        this.videoQuestionId = videoQuestionId;
        this.videoAnswerId = videoAnswerId;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getRightI() {
        return rightI;
    }

    public void setRightI(int rightI) {
        this.rightI = rightI;
    }

    public int getVideoQuestionId() {
        return videoQuestionId;
    }

    public void setVideoQuestionId(int videoQuestionId) {
        this.videoQuestionId = videoQuestionId;
    }

    public int getVideoAnswerId() {
        return videoAnswerId;
    }

    public void setVideoAnswerId(int videoAnswerId) {
        this.videoAnswerId = videoAnswerId;
    }
}
