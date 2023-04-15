package ru.student.detected.educator.data.models;

import java.util.List;

public class Article {
    private List<Integer> images;
    private String text;

    public Article(List<Integer> images, String text) {
        this.images = images;
        this.text = text;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
