package com.example.lab4.service;

import com.example.lab4.entities.Friendship;
import com.example.lab4.entities.User;
import com.example.lab4.entities.validators.ValidationException;
import com.example.lab4.repository.DBRepo.DBFriendshipRepository;
import com.example.lab4.repository.DBRepo.DBUserRepository;
import com.example.lab4.repository.MemoryRepo.FriendshipRepository;
import com.example.lab4.repository.MemoryRepo.UserRepository;
import com.example.lab4.repository.RepositoryInterface;
import com.example.lab4.utils.Observable;
import com.example.lab4.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class FriendshipService implements Observable {
    DBFriendshipRepository friendshipRepository;
    DBUserRepository userRepository;
    List<Observer> observers;

    public FriendshipService(DBFriendshipRepository friendshipRepository, DBUserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.observers = new ArrayList<>();
    }

    public void add(String firstName1, String firstName2, String lastName1, String lastName2, String status){
        /*
        User user1 = new User(firstName1, lastName1);
        User user2 = new User(firstName2, lastName2);
         */
        try{
            //System.out.println(userRepository.get_all().size());
            User user1 = userRepository.findUser(firstName1,lastName1);
            User user2 = userRepository.findUser(firstName2,lastName2);
            Friendship friendship = new Friendship(user1, user2, status);
            friendshipRepository.add(friendship);
            notifyObserevers();
            //this.friendshipRepository.writeToFile();
        }
        catch (ValidationException ve){
            throw ve;
        }
    }

    public void deletefriendship(String firstName1, String firstName2, String lastName1, String lastName2){
        try{
            User user1 = userRepository.findUser(firstName1, lastName1);
            User user2 = userRepository.findUser(firstName2, lastName2);
            Friendship friendship = friendshipRepository.findFriendship(user1, user2);
            friendshipRepository.delete(friendship);
            notifyObserevers();
            //this.friendshipRepository.writeToFile();
        }
        catch (ValidationException ve){
            throw ve;
        }
    }

    public void updateFriendship(String firstName1, String firstName2, String lastName1, String lastName2, String firstName3, String firstName4, String lastName3, String lastName4){
        try{
            User user1 = userRepository.findUser(firstName1,lastName1);
            User user2 = userRepository.findUser(firstName2,lastName2);
            User user3 = userRepository.findUser(firstName3,lastName3);
            User user4 = userRepository.findUser(firstName4,lastName4);
            Friendship friendship1 = friendshipRepository.findFriendship(user1,user2);
            if(friendshipRepository.isFriends(user3,user4)==true)
                throw new ValidationException("Friendship already exists");
            Friendship friendship2 = new Friendship(user3,user4);
            friendshipRepository.update(friendship1,friendship2);
            notifyObserevers();
            //this.friendshipRepository.writeToFile();
        }
        catch(ValidationException ve) {
            throw ve;
        }
    }

    public ArrayList<Friendship> get_all(){
        return this.friendshipRepository.get_all();
    }

    public ArrayList<Friendship> getFriends(User user){
        return this.friendshipRepository.getFriends(user);
    }

    public Friendship findFriendship(User u1, User u2){
        return friendshipRepository.findFriendship(u1,u2);
    }

    @Override
    public void addObserver(Observer observer){
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        this.observers.remove(observer);
    }

    @Override
    public void notifyObserevers(){
        this.observers.stream().forEach(observer -> observer.update());
    }
}
