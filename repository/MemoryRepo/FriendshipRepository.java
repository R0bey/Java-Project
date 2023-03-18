package com.example.lab4.repository.MemoryRepo;

import com.example.lab4.entities.Friendship;
import com.example.lab4.entities.User;
import com.example.lab4.entities.validators.FriendshipValidator;
import com.example.lab4.entities.validators.ValidationException;
import com.example.lab4.entities.validators.Validator;
import com.example.lab4.repository.RepositoryInterface;

import java.util.ArrayList;
import java.util.UUID;

public class FriendshipRepository implements RepositoryInterface<Friendship> {

    protected ArrayList<Friendship> friendships;

    protected FriendshipValidator friendshipValidator;

    public FriendshipRepository() {
        friendships= new ArrayList<>();
        friendshipValidator = new FriendshipValidator();
    }

    @Override
    public void add(Friendship friendship) {
        //UUID uuid = UUID.randomUUID();
        //String uuidAsString = uuid.toString();
        //friendship.setId(uuidAsString);
        friendshipValidator.validate_all(friendship,friendships);
        friendships.add(friendship);
    }

    @Override
    public void delete(Friendship friendship) {
        friendshipValidator.validate(friendship);
        friendships.remove(friendship);
        friendship.delete();
    }

    @Override
    public void update(Friendship friendship1, Friendship friendship2) {
        try{
            this.findFriendship(friendship1.getFriend1(), friendship1.getFriend2());
        }
        catch (ValidationException ve){
            throw ve;
        }
        //UUID uuid = UUID.randomUUID();
        //String uuidAsString = uuid.toString();
        //friendship2.setId(uuidAsString);
        friendshipValidator.validate_all(friendship2,friendships);
        this.delete(friendship1);
        this.add(friendship2);
    }

    @Override
    public ArrayList<Friendship> get_all() {
        return this.friendships;
    }

    public Friendship findFriendship(User user1, User user2){
        for (Friendship friendship : friendships){
            if (friendship.getFriend1().equals(user1) && friendship.getFriend2().equals(user2) || (friendship.getFriend1().equals(user2) && friendship.getFriend2().equals(user1))){
                return friendship;
            }
        }
        throw new ValidationException("No such friendship");
    }

    public boolean isFriends(User user1, User user2) {
        for (Friendship friendship : friendships){
            if (friendship.getFriend1().equals(user1) && friendship.getFriend2().equals(user2) || (friendship.getFriend1().equals(user2) && friendship.getFriend2().equals(user1)))
                return true;
        }
        return false;
    }

    public void userRemove(User user) {
        ArrayList<Friendship> temp = new ArrayList<>();
        for (Friendship friendship : friendships)
            temp.add(friendship);
        for (Friendship friendship : temp){
            if (friendship.getFriend2().equals(user) || friendship.getFriend1().equals(user))
                friendships.remove(friendship);
        }
        /*
        for (Friendship friendship : temp)
            this.friendships.remove(friendship);
         */
    }

    public void userUpdate(User user1, User user2){
        ArrayList<Friendship> temp = new ArrayList<>();
        for (var friendship: friendships)
            temp.add(friendship);
        for (Friendship friendship : temp) {
            if (friendship.getFriend1().equals(user1)) {
                user2.setId(friendship.getFriend1().getId());
                friendship.setFriend1(user2);
            }
            if (friendship.getFriend2().equals(user2)){
                user1.setId(friendship.getFriend2().getId());
                friendship.setFriend2(user1);
            }
        }
    }

    public ArrayList<Friendship> getFriends(User user){
        return this.friendships;
    }
}
