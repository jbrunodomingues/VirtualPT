package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.entity.Client;
import com.brn.homebrew.service.ClientService;

/**
 * @author Bruno Domingues
 */
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;

    public ClientServiceImpl() {
    }

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void create(Client client) {
        clientDao.create(client);
    }

    @Override
    public Client read(Long id) {
        return null;
    }

    @Override
    public void update(Client client) {

    }

    @Override
    public void delete(Client client) {

    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }
}
