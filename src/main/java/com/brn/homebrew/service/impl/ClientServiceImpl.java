package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.entity.Client;
import com.brn.homebrew.service.ClientService;

/**
 * @author Bruno Domingues
 */
public class ClientServiceImpl extends AbstractService<Client> implements ClientService {

    public ClientServiceImpl() {
    }

    public ClientServiceImpl(ClientDao clientDao) {
        setDao(clientDao);
    }

    public void setClientDao(ClientDao clientDao) {
        setDao(clientDao);
    }
}