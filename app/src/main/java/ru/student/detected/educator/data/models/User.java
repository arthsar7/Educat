package ru.student.detected.educator.data.models;

import android.net.Uri;

public class User {
    private String firstName;
    private String email;
    private String secondName;
    private Uri photoUrl;
    public User(String firstName, String email, String secondName, Uri photoUrl) {
        this.firstName = firstName;
        this.email = email;
        this.secondName = secondName;
        this.photoUrl = photoUrl;
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

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }
}
