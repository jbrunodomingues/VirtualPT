package com.brn.homebrew.controller.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Bruno Domingues
 */
public class PersonalTrainerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Set<ClientDto> clients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ClientDto> getClients() {
        return clients;
    }

    public void setClients(Set<ClientDto> clients) {
        this.clients = clients;
    }
}