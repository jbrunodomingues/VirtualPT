package com.brn.homebrew.dao;

/**
 * @author Bruno Domingues
 */
public interface Dao<T> {

    Long create(T entity);

    T read(Long id);

    void update(T entity);

    void delete(T entity);
}