package com.brn.homebrew.dao.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.model.Client;

/**
 * @author Bruno Domingues
 */
public class ClientDaoImpl extends AbstractDao<Client> implements ClientDao {

    @Override
    protected Class getTargetClass() {
        return Client.class;
    }
}
