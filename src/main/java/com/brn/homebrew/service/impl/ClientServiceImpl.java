package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.dao.PtClientAssociationDao;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PtClientAssociation;
import com.brn.homebrew.service.ClientService;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public class ClientServiceImpl extends AbstractService<Client> implements ClientService {

    private PtClientAssociationDao ptClientAssociationDao;

    @Override
    public void delete(Client client) {
        List<PtClientAssociation> ptClientAssociationList = ptClientAssociationDao.readAllFromClient(client);
        if (ptClientAssociationList.size() > 0) {
            throw new IllegalStateException("Client with associations can't be deleted");
        }
        super.delete(client);
    }

    public void setClientDao(ClientDao clientDao) {
        setDao(clientDao);
    }

    public void setPtClientAssociationDao(PtClientAssociationDao ptClientAssociationDao) {
        this.ptClientAssociationDao = ptClientAssociationDao;
    }
}