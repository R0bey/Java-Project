package com.example.lab4.repository;

import java.util.ArrayList;
import java.util.List;

public interface RepositoryInterface <T> {
    void add(T entity);
    void delete(T entity);
    void update(T entity, T entity2);
    ArrayList<T> get_all();
}
