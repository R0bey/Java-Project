package com.example.lab4.service;

import com.example.lab4.entities.Message;
import com.example.lab4.entities.User;
import com.example.lab4.repository.DBRepo.DBFriendshipRepository;
import com.example.lab4.repository.DBRepo.DBMessageRepo;
import com.example.lab4.repository.DBRepo.DBUserRepository;
import com.example.lab4.utils.Observable;
import com.example.lab4.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class MessageService implements Observable {

    DBMessageRepo messageRepo;
    //DBFriendshipRepository friendshipRepository;
    //DBUserRepository userRepository;
    List<Observer> observers;

    public MessageService(DBMessageRepo messageRepo) {
        this.messageRepo = messageRepo;
        //this.friendshipRepository = friendshipRepository;
        //this.userRepository = userRepository;
        this.observers = new ArrayList<>();
    }


    public void add(User u1, User u2, String c){
        Message message = new Message(u1, u2, c);
        this.messageRepo.addMessage(message);
        notifyObserevers();
    }

    public ArrayList<Message> find(User u_from, User u_to){
        return this.messageRepo.findMessages(u_from, u_to);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserevers() {
        this.observers.stream().forEach(observer -> observer.update());
    }
}
