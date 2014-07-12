package com.brn.homebrew.service;

import com.brn.homebrew.entity.Client;
import com.brn.homebrew.entity.PersonalTrainer;
import com.brn.homebrew.entity.PtClientAssociation;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public interface PtClientAssociationService extends Service<PtClientAssociation> {

    List<PtClientAssociation> readAllFromPersonalTrainer(PersonalTrainer personalTrainer);

    List<PtClientAssociation> readAllFromClient(Client client);
}
