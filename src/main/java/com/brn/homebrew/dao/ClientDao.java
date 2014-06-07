package com.brn.homebrew.dao;

import com.brn.homebrew.entity.Client;

/**
 * @author Bruno Domingues
 */
public interface ClientDao {

    void create(Client client);

    Client read(Long id);

    void update(Client client);

    void delete(Client client);
}
