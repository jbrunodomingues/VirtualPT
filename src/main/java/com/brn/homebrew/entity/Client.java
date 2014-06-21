package com.brn.homebrew.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bruno Domingues
 */
@Entity
@Table(name = "CLIENT")
@PrimaryKeyJoinColumn(name = "ID_PERSON")
public class Client extends Person {

    @ManyToMany(mappedBy = "clients")
    private Set<PersonalTrainer> personalTrainers = new HashSet<>();

    public Set<PersonalTrainer> getPersonalTrainers() {
        return personalTrainers;
    }

    public void setPersonalTrainers(Set<PersonalTrainer> personalTrainerList) {
        this.personalTrainers = personalTrainerList;
    }

    @Override
    public String toString() {
        return "Client{} " + super.toString();
    }
}