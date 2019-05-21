package com.archat.engine.Chat.ui;

public class User {

    public String username;
    public String uid;
    public String email;
    public String photoUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String uid, String email, String photoUrl) {
        this.username = username;
        this.uid = uid;
        this.email = email;
        this.photoUrl = photoUrl;
    }

}
