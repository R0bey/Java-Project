package com.example.lab4.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Integer>{

    private String firstName;
    private String lastName;

    private String password;
    //private ArrayList<User> friends;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        //friends = new ArrayList<User>();
    }

    public User(String firstName, String lastName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /*public List<User> getFriends() {
        return friends;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ((Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    /*public void addFriend(User o){
        this.friends.add(o);
    }*/

    /*public void removeFriend(User o){
        int index= -1;
        index=this.friends.indexOf(o);
        if(index!=-1)
            this.friends.remove(index);
    }*/

    public void delete(){
        //this.friends.clear();
        this.lastName="";
        this.firstName="";
        //this.setId(Long.parseLong("0"));
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
        //", friends=" +  friendsPrint() +;
    }

    /*public String friendsPrint(){
        String friends="[";
        for(User u : this.friends)
            friends = friends + "[" + u.getFirstName() + " " + u.getLastName() + "]";
        friends = friends+"]";
        return friends;
    }*/
}
