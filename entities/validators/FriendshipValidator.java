package com.example.lab4.entities.validators;

import com.example.lab4.entities.Friendship;
import com.example.lab4.entities.User;

import java.util.ArrayList;

public class FriendshipValidator implements Validator<Friendship>{

    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (entity.getFriend1().equals(entity.getFriend2()))
            throw new ValidationException("Please provide different users");
    }
    public void unique(Friendship friendship, ArrayList<Friendship> friendships) throws ValidationException{
        for (var f : friendships)
            if ((friendship.getFriend1().equals(f.getFriend1()) && friendship.getFriend2().equals(f.getFriend2())) ||
                    (friendship.getFriend2().equals(f.getFriend1()) && friendship.getFriend1().equals(f.getFriend2())))
                throw  new ValidationException("Friendship already exists");
    }

    public void validate_all(Friendship friendship, ArrayList<Friendship> friendships){
        this.validate(friendship);
        this.unique(friendship,friendships);
    }
}
