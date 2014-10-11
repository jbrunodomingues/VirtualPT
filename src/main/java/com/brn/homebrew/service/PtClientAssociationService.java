package com.brn.homebrew.service;

import com.brn.homebrew.model.Client;
import com.brn.homebrew.model.PersonalTrainer;
import com.brn.homebrew.model.PtClientAssociation;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public interface PtClientAssociationService extends Service<PtClientAssociation> {

    List<PtClientAssociation> readAllFromPersonalTrainer(PersonalTrainer personalTrainer);

    List<PtClientAssociation> readAllFromClient(Client client);
}
