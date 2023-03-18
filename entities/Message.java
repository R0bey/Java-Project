package com.example.lab4.entities;

public class Message {
    private User user_to;
    private User user_from;
    private String message;

    public Message(User to, User from, String message) {
        this.user_to = to;
        this.user_from = from;
        this.message = message;
    }

    public User getUser_to() {
        return user_to;
    }

    public void setUser_to(User user_to) {
        this.user_to = user_to;
    }

    public User getUser_from() {
        return user_from;
    }

    public void setUser_from(User user_from) {
        this.user_from = user_from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
