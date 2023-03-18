package com.example.lab4.entities.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
