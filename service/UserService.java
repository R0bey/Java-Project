package com.example.lab4.service;

import com.example.lab4.entities.User;
import com.example.lab4.entities.validators.ValidationException;
import com.example.lab4.repository.DBRepo.DBFriendshipRepository;
import com.example.lab4.repository.DBRepo.DBUserRepository;
import com.example.lab4.repository.MemoryRepo.FriendshipRepository;
import com.example.lab4.repository.MemoryRepo.UserRepository;
import com.example.lab4.utils.Observable;
import com.example.lab4.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class UserService implements Observable {
    DBUserRepository repository;

    DBFriendshipRepository friendshipRepository;

    List<Observer> observers;

    public UserService(DBUserRepository repository, DBFriendshipRepository friendshipRepository) {
        this.repository = repository;
        this.friendshipRepository = friendshipRepository;
        this.observers = new ArrayList<>();
    }

    public void addUser(String firstName, String lastName, String password){
        User user = new User(firstName, lastName, password);
        this.repository.add(user);
        notifyObserevers();
        //this.repository.writeToFile();
    }

    public void removeUser(String firstName, String lastName){
        User user = new User(firstName, lastName);
        user=this.repository.findUser(firstName,lastName);
        this.friendshipRepository.userRemove(user);
        this.repository.delete(user);
        notifyObserevers();
        //this.repository.writeToFile();
        //this.friendshipRepository.writeToFile();
    }

    public void update(String firstName1, String firstName2, String lastName1, String lastName2){
        User user1 = new User(firstName1, lastName1);
        User user2 = new User(firstName2, lastName2);
        this.friendshipRepository.userUpdate(this.repository.findUser(user1.getFirstName(),user1.getLastName()),user2);
        this.repository.update(this.repository.findUser(user1.getFirstName(),user1.getLastName()),user2);
        notifyObserevers();
        //this.repository.writeToFile();
        //this.friendshipRepository.writeToFile();
    }

    public ArrayList<User> getAll(){
        return this.repository.get_all();
    }

    /*
    public void addFriend(String firstName1, String lastName1, String firstName2, String lastName2){
        try{
            repository.addFriend(findUser(firstName1,lastName1),findUser(firstName2,lastName2));
        }
        catch (ValidationException ve){
            System.out.println("No such users");
        }
    }
    */

    public User findUser(String firstName, String lastName){
        try{
            return repository.findUser(firstName, lastName);
        }
        catch (ValidationException ve){
            throw ve;
        }
    }

    /*
    public void removeFriend(String firstName1, String lastName1, String firstName2, String lastName2){
        try{
            repository.removeFriend(findUser(firstName1,lastName1),findUser(firstName2,lastName2));
        }
        catch (ValidationException ve){
            System.out.println("No such users");
        }
    }
    */


    /*
    public int[][] friendshipMatrix(){
        int size=repository.getAll().size();
        int[][] adjacent = new int[size][size];
        for (User u1 : repository.getAll())
            for (User u2 : repository.getAll()){
                if (repository.friendship(u1,u2))
                    adjacent[repository.getAll().indexOf(u1)][repository.getAll().indexOf(u2)]=1;
            }
        return adjacent;
        /*for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(adjacent[i][j]);
            System.out.println();
        }
    }

    public void connexComponents(){
        int size=repository.getAll().size();
        int[][]adjacent=new int[size][size];
        adjacent=friendshipMatrix();
        ArrayList<ArrayList<User>> components = new ArrayList<>();
        //ArrayList<User> connex = new ArrayList<>();
        int[] visited = new int[size];
        for (int i=0; i<size; i++) {
            if (visited[i]==0) {
                visited[i]=1;
                ArrayList<User> connex = new ArrayList<>();
                connex.add(repository.getAll().get(i));
                for (int j = 0; j < size; j++) {
                    if (visited[j]==0)
                }
            }
        }
    }

    public void dfs()
    */

    /*
    public ArrayList<ArrayList<User>> connexComponents(){
        int size=repository.getAll().size();
        int[] visited = new int[size];
        ArrayList<ArrayList<User>> connex = new ArrayList<>();
        for (User u : this.repository.getAll()){
            if (visited[this.repository.getAll().indexOf(u)]==0) {
                ArrayList<User> aux = new ArrayList<>();
                aux.add(u);
                visited[this.repository.getAll().indexOf(u)]=1;
                dfs(u, visited, aux);
                connex.add(aux);
            }
        }
        /*
        for (ArrayList<User> us : connex){
            System.out.print(connex.indexOf(us) + ": ");
            for (User u : us)
                System.out.print(u.getFirstName()+ " " + u.getLastName() + ", ");
            System.out.println();
        }*/
      /*  return connex;
    }
    */

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
