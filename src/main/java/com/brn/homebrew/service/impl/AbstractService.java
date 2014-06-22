package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.Dao;

/**
 * @author Bruno Domingues
 */
public abstract class AbstractService<T> {

    protected Dao<T> dao;

    public void create(T entity) {
        dao.create(entity);
    }

    public T read(Long id) {
        return dao.read(id);
    }

    public void update(T entity) {
        dao.update(entity);
    }

    public void delete(T entity) {
        dao.delete(entity);
    }

    public void setDao(Dao<T> dao) {
        this.dao = dao;
    }
}