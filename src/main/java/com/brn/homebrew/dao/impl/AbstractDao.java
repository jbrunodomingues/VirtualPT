package com.brn.homebrew.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Bruno Domingues
 */
public abstract class AbstractDao<T> {

    private SessionFactory sessionFactory;

    public void create(T personalTrainer) {
        getSession().save(personalTrainer);
    }

    @SuppressWarnings("unchecked")
    public T read(Long id) {
        return (T) getSession().get(getTargetClass(), id);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected abstract Class getTargetClass();

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}