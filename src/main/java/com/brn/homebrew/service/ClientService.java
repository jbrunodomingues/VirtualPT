package com.brn.homebrew.service;

import com.brn.homebrew.entity.Client;

/**
 * @author Bruno Domingues
 */
public interface ClientService {

    void create(Client client);

    Client read(Long id);

    void update(Client client);

    void delete(Client client);
}
