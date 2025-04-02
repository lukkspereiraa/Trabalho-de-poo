package model.dao;

import java.util.List;
import model.exceptions.DatabaseException;

public interface BaseDAO<T> {
    void insert(T entity) throws DatabaseException;
    void update(T entity) throws DatabaseException;
    void deleteById(Long id) throws DatabaseException;
    T findById(Long id) throws DatabaseException;
    List<T> findAll() throws DatabaseException;
}