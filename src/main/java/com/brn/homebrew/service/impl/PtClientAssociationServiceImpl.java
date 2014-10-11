package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.PtClientAssociationDao;
import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;
import com.brn.homebrew.service.PtClientAssociationService;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public class PtClientAssociationServiceImpl extends AbstractService<PtClientAssociation> implements PtClientAssociationService {
    @Override
    public List<PtClientAssociation> readAllFromPersonalTrainer(PersonalTrainer personalTrainer) {
        return ((PtClientAssociationDao) dao).readAllFromPersonalTrainer(personalTrainer);
    }

    @Override
    public List<PtClientAssociation> readAllFromClient(Client client) {
        return ((PtClientAssociationDao) dao).readAllFromClient(client);
    }
}