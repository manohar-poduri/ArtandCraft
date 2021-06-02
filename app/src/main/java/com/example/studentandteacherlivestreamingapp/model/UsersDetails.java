package com.example.studentandteacherlivestreamingapp.model;

public class UsersDetails {

    String name,email,password,imageUri,uid, status;

    public UsersDetails() {
    }

    public UsersDetails(String name, String email, String password, String imageUri, String uid, String status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUri = imageUri;
        this.uid = uid;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
