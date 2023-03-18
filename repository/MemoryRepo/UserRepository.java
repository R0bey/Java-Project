package com.example.lab4.repository.MemoryRepo;

import com.example.lab4.entities.User;
import com.example.lab4.entities.validators.UserValidator;
import com.example.lab4.entities.validators.ValidationException;
import com.example.lab4.entities.validators.Validator;
import com.example.lab4.repository.RepositoryInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UserRepository implements RepositoryInterface<User> {
    protected ArrayList<User> users;

    protected UserValidator userValidator;

    public UserRepository(){
        //this.fileName=fileName;
        users = new ArrayList<>();
        userValidator = new UserValidator();
        //this.loadData();
    }

    /*
    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine())!=null){
                List<String> attr= Arrays.asList(linie.split(";"));
                this.save(attr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save(List<String> attributes){
        User user = new User(attributes.get(1), attributes.get(2));
        user.setId(attributes.get(0));
        this.userValidator.validate(user);
        this.users.add(user);
    }

    public void addUser(String firstName, String lastName){
        User user= new User(firstName, lastName);
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        user.setId(uuidAsString);
        this.userValidator.validate(user);
        System.out.println(user.toString());
        this.users.add(user);

    }

    public void addFriend(User user, User friend){
        user.addFriend(friend);
        friend.addFriend(user);
    }

    public void removeUser(String firstName, String lastName){
        User user = new User(firstName, lastName);
        this.userValidator.validate(user);
        for (User user1 : users) {
            user1.removeFriend(user);
        }
        this.users.remove(user);
        user.delete();
    }

    public void removeFriend(User user1, User user2){
        user1.removeFriend(user2);
        user2.removeFriend(user1);
    }

    public ArrayList<User> getAll(){
        return this.users;
    }

    public User findUser(String firstName, String lastName) {
        for (User user1 : users)
            if (user1.getFirstName().equals(firstName) && user1.getLastName().equals(lastName)){
                return user1;
            }
        throw new ValidationException();
    }

    public boolean friendship(User user1, User user2){
        for (User u : user1.getFriends())
            if (u.equals(user2))
                return true;
        return false;
    }*/

    @Override
    public void add(User user) {
        //UUID uuid = UUID.randomUUID();
        //int uuidAsString = uuid.toString();
        //user.setId(uuidAsString);
        this.userValidator.validate_all(user,users);
        //System.out.println(user.toString());
        this.users.add(user);
    }

    @Override
    public void delete(User user) {
        this.userValidator.validate(user);
        this.users.remove(user);
        user.delete();
    }

    @Override
    public void update(User user1, User user2) {
        try{
            this.findUser(user1.getFirstName(), user1.getLastName());
            this.userValidator.validate_all(user2,users);
            for (var user : this.users)
                if (user.equals(user1)){
                    user.setFirstName(user2.getFirstName());
                    user.setLastName(user2.getLastName());
                }
            //user1.setFirstName(user2.getFirstName());
            //user1.setLastName(user2.getLastName());
        }
        catch (ValidationException ve){
            throw ve;
        }
    }

    @Override
    public ArrayList<User> get_all() {
        return this.users;
    }

    public User findUser(String firstName, String lastName) {
        for (User user1 : users)
            if (user1.getFirstName().equals(firstName) && user1.getLastName().equals(lastName)){
                return user1;
            }
        throw new ValidationException("No user found");
    }
}
