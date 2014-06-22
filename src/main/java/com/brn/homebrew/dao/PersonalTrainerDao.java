package com.brn.homebrew.dao;

import com.brn.homebrew.entity.PersonalTrainer;

import java.util.List;

/**
 * @author Bruno Domingues
 */
public interface PersonalTrainerDao extends Dao<PersonalTrainer> {

    List<PersonalTrainer> readAll();
}
