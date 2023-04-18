package ru.student.detected.educator.data.models;

public class Theory {
    private int img;
    private String name;
    private String description;
    private int itemImg;
    private boolean isChecked;

    public Theory(int img, String name, String description, int itemImg) {
        this.img = img;
        this.name = name;
        this.description = description;
        this.itemImg = itemImg;
        isChecked = false;
    }

    public int getItemImg() {
        return itemImg;
    }

    public void setItemImg(int itemImg) {
        this.itemImg = itemImg;
    }

    public String getTheoryDescription() {
        return description;
    }

    public void setTheoryDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
