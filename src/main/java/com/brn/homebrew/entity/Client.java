package com.brn.homebrew.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Bruno Domingues
 */
@Entity
@Table(name = "CLIENT")
@PrimaryKeyJoinColumn(name = "ID_PERSON")
public class Client extends Person {
}