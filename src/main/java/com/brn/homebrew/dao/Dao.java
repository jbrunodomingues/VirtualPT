package com.brn.homebrew.dao;

/**
 * @author Bruno Domingues
 */
public interface Dao<T> {

    void create(T client);

    T read(Long id);

    void update(T client);

    void delete(T client);
}