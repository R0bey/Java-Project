package com.example.lab4.entities.validators;

import com.example.lab4.entities.User;

import java.util.ArrayList;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.getFirstName().equals(""))
            throw new ValidationException("Introduceti un nume valid");
        if (entity.getLastName().equals(""))
            throw new ValidationException("Introduceti un nume valid");
    }

    public void unique(User user, ArrayList<User> users) throws  ValidationException{
        for (var u : users)
            if (user.equals(u))
                throw new ValidationException("Userul exista deja");
    }

    public void validate_all(User user, ArrayList<User> users){
        validate(user);
        unique(user,users);
    }
}
