package ru.student.detected.educator.data.models;


public class ImageQuestion  extends Question {
    private final int imageId;

    public ImageQuestion(String question, int answer, int imageId) {
        super(question, answer);
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }
}
