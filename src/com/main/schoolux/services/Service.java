package com.main.schoolux.services;
import java.util.Collection;




// interface
public interface Service<T> {


    boolean alreadyExist(T t);

    T selectOneByIdOrNull(int id);

    Collection<T> selectAllOrNull();




    void insert(T t);

    void update(T t);

    void delete(T t);
}