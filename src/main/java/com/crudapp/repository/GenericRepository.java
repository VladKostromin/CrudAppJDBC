package com.crudapp.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T, ID> {
    T findById(ID id);
    List<T> getAll();
    T save(T t);
    T update(T t);
    void deleteById(ID id);
}
