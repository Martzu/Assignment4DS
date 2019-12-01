package com.example.springdemo.repositories;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T>{

    List<T> findAll();

    T save(T element);

    void delete(T element);

    Optional<T> findById(int id);
}
