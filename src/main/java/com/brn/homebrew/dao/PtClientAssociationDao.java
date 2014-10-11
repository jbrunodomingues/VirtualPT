package com.brn.homebrew.dao;

import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public interface PtClientAssociationDao extends Dao<PtClientAssociation> {

    List<PtClientAssociation> readAll();

    List<PtClientAssociation> readAllFromPersonalTrainer(PersonalTrainer personalTrainer);

    @SuppressWarnings("unchecked")
    List<PtClientAssociation> readAllFromClient(Client client);
}