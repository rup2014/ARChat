package com.archat.engine.Chat.ui;

public class User {

    public String username;
    public String uid;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String uid, String email) {
        this.username = username;
        this.uid = uid;
        this.email = email;
    }

}
