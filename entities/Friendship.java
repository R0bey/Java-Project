package com.example.lab4.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Friendship extends Entity<Integer> {
    private User friend1;
    private User friend2;

    private String status;
    private LocalDateTime friendsFrom;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getFriend1() {
        return friend1;
    }

    public void setFriend1(User friend1) {
        this.friend1 = friend1;
    }

    public User getFriend2() {
        return friend2;
    }

    public void setFriend2(User friend2) {
        this.friend2 = friend2;
    }

    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public Friendship(User friend1, User friend2) {
        this.friend1 = friend1;
        this.friend2 = friend2;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.setFriendsFrom(now);
    }

    public Friendship(User friend1, User friend2, String status){
        this.friend1 = friend1;
        this.friend2 = friend2;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.setFriendsFrom(now);
        this.status=status;
    }

    public Friendship(User friend1, User friend2, String status, Date date){
        this.friend1 = friend1;
        this.friend2 = friend2;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        this.friendsFrom = localDateTime;
        this.status=status;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "friend1=" + friend1 +
                ", friend2=" + friend2 +
                ", friendsFrom=" + friendsFrom +
                '}';
    }

    public String toStringFile() {
        return friend1.getId() + "," + friend2.getId();
    }

    public void delete() {

    }


}
