package com.brn.homebrew.service.impl;

import com.brn.homebrew.dao.ClientDao;
import com.brn.homebrew.dao.PersonalTrainerDao;
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

    private ClientDao clientDao;
    private PersonalTrainerDao personalTrainerDao;

    @Override
    public List<PtClientAssociation> readAllFromPersonalTrainer(PersonalTrainer personalTrainer) {
        return ((PtClientAssociationDao) dao).readAllFromPersonalTrainer(personalTrainer);
    }

    @Override
    public List<PtClientAssociation> readAllFromClient(Client client) {
        return ((PtClientAssociationDao) dao).readAllFromClient(client);
    }

    @Override
    public Long create(long personalTrainerId, long clientId) {
        PersonalTrainer personalTrainer = personalTrainerDao.read(personalTrainerId);
        Client client = clientDao.read(clientId);
        PtClientAssociation ptClientAssociation = new PtClientAssociation();
        ptClientAssociation.setPersonalTrainer(personalTrainer);
        ptClientAssociation.setClient(client);
        return dao.create(ptClientAssociation);
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void setPersonalTrainerDao(PersonalTrainerDao personalTrainerDao) {
        this.personalTrainerDao = personalTrainerDao;
    }

    public void setPtClientAssociationDao(PtClientAssociationDao ptClientAssociationDao) {
        setDao(ptClientAssociationDao);
    }
}