package com.brn.homebrew.entity;

import javax.persistence.*;

/**
 * @author Bruno Domingues
 */
@Entity
@Table(name = "PT_CLIENT_ASSOCIATION")
public class PtClientAssociation {

    @Id
    @Column(name = "ID_PT_CLIENT_ASSOCIATION")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REFID_PERSONAL_TRAINER")
    private PersonalTrainer personalTrainer;

    @ManyToOne
    @JoinColumn(name = "REFID_CLIENT")
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "PtClientAssociation{" +
                "id=" + id +
                ", personalTrainer=" + personalTrainer +
                ", client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PtClientAssociation that = (PtClientAssociation) o;

        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (personalTrainer != null ? !personalTrainer.equals(that.personalTrainer) : that.personalTrainer != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (personalTrainer != null ? personalTrainer.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }
}