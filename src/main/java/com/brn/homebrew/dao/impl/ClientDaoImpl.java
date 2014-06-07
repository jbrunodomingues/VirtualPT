package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.entity.Client;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * @author Bruno Domingues
 */
public class ClientDaoImpl implements ClientDao {

    private SessionFactory sessionFactory;

    @Override
    public void create(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.save(client);
    }

    @Override
    public Client read(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Client client = (Client) session.get(Client.class, id);
        return client;
    }

    @Override
    public void update(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.save(client);
    }

    @Override
    public void delete(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(client);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
