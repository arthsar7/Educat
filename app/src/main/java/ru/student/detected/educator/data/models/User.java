package ru.student.detected.educator.data.models;

public class User {
    private String firstName;
    private String email;
    private String secondName;
    private String photoUrl;
    private String ID;
    public User(String firstName, String email, String secondName, String photoUrl, String ID) {
        this.firstName = firstName;
        this.email = email;
        this.secondName = secondName;
        this.photoUrl = photoUrl;
        this.ID = ID;
    }
    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
