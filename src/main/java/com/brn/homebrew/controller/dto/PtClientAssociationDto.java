package com.brn.homebrew.controller.dto;

/**
 * @author Bruno Domingues
 */
public class PtClientAssociationDto {

    private Long id;
    private PersonalTrainerDto personalTrainer;
    private ClientDto client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalTrainerDto getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainerDto personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }
}