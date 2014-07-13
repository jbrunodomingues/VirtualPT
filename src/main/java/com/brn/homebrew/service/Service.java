package com.brn.homebrew.service;

/**
 * @author Bruno Domingues
 */
public interface Service<T> {

    Long create(T client);

    T read(Long id);

    void update(T entity);

    void delete(T entity);
}