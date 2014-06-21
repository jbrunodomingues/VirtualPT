package com.brn.homebrew.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bruno Domingues
 */
@Entity
@Table(name = "PERSONAL_TRAINER")
@PrimaryKeyJoinColumn(name = "ID_PERSON")
public class PersonalTrainer extends Person {

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "PERSONAL_TRAINER_CLIENT",
            joinColumns = {@JoinColumn(name = "REFID_PERSONAL_TRAINER")},
            inverseJoinColumns = {@JoinColumn(name = "REFID_CLIENT")})
    private Set<Client> clients = new HashSet<>();

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clientList) {
        this.clients = clientList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PersonalTrainer that = (PersonalTrainer) o;

        if (clients != null ? !clients.equals(that.clients) : that.clients != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (clients != null ? clients.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonalTrainer{" +
                "clients=" + clients +
                "} " + super.toString();
    }
}
