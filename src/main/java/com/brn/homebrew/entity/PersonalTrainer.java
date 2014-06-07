package com.brn.homebrew.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Bruno Domingues
 */
@Entity
@Table(name = "PERSONAL_TRAINER")
@PrimaryKeyJoinColumn(name = "ID_PERSON")
public class PersonalTrainer extends Person {
}
